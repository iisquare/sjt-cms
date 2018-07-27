package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.api.domain.User;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.api.service.MenuService;
import com.iisquare.sjt.api.service.MessageService;
import com.iisquare.sjt.api.service.UserService;
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

@Controller
@RequestMapping("/user")
public class UserController extends WebController {

    @Autowired
    private UserService userService;
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
        if(System.currentTimeMillis() - msgcodetime > 1800000 || !msgcode.equals(session.get("msgcode"))) {
            return ApiUtil.echoResult(1004, "短信验证码不正确或已过期", msgcode);
        }
        User info = userService.infoBySerial(mobile);
        long time = System.currentTimeMillis();
        String ip = ServletUtil.getRemoteAddr(request);
        if(null == info) {
            String name = "网友_" + time + DPUtil.random(6);
            info = User.builder().createdIp(ip).createdTime(time).loginedIp(ip).loginedTime(time)
                .name(name).serial(mobile).status(1).description("CMSWeb登录自动注册").build();
            info = userService.save(info, 0);
            return ApiUtil.echoResult(null == info ? 500 : 0, null, null);
        }
        if(info.getLockedTime() > System.currentTimeMillis()) {
            return ApiUtil.echoResult(1103, "账号已锁定，请联系客服人员", null);
        }
        info.setLoginedTime(time);
        info.setLoginedIp(ip);
        userService.save(info, 0);
        sessionService.currentInfo(request, DPUtil.buildMap(
            "uid", info.getId(), "code", null, "codetime", null, "msgcode", null, "msgcodetime", null));
        return ApiUtil.echoResult(0, null, null);
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
        sessionService.currentInfo(request, DPUtil.buildMap("msgcode", msgcode, "msgcodetime", System.currentTimeMillis()));
        return ApiUtil.echoResult(messageService.sendCode(mobile, msgcode, 120));
    }

    @GetMapping("/login.shtml")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        model.put("sectionLogin", settingService.get("cmsSectionLogin"));
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