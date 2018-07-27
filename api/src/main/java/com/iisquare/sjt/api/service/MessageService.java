package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.dao.ArticleDao;
import com.iisquare.sjt.api.domain.Article;
import com.iisquare.sjt.api.mvc.ServiceBase;
import com.iisquare.sjt.api.util.ServiceUtil;
import com.iisquare.sjt.core.util.ApiUtil;
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
public class MessageService extends ServiceBase {

    @Autowired
    private SettingService settingService;

    public Map<String, Object> sendCode(String mobile, String code, Object data) {
        System.out.println(mobile + "->" + code);
        return ApiUtil.result(0, "短信验证码发送成功", data);
    }

}
