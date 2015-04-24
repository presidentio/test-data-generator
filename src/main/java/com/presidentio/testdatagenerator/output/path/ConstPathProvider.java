package com.presidentio.testdatagenerator.output.path;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;

import java.util.Map;

/**
 * Created by presidentio on 24.04.15.
 */
public class ConstPathProvider implements PathProvider {

    private String filePath;

    @Override
    public void init(Map<String, String> props) {
        filePath = props.get(PropConst.FILE);
        if (filePath == null) {
            throw new IllegalArgumentException(PropConst.FILE + " does not specified or null");
        }
    }

    @Override
    public String getFilePath(Template template, Map<String, Object> map) {
        return filePath;
    }
}
