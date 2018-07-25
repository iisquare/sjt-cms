package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.api.domain.Category;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import com.iisquare.sjt.manage.mvc.Permission;
import com.iisquare.sjt.manage.mvc.PermitController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController extends PermitController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/list")
    @Permission("")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = categoryService.search(param, DPUtil.buildMap(
            "withUserInfo", true, "withStatusText", true, "withParentInfo", true
        ));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    @Permission({"add", "modify"})
    public String saveAction(@RequestBody Map<?, ?> param, HttpServletRequest request) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
        if(DPUtil.empty(name)) return ApiUtil.echoResult(1001, "名称异常", name);
        int status = DPUtil.parseInt(param.get("status"));
        if(!categoryService.status("default").containsKey(status)) return ApiUtil.echoResult(1002, "状态异常", status);
        int parentId = DPUtil.parseInt(param.get("parentId"));
        if(parentId < 0) {
            return ApiUtil.echoResult(1003, "上级节点异常", name);
        } else if(parentId > 0) {
            Category parent = categoryService.info(parentId);
            if(null == parent || !categoryService.status("default").containsKey(parent.getStatus())) {
                return ApiUtil.echoResult(1004, "上级节点不存在或已删除", name);
            }
        }
        Category info = null;
        if(id > 0) {
            if(!hasPermit(request, "modify")) return ApiUtil.echoResult(9403, null, null);
            info = categoryService.info(id);
            if(null == info) return ApiUtil.echoResult(404, null, id);
        } else {
            if(!hasPermit(request, "add")) return ApiUtil.echoResult(9403, null, null);
            info = new Category();
        }
        info.setName(name);
        info.setParentId(parentId);
        info.setUrl(DPUtil.trim(DPUtil.parseString(param.get("url"))));
        info.setTarget(DPUtil.trim(DPUtil.parseString(param.get("target"))));
        info.setSort(DPUtil.parseInt(param.get("sort")));
        info.setStatus(status);
        info.setKeywords(DPUtil.parseString(param.get("keywords")));
        info.setDescription(DPUtil.parseString(param.get("description")));
        info = categoryService.save(info, uid(request));
        return ApiUtil.echoResult(null == info ? 500 : 0, null, info);
    }

    @RequestMapping("/delete")
    @Permission
    public String deleteAction(@RequestBody Map<?, ?> param, HttpServletRequest request) {
        List<Integer> ids = null;
        if(param.get("ids") instanceof List) {
            ids = DPUtil.parseIntList((List<?>) param.get("ids"));
        } else {
            ids = Arrays.asList(DPUtil.parseInt(param.get("ids")));
        }
        boolean result = categoryService.delete(ids, uid(request));
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

    @RequestMapping("/config")
    @Permission("")
    public String configAction(ModelMap model) {
        model.put("status", categoryService.status("default"));
        return ApiUtil.echoResult(0, null, model);
    }

}
