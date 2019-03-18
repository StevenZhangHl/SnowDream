package com.example.zealience.oneiromancy.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.NewsEntity;
import com.example.zealience.oneiromancy.mvp.contract.NewsContract;
import com.example.zealience.oneiromancy.mvp.model.NewsModel;
import com.example.zealience.oneiromancy.mvp.presenter.NewsPresenter;
import com.example.zealience.oneiromancy.ui.FragmentListAdapter;
import com.jaeger.library.StatusBarUtil;
import com.steven.base.base.BaseFragment;
import com.steven.base.util.AssetsUtil;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.Typefaces;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/7 13:17
 * @description 新闻页
 */
public class NewsFragment extends BaseFragment {
    private static final String[] CHANNELS = new String[]{"推荐", "社会", "国内", "国际", "娱乐", "体育", "军事", "科技", "财经", "时尚"};
    private List<String> mDataList = Arrays.asList(CHANNELS);
    private MagicIndicator magicIndicator;
    private ViewPager viewPager;
    private TextView tv_news_title;
    private FragmentListAdapter fragmentListAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    public static NewsFragment getInstance(String title) {
        NewsFragment newsFragment = new NewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        newsFragment.setArguments(bundle);
        return newsFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        magicIndicator = (MagicIndicator) rootView.findViewById(R.id.magic_indicator);
        tv_news_title = (TextView) rootView.findViewById(R.id.tv_news_title);
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        for (int i = 0; i < mDataList.size(); i++) {
            NewsChildFragment childFragment = NewsChildFragment.getInstance(mDataList.get(i));
            fragments.add(childFragment);
        }
        fragmentListAdapter = new FragmentListAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(fragmentListAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        initMagicIndicator();
        tv_news_title.setTypeface(Typefaces.get(_mActivity, "showlove.ttf"));
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(_mActivity.getResources().getColor(R.color.mainColor));
        CommonNavigator commonNavigator = new CommonNavigator(_mActivity);
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
                clipPagerTitleView.setTextColor(_mActivity.getResources().getColor(R.color.color_f5));
                clipPagerTitleView.setClipColor(_mActivity.getResources().getColor(R.color.white));
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.WHITE);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
