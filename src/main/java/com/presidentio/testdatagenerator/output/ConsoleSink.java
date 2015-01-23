package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.model.Template;

import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class ConsoleSink implements Sink {
    @Override
    public void process(Template template, Map<String, Object> map) {
        System.out.println(map);
    }
}
