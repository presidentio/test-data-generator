package com.presidentio.testdatagenerator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Template {

    private String id;

    private int count;

    private String name;

    private List<Field> fields;

    private List<String> child = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<String> getChild() {
        return child;
    }

    public void setChild(List<String> child) {
        this.child = child;
    }
}
