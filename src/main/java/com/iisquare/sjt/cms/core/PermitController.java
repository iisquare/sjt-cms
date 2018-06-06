package com.iisquare.sjt.cms.core;

import com.iisquare.sjt.cms.service.SessionService;
import com.iisquare.sjt.cms.utils.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public abstract class PermitController extends ControllerBase {

    @Autowired
    protected SessionService sessionService;

    public int uid(HttpServletRequest request) {
        return DPUtil.parseInt(sessionService.currentInfo(request, null).get("uid"));
    }

    public boolean hasPermit(HttpServletRequest request, String module, String controller, String action) {
        return sessionService.hasPermit(request, module, controller, action);
    }

    public boolean hasPermit(HttpServletRequest request, String controller, String action) {
        String module = request.getAttribute("module").toString();
        return sessionService.hasPermit(request, module, controller, action);
    }

    public boolean hasPermit(HttpServletRequest request, String action) {
        String module = request.getAttribute("module").toString();
        String controller = request.getAttribute("controller").toString();
        return sessionService.hasPermit(request, module, controller, action);
    }

    public boolean hasPermit(HttpServletRequest request) {
        String module = request.getAttribute("module").toString();
        String controller = request.getAttribute("controller").toString();
        String action = request.getAttribute("action").toString();
        return sessionService.hasPermit(request, module, controller, action);
    }

}
