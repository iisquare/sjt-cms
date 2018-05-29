package com.iisquare.sjt.cms.service;

import com.iisquare.sjt.cms.core.ServiceBase;
import com.iisquare.sjt.cms.dao.UserDao;
import com.iisquare.sjt.cms.domain.User;
import com.iisquare.sjt.cms.utils.DPUtil;
import com.iisquare.sjt.cms.utils.ReflectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService extends ServiceBase {

    @Autowired
    private UserDao userDao;

    public List<?> fillInfo(List<?> list, String ...properties) {
        if(null == list || list.size() < 1 || properties.length < 1) return list;
        Set<Integer> ids = new HashSet<>();
        for (Object item : list) {
            for (String property : properties) {
                int id = DPUtil.parseInt(ReflectUtil.getPropertyValue(item, property));
                if(id < 1) continue;
                ids.add(DPUtil.parseInt(id));
            }
        }
        if(ids.size() < 1) return list;
        List<User> users = userDao.findAllById(ids);
        Map<Integer, User> map = new HashMap<>();
        for (User item : users) {
            map.put(item.getId(), item);
        }
        if(users.size() < 1) return list;
        for (Object item : list) {
            for (String property : properties) {
                User user = map.get(ReflectUtil.getPropertyValue(item, property));
                if(null == user) continue;
                ReflectUtil.setPropertyValue(item, property + "Name", new Class[]{String.class}, new Object[]{user.getName()});
            }
        }
        return list;
    }

    public User current() {
        return User.builder().id(1).build();
    }

}
