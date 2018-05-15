package com.iisquare.sjt.cms.service;

import com.iisquare.sjt.cms.core.ServiceBase;
import com.iisquare.sjt.cms.dao.TestDao;
import com.iisquare.sjt.cms.domain.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService extends ServiceBase {

    @Autowired
    private TestDao testDao;

    public Test findByName(String name) {
        return testDao.findByName(name);
    }

}
