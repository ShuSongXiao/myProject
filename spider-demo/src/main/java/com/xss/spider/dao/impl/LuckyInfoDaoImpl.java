package com.xss.spider.dao.impl;

import com.xss.spider.Entity.LuckyInfo;
import com.xss.spider.dao.LuckyInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xiaoss390 on 2017/11/8.
 */
@Repository
public class LuckyInfoDaoImpl implements LuckyInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void saveBatch(List<LuckyInfo> infos) {
        String sql = "REPLACE INTO T_LUCKY(" + "ISSUE_NO," + "LOTTERY_TIME," + "FORMULA," + "RESULT) "
                + "VALUES(?,?,?,?)";
        for(LuckyInfo info : infos) {
            jdbcTemplate.update(sql, info.getIssueNo(), info.getLotteryTime(), info.getFormula(), info.getResult());
        }
    }

    @Override
    public void deleteBatch() {
        String sql = "DELETE FROM T_LUCKY";
        jdbcTemplate.execute(sql);
    }
}
