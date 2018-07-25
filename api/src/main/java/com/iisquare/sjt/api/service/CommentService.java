package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.dao.CommentDao;
import com.iisquare.sjt.api.domain.Comment;
import com.iisquare.sjt.api.mvc.ServiceBase;
import com.iisquare.sjt.api.util.ServiceUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentService extends ServiceBase {

    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleService articleService;
    @Value("${custom.cms.web}")
    private String cmsWeb;

    public Map<?, ?> status(String level) {
        Map<Integer, String> status = new LinkedHashMap<>();
        status.put(1, "展示");
        status.put(2, "隐藏");
        switch (level) {
            case "default":
                break;
            case "full":
                status.put(-1, "已删除");
                break;
            default:
                return null;
        }
        return status;
    }

    public Comment info(Integer id) {
        if(null == id || id < 1) return null;
        return commentDao.findById(id).get();
    }

    public Comment save(Comment info, int uid) {
        long time = System.currentTimeMillis();
        info.setUpdatedTime(time);
        info.setUpdatedUid(uid);
        if(null == info.getId()) {
            info.setCreatedTime(time);
            info.setCreatedUid(uid);
            Long sort = info.getSort();
            if(null == sort || sort < 1) info.setSort(time);
            Long publishTime = info.getPublishTime();
            if(null == publishTime || publishTime < 1) info.setPublishTime(time);
        } else {
            Long publishTime = info.getPublishTime();
            if(null == publishTime || publishTime < 1) info.setPublishTime(info.getCreatedTime());
            Long sort = info.getSort();
            if(null == sort || sort < 1) info.setSort(info.getPublishTime());
        }
        return commentDao.save(info);
    }

    public Map<?, ?> search(Map<?, ?> param, Map<?, ?> config) {
        Map<String, Object> result = new LinkedHashMap<>();
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 500, 15);
        Page<Comment> data = commentDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("status"), -1));
                String content = DPUtil.trim(DPUtil.parseString(param.get("content")));
                if(!DPUtil.empty(content)) {
                    predicates.add(cb.like(root.get("content"), "%" + content + "%"));
                }
                int categoryId = DPUtil.parseInt(param.get("categoryId"));
                if(!"".equals(DPUtil.parseString(param.get("categoryId")))) {
                    predicates.add(cb.equal(root.get("categoryId"), categoryId));
                }
                int articleId = DPUtil.parseInt(param.get("articleId"));
                if(!"".equals(DPUtil.parseString(param.get("articleId")))) {
                    predicates.add(cb.equal(root.get("articleId"), articleId));
                }
                int parentId = DPUtil.parseInt(param.get("parentId"));
                if(!"".equals(DPUtil.parseString(param.get("parentId")))) {
                    predicates.add(cb.equal(root.get("parentId"), parentId));
                }
                int parentUid = DPUtil.parseInt(param.get("parentUid"));
                if(!"".equals(DPUtil.parseString(param.get("parentUid")))) {
                    predicates.add(cb.equal(root.get("parentUid"), parentUid));
                }
                int topId = DPUtil.parseInt(param.get("topId"));
                if(!"".equals(DPUtil.parseString(param.get("topId")))) {
                    predicates.add(cb.equal(root.get("topId"), topId));
                }
                String createdIp = DPUtil.trim(DPUtil.parseString(param.get("createdIp")));
                if(!DPUtil.empty(createdIp)) {
                    predicates.add(cb.equal(root.get("createdIp"), createdIp));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.asc("sort"))));
        List<Comment> rows = data.getContent();
        if(!DPUtil.empty(config.get("withUserInfo"))) {
            userService.fillInfo(rows, "createdUid", "updatedUid", "parentUid");
        }
        if(!DPUtil.empty(config.get("withStatusText"))) {
            ServiceUtil.fillProperties(rows, new String[]{"status"}, new String[]{"statusText"}, status("full"));
        }
        if(!DPUtil.empty(config.get("withParentInfo"))) {
            categoryService.fillInfo(rows, "categoryId");
            articleService.fillInfo(rows, "articleId");
        }
        for (Comment info : rows) {
            String url = info.getUrl();
            if(DPUtil.empty(url)) {
                url = cmsWeb + "/news-" + info.getCategoryId() + "-" + info.getArticleId() + ".shtml";
                info.setUrl(url);
            }
        }
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("total", data.getTotalElements());
        result.put("rows", rows);
        return result;
    }

    public boolean remove(List<Integer> ids) {
        if(null == ids || ids.size() < 1) return false;
        commentDao.deleteInBatch(commentDao.findAllById(ids));
        return true;
    }

    public boolean delete(List<Integer> ids, int uid) {
        if(null == ids || ids.size() < 1) return false;
        List<Comment> list = commentDao.findAllById(ids);
        long time = System.currentTimeMillis();
        for (Comment item : list) {
            item.setStatus(-1);
            item.setUpdatedTime(time);
            item.setUpdatedUid(uid);
        }
        commentDao.saveAll(list);
        return true;
    }
}