package com.presidentio.testdatagenerator.benchmark;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.provider.ParentProvider;
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
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Measurement(iterations = 10)
@State(Scope.Benchmark)
public class ParentBenchmark {

    private Context context;
    private ValueProvider valueProvider;
    private Field field;

    @Setup
    public void init() {
        Map<String, String> props = new HashMap<>();
        props.put(PropConst.DEPTH, "3");
        props.put(PropConst.FIELD, "id");
        valueProvider = new ParentProvider();
        valueProvider.init(props);
        context = new Context(new Context(new Context(new Context(null, null, null), Collections.<String, Object>singletonMap("id", "123")), new HashMap<String, Object>()), new HashMap<String, Object>());
        field = new Field("testField", TypeConst.STRING, null);
    }

    @Benchmark
    public void measureExpression() {
        valueProvider.nextValue(context, field);
    }

}
