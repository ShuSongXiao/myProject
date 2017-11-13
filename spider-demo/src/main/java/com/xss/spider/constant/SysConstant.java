package com.xss.spider.constant;

/**
 * Created by Administrator on 2017/7/18 0018
 * 工具类 可阔扩展为更多网站
 */
public interface SysConstant {

    /**
     * 系统默认字符集
     */
    String DEFAULT_CHARSET = "utf-8";
    /**
     * 需要爬取的网站
     */
    String BASE_URL = "https://search.jd.com/Search";

    String DANDAN_29 = "https://www.dandan29.com/";

    interface Header {
        String ACCEPT = "Accept";
        String ACCEPT_ENCODING = "Accept-Encoding";
        String ACCEPT_LANGUAGE = "Accept-Language";
        String CACHE_CONTROL = "Cache-Controle";
        String COOKIE = "Cookie";
        String HOST = "Host";
        String PROXY_CONNECTION = "Proxy-Connection";
        String REFERER = "Referer";
        String USER_AGENT = "User-Agent";
    }
}

