package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public interface ValueProvider<Type> {

    Type nextValue(Context context, Field field);

}
