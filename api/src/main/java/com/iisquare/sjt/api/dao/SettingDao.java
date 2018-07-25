package com.iisquare.sjt.api.dao;

import com.iisquare.sjt.api.mvc.DaoBase;
import com.iisquare.sjt.api.domain.Setting;

import java.util.List;

public interface SettingDao extends DaoBase<Setting, Integer> {

    Setting findFirstByTypeAndName(String type, String name);

    List<Setting> findAllByType(String type);

}
