package com.iisquare.sjt.api.mvc;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

public class BeanNameGenerator extends AnnotationBeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String classname = definition.getBeanClassName();
        if(classname.startsWith("com.iisquare.sjt.")) return classname;
        return super.generateBeanName(definition, registry);
    }

}
