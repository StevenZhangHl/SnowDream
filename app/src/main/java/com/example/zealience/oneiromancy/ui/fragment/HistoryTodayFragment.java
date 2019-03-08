package com.example.zealience.oneiromancy.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TimeUtils;
import android.view.View;
import android.widget.DatePicker;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.example.zealience.oneiromancy.entity.HistoryDetailEntity;
import com.example.zealience.oneiromancy.entity.HistoryEntity;
import com.example.zealience.oneiromancy.mvp.contract.HistoryContract;
import com.example.zealience.oneiromancy.mvp.model.HistoryModel;
import com.example.zealience.oneiromancy.mvp.presenter.HistoryPresenter;
import com.example.zealience.oneiromancy.ui.HistoryListAdapter;
import com.example.zealience.oneiromancy.ui.activity.HistoryDetailActivity;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.steven.base.base.BaseFragment;
import com.steven.base.util.DateTimeHelper;
import com.steven.base.util.ToastUitl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @user steven
 * @createDate 2019/3/7 13:22
 * @description 历史上的今天
 */
public class HistoryTodayFragment extends BaseFragment<HistoryPresenter, HistoryModel> implements HistoryContract.View, DatePickerDialog.OnDateSetListener {
    private TitleBar historyToolbar;
    private RecyclerView recyclerviewHistoryList;
    private HistoryListAdapter historyListAdapter;
    private List<HistoryEntity> mHistoryData = new ArrayList<>();

    public static HistoryTodayFragment getInstance(String title) {
        HistoryTodayFragment historyTodayFragment = new HistoryTodayFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        historyTodayFragment.setArguments(bundle);
        return historyTodayFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_history_today;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        historyToolbar = (TitleBar) rootView.findViewById(R.id.history_toolbar);
        recyclerviewHistoryList = (RecyclerView) rootView.findViewById(R.id.recyclerview_history_list);
        historyListAdapter = new HistoryListAdapter(R.layout.item_history_list, new ArrayList<>());
        recyclerviewHistoryList.setLayoutManager(new LinearLayoutManager(_mActivity));
        recyclerviewHistoryList.setAdapter(historyListAdapter);
        historyToolbar.setRightIcon(R.mipmap.icon_calander);
        String currentDay = mPresenter.getCurrentDay();
        historyToolbar.setRightTitle(currentDay);
        historyToolbar.setRightColor(_mActivity.getResources().getColor(R.color.mainColor));
        mPresenter.getHistoryBydate(currentDay);
        historyToolbar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {

            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(_mActivity, HistoryTodayFragment.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        recyclerviewHistoryList.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(KeyConstant.HISITORY_ENTITY_KEY, mHistoryData.get(position));
                HistoryDetailActivity.startActivity(_mActivity, bundle);
            }
        });
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void setHistoryEntities(List<HistoryEntity> historyEntities) {
        historyListAdapter.setNewData(historyEntities);
        mHistoryData.clear();
        mHistoryData.addAll(historyEntities);
    }

    @Override
    public void historyEmpty() {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        mPresenter.getHistoryBydate(month + 1 + "/" + dayOfMonth);
        historyToolbar.setRightTitle(month + 1 + "/" + dayOfMonth);
    }
}
