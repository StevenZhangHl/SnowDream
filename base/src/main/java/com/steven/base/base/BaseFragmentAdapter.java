package com.steven.base.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 该类内的每一个生成的 Fragment 都将保存在内存之中，
 * 因此适用于那些相对静态的页，数量也比较少的那种；
 * 如果需要处理有很多页，并且数据动态性较大、占用内存较多的情况，
 * 应该使用FragmentStatePagerAdapter。
 */
public class BaseFragmentAdapter extends FragmentPagerAdapter {

    List<SupportFragment> fragmentList = new ArrayList<SupportFragment>();
    private List<String> mTitles;
    private String[] mTitless;

    public BaseFragmentAdapter(FragmentManager fm, List<SupportFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<SupportFragment> fragmentList, String[] mTitless) {
        super(fm);
        this.fragmentList = fragmentList;
        this.mTitless = mTitless;
    }

    public BaseFragmentAdapter(FragmentManager fm, List<SupportFragment> fragmentList, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
        setFragments(fm,fragmentList,mTitles);
    }
    //刷新fragment
    public void setFragments(FragmentManager fm, List<SupportFragment> fragments, List<String> mTitles) {
        this.mTitles = mTitles;
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commitAllowingStateLoss();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragments;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        return !CollectionUtils.isNullOrEmpty(mTitles) ? mTitles.get(position) : "";
        return mTitless[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
