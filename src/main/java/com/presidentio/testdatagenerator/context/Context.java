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
package com.presidentio.testdatagenerator.context;

import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.Sink;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Context {

    private Parent parent;

    private Map<String, Template> templates;

    private Map<String, Object> variables;

    private Random random = new Random();

    private Sink sink;

    public Context(Map<String, Template> templates, Map<String, Object> variables, Sink sink) {
        this.templates = templates;
        this.variables = variables;
        this.sink = sink;
    }

    public Context(Context parentContext, Map<String, Object> entity) {
        parent = new Parent(entity, parentContext.getParent());
        templates = parentContext.getTemplates();
        variables = parentContext.getVariables();
        sink = parentContext.getSink();
    }

    public Parent getParent() {
        return parent;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public Sink getSink() {
        return sink;
    }

    public Map<String, Template> getTemplates() {
        return templates;
    }

    public Random getRandom() {
        return random;
    }

    public Object selectFrom(List<Object> objects)
    {
        int limit = objects.size();
        return objects.get(random.nextInt(limit));
    }
}
