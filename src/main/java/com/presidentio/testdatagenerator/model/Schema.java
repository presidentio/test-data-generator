package com.presidentio.testdatagenerator.model;

import java.util.List;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Schema {

    private List<Template> templates;

    private List<String> root;

    private List<Variable> variables;

    private Output output;

    public List<Template> getTemplates() {
        return templates;
    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }

    public List<String> getRoot() {
        return root;
    }

    public void setRoot(List<String> root) {
        this.root = root;
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public Output getOutput() {
        return output;
    }

    public void setOutput(Output output) {
        this.output = output;
    }
}
