package com.presidentio.testdatagenerator.model;

import java.util.Map;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Output {

    private String type;

    private Map<String, String> props;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getProps() {
        return props;
    }

    public void setProps(Map<String, String> props) {
        this.props = props;
    }
}
