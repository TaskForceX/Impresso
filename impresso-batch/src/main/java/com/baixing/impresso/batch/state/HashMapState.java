package com.baixing.impresso.batch.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by onesuper on 22/02/2017.
 */
public class HashMapState<K, V> implements MapState<K, V> {
    private final Logger logger = LoggerFactory.getLogger(HashMapState.class);

    private HashMap<K, V> hashMap = new HashMap<>();

    @Override
    public List<V> multiUpdate(List<K> keys, List<ValueUpdater<V>> updaters) {
        List<V> olds = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            V stored = hashMap.get(keys.get(i));
            V newer = updaters.get(i).update(stored);
            hashMap.put(keys.get(i), newer);
            olds.add(stored);
            if (logger.isDebugEnabled()) {
                logger.debug("key: {}, value: {} -> {}", keys.get(i), stored, newer);
            }
        }
        return olds;
    }

    @Override
    public void multiPut(List<K> keys, List<V> values) {
        for (int i = 0; i < keys.size(); i++) {
            hashMap.put(keys.get(i), values.get(i));
            if (logger.isDebugEnabled()) {
                logger.debug("key: {}, put value: {}", keys.get(i), values.get(i));
            }
        }
    }

    @Override
    public List<V> multiGet(List<K> keys) {
        List<V> got = new ArrayList<>();
        for (int i = 0; i < keys.size(); i++) {
            got.add(hashMap.get(keys.get(i)));
        }
        return got;
    }
}
