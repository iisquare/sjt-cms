package com.iisquare.sjt.cms.core;

import com.iisquare.sjt.cms.utils.DPUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.List;

@Configuration
public class CorsConfig extends WebMvcConfigurationSupport {

    @Value("${spring.http.encoding.charset}")
    private String charset;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if(!(handler instanceof HandlerMethod)) return true;
                HandlerMethod method = (HandlerMethod) handler;
                if(!(method.getBean() instanceof PermitController)) return true;
                PermitController instance = (PermitController) method.getBean();
                String[] names = DPUtil.explode(instance.getClass().getName(), "\\.", null, false);
                if(names.length < 2) return false;
                String module = names[names.length - 2];
                String controller = names[names.length - 1].replaceFirst("Controller", "");
                controller = controller.substring(0, 1).toLowerCase() + controller.substring(1);
                String action = method.getMethod().getName().replaceFirst("Action", "");
                action = action.substring(0, 1).toLowerCase() + action.substring(1);
                request.setAttribute("module", module);
                request.setAttribute("controller", controller);
                request.setAttribute("action", action);
                Permission permission = method.getMethodAnnotation(Permission.class);
                if(null == permission) return true;
                String[] permissions = permission.value();
                if(null == permissions || permissions.length < 1) {
                    permissions = new String[]{ module + ":" + controller + ":" + action };
                }
                for (String str : permissions) {
                    if(null == str) str = "";
                    String[] strs = str.split(":");
                    if(strs.length > 3) {
                        return false;
                    } else if(strs.length > 2) {
                        strs = new String[]{ strs[0], strs[1], strs[2] };
                    } else if(strs.length > 1) {
                        strs = new String[]{ module, strs[0], strs[1] };
                    } else if(strs.length > 0) {
                        strs = new String[]{ module, controller, strs[0] };
                    } else {
                        return false;
                    }
                    if(instance.hasPermit(request, strs[0], strs[1], strs[2])) return true;
                }
                return false;
            }
        }).addPathPatterns("/manage/**");
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
