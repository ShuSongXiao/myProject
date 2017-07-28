package com.xss.spider.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Administrator on 2017/7/20 0020
 * 基础控制类
 */
@Controller
public class BackController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String login(){
        return "成功";
    }
}
