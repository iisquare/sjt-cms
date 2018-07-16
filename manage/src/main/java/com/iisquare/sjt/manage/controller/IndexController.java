package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.manage.mvc.PermitController;
import com.iisquare.sjt.api.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/")
@Controller
public class IndexController extends PermitController {

    @Autowired
    private SettingsService settingsService;

    @RequestMapping("/")
    public String indexAction(ModelMap model) {
        Map<String, Object> page = new HashMap<>();
        page.put("title", settingsService.get("system", "siteName"));
        model.put("page", page);
        return "manage/index";
    }

}
