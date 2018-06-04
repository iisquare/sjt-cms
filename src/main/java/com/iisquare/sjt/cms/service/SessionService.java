package com.iisquare.sjt.cms.service;

import com.iisquare.sjt.cms.core.ServiceBase;
import com.iisquare.sjt.cms.dao.MenuDao;
import com.iisquare.sjt.cms.dao.RelationDao;
import com.iisquare.sjt.cms.dao.ResourceDao;
import com.iisquare.sjt.cms.domain.Menu;
import com.iisquare.sjt.cms.utils.DPUtil;
import com.iisquare.sjt.cms.utils.ServiceUtil;
import com.iisquare.sjt.cms.utils.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SessionService extends ServiceBase {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private ResourceDao resourceDao;
    @Autowired
    private RelationDao relationDao;

    public Map<String, Object> currentInfo(HttpServletRequest request, Map<?, ?> info) {
        Map<String, Object> result = ServletUtil.getSessionMap(request);
        if(null == info) return result;
        for (Map.Entry<?, ?> entry : info.entrySet()) {
            result.put(entry.getKey().toString(), entry.getValue());
        }
        ServletUtil.setSession(request, result);
        return result;
    }

    public List<Menu> menu(Map<?, ?> info, Integer parentId) {
        int uid = DPUtil.parseInt(info.get("uid"));
        if(uid < 1) return new ArrayList<>();
        Set<Integer> roleIds = ServiceUtil.getPropertyValues(relationDao.findAllByTypeAndAid("user_role", uid), Integer.class, "bid");
        if(roleIds.size() < 1) return new ArrayList<>();
        Set<Integer> menuIds = ServiceUtil.getPropertyValues(relationDao.findAllByTypeAndAidIn("role_menu", roleIds), Integer.class, "bid");
        if(menuIds.size() < 1) return new ArrayList<>();
        List<Menu> data = menuDao.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("status"), 1));
                predicates.add(root.get("id").in(menuIds));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }, Sort.by(Sort.Order.desc("sort")));
        return ServiceUtil.formatRelation(data, Menu.class, "parentId", parentId, "id", "children");
    }

}
