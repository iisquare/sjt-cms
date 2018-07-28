package com.iisquare.sjt.cms.wap.controller;

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

@Controller
@RequestMapping("/")
public class IndexController extends WapController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String indexAction(ModelMap model, HttpServletRequest request) {
        model.put("sectionComm", settingService.get("cmsSectionComm"));
        model.put("sectionIndex", settingService.get("cmsSectionIndex"));
        model.put("menu", menuService.tree(DPUtil.parseInt(settingService.get("system", "cmsMenuParentId"))));
        model.put("category", categoryService.tree(0, false));
        return displayTemplate(model, request, "index", "index");
    }

}
