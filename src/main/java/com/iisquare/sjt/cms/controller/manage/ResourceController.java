package com.iisquare.sjt.cms.controller.manage;

import com.iisquare.sjt.cms.domain.Resource;
import com.iisquare.sjt.cms.service.ResourceService;
import com.iisquare.sjt.cms.service.UserService;
import com.iisquare.sjt.cms.utils.ApiUtil;
import com.iisquare.sjt.cms.utils.DPUtil;
import com.iisquare.sjt.cms.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/manage/resource")
public class ResourceController {


    @Autowired
    private UserService userService;
    @Autowired
    private ResourceService resourceService;

    @RequestMapping("/list")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = resourceService.search(param, DPUtil.buildMap(
            "withUserInfo", true, "withStatusText", true, "withParentInfo", true
        ));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    public String saveAction(@RequestBody Map<?, ?> param) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
        if(DPUtil.empty(name)) return ApiUtil.echoResult(1001, "名称异常", name);
        int sort = DPUtil.parseInt(param.get("sort"));
        int status = DPUtil.parseInt(param.get("status"));
        if(!resourceService.status("default").containsKey(status)) return ApiUtil.echoResult(1002, "状态异常", status);
        String description = DPUtil.parseString(param.get("description"));
        int parentId = DPUtil.parseInt(param.get("parentId"));
        if(parentId < 0) {
            return ApiUtil.echoResult(1003, "上级节点异常", name);
        } else if(parentId > 0) {
            Resource parent = resourceService.info(parentId);
            if(null == parent || !resourceService.status("default").containsKey(parent.getStatus())) {
                return ApiUtil.echoResult(1004, "上级节点不存在或已删除", name);
            }
        }
        String module = DPUtil.trim(DPUtil.parseString(param.get("module")));
        String controller = DPUtil.trim(DPUtil.parseString(param.get("controller")));
        String action = DPUtil.trim(DPUtil.parseString(param.get("action")));
        Resource info = null;
        if(id > 0) {
            info = resourceService.info(id);
            if(null == info) return ApiUtil.echoResult(404, null, id);
        } else {
            info = new Resource();
        }
        info.setName(name);
        info.setParentId(parentId);
        info.setModule(module);
        info.setController(controller);
        info.setAction(action);
        info.setSort(sort);
        info.setStatus(status);
        info.setDescription(description);
        info = resourceService.save(info, userService.current().getId());
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
        boolean result = resourceService.delete(ids);
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

    @RequestMapping("/config")
    public String configAction(ModelMap model) {
        model.put("status", resourceService.status("default"));
        return ApiUtil.echoResult(0, null, model);
    }


}
