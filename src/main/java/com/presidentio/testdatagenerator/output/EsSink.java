package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class EsSink extends AbstractFileSink {

    private static final String INDEX = "{ \"index\" : { \"_index\" : \"%s\", \"_type\" : \"%s\"} }";
    private static final String NEW_LINE = "\n";

    private ObjectMapper objectMapper = new ObjectMapper();

    private String index;

    public EsSink(Map<String, String> props) {
        super(props);
        index = props.get(PropConst.INDEX);
        if (index == null) {
            throw new IllegalArgumentException(PropConst.INDEX + " does not specified or null");
        }
    }

    @Override
    public void process(Template template, Map<String, Object> map) {
        String result = String.format(INDEX, index, template.getName());
        result += NEW_LINE;
        try {
            result += objectMapper.writeValueAsString(map);
            result += NEW_LINE;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        write(result.getBytes());
    }
}
