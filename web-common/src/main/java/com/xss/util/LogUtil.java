package com.xss.util;

import com.xss.date.DateFormatType;
import com.xss.date.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * Created by Administrator on 2017/7/19 0019
 * 日志打印工具类
 */
public final class LogUtil {
    /** 根日志: 在类里面使用 LoggerFactory.getLogger(XXX.class) 跟这种方式一样! */
    public static final Logger ROOT_LOG = LoggerFactory.getLogger("root");

    /** SQL 相关的日志 */
    public static final Logger SQL_LOG = LoggerFactory.getLogger("sqlLog");

    /** mq 相关的日志 */
    public static final Logger MQ_LOG = LoggerFactory.getLogger("mqLog");

    public static final class Mdc {
        /** 接收到请求的时间. 在 log.xml 中使用 %X{receiveTime} 获取  */
        private static final String RECEIVE_TIME = "receiveTime";
        /** 请求信息, 包括有 ip、url, param 等  */
        private static final String REQUEST_INFO = "requestInfo";
        /** 请求里包含的头信息  */
        private static final String HEAD_INFO = "headInfo";

        /** 输出当前请求信息, 在日志中显示. 非线上时打印出头部参数 */
        public static void bind(Long id, String name) {
            String ip = RequestUtils.getRealIp();
            String method = RequestUtils.getRequest().getMethod();
            String url = RequestUtils.getRequestUrl();
            String param = RequestUtils.formatParam();

            MDC.put(RECEIVE_TIME, DateUtil.format(DateUtil.now(), DateFormatType.YYYY_MM_DD_HH_MM_SS_SSS) + " -> ");
            MDC.put(REQUEST_INFO, String.format("%s (%s,%s) (%s %s) param(%s)", ip, id, name, method, url, param));
            MDC.put(HEAD_INFO, String.format("header(%s)", RequestUtils.formatHeadParam()));
        }
        public static void unbind() {
            MDC.clear();
        }
    }
}
