package com.xss.spider.Handler;

import com.google.common.collect.Maps;
import com.xss.date.DateFormatType;
import com.xss.spider.constant.SysConstant;
import com.xss.spider.service.SpiderService;
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
 * Created by Administrator on 2017/7/19 0019.
 */
@Component
public class SpiderHandler {
    @Autowired
    private SpiderService spiderService;
    public void spiderData() {
        LogUtil.ROOT_LOG.info("爬虫开始....");
        Date startDate = new Date();
        // 使用现线程池提交任务
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //引入countDownLatch进行线程同步，使主线程等待线程池的所有任务结束，便于计时
        CountDownLatch countDownLatch = new CountDownLatch(100);
        for(int i = 1; i < 201; i += 2) {
            Map<String, String> params = Maps.newHashMap();
            params.put("keyword", "零食");
            params.put("enc", "utf-8");
            params.put("wc", "零食");
            params.put("page", i + "");
            executorService.submit(() -> {
                spiderService.spiderData(SysConstant.BASE_URL, params);
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        Date endDate = new Date();

        FastDateFormat fdf = FastDateFormat.getInstance(DateFormatType.YYYY_MM_DD_HH_MM_SS.getValue());
        LogUtil.ROOT_LOG.info("爬虫结束....");
        LogUtil.ROOT_LOG.info("[开始时间:" + fdf.format(startDate) + ",结束时间:" + fdf.format(endDate) + ",耗时:"
                + (endDate.getTime() - startDate.getTime()) + "ms]");

    }
}