package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.api.domain.Upload;
import com.iisquare.sjt.api.service.UploadService;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.FileUtil;
import com.iisquare.sjt.manage.mvc.Permission;
import com.iisquare.sjt.manage.mvc.PermitController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class UploadController extends PermitController {

    @Autowired
    private UploadService uploadService;
    @Value("${custom.uploads.path}")
    private String uploadsPath;

    @RequestMapping("/list")
    @Permission("")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = uploadService.search(param, DPUtil.buildMap(
            "withUserInfo", true
        ));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    @Permission("add")
    public String saveAction(HttpServletRequest request) {
        Map<String, Object> result = uploadService.upload(request, uid(request));
        return ApiUtil.echoResult(result);
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
        if(ids.size() != 1) {
            return ApiUtil.echoResult(1001, "当前仅支持单个插件操作", ids);
        }
        Upload info = uploadService.info(ids.get(0));
        if(null == info || -1 == info.getStatus()) return ApiUtil.echoResult(404, null, info);
        String filepath = uploadsPath + File.separator + info.getPath();
        if(!FileUtil.delete(filepath, false)) {
            return ApiUtil.echoResult(5001, "文件删除失败", info.getPath());
        }
        boolean result = uploadService.delete(ids, uid(request));
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

    @RequestMapping("/config")
    @Permission("")
    public String configAction(ModelMap model) {
        model.put("types", uploadService.typeMap());
        return ApiUtil.echoResult(0, null, model);
    }

}
