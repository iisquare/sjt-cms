package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.mvc.ServiceBase;
import com.iisquare.sjt.api.dao.SettingsDao;
import com.iisquare.sjt.api.domain.Settings;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SettingsService extends ServiceBase {

    @Autowired
    private SettingsDao settingsDao;
    @Autowired
    private UserService userService;

    public boolean set(String type, String key, String value) {
        Settings info = settingsDao.findFirstByTypeAndName(type, key);
        if(null == info) return false;
        info.setContent(value);
        return null != save(info, 0);
    }

    public String get(String type, String key) {
        Settings info = settingsDao.findFirstByTypeAndName(type, key);
        if(null == info) return "";
        String value = info.getContent();
        if(null == value) return "";
        return value;
    }

    public Settings info(Integer id) {
        if(null == id || id < 1) return null;
        return settingsDao.findById(id).get();
    }

    public Settings save(Settings info, int uid) {
        long time = System.currentTimeMillis();
        info.setUpdatedTime(time);
        info.setUpdatedUid(uid);
        return settingsDao.save(info);
    }

    public Map<?, ?> search(Map<?, ?> param, Map<?, ?> config) {
        Map<String, Object> result = new LinkedHashMap<>();
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 500, 15);
        Page<?> data = settingsDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
                if(!DPUtil.empty(name)) {
                    predicates.add(cb.like(root.get("name"), "%" + name + "%"));
                }
                String type = DPUtil.trim(DPUtil.parseString(param.get("type")));
                if(!DPUtil.empty(type)) {
                    predicates.add(cb.equal(root.get("type"), type));
                }
                String content = DPUtil.trim(DPUtil.parseString(param.get("content")));
                if(!DPUtil.empty(content)) {
                    predicates.add(cb.like(root.get("content"), "%" + content + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("sort"))));
        List<?> rows = data.getContent();
        if(!DPUtil.empty(config.get("withUserInfo"))) {
            userService.fillInfo(rows, "createdUid", "updatedUid");
        }
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("total", data.getTotalElements());
        result.put("rows", rows);
        return result;
    }

    public boolean delete(List<Integer> ids) {
        if(null == ids || ids.size() < 1) return false;
        settingsDao.deleteInBatch(settingsDao.findAllById(ids));
        return true;
    }

}
