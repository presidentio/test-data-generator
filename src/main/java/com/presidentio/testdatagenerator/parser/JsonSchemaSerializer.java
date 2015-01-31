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
package com.presidentio.testdatagenerator.parser;

import com.presidentio.testdatagenerator.model.Schema;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.StdDeserializerProvider;
import org.codehaus.jackson.map.module.SimpleDeserializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class JsonSchemaSerializer implements SchemaSerializer {

    private ObjectMapper objectMapper;

    public JsonSchemaSerializer() {
        objectMapper = new ObjectMapper();
        SimpleDeserializers simpleDeserializers = new SimpleDeserializers();
        simpleDeserializers.addDeserializer(String.class, new StringDeserializer());
        objectMapper.setDeserializerProvider(
                new StdDeserializerProvider().withAdditionalDeserializers(simpleDeserializers));
    }

    @Override
    public Schema deserialize(InputStream inputStream) throws IOException {
        return objectMapper.readValue(inputStream, Schema.class);
    }

    @Override
    public Schema deserialize(String string) throws IOException {
        return objectMapper.readValue(string, Schema.class);
    }

    @Override
    public void serialize(Schema schema, OutputStream outputStream) throws IOException {
        objectMapper.writeValue(outputStream, schema);
    }

    @Override
    public String serialize(Schema schema) throws IOException {
        return objectMapper.writeValueAsString(schema);
    }

}
