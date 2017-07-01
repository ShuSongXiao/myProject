package com.xss.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/7/1 0001.
 * 单元测试基础类
 */
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ServiceBaseTest.class)
public class ServiceBaseTest {
    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
