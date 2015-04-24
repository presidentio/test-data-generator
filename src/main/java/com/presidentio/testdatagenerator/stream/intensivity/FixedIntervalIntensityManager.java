package com.presidentio.testdatagenerator.stream.intensivity;

/**
 * Created by presidentio on 24.04.15.
 */
public class FixedIntervalIntensityManager implements IntensityManager {

    private long waitTime;

    @Override
    public void waitNext() {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
