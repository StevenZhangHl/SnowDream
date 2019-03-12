package com.example.zealience.oneiromancy.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.mvp.contract.SearchContract;
import com.example.zealience.oneiromancy.mvp.model.SearchModel;
import com.example.zealience.oneiromancy.mvp.presenter.SearchPresenter;
import com.example.zealience.oneiromancy.ui.SearchDreamAdapter;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.steven.base.app.BaseApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity<SearchPresenter, SearchModel> implements IShareElements, SearchContract.View, TextView.OnEditorActionListener, View.OnClickListener, TextWatcher {
    private EditText et_search;
    private ImageView iv_back;
    private TextView tv_search;
    private String searchContent;
    private RecyclerView recyclerview_dream_list;
    private SearchDreamAdapter searchDreamAdapter;
    private List<DreamEntity> dreamEntityList = new ArrayList<>();
    private DreamTypeEntity dreamTypeEntity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setWhiteStatusBar(R.color.white);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_search = (TextView) findViewById(R.id.tv_search);
        recyclerview_dream_list = (RecyclerView) findViewById(R.id.recyclerview_dream_list);
        YcShareElement.postStartTransition(this);
        et_search.setOnEditorActionListener(this);
        iv_back.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        et_search.addTextChangedListener(this);
        searchDreamAdapter = new SearchDreamAdapter(R.layout.item_search_dream, new ArrayList<>());
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerview_dream_list.setLayoutManager(gridLayoutManager);
        recyclerview_dream_list.setAdapter(searchDreamAdapter);
        recyclerview_dream_list.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                intent.setClass(SearchActivity.this, DreamDetailActivity.class);
                intent.putExtra("dreamInfo", dreamEntityList.get(position));
                startActivity(intent);
            }
        });
        if (getIntent() != null && getIntent().getExtras() != null) {
            dreamTypeEntity = (DreamTypeEntity) getIntent().getExtras().getSerializable("dreamType");
        }
        if (dreamTypeEntity != null) {
            et_search.setHint(dreamTypeEntity.getName() + "相关");
        }
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return new ShareElementInfo[]{new ShareElementInfo(et_search, new TextViewStateSaver())};
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchResult(searchContent);
            KeyboardUtil.closeKeybord(et_search, SearchActivity.this);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back) {
            onBackPressed();
        }
        if (v == tv_search) {
            searchResult(searchContent);
            KeyboardUtil.closeKeybord(et_search, SearchActivity.this);
        }
    }

    @Override
    public void onBackPressed() {
        KeyboardUtil.closeKeybord(et_search, this);
        super.onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchContent = s.toString();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void searchResult(String searchContent) {
        int cid = -1;
        if (dreamTypeEntity != null) {
            cid = Integer.parseInt(dreamTypeEntity.getFid());
        }
        mPresenter.searchDreamData(searchContent, cid);
    }

    @Override
    public void setDreamData(List<DreamEntity> dreamEntities) {
        searchDreamAdapter.setNewData(dreamEntities);
        dreamEntityList.clear();
        dreamEntityList.addAll(dreamEntities);
    }
}
