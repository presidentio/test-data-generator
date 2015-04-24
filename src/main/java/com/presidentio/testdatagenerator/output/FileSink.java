/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.DelimiterConst;
import com.presidentio.testdatagenerator.cons.FileFormatConst;
import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.output.formatter.EsFormatter;
import com.presidentio.testdatagenerator.output.formatter.Formatter;
import com.presidentio.testdatagenerator.output.formatter.SqlFormatter;
import com.presidentio.testdatagenerator.output.formatter.SvFormatter;

import java.io.*;
import java.util.Map;

public class FileSink extends AbstractBufferedSink {

    private BufferedOutputStream outputStream;

    @Override
    public void init(Map<String, String> props) {
        String file = props.get(PropConst.FILE);
        if (file == null) {
            throw new IllegalArgumentException(PropConst.FILE + " does not specified or null");
        }
        try {
            File outFile = new File(file);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Failed to create file sink", e);
        }

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
        init(formatter);
    }

    @Override
    public void write(String query) {
        try {
            outputStream.write(query.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save: " + query, e);
        }
    }

    @Override
    public void close() {
        super.close();
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Formatter getFormatter(String format) {
        switch (format) {
            case FileFormatConst.CSV:
                return new SvFormatter(DelimiterConst.COMMA);
            case FileFormatConst.TSV:
                return new SvFormatter(DelimiterConst.TAB);
            case FileFormatConst.JSON:
                throw new UnsupportedOperationException("json haven't implemented yet");
            case FileFormatConst.SQL:
                return new SqlFormatter();
            case FileFormatConst.ES:
                return new EsFormatter();
            default:
                throw new IllegalArgumentException("unknown format: " + format);
        }
    }
}
