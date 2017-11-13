package com.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Created by xiaoss390 on 2017/11/9.
 */
@SpringBootApplication
public class ScheduleJobApplication {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ScheduleJobApplication.class);
    }*/

    public static void main(String[] args) {
        SpringApplication.run(ScheduleJobApplication.class, args);
    }
}
