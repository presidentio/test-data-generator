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
import com.presidentio.testdatagenerator.model.Output;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.Sink;
import com.presidentio.testdatagenerator.output.SinkFactory;
import com.presidentio.testdatagenerator.provider.DefaultValueProviderFactory;
import com.presidentio.testdatagenerator.provider.ValueProviderFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class InitTask extends RecursiveAction {

    private Context context;
    private List<String> roots;
    private ValueProviderFactory valueProviderFactory = new DefaultValueProviderFactory();
    private SinkFactory sinkFactory = new SinkFactory();

    private boolean async = true;

    public InitTask(Context context, List<String> roots) {
        this.context = context;
        this.roots = roots;
    }

    @Override
    protected void compute() {
        List<GenerateTask> tasks = new ArrayList<>(roots.size());
        for (String rootTemplateId : roots) {
            final Template rootTemplate = context.getTemplates().get(rootTemplateId);
            if (rootTemplate == null) {
                throw new IllegalArgumentException("Template with id does not defined: " + rootTemplateId);
            }
            GenerateTask task = new GenerateTask(context, rootTemplate, valueProviderFactory);
            task.setAsync(async);
            tasks.add(task);
        }
        if (async) {
            invokeAll(tasks);
        } else {
            for (GenerateTask forkJoinTask : tasks) {
                forkJoinTask.compute();
            }
        }
        context.getSink().close();

    }

    private Sink buildSink(Output output) {
        return sinkFactory.getSink(output);
    }

    public boolean isAsync() {
        return async;
    }

    public void setAsync(boolean async) {
        this.async = async;
    }

    public ValueProviderFactory getValueProviderFactory() {
        return valueProviderFactory;
    }

    public void setValueProviderFactory(ValueProviderFactory valueProviderFactory) {
        this.valueProviderFactory = valueProviderFactory;
    }

    public SinkFactory getSinkFactory() {
        return sinkFactory;
    }

    public void setSinkFactory(SinkFactory sinkFactory) {
        this.sinkFactory = sinkFactory;
    }
}
