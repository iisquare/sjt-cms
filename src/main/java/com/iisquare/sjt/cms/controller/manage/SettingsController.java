package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.domain.Settings;
import com.iisquare.sjt.cms.service.SettingsService;
import com.iisquare.sjt.cms.service.UserService;
import com.iisquare.sjt.cms.utils.ApiUtil;
import com.iisquare.sjt.cms.utils.DPUtil;
import com.iisquare.sjt.cms.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage/settings")
public class SettingsController {

    @Autowired
    private UserService userService;
    @Autowired
    private SettingsService settingsService;

    @RequestMapping("/list")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = settingsService.search(param, DPUtil.buildMap("withUserInfo", true));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    public String saveAction(@RequestBody Map<?, ?> param) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
        if(DPUtil.empty(name)) return ApiUtil.echoResult(1001, "名称异常", name);
        int sort = DPUtil.parseInt(param.get("sort"));
        String description = DPUtil.parseString(param.get("description"));
        String type = DPUtil.trim(DPUtil.parseString(param.get("type")));
        Settings info = null;
        if(id > 0) {
            info = settingsService.info(id);
            if(null == info) return ApiUtil.echoResult(404, null, id);
        } else {
            info = new Settings();
        }
        info.setName(name);
        info.setType(type);
        info.setContent(DPUtil.parseString(param.get("content")));
        info.setSort(sort);
        info.setDescription(description);
        info = settingsService.save(info, userService.current().getId());
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
        boolean result = settingsService.delete(ids);
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

}
