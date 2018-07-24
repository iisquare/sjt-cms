package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.manage.mvc.PermitController;
import com.iisquare.sjt.api.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/")
@Controller
public class IndexController extends PermitController {

    @Autowired
    private SettingService settingService;

    @RequestMapping("/")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request);
    }

}
