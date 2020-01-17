package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.ui.DreamDetailAdapter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;

import java.util.ArrayList;

public class DreamDetailActivity extends BaseActivity {
    private AppBarLayout appBar;
    private CollapsingToolbarLayout toolbarLayout;
    private FloatingActionButton fab;
    private RecyclerView recyclerview_dream_detail_list;
    private DreamDetailAdapter dreamDetailAdapter;
    private TitleBar dream_detail_titlebar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dream_detail;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.activity_dream_detail);
        DreamEntity entity = (DreamEntity) getIntent().getSerializableExtra("dreamInfo");
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        dream_detail_titlebar = (TitleBar) findViewById(R.id.dream_detail_titlebar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerview_dream_detail_list = (RecyclerView) findViewById(R.id.recyclerview_dream_detail_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, entity.getTitle(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        toolbarLayout.setTitle(entity.getTitle());
        recyclerview_dream_detail_list.setLayoutManager(new LinearLayoutManager(this));
        dreamDetailAdapter = new DreamDetailAdapter(R.layout.item_dream_detail, new ArrayList<>());
        recyclerview_dream_detail_list.setAdapter(dreamDetailAdapter);
        dreamDetailAdapter.setNewData(entity.getList());
        dream_detail_titlebar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
