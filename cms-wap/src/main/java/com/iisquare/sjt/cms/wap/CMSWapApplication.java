package com.iisquare.sjt.cms.wap;

import com.iisquare.sjt.api.mvc.BeanNameGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.iisquare.sjt.api.domain"})
@EnableJpaRepositories(basePackages = {"com.iisquare.sjt.api.dao"})
@ComponentScan(basePackages = {"com.iisquare.sjt.api.*", "com.iisquare.sjt.cms.wap"})
public class CMSWapApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(CMSWapApplication.class).beanNameGenerator(new BeanNameGenerator()).run(args);
    }

}
