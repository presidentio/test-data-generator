package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.formatter.Formatter;

import java.util.*;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
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
