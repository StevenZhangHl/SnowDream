package com.example.zealience.oneiromancy.ui.activity;

import com.umeng.message.inapp.InAppMessageManager;
import com.umeng.message.inapp.UmengSplashMessageActivity;

/**
 * @user steven
 * @createDate 2019/3/26 17:44
 * @description 自定义
 */
public class SplashMessageActivity extends UmengSplashMessageActivity {
    @Override
    public boolean onCustomPretreatment() {
        InAppMessageManager mInAppMessageManager = InAppMessageManager.getInstance(this);
        //设置应用内消息为Debug模式
        mInAppMessageManager.setInAppMsgDebugMode(true);
        //参数为Activity的完整包路径，下面仅是示例代码，请按实际需求填写
        mInAppMessageManager.setMainActivityPath("com.example.zealience.oneiromancy.ui.activity.WelcomeActivity");
        return super.onCustomPretreatment();
    }
}
