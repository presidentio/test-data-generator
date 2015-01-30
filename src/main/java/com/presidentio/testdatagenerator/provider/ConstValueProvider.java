package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class ConstValueProvider implements ValueProvider {

    private String value;

    public ConstValueProvider(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        value = propsCopy.remove(PropConst.VALUE);
        if (value == null) {
            throw new IllegalArgumentException("Value does not specified or null");
        }
        if (!propsCopy.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for ConstValueProvider: " + propsCopy);
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        String type = field.getType();
        switch (type) {
            case TypeConst.STRING:
                return value;
            case TypeConst.LONG:
                return Long.valueOf(value);
            case TypeConst.INT:
                return Integer.valueOf(value);
            case TypeConst.BOOLEAN:
                return Boolean.valueOf(value);
            default:
                throw new IllegalArgumentException("Field type not known: " + type);
        }
    }

}
