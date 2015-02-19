package com.presidentio.testdatagenerator.output.formatter;

import com.presidentio.testdatagenerator.model.Template;

import java.util.List;
import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
public interface Formatter {

    void init(Map<String, String> props);

    String format(Map<String, Object> map, Template template);

    String format(List<Map<String, Object>> maps, Template template);
    
    boolean supportMultiInsert();

}
