package com.iisquare.sjt.api.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

/**
 * WebMvcConfigurationSupport只能有一个实例
 */
public abstract class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Value("${spring.http.encoding.charset}")
    private String charset;
    @Value("${custom.uploads.path}")
    private String uploadsPath;

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/", "classpath:/public/");
        String filepath = new File(uploadsPath).getAbsolutePath().replaceAll("\\\\", "/");
        if(!filepath.startsWith("/")) filepath = "/" + filepath;
        if(!filepath.endsWith("/")) filepath += "/";
        registry.addResourceHandler("/files/**").addResourceLocations("file://" + filepath);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        StringHttpMessageConverter convert = new StringHttpMessageConverter(Charset.forName(charset));
        convert.setWriteAcceptCharset(false);
        converters.add(convert);
        converters.add(new MappingJackson2HttpMessageConverter());
    }

    @Override
    protected void addCorsMappings(CorsRegistry registry) {
        super.addCorsMappings(registry);
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowCredentials(true)
            .allowedMethods("GET", "POST", "DELETE", "PUT")
            .maxAge(3600);
    }

}
