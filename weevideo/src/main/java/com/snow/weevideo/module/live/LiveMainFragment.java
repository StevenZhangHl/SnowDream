package com.snow.weevideo.module.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.snow.weevideo.R;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * @user steven
 * @createDate 2019/6/24 16:51
 * @description 自定义
 */
public class LiveMainFragment extends SupportFragment {

    public static LiveMainFragment getInstance(String title) {
        LiveMainFragment liveMainFragment = new LiveMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        liveMainFragment.setArguments(bundle);
        return liveMainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_live_main, null);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
