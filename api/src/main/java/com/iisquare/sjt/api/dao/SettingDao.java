package com.iisquare.sjt.api.dao;

import com.iisquare.sjt.api.mvc.DaoBase;
import com.iisquare.sjt.api.domain.Setting;

public interface SettingDao extends DaoBase<Setting, Integer> {

    Setting findFirstByTypeAndName(String type, String name);

}
