package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.mvel2.MVEL;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class ExpressionProvider implements ValueProvider {

    private String expr;

    public ExpressionProvider(Map<String, String> props) {
        if (props.containsKey(PropConst.EXPR)) {
            expr = props.remove(PropConst.EXPR);
        }
        if (!props.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + props);
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        Serializable compiledExpression = MVEL.compileExpression(expr);
        Class type;
        switch (field.getType()) {
            case TypeConst.STRING:
                type = String.class;
                break;
            case TypeConst.LONG:
                type = Long.class;
                break;
            case TypeConst.INT:
                type = Integer.class;
                break;
            case TypeConst.BOOLEAN:
                type = Boolean.class;
                break;
            default:
                throw new IllegalArgumentException("Field type not known: " + field.getType());
        }
        return MVEL.executeExpression(compiledExpression, context, context.getVariables(), type);
    }
}
