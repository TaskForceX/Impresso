package com.baixing.impresso.batch.util;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by onesuper on 23/02/2017.
 */
public class RedisClient {
    private JedisPool pool;

    public RedisClient(String host, int port) {
        this.pool = new JedisPool(new JedisPoolConfig(), host, port);
    }

}
