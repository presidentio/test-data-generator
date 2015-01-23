package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class RandomProviderTest {

    @Test
    public void testNextValue() throws Exception {
        RandomProvider randomProvider = new RandomProvider(Collections.<String, String>emptyMap());
        Assert.assertNotNull(randomProvider.nextValue(new Context(), new Field(null, TypeConst.BOOLEAN, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(), new Field(null, TypeConst.STRING, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(), new Field(null, TypeConst.INT, null)));
        Assert.assertNotNull(randomProvider.nextValue(new Context(), new Field(null, TypeConst.LONG, null)));

    }
}