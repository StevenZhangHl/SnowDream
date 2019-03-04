package com.example.zealience.oneiromancy.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @user steven
 * @createDate 2019/2/20 13:37
 * @description 自定义
 */
public class ApiRequest {
    private static JSONObject object;

    private static RequestBody getRequestBody(JSONObject object) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), object.toString());
    }

    public static JSONObject getDefaultJSONObject() {
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }

    public static Map<String, Object> getHomeDreamType() {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "3fc44d0a8ea0c260e39d72ce4783dd42");
        return map;
    }

    /**
     * 解梦查询
     * @param content
     * @param dreamType
     * @return
     */
    public static Map<String, Object> searchDreamData(String content,int dreamType) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", "3fc44d0a8ea0c260e39d72ce4783dd42");
        map.put("q", content);
        if (dreamType!=-1) {
            map.put("cid", dreamType);
        }
        map.put("full", 1);
        return map;
    }
}
