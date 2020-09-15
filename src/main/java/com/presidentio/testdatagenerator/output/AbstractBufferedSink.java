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

import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.formatter.Formatter;

import java.util.*;

public abstract class AbstractBufferedSink implements Sink {

    private static final String DEFAULT_PARTITION = "default";
    private static final int DEFAULT_BUFFER_SIZE = 1000000;

    private LinkedHashMap<Template, Map<Object, List<Map<String, Object>>>> buffer = new LinkedHashMap<>();

    private Formatter formatter;

    private int bufferSize = DEFAULT_BUFFER_SIZE;
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
            Object partition = getPartition(template, map);
            write(partition, data);
        }
    }

    public abstract void write(Object partition, String bytes);

    protected Object getPartition(Template template, Map<String, Object> map) {
        return DEFAULT_PARTITION;
    }

    private synchronized void addToBuffer(Template template, Map<String, Object> map) {
        Map<Object, List<Map<String, Object>>> partitions = buffer.get(template);
        if (partitions == null) {
            partitions = new HashMap<>();
            buffer.put(template, partitions);
        }
        Object partition = getPartition(template, map);
        List<Map<String, Object>> items = partitions.get(partition);
        if (items == null) {
            items = new ArrayList<>();
            partitions.put(partition, items);
        }
        items.add(map);
        if (items.size() >= bufferSize) {
            flush(template, partition, items);
            items.clear();
        }
    }

    private synchronized void flush(Template template, Object partition, List<Map<String, Object>> items) {
        if (items != null && !items.isEmpty()) {
            String data = formatter.format(items, template);
            write(partition, data);
        }
    }

    @Override
    public synchronized void close() {
        for (Template template : buffer.keySet()) {
            Map<Object, List<Map<String, Object>>> partitions = buffer.get(template);
            for (Object partition : partitions.keySet()) {
                List<Map<String, Object>> items = partitions.get(partition);
                flush(template, partition, items);
            }
            partitions.clear();
        }
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public void setBufferingEnabled(boolean bufferingEnabled) {
        this.bufferingEnabled = bufferingEnabled;
    }
}
