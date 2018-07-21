package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.cms.web.mvc.WebController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController extends WebController {

    @GetMapping("/")
    public String indexAction() {
        return "index/index";
    }

}
