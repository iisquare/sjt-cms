package com.iisquare.sjt.cms.dao;

import com.iisquare.sjt.cms.core.DaoBase;
import com.iisquare.sjt.cms.domain.Relation;

import java.util.Collection;
import java.util.List;

public interface RelationDao extends DaoBase<Relation, String> {

    List<Relation> findAllByTypeAndAid(String type, Integer aid);

    List<Relation> findAllByTypeAndAidIn(String type, Collection<Integer> aids);

    List<Relation> findAllByTypeAndBidIn(String type, Collection<Integer> bids);

}
