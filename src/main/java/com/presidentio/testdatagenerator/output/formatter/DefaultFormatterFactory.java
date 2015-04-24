package com.presidentio.testdatagenerator.output.formatter;

import com.presidentio.testdatagenerator.cons.DelimiterConst;
import com.presidentio.testdatagenerator.cons.FileFormatConst;
import com.presidentio.testdatagenerator.cons.PropConst;

import java.util.Map;

/**
 * Created by presidentio on 24.04.15.
 */
public class DefaultFormatterFactory implements FormatterFactory {

    @Override
    public Formatter buildFormatter(Map<String, String> props) {
        String format = props.get(PropConst.FORMAT);
        if (format == null) {
            format = FileFormatConst.CSV;
        }
        if (!FileFormatConst.ALL.contains(format)) {
            throw new IllegalArgumentException(PropConst.FORMAT + " is incorrect. Must be one of: "
                    + FileFormatConst.ALL);
        }
        Formatter formatter = getFormatter(format);
        formatter.init(props);
        return formatter;
    }

    private Formatter getFormatter(String format) {
        switch (format) {
            case FileFormatConst.CSV:
                return new SvFormatter(DelimiterConst.COMMA);
            case FileFormatConst.TSV:
                return new SvFormatter(DelimiterConst.TAB);
            case FileFormatConst.JSON:
                throw new UnsupportedOperationException("json hasn't implemented yet");
            case FileFormatConst.SQL:
                return new SqlFormatter();
            case FileFormatConst.ES:
                return new EsFormatter();
            default:
                throw new IllegalArgumentException("unknown format: " + format);
        }
    }

}
