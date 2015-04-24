package com.presidentio.testdatagenerator.output.path;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by presidentio on 24.04.15.
 */
public class TimeBasedPathProvider implements PathProvider {

    private String suffix = "";
    private String prefix = "";

    private SimpleDateFormat format = new SimpleDateFormat("/yyyy/MM/dd/HH/");

    @Override
    public void init(Map<String, String> props) {
        if (props.containsKey(PropConst.SUFFIX)) {
            suffix = props.get(PropConst.SUFFIX);
        }
        if (props.containsKey(PropConst.PREFIX)) {
            prefix = props.get(PropConst.PREFIX);
        }
    }

    @Override
    public String getFilePath(Template template, Map<String, Object> map) {
        return prefix + format.format(new Date()) + suffix;
    }
}
