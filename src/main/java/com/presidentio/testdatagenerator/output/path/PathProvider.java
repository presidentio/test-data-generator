package com.presidentio.testdatagenerator.output.path;

import com.presidentio.testdatagenerator.model.Template;

import java.util.Map;

/**
 * Created by presidentio on 24.04.15.
 */
public interface PathProvider {

    void init(Map<String, String> props);

    String getFilePath(Template template, Map<String, Object> map);

}
