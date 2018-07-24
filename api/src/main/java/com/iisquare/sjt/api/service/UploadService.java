package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.dao.UploadDao;
import com.iisquare.sjt.api.domain.Upload;
import com.iisquare.sjt.api.mvc.ServiceBase;
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
import java.util.*;

@Service
public class UploadService extends ServiceBase {

    @Autowired
    private UploadDao uploadDao;
    @Autowired
    private UserService userService;
    @Value("${custom.uploads.uri}")
    private String uploadsUri;

    public Map<String, Map<String, String>> typeMap() {
        Map<String, Map<String, String>> typeMap = new LinkedHashMap<>();
        Map<String, String> contentTypeMap = new LinkedHashMap<>();
        contentTypeMap.put("image/png", "png");
        contentTypeMap.put("image/jpeg", "jpg");
        contentTypeMap.put("image/gif", "gif");
        typeMap.put("image", contentTypeMap);
        return typeMap;
    }

    public Upload info(Integer id) {
        if(null == id || id < 1) return null;
        return uploadDao.findById(id).get();
    }

    public Upload save(Upload info, int uid) {
        long time = System.currentTimeMillis();
        info.setUpdatedTime(time);
        info.setUpdatedUid(uid);
        if(null == info.getId()) {
            info.setCreatedTime(time);
            info.setCreatedUid(uid);
        }
        return uploadDao.save(info);
    }

    public String url(String path) {
        return uploadsUri + "/" + path;
    }

    public Map<?, ?> search(Map<?, ?> param, Map<?, ?> config) {
        Map<String, Object> result = new LinkedHashMap<>();
        int page = ValidateUtil.filterInteger(param.get("page"), true, 1, null, 1);
        int pageSize = ValidateUtil.filterInteger(param.get("pageSize"), true, 1, 500, 15);
        Page<Upload> data = uploadDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.notEqual(root.get("status"), -1));
                String name = DPUtil.trim(DPUtil.parseString(param.get("name")));
                if(!DPUtil.empty(name)) {
                    predicates.add(cb.like(root.get("name"), "%" + name + "%"));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.asc("name"))));
        List<Upload> rows = data.getContent();
        if(!DPUtil.empty(config.get("withUserInfo"))) {
            userService.fillInfo(rows, "createdUid", "updatedUid");
        }
        for (Upload info : rows) {
            info.setUrl(url(info.getPath()));
        }
        result.put("page", page);
        result.put("pageSize", pageSize);
        result.put("total", data.getTotalElements());
        result.put("rows", rows);
        return result;
    }

    public boolean remove(List<Integer> ids) {
        if(null == ids || ids.size() < 1) return false;
        uploadDao.deleteInBatch(uploadDao.findAllById(ids));
        return true;
    }

    public boolean delete(List<Integer> ids, int uid) {
        if(null == ids || ids.size() < 1) return false;
        List<Upload> list = uploadDao.findAllById(ids);
        long time = System.currentTimeMillis();
        for (Upload item : list) {
            item.setStatus(-1);
            item.setUpdatedTime(time);
            item.setUpdatedUid(uid);
        }
        uploadDao.saveAll(list);
        return true;
    }
}
