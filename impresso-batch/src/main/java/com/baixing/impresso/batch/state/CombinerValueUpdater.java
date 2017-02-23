package com.baixing.impresso.batch.state;

/**
 * Created by onesuper on 22/02/2017.
 */
public class CombinerValueUpdater<T> implements ValueUpdater<T> {
    T arg;
    CombinerAggregator<T> agg;

    public CombinerValueUpdater(CombinerAggregator agg, T arg) {
        this.agg = agg;
        this.arg = arg;
    }

    @Override
    public T update(T stored) {
        if (stored == null) {
            return arg;
        } else {
            return agg.combine(stored, arg);
        }
    }
}
