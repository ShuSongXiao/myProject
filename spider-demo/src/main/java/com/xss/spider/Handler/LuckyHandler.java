package com.xss.spider.Handler;

import com.google.common.collect.Maps;
import com.xss.date.DateFormatType;
import com.xss.spider.constant.SysConstant;
import com.xss.spider.service.LuckyService;
import com.xss.util.LogUtil;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaoss390 on 2017/11/8.
 */
@Component
public class LuckyHandler {

    @Autowired
    private LuckyService luckyService;

    public void spiderData() {
        LogUtil.ROOT_LOG.info("爬虫开始....");
        Date startDate = new Date();
        luckyService.spiderData(SysConstant.DANDAN_29, null);
        Date endDate = new Date();

        FastDateFormat fdf = FastDateFormat.getInstance(DateFormatType.YYYY_MM_DD_HH_MM_SS.getValue());
        LogUtil.ROOT_LOG.info("爬虫结束....");
        LogUtil.ROOT_LOG.info("[开始时间:" + fdf.format(startDate) + ",结束时间:" + fdf.format(endDate) + ",耗时:"
                + (endDate.getTime() - startDate.getTime()) + "ms]");

    }
}
