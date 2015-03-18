package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Vitalii_Gergel on 3/18/2015.
 */
public class GenerateTask extends RecursiveAction {

    private boolean async = true;

    private Context context;
    private Template template;
    private ValueProviderFactory valueProviderFactory;

    public GenerateTask(Context context, Template template, ValueProviderFactory valueProviderFactory) {
        this.context = context;
        this.template = template;
        this.valueProviderFactory = valueProviderFactory;
    }

    private void generateEntity(final Context context, final Template template) {
        List<GenerateTask> forkJoinTasks = new ArrayList<>(template.getCount());
        for (int i = 0; i < template.getCount(); i++) {
            Map<String, Object> entity = new HashMap<>();
            for (Field field : template.getFields()) {
                ValueProvider valueProvider = valueProviderFactory.buildValueProvider(field.getProvider());
                entity.put(field.getName(), valueProvider.nextValue(context, field));
            }
            context.getSink().process(template, entity);
            final Context childContext = new Context(context, entity);
            for (String childTemplateId : template.getChilds()) {
                final Template childTemplate = context.getTemplates().get(childTemplateId);
                if (childTemplate == null) {
                    throw new IllegalArgumentException("Template with id does not defined: " + childTemplateId);
                }

                GenerateTask task = new GenerateTask(childContext, childTemplate, valueProviderFactory);
                task.setAsync(async);
                forkJoinTasks.add(task);
            }
        }
        if (async) {
            invokeAll(forkJoinTasks);
        } else {
            for (GenerateTask forkJoinTask : forkJoinTasks) {
                forkJoinTask.compute();
            }
        }
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    @Override
    protected void compute() {
        generateEntity(context, template);
    }
}
