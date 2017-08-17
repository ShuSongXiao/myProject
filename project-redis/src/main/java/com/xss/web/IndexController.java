package com.xss.web;

import com.xss.json.JsonResult;
import com.xss.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*import org.springframework.web.bind.annotation.RestController;*/

import static com.xss.json.JsonResult.success;

/**
 * Created by Administrator on 2017/8/16 0016
 *
 */
@Controller
public class IndexController {


    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/user")
    public JsonResult getUser(){
        User user = new User();
        user.setSex(1).setUserName("123");
        return success("aaa", user);
    }
}
