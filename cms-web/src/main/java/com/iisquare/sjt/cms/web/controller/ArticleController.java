package com.iisquare.sjt.cms.web.controller;

import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.cms.web.mvc.WebController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class ArticleController extends WebController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;

    @GetMapping("/news-{categoryId}-{articleId}.shtml")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        return displayTemplate(model, request, "article", "index");
    }

}
