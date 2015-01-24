package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.cons.ValueProviderNameConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.model.Provider;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by Vitaliy on 24.01.2015.
 */
public class PreopleNameValueProviderTest {

    @Test
    public void testNextValue() throws Exception {
        ValueProvider valueProvider = new ValueProviderFactory().buildValueProvider(
                new Provider(ValueProviderNameConst.PEOPLE_NAME, new HashMap<String, String>()));
        Assert.assertNotNull(valueProvider.nextValue(new Context(), new Field(null, TypeConst.STRING, null)));
    }
}
