package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.api.domain.User;
import com.iisquare.sjt.api.service.MessageService;
import com.iisquare.sjt.api.util.ServletUtil;
import com.iisquare.sjt.api.util.VerifyCodeUtil;
import com.iisquare.sjt.cms.web.mvc.WebController;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController extends WebController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/login")
    @ResponseBody
    public String loginAction(@RequestParam Map<?, ?> param, HttpServletRequest request) {
        String mobile = ValidateUtil.filterMobile(DPUtil.parseString(param.get("username")), true, 11, 11, null);
        if(DPUtil.empty(mobile)) return ApiUtil.echoResult(1001, "手机号码不能为空或格式不正确", mobile);
        String code = DPUtil.trim(DPUtil.parseString(param.get("code")));
        if(DPUtil.empty(code)) return ApiUtil.echoResult(1002, "请输入验证码", code);
        String msgcode = DPUtil.trim(DPUtil.parseString(param.get("password")));
        if(DPUtil.empty(msgcode)) return ApiUtil.echoResult(1005, "请输入短信验证码", msgcode);
        Map<String, Object> session = sessionService.currentInfo(request, null);
        long codetime = DPUtil.parseLong(session.get("codetime"));
        if(System.currentTimeMillis() - codetime > 900000 || !code.toLowerCase().equals(DPUtil.parseString(session.get("code")).toLowerCase())) {
            return ApiUtil.echoResult(1003, "验证码不正确或已过期", code);
        }
        long msgcodetime = DPUtil.parseLong(session.get("msgcodetime"));
        if(System.currentTimeMillis() - msgcodetime > 1800000
                || !mobile.equals(session.get("msgmobile")) || !msgcode.equals(session.get("msgcode"))) {
            return ApiUtil.echoResult(1004, "短信验证码不正确或已过期", msgcode);
        }
        User info = userService.infoBySerial(mobile);
        long time = System.currentTimeMillis();
        String ip = ServletUtil.getRemoteAddr(request);
        if(null == info) {
            String name = "网友_" + UUID.randomUUID().toString().replaceAll("\\-", "").toLowerCase();
            info = User.builder().createdIp(ip).createdTime(time).loginedIp(ip).loginedTime(time)
                .name(name).serial(mobile).status(1).description("CMSWeb登录自动注册").build();
            info = userService.save(info, 0);
        } else {
            if(1 != info.getStatus() || info.getLockedTime() > System.currentTimeMillis()) {
                return ApiUtil.echoResult(1103, "账号已锁定，请联系客服人员", null);
            }
            info.setLoginedTime(time);
            info.setLoginedIp(ip);
            info = userService.save(info, 0);
        }
        if(null == info) return ApiUtil.echoResult(500, null, null);
        sessionService.currentInfo(request, DPUtil.buildMap(
            "uid", info.getId(), "code", null, "codetime", null, "msgmobile", null, "msgcode", null, "msgcodetime", null));
        String url = DPUtil.trim(DPUtil.parseString(param.get("forward")));
        if(DPUtil.empty(url) || url.indexOf("login.shtml") != -1 || url.indexOf("logout") != -1) url = "/";
        return ApiUtil.echoResult(0, null, url);
    }

    @PostMapping("/sendmsg")
    @ResponseBody
    public String sendmsgAction(@RequestParam Map<?, ?> param, HttpServletRequest request) {
        String mobile = ValidateUtil.filterMobile(DPUtil.parseString(param.get("username")), true, 11, 11, null);
        if(DPUtil.empty(mobile)) return ApiUtil.echoResult(1001, "手机号码不能为空或格式不正确", mobile);
        String code = DPUtil.trim(DPUtil.parseString(param.get("code")));
        if(DPUtil.empty(code)) return ApiUtil.echoResult(1002, "请输入验证码", code);
        Map<String, Object> session = sessionService.currentInfo(request, null);
        long codetime = DPUtil.parseLong(session.get("codetime"));
        if(System.currentTimeMillis() - codetime > 900000 || !code.toLowerCase().equals(DPUtil.parseString(session.get("code")).toLowerCase())) {
            return ApiUtil.echoResult(1003, "验证码不正确或已过期", code);
        }
        long msgcodetime = DPUtil.parseLong(session.get("msgcodetime"));
        if(System.currentTimeMillis() - msgcodetime < 120000) {
            return ApiUtil.echoResult(1004, "操作太频繁", msgcodetime);
        }
        String msgcode = DPUtil.random(4);
        sessionService.currentInfo(request, DPUtil.buildMap(
                "msgmobile", mobile, "msgcode", msgcode, "msgcodetime", System.currentTimeMillis()));
        return ApiUtil.echoResult(messageService.sendCode(mobile, msgcode, 120));
    }

    @GetMapping("/login.shtml")
    public String indexAction(@RequestParam Map<?, ?> param, ModelMap model, HttpServletRequest request) {
        model.put("sectionLogin", settingService.get("cmsSectionLogin"));
        String url = DPUtil.trim(DPUtil.parseString(param.get("forward")));
        if(DPUtil.empty(url)) url = request.getHeader("referer");
        model.put("forward", url);
        return displayTemplate(model, request, "user", "login");
    }

    @GetMapping("/code.jpg")
    public void codeAction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String code = VerifyCodeUtil.generateVerifyCode(4);
        sessionService.currentInfo(request, DPUtil.buildMap("code", code, "codetime", System.currentTimeMillis()));
        int w = 100, h = 35;
        VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), code);
    }

    @RequestMapping("/logout")
    public String logoutAction(ModelMap model, HttpServletRequest request) {
        sessionService.currentInfo(request, DPUtil.buildMap("uid", 0));
        return "redirect:/user/login.shtml";
    }

}
