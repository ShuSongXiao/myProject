package com.xss.redis;

import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15 0015.
 * 测试 jedis
 */
public class RedisTest {

    public static void main(String [] str){

        Jedis jedis = new Jedis("192.168.32.180",6379);
        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
        System.out.println("Server is running: "+jedis.ping());
        jedis.lpush("forezp-list", "Redis");
        jedis.lpush("forezp-list", "Mongodb");
        jedis.lpush("forezp-list", "Mysql");
        // Get the stored data and print it
        List<String> list = jedis.lrange("forezp-list", 0 ,5);
        for(int i=0; i<list.size(); i++) {
            System.out.println("Stored string in redis:: "+list.get(i));
        }
    }

}
