package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.Collections;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class EmailProvider implements ValueProvider<String> {

    private RandomProvider randomProvider;

    private String domain = "email.com";

    public EmailProvider(Map<String, String> props) {
        if (props.containsKey(PropConst.DOMAIN)) {
            domain = props.remove(PropConst.DOMAIN);
        }
        if (!props.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + props);
        }
        randomProvider = new RandomProvider(Collections.<String, String>emptyMap());
    }

    @Override
    public String nextValue(Context context, Field field) {
        if (!field.getType().equals(TypeConst.STRING)) {
            throw new IllegalArgumentException("Illegal type for email required " + TypeConst.STRING + ": "
                    + field.getType());
        }
        return randomProvider.nextValue(context, field).toString() + "@" + domain;
    }

}
