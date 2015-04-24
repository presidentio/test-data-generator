package com.presidentio.testdatagenerator.stream.intensivity;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by presidentio on 24.04.15.
 */
public class WeekHourlyIntensityManager implements IntensityManager {

    private static final long MILLIS_IN_HOUR = TimeUnit.HOURS.toMillis(1);
    private static final long HOURS_IN_WEEK = 24 * 7;

    private Long[] tickPerHour;

    private int curHourOfWeek;
    private long curHourStart;
    private long curHourTicks;
    private long curMillisPerTick;

    @Override
    public void waitNext() {
        updateCurHourInfo();
        long timeFromHourStart = System.currentTimeMillis() - curHourStart;
        long timeToWait = curMillisPerTick * curHourTicks - timeFromHourStart;
        if (timeToWait <= 0) {
            return;
        } else {
            try {
                Thread.sleep(timeToWait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCurHourInfo() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        long newCurHourStart = calendar.getTimeInMillis() - calendar.getTimeInMillis() % MILLIS_IN_HOUR;
        if (newCurHourStart != curHourStart) {
            curHourStart = newCurHourStart;
            curHourOfWeek = hourOfWeek(curHourStart);
            curHourTicks = 0;
            curMillisPerTick = MILLIS_IN_HOUR / tickPerHour[curHourOfWeek];
        }
        curHourTicks++;
    }

    private int hourOfWeek(long hourStartInMillis) {
        return (int) (hourStartInMillis % HOURS_IN_WEEK + 24 + (hourStartInMillis % 24));
    }

    public Long[] getTickPerHour() {
        return tickPerHour;
    }

    public void setTickPerHour(Long[] tickPerHour) {
        this.tickPerHour = tickPerHour;
    }
}
