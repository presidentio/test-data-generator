package com.presidentio.testdatagenerator.model;

import com.presidentio.testdatagenerator.parser.StringDeserializer;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Map;

/**
 * Created by Vitaliy on 22.01.2015.
 */
public class Output {

    private String type;

    @JsonDeserialize(contentUsing = StringDeserializer.class)
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
