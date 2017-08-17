package com.xss.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/7/20 0020
 *当想给某个方法自定义返回属性时, 使用此注解. 标注在 controller 上!<br>
 * <span style="color:red;">只在方法的返回值是 JsonResult 时有效, 且必须保证 value 和 cascade 其中一个有值</span>
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReturnProperties {

    /**
     * 用来说明 JsonResult 中 data 的过滤属性. 当 data 只有一层时有用<br>
     * 如 value = {"id", "name"}, 最终会生成 {"code", "msg", .. "data", "data.id", "data.name"} 进行过滤
     */
    String[] value() default {};

    /**
     * <pre>
     * 用来说明 JsonResult 中 data 的过滤属性. 当 data 是两层时有用
     *
     * 如
     * cascade = {
     *   &#064;Mapping(key = "users", values = {"id", "name"}),
     *   {@literal @}Mapping(key = "orders", values = {"id", "orderNo"})
     * }
     *
     * 最终会生成
     * {
     *   "code", "msg", .. "data",
     *   "data.users", "data.users.id", "data.users.name",
     *   "data.orders", "data.orders.id", "data.orders.orderNo"
     * }
     * 进行过滤
     * </pre>
     */
    Mapping[] cascade() default {};

    public @interface Mapping {
        String key();
        String[] values();
    }
}
