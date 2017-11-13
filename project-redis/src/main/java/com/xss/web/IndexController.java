package com.xss.web;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.xss.json.JsonResult;
import com.xss.model.User;
import com.xss.service.GoodsInfoService;
import com.xss.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
/*import org.springframework.web.bind.annotation.RestController;*/

import static com.xss.json.JsonResult.fail;
import static com.xss.json.JsonResult.success;

/**
 * Created by Administrator on 2017/8/16 0016
 *
 */
@Controller
public class IndexController {

    @Autowired
    private GoodsInfoService goodsInfoService;


    @RequestMapping(value = "/index")
    @ResponseBody
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/user")
    public JsonResult getUser(){
        User user = new User();
        user.setSex(1).setUserName("123");
        return success("aaa", user);
    }

    @RequestMapping(value = "/goods-info")
    public JsonResult getGoodsInfoForId(Integer id){
        return success("查询成功", goodsInfoService.getInfo(id));
    }

    @RequestMapping(value = "/goods-info-page")
    public JsonResult getGoodsInfoForPage(PageBounds pageBounds){
        return success("查询成功", goodsInfoService.getInfoPage(pageBounds));
    }

    @RequestMapping(value = "/goods-info-id")
    public JsonResult getInfoForId(Integer id){
        if(U.less0(id)) return fail("查询条件不能为空");
        return success("查询成功", goodsInfoService.getGoodsInfoForId(id));
    }

    @RequestMapping(value = "/goods-info-redis")
    public JsonResult getInfoForRedis(Integer id){
        if(U.less0(id)) return fail("查询条件不能为空");
        return success("查询成功", goodsInfoService.getGoodsInfoForRedis(id));
    }
}
