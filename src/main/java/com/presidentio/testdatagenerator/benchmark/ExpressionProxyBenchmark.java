package com.presidentio.testdatagenerator.benchmark;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.provider.ExpressionProxyProvider;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import org.openjdk.jmh.annotations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vitalii_Gergel on 3/19/2015.
 */
@Fork(1)
@Warmup(iterations = 3)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class ExpressionProxyBenchmark {

    private Context context1;
    private Context context2;
    private Context context3;
    private ValueProvider valueProvider1;
    private ValueProvider valueProvider2;
    private ValueProvider valueProvider3;
    private Field field1;
    private Field field2;
    private Field field3;

    @Setup
    public void init() {
        Map<String, String> props = new HashMap<>();
        String propExpr = "a + b";
        props.put(PropConst.EXPR, propExpr);
        valueProvider1 = new ExpressionProxyProvider();
        valueProvider1.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "abc");
        variables.put("b", "zxc");
        context1 = new Context(null, variables, null);
        field1 = new Field(null, TypeConst.STRING, null);

        props = new HashMap<>();
        propExpr = "a++";
        props.put(PropConst.EXPR, propExpr);
        valueProvider2 = new ExpressionProxyProvider();
        valueProvider2.init(props);
        variables = new HashMap<>();
        variables.put("a", "2");
        context2 = new Context(null, variables, null);
        field2 = new Field(null, TypeConst.STRING, null);

        props = new HashMap<>();
        propExpr = "parent.parent.id";
        props.put(PropConst.EXPR, propExpr);
        valueProvider3 = new ExpressionProxyProvider();
        valueProvider3.init(props);
        variables = new HashMap<>();
        variables.put("a", "2");
        context3 = new Context(new Context(new Context(null, variables, null), Collections.<String, Object>singletonMap("id", "abc")), Collections.<String, Object>emptyMap());
        field3 = new Field(null, TypeConst.STRING, null);
    }

    @Benchmark
    public void measureExpression() {
        valueProvider1.nextValue(context1, field1);
    }

    @Benchmark
    public void measureCounter() {
        valueProvider2.nextValue(context2, field2);
    }

    @Benchmark
    public void measureParent() {
        valueProvider3.nextValue(context3, field3);
    }

}
