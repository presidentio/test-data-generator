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

import com.presidentio.testdatagenerator.model.*;
import com.presidentio.testdatagenerator.parser.JsonSchemaSerializer;
import com.presidentio.testdatagenerator.parser.SchemaSerializer;

import java.io.IOException;

public class Starter {

    public static void main(String[] args) throws IOException {
        SchemaSerializer schemaSerializer = new JsonSchemaSerializer();
        Schema schema = schemaSerializer.deserialize(Starter.class.getClassLoader().getResourceAsStream("schema.json"));
        Generator generator = new Generator();
        generator.generate(schema);
    }

}
