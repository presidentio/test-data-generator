/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

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
            Map<String, Object> entity = new LinkedHashMap<>();
            Map<String, Object> outputEntity = new LinkedHashMap<>();
            for (Field field : template.getFields()) {
                ValueProvider valueProvider = valueProviderFactory.buildValueProvider(field.getProvider());
                Object nextValue = valueProvider.nextValue(context, field);
                entity.put(field.getName(), nextValue);
                if (!field.isIgnored())
                {
                    outputEntity.put(field.getName(), nextValue);
                }
            }
            context.getSink().process(template, outputEntity);
            final Context childContext = new Context(context, entity);
            for (String childTemplateId : template.getChildren()) {
                final Template childTemplate = context.getTemplates().get(childTemplateId);
                if (childTemplate == null) {
                    throw new IllegalArgumentException("Template not defined: " +  childTemplateId);
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
    public void compute() {
        generateEntity(context, template);
    }
}
