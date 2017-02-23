package com.baixing.impresso.batch.time;

/**
 * Created by onesuper on 06/02/2017.
 */
public class Hours extends Duration {

    public Hours(long hours) {
        super(hours * 60 * 60_000);
    }
}
