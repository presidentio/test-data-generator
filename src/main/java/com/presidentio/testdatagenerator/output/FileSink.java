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

import com.presidentio.testdatagenerator.cons.PathProviderConst;
import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.formatter.*;
import com.presidentio.testdatagenerator.output.path.ConstPathProvider;
import com.presidentio.testdatagenerator.output.path.PathProvider;
import com.presidentio.testdatagenerator.output.path.TimeBasedPathProvider;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileSink extends AbstractBufferedSink {

    private FormatterFactory formatterFactory = new DefaultFormatterFactory();

    private Map<String, BufferedOutputStream> outputStreams = new HashMap<>();

    private PathProvider pathProvider;

    @Override
    public void init(Map<String, String> props) {
        pathProvider = getPathProvider(props);
        init(formatterFactory.buildFormatter(props));
    }

    @Override
    public void write(Object partition, String formatted) {
        String filePath = (String) partition;
        try {
            getStream(filePath).write(formatted.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save: " + formatted, e);
        }
    }

    @Override
    public void close() {
        super.close();
        try {
            for (BufferedOutputStream bufferedOutputStream : outputStreams.values()) {
                bufferedOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Object getPartition(Template template, Map<String, Object> map) {
        return pathProvider.getFilePath(template, map);
    }

    private BufferedOutputStream getStream(String filePath) throws FileNotFoundException {
        BufferedOutputStream stream = outputStreams.get(filePath);
        if (stream == null) {
            File outFile = new File(filePath);
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            stream = new BufferedOutputStream(new FileOutputStream(outFile));
            outputStreams.put(filePath, stream);
        }
        return stream;
    }

    private PathProvider getPathProvider(Map<String, String> props) {
        String pathProviderType = props.get(PropConst.PATH_PROVIDER);
        PathProvider pathProvider;
        switch (pathProviderType) {
            case PathProviderConst.CONST:
                pathProvider = new ConstPathProvider();
                break;
            case PathProviderConst.TIME:
                pathProvider = new TimeBasedPathProvider();
                break;
            default:
                throw new IllegalArgumentException("unknown path provider: " + pathProviderType);
        }
        pathProvider.init(props);
        return pathProvider;
    }
}
