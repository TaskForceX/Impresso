package com.baixing.impresso.batch.time;

import java.util.Iterator;

/**
 * Created by onesuper on 06/02/2017.
 */
public class TimeIntervalGenerator implements Iterator<TimeInterval> {

    private TimeInterval timeInterval;

    public TimeIntervalGenerator(TimeInterval interval) {
        timeInterval = interval.previousTimeInterval();
    }

    @Override
    public TimeInterval next() {
        timeInterval = timeInterval.nextTimeInterval();
        return timeInterval;
    }

    @Override
    public void remove() {

    }

    @Override
    public boolean hasNext() {
        return true;
    }
}
