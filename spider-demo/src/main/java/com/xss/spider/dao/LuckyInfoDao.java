package com.xss.spider.dao;

import com.xss.spider.Entity.LuckyInfo;

import java.util.List;

/**
 * Created by xiaoss390 on 2017/11/8
 * 幸运28
 */
public interface LuckyInfoDao {

    /**
     * 插入信息
     * @param infos
     */
    void saveBatch(List<LuckyInfo> infos);

    /**
     * 删除信息
     */
    void deleteBatch();
}
