package com.presidentio.testdatagenerator.stream.stop;

import com.presidentio.testdatagenerator.context.Context;

/**
 * Created by presidentio on 24.04.15.
 */
public class LimitedTickStopManager implements StopManager {

    private long limit = Long.MAX_VALUE;

    private long tickCount;

    @Override
    public boolean isStop(Context context) {
        return Thread.currentThread().isInterrupted() || ++tickCount > limit;
    }
}
