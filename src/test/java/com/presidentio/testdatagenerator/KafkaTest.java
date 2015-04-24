package com.presidentio.testdatagenerator;

import com.presidentio.testdatagenerator.model.Output;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.List;

/**
 * Created by presidentio on 24.04.15.
 */
@Ignore
public class KafkaTest extends AbstractGeneratorTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Override
    protected List<String> getSchemaResource() {
        return Arrays.asList("test-kafka-schema.json");
    }

    @Override
    protected void testResult(Output output) {
    }
}
