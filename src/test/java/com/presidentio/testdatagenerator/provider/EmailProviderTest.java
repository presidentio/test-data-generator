package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class EmailProviderTest {

    @Test
    public void testNextValueWithSpecifiedDomain() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propDomain = "test.domain";
        props.put(PropConst.DOMAIN, propDomain);
        EmailProvider emailProvider = new EmailProvider(props);
        Object result = emailProvider.nextValue(new Context(), new Field(null, TypeConst.STRING, null));
        Assert.assertTrue(result.toString().matches(".+@\\w+\\.\\w+"));
        Assert.assertEquals(propDomain, result.toString().split("@")[1]);
    }

    @Test
    public void testNextValueWithDefaultDomain() throws Exception {
        Map<String, String> props = new HashMap<>();
        EmailProvider emailProvider = new EmailProvider(props);
        Object result = emailProvider.nextValue(new Context(), new Field(null, TypeConst.STRING, null));
        Assert.assertTrue(result.toString().matches(".+@\\w+\\.\\w+"));
    }
}