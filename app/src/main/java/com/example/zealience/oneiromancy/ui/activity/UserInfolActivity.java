package com.example.zealience.oneiromancy.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.SnowConstant;
import com.example.zealience.oneiromancy.entity.EventEntity;
import com.example.zealience.oneiromancy.entity.UserInfo;
import com.example.zealience.oneiromancy.util.BlurTransformation;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.hjq.bar.OnTitleBarListener;
import com.steven.base.app.GlideApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.AnimationUtils;
import com.steven.base.util.Glide4Engine;
import com.steven.base.widget.CustomDialog;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfolActivity extends BaseActivity implements View.OnClickListener {
    private final int REQUEST_CODE_CHOOSE = 1;
    private Button bt_login_out;
    private ImageView mIvBackgroundWall;
    /**
     * 退出登录
     */
    private Button mBtLoginOut;
    private CircleImageView mIvUserInfoHead;
    /**
     * 选择图片的路径
     */
    private String selectPath;

    private CustomDialog customDialog;

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, UserInfolActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_photo_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        showTitle("个人信息");
        getTitlebar().setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                onBackPressed();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
        setWhiteStatusBar(R.color.white);
        bt_login_out = (Button) findViewById(R.id.bt_login_out);
        mIvUserInfoHead = (CircleImageView) findViewById(R.id.iv_user_info_head);
        mIvBackgroundWall = (ImageView) findViewById(R.id.iv_background_wall);
        mBtLoginOut = (Button) findViewById(R.id.bt_login_out);
        mBtLoginOut.setOnClickListener(this);
        mIvUserInfoHead.setOnClickListener(this);
        bt_login_out.setOnClickListener(this);
        GlideApp.with(this)
                .load(UserHelper.getUserInfo(this).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(mIvUserInfoHead);
        GlideApp.with(this)
                .load(R.mipmap.bg_user_wall)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(this, 12)))
                .into(mIvBackgroundWall);
        startAnim();
    }

    private void showDailog() {
        customDialog = new CustomDialog(this);
        customDialog.showTitle("退出提示")
                .setButtonTexts("取消", "确定")
                .showContent("确定退出吗？")
                .setOnClickListene(new CustomDialog.OnClickListener() {
                    @Override
                    public void onLeftClick() {
                        customDialog.dismiss();
                    }

                    @Override
                    public void onRightClick() {
                        customDialog.dismiss();
                        UserHelper.clearUseInfo(UserInfolActivity.this);
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(LoginActivity.class);
                    }
                })
                .show();
    }

    private int delayTime = 10000;
    private int count = 0;
    private Handler mHandlerAnim = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (count % 2 == 0) {
                AnimationUtils.scaleBigView(mIvBackgroundWall, delayTime);
            } else {
                AnimationUtils.scaleSmallView(mIvBackgroundWall, delayTime);
            }
            count++;
            mHandlerAnim.postDelayed(this, delayTime);
        }
    };

    private void startAnim() {
        mHandlerAnim.post(runnable);
    }

    @Override
    public void onClick(View v) {
        if (v == bt_login_out) {
            showDailog();
        }
        if (v == mIvUserInfoHead) {
            if (Build.VERSION.SDK_INT >= 23) {
                postPermission();
            }
        }
    }

    /**
     * 动态请求读取权限
     */
    private void postPermission() {
        AndPermission.with(UserInfolActivity.this)
                .runtime()
                .permission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        openCameraAndAlbum();
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                if (AndPermission.hasAlwaysDeniedPermission(UserInfolActivity.this, data)) {
                    // 打开权限设置页
                    AndPermission.permissionSetting(UserInfolActivity.this).execute();
                    return;
                }
            }
        }).start();
    }

    private void openCameraAndAlbum() {
        Matisse.from(UserInfolActivity.this)
                .choose(MimeType.ofAll())
                .countable(true)
                .maxSelectable(1)
                .capture(true)
                .captureStrategy(new CaptureStrategy(true, SnowConstant.FILE_PROVIDER))
//                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
//                .gridExpectedSize(getResources().getDimensionPixelSize())
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                selectPath = Matisse.obtainPathResult(data).get(0);
                GlideApp.with(UserInfolActivity.this)
                        .load(selectPath)
                        .into(mIvUserInfoHead);
                UserInfo userInfo = UserHelper.getUserInfo(UserInfolActivity.this);
                userInfo.setHeadImageUrl(selectPath);
                UserHelper.saveUserInfo(UserInfolActivity.this, userInfo);
                EventBus.getDefault().post(new EventEntity(SnowConstant.EVENT_UPDATE_USER_HEAD));
                Log.i("Matisse", "mSelected: " + selectPath);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandlerAnim != null) {
            mHandlerAnim.removeCallbacks(runnable);
        }
    }
}
