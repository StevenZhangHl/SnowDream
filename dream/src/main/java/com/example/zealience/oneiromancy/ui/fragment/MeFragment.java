package com.example.zealience.oneiromancy.ui.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.api.ApiManager;
import com.example.zealience.oneiromancy.api.ApiRequest;
import com.steven.base.app.SharePConstant;
import com.example.zealience.oneiromancy.constant.SnowConstant;
import com.example.zealience.oneiromancy.entity.EventEntity;
import com.example.zealience.oneiromancy.entity.LuckEntity;
import com.steven.base.bean.UserInfo;
import com.example.zealience.oneiromancy.ui.activity.CustomViewActivity;
import com.example.zealience.oneiromancy.ui.activity.user.MyAddressActivity;
import com.example.zealience.oneiromancy.ui.activity.SetingActivity;
import com.example.zealience.oneiromancy.ui.activity.SignInActivity;
import com.example.zealience.oneiromancy.ui.activity.user.UserInfolActivity;
import com.example.zealience.oneiromancy.ui.widget.ConstellationSelectDialog;
import com.steven.base.util.UserHelper;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.steven.base.ARouterPath;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseFragment;
import com.steven.base.rx.BaseObserver;
import com.steven.base.rx.RxHelper;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
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
    private CustomLayoutGroup customLayout_wee_video;
    private TitleBar me_title_bar;
    private TextView tv_user_nick;
    private TextView tv_set_constellation;
    private CustomLayoutGroup rl_my_constellation;
    private CustomLayoutGroup rl_date;
    private CustomLayoutGroup rl_luck_color;
    private CustomLayoutGroup rl_luck_number;
    private CustomLayoutGroup rl_friend;
    /**
     * 今日概述
     */
    private TextView tv_summary;
    private RatingBar rating_all;
    private RatingBar rating_health;
    private RatingBar rating_love;
    private RatingBar rating_money;
    private RatingBar rating_work;
    private LinearLayout ll_luck_content;

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
        tv_user_nick = (TextView) rootView.findViewById(R.id.tv_user_nick);
        ivMeHead = (CircleImageView) rootView.findViewById(R.id.iv_me_head);
        customLayout_collection = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_collection);
        customLayoutSignIn = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_signIn);
        customLayoutAddress = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_address);
        customLayout_wee_video = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_wee_video);
        tv_set_constellation = (TextView) rootView.findViewById(R.id.tv_set_constellation);
        customLayout_collection = (CustomLayoutGroup) rootView.findViewById(R.id.customLayout_collection);
        rl_my_constellation = (CustomLayoutGroup) rootView.findViewById(R.id.rl_my_constellation);
        rl_date = (CustomLayoutGroup) rootView.findViewById(R.id.rl_date);
        rl_luck_color = (CustomLayoutGroup) rootView.findViewById(R.id.rl_luck_color);
        rl_luck_number = (CustomLayoutGroup) rootView.findViewById(R.id.rl_luck_number);
        rl_friend = (CustomLayoutGroup) rootView.findViewById(R.id.rl_friend);
        tv_summary = (TextView) rootView.findViewById(R.id.tv_summary);
        rating_all = (RatingBar) rootView.findViewById(R.id.rating_all);
        rating_health = (RatingBar) rootView.findViewById(R.id.rating_health);
        rating_love = (RatingBar) rootView.findViewById(R.id.rating_love);
        rating_money = (RatingBar) rootView.findViewById(R.id.rating_money);
        rating_work = (RatingBar) rootView.findViewById(R.id.rating_work);
        ll_luck_content = (LinearLayout) rootView.findViewById(R.id.ll_luck_content);
        me_title_bar.setRightIcon(R.mipmap.icon_set);
        GlideApp.with(_mActivity)
                .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(ivMeHead);
        tv_user_nick.setText(UserHelper.getUserInfo(_mActivity).getNick());
        customLayout_collection.setOnClickListener(this);
        customLayoutAddress.setOnClickListener(this);
        customLayoutSignIn.setOnClickListener(this);
        customLayout_wee_video.setOnClickListener(this);
        ivMeHead.setOnClickListener(this);
        tv_set_constellation.setOnClickListener(this);
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
        getUserLuckData();
    }

    /**
     * 获取用户当天运势
     */
    private void getUserLuckData() {
        UserInfo userInfo = UserHelper.getUserInfo(_mActivity);
        if (!TextUtils.isEmpty(userInfo.getConstellation())) {
            tv_set_constellation.setVisibility(View.GONE);
            ll_luck_content.setVisibility(View.VISIBLE);
            mRxManager.add(ApiManager.getInstance().getUserLuckData(ApiRequest.getUserLuckData(userInfo.getConstellation(), "")).compose(RxHelper.customResult()).subscribeWith(new BaseObserver<LuckEntity>(_mActivity, false) {
                @Override
                protected void onSuccess(LuckEntity luckEntity) {
                    if (luckEntity != null) {
                        rl_my_constellation.setTv_right(luckEntity.getName());
                        rl_date.setTv_right(luckEntity.getDatetime());
                        rl_luck_color.setTv_right(luckEntity.getColor());
                        rl_luck_number.setTv_right(luckEntity.getNumber() + "");
                        rl_friend.setTv_right(luckEntity.getQFriend());
                        tv_summary.setText(luckEntity.getSummary());
                        rating_all.setRating(calculateRatingNum(luckEntity.getAll()));
                        rating_health.setRating(calculateRatingNum(luckEntity.getHealth()));
                        rating_love.setRating(calculateRatingNum(luckEntity.getLove()));
                        rating_money.setRating(calculateRatingNum(luckEntity.getMoney()));
                        rating_work.setRating(calculateRatingNum(luckEntity.getWork()));
                    }
                }

                @Override
                protected void onError(String message) {

                }
            }));
        } else {
            ll_luck_content.setVisibility(View.GONE);
            tv_set_constellation.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 计算星星的个数
     *
     * @param value
     * @return
     */
    private int calculateRatingNum(String value) {
        if (value.contains("%")) {
            int i = Integer.parseInt(value.substring(0, value.length() - 1));
            if (i == 0) {
                return 0;
            } else if (i > 0 && i < 20) {
                return 1;
            } else if (i >= 20 && i < 40) {
                return 2;
            } else if (i >= 40 && i < 60) {
                return 3;
            } else if (i >= 60 && i < 80) {
                return 4;
            } else if (i >= 80 && i < 100) {
                return 5;
            }
        }
        return 0;
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
            startActivity(CustomViewActivity.class);
        }
        if (v == customLayoutAddress) {
            startActivity(MyAddressActivity.class);
        }
        if (v == customLayoutSignIn) {
            startActivity(SignInActivity.class);
        }
        if (v == customLayout_wee_video) {
            ARouter.getInstance().build(ARouterPath.WEE_VIDEO_MAIN_ACTIVITY).navigation();
        }
        if (v == tv_set_constellation) {
            ConstellationSelectDialog selectDialog = new ConstellationSelectDialog(_mActivity, null);
            selectDialog.setMyOnItemClickListener(new ConstellationSelectDialog.MyOnItemClickListener() {
                @Override
                public void getSelectType(String s) {
                    UserInfo userInfo = UserHelper.getUserInfo(_mActivity);
                    userInfo.setConstellation(s);
                    SPUtils.setSharedStringData(_mActivity, SharePConstant.KEY_USER_INFO_DATA, GsonUtil.GsonString(userInfo));
                    getUserLuckData();
                }
            });
            selectDialog.show();
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
        if (SnowConstant.EVENT_UPDATE_USER_SNAK.equals(event.getEvent())) {
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
            me_title_bar.setTitle(UserHelper.getUserInfo(_mActivity).getNick());
        } else {
            me_title_bar.setTitle(UserHelper.getUserInfo(_mActivity).getNick());
            me_title_bar.setBackgroundColor(Color.argb((int) 255, 44, 44, 44));
        }
    }
}
