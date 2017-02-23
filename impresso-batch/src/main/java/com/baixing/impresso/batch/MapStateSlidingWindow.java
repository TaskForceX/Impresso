package com.baixing.impresso.batch;

import com.baixing.impresso.batch.state.CombinerAggregator;
import com.baixing.impresso.batch.state.CombinerValueUpdater;
import com.baixing.impresso.batch.state.HashMapState;
import com.baixing.impresso.batch.state.ValueUpdater;
import com.baixing.impresso.batch.time.Seconds;
import com.baixing.impresso.batch.window.SlidingTimeWindow;
import com.baixing.impresso.batch.window.TimeSegment;
import com.baixing.impresso.batch.window.WindowProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by onesuper on 07/02/2017.
 */
public class MapStateSlidingWindow {

    private static final Random RAND = new Random(100);

    private static String randString(int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("abcdefghijklmnopqerstuvwxyz0123456789".charAt(RAND.nextInt(36)));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {

        final HashMapState<String, Integer> hmState = new HashMapState<>();

        SlidingTimeWindow slidingTimeWindow = SlidingTimeWindow.now()
                .every(new Seconds(5))
                .window(5, 2)
                .doAdmitted(new WindowProcessor() {
                    @Override
                    public void process(TimeSegment ts) {

                        final CombinerAggregator<Integer> combiner = new CombinerAggregator<Integer>() {
                            @Override
                            public Integer combine(Integer val1, Integer val2) {
                                return val1 + val2;
                            }
                        };

                        List<String> keys = new ArrayList<>();
                        List<ValueUpdater<Integer>> updaters = new ArrayList<>();

                        for (int i = 0; i < 100_000; i++) {
                            keys.add(randString(5));
                            updaters.add(new CombinerValueUpdater<>(combiner, 1));
                        }

                        hmState.multiUpdate(keys, updaters);
                    }
                })
                .build();

        slidingTimeWindow.start();
    }


}
