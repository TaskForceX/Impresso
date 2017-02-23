package com.baixing.impresso.batch.time;

import java.text.SimpleDateFormat;

/**
 * Created by onesuper on 06/02/2017.
 */
public class TimeInterval {

    private final long startMillis;
    private final Duration duration;

    public TimeInterval(long startMillis, Duration duration) {

        if (duration instanceof Seconds) {
            this.startMillis = startMillis / 1_000 * 1_000;
        } else if (duration instanceof Minutes) {
            this.startMillis = startMillis / 60_000 * 60_000;
        } else if (duration instanceof Hours) {
            this.startMillis = startMillis / (60 * 60_000) * 60 * 60_000;
        } else {
            this.startMillis = startMillis;
        }

        this.duration = duration;
    }

    public Duration duration() {
        return duration;
    }

    public long startsAt() {
        return startMillis;
    }

    public long endsAt() {
        return startMillis + duration.millis;
    }

    public boolean isWithin(long millis) {
        return (millis > startsAt()) && (millis >= endsAt());
    }

    public TimeInterval nextTimeInterval() {
        return new TimeInterval(endsAt(), duration);
    }

    public TimeInterval previousTimeInterval() {
        return new TimeInterval(startMillis - duration.millis, duration);
    }

    public String startTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(startsAt());
    }

    public String endTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(endsAt());
    }

    @Override
    public String toString() {
        return "TimeInterval[" + startTimestamp() + ", " + endTimestamp() + "]";
    }

    @Override
    public boolean equals(Object t) {
        if (!(t instanceof TimeInterval)) {
            return false;
        }

        return this.startMillis == ((TimeInterval) t).startMillis &&
                this.duration.millis == ((TimeInterval) t).duration.millis;
    }
}
