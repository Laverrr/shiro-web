package com.laver.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by L on 2018/9/8.
 */
@Component
public class JedisUtil {

    @Autowired
    private JedisPool jedisPool;

    private Jedis getResource(){
        return  jedisPool.getResource();
    }

    public byte[] set(byte[] key,byte[] value){
        Jedis jedis = getResource();

        try {
            jedis.set(key,value);
            return value;
        } finally {
            jedis.close();
        }
    }

    public void expire(byte[] key, int i){
        Jedis jedis = getResource();

        try {
            jedis.expire(key,i);
        } finally {
            jedis.close();
        }
    }


    public byte[] get(byte[] key) {
        Jedis jedis = getResource();
        byte[] value = null;
        try {
            value = jedis.get(key);
        } finally {
            jedis.close();
        }
        return  value;
    }

    public void del(byte[] key){
        Jedis jedis = getResource();
        try {
            jedis.del(key);
        } finally {
            jedis.close();
        }
    }

    public Set<byte[]> keys(String SHIRO_SESSION_PREFIX) {
        Jedis jedis = getResource();
        try {
            return jedis.keys((SHIRO_SESSION_PREFIX + "*").getBytes());
        } finally {
            jedis.close();
        }
    }
}
