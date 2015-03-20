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

import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.formatter.Formatter;

import java.util.*;

public abstract class AbstractBufferedSink implements Sink {

    private Map<Template, List<Map<String, Object>>> buffer = new HashMap<>();

    private Formatter formatter;

    private int bufferSize = 1000;
    private boolean bufferingEnabled = true;

    protected void init(Formatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void process(Template template, Map<String, Object> map) {
        if (bufferingEnabled && formatter.supportMultiInsert()) {
            addToBuffer(template, map);
        } else {
            String data = formatter.format(map, template);
            write(data);
        }
    }

    public abstract void write(String bytes);

    private synchronized void addToBuffer(Template template, Map<String, Object> map) {
        List<Map<String, Object>> items = buffer.get(template);
        if (items == null) {
            items = new ArrayList<>();
            buffer.put(template, items);
        }
        items.add(map);
        if (items.size() >= bufferSize) {
            flush(template, items);
            items.clear();
        }
    }

    private synchronized void flush(Template template, List<Map<String, Object>> items) {
        if (items != null && !items.isEmpty()) {
            String data = formatter.format(items, template);
            write(data);
        }
    }

    @Override
    public synchronized void close() {
        for (Template template : buffer.keySet()) {
            List<Map<String, Object>> items = buffer.get(template);
            flush(template, items);
            items.clear();
        }
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setBufferingEnabled(boolean bufferingEnabled) {
        this.bufferingEnabled = bufferingEnabled;
    }
}
