/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator.output.formatter;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.cons.TypeConst;
import com.presidentio.testdatagenerator.model.Field;
import com.presidentio.testdatagenerator.model.Template;

import java.util.*;

public class SqlFormatter implements Formatter {

    public static final String INSERT_TEMPlATE = "INSERT INTO %s %s VALUES %s;\n";
    public static final String MULTI_INSERT_TEMPlATE_START = "INSERT INTO %s %s VALUES \n";
    public static final String MULTI_INSERT_TEMPlATE_END = ";\n";
    public static final String COMMA = ", ";

    private static final List<String> JDBC_DRIVERS_WITH_MULTI_INSERT = Arrays.asList("org.postgresql.Driver",
            "com.mysql.jdbc.Driver");

    private boolean supportMultiInsert;

    @Override
    public void init(Map<String, String> props) {
        String jdbcDriver = props.get(PropConst.JDBC_DRIVER);
        if (jdbcDriver != null) {
            supportMultiInsert = JDBC_DRIVERS_WITH_MULTI_INSERT.contains(jdbcDriver);
        }
    }

    @Override
    public String format(Map<String, Object> map, Template template) {
        String columns = getColumns(template);
        String values = getValues(map, template);
        return String.format(INSERT_TEMPlATE, template.getName(), columns, values);
    }

    private String getColumns(Template template) {
        StringBuilder columns = new StringBuilder("(");
        boolean first = true;
        for (Field field : template.getFields()) {
            if (field.isIgnored())
            {
                continue;
            }
            if (!first) {
                columns.append(COMMA);
            }
            first = false;
            columns.append(field.getName());
        }
        columns.append(")");
        return columns.toString();
    }

    private String getValues(Map<String, Object> map, Template template) {
        StringBuilder values = new StringBuilder("(");
        boolean first = true;
        for (Field field : template.getFields()) {
            if (field.isIgnored())
            {
                continue;
            }
            if (!first) {
                values.append(COMMA);
            }
            first = false;
            values.append(formatValue(field.getType(), map.get(field.getName())));
        }
        values.append(")");
        return values.toString();
    }

    @Override
    public String format(List<Map<String, Object>> maps, Template template) {
        if (supportMultiInsert) {
            return formatMultiInsert(maps, template);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map<String, Object> map : maps) {
                stringBuilder.append(format(map, template));
            }
            return stringBuilder.toString();
        }
    }

    private String formatMultiInsert(List<Map<String, Object>> maps, Template template) {
        String columns = getColumns(template);
        String start = String.format(MULTI_INSERT_TEMPlATE_START, template.getName(), columns);
        StringBuilder stringBuilder = new StringBuilder(start);
        boolean first = true;
        for (Map<String, Object> map : maps) {
            if (!first) {
                stringBuilder.append(COMMA);
            }
            first = false;
            stringBuilder.append(getValues(map, template));
        }
        stringBuilder.append(MULTI_INSERT_TEMPlATE_END);
        return stringBuilder.toString();
    }

    protected String formatValue(String type, Object value) {
        switch (type) {
            case TypeConst.STRING:
                return "'" + value + "'";
            case TypeConst.VERBATIM:
            case TypeConst.LONG:
            case TypeConst.INT:
            case TypeConst.BOOLEAN:
                return value.toString();
            default:
                throw new IllegalArgumentException("Field type not known: " + type);
        }
    }

    @Override
    public boolean supportMultiInsert() {
        return supportMultiInsert;
    }
}
