package com.iisquare.sjt.cms.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DaoBase<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}
