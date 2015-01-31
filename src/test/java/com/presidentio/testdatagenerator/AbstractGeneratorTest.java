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
import com.presidentio.testdatagenerator.parser.JsonSchemaSerializer;
import org.junit.Test;

public abstract class AbstractGeneratorTest {

    protected abstract String getSchemaResource();

    @Test
    public void testGenerate() throws Exception {
        JsonSchemaSerializer jsonSchemaSerializer = new JsonSchemaSerializer();
        Schema schema = jsonSchemaSerializer.deserialize(AbstractGeneratorTest.class.getClassLoader()
                .getResourceAsStream(getSchemaResource()));
        Generator generator = new Generator();
        generator.generate(schema);
        testResult(schema.getOutput());
    }

    protected abstract void testResult(Output output);

}
