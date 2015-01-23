package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropCons;
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
        if (props.containsKey(PropCons.DOMAIN)) {
            domain = props.remove(PropCons.DOMAIN);
        }
        if (!props.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + props);
        }
        randomProvider = new RandomProvider(Collections.<String, String>emptyMap());
    }

    @Override
    public String nextValue(Context context, Field field) {
        return randomProvider.nextValue(context, field).toString() + "@" + domain;
    }

}
