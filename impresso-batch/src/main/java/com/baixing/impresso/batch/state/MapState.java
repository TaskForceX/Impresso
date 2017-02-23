package com.baixing.impresso.batch.state;

import java.util.List;

/**
 * Created by onesuper on 22/02/2017.
 */
public interface MapState<K, V> {
    /**
     * read-modify-write
     */
    List<V> multiUpdate(List<K> keys, List<ValueUpdater<V>> updaters);

    void multiPut(List<K> keys, List<V> values);

    List<V> multiGet(List<K> keys);
}

