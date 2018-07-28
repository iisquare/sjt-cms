package com.iisquare.sjt.cms.wap.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.domain.Category;
import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.api.service.MenuService;
import com.iisquare.sjt.cms.wap.mvc.WapController;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/")
public class CategoryController extends WapController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MenuService menuService;

    @GetMapping("/columns-{categoryId}-{page}.shtml")
    public String indexAction(
        @PathVariable("categoryId") Integer categoryId,
        @PathVariable("page") Integer page, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Category info = categoryService.info(categoryId);
        if(null == info || 1 != info.getStatus()) return error(model, request, response, 404);
        model.put("info", info);
        model.put("sectionComm", settingService.get("cmsSectionComm"));
        model.put("menu", menuService.tree(DPUtil.parseInt(settingService.get("system", "cmsMenuParentId"))));
        model.put("category", categoryService.tree(0, false));
        Map<String, String> seo = new HashMap<>();
        seo.put("title", info.getName() + " - " + settingService.get("cms", "siteName"));
        seo.put("keywords", info.getKeywords());
        seo.put("description", info.getDescription());
        model.put("page", seo);
        return displayTemplate(model, request, "category", "index");
    }

    @GetMapping("/search")
    public String searchAction(@RequestParam Map<?, ?> param, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        String keyword = DPUtil.trim(DPUtil.parseString(param.get("keyword")));
        model.put("keyword", keyword);
        model.put("sectionComm", settingService.get("cmsSectionComm"));
        model.put("menu", menuService.tree(DPUtil.parseInt(settingService.get("system", "cmsMenuParentId"))));
        model.put("category", categoryService.tree(0, false));
        Map<String, String> seo = new HashMap<>();
        seo.put("title", "搜索结果 - " + settingService.get("cms", "siteName"));
        model.put("page", seo);
        return displayTemplate(model, request, "category", "index");
    }

    @RequestMapping("/category/list")
    @ResponseBody
    public String listAction(@RequestParam Map<?, ?> param) {
        Integer categoryId = ValidateUtil.filterInteger(param.get("id"), true, 1, null, null);
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 100, 20);
        String keyword = DPUtil.trim(DPUtil.parseString(param.get("keyword")));
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("categoryId", categoryId);
        args.put("page", page);
        args.put("pageSize", pageSize);
        args.put("status", 1);
        args.put("keyword", keyword);
        Map<String, Object> result = articleService.search(args, DPUtil.buildMap("withParentInfo", true));
        List<Map<String, Object>> data = new ArrayList<>();
        for (Article info : (List<Article>) result.get("rows")) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", info.getId());
            item.put("title", info.getTitle());
            item.put("categoryId", info.getCategoryId());
            item.put("categoryIdName", info.getCategoryIdName());
            item.put("thumbUrl", info.getThumbUrl());
            item.put("url", info.getUrl());
            item.put("target", info.getTarget());
            item.put("description", info.getDescription());
            item.put("publishTime", info.getPublishTime());
            data.add(item);
        }
        result.put("rows", data);
        return ApiUtil.echoResult(0, null, result);
    }

}
