package com.baixing.impresso.batch.time;

/**
 * Created by onesuper on 06/02/2017.
 */
public class Seconds extends Duration {

    public Seconds(long seconds) {
        super(seconds * 1_000);
    }
}
