package com.baixing.impresso.batch.window;

import com.baixing.impresso.batch.time.TimeInterval;

/**
 * Created by onesuper on 22/02/2017.
 */
public class BaseTimeSegment implements TimeSegment {

    int numTimeIntervals;
    TimeInterval first = null;
    TimeInterval last = null;

    Iterable<TimeInterval> timeIntervals;

    public long startsAt() {
        return first.startsAt();
    }

    public long endsAt() {
        return last.endsAt();
    }

    protected BaseTimeSegment(int numTimeIntervals, TimeInterval first, TimeInterval last) {
        this.numTimeIntervals = numTimeIntervals;
        this.first = first;
        this.last = last;
    }

    public static BaseTimeSegment fromTimeIntervals(Iterable<TimeInterval> timeIntervals) {
        int numTimeIntervals = 0;
        TimeInterval first = null;
        TimeInterval last = null;

        for (TimeInterval t : timeIntervals) {
            if (numTimeIntervals == 0) {
                first = t;
            }
            numTimeIntervals++;
            last = t;
        }
        return new BaseTimeSegment(numTimeIntervals, first, last);
    }

    public String toString() {
        return "TimeSegment: [" + first.startTimestamp() + ", " + last.endTimestamp() + "]";
    }

}
