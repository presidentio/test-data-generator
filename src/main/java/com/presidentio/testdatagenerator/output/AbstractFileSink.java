package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractFileSink implements Sink {

    private String file;

    private BufferedOutputStream outputStream;

    public AbstractFileSink(Map<String, String> props) {
        file = props.get(PropConst.FILE);
        if (file == null) {
            throw new IllegalArgumentException(PropConst.FILE + " does not specified or null");
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Failed to create file sink", e);
        }
    }

    protected void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save: " + new String(bytes), e);
        }
    }

    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
