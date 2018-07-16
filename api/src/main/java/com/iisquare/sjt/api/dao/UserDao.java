package com.iisquare.sjt.api.dao;

import com.iisquare.sjt.api.mvc.DaoBase;
import com.iisquare.sjt.api.domain.User;

public interface UserDao extends DaoBase<User, Integer> {

    boolean existsByNameEqualsAndIdNotIn(String name, Integer ...ids);

    boolean existsBySerial(String serial);

    User findFirstBySerial(String serial);

}
