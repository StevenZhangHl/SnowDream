package com.snow.weevideo.module.attention;

import android.os.Bundle;
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
public class AttentionMainFragment extends SupportFragment {
    public static AttentionMainFragment getInstance(String title) {
        AttentionMainFragment attentionMainFragment = new AttentionMainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        attentionMainFragment.setArguments(bundle);
        return attentionMainFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_attention_main, null);
        return view;
    }

    @Override
    public void onLazyInitView(Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
