package com.iisquare.sjt.manage.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.iisquare.sjt.api.domain.Upload;
import com.iisquare.sjt.api.service.UploadService;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.FileUtil;
import com.iisquare.sjt.manage.mvc.PermitController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/ueditor")
public class UeditorController extends PermitController {

    @Autowired
    private UploadService uploadService;

    @RequestMapping("")
    public String indexAction(Map<?, ?> param, HttpServletRequest request) {
        String action = DPUtil.parseString(param.get("action"));
        if(DPUtil.empty(action)) action = request.getParameter("action");
        switch (action) {
            case "config":
                String json = FileUtil.getContent(
                    getClass().getClassLoader().getResource("ueditor.config.json"), true, "utf-8"
                );
                json = json.replaceAll("\\/\\*[\\s\\S]+?\\*\\/", "");
                JsonNode config = DPUtil.parseJSON(json);
                return DPUtil.stringify(config);
            case "uploadimage":
                request.setAttribute("type", "image");
                Map<String, Object> data = uploadService.upload(request, uid(request));
                Map<String, Object> result = new LinkedHashMap<>();
                if(0 == DPUtil.parseInt(data.get("code"))) {
                    result.put("state", "SUCCESS");
                    Upload info = (Upload) data.get("data");
                    result.put("url", info.getUrl());
                    result.put("title", info.getPath());
                    result.put("original", info.getName());
                    result.put("type", info.getContentType());
                    result.put("size", info.getSize());
                } else {
                    result.put("state", data.get("message"));
                }
                return ApiUtil.echoResult(result);
            default:
                return DPUtil.stringify(DPUtil.objectNode().put("state", "暂不支持该操作"));
        }
    }

}
