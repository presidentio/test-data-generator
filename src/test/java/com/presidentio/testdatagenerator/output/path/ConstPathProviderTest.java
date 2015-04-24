package com.presidentio.testdatagenerator.output.path;

import com.presidentio.testdatagenerator.cons.PropConst;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by presidentio on 24.04.15.
 */
public class ConstPathProviderTest extends TestCase {

    @Test
    public void testGetFilePath() throws Exception {
        String expected = "a.sql";
        Map<String, String> props = new HashMap<>();
        props.put(PropConst.FILE, expected);
        ConstPathProvider constPathProvider = new ConstPathProvider();
        constPathProvider.init(props);
        String result = constPathProvider.getFilePath(null, null);
        Assert.assertEquals(expected, result);
    }
}