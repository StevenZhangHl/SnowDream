package com.example.zealience.oneiromancy.ui.fragment;

import android.os.Bundle;

import com.example.zealience.oneiromancy.R;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.header.PhoenixHeader;
import com.scwang.smartrefresh.header.TaurusHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.steven.base.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/6 18:02
 * @description 我的页面
 */
public class MeFragment extends BaseFragment {
    private SmartRefreshLayout refreshMe;
    private CircleImageView ivMeHead;

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
        refreshMe = (SmartRefreshLayout) rootView.findViewById(R.id.refresh__me);
        ivMeHead = (CircleImageView) rootView.findViewById(R.id.iv_me_head);
        refreshMe.setPrimaryColors(_mActivity.getResources().getColor(R.color.mainColor));
        refreshMe.setRefreshHeader(new BezierRadarHeader(_mActivity));
    }

    @Override
    public void onError(String msg) {

    }
}
