package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.output.formatter.Formatter;
import com.presidentio.testdatagenerator.output.formatter.SqlFormatter;

import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
public class SqlDirectSink extends AbstractJdbcSink {

    @Override
    public void init(Map<String, String> props) {
        super.init(props);
        Formatter formatter = new SqlFormatter();
        formatter.init(props);
        init(formatter);
    }
}
