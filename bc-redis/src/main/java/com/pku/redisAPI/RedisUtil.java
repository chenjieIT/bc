package com.pku.redisAPI;

import java.util.Set;

import redis.clients.jedis.Jedis;

public class RedisUtil {
	public Jedis jedis;
	private static RedisUtil redisUtil;
    private RedisUtil() {
        jedis = new Jedis("127.0.0.1",6379);
        //jedis.auth("123456");
        //查看服务是否运行
        System.out.println("Server is running: "+jedis.ping());
    }
    public static RedisUtil getRedisUtil() {
    	if(redisUtil == null){
    		redisUtil = new RedisUtil();
    	}
    	return redisUtil;
    }
    public void saveMessage(String message) {
    	jedis.sadd("message",message);
    }
    public Set<String> getMessage() {
    	return jedis.smembers("message");
    }
}


