package com.example.zealience.oneiromancy.util;

import android.content.Context;
import android.text.TextUtils;

import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.example.zealience.oneiromancy.entity.UserInfo;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;

/**
 * @user steven
 * @createDate 2019/3/6 15:37
 * @description 自定义
 */
public class UserHelper {
    /**
     * 保存用户信息
     * @param context
     * @param userInfo
     */
    public static void saveUserInfo(Context context, UserInfo userInfo) {
        SPUtils.setSharedStringData(context, SharePConstant.KEY_USER_INFO_DATA, GsonUtil.GsonString(userInfo));
    }

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static UserInfo getUserInfo(Context context) {
        return GsonUtil.GsonToBean(SPUtils.getSharedStringData(context, SharePConstant.KEY_USER_INFO_DATA), UserInfo.class);
    }

    /**
     * 返回用户登录状态
     * @param context
     * @return
     */
    public static boolean isLogin(Context context) {
        if (!TextUtils.isEmpty(SPUtils.getSharedStringData(context, SharePConstant.KEY_USER_INFO_DATA))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 清空用户数据
     * @param context
     */
    public static void clearUseInfo(Context context){
        SPUtils.setSharedStringData(context, SharePConstant.KEY_USER_INFO_DATA, "");
    }
}
