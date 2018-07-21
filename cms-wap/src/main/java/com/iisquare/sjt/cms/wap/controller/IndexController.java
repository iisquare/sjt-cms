package com.iisquare.sjt.cms.wap.controller;

import com.iisquare.sjt.cms.wap.mvc.WapController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController extends WapController {

    @GetMapping("/")
    public String indexAction() {
        return "index/index";
    }

}
