package com.example.zealience.oneiromancy.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.SnowConstant;
import com.example.zealience.oneiromancy.entity.EventEntity;
import com.example.zealience.oneiromancy.ui.activity.MyAddressActivity;
import com.example.zealience.oneiromancy.ui.activity.SearchActivity;
import com.example.zealience.oneiromancy.ui.activity.SetingActivity;
import com.example.zealience.oneiromancy.ui.activity.ShowAmapActivity;
import com.example.zealience.oneiromancy.ui.activity.SignInActivity;
import com.example.zealience.oneiromancy.ui.activity.UserInfolActivity;
import com.example.zealience.oneiromancy.ui.widget.SnowRefershHeader;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.hw.ycshareelement.transition.ViewStateSaver;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseFragment;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.CustomLayoutGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/6 18:02
 * @description 我的页面
 */
@TargetApi(Build.VERSION_CODES.M)
public class MeFragment extends BaseFragment implements View.OnClickListener, IShareElements, View.OnScrollChangeListener {
    private NestedScrollView me_scroll_view;
    private CircleImageView ivMeHead;
    private CustomLayoutGroup customLayout_collection;
    private CustomLayoutGroup customLayoutSignIn;
    private CustomLayoutGroup customLayoutAddress;
    private TitleBar me_title_bar;
    private TextView tv_user_nick;

    public static MeFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        MeFragment meFragment = new MeFragment();
        meFragment.setArguments(bundle);
        return meFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        me_title_bar = (TitleBar) rootView.findViewById(R.id.me_title_bar);
        me_scroll_view = (NestedScrollView) rootView.findViewById(R.id.me_scroll_view);
        tv_user_nick = (TextView)rootView.findViewById(R.id.tv_user_nick);
        ivMeHead = (CircleImageView) rootView.findViewById(R.id.iv_me_head);
        customLayout_collection = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_collection);
        customLayoutSignIn = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_signIn);
        customLayoutAddress = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_address);
        me_title_bar.setRightIcon(R.mipmap.icon_set);
        GlideApp.with(_mActivity)
                .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(ivMeHead);
        tv_user_nick.setText(UserHelper.getUserInfo(_mActivity).getNick());
        customLayout_collection.setOnClickListener(this);
        customLayoutAddress.setOnClickListener(this);
        customLayoutSignIn.setOnClickListener(this);
        ivMeHead.setOnClickListener(this);
        me_scroll_view.setOnScrollChangeListener(this);
        me_title_bar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                startActivity(SetingActivity.class);
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onClick(View v) {
        if (v == ivMeHead) {
            Intent intent = new Intent(_mActivity, UserInfolActivity.class);
            Bundle optionsBundle = YcShareElement.buildOptionsBundle(_mActivity, MeFragment.this);
            startActivity(intent, optionsBundle);
        }
        if (v == customLayout_collection) {
            ToastUitl.showShort("收藏");
        }
        if (v == customLayoutAddress) {
            startActivity(MyAddressActivity.class);
        }
        if (v == customLayoutSignIn) {
            startActivity(SignInActivity.class);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return new ShareElementInfo[]{new ShareElementInfo(ivMeHead, new TextViewStateSaver())};
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventEntity event) {
        if (SnowConstant.EVENT_UPDATE_USER_HEAD.equals(event.getEvent())) {
            GlideApp.with(_mActivity)
                    .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                    .into(ivMeHead);
        }
        if (SnowConstant.APLIPAY_APP_ID.equals(event.getEvent())){
            tv_user_nick.setText(UserHelper.getUserInfo(_mActivity).getNick());
        }
    }

    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        int height = DisplayUtil.dip2px(170);
        if (scrollY <= 0) {
            me_title_bar.setTitle("");
            me_title_bar.setBackgroundColor(Color.argb((int) 0, 44, 44, 44));//AGB由相关工具获得，或者美工提供
        } else if (scrollY > 0 && scrollY <= height) {
            float scale = (float) scrollY / height;
            float alpha = (255 * scale);
            me_title_bar.setBackgroundColor(Color.argb((int) alpha, 44, 44, 44));
            me_title_bar.setTitleColor(Color.argb((int) alpha, 255, 255, 255));
            me_title_bar.setTitle("小可爱");
        } else {
            me_title_bar.setTitle("小可爱");
            me_title_bar.setBackgroundColor(Color.argb((int) 255, 44, 44, 44));
        }
    }
}
