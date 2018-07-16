package com.iisquare.sjt.api.dao;

import com.iisquare.sjt.api.mvc.DaoBase;
import com.iisquare.sjt.api.domain.Settings;

public interface SettingsDao extends DaoBase<Settings, Integer> {

    Settings findFirstByTypeAndName(String type, String name);

}
