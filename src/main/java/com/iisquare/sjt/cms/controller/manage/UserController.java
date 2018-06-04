package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.core.Configuration;
import com.iisquare.sjt.cms.domain.User;
import com.iisquare.sjt.cms.service.*;
import com.iisquare.sjt.cms.utils.ApiUtil;
import com.iisquare.sjt.cms.utils.DPUtil;
import com.iisquare.sjt.cms.utils.ServletUtil;
import com.iisquare.sjt.cms.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/manage/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private RelationService relationService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SessionService sessionService;

    @RequestMapping("/list")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = userService.search(param, DPUtil.buildMap("withUserInfo", true, "withStatusText", true, "withRoles", true));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    public String saveAction(@RequestBody Map<?, ?> param, HttpServletRequest request) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
        if(DPUtil.empty(name)) return ApiUtil.echoResult(1001, "名称异常", name);
        if(userService.existsByName("name", id)) return ApiUtil.echoResult(2001, "名称已存在", name);
        String password = DPUtil.trim(DPUtil.parseString(param.get("password")));
        User info = null;
        if(id > 0) {
            info = userService.info(id);
            if(null == info) return ApiUtil.echoResult(404, null, id);
        } else {
            info = new User();
            String serial = DPUtil.trim(DPUtil.parseString(param.get("serial")));
            if(DPUtil.empty(serial)) return ApiUtil.echoResult(1002, "账号不能为空", name);
            if(userService.existsBySerial(serial)) return ApiUtil.echoResult(2002, "账号已存在", name);
            info.setSerial(serial);
            if(DPUtil.empty(password)) return ApiUtil.echoResult(1003, "密码不能为空", name);
            info.setCreatedIp(ServletUtil.getRemoteAddr(request));
        }
        if(!DPUtil.empty(password)) {
            String salt = DPUtil.random(4);
            password = userService.password(password, salt);
            info.setPassword(password);
            info.setSalt(salt);
        }
        int sort = DPUtil.parseInt(param.get("sort"));
        int status = DPUtil.parseInt(param.get("status"));
        if(!userService.status("default").containsKey(status)) return ApiUtil.echoResult(1002, "状态异常", status);
        String description = DPUtil.parseString(param.get("description"));
        info.setName(name);
        info.setSort(sort);
        info.setStatus(status);
        info.setDescription(description);
        String lockedTime =  DPUtil.trim(DPUtil.parseString(param.get("lockedTime")));
        if(!DPUtil.empty(lockedTime)) {
            info.setLockedTime(DPUtil.dateTimeToMillis(lockedTime, configuration.getDateFormat()));
        }
        info = userService.save(info, userService.current().getId());
        return ApiUtil.echoResult(null == info ? 500 : 0, null, info);
    }

    @RequestMapping("/delete")
    public String deleteAction(@RequestBody Map<?, ?> param) {
        List<Integer> ids = null;
        if(param.get("ids") instanceof List) {
            ids = DPUtil.parseIntList((List<?>) param.get("ids"));
        } else {
            ids = Arrays.asList(DPUtil.parseInt(param.get("ids")));
        }
        boolean result = userService.delete(ids);
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

    @RequestMapping("/config")
    public String configAction(ModelMap model) {
        model.put("status", userService.status("full"));
        model.put("defaultPassword", settingsService.get("system", "defaultPassword"));
        Map<?, ?> searchResult = roleService.search(new LinkedHashMap<>(), DPUtil.buildMap("withStatusText", true));
        model.put("roles", searchResult.get("rows"));
        return ApiUtil.echoResult(0, null, model);
    }

    @RequestMapping("/tree")
    public String treeAction(@RequestBody Map<?, ?> param, ModelMap model) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        if(id < 1) return ApiUtil.echoResult(1001, "参数异常", id);
        User info = userService.info(id);
        if(null == info || -1 == info.getStatus()) return ApiUtil.echoResult(1002, "记录不存在或已删除", id);
        Map<String, Object> result = new LinkedHashMap<>();
        String type = DPUtil.parseString(param.get("type"));
        if(param.containsKey("bids")) {
            switch (type) {
                case "role":
                    Set<Integer> bids = new HashSet<>();
                    bids.addAll((List<Integer>) param.get("bids"));
                    bids = relationService.relationIds("user_" + type, id, bids);
                    return ApiUtil.echoResult(null == bids ? 500 : 0, null, bids);
                default:
                    return ApiUtil.echoResult(1003, "类型异常", id);
            }
        } else {
            result.put("checked", relationService.relationIds("user_" + type, info.getId(), null));
            return ApiUtil.echoResult(0, null, result);
        }
    }

    @RequestMapping("/login")
    public String loginAction(@RequestBody Map<?, ?> param, HttpServletRequest request) {
        User info = null;
        Map<String, Object> session = null;
        if(param.containsKey("serial")) {
            info = userService.infoBySerial(DPUtil.parseString(param.get("serial")));
            if(null == info) return ApiUtil.echoResult(1001, "账号不存在", null);
            if(!info.getPassword().equals(userService.password(DPUtil.parseString(param.get("password")), info.getSalt()))) {
                return ApiUtil.echoResult(1002, "密码错误", null);
            }
            info.setLoginedTime(System.currentTimeMillis());
            info.setLoginedIp(ServletUtil.getRemoteAddr(request));
            userService.save(info, 0);
            session = sessionService.currentInfo(request, DPUtil.buildMap("uid", info.getId()));
        } else {
            session = sessionService.currentInfo(request, null);
            info = userService.info(DPUtil.parseInt(session.get("uid")));
        }
        if(null != info) {
            info.setPassword("");
            info.setSalt("");
        }
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("info", info);
        result.put("menu", sessionService.menu(session, DPUtil.parseInt(settingsService.get("system", "manageMenuParentId"))));
        result.put("resource", null);
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/logout")
    public String logoutAction(HttpServletRequest request) {
        sessionService.currentInfo(request, DPUtil.buildMap("uid", 0));
        return ApiUtil.echoResult(0, null, null);
    }

}
