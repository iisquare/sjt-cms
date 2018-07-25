package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.dao.CategoryDao;
import com.iisquare.sjt.api.domain.Category;
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
public class CategoryService extends ServiceBase {

    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private UserService userService;
    @Value("${custom.cms.web}")
    private String cmsWeb;

    public Map<?, ?> status(String level) {
        Map<Integer, String> status = new LinkedHashMap<>();
        status.put(1, "启用");
        status.put(2, "关闭");
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

    public Category info(Integer id) {
        if(null == id || id < 1) return null;
        return categoryDao.findById(id).get();
    }

    public Category save(Category info, int uid) {
        long time = System.currentTimeMillis();
        info.setUpdatedTime(time);
        info.setUpdatedUid(uid);
        if(null == info.getId()) {
            info.setCreatedTime(time);
            info.setCreatedUid(uid);
        }
        return categoryDao.save(info);
    }

    public List<?> fillInfo(List<?> list, String ...properties) {
        if(null == list || list.size() < 1 || properties.length < 1) return list;
        Set<Integer> ids = ServiceUtil.getPropertyValues(list, Integer.class, properties);
        if(ids.size() < 1) return list;
        Map<Integer, Category> map = ServiceUtil.indexObjectList(categoryDao.findAllById(ids), Integer.class, Category.class, "id");
        if(map.size() < 1) return list;
        for (Object item : list) {
            for (String property : properties) {
                Category info = map.get(ReflectUtil.getPropertyValue(item, property));
                if(null == info) continue;
                ReflectUtil.setPropertyValue(item, property + "Name", null, new Object[]{info.getName()});
            }
        }
        return list;
    }

    public List<Category> tree(int parentId, boolean format) {
        List<Category> data = categoryDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("status"), 1));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, Sort.by(Sort.Order.desc("sort")));
        for (Category info : data) {
            String url = info.getUrl();
            if(DPUtil.empty(url)) {
                url = cmsWeb + "/columns-" + info.getId() + "-1.shtml";
                info.setUrl(url);
            }
        }
        if(!format) return data;
        return ServiceUtil.formatRelation(data, Category.class, "parentId", parentId, "id", "children");
    }

    public List<Category> tree() {
        List<Category> data = categoryDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("status"), -1));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, Sort.by(Sort.Order.desc("sort")));
        return ServiceUtil.formatRelation(data, Category.class, "parentId", 0, "id", "children");
    }

    public Map<?, ?> search(Map<?, ?> param, Map<?, ?> config) {
        Map<String, Object> result = new LinkedHashMap<>();
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 500, 15);
        Page<Category> data = categoryDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("status"), -1));
                String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
                if(!DPUtil.empty(name)) {
                    predicates.add(cb.like(root.get("name"), "%" + name + "%"));
                }
                int parentId = DPUtil.parseInt(param.get("parentId"));
                if(!"".equals(DPUtil.parseString(param.get("parentId")))) {
                    predicates.add(cb.equal(root.get("parentId"), parentId));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("sort"))));
        List<Category> rows = data.getContent();
        if(!DPUtil.empty(config.get("withUserInfo"))) {
            userService.fillInfo(rows, "createdUid", "updatedUid");
        }
        if(!DPUtil.empty(config.get("withStatusText"))) {
            ServiceUtil.fillProperties(rows, new String[]{"status"}, new String[]{"statusText"}, status("full"));
        }
        if(!DPUtil.empty(config.get("withParentInfo"))) {
            this.fillInfo(rows, "parentId");
        }
        for (Category info : rows) {
            String url = info.getUrl();
            if(DPUtil.empty(url)) {
                url = cmsWeb + "/columns-" + info.getId() + "-1.shtml";
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
        categoryDao.deleteInBatch(categoryDao.findAllById(ids));
        return true;
    }

    public boolean delete(List<Integer> ids, int uid) {
        if(null == ids || ids.size() < 1) return false;
        List<Category> list = categoryDao.findAllById(ids);
        long time = System.currentTimeMillis();
        for (Category item : list) {
            item.setStatus(-1);
            item.setUpdatedTime(time);
            item.setUpdatedUid(uid);
        }
        categoryDao.saveAll(list);
        return true;
    }
}
