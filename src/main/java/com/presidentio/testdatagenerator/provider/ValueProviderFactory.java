package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.ValueProviderNameConst;
import com.presidentio.testdatagenerator.model.Provider;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class ValueProviderFactory {

    private static String NAMES;

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
            case ValueProviderNameConst.SELECT:
                return new SelectProvider(props);
            case ValueProviderNameConst.PEOPLE_NAME:
                props.put(PropConst.ITEMS, getNames());
                props.put(PropConst.DELIMITER, "\n");
                return new SelectProvider(props);
            default:
                throw new IllegalArgumentException("Unknown value provider: " + provider.getName());
        }
    }

    private static String getNames() {
        if (NAMES == null) {
            try {
                NAMES = IOUtils.toString(ValueProviderFactory.class.getClassLoader().getResourceAsStream("name.dat"));
            } catch (IOException e) {
                throw new RuntimeException("Failed to load names", e);
            }
        }
        return NAMES;
    }


}
