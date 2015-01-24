package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class SelectProviderTest {

    @Test
    public void testNextValue() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propItems = "5,6,7,8,9";
        String propDelimiter = ",";
        props.put(PropConst.ITEMS, propItems);
        props.put(PropConst.DELIMITER, propDelimiter);
        SelectProvider selectProvider = new SelectProvider(props);

        List items = Arrays.asList(propItems.split(propDelimiter));
        Object result = selectProvider.nextValue(new Context(), new Field("testField", TypeConst.STRING, null));
        Assert.assertTrue(items.contains(result));
    }
}