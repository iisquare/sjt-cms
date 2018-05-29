package com.iisquare.sjt.cms.utils;

import java.util.*;

/**
 * 通用业务辅助处理类
 * @author Ouyang <iisquare@163.com>
 *
 */
public class ServiceUtil {

	public static void fillProperties(List<?> list, String[] froms, String[] tos, Map<?, ?> ...maps) {
		if(null == list) return;
		for (Object item : list) {
			for (int i = 0; i < froms.length; i++) {
				ReflectUtil.setPropertyValue(item, tos[i], null, new Object[] {
					maps[i].get(ReflectUtil.getPropertyValue(item, froms[i]))
				});
			}
		}
	}

	/**
	 * 获取对应字段的值列表
	 */
	public static Set<?> getPropertyValues(List<?> list, String... properties) {
		Set<Object> valueList = new HashSet<>();
		for (Object object : list) {
			for (String property : properties) {
				Object value = ReflectUtil.getPropertyValue(object, property);
				if(null == value) continue;
				valueList.add(value);
			}
		}
		return valueList;
	}

	/**
	 * 将List数据格式化为以对应字段值为下标的Map
	 */
	public static Map<?, ?> indexObjectList(List<?> list, String property) {
		Map<Object, Object> map = new HashMap<>();
		for (Object item : list) {
			map.put(ReflectUtil.getPropertyValue(item, property), item);
		}
		return map;
	}

	/**
	 * 将List数据格式化为以对应字段值为下标的Map
	 */
	public static Map<?, ?> indexesObjectList(List<?> list, String property) {
		Map<Object, List<Object>> map = new HashMap<>();
		for (Object item : list) {
			Object key = ReflectUtil.getPropertyValue(item, property);
			if(null == key) continue;
			List<Object> subList = map.get(key);
			if(null == subList) {
				subList = new ArrayList<>();
			}
			subList.add(item);
			map.put(key, subList);
		}
		return map;
	}

}
