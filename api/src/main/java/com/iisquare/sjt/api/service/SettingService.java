package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.mvc.ServiceBase;
import com.iisquare.sjt.api.dao.SettingDao;
import com.iisquare.sjt.api.domain.Setting;
import com.iisquare.sjt.core.util.DPUtil;
import com.iisquare.sjt.core.util.ValidateUtil;
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
public class SettingService extends ServiceBase {

    @Autowired
    private SettingDao settingDao;
    @Autowired
    private UserService userService;

    public boolean set(String type, String key, String value) {
        Setting info = settingDao.findFirstByTypeAndName(type, key);
        if(null == info) return false;
        info.setContent(value);
        return null != save(info, 0);
    }

    public String get(String type, String key) {
        Setting info = settingDao.findFirstByTypeAndName(type, key);
        if(null == info) return "";
        String value = info.getContent();
        if(null == value) return "";
        return value;
    }

    public Map<String, String> get(String type) {
        Map<String, String> result = new LinkedHashMap<>();
        List<Setting> list = settingDao.findAllByType(type);
        for (Setting info : list) {
            String key = info.getName();
            String value = info.getContent();
            if(null == value) value = "";
            result.put(key, value);
        }
        return result;
    }

    public Setting info(Integer id) {
        if(null == id || id < 1) return null;
        return settingDao.findById(id).get();
    }

    public Setting save(Setting info, int uid) {
        long time = System.currentTimeMillis();
        info.setUpdatedTime(time);
        info.setUpdatedUid(uid);
        return settingDao.save(info);
    }

    public Map<?, ?> search(Map<?, ?> param, Map<?, ?> config) {
        Map<String, Object> result = new LinkedHashMap<>();
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 500, 15);
        Page<?> data = settingDao.findAll(new Specification() {
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
                String description = DPUtil.trim(DPUtil.parseString(param.get("description")));
                if(!DPUtil.empty(description)) {
                    predicates.add(cb.like(root.get("description"), "%" + description + "%"));
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
        settingDao.deleteInBatch(settingDao.findAllById(ids));
        return true;
    }

}
