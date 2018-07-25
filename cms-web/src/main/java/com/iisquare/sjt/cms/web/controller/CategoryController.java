package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.cms.web.mvc.WebController;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class CategoryController extends WebController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/news-{categoryId}-{page}.shtml")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request, "category", "index");
    }

    @RequestMapping("/category/list")
    @ResponseBody
    public String listAction(@RequestParam Map<?, ?> param) {
        Integer categoryId = ValidateUtil.filterInteger(param.get("id"), true, 1, null, null);
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 100, 20);
        Map<String, Object> args = new LinkedHashMap<>();
        args.put("categoryId", categoryId);
        args.put("page", page);
        args.put("pageSize", pageSize);
        args.put("status", 1);
        Map<String, Object> result = articleService.search(args, DPUtil.buildMap("withParentInfo", true));
        List<Map<String, Object>> data = new ArrayList<>();
        for (Article info : (List<Article>) result.get("rows")) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("id", info.getId());
            item.put("title", info.getTitle());
            item.put("categoryId", info.getCategoryId());
            item.put("categoryIdName", info.getCategoryIdName());
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
