package com.iisquare.sjt.cms.service;

import com.iisquare.sjt.cms.core.ServiceBase;
import com.iisquare.sjt.cms.dao.RelationDao;
import com.iisquare.sjt.cms.domain.Relation;
import com.iisquare.sjt.cms.utils.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RelationService extends ServiceBase {

    @Autowired
    private RelationDao relationDao;

    public Set<Integer> relationIds(String type, Integer aid, Set<Integer> bids) {
        List<Relation> list = relationDao.findAllByTypeAndAid(type, aid);
        if(null == bids) {
            return ServiceUtil.getPropertyValues(list, Integer.class, "bid");
        } else {
            relationDao.deleteAll(list);
            list = new ArrayList<>();
            for (Integer bid : bids) {
                list.add(Relation.builder().id(type + "_" + aid + "_" + bid).type(type).aid(aid).bid(bid).build());
            }
            relationDao.saveAll(list);
            return bids;
        }
    }

}
