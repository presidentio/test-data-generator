package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.ValueProviderNameConst;
import com.presidentio.testdatagenerator.model.Provider;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class ValueProviderFactory {

    public ValueProvider buildValueProvider(Provider provider) {
        Map<String, String> props = Collections.emptyMap();
        if (provider.getProps() != null) {
            props = new HashMap<>(provider.getProps());
        }
        switch (provider.getName()) {
            case ValueProviderNameConst.CONST:
                return new ConstValueProvider(props);
            case ValueProviderNameConst.EMAIL:
                return new EmailProvider(props);
            case ValueProviderNameConst.EXPR:
                return new ExpressionProvider(props);
            case ValueProviderNameConst.RANDOM:
                return new RandomProvider(props);
            default:
                throw new IllegalArgumentException("Unknown value provider: " + provider.getName());
        }
    }


}
