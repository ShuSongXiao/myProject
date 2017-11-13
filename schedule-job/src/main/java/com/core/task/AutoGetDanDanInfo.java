package com.core.task;

import com.core.quartz.Quartz;
import com.xss.util.LogUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by xiaoss390 on 2017/11/10
 * 定时任务，爬去蛋蛋28的数据
 */
public class AutoGetDanDanInfo extends QuartzJobBean implements Quartz {
    @Override
    public String getCronExpression() {
        return "0 0/5 * * * ? "; //五分钟执行一次
    }

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LogUtil.ROOT_LOG.info("定时任务开启");
    }
}
