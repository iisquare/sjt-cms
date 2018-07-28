package com.iisquare.sjt.cms.wap.controller;

import com.iisquare.sjt.api.domain.Category;
import com.iisquare.sjt.api.service.CategoryService;
import com.iisquare.sjt.api.service.MenuService;
import com.iisquare.sjt.cms.wap.mvc.WapController;
import com.iisquare.sjt.core.util.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController extends WapController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        model.put("sectionWap", settingService.get("cmsSectionWap"));
        List<Category> categories = categoryService.tree(0, false);
        List<Category> menus = new ArrayList<>();
        menus.add(Category.builder().id(0).name("全部").url("/").build());
        int count = categories.size();
        if(count > 6) count = 6;
        for (int i = 0; i < count; i++) {
            menus.add(categories.get(i));
        }
        model.put("menu", menus);
        model.put("category", categories);
        return displayTemplate(model, request, "index", "index");
    }

}
