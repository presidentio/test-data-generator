package com.presidentio.testdatagenerator.benchmark;

import com.presidentio.testdatagenerator.Generator;
import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.parser.SchemaBuilder;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;

/**
 * Created by Vitalii_Gergel on 3/17/2015.
 */
public class EsFileBenchmark {

    @Fork(1)
    @Warmup(iterations = 3)
    @Benchmark
    public void measureEsDirect() {
        SchemaBuilder schemaBuilder = new SchemaBuilder();
        for (String resource : Arrays.asList("test-es-file-schema.json")) {
            schemaBuilder.fromResource(resource);
        }
        Schema schema = schemaBuilder.build();
        Generator generator = new Generator();
        generator.generate(schema);
    }
}
