package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.service.RoleService;
import com.iisquare.sjt.cms.utils.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/manage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String listAction(@RequestParam Map<?, ?> param, ModelMap model) {
        Map<?, ?> result = roleService.search(param);
        return ApiUtil.echoResult(0, null, result);
    }

}
