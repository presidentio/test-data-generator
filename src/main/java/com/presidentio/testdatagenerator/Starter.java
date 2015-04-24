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

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.presidentio.testdatagenerator.model.*;
import com.presidentio.testdatagenerator.parser.JsonSchemaSerializer;
import com.presidentio.testdatagenerator.parser.SchemaSerializer;

import java.io.FileInputStream;
import java.io.IOException;

public class Starter {

    @Parameter(names = "-a", description = "async, default true")
    private Boolean async = true;

    @Parameter(names = "-th", description = "thread count, work only when async true, default is processors count")
    private Integer threadCount;

    @Parameter(names = "-s", required = true, description = "Generation schema json file")
    private String schemaFile;

    public static void main(String[] args) throws IOException {
        Starter starter = new Starter();
        JCommander jCommander = new JCommander(starter);
        try {
            jCommander.parse(args);
            starter.start();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            jCommander.usage();
        }
    }

    public void start() throws IOException {
        SchemaSerializer schemaSerializer = new JsonSchemaSerializer();
        Schema schema = schemaSerializer.deserialize(new FileInputStream(schemaFile));
        OneTimeGenerator generator = new OneTimeGenerator();
        if (threadCount != null) {
            generator.setThreadCount(threadCount);
        }
        generator.setAsync(async);
        generator.generate(schema);
    }

}
