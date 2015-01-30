package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Vitaliy on 24.01.2015.
 */
public class SelectProvider implements ValueProvider {

    private String[] items;

    private String delimiter = ",";
    private Random random = new Random();

    public SelectProvider(Map<String, String> props) {
        Map<String, String> propsCopy = new HashMap<>(props);
        if (propsCopy.containsKey(PropConst.DELIMITER)) {
            delimiter = propsCopy.remove(PropConst.DELIMITER);
        }
        String items = propsCopy.remove(PropConst.ITEMS);
        if (items == null) {
            throw new IllegalArgumentException("Items does not specified or null");
        }
        this.items = items.split(delimiter);
        if (this.items.length <= 0) {
            throw new IllegalArgumentException("Items are empty");
        }
        if (!propsCopy.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for RandomProvider: " + propsCopy);
        }
    }

    @Override
    public Object nextValue(Context context, Field field) {
        int index = random.nextInt(items.length);
        return TypeConverter.convert(items[index], field.getType());
    }

}
