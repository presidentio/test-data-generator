package com.presidentio.testdatagenerator.parser;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringDeserializer extends JsonDeserializer<String> {

    private static final Pattern REGEX = Pattern.compile("\\$\\{(\\w+)\\}");

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        String str = jsonParser.getText();
        return formatString(str);
    }

    public String formatString(String s) {
        Matcher matcher = REGEX.matcher(s);
        StringBuilder result = new StringBuilder();
        int curIndex = 0;
        while (matcher.find()) {
            String group = matcher.group(1);
            result.append(s.substring(curIndex, matcher.start()));
            result.append(evaluate(group));
            curIndex = matcher.end() + 1;
        }
        result.append(s.substring(curIndex, s.length()));
        return result.toString();
    }

    private String evaluate(String s) {
        switch (s) {
            case "tmp":
                return System.getProperty("java.io.tmpdir");
            default:
                throw new IllegalArgumentException("Unknown expression: " + s);
        }
    }
}
