package com.iisquare.sjt.api.service;

import com.iisquare.sjt.api.mvc.ServiceBase;
import com.iisquare.sjt.core.util.ApiUtil;
import com.iisquare.sjt.core.util.DPUtil;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MessageService extends ServiceBase {

    @Autowired
    private SettingService settingService;

    public Map<String, Object> sendCode(String mobile, String code, Object data) {
        Map<String, String> sms = settingService.get("sms");
        String apiUrl = DPUtil.trim(DPUtil.parseString(sms.get("apiUrl")));
        String apiUser = DPUtil.trim(DPUtil.parseString(sms.get("apiUser")));
        String apiKey = DPUtil.trim(DPUtil.parseString(sms.get("apiKey")));
        String content = DPUtil.trim(DPUtil.parseString(sms.get("sendCode")));
        if(DPUtil.empty(content)) content = "{code}";
        content = content.replaceAll("\\{code\\}", code);
        if(DPUtil.empty(apiUrl)) {
            return ApiUtil.result(5001, "发送失败", "短信网关配置异常");
        }
        if(DPUtil.empty(apiUser) || DPUtil.empty(apiKey)) {
            return ApiUtil.result(0, "调试模式：" + mobile + "->" + content, data);
        }
        HttpClient client = new HttpClient();
        PostMethod post = new PostMethod(apiUrl);
        post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");//在头文件中设置转码
        NameValuePair[] param = {
            new NameValuePair("Uid", apiUser),
            new NameValuePair("Key", apiKey),
            new NameValuePair("smsMob", mobile),
            new NameValuePair("smsText", content)
        };
        post.setRequestBody(param);
        try {
            client.executeMethod(post);
        } catch (IOException e) {
            return ApiUtil.result(5002, "发送失败", e.getMessage());
        }
        Header[] headers = post.getResponseHeaders();
        int statusCode = post.getStatusCode();
        if(200 != statusCode) return ApiUtil.result(5003, "短信网关异常", statusCode);
        try {
            String result = new String(post.getResponseBodyAsString().getBytes("utf-8"));
            if("1".equals(result)) return ApiUtil.result(0, "验证码发送成功", data);
            return ApiUtil.result(5004, "发送验证码失败", result);
        } catch (IOException e) {
            return ApiUtil.result(5005, "读取返回结果失败", e.getMessage());
        } finally {
            post.releaseConnection();
        }
    }

}
