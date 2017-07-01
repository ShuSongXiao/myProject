package com.xss.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Administrator on 2017/7/1 0001
 * 单元测试基类
 */
@SpringBootApplication
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JavaBaseTest.class)
public class JavaBaseTest {
    @Test
    public void test() {
        Assert.assertTrue(true);
    }
}
