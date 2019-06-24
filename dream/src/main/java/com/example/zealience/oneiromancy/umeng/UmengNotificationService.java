package com.example.zealience.oneiromancy.umeng;

import android.content.Context;
import android.content.Intent;
import com.umeng.message.UmengMessageService;

import org.android.agoo.common.AgooConstants;

/**
 * @user steven
 * @createDate 2019/3/26 18:06
 * @description 自定义通知接受服务
 */
public class UmengNotificationService extends UmengMessageService {
    @Override
    public void onMessage(Context context, Intent intent) {
        String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        Intent intent1 = new Intent();
        intent1.setClass(context, MyNotificationService.class);
        intent1.putExtra("UmengMsg", message);
        context.startService(intent1);
    }
}
