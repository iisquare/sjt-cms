package com.iisquare.sjt.cms.web.mvc;

import com.iisquare.sjt.api.mvc.ControllerBase;
import com.iisquare.sjt.api.service.SessionService;
import com.iisquare.sjt.api.service.SettingsService;
import com.iisquare.sjt.core.util.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class WebController extends ControllerBase {

    @Autowired
    protected SessionService sessionService;
    @Autowired
    private SettingsService settingsService;

    public int uid(HttpServletRequest request) {
        return DPUtil.parseInt(sessionService.currentInfo(request, null).get("uid"));
    }

    protected String displayTemplate(ModelMap model, HttpServletRequest request, String controller, String action) {
        String module = request.getAttribute("module").toString();
        Map<String, Object> page = new HashMap<>();
        page.put("title", settingsService.get("system", "siteName"));
        model.put("page", page);
        if(DPUtil.empty(controller)) {
            return module + "/" + action;
        }
        return controller + "/" + action;
    }

}
