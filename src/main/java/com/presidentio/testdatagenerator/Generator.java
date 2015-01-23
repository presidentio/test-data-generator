package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.context.Parent;
import com.presidentio.testdatagenerator.model.*;
import com.presidentio.testdatagenerator.output.ConsoleSink;
import com.presidentio.testdatagenerator.output.Sink;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.*;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class Generator {

    private static ValueProviderFactory valueProviderFactory = new ValueProviderFactory();

    public void generate(Schema schema) {
        Context context = buildContext(schema);
        for (String rootTemplateId : schema.getRoot()) {
            Template rootTemplate = context.getTemplates().get(rootTemplateId);
            if (rootTemplate == null) {
                throw new RuntimeException("Template with id does not defined: " + rootTemplateId);
            }
            generateEntity(context, rootTemplate);
        }
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
                    throw new RuntimeException("Template with id does not defined: " + childTemplateId);
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

    private Sink buildSink(Output output) {
        return new ConsoleSink();
    }

}
