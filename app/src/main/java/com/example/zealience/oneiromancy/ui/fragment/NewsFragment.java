package com.example.zealience.oneiromancy.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.NewsEntity;
import com.example.zealience.oneiromancy.mvp.contract.NewsContract;
import com.example.zealience.oneiromancy.mvp.model.NewsModel;
import com.example.zealience.oneiromancy.mvp.presenter.NewsPresenter;
import com.example.zealience.oneiromancy.ui.FragmentListAdapter;
import com.steven.base.base.BaseFragment;
import com.steven.base.util.DisplayUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
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
        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        for (int i = 0; i < mDataList.size(); i++) {
            NewsChildFragment childFragment = NewsChildFragment.getInstance(mDataList.get(i));
            fragments.add(childFragment);
        }
        fragmentListAdapter = new FragmentListAdapter(getChildFragmentManager(), fragments);
        viewPager.setAdapter(fragmentListAdapter);
        viewPager.setOffscreenPageLimit(fragments.size());
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
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
                clipPagerTitleView.setTextColor(_mActivity.getResources().getColor(R.color.color_707070));
                clipPagerTitleView.setClipColor(_mActivity.getResources().getColor(R.color.mainColor));
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
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setVerticalPadding(DisplayUtil.dip2px(3));
                indicator.setHorizontalPadding(DisplayUtil.dip2px(8));
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onError(String msg) {

    }

}
