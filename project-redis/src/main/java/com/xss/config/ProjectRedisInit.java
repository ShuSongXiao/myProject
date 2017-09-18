package com.xss.config;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2017/8/17 0017
 * 项目中需要额外加载的类
 */
@Configuration
public class ProjectRedisInit {

    @Value("${online:false}")
    private boolean online;

    /*@Bean
    public ApplicationContextUtil setupApplicationContext() {
        return new ApplicationContextUtil();
    }*/

    /** 非线上则将 druid 的 servlet 开启 */
    @Bean
    @ConditionalOnProperty(name = {"online"}, havingValue = "false")
    public ServletRegistrationBean druidServlet() {
        return new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    }
}
