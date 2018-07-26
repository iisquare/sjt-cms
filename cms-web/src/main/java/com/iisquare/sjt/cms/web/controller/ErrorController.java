package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.cms.web.mvc.WebController;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController extends WebController {

    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry registry) {
                registry.addErrorPages(
                    new ErrorPage(HttpStatus.NOT_FOUND, "/error-404.shtml"),
                    new ErrorPage(HttpStatus.BAD_REQUEST, "/error-404.shtml"),
                    new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-500.shtml")
                );
            }
        };
    }

    @RequestMapping("/error-404.shtml")
    public String handleError404(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request, "error", "404");
    }

    @RequestMapping("/error-500.shtml")
    public String handleError500(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request, "error", "500");
    }

}
