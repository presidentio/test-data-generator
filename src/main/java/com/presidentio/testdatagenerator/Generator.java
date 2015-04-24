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
package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.SinkFactory;
import com.presidentio.testdatagenerator.provider.DefaultValueProviderFactory;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class Generator {

    private boolean async = false;
    private Integer threadCount = Runtime.getRuntime().availableProcessors();

    private ValueProviderFactory valueProviderFactory = new DefaultValueProviderFactory();
    private SinkFactory sinkFactory = new SinkFactory();

    public void generate(Schema schema) {
        validateSchema(schema);
        InitTask initTask = new InitTask(schema);
        initTask.setSinkFactory(sinkFactory);
        initTask.setValueProviderFactory(valueProviderFactory);
        initTask.setAsync(async);
        if (async) {
            ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
            forkJoinPool.invoke(initTask);
        } else {
            initTask.compute();
        }
    }

    private void validateSchema(Schema schema) {
        assert schema.getOutput() != null;
        Set<String> templateIds = new HashSet<>(schema.getTemplates().size());
        for (Template template : schema.getTemplates()) {
            assert !templateIds.contains(template.getId());
            templateIds.add(template.getId());
            assert !(template.getExtend() == null ^ template.getExtendTemplate() == null);
        }
    }

    public ValueProviderFactory getValueProviderFactory() {
        return valueProviderFactory;
    }

    public void setValueProviderFactory(ValueProviderFactory valueProviderFactory) {
        this.valueProviderFactory = valueProviderFactory;
    }

    public void setSinkFactory(SinkFactory sinkFactory) {
        this.sinkFactory = sinkFactory;
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public Integer getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(Integer threadCount) {
        this.threadCount = threadCount;
    }
}
