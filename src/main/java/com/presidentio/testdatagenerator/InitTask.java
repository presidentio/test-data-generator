package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Output;
import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.model.Variable;
import com.presidentio.testdatagenerator.output.Sink;
import com.presidentio.testdatagenerator.output.SinkFactory;
import com.presidentio.testdatagenerator.provider.DefaultValueProviderFactory;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Vitalii_Gergel on 3/18/2015.
 */
public class InitTask extends RecursiveAction {

    private Schema schema;
    private ValueProviderFactory valueProviderFactory = new DefaultValueProviderFactory();
    private SinkFactory sinkFactory = new SinkFactory();

    private boolean async = true;

    public InitTask(Schema schema) {
        this.schema = schema;
    }

    @Override
    protected void compute() {
        final Context context = buildContext(schema);
        List<GenerateTask> tasks = new ArrayList<>(schema.getRoot().size());
        for (String rootTemplateId : schema.getRoot()) {
            final Template rootTemplate = context.getTemplates().get(rootTemplateId);
            if (rootTemplate == null) {
                throw new IllegalArgumentException("Template with id does not defined: " + rootTemplateId);
            }
            GenerateTask task = new GenerateTask(context, rootTemplate, valueProviderFactory);
            task.setAsync(async);
            tasks.add(task);
        }
        if (async) {
            invokeAll(tasks);
        } else {
            for (GenerateTask forkJoinTask : tasks) {
                forkJoinTask.compute();
            }
        }
        context.getSink().close();

    }

    private Context buildContext(Schema schema) {
        Map<String, Object> variables = new HashMap<>();
        for (Variable variable : schema.getVariables()) {
            Object initValue;
            switch (variable.getType()) {
                case TypeConst.STRING:
                    initValue = variable.getInitValue();
                    break;
                case TypeConst.LONG:
                    initValue = Long.valueOf(variable.getInitValue());
                    break;
                case TypeConst.INT:
                    initValue = Integer.valueOf(variable.getInitValue());
                    break;
                case TypeConst.BOOLEAN:
                    initValue = Boolean.valueOf(variable.getInitValue());
                    break;
                default:
                    throw new IllegalArgumentException("Variable type not known: " + variable.getType());
            }

            variables.put(variable.getName(), initValue);
        }

        Sink sink = buildSink(schema.getOutput());

        Map<String, Template> templates = new HashMap<>();
        for (Template template : schema.getTemplates()) {
            templates.put(template.getId(), template);
        }

        return new Context(templates, variables, sink);
    }

    private Sink buildSink(Output output) {
        return sinkFactory.getSink(output);
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public ValueProviderFactory getValueProviderFactory() {
        return valueProviderFactory;
    }

    public void setValueProviderFactory(ValueProviderFactory valueProviderFactory) {
        this.valueProviderFactory = valueProviderFactory;
    }

    public SinkFactory getSinkFactory() {
        return sinkFactory;
    }

    public void setSinkFactory(SinkFactory sinkFactory) {
        this.sinkFactory = sinkFactory;
    }
}
