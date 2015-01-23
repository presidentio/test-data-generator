package com.presidentio.testdatagenerator.provider;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ExpressionProviderTest {

    @Test
    public void testNextValueSimpleMath() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "2 + 10";
        props.put(PropConst.EXPR, propExpr);
        ExpressionProvider expressionProvider = new ExpressionProvider(props);
        Object result = expressionProvider.nextValue(new Context(), new Field(null, TypeConst.INT, null));
        Assert.assertEquals(12, result);
    }

    @Test
    public void testNextValueMathVariable() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "++a + 10*b";
        props.put(PropConst.EXPR, propExpr);
        ExpressionProvider expressionProvider = new ExpressionProvider(props);
        Context context = new Context();
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", 5);
        variables.put("b", 2);
        context.setVariables(variables);
        Object result = expressionProvider.nextValue(context, new Field(null, TypeConst.INT, null));
        Assert.assertEquals(26, result);
    }

    @Test
    public void testNextValueStringVariable() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "a + b";
        props.put(PropConst.EXPR, propExpr);
        ExpressionProvider expressionProvider = new ExpressionProvider(props);
        Context context = new Context();
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "abc");
        variables.put("b", "zxc");
        context.setVariables(variables);
        Object result = expressionProvider.nextValue(context, new Field(null, TypeConst.STRING, null));
        Assert.assertEquals("abczxc", result);
    }

    @Test
    public void testNextValueType() throws Exception {
        Map<String, String> props = new HashMap<>();
        String propExpr = "a + b";
        props.put(PropConst.EXPR, propExpr);
        ExpressionProvider expressionProvider = new ExpressionProvider(props);
        Context context = new Context();
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "1");
        variables.put("b", "1");
        context.setVariables(variables);
        Object result = expressionProvider.nextValue(context, new Field(null, TypeConst.STRING, null));
        Assert.assertEquals("11", result);
        variables.put("a", 1);
        variables.put("b", 1);
        context.setVariables(variables);
        result = expressionProvider.nextValue(context, new Field(null, TypeConst.INT, null));
        Assert.assertEquals(2, result);
        result = expressionProvider.nextValue(context, new Field(null, TypeConst.LONG, null));
        Assert.assertEquals(2L, result);
        result = expressionProvider.nextValue(context, new Field(null, TypeConst.BOOLEAN, null));
        Assert.assertEquals(true, result);
    }
}