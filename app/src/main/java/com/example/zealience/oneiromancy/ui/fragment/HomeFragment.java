package com.example.zealience.oneiromancy.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.example.zealience.oneiromancy.constant.SharePConstant;
import com.example.zealience.oneiromancy.constant.SnowConstant;
import com.example.zealience.oneiromancy.constant.UrlConstant;
import com.example.zealience.oneiromancy.entity.DreamTypeEntity;
import com.example.zealience.oneiromancy.entity.EventEntity;
import com.example.zealience.oneiromancy.entity.HomeRecommendEntity;
import com.example.zealience.oneiromancy.mvp.contract.HomeContract;
import com.example.zealience.oneiromancy.mvp.model.HomeModel;
import com.example.zealience.oneiromancy.mvp.presenter.HomePresenter;
import com.example.zealience.oneiromancy.ui.DreamHotAdapter;
import com.example.zealience.oneiromancy.ui.DreamTypeAdapter;
import com.example.zealience.oneiromancy.ui.activity.CaptureActivity;
import com.example.zealience.oneiromancy.ui.activity.ChannelManagerActivity;
import com.example.zealience.oneiromancy.ui.activity.SearchActivity;
import com.example.zealience.oneiromancy.ui.activity.ShowScanResultActivity;
import com.example.zealience.oneiromancy.ui.activity.UserInfolActivity;
import com.example.zealience.oneiromancy.ui.activity.WebViewActivity;
import com.example.zealience.oneiromancy.ui.widget.AdDialog;
import com.example.zealience.oneiromancy.util.SpaceItemDecoration;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.steven.base.app.BaseApp;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseFragment;
import com.steven.base.util.AnimationUtils;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.GlideImageLoader;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
import com.steven.base.util.ToastUitl;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.WeakHandler;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZMediaManager;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdMgr;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/6 18:02
 * @description 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter, HomeModel> implements HomeContract.View, View.OnClickListener, IShareElements {
    private static final int REQUEST_CODE = 1;
    private DreamTypeAdapter dreamTypeAdapter;
    private RecyclerView recyclerview_dream_type;
    private CircleImageView iv_user_head;
    private TextView et_search_dream;
    private LinearLayout ll_search;
    private ImageView iv_sao;
    private ImageView iv_small_snow;
    private Banner bannerContainer;
    private NestedScrollView nested_scrollview;
    private RecyclerView recyclerview_dream_hot;
    private DreamHotAdapter dreamHotAdapter;
    private int column = 5;
    private int girdMargin = 10;
    private boolean isStopAnim;
    private List<DreamTypeEntity> mDreamTypeList = new ArrayList<>();
    private String[] mHotSearchData;
    private Handler handlerSearch = new Handler();
    private int count = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        YcShareElement.enableContentTransition(BaseApp.getInstance());
        recyclerview_dream_type = (RecyclerView) rootView.findViewById(R.id.recyclerview_dream_type);
        et_search_dream = (TextView) rootView.findViewById(R.id.et_search_dream);
        iv_user_head = (CircleImageView) rootView.findViewById(R.id.iv_user_head);
        ll_search = (LinearLayout) rootView.findViewById(R.id.ll_search);
        iv_sao = (ImageView) rootView.findViewById(R.id.iv_sao);
        iv_small_snow = (ImageView) rootView.findViewById(R.id.iv_small_snow);
        bannerContainer = (Banner) rootView.findViewById(R.id.bannerContainer);
        nested_scrollview = (NestedScrollView) rootView.findViewById(R.id.nested_scrollview);
        recyclerview_dream_hot = (RecyclerView) rootView.findViewById(R.id.recyclerview_dream_hot);
        GlideApp.with(_mActivity)
                .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(iv_user_head);
        initClick();
        initRecyclerView();
        initRabot();
        mPresenter.getHomeBannerData();
        mPresenter.getHotSearchData();
        mPresenter.getHomeRecommendData();
        mPresenter.getAppActivityData();
        EventBus.getDefault().register(this);
    }

    /**
     * 初始化机器人
     */
    private void initRabot() {
        GlideApp.with(this)
                .asGif()
                .load(R.drawable.home_ali)
                .into(iv_small_snow);
    }

    private void initRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(_mActivity, column);
        gridLayoutManager.setSmoothScrollbarEnabled(true);
        gridLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_dream_type.setHasFixedSize(true);
        recyclerview_dream_type.setNestedScrollingEnabled(false);
        recyclerview_dream_type.setLayoutManager(gridLayoutManager);
        recyclerview_dream_type.addItemDecoration(new SpaceItemDecoration(DisplayUtil.dip2px(girdMargin), column));
        dreamTypeAdapter = new DreamTypeAdapter(R.layout.item_dream_type, new ArrayList<>());
        recyclerview_dream_type.setAdapter(dreamTypeAdapter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nested_scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    /**
                     * 第一个参数NestedScrollView v:是NestedScrollView的对象
                     * 第二个参数:scrollX是目前的（滑动后）的X轴坐标
                     * 第三个参数:ScrollY是目前的（滑动后）的Y轴坐标
                     * 第四个参数:oldScrollX是之前的（滑动前）的X轴坐标
                     * 第五个参数:oldScrollY是之前的（滑动前）的Y轴坐标
                     */
                    if (scrollY > oldScrollY) {
                        //向下滑动

                    }
                    if (scrollY < oldScrollY) {
                        //向下滑动
                    }
                    if (DisplayUtil.px2dip(scrollY) <= 150) {
                        int imageWidth = DisplayUtil.dip2px(50) - 2 * scrollY / 15;
                        ViewGroup.LayoutParams layoutParams = iv_user_head.getLayoutParams();
                        layoutParams.width = imageWidth;
                        layoutParams.height = imageWidth;
                        iv_user_head.setLayoutParams(layoutParams);
                    }
                    Log.i("distance--------->", scrollY + "");
                }
            });
        }
        if (!TextUtils.isEmpty(SPUtils.getSharedStringData(_mActivity, SharePConstant.KEY_DREAM_DATA))) {
            setDreamTypeData(GsonUtil.GsonToList(SPUtils.getSharedStringData(_mActivity, SharePConstant.KEY_DREAM_DATA), DreamTypeEntity.class));
        } else {
            mPresenter.getHomeDreamTypeData();
        }
        recyclerview_dream_type.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position == 9) {
                    startActivity(ChannelManagerActivity.class);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("dreamType", mDreamTypeList.get(position));
                    startActivity(SearchActivity.class, bundle);
                }
            }
        });
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(i + "我");
        }
        dreamHotAdapter = new DreamHotAdapter(new ArrayList<>());
        dreamHotAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(_mActivity);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerview_dream_hot.setHasFixedSize(true);
        recyclerview_dream_hot.setNestedScrollingEnabled(false);
        recyclerview_dream_hot.setLayoutManager(linearLayoutManager);
        recyclerview_dream_hot.setAdapter(dreamHotAdapter);
        recyclerview_dream_hot.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                Jzvd jzvd = view.findViewById(R.id.jz_video);
                if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                    Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();
                    if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
    }

    private void initClick() {
        et_search_dream.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);
        iv_sao.setOnClickListener(this);
        iv_small_snow.setOnClickListener(this);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onClick(View v) {
        if (v == et_search_dream) {
            Intent intent = new Intent(_mActivity, SearchActivity.class);
            Bundle optionsBundle = YcShareElement.buildOptionsBundle(_mActivity, HomeFragment.this);
            intent.putExtra(KeyConstant.HOME_HOT_SEARCH_KEY, et_search_dream.getText().toString());
            startActivity(intent, optionsBundle);
        }
        if (v == iv_user_head) {
            startActivity(UserInfolActivity.class);
        }
        if (v == iv_sao) {
            startActivityForResult(CaptureActivity.class, REQUEST_CODE);
        }
        if (v == iv_small_snow) {
            Bundle bundle = new Bundle();
            bundle.putString(KeyConstant.URL_KEY, UrlConstant.URL_MYGITHUB_HOME);
            WebViewActivity.startActivity(_mActivity, bundle);
        }
    }

    @Override
    public void setDreamTypeData(List<DreamTypeEntity> dreamTypeEntityList) {
        for (int i = 0; i < dreamTypeEntityList.size(); i++) {
            if (dreamTypeEntityList.get(i).getName().equals("其他类")) {
                dreamTypeEntityList.remove(i);
            }
        }
        dreamTypeAdapter.setNewData(dreamTypeEntityList);
        nested_scrollview.smoothScrollTo(0, 0);
        mDreamTypeList.clear();
        mDreamTypeList.addAll(dreamTypeEntityList);
    }

    @Override
    public void setBannerDdata(List<Integer> images) {
        bannerContainer.setBannerAnimation(Transformer.Accordion)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setImageLoader(new GlideImageLoader())
                .setImages(images)
                .setOnBannerListener(new OnBannerListener() {
                    @Override
                    public void OnBannerClick(int position) {
                        ToastUitl.showShort(position + "");
                    }
                }).start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            et_search_dream.setText(mHotSearchData[count]);
            AnimationUtils.scaleView(et_search_dream, 1000, 0, 1);
            count++;
            if (count == mHotSearchData.length) {
                count = 0;
            }
            handlerSearch.postDelayed(this, 5000);
        }
    };

    @Override
    public void setHotSearchData(String[] hotSearchData) {
        mHotSearchData = hotSearchData;
        handlerSearch.post(runnable);
    }

    @Override
    public void setRecommendData(List<HomeRecommendEntity> homeRecommendEntities) {
        dreamHotAdapter.setNewData(homeRecommendEntities);
    }

    private WeakHandler mHandler = new WeakHandler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            adDialog.show();
        }
    };
    private AdDialog adDialog;

    @Override
    public void showAppAdv(String url, String functionUrl) {
        adDialog = new AdDialog(_mActivity, url, functionUrl);
        mHandler.postDelayed(mRunnable, 1500);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return new ShareElementInfo[]{new ShareElementInfo(ll_search, new TextViewStateSaver())};
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (hidden) {
            bannerContainer.stopAutoPlay();
        } else {
            bannerContainer.startAutoPlay();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventEntity event) {
        if (SnowConstant.EVENT_UPDATE_USER_HEAD.equals(event.getEvent())) {
            GlideApp.with(_mActivity)
                    .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                    .into(iv_user_head);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (!TextUtils.isEmpty(result)) {
                        if (result.startsWith("http://") || result.startsWith("https://")) {
                            bundle.putString(KeyConstant.URL_KEY, result);
                            WebViewActivity.startActivity(_mActivity, bundle);
                        } else {
                            startActivity(ShowScanResultActivity.class, bundle);
                        }
                    }
                }
            }
        }
    }
}
