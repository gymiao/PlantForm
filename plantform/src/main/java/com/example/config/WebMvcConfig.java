package com.example.config;

import com.example.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //添加映射
        log.info("映射资源开始");
        // registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:static/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:static/front/");
    }
    
    /*
    扩展Mvc
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper((new JacksonObjectMapper()));
        converters.add(0,mappingJackson2HttpMessageConverter);
    }
}
