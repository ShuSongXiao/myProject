package com.xss;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.springframework.http.HttpMethod;

/**
 * Created by Administrator on 2017/7/1 0001
 * 一般项目中用到的常量放在这里
 */
public final class Const {

    /** 分页默认页 */
    public static final int DEFAULT_PAGE_NO = 1;
    /** 分页默认的每页条数 */
    public static final int DEFAULT_LIMIT = 15;
    /** 前台传递过来的分页参数名 */
    public static final String GLOBAL_PAGE = "page";
    /** 前台传递过来的每页条数名 */
    public static final String GLOBAL_LIMIT = "limit";

    /** 分页空对象 **/
    public static final PageList EMPTY_PAGE_LIST = new PageList<>( new Paginator( DEFAULT_PAGE_NO, DEFAULT_LIMIT, 0 ) );

    /** cors 支持的所有方法. 原则上来说, 国内复杂的浏览器环境, 开发时只支持 get 和 post 就好了, 其他忽略 */
    public static final String[] SUPPORT_METHODS = new String[] {
            HttpMethod.HEAD.name(),

            HttpMethod.GET.name(),
            HttpMethod.POST.name(),

            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.TRACE.name()
    };
}
