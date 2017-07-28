package com.xss.spider.config;

import com.github.miemiedev.mybatis.paginator.springmvc.PageListAttrHandlerInterceptor;
import com.google.common.collect.Lists;
import com.xss.Const;
import com.xss.converter.*;
import com.xss.json.JsonResult;
import com.xss.json.JsonUtil;
import com.xss.spider.annotation.ReturnProperties;
import com.xss.util.A;
import com.xss.util.RequestUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.MethodParameter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20 0020
 *
 */
@Configurable
public class WebApiWarInit extends WebMvcConfigurationSupport {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        // 从前台过来的数据转换成对应类型的转换器
        registry.addConverter(new StringTrimAndEscapeConverter());
        registry.addConverterFactory(new StringToNumberConverter());
        registry.addConverterFactory(new StringToEnumConverter());
        registry.addConverter(new String2DateConverter());
        registry.addConverter(new String2MoneyConverter());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 默认转换器注册后, 插入自定义的请求响应转换器
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new MappingJackson2HttpMessageConverter(JsonUtil.RENDER));
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        super.addReturnValueHandlers(returnValueHandlers);
        // 如果 Controller 方法的返回值是 JsonResult 则返回 json, 不需要额外在方法或类上标注 @ResponseBody
        returnValueHandlers.add(new HandlerMethodReturnValueHandler() {
            @Override
            public boolean supportsReturnType(MethodParameter returnType) {
                return JsonResult.class.isAssignableFrom(returnType.getParameterType());
            }
            @Override
            public void handleReturnValue(Object returnValue, MethodParameter returnType,
                                          ModelAndViewContainer mav, NativeWebRequest request) throws Exception {
                mav.setRequestHandled(true);

                Object userId = request.getAttribute("no", RequestAttributes.SCOPE_REQUEST);
                Object token = request.getAttribute("token", RequestAttributes.SCOPE_REQUEST);

                JsonResult result = ((JsonResult) returnValue);
                if (userId != null && token != null) {
                    result.setInfo(NumberUtils.toLong(userId.toString()), token.toString());
                }
                RequestUtils.toJson(returnJson(returnType, result), request.getNativeResponse(HttpServletResponse.class));
            }
        });
    }

    private String returnJson(final MethodParameter returnType, final JsonResult result) {
        ReturnProperties returnProperties = returnType.getMethodAnnotation(ReturnProperties.class);
        if (returnProperties != null) {
            String[] properties = returnProperties.value();
            ReturnProperties.Mapping[] mapping = returnProperties.cascade();
            if (A.isNotEmpty(properties) || A.isNotEmpty(mapping)) {
                List<String> propertyList = Lists.newArrayList(JsonResult.RETURN);
                if (A.isNotEmpty(properties)) {
                    for (String property : properties) {
                        propertyList.add(JsonResult.DATA + "." + property);
                    }
                } else if (A.isNotEmpty(mapping)) {
                    for (ReturnProperties.Mapping map : mapping) {
                        String parentCascade = JsonResult.DATA + "." + map.key();

                        propertyList.add(parentCascade);
                        for (String value : map.values()) {
                            propertyList.add(parentCascade + "." + value);
                        }
                    }
                }
                return JsonUtil.toJsonWithField(result, propertyList.toArray(new String[propertyList.size()]));
            }
        }
        return JsonUtil.toRender(result);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        // 添加自定义的拦截器
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new PageListAttrHandlerInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**").allowedMethods(Const.SUPPORT_METHODS);
    }
}
