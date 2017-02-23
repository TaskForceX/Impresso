package com.baixing.impresso.batch.state;

/**
 * Created by onesuper on 22/02/2017.
 */
public interface ValueUpdater<T> {
    T update(T stored);
}