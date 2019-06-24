package com.example.zealience.oneiromancy.api;

import com.steven.base.net.NetManager;

/**
 * @user steven
 * @createDate 2019/2/20 13:38
 * @description 自定义
 */
public class ApiManager {
    private static Api api;

    private ApiManager() {
    }

    public static Api getInstance() {
        if (api == null) {
            synchronized (ApiManager.class) {
                if (api == null) {
                    api = NetManager.getInstance().getRetrofit().create(Api.class);
                }
            }
        }
        return api;

    }
}
