package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.core.PermitController;
import com.iisquare.sjt.cms.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/manage")
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
