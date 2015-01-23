package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.model.*;
import com.presidentio.testdatagenerator.parser.JsonSchemaSerializer;
import com.presidentio.testdatagenerator.parser.SchemaSerializer;

import java.io.IOException;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Starter {

    public static void main(String[] args) throws IOException {
        SchemaSerializer schemaSerializer = new JsonSchemaSerializer();
        Schema schema = schemaSerializer.deserialize(Starter.class.getClassLoader().getResourceAsStream("schema.json"));
        Generator generator = new Generator();
        generator.generate(schema);
    }

}
