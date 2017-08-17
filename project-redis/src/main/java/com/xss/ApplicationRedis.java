package com.xss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by Administrator on 2017/8/16 0016
 *
 */
@SpringBootApplication
public class ApplicationRedis extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ApplicationRedis.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationRedis.class, args);
    }
}
