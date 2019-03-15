package com.example.zealience.oneiromancy.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.SnowConstant;
import com.example.zealience.oneiromancy.entity.EventEntity;
import com.example.zealience.oneiromancy.ui.activity.MyAddressActivity;
import com.example.zealience.oneiromancy.ui.activity.SearchActivity;
import com.example.zealience.oneiromancy.ui.activity.ShowAmapActivity;
import com.example.zealience.oneiromancy.ui.activity.SignInActivity;
import com.example.zealience.oneiromancy.ui.activity.UserInfolActivity;
import com.example.zealience.oneiromancy.ui.widget.SnowRefershHeader;
import com.example.zealience.oneiromancy.util.UserHelper;
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
public class MeFragment extends BaseFragment implements View.OnClickListener, IShareElements {
    private SmartRefreshLayout refreshMe;
    private CircleImageView ivMeHead;
    private CustomLayoutGroup customLayout_collection;
    private CustomLayoutGroup customLayoutSignIn;
    private CustomLayoutGroup customLayoutAddress;

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
        refreshMe = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_me);
        ivMeHead = (CircleImageView) rootView.findViewById(R.id.iv_me_head);
        customLayout_collection = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_collection);
        customLayoutSignIn = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_signIn);
        customLayoutAddress = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_address);
        refreshMe.setPrimaryColors(_mActivity.getResources().getColor(R.color.mainColor));
        refreshMe.setRefreshHeader(new MaterialHeader(_mActivity));
        customLayout_collection.setTv_left("收藏");
        customLayoutSignIn.setTv_left("签到");
        customLayoutAddress.setTv_left("地址");
        customLayout_collection.hidenLine();
        customLayout_collection.setLeftDrawable(R.mipmap.icon_collection);
        customLayoutSignIn.setLeftDrawable(R.mipmap.icon_sign_in);
        customLayoutAddress.setLeftDrawable(R.mipmap.icon_address);
        GlideApp.with(_mActivity)
                .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(ivMeHead);
        customLayout_collection.setOnClickListener(this);
        customLayoutAddress.setOnClickListener(this);
        customLayoutSignIn.setOnClickListener(this);
        ivMeHead.setOnClickListener(this);
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
    }
}
