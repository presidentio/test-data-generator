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

import com.presidentio.testdatagenerator.model.Output;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CsvFileTest extends AbstractGeneratorTest {

    @Override
    protected List<String> getSchemaResource() {
        return Arrays.asList("test-csv-file-schema.json");
    }

    @Override
    protected void testResult(Output output) {
        File file = new File(output.getProps().get("file"));
        try {
            List<String> csvFile = IOUtils.readLines(new FileInputStream(file));
            Assert.assertEquals(10 + 10 * 5 + 10 * 5 * 5 + 1 + 1 * 5 + 1 * 5 * 5, csvFile.size());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
