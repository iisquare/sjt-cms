package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.domain.Category;
import com.iisquare.sjt.api.service.ArticleService;
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
@RequestMapping("/article")
public class ArticleController extends PermitController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/info")
    @Permission("")
    public String infoAction(@RequestBody Map<?, ?> param) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        Article info = articleService.info(id);
        if(null == info || -1 == info.getStatus()) {
            return ApiUtil.echoResult(404, null, id);
        }
        return ApiUtil.echoResult(0, null , info);
    }

    @RequestMapping("/list")
    @Permission("")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = articleService.search(param, DPUtil.buildMap(
            "withUserInfo", true, "withStatusText", true, "withParentInfo", true
        ));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    @Permission({"add", "modify"})
    public String saveAction(@RequestBody Map<?, ?> param, HttpServletRequest request) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        String title = DPUtil.trim(DPUtil.parseString(param.get("title")));
        if(DPUtil.empty(title)) return ApiUtil.echoResult(1001, "标题异常", title);
        int status = DPUtil.parseInt(param.get("status"));
        if(!articleService.status("default").containsKey(status)) return ApiUtil.echoResult(1002, "状态异常", status);
        int categoryId = DPUtil.parseInt(param.get("categoryId"));
        if(categoryId < 1) {
            return ApiUtil.echoResult(1003, "栏目异常", categoryId);
        } else if(categoryId > 0) {
            Category parent = categoryService.info(categoryId);
            if(null == parent || !categoryService.status("default").containsKey(parent.getStatus())) {
                return ApiUtil.echoResult(1004, "栏目不存在或已删除", categoryId);
            }
        }
        Article info = null;
        if(id > 0) {
            if(!hasPermit(request, "modify")) return ApiUtil.echoResult(9403, null, null);
            info = articleService.info(id);
            if(null == info) return ApiUtil.echoResult(404, null, id);
        } else {
            if(!hasPermit(request, "add")) return ApiUtil.echoResult(9403, null, null);
            info = new Article();
        }
        info.setTitle(title);
        info.setCategoryId(categoryId);
        info.setFromName(DPUtil.trim(DPUtil.parseString(param.get("fromName"))));
        info.setFromUrl(DPUtil.trim(DPUtil.parseString(param.get("fromUrl"))));
        info.setAuthor(DPUtil.trim(DPUtil.parseString(param.get("author"))));
        info.setThumbUrl(DPUtil.trim(DPUtil.parseString(param.get("thumbUrl"))));
        info.setUrl(DPUtil.trim(DPUtil.parseString(param.get("url"))));
        info.setTarget(DPUtil.trim(DPUtil.parseString(param.get("target"))));
        info.setCommentEnable(DPUtil.parseBoolean(param.get("commentEnable")) ? 1 : 0);
        info.setSort(DPUtil.parseLong(param.get("sort")));
        info.setStatus(status);
        info.setPublishTime(DPUtil.parseLong(param.get("publishTime")));
        info.setKeywords(DPUtil.parseString(param.get("keywords")));
        info.setDescription(DPUtil.parseString(param.get("description")));
        info.setContent(DPUtil.parseString(param.get("content")));
        info = articleService.save(info, uid(request));
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
        boolean result = articleService.delete(ids, uid(request));
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

    @RequestMapping("/config")
    @Permission("")
    public String configAction(ModelMap model) {
        model.put("status", articleService.status("default"));
        model.put("categories", categoryService.tree());
        return ApiUtil.echoResult(0, null, model);
    }

}
