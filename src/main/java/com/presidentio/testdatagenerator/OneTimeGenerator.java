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

import com.presidentio.testdatagenerator.context.Context;
import com.presidentio.testdatagenerator.model.Schema;

import java.util.concurrent.ForkJoinPool;

public class OneTimeGenerator extends AbstractGenerator {

    private boolean async = false;
    private Integer threadCount = Runtime.getRuntime().availableProcessors();

    @Override
    public void generate(Context context, Schema schema) {
        InitTask initTask = new InitTask(context, schema.getRoot());
        initTask.setSinkFactory(getSinkFactory());
        initTask.setValueProviderFactory(getValueProviderFactory());
        initTask.setAsync(async);
        if (async) {
            ForkJoinPool forkJoinPool = new ForkJoinPool(threadCount);
            forkJoinPool.invoke(initTask);
        } else {
            initTask.compute();
        }
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
