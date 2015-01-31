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

import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchClient;
import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchNode;
import com.github.tlrx.elasticsearch.test.annotations.ElasticsearchSetting;
import com.github.tlrx.elasticsearch.test.support.junit.runners.ElasticsearchRunner;
import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Output;
import org.apache.commons.io.IOUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RunWith(ElasticsearchRunner.class)
public abstract class AbstractEsTest extends AbstractGeneratorTest {

    @ElasticsearchNode(name = "node", clusterName = "fourth-cluster-name", local = true, data = true, settings = {
            @ElasticsearchSetting(name = "http.enabled", value = "false"),
            @ElasticsearchSetting(name = "node.zone", value = "zone_one")})
    private Node node;

    @ElasticsearchClient(nodeName = "node")
    private Client client;

    @Before
    public void setUp() throws Exception {
        if (client.admin().indices().prepareExists(getIndexName()).execute().actionGet().isExists()) {
            DeleteIndexResponse deleteIndexResponse = client.admin().indices().prepareDelete(getIndexName())
                    .execute().actionGet();
            Assert.assertTrue(deleteIndexResponse.isAcknowledged());
        }
        CreateIndexResponse createIndexResponse = client.admin().indices().prepareCreate(getIndexName())
                .execute().actionGet();
        Assert.assertTrue(createIndexResponse.isAcknowledged());
        for (Map.Entry<String, String> esMapping : getEsMappings().entrySet()) {
            String esMappingSource = IOUtils.toString(
                    AbstractEsTest.class.getClassLoader().getResourceAsStream(esMapping.getValue()));
            PutMappingResponse putMappingResponse = client.admin().indices().preparePutMapping(getIndexName())
                    .setType(esMapping.getKey()).setSource(esMappingSource).execute().actionGet();
            Assert.assertTrue(putMappingResponse.isAcknowledged());
        }
    }

    @Override
    public void testGenerate() throws Exception {
        super.testGenerate();
    }

    @Override
    protected void testResult(Output output) {
        try {
            executeFile(output.getProps().get(PropConst.FILE));
            testEsContent(client);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeFile(String file) throws IOException {
        String fileContent = IOUtils.toString(new FileReader(file));
        byte[] bytes = fileContent.getBytes();
        try {
            BulkResponse bulkItemResponse = client.prepareBulk().add(bytes, 0, bytes.length, false)
                    .setTimeout(TimeValue.timeValueSeconds(1)).execute().actionGet();
            Assert.assertFalse(bulkItemResponse.hasFailures());
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    protected void testEsContent(Client client) throws SQLException {

    }

    protected Map<String, String> getEsMappings() {
        Map<String, String> mappings = new HashMap<>(3);
        mappings.put("user", "test-es-user-mapping.json");
        mappings.put("training", "test-es-training-mapping.json");
        mappings.put("exercise", "test-es-exercise-mapping.json");
        return mappings;
    }

    protected abstract String getIndexName();

}
