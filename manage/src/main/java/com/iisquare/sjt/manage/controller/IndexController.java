package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.domain.Category;
import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.manage.mvc.PermitController;
import com.iisquare.sjt.api.service.SettingService;
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

@RequestMapping("/")
@Controller
public class IndexController extends PermitController {

    @Autowired
    private SettingService settingService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/news-{categoryId}-{articleId}.shtml")
    public String indexAction(
            @PathVariable("categoryId") Integer categoryId,
            @PathVariable("articleId") Integer articleId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
        Article info = articleService.info(articleId);
        model.put("info", info);
        return displayTemplate(model, request, "article");
    }

    @RequestMapping("/")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request);
    }

}
