package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.SinkTypeConst;
import com.presidentio.testdatagenerator.model.Output;
import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.parser.JsonSchemaSerializer;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractGeneratorTest {

    protected abstract String getSchemaResource();

    @Test
    public void testGenerate() throws Exception {
        JsonSchemaSerializer jsonSchemaSerializer = new JsonSchemaSerializer();
        Schema schema = jsonSchemaSerializer.deserialize(AbstractGeneratorTest.class.getClassLoader()
                .getResourceAsStream(getSchemaResource()));
        Generator generator = new Generator();
        generator.generate(schema);
        testResult(schema.getOutput());
    }

    protected abstract void testResult(Output output);

}
