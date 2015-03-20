package com.presidentio.testdatagenerator.benchmark;

import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.provider.RandomProvider;
import com.presidentio.testdatagenerator.provider.ValueProvider;
import org.openjdk.jmh.annotations.*;

import java.util.Collections;
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
public class RandomBenchmark {

    private Context context;
    private ValueProvider valueProvider;
    private Field field;

    @Setup
    public void init() {
        valueProvider = new RandomProvider();
        valueProvider.init(Collections.<String, String>emptyMap());
        field = new Field(null, TypeConst.BOOLEAN, null);
        context = new Context(null, null, null);
    }

    @Benchmark
    public void measureExpression() {
        valueProvider.nextValue(context, field);
    }

}
