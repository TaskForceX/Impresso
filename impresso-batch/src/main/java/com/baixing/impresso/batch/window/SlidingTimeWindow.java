package com.baixing.impresso.batch.window;

import com.baixing.impresso.batch.time.*;
import com.baixing.impresso.batch.util.Stopwatch;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by onesuper on 06/02/2017.
 */
public class SlidingTimeWindow {

    private final Logger logger = LoggerFactory.getLogger(SlidingTimeWindow.class);

    private MemorySlidingWindow<TimeInterval> slidingWindow;
    private int slideLength;

    private WindowProcessor activeWindowProcessor = null;
    private WindowProcessor admittedWindowProcessor = null;
    private WindowProcessor retiredWindowProcessor = null;

    protected SlidingTimeWindow(long startMillis, Duration duration, int windowLength, int slideLength) {
        final TimeInterval interval = new TimeInterval(startMillis, duration);
        slidingWindow = new MemorySlidingWindow<>(new TimeIntervalGenerator(interval), windowLength);
        this.slideLength = slideLength;
    }

    public void setActiveWindowProcessor(WindowProcessor activeWindowProcessor) {
        this.activeWindowProcessor = activeWindowProcessor;
    }

    public void setAdmittedWindowProcessor(WindowProcessor admittedWindowProcessor) {
        this.admittedWindowProcessor = admittedWindowProcessor;
    }

    public void setRetiredWindowProcessor(WindowProcessor retiredWindowProcessor) {
        this.retiredWindowProcessor = retiredWindowProcessor;
    }

    public static Builder now() {
        return new Builder().since(System.currentTimeMillis());
    }

    public static Builder since(long startMillis) {
        return new Builder().since(startMillis);
    }

    public static class Builder {

        private WindowProcessor activeWindowProcessor;
        private WindowProcessor admittedWindowProcessor;
        private WindowProcessor retiredWindowProcessor;
        private int windowLength;
        private int slideLength;
        private Duration duration;
        private long startMillis;

        public Builder since(long startMillis) {
            this.startMillis = startMillis;
            return this;
        }

        public Builder every(Duration duration) {
            this.duration = duration;
            return this;
        }

        public Builder window(int windowLength, int slideLength) {
            this.windowLength = windowLength;
            this.slideLength = slideLength;
            return this;
        }

        public Builder doActive(WindowProcessor activeWindowProcessor) {
            this.activeWindowProcessor = activeWindowProcessor;
            return this;
        }

        public Builder doRetired(WindowProcessor retiredWindowProcessor) {
            this.retiredWindowProcessor = retiredWindowProcessor;
            return this;
        }

        public Builder doAdmitted(WindowProcessor admittedWindowProcessor) {
            this.admittedWindowProcessor = admittedWindowProcessor;
            return this;
        }

        public SlidingTimeWindow build() {
            Preconditions.checkNotNull(startMillis, "The startMillis cannot be null.");
            Preconditions.checkNotNull(duration, "The duration cannot be null.");
            Preconditions.checkNotNull(windowLength, "The windowLength cannot be null.");
            Preconditions.checkNotNull(slideLength, "The slideLength cannot be null.");

            SlidingTimeWindow window = new SlidingTimeWindow(startMillis, duration, windowLength, slideLength);
            window.setActiveWindowProcessor(activeWindowProcessor);
            window.setAdmittedWindowProcessor(admittedWindowProcessor);
            window.setRetiredWindowProcessor(retiredWindowProcessor);
            return window;
        }
    }

    public void start() throws InterruptedException {
        while (true) {
            slidingWindow.slideBy(slideLength);

            // wait until the last time interval passes away, then trigger!
            for (TimeInterval t : slidingWindow.getAdmitted()) {
                while (System.currentTimeMillis() < t.endsAt()) {
                    Thread.sleep(1000);
                }
            }

            Stopwatch stopwatch = new Stopwatch();

            if (admittedWindowProcessor != null) {
                stopwatch.start();
                TimeSegment ts = BaseTimeSegment.fromTimeIntervals(slidingWindow.getAdmitted());
                admittedWindowProcessor.process(ts);
                long took = stopwatch.elapsedMillis();
                logger.info("It took {} ms to doAdmitted {} ", took, ts);
            }

            if (activeWindowProcessor != null) {
                stopwatch.start();
                TimeSegment ts = BaseTimeSegment.fromTimeIntervals(slidingWindow.getAdmitted());
                admittedWindowProcessor.process(ts);
                long took = stopwatch.elapsedMillis();
                logger.info("It took {} ms to doActive {}", took, ts);
            }

            if (retiredWindowProcessor != null) {
                stopwatch.start();
                TimeSegment ts = BaseTimeSegment.fromTimeIntervals(slidingWindow.getAdmitted());
                admittedWindowProcessor.process(ts);

                long took = stopwatch.elapsedMillis();
                logger.info("It took {} ms to doRetired {} ", took, ts);
            }
        }
    }


}
