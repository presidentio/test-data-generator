package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.model.Variable;
import com.presidentio.testdatagenerator.output.Sink;
import com.presidentio.testdatagenerator.output.SinkFactory;
import com.presidentio.testdatagenerator.provider.DefaultValueProviderFactory;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by presidentio on 24.04.15.
 */
public abstract class AbstractGenerator implements Generator {

    private ValueProviderFactory valueProviderFactory = new DefaultValueProviderFactory();
    private SinkFactory sinkFactory = new SinkFactory();

    @Override
    public void generate(Schema schema) {
        validateSchema(schema);
        Context context = buildContext(schema);
        generate(context, schema);
    }

    protected abstract void generate(Context context, Schema schema);

    private void validateSchema(Schema schema) {
        assert schema.getOutput() != null;
        Set<String> templateIds = new HashSet<>(schema.getTemplates().size());
        for (Template template : schema.getTemplates()) {
            assert !templateIds.contains(template.getId());
            templateIds.add(template.getId());
            assert !(template.getExtend() == null ^ template.getExtendTemplate() == null);
        }
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

        Sink sink = sinkFactory.getSink(schema.getOutput());

        Map<String, Template> templates = new HashMap<>();
        for (Template template : schema.getTemplates()) {
            templates.put(template.getId(), template);
        }

        return new Context(templates, variables, sink);
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
