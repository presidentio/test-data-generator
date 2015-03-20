package com.presidentio.testdatagenerator.benchmark;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.provider.CounterProvider;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import org.openjdk.jmh.annotations.*;

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
public class CounterBenchmark {

    private Context context;
    private ValueProvider valueProvider;
    private Field field;

    @Setup
    public void init() {
        Map<String, String> props = new HashMap<>();
        props.put(PropConst.VAR, "a");
        valueProvider = new CounterProvider();
        valueProvider.init(props);
        Map<String, Object> variables = new HashMap<>();
        variables.put("a", "1");
        context = new Context(null, variables, null);
        field = new Field(null, TypeConst.LONG, null);
    }

    @Benchmark
    public void measureExpression() {
        valueProvider.nextValue(context, field);
    }

}
