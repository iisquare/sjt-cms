package com.iisquare.sjt.cms.controller;

import com.iisquare.sjt.cms.core.ControllerBase;
import com.iisquare.sjt.cms.domain.Test;
import com.iisquare.sjt.cms.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController extends ControllerBase {

    @Autowired
    private TestService testService;

    @GetMapping("/")
    public String indexAction(Model model) {
        Test info = testService.findByName("test");
        model.addAttribute("info", info);
        return "index/index";
    }

    @RequestMapping("list")
    public String listAction() {
        return null;
    }

    @PostMapping("save")
    public String saveAction() {
        return null;
    }

    @PostMapping("delete")
    public String deleteAction() {
        return null;
    }

}
