package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.model.Output;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Vitalii_Gergel on 3/2/2015.
 */
public class ExtendTemplateTest extends AbstractEsTest {

    private String indexName = "test_data_generator";

    @Override
    protected List<String> getSchemaResource() {
        return Arrays.asList("extend-template-test-schema.json");
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
