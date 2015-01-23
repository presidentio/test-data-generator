package com.presidentio.testdatagenerator.context;

import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.Sink;

import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class Context {

    private Parent parent;

    private Map<String, Template> templates;

    private Map<String, Object> variables;

    private Sink sink;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public Sink getSink() {
        return sink;
    }

    public void setSink(Sink sink) {
        this.sink = sink;
    }

    public Map<String, Template> getTemplates() {
        return templates;
    }

    public void setTemplates(Map<String, Template> templates) {
        this.templates = templates;
    }
}
