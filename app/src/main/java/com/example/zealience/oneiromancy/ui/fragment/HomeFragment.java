package com.example.zealience.oneiromancy.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.zealience.oneiromancy.mvp.contract.HomeContract;
import com.example.zealience.oneiromancy.mvp.model.HomeModel;
import com.example.zealience.oneiromancy.mvp.presenter.HomePresenter;
import com.example.zealience.oneiromancy.ui.DreamTypeAdapter;
import com.example.zealience.oneiromancy.ui.activity.SearchActivity;
import com.example.zealience.oneiromancy.ui.activity.UserInfolActivity;
import com.example.zealience.oneiromancy.ui.activity.VRActivity;
import com.example.zealience.oneiromancy.ui.activity.WebViewActivity;
import com.example.zealience.oneiromancy.util.SpaceItemDecoration;
import com.example.zealience.oneiromancy.util.UserHelper;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.jaeger.library.StatusBarUtil;
import com.steven.base.app.BaseApp;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseFragment;
import com.steven.base.util.DisplayUtil;
import com.steven.base.util.GlideImageLoader;
import com.steven.base.util.GsonUtil;
import com.steven.base.util.SPUtils;
import com.steven.base.util.ToastUitl;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @user steven
 * @createDate 2019/3/6 18:02
 * @description 首页
 */
public class HomeFragment extends BaseFragment<HomePresenter, HomeModel> implements HomeContract.View, View.OnClickListener, IShareElements {
    private DreamTypeAdapter dreamTypeAdapter;
    private RecyclerView recyclerview_dream_type;
    private CircleImageView iv_user_head;
    private TextView et_search_dream;
    private ImageView iv_sao;
    private ImageView iv_small_snow;
    private Banner bannerContainer;
    private NestedScrollView nested_scrollview;
    private int column = 4;
    private int girdMargin = 10;
    private boolean isStopAnim;
    private List<DreamTypeEntity> mDreamTypeList = new ArrayList<>();

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
        iv_sao = (ImageView) rootView.findViewById(R.id.iv_sao);
        iv_small_snow = (ImageView) rootView.findViewById(R.id.iv_small_snow);
        bannerContainer = (Banner) rootView.findViewById(R.id.bannerContainer);
        nested_scrollview = (NestedScrollView) rootView.findViewById(R.id.nested_scrollview);
        GlideApp.with(_mActivity)
                .load(UserHelper.getUserInfo(_mActivity).getHeadImageUrl())
                .placeholder(R.mipmap.icon_user)
                .into(iv_user_head);
        initClick();
        initRecyclerView();
        initRabot();
        mPresenter.getHomeBannerData();
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("dreamType", mDreamTypeList.get(position));
                startActivity(SearchActivity.class, bundle);
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
            startActivity(intent, optionsBundle);
        }
        if (v == iv_user_head) {
            startActivity(UserInfolActivity.class);
        }
        if (v == iv_sao) {
            ToastUitl.showShort("功能待开发");
        }
        if (v == iv_small_snow) {
            Bundle bundle = new Bundle();
            bundle.putString(KeyConstant.URL_KEY, UrlConstant.URL_ZHIHU);
            WebViewActivity.startActivity(_mActivity, bundle);
        }
    }

    @Override
    public void setDreamTypeData(List<DreamTypeEntity> dreamTypeEntityList) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public ShareElementInfo[] getShareElements() {
        return new ShareElementInfo[]{new ShareElementInfo(et_search_dream, new TextViewStateSaver())};
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
}
