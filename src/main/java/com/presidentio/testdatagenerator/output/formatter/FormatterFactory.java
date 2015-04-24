package com.presidentio.testdatagenerator.output.formatter;

import java.util.Map;

/**
 * Created by presidentio on 24.04.15.
 */
public interface FormatterFactory {

    Formatter buildFormatter(Map<String, String> props);

}
