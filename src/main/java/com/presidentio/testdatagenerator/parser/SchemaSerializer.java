package com.presidentio.testdatagenerator.parser;

import com.presidentio.testdatagenerator.model.Schema;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public interface SchemaSerializer {

    Schema deserialize(InputStream inputStream) throws IOException;

    Schema deserialize(String string) throws IOException;

    void serialize(Schema schema, OutputStream outputStream) throws IOException;

    String serialize(Schema schema) throws IOException;

}
