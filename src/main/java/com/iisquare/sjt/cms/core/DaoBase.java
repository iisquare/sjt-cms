package com.iisquare.sjt.cms.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DaoBase<T, ID> extends JpaRepository<T, ID> {

}
