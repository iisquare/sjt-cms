package com.iisquare.sjt.cms.wap.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.domain.Category;
import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.api.service.CommentService;
import com.iisquare.sjt.api.service.MenuService;
import com.iisquare.sjt.cms.wap.mvc.WapController;
import com.iisquare.sjt.core.util.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ArticleController extends WapController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/news-{categoryId}-{articleId}.shtml")
    public String indexAction(
        @PathVariable("categoryId") Integer categoryId,
        @PathVariable("articleId") Integer articleId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Category parent = categoryService.info(categoryId);
        if(null == parent || 1 != parent.getStatus()) return error(model, request, response, 404);
        Article info = articleService.info(articleId);
        if(null == info || 1 != info.getStatus() || info.getCategoryId() != parent.getId()) return error(model, request, response, 404);
        model.put("info", info);
        model.put("sectionComm", settingService.get("cmsSectionComm"));
        model.put("sectionArticle", settingService.get("cmsSectionArticle"));
        model.put("menu", menuService.tree(DPUtil.parseInt(settingService.get("system", "cmsMenuParentId"))));
        model.put("category", categoryService.tree(0, false));
        Map<String, String> seo = new HashMap<>();
        seo.put("title", info.getTitle() + " - " + parent.getName() + " - " + settingService.get("cms", "siteName"));
        seo.put("keywords", info.getKeywords());
        seo.put("description", info.getDescription());
        model.put("page", seo);
        model.put("recommand", articleService.recommand(Arrays.asList(info.getId())));
        if(DPUtil.parseBoolean(info.getCommentEnable())) model.put("comment", commentService.simple(info.getId()));
        return displayTemplate(model, request, "article", "index");
    }

}
