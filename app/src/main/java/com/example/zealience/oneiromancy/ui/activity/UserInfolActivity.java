package com.example.zealience.oneiromancy.ui.activity;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.example.zealience.oneiromancy.constant.SnowConstant;
import com.example.zealience.oneiromancy.entity.EventEntity;
import com.example.zealience.oneiromancy.entity.UserDynamicEntity;
import com.example.zealience.oneiromancy.entity.UserInfo;
import com.example.zealience.oneiromancy.ui.UserDynamicAdapter;
import com.example.zealience.oneiromancy.util.BlurTransformation;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.hjq.bar.OnTitleBarListener;
import com.steven.base.app.GlideApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.impl.SpanIndexListener;
import com.steven.base.util.AddRecyclerEmptyView;
import com.steven.base.util.AnimationUtils;
import com.steven.base.util.DateTimeHelper;
import com.steven.base.util.DotItemDecoration;
import com.steven.base.util.Glide4Engine;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
import com.steven.base.widget.CustomDialog;
import com.steven.base.widget.CustomLayoutGroup;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfolActivity extends BaseActivity implements View.OnClickListener {
    private final int REQUEST_CODE_CHOOSE = 1;
    private final int REQUEST_CODE_ADD_DYNAMIC = 2;
    private ImageView mIvBackgroundWall;
    private CircleImageView mIvUserInfoHead;
    /**
     * 选择图片的路径
     */
    private String selectPath;
    private CustomLayoutGroup customLayout_user_name;
    private RecyclerView recyclerview_user_dynamic;
    private UserDynamicAdapter dynamicAdapter;
    private List<UserDynamicEntity> userDynamicEntityList = new ArrayList<>();
    private DotItemDecoration mItemDecoration;

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
        setWhiteStatusBar(R.color.white);
        mIvUserInfoHead = (CircleImageView) findViewById(R.id.iv_user_info_head);
        mIvBackgroundWall = (ImageView) findViewById(R.id.iv_background_wall);
        recyclerview_user_dynamic = (RecyclerView) findViewById(R.id.recyclerview_user_dynamic);
        customLayout_user_name = (CustomLayoutGroup) findViewById(R.id.customLayout_user_name);
        customLayout_user_name.setOnClickListener(this);
        mIvUserInfoHead.setOnClickListener(this);
        getTitlebar().setRightIcon(R.mipmap.icon_pulibsh_dynamic);
        getTitlebar().setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                startActivityForResult(PublishDynamicActivity.class, REQUEST_CODE_ADD_DYNAMIC);
            }
        });
        GlideApp.with(this)
                .load(UserHelper.getUserInfo(this).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(mIvUserInfoHead);
        GlideApp.with(this)
                .load(R.mipmap.bg_user_wall)
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(this, 12)))
                .into(mIvBackgroundWall);
        initRecyclerView();
        startAnim();
    }

    private void initRecyclerView() {
        mItemDecoration = new DotItemDecoration
                .Builder(this)
                .setOrientation(DotItemDecoration.VERTICAL)//if you want a horizontal item decoration,remember to set horizontal orientation to your LayoutManager
                .setItemStyle(DotItemDecoration.STYLE_DRAW)//choose to draw or use resource
                .setTopDistance(20)//dp
                .setItemInterVal(10)//dp
                .setItemPaddingLeft(10)//default value equals to item interval value
                .setItemPaddingRight(10)//default value equals to item interval value
                .setDotColor(ContextCompat.getColor(this, R.color.color_666))
                .setDotRadius(2)//dp
                .setDotPaddingTop(0)
                .setDotInItemOrientationCenter(false)//set true if you want the dot align center
                .setLineColor(ContextCompat.getColor(this, R.color.color_999))//the color of vertical line
                .setLineWidth(1)//dp
                .setEndText("")//set end text
                .setTextColor(ContextCompat.getColor(this, R.color.color_666))
                .setTextSize(10)//sp
                .setDotPaddingText(2)//dp.The distance between the last dot and the end text
                .setBottomDistance(0)//you can add a distance to make bottom line longer
                .create();
        mItemDecoration.setSpanIndexListener(new SpanIndexListener() {
            @Override
            public void onSpanIndexChange(View view, int spanIndex) {
                view.setBackgroundResource(spanIndex == 0 ? R.drawable.pop_left : R.drawable.pop_right);
            }
        });
        recyclerview_user_dynamic.setNestedScrollingEnabled(false);
        recyclerview_user_dynamic.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        dynamicAdapter = new UserDynamicAdapter(R.layout.item_user_dynamic, new ArrayList<>());
        dynamicAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        recyclerview_user_dynamic.setAdapter(dynamicAdapter);
        getUserDynamiData();
    }

    /**
     * 获取用户动态数据
     */
    private void getUserDynamiData() {
        userDynamicEntityList = GsonUtil.GsonToList(SPUtils.getSharedStringData(this, SharePConstant.KEY_USER_DYNAMIC_LIST), UserDynamicEntity.class);
        if (userDynamicEntityList.size() == 0) {
            AddRecyclerEmptyView.setEmptyView(dynamicAdapter, recyclerview_user_dynamic);
        } else {
            recyclerview_user_dynamic.addItemDecoration(mItemDecoration);
            dynamicAdapter.setNewData(userDynamicEntityList);
        }

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
        if (v == mIvUserInfoHead) {
            if (Build.VERSION.SDK_INT >= 23) {
                postPermission();
            }
        }
        if (v == customLayout_user_name) {
            CustomDialog customDialog = new CustomDialog(this);
            customDialog.showTitle("修改昵称")
                    .isShowEditText(true)
                    .isShowContent(false)
                    .show();
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
            if (requestCode == REQUEST_CODE_ADD_DYNAMIC) {
                UserDynamicEntity dynamicEntity = (UserDynamicEntity) data.getSerializableExtra("dynamicEntity");
                userDynamicEntityList.add(0, dynamicEntity);
                SPUtils.setSharedStringData(UserInfolActivity.this, SharePConstant.KEY_USER_DYNAMIC_LIST, GsonUtil.GsonString(userDynamicEntityList));
                getUserDynamiData();
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
