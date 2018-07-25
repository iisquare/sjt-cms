package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.dao.ArticleDao;
import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.mvc.ServiceBase;
import com.iisquare.sjt.api.util.ServiceUtil;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ReflectUtil;
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
import java.util.*;

@Service
public class ArticleService extends ServiceBase {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;
    @Value("${custom.cms.web}")
    private String cmsWeb;

    public Map<?, ?> status(String level) {
        Map<Integer, String> status = new LinkedHashMap<>();
        status.put(1, "上架");
        status.put(2, "下架");
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

    public List<?> fillInfo(List<?> list, String ...properties) {
        if(null == list || list.size() < 1 || properties.length < 1) return list;
        Set<Integer> ids = ServiceUtil.getPropertyValues(list, Integer.class, properties);
        if(ids.size() < 1) return list;
        Map<Integer, Article> map = ServiceUtil.indexObjectList(articleDao.findAllById(ids), Integer.class, Article.class, "id");
        if(map.size() < 1) return list;
        for (Object item : list) {
            for (String property : properties) {
                Article info = map.get(ReflectUtil.getPropertyValue(item, property));
                if(null == info) continue;
                ReflectUtil.setPropertyValue(item, property + "Title", null, new Object[]{info.getTitle()});
            }
        }
        return list;
    }

    public Article info(Integer id) {
        if(null == id || id < 1) return null;
        return articleDao.findById(id).get();
    }

    public Article save(Article info, int uid) {
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
        return articleDao.save(info);
    }

    public Map<String, Object> search(Map<?, ?> param, Map<?, ?> config) {
        Map<String, Object> result = new LinkedHashMap<>();
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 500, 15);
        Page<Article> data = articleDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                Object status = param.get("status");
                if(null != status && status instanceof Integer) {
                    predicates.add(cb.equal(root.get("status"), DPUtil.parseInt(status)));
                } else if(!DPUtil.empty(status) && status instanceof Collection) {
                    predicates.add(root.get("status").in(DPUtil.parseIntSet((Collection) status)));
                } else {
                    predicates.add(cb.notEqual(root.get("status"), -1));
                }
                String title = DPUtil.trim(DPUtil.parseString(param.get("title")));
                if(!DPUtil.empty(title)) {
                    predicates.add(cb.like(root.get("title"), "%" + title + "%"));
                }
                int categoryId = DPUtil.parseInt(param.get("categoryId"));
                if(!"".equals(DPUtil.parseString(param.get("categoryId")))) {
                    predicates.add(cb.equal(root.get("categoryId"), categoryId));
                }
                String fromName = DPUtil.trim(DPUtil.parseString(param.get("fromName")));
                if(!DPUtil.empty(fromName)) {
                    predicates.add(cb.equal(root.get("fromName"), fromName));
                }
                String author = DPUtil.trim(DPUtil.parseString(param.get("author")));
                if(!DPUtil.empty(author)) {
                    predicates.add(cb.equal(root.get("author"), author));
                }
                String description = DPUtil.trim(DPUtil.parseString(param.get("description")));
                if(!DPUtil.empty(description)) {
                    predicates.add(cb.like(root.get("description"), "%" + description + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("sort"))));
        List<Article> rows = data.getContent();
        if(!DPUtil.empty(config.get("withUserInfo"))) {
            userService.fillInfo(rows, "createdUid", "updatedUid");
        }
        if(!DPUtil.empty(config.get("withStatusText"))) {
            ServiceUtil.fillProperties(rows, new String[]{"status"}, new String[]{"statusText"}, status("full"));
        }
        if(!DPUtil.empty(config.get("withParentInfo"))) {
            categoryService.fillInfo(rows, "categoryId");
        }
        for (Article info : rows) {
            String url = info.getUrl();
            if(DPUtil.empty(url)) {
                url = cmsWeb + "/news-" + info.getCategoryId() + "-" + info.getId() + ".shtml";
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
        articleDao.deleteInBatch(articleDao.findAllById(ids));
        return true;
    }

    public boolean delete(List<Integer> ids, int uid) {
        if(null == ids || ids.size() < 1) return false;
        List<Article> list = articleDao.findAllById(ids);
        long time = System.currentTimeMillis();
        for (Article item : list) {
            item.setStatus(-1);
            item.setUpdatedTime(time);
            item.setUpdatedUid(uid);
        }
        articleDao.saveAll(list);
        return true;
    }
}
