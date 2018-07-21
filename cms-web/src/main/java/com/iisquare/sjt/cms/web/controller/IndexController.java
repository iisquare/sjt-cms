package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.cms.web.mvc.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class IndexController extends WebController {

    @GetMapping("/")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request, "index", "index");
    }

}
