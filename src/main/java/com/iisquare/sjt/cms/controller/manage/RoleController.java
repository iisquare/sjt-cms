package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.service.RoleService;
import com.iisquare.sjt.cms.utils.ApiUtil;
import com.iisquare.sjt.cms.utils.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/list")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = roleService.search(param);
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/delete")
    public String deleteAction(@RequestBody Map<?, ?> param) {
        List<Integer> ids = null;
        if(param.get("ids") instanceof List) {
            ids = DPUtil.parseIntList((List<?>) param.get("ids"));
        } else {
            ids = Arrays.asList(DPUtil.parseInt(param.get("ids")));
        }
        boolean result = roleService.delete(ids);
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

}
