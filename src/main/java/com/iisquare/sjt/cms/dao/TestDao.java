package com.iisquare.sjt.cms.dao;

import com.iisquare.sjt.cms.core.DaoBase;
import com.iisquare.sjt.cms.domain.Test;

public interface TestDao<T extends Test, ID extends Integer> extends DaoBase<T, ID> {

    T findByName(String name);

}
