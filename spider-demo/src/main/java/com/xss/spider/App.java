package com.xss.spider;

import com.xss.spider.Handler.SpiderHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2017/7/18 0018
 * spring-boot 启动类
 */
@SpringBootApplication
public class App {

    @Autowired
    private SpiderHandler spiderHandler;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    public void task() {

        spiderHandler.spiderData();
    }
}
