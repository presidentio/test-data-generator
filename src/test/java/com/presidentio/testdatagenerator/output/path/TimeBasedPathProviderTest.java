package com.presidentio.testdatagenerator.output.path;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * Created by presidentio on 24.04.15.
 */
public class TimeBasedPathProviderTest extends TestCase {

    @Test
    public void testGetFilePath() throws Exception {
        TimeBasedPathProvider timeBasedPathProvider = new TimeBasedPathProvider();
        timeBasedPathProvider.init(Collections.<String, String>emptyMap());
        Assert.assertNotNull(timeBasedPathProvider.getFilePath(null, null));
    }
}