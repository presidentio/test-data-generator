package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.model.Template;

import java.io.*;
import java.util.Map;

/**
 * Created by Vitaliy on 23.01.2015.
 */
public class SqlFileSink implements Sink {

    public static final String INSERT_TEMPlATE = "INSERT INTO %s (%s) VALUES (%s);\n";
    public static final String COMMA = ", ";

    private String file;

    private BufferedOutputStream outputStream;

    public SqlFileSink(Map<String, String> props) {
        file = props.remove(PropConst.FILE);
        if (file == null) {
            throw new IllegalArgumentException(PropConst.FILE + " does not specified or null");
        }
        if (!props.isEmpty()) {
            throw new IllegalArgumentException("Redundant props for " + getClass().getName() + ": " + props);
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Failed to create SqlSink", e);
        }
    }

    @Override
    public void process(Template template, Map<String, Object> map) {
        String sql = formatSql(template, map);
        try {
            outputStream.write(sql.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save sql: " + sql, e);
        }
    }

    private String formatSql(Template template, Map<String, Object> map) {
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        for (Field field : template.getFields()) {
            if (columns.length() != 0) {
                columns.append(COMMA);
            }
            columns.append(field.getName());

            if (values.length() != 0) {
                values.append(COMMA);
            }
            values.append(formatValue(field.getType(), map.get(field.getName())));
        }
        return String.format(INSERT_TEMPlATE, template.getName(), columns, values);
    }

    private String formatValue(String type, Object value) {
        switch (type) {
            case TypeConst.STRING:
                return "'" + value + "'";
            case TypeConst.LONG:
                return value.toString();
            case TypeConst.INT:
                return value.toString();
            case TypeConst.BOOLEAN:
                return value.toString();
            default:
                throw new IllegalArgumentException("Field type not known: " + type);
        }
    }

    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
