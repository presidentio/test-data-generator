package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.SinkTypeConst;
import com.presidentio.testdatagenerator.model.Output;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class SinkFactory {

    public Sink getSink(Output output) {
        switch (output.getType()) {
            case SinkTypeConst.CONSOLE:
                return new ConsoleSink(output.getProps());
            case SinkTypeConst.SQL:
                return new SqlSink(output.getProps());
            case SinkTypeConst.ES:
                return new EsSink(output.getProps());
            default:
                throw new IllegalArgumentException("Unknown sink type: " + output.getType());
        }
    }

}
