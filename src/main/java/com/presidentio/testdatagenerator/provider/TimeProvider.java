package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

import java.util.Map;

/**
 * Created by Vitaliy on 23.04.2015.
 */
public class TimeProvider implements ValueProvider {
    
    @Override
    public void init(Map<String, String> props) {
        
    }

    @Override
    public Object nextValue(Context context, Field field) {
        return System.currentTimeMillis();
    }
}
