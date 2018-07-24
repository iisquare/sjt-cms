package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.api.domain.Upload;
import com.iisquare.sjt.api.service.UploadService;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.FileUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import com.iisquare.sjt.manage.mvc.Permission;
import com.iisquare.sjt.manage.mvc.PermitController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
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
        MultipartHttpServletRequest multiRequest = new StandardMultipartHttpServletRequest(request);
        Iterator iterator = multiRequest.getFileNames();
        if(!iterator.hasNext()) {
            return ApiUtil.echoResult(1002, "请选择上传文件", null);
        }
        MultipartFile file = multiRequest.getFile(iterator.next().toString());
        if(null == file) {
            return ApiUtil.echoResult(1003, "获取文件句柄失败", null);
        }
        Map<String, Map<String, String>> typeMap = uploadService.typeMap();
        String type = request.getParameter("type");
        if(!typeMap.containsKey(type)) {
            return ApiUtil.echoResult(1004, "文件类型不支持", type);
        }
        String contentType = file.getContentType().toLowerCase();
        if(!typeMap.get(type).containsKey(contentType)) {
            return ApiUtil.echoResult(1005, "非法类型文件", contentType);
        }
        String path = type + "/" + DPUtil.random(6) + System.currentTimeMillis() + "." + typeMap.get(type).get(contentType);
        String filepath = uploadsPath + File.separator + path;
        String fileurl = uploadService.url(path);
        Upload info = Upload.builder().name(file.getOriginalFilename())
            .type(type).contentType(contentType).path(path).url(fileurl).status(1).build();
        info = uploadService.save(info, uid(request));
        if(null == info) {
            return ApiUtil.echoResult(1006, "生成记录失败", info);
        }
        try {
            FileUtil.mkdirs(uploadsPath + File.separator + type);
            file.transferTo(new File(filepath).getAbsoluteFile());
        } catch (IOException e) {
            return ApiUtil.echoResult(1007, "写入文件失败", e.getMessage());
        }
        return ApiUtil.echoResult(0, null, info);
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
