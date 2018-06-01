package com.iisquare.sjt.cms.dao;

import com.iisquare.sjt.cms.core.DaoBase;
import com.iisquare.sjt.cms.domain.Relation;

import java.util.List;

public interface RelationDao extends DaoBase<Relation, String> {

    List<Relation> findAllByTypeAndAid(String type, Integer aid);

}
