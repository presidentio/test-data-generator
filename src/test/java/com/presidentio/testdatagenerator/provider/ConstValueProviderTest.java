package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ConstValueProviderTest {

    @Test(expected = IllegalArgumentException.class)
    public void testRequiredProp() throws Exception {
        new ConstValueProvider(Collections.<String, String>emptyMap());
    }

    @Test
    public void testNextValue() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propValue = "123";
        props.put(PropConst.VALUE, propValue);
        ConstValueProvider constValueProvider = new ConstValueProvider(props);

        Object result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.STRING, null));
        Assert.assertEquals(propValue, result);

        result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.BOOLEAN, null));
        Assert.assertEquals(Boolean.valueOf(propValue), result);

        result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.INT, null));
        Assert.assertEquals(Integer.valueOf(propValue), result);

        result = constValueProvider.nextValue(new Context(), new Field("testField", TypeConst.LONG, null));
        Assert.assertEquals(Long.valueOf(propValue), result);

    }
}