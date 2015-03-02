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
package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.model.Output;
import com.presidentio.testdatagenerator.model.Schema;
import com.presidentio.testdatagenerator.parser.SchemaBuilder;
import org.junit.Test;

import java.util.List;

public abstract class AbstractGeneratorTest {

    protected abstract List<String> getSchemaResource();

    protected Generator buildGenerator() {
        return new Generator();
    }

    @Test
    public void testGenerate() throws Exception {
        SchemaBuilder schemaBuilder = new SchemaBuilder();
        for (String resource : getSchemaResource()) {
            schemaBuilder.fromResource(resource);
        }
        Schema schema = schemaBuilder.build();
        Generator generator = buildGenerator();
        generator.generate(schema);
        testResult(schema.getOutput());
    }

    protected abstract void testResult(Output output);

}
