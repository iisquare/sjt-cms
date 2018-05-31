package com.iisquare.sjt.cms.dao;

import com.iisquare.sjt.cms.core.DaoBase;
import com.iisquare.sjt.cms.domain.User;

public interface UserDao extends DaoBase<User, Integer> {

    boolean existsByNameEqualsAndIdNotIn(String name, Integer ...ids);

    boolean existsBySerial(String serial);

}
