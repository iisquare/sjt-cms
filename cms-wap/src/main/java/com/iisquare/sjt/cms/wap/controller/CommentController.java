package com.iisquare.sjt.cms.wap.controller;

import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.domain.Comment;
import com.iisquare.sjt.api.service.ArticleService;
import com.iisquare.sjt.api.service.CommentService;
import com.iisquare.sjt.api.util.ServletUtil;
import com.iisquare.sjt.cms.wap.mvc.WapController;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController extends WapController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private ArticleService articleService;

    @PostMapping("/add")
    @ResponseBody
    public String addAction(@RequestParam Map<?, ?> param, HttpServletRequest request) {
        int uid = uid(request);
        if(uid < 1) return ApiUtil.echoResult(403, "请登录后再进行操作", null);
        String content = DPUtil.trim(DPUtil.parseString(param.get("content")));
        if(DPUtil.empty(content)) return ApiUtil.echoResult(1001, "请输入内容", content);
        int articleId = DPUtil.parseInt(param.get("articleId"));
        int parentId = DPUtil.parseInt(param.get("parentId"));
        Comment parentInfo = commentService.info(parentId);
        Article articleInfo = articleService.info(articleId);
        if(null == articleInfo || 1 != articleInfo.getStatus()) {
            return ApiUtil.echoResult(1003, "文章不存在或已被删除", articleInfo);
        }
        if(System.currentTimeMillis() - commentService.lastTime(uid) < 120000) {
            return ApiUtil.echoResult(1103, "操作过于频繁，请稍后再试", null);
        }
        Comment info = new Comment();
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
        info.setCreatedIp(ServletUtil.getRemoteAddr(request));
        int status = DPUtil.parseInt(settingService.get("cms", "defaultCommentStatus"));
        if(!commentService.status("default").containsKey(status)) status = 2;
        info.setStatus(status);
        info = commentService.save(info, uid);
        if(null == info) return ApiUtil.echoResult(500, "评论出错了，请稍后再试", null);
        return ApiUtil.echoResult(0, "评论成功，审核成功后方可显示", null);
    }

}
