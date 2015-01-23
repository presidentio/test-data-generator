package com.presidentio.testdatagenerator.model;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Field {

    private String name;

    private String type;

    private Provider provider;

    public Field() {
    }

    public Field(String name, String type, Provider provider) {
        this.name = name;
        this.type = type;
        this.provider = provider;
    }

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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
