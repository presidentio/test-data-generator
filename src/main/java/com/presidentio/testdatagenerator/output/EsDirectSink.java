package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.output.formatter.EsFormatter;
import com.presidentio.testdatagenerator.output.formatter.Formatter;

import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
public class EsDirectSink extends AbstractEsSink {

    @Override
    public void init(Map<String, String> props) {
        super.init(props);
        Formatter formatter = new EsFormatter();
        formatter.init(props);
        init(formatter);
    }
    
}
