package com.presidentio.testdatagenerator.output.formatter;

import com.presidentio.testdatagenerator.cons.DelimiterConst;
import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
public class EsFormatter implements Formatter {

    private static final String INDEX = "{ \"index\" : { \"_index\" : \"%s\", \"_type\" : \"%s\"} }";

    private ObjectMapper objectMapper = new ObjectMapper();

    private String index;

    @Override
    public void init(Map<String, String> props) {
        index = props.get(PropConst.INDEX);
        if (index == null) {
            throw new IllegalArgumentException(PropConst.INDEX + " does not specified or null");
        }

    }

    @Override
    public String format(Map<String, Object> map, Template template) {
        String result = String.format(INDEX, index, template.getName());
        result += DelimiterConst.NEW_LINE;
        try {
            result += objectMapper.writeValueAsString(map);
            result += DelimiterConst.NEW_LINE;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return result;
    }

    @Override
    public String format(List<Map<String, Object>> maps, Template template) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, Object> map : maps) {
            stringBuilder.append(format(map, template));
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean supportMultiInsert() {
        return true;
    }
}
