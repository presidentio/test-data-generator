package com.presidentio.testdatagenerator;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;

import java.sql.SQLException;

public class EsGeneratorTest extends AbstractEsTest {

    private String indexName = "test_data_generator";

    @Override
    protected String getSchemaResource() {
        return "test-es-schema.json";
    }

    @Override
    protected void testEsContent(Client client) throws SQLException {
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
