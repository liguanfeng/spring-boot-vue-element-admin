package com.yy.admin.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yy.admin.interceptor.CommonInterceptor;
import com.yy.admin.interceptor.LoginInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.format.Formatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author wangwu
 * @date 2019/3/4 10:54
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public CommonInterceptor commonInterceptor() {
        return new CommonInterceptor();
    }

    @Bean
    public LoginInterceptor authorizationInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setKeepAliveSeconds(1200);
        executor.setMaxPoolSize(2000);
        executor.setQueueCapacity(200);
        return executor;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(commonInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("static/html/index.html");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect);
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
//        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
//        serializeConfig.put(Long.class, ToStringSerializer.instance);
//        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
//        serializeConfig.put(LocalDateTime.class, ToDateSerializer.instance);
//        serializeConfig.put(LocalDate.class, ToDateSerializer.instance);
//        serializeConfig.put(Date.class, ToDateSerializer.instance);
//        serializeConfig.put(java.sql.Date.class, ToDateSerializer.instance);
        config.setSerializeConfig(serializeConfig);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        converters.add(fastJsonHttpMessageConverter);
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public @Nullable
            String print(@Nullable LocalDate object, @Nullable Locale locale) {
                if (Objects.isNull(object)) {
                    return null;
                }
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            @Override
            public @Nullable
            LocalDate parse(@Nullable String text, @Nullable Locale locale) {
                if (StringUtils.isEmpty(text)) {
                    return null;
                }
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public @Nullable
            String print(@Nullable LocalDateTime object, @Nullable Locale locale) {
                if (Objects.isNull(object)) {
                    return null;
                }
                return object.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }

            @Override
            public @Nullable
            LocalDateTime parse(@Nullable String text, @Nullable Locale locale) {
                if (StringUtils.isEmpty(text)) {
                    return null;
                }
                return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };
    }

    @Bean
    public Formatter<LocalTime> localTimeFormatter() {
        return new Formatter<LocalTime>() {
            @Override
            public @Nullable
            String print(@Nullable LocalTime object, @Nullable Locale locale) {
                if (Objects.isNull(object)) {
                    return null;
                }
                return object.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            }

            @Override
            public @Nullable
            LocalTime parse(@Nullable String text, @Nullable Locale locale) {
                if (StringUtils.isEmpty(text)) {
                    return null;
                }
                return LocalTime.parse(text, DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        };
    }
}
