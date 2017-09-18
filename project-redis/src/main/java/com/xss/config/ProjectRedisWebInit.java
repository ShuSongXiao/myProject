package com.xss.config;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.springmvc.PageListAttrHandlerInterceptor;
import com.google.common.collect.Lists;
import com.xss.Const;
import com.xss.annotation.ReturnProperties;
import com.xss.converter.*;
import com.xss.json.JsonResult;
import com.xss.json.JsonUtil;
import com.xss.util.A;
import com.xss.util.RequestUtils;
import com.xss.util.U;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by Administrator on 2017/8/17 0017
 *  自定义拦截器
 */
@Configuration
public class ProjectRedisWebInit extends WebMvcConfigurationSupport {

    @Override
    public void addFormatters(FormatterRegistry registry){
        registry.addConverter(new StringTrimAndEscapeConverter());
        registry.addConverterFactory(new StringToNumberConverter());
        registry.addConverterFactory(new StringToEnumConverter());
        registry.addConverter(new String2DateConverter());
        registry.addConverter(new String2MoneyConverter());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new MappingJackson2HttpMessageConverter(JsonUtil.RENDER));
    }

    /*page默认值设定  addArgumentResolvers 参数解析器*/
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        super.addArgumentResolvers(argumentResolvers);
        // 如果 Controller 方法中的参数有 PageBounds 则从前台获取数据组装, 如果没有传递则给设置一个默认值
        argumentResolvers.add(new HandlerMethodArgumentResolver() {
            @Override
            public boolean supportsParameter(MethodParameter methodParameter) {/*支持的属性*/
                return PageBounds.class.isAssignableFrom(methodParameter.getParameterType());
            }

            /*添加属性的默认值*/
            @Override
            public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                          NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
                    throws Exception {
                // 判断数据是否合理, 不合理就给定默认值
                int limit = NumberUtils.toInt(nativeWebRequest.getParameter(Const.GLOBAL_LIMIT));
                if (limit <= 0) limit = Const.DEFAULT_LIMIT;

                //手机端只用limit
                return new PageBounds(limit);
            }
        });
    }

    /*JsonResult  控制器自定义返回值*/
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

                Object userId = request.getAttribute(Const.NO, RequestAttributes.SCOPE_REQUEST);
                Object token = request.getAttribute(Const.TOKEN, RequestAttributes.SCOPE_REQUEST);

                JsonResult result = ((JsonResult) returnValue);
                if (userId != null && token != null) {
                    result.setInfo(NumberUtils.toLong(userId.toString()), token.toString());
                }
                RequestUtils.toJson(returnJson(returnType, result), request.getNativeResponse(HttpServletResponse.class));
            }
        });
    }

    /*返回json字符串*/
    public String returnJson(final MethodParameter returnType, final JsonResult result){
        ReturnProperties returnProperties = returnType.getMethodAnnotation(ReturnProperties.class);/*获取注解*/
        if(U.isNotBlank(returnProperties)){
            String[] properties = returnProperties.value(); /*从注解中获取返回值*/
            ReturnProperties.Mapping[] mapping = returnProperties.cascade();/*从注解中获取值*/
            if(A.isNotEmpty(mapping) || A.isNotEmpty(properties)){
                List<String> propertyList = Lists.newArrayList(JsonResult.RETURN);/*获取要返回的对象*/
                if (A.isNotEmpty(properties)) {/*从properties中获取值*/
                    for (String property : properties) {
                        propertyList.add(JsonResult.DATA + "." + property);
                    }
                } else { /*从 mapping中获取值*/
                    for (ReturnProperties.Mapping map : mapping) {
                        String parentCascade = JsonResult.DATA + "." + map.key();
                        propertyList.add(parentCascade);
                        for(String value : map.values()){
                            String obj = parentCascade + "." + value;
                            propertyList.add(obj);
                        }
                    }
                }
                return JsonUtil.toJsonWithField(result, propertyList.toArray(new String[propertyList.size()]));
            }
        }
        return JsonUtil.toRender(result);
    }

    /*添加自定义拦截器*/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
        //registry.addInterceptor(new PlatformInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new PageListAttrHandlerInterceptor()).addPathPatterns("/**");
    }

    /**
     * see : http://www.ruanyifeng.com/blog/2016/04/cors.html
     *
     * {@link org.springframework.web.servlet.config.annotation.CorsRegistration#CorsRegistration(String)}
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**").allowedMethods(Const.SUPPORT_METHODS);
    }

}
