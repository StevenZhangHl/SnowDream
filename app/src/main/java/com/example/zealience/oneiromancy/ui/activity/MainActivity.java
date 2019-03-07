package com.example.zealience.oneiromancy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.TabEntity;
import com.example.zealience.oneiromancy.ui.fragment.HistoryTodayFragment;
import com.example.zealience.oneiromancy.ui.fragment.HomeFragment;
import com.example.zealience.oneiromancy.ui.fragment.MeFragment;
import com.example.zealience.oneiromancy.ui.fragment.NewsFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jaeger.library.StatusBarUtil;
import com.steven.base.app.BaseApp;

import java.util.ArrayList;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements View.OnClickListener {

    private FrameLayout mFlContainer;
    private CommonTabLayout mCommonTabLayout;
    private ImageView mTabMainCenter;
    private HomeFragment homeFragment;
    private MeFragment meFragment;
    private NewsFragment newsFragment;
    private HistoryTodayFragment historyTodayFragment;
    /**
     * fragment集合
     */
    private SupportFragment[] mFragments = new SupportFragment[4];
    /**
     * 导航栏数据集合
     */
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    /**
     * 导航栏标题数据源
     */
    private String[] mTabTitles = BaseApp.getAppResources().getStringArray(R.array.tab_title_items);
    /**
     * 未选中时显示的图片集合
     */
    private int[] mIconUnselectIds = {R.mipmap.tab_home_unselect, R.mipmap.tab_news_unselect, R.mipmap.dot, R.mipmap.tab_history_unselect, R.mipmap.tab_my_unselect};
    /**
     * 选中时显示的图片集合
     */
    private int[] mIconSelectIds = {R.mipmap.tab_home_select, R.mipmap.tab_news_select, R.mipmap.dot, R.mipmap.tab_history_select, R.mipmap.tab_my_select};
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THREE = 2;
    public static final int FOUR = 3;
    /**
     * 当前选中的位置
     */
    private int currentPosition = FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.mainColor), 0);
        setContentView(R.layout.activity_main);
        initView();
        BaseApp.getInstance().addActivity(this);
        if (savedInstanceState == null) {
            homeFragment = new HomeFragment();
            newsFragment = NewsFragment.getInstance("资讯");
            historyTodayFragment = HistoryTodayFragment.getInstance("今史");
            meFragment = MeFragment.newInstance("我的");
            setmFragmentsData();
            loadMultipleRootFragment(R.id.fl_container, FIRST, mFragments);
        } else {
            homeFragment = findFragment(HomeFragment.class);
            newsFragment = findFragment(NewsFragment.class);
            historyTodayFragment = findFragment(HistoryTodayFragment.class);
            meFragment = findFragment(MeFragment.class);
            setmFragmentsData();
        }
        initTab();
    }

    private void initView() {
        mFlContainer = (FrameLayout) findViewById(R.id.fl_container);
        mCommonTabLayout = (CommonTabLayout) findViewById(R.id.common_tab_layout);
        mTabMainCenter = (ImageView) findViewById(R.id.tab_main_center);
        mTabMainCenter.setOnClickListener(this);
    }

    private void initTab() {
        for (int i = 0; i < mTabTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTabTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        mCommonTabLayout.setTabData(mTabEntities);
        mCommonTabLayout.setCurrentTab(FIRST);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        currentPosition = FIRST;
                        break;
                    case 1:
                        currentPosition = SECOND;
                        break;
                    case 3:
                        currentPosition = THREE;
                        break;
                    case 4:
                        currentPosition = FOUR;
                        break;
                }
                showHideFragment(mFragments[currentPosition]);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 设置mFragments数据
     */
    private void setmFragmentsData() {
        mFragments[FIRST] = homeFragment;
        mFragments[SECOND] = newsFragment;
        mFragments[THREE] = historyTodayFragment;
        mFragments[FOUR] = meFragment;
    }

    @Override
    public void onClick(View v) {
        if (v == mTabMainCenter) {
            Intent intent = new Intent(MainActivity.this, VRActivity.class);
            startActivity(intent);
        }
    }
}
