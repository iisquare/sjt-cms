package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.utils.ApiUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/user")
public class UserController {

    @RequestMapping("/info")
    public String infoAction() {
        return ApiUtil.echoResult(0, null, null);
    }

}
