package com.baixing.impresso.batch.state;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by onesuper on 22/02/2017.
 */
public class CombinerValueUpdaterTest {
    @Test
    public void testUpdate() throws Exception {
        CombinerValueUpdater<Long> updater = new CombinerValueUpdater<>(new CombinerAggregator<Long>() {
            @Override
            public Long combine(Long val1, Long val2) {
                return val1 + val2;
            }
        }, 1L);

        Assert.assertEquals(updater.update(9999L).longValue(), 10000L);
    }

}