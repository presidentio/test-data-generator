package com.presidentio.testdatagenerator.model;

import java.util.HashMap;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Provider {

    private String name;

    private HashMap<String, String> props;

    public Provider() {
    }

    public Provider(String name, HashMap<String, String> props) {
        this.name = name;
        this.props = props;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getProps() {
        return props;
    }

    public void setProps(HashMap<String, String> props) {
        this.props = props;
    }
}
