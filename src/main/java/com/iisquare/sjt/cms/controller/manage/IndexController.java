package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.core.ControllerBase;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manage")
@Controller
public class IndexController extends ControllerBase {

    @RequestMapping("/")
    public String indexAction() {
        return "manage/index";
    }

}
