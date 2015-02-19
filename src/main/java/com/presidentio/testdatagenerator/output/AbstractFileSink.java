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
package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;

import java.io.*;
import java.util.Map;

public abstract class AbstractFileSink extends AbstractBufferedSink {

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
}
