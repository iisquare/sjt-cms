package com.iisquare.sjt.cms.controller.web;

import com.iisquare.sjt.cms.core.ControllerBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class IndexController extends ControllerBase {

    @RequestMapping("/")
    public String indexAction() {
        return "web/index/index";
    }

}
