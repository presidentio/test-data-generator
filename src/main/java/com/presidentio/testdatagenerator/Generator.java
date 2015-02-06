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

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.context.Parent;
import com.presidentio.testdatagenerator.model.*;
import com.presidentio.testdatagenerator.output.Sink;
import com.presidentio.testdatagenerator.output.SinkFactory;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;
import com.presidentio.testdatagenerator.provider.DefaultValueProviderFactory;

import java.util.HashMap;
import java.util.Map;

public class Generator {

    private static ValueProviderFactory valueProviderFactory = new DefaultValueProviderFactory();
    private static SinkFactory sinkFactory = new SinkFactory();

    public void generate(Schema schema) {
        Context context = buildContext(schema);
        for (String rootTemplateId : schema.getRoot()) {
            Template rootTemplate = context.getTemplates().get(rootTemplateId);
            if (rootTemplate == null) {
                throw new IllegalArgumentException("Template with id does not defined: " + rootTemplateId);
            }
            generateEntity(context, rootTemplate);
        }
        context.getSink().close();
    }

    private void generateEntity(Context context, Template template) {
        for (int i = 0; i < template.getCount(); i++) {
            Map<String, Object> entity = new HashMap<>();
            for (Field field : template.getFields()) {
                ValueProvider valueProvider = valueProviderFactory.buildValueProvider(field.getProvider());
                entity.put(field.getName(), valueProvider.nextValue(context, field));
            }
            context.getSink().process(template, entity);
            for (String childTemplateId : template.getChild()) {
                Template childTemplate = context.getTemplates().get(childTemplateId);
                if (childTemplate == null) {
                    throw new IllegalArgumentException("Template with id does not defined: " + childTemplateId);
                }
                context.setParent(new Parent(entity, context.getParent()));
                generateEntity(context, childTemplate);
                context.setParent(context.getParent().getParent());
            }
        }
    }

    private Context buildContext(Schema schema) {
        Context context = new Context();
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
        context.setVariables(variables);

        context.setSink(buildSink(schema.getOutput()));

        Map<String, Template> templates = new HashMap<>();
        for (Template template : schema.getTemplates()) {
            templates.put(template.getId(), template);
        }
        context.setTemplates(templates);

        return context;
    }

    public static ValueProviderFactory getValueProviderFactory() {
        return valueProviderFactory;
    }

    public static void setValueProviderFactory(ValueProviderFactory valueProviderFactory) {
        Generator.valueProviderFactory = valueProviderFactory;
    }

    private Sink buildSink(Output output) {
        return sinkFactory.getSink(output);
    }

}
