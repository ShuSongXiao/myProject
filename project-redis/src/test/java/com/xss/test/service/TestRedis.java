package com.xss.test.service;

import com.xss.model.User;
import com.xss.test.JavaBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/16 0016
 *
 */
@SpringBootApplication(scanBasePackages = "com.xss")
public class TestRedis extends JavaBaseTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test(){
        //System.out.println("aaa");
        //stringRedisTemplate.opsForValue().set("aaa","小娟娟");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa").equals("小娟娟"));
        //Assert.assertEquals("小娟娟", stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testObj() throws Exception{
        User user = new User();
        user.setEmail("xsslibe@163.com");
        user.setUserName("xsslibe");
        user.setPwd("xsslibe");
        user.setSex(1);
        ValueOperations<String, User> opt = redisTemplate.opsForValue();
        opt.set("myInfo", user);
        opt.set("info", user, 1, TimeUnit.SECONDS);
        Thread.sleep(1000);
        boolean exits = redisTemplate.hasKey("info");
        if(exits){
            System.out.printf("exits is true");
        }else {
            System.out.println("exits is false");
        }
        System.out.println(opt.get("myInfo").getUserName());

    }



}
