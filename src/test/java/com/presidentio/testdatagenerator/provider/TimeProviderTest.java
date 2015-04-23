package com.presidentio.testdatagenerator.provider;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Vitaliy on 23.04.2015.
 */
public class TimeProviderTest {

    @Test
    public void testNextValue() throws Exception {
        TimeProvider timeProvider = new TimeProvider();
        for (int i = 0; i < 10; i++) {
            long timeBefore = System.currentTimeMillis();
            long value = (Long) timeProvider.nextValue(null, null);
            long timeAfter = System.currentTimeMillis();
            Assert.assertTrue(timeBefore <= value && value <= timeAfter);
        }
    }
}