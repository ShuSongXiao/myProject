package com.xss.spider.dao;

import com.xss.spider.Entity.GoodsInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019
 * 这里使用spring 的 jdbcTemplate
 */
public interface GoodsInfoDao {

    /**
     * 插入商品信息
     * @param infos
     */
    void saveBatch(List<GoodsInfo> infos);
}
