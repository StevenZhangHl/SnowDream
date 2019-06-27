package com.snow.weevideo;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jaeger.library.StatusBarUtil;
import com.snow.weevideo.adapter.FragmentListAdapter;
import com.snow.weevideo.module.attention.AttentionMainFragment;
import com.snow.weevideo.module.live.LiveMainFragment;
import com.snow.weevideo.module.samecity.SameCityMainFragment;
import com.snow.weevideo.module.video.fragment.VideoMainFragment;
import com.steven.base.ARouterPath;
import com.steven.base.base.AppManager;
import com.steven.base.util.DisplayUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.yokeyword.fragmentation.SupportActivity;

@Route(path = ARouterPath.WEE_VIDEO_MAIN_ACTIVITY)
public class WeeVideoMainActivity extends SupportActivity {
    private static final String[] CHANNELS = new String[]{"关注", "视频", "同城", "直播"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private CircleImageView iv_user_head;
    private MagicIndicator home_top_indicator;
    private ViewPager home_viewpager;
    private RelativeLayout rl_video_top;
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentListAdapter fragmentListAdapter;
    private VideoMainFragment videoMainFragment;
    private AttentionMainFragment attentionMainFragment;
    private SameCityMainFragment sameCityMainFragment;
    private LiveMainFragment liveMainFragment;
    private int viewpagerHeight;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWhiteStatusBar(R.color.white);
        setContentView(R.layout.activity_wee_video_main);
        initView(savedInstanceState);
    }


    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        iv_user_head = (CircleImageView) findViewById(R.id.iv_user_head);
        home_top_indicator = (MagicIndicator) findViewById(R.id.home_top_indicator);
        home_viewpager = (ViewPager) findViewById(R.id.home_viewpager);
        rl_video_top = (RelativeLayout) findViewById(R.id.rl_video_top);
        measureLocation();
    }

    private void measureLocation() {
        int topBarHeight = DisplayUtil.dip2px(45);
        int screenHeight = DisplayUtil.getScreenHeight(this);
        rl_video_top.post(new Runnable() {
            @Override
            public void run() {
                int location[] = new int[2];
                rl_video_top.getLocationOnScreen(location);
                viewpagerHeight = screenHeight - topBarHeight - location[1];
                initViewPager();
                initIndicator();
            }
        });
    }

    private void initViewPager() {
        videoMainFragment = VideoMainFragment.getInstance("video", viewpagerHeight);
        attentionMainFragment = AttentionMainFragment.getInstance("attention");
        sameCityMainFragment = SameCityMainFragment.getInstance("samecity",viewpagerHeight);
        liveMainFragment = LiveMainFragment.getInstance("live");
        fragments.add(attentionMainFragment);
        fragments.add(videoMainFragment);
        fragments.add(sameCityMainFragment);
        fragments.add(liveMainFragment);
        fragmentListAdapter = new FragmentListAdapter(getSupportFragmentManager(), fragments);
        home_viewpager.setAdapter(fragmentListAdapter);
        home_viewpager.setOffscreenPageLimit(4);
        home_viewpager.setCurrentItem(1);
    }

    private void initIndicator() {
        home_top_indicator.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setScrollPivotX(0.35f);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(mDataList.get(index));
                clipPagerTitleView.setTextColor(ContextCompat.getColor(WeeVideoMainActivity.this, R.color.color_666666));
                clipPagerTitleView.setClipColor(ContextCompat.getColor(WeeVideoMainActivity.this, R.color.color_333333));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        home_viewpager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 13));
                indicator.setRoundRadius(UIUtil.dip2px(context, 2));
                indicator.setYOffset(15);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.RED);
                return indicator;
            }
        });
        home_top_indicator.setNavigator(commonNavigator);
        home_top_indicator.onPageSelected(1);
        ViewPagerHelper.bind(home_top_indicator, home_viewpager);
    }

    /**
     * 白色状态栏
     * 6.0以上状态栏修改为白色，状态栏字体为黑色
     * 6.0以下状态栏为黑色
     */
    protected void setWhiteStatusBar(@ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            StatusBarUtil.setColor(this, getResources().getColor(color), 0);
        } else {
            StatusBarUtil.setColor(this, getResources().getColor(R.color.black), 0);
        }
    }
}
