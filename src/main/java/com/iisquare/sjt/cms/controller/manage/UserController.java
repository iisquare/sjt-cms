package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.domain.User;
import com.iisquare.sjt.cms.utils.ApiUtil;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/manage/user")
public class UserController {

    @RequestMapping("/config")
    public String configAction() {
        return ApiUtil.echoResult(0, null, null);
    }

    @RequestMapping("/login")
    public String loginAction(@RequestParam Map<?, ?> param, ModelMap model) {
        model.put("info", User.builder().name("test").build());
        model.put("menu", null);
        model.put("resource", null);
        return ApiUtil.echoResult(0, null, model);
    }

}
