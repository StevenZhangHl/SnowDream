package com.example.zealience.oneiromancy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.example.zealience.oneiromancy.entity.DreamEntity;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.mvp.contract.HomeContract;
import com.example.zealience.oneiromancy.mvp.model.HomeModel;
import com.example.zealience.oneiromancy.mvp.presenter.HomePresenter;
import com.example.zealience.oneiromancy.ui.DreamTypeAdapter;
import com.example.zealience.oneiromancy.ui.activity.SearchActivity;
import com.example.zealience.oneiromancy.ui.activity.SetingActivity;
import com.example.zealience.oneiromancy.ui.activity.UserPhotoDetailActivity;
import com.example.zealience.oneiromancy.ui.activity.VRActivity;
import com.example.zealience.oneiromancy.ui.activity.WebViewActivity;
import com.example.zealience.oneiromancy.util.SpaceItemDecoration;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.jaeger.library.StatusBarUtil;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
import com.steven.base.util.TiaoZiUtil;
import com.example.zealience.oneiromancy.util.TiaoZUtil;
import com.steven.base.widget.FloatBallView;
import com.steven.base.widget.FloatView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity<HomePresenter, HomeModel> implements HomeContract.View, View.OnClickListener, IShareElements {
    private DreamTypeAdapter dreamTypeAdapter;
    private RecyclerView recyclerview_dream_type;
    private CircleImageView iv_user_head;
    private TextView et_search_dream;
    private TextView tv_one;
    private ImageView iv_vr;
    private ImageView iv_set;
    private ImageView iv_small_snow;
    private FloatView float_view;
    private int column = 4;
    private int girdMargin = 10;
    private String[] args = {"红豆生南国", "春来发几枝", "愿君多采撷", "此物最相思", "玲珑骰子安红豆", "入骨相思知不知"};
    int time = 0;
    private List<DreamTypeEntity> mDreamTypeList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        YcShareElement.enableContentTransition(getApplication());
        recyclerview_dream_type = (RecyclerView) findViewById(R.id.recyclerview_dream_type);
        et_search_dream = (TextView) findViewById(R.id.et_search_dream);
        iv_user_head = (CircleImageView) findViewById(R.id.iv_user_head);
        tv_one = (TextView) findViewById(R.id.tv_one);
        iv_vr = (ImageView) findViewById(R.id.iv_vr);
        iv_set = (ImageView) findViewById(R.id.iv_set);
        iv_small_snow = (ImageView) findViewById(R.id.iv_small_snow);
        float_view = (FloatView) findViewById(R.id.float_view);
        dreamTypeAdapter = new DreamTypeAdapter(R.layout.item_dream_type, new ArrayList<>());
        recyclerview_dream_type.setLayoutManager(new GridLayoutManager(this, column));
        recyclerview_dream_type.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(girdMargin), column));
        recyclerview_dream_type.setAdapter(dreamTypeAdapter);
        if (!TextUtils.isEmpty(SPUtils.getSharedStringData(this, SharePConstant.KEY_DREAM_DATA))) {
            setDreamTypeData(GsonUtil.GsonToList(SPUtils.getSharedStringData(this, SharePConstant.KEY_DREAM_DATA), DreamTypeEntity.class));
        } else {
            mPresenter.getHomeDreamTypeData();
        }
//        new TiaoZiUtil(tv_one, "老婆我爱你", 400);
        initClick();
        GlideApp.with(this)
                .asGif()
                .load(R.drawable.home_ali)
                .into(iv_small_snow);
        float_view.setmDragView(iv_small_snow);
        recyclerview_dream_type.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("dreamType", mDreamTypeList.get(position));
                startActivity(SearchActivity.class, bundle);
            }
        });
    }

    private void initClick() {
        et_search_dream.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);
        iv_vr.setOnClickListener(this);
        iv_set.setOnClickListener(this);
        iv_small_snow.setOnClickListener(this);
    }

    @Override
    public void setDreamTypeData(List<DreamTypeEntity> dreamTypeEntityList) {
        dreamTypeAdapter.setNewData(dreamTypeEntityList);
        mDreamTypeList.clear();
        mDreamTypeList.addAll(dreamTypeEntityList);
    }

    @Override
    public void onClick(View v) {
        if (v == et_search_dream) {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            Bundle optionsBundle = YcShareElement.buildOptionsBundle(MainActivity.this, MainActivity.this);
            startActivity(intent, optionsBundle);
        }
        if (v == iv_user_head) {
            startActivity(UserPhotoDetailActivity.class);
        }
        if (v == iv_vr) {
            startActivity(VRActivity.class);
        }
        if (v == iv_set) {
            startActivity(SetingActivity.class);
        }
        if (v == iv_small_snow) {
            startActivity(WebViewActivity.class);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return new ShareElementInfo[]{new ShareElementInfo(et_search_dream, new TextViewStateSaver())};
    }
}
