package com.presidentio.testdatagenerator.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class Parent extends HashMap<String, Object> {

    private Parent parent;

    public Parent() {
    }

    public Parent(Map<String, Object> entity, Parent parent) {
        super(entity);
        this.parent = parent;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }
}
