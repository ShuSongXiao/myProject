package com.xss.spider.dao.impl;

import com.xss.spider.Entity.GoodsInfo;
import com.xss.spider.dao.GoodsInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019
 * 实现类
 */
@Repository
public class GoodsInfoDaoImpl implements GoodsInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /***使用jdbc的占位符向数据库插入数据*/
    @Override
    public void saveBatch(List<GoodsInfo> infos) {
        String sql = "REPLACE INTO GOODS_INFO(" + "GOODS_ID," + "GOODS_NAME," + "GOODS_PRICE," + "IMG_URL) "
                + "VALUES(?,?,?,?)";
        for(GoodsInfo info : infos) {
            jdbcTemplate.update(sql, info.getGoodsId(), info.getGoodsName(), info.getGoodsPrice(), info.getImgUrl());
        }
    }
}
