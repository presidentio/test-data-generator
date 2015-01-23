package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.Map;
import java.util.Random;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class RandomProvider implements ValueProvider {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private long size = 10;
    private Random random = new Random();

    public RandomProvider(Map<String, String> props) {
        if (props.containsKey(PropConst.SIZE)) {
            size = Long.valueOf(props.remove(PropConst.SIZE));
        }
        if (!props.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for RandomProvider: " + props);
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        String type = field.getType();
        switch (type) {
            case TypeConst.STRING:
                StringBuilder result = new StringBuilder((int) size);
                for (int i = 0; i < size; i++) {
                    result.append(ALPHABET.charAt(random.nextInt(ALPHABET.length())));
                }
                return result;
            case TypeConst.LONG:
                return random.nextLong() % size;
            case TypeConst.INT:
                return random.nextInt((int) size);
            case TypeConst.BOOLEAN:
                return random.nextBoolean();
            default:
                throw new IllegalArgumentException("Field type not known: " + type);
        }
    }
}
