package com.presidentio.testdatagenerator.parser;

import junit.framework.TestCase;
import org.junit.Assert;

/**
 * Created by presidentio on 23.04.15.
 */
public class StringDeserializerTest extends TestCase {

    public void testFormatString() throws Exception {
        String in = "ashd${tmp}/asdokjk${tmp}aasd";
        String out = "ashd" + System.getProperty("java.io.tmpdir") + "/asdokjk" + System.getProperty("java.io.tmpdir")
                + "aasd";
        StringDeserializer stringDeserializer = new StringDeserializer();
        Assert.assertEquals(out, stringDeserializer.formatString(in));
    }
}