package com.iisquare.sjt.cms.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class ApiUtil {

    public static String echoResult(int code, String message, Object data) {
        return DPUtil.stringify(result(code, message, data));
    }

    public static Map<?, ?> result(int code, String message, Object data) {
        if(null == message) {
            switch (code) {
                case 0:
                    message = "操作成功";
                    break;
                case 403:
                    message = "禁止访问";
                    break;
                case 404:
                    message = "信息不存在";
                    break;
                case 500:
                    message = "操作失败";
                    break;
            }
        }
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

}
