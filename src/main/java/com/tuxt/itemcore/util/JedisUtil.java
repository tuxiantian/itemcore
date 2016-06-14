package com.tuxt.itemcore.util;


import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisUtil {
	static JedisPool pool;
	 static{
		 ResourceBundle bundle = ResourceBundle.getBundle("config/redis");  
	        if (bundle == null) {  
	            throw new IllegalArgumentException(  
	                    "[redis.properties] is not found!");  
	        }  
	        GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
	        poolConfig.setMaxIdle(Integer.parseInt(bundle.getString("redis.pool.maxIdle").trim()));
	        poolConfig.setMaxTotal(Integer.parseInt(bundle.getString("redis.pool.maxTotal").trim()));
	        poolConfig.setMaxWaitMillis(
	        		Long.parseLong(bundle.getString("redis.pool.maxWaitMillis").trim()));
	        poolConfig.setMinEvictableIdleTimeMillis(
	        		Long.parseLong(bundle.getString("redis.pool.minEvictableIdleTimeMillis").trim()));
	        poolConfig.setMinIdle(
	        		Integer.parseInt(bundle.getString("redis.pool.minIdle").trim()));
	        pool=new JedisPool(poolConfig, bundle.getString("redis.host").trim(),
	        		Integer.parseInt(bundle.getString("redis.port").trim()));
	 }
	 public static synchronized Jedis getInstance(){
		 return pool.getResource();
	 }
	 public static void releace(Jedis jedis){
		 pool.returnResourceObject(jedis);
	 }
    public static Jedis createJedis() {
    	Jedis jedis=new Jedis("127.0.0.1");
        return jedis;
    }

    public static Jedis createJedis(String host, int port) {
        Jedis jedis = new Jedis(host, port);

        return jedis;
    }

    public static Jedis createJedis(String host, int port, String passwrod) {
        Jedis jedis = new Jedis(host, port);

        if (StringUtil.isNotEmpty(passwrod))
            jedis.auth(passwrod);
        
        return jedis;
    }
    public static void hmset(String key,Map<String,String> hash){
    	Jedis jedis = getInstance();
    	if (!jedis.exists(key)) {
			jedis.hmset(key, hash);
		}
    }
    public static String hmget(String mapkey,String key){
    	Jedis jedis = getInstance();
    	return jedis.hmget(mapkey, key).get(0);
    }
    public static void main(String[] args) {
    	Jedis jedis = getInstance();
    	//jedis.del("test");
    	//jedis.append("test", "hello word");
    	/*if (jedis.exists("test")) {
			
    		System.out.println(jedis.get("test"));
		}
    	releace(jedis);
    	if (jedis.exists("test")) {
			
    		System.out.println(jedis.get("test"));
		}*/
    	System.out.println(hmget("provinceMap", "100"));
	}
}