package com.baixing.impresso.batch;

import com.baixing.impresso.batch.time.Seconds;
import com.baixing.impresso.batch.window.SlidingTimeWindow;
import com.baixing.impresso.batch.window.TimeSegment;
import com.baixing.impresso.batch.window.WindowProcessor;

/**
 * Created by onesuper on 07/02/2017.
 */
public class SlidingWindowExample {

    static class Printer implements WindowProcessor {
        @Override
        public void process(TimeSegment ts) {
            System.out.println(ts);
        }
    }

    public static void main(String[] args) throws Exception {

        SlidingTimeWindow slidingTimeWindow = SlidingTimeWindow.now()
                .every(new Seconds(10))
                .window(5, 2)
                .doAdmitted(new Printer())
                .doRetired(new Printer())
                .build();

        slidingTimeWindow.start();
    }
}
