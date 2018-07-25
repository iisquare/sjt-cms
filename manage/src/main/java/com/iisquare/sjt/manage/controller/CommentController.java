package com.iisquare.sjt.manage.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.domain.Comment;
import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.api.service.CommentService;
import com.iisquare.sjt.api.util.ServletUtil;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import com.iisquare.sjt.manage.mvc.Permission;
import com.iisquare.sjt.manage.mvc.PermitController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController extends PermitController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/info")
    @Permission("")
    public String infoAction(@RequestBody Map<?, ?> param) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        Comment info = commentService.info(id);
        if(null == info || -1 == info.getStatus()) {
            return ApiUtil.echoResult(404, null, id);
        }
        return ApiUtil.echoResult(0, null , info);
    }

    @RequestMapping("/list")
    @Permission("")
    public String listAction(@RequestBody Map<?, ?> param) {
        Map<?, ?> result = commentService.search(param, DPUtil.buildMap(
            "withUserInfo", true, "withStatusText", true, "withParentInfo", true
        ));
        return ApiUtil.echoResult(0, null, result);
    }

    @RequestMapping("/save")
    @Permission({"add", "modify"})
    public String saveAction(@RequestBody Map<?, ?> param, HttpServletRequest request) {
        Integer id = ValidateUtil.filterInteger(param.get("id"), true, 1, null, 0);
        String content = DPUtil.trim(DPUtil.parseString(param.get("content")));
        if(DPUtil.empty(content)) return ApiUtil.echoResult(1001, "请输入评论内容", content);
        int status = DPUtil.parseInt(param.get("status"));
        if(!commentService.status("default").containsKey(status)) return ApiUtil.echoResult(1002, "状态异常", status);
        Comment info = null;
        if(id > 0) {
            if(!hasPermit(request, "modify")) return ApiUtil.echoResult(9403, null, null);
            info = commentService.info(id);
            if(null == info) return ApiUtil.echoResult(404, null, id);
        } else {
            if(!hasPermit(request, "add")) return ApiUtil.echoResult(9403, null, null);
            info = new Comment();
            info.setCreatedIp(ServletUtil.getRemoteAddr(request));
        }
        int articleId = DPUtil.parseInt(param.get("articleId"));
        int parentId = DPUtil.parseInt(param.get("parentId"));
        Comment parentInfo = commentService.info(parentId);
        Article articleInfo = articleService.info(articleId);
        if(null == articleInfo || 1 != articleInfo.getStatus()) {
            return ApiUtil.echoResult(1003, "文章异常", articleInfo);
        }
        info.setContent(content);
        info.setCategoryId(articleInfo.getCategoryId());
        info.setArticleId(articleInfo.getId());
        if(null == parentInfo) {
            info.setParentId(0);
            info.setParentUid(0);
            info.setTopId(0);
        } else {
            if(articleInfo.getId() != parentInfo.getArticleId()) {
                return ApiUtil.echoResult(1004, "父级评论异常", articleInfo);
            }
            info.setParentId(parentInfo.getId());
            info.setParentUid(parentInfo.getCreatedUid());
            info.setTopId(parentInfo.getTopId() > 0 ? parentInfo.getTopId() : parentInfo.getId());
        }
        info.setSort(DPUtil.parseLong(param.get("sort")));
        info.setStatus(status);
        info.setPublishTime(DPUtil.parseLong(param.get("publishTime")));
        info = commentService.save(info, uid(request));
        return ApiUtil.echoResult(null == info ? 500 : 0, null, info);
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
        boolean result = commentService.delete(ids, uid(request));
        return ApiUtil.echoResult(result ? 0 : 500, null, result);
    }

    @RequestMapping("/config")
    @Permission("")
    public String configAction(ModelMap model) {
        model.put("status", commentService.status("default"));
        return ApiUtil.echoResult(0, null, model);
    }

}
