package com.iisquare.sjt.cms.web.mvc;

import com.iisquare.sjt.api.mvc.ControllerBase;
import com.iisquare.sjt.api.service.SessionService;
import com.iisquare.sjt.api.service.SettingService;
import com.iisquare.sjt.api.service.UserService;
import com.iisquare.sjt.core.util.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class WebController extends ControllerBase {

    @Autowired
    protected SessionService sessionService;
    @Autowired
    protected SettingService settingService;
    @Autowired
    protected UserService userService;

    public int uid(HttpServletRequest request) {
        return DPUtil.parseInt(sessionService.currentInfo(request, null).get("uid"));
    }

    protected String displayTemplate(ModelMap model, HttpServletRequest request, String controller, String action) {
        Map<String, String> page = (Map<String, String>) model.get("page");
        if(null == page) model.put("page", page = new HashMap<>());
        if(DPUtil.empty(page.get("title"))) page.put("title", settingService.get("cms", "siteName"));
        if(DPUtil.empty(page.get("keywords"))) page.put("keywords", settingService.get("cms", "siteKeywords"));
        if(DPUtil.empty(page.get("description"))) page.put("description", settingService.get("cms", "siteDescription"));
        model.put("staticUrl", "/static");
        model.put("userInfo", userService.info(DPUtil.parseInt(sessionService.currentInfo(request, null).get("uid"))));
        if(DPUtil.empty(controller)) return action;
        return controller + "/" + action;
    }

    protected String error(ModelMap model, HttpServletRequest request, HttpServletResponse response, Integer code) {
        response.setStatus(code);
        return "forward:/error-"+ code + ".shtml";
    }

}
