package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.TypeConst;

/**
 * Created by Vitaliy on 24.01.2015.
 */
public class TypeConverter {

    public static <T> T convert(String value, String type) {
        switch (type) {
            case TypeConst.STRING:
                return (T) value;
            case TypeConst.LONG:
                return (T) Long.valueOf(value);
            case TypeConst.INT:
                return (T) Integer.valueOf(value);
            case TypeConst.BOOLEAN:
                return (T) Boolean.valueOf(value);
            default:
                throw new IllegalArgumentException("Type not known: " + type);
        }
    }

}
