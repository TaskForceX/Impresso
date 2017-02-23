package com.baixing.impresso.batch.state;

import java.io.Serializable;

/**
 * Created by onesuper on 22/02/2017.
 */
public interface CombinerAggregator<T> extends Serializable {
    T combine(T val1, T val2);
}
