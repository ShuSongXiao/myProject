package com.xss.service.service;

import com.xss.service.ServiceBaseTest;
import org.junit.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Administrator on 2017/7/1 0001
 *
 */
@SpringBootApplication(scanBasePackages = "com.xss.service")
public class BaseTest extends ServiceBaseTest {

    @Test
    public void addTest(){
        System.out.println("aaa");
    }
}
