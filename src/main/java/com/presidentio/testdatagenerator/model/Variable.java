package com.presidentio.testdatagenerator.model;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Variable {

    private String name;

    private String type;

    private String initValue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getInitValue() {
        return initValue;
    }

    public void setInitValue(String initValue) {
        this.initValue = initValue;
    }
}
