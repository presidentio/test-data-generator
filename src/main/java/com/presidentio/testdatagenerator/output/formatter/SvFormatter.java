package com.presidentio.testdatagenerator.output.formatter;

import com.presidentio.testdatagenerator.cons.DelimiterConst;
import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by presidentio on 23.04.15.
 */
public class SvFormatter implements Formatter {

    private String defaultDelimiter = DelimiterConst.COMMA;

    private boolean printHeader;

    private Set<String> header;

    private String delimiter;

    public SvFormatter(String defaultDelimiter) {
        this.defaultDelimiter = defaultDelimiter;
    }

    public SvFormatter() {
    }

    @Override
    public void init(Map<String, String> props) {
        String headerStr = props.get(PropConst.HEADER);
        printHeader = Boolean.valueOf(headerStr);

        delimiter = props.get(PropConst.DELIMITER);
        if (delimiter == null) {
            delimiter = defaultDelimiter;
        }
    }

    @Override
    public String format(Map<String, Object> map, Template template) {
        StringBuilder stringBuilder = new StringBuilder();
        if (header == null) {
            header = map.keySet();
            if (printHeader) {
                boolean first = true;
                for (String column : header) {
                    if (!first) {
                        stringBuilder.append(delimiter);
                    }
                    stringBuilder.append(column);
                    first = false;
                }
                stringBuilder.append(DelimiterConst.NEW_LINE);
            }
        }
        boolean first = true;
        for (String column : header) {
            if (!first) {
                stringBuilder.append(delimiter);
            }
            stringBuilder.append(map.get(column));
            first = false;
        }
        stringBuilder.append(DelimiterConst.NEW_LINE);
        return stringBuilder.toString();
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
        return false;
    }
}
