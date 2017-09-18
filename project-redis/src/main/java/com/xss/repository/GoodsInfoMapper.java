package com.xss.repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.xss.model.GoodsInfo;
import com.xss.model.GoodsInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

public interface GoodsInfoMapper {
    int countByExample(GoodsInfoExample example);

    int deleteByExample(GoodsInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insertSelective(GoodsInfo record);

    List<GoodsInfo> selectByExample(GoodsInfoExample example, PageBounds page);

    void selectByExample(GoodsInfoExample example, ResultHandler handler);

    void selectByExample(GoodsInfoExample example, PageBounds page, ResultHandler handler);

    List<GoodsInfo> selectByExample(GoodsInfoExample example);

    GoodsInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") GoodsInfo record, @Param("example") GoodsInfoExample example);

    int updateByPrimaryKeySelective(GoodsInfo record);
}