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

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public abstract class AbstractFileSink implements Sink {

    private String file;

    private BufferedOutputStream outputStream;

    public AbstractFileSink(Map<String, String> props) {
        file = props.get(PropConst.FILE);
        if (file == null) {
            throw new IllegalArgumentException(PropConst.FILE + " does not specified or null");
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Failed to create file sink", e);
        }
    }

    protected void write(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to save: " + new String(bytes), e);
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
