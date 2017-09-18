package com.xss.service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.xss.model.GoodsInfo;
import com.xss.model.GoodsInfoExample;
import com.xss.repository.GoodsInfoMapper;
import com.xss.util.LogUtil;
import com.xss.util.U;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/8/17 0017
 *
 */
@Service
public class GoodsInfoService {

    @Autowired
    private GoodsInfoMapper goodsInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<GoodsInfo> getInfo(Integer id){
        GoodsInfoExample example = new GoodsInfoExample();
        example.or().andIdEqualTo(id);
        return goodsInfoMapper.selectByExample(example);
    }

    public List<GoodsInfo> getInfoPage(PageBounds pageBounds){
        GoodsInfoExample example = new GoodsInfoExample();
        return goodsInfoMapper.selectByExample(example,pageBounds);
    }

    /**
     * @Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有再从DB获取数据，然后把数据添加到缓存中
     * unless 表示条件表达式成立的话不放入缓存
     * @param  id
     * @return GoodsInfo
     */
    @Cacheable(value = "goodsInfo", key = "#root.methodName + #id")
    public GoodsInfo getGoodsInfoForId(Integer id){

        return goodsInfoMapper.selectByPrimaryKey(id);
    }


    public GoodsInfo getGoodsInfoForRedis(Integer id){
        String key = "goods_" + id;
        ValueOperations operations = redisTemplate.opsForValue();

        //缓存是否存在
        boolean haskey = redisTemplate.hasKey(key);
        if(haskey){
            GoodsInfo goodsInfo = (GoodsInfo) operations.get(key);//从缓存中读数据
            LogUtil.ROOT_LOG.info("从缓存中读取到商品信息数据：{}",goodsInfo.getGoodsName());
            return  goodsInfo;
        }
        //从db读取数据
        GoodsInfo goodsInfo = goodsInfoMapper.selectByPrimaryKey(id);
        //不为空则先缓存
        if(U.isNotBlank(goodsInfo))
            operations.set(key, goodsInfo, 1 , TimeUnit.HOURS);

        LogUtil.ROOT_LOG.info("插入商品信息数据到缓存成功：{}" , key);
        return goodsInfo;
    }
}
