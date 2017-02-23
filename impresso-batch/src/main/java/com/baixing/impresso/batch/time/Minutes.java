package com.baixing.impresso.batch.time;

/**
 * Created by onesuper on 06/02/2017.
 */
public class Minutes extends Duration {

    public Minutes(long minutes) {
        super(minutes * 60_000);
    }
}
