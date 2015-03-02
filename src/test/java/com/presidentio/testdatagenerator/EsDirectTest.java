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
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

public class EsDirectTest extends AbstractEsTest {

    private String indexName = "test_data_generator";

    @Override
    protected List<String> getSchemaResource() {
        return Arrays.asList("test-es-direct-schema.json");
    }

    @Override
    protected void testResult(Output output) {
        testEsContent(client);
    }

    protected void testEsContent(Client client) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CountResponse countResponse = client.prepareCount(indexName).setQuery(QueryBuilders.termQuery("_type", "user"))
                .execute().actionGet();
        Assert.assertEquals(11, countResponse.getCount());

        countResponse = client.prepareCount(indexName).setQuery(QueryBuilders.termQuery("_type", "training"))
                .execute().actionGet();
        Assert.assertEquals(55, countResponse.getCount());

        countResponse = client.prepareCount(indexName).setQuery(QueryBuilders.termQuery("_type", "exercise"))
                .execute().actionGet();
        Assert.assertEquals(275, countResponse.getCount());
    }

    @Override
    protected String getIndexName() {
        return indexName;
    }
}
