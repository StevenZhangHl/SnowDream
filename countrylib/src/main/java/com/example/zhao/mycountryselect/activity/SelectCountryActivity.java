package com.example.zhao.mycountryselect.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhao.mycountryselect.R;
import com.example.zhao.mycountryselect.adapter.CountrySelectAdapter;
import com.example.zhao.mycountryselect.bean.CountryBean;
import com.example.zhao.mycountryselect.utils.DividerItemDecoration;
import com.example.zhao.mycountryselect.utils.OnItemClickListener;
import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SelectCountryActivity extends AppCompatActivity {

    public final static int REQUEST_ZONE = 66;
    public final static String REQUEST_OUTPUT = "zone";

    private ImageView ivBackClick;
    private TextView tvTitle;
    private EditText etSearch;
    private ImageView ivSearchClick;
    private RelativeLayout rlBaseTitleBe;
    private RecyclerView mRecyclerView;
    private IndexBar mIndexBar;
    private TextView mTvSideBarHint;

    private Context mContext;
    private CountrySelectAdapter mAdapter;
    private LinearLayoutManager mManager;

    //主体部分数据源（城市数据）
    private List<CountryBean> mBodyDatas;
    //搜索用
    private List<CountryBean> mBodyDatas2;
    //设置给InexBar、ItemDecoration的完整数据集
    private List<BaseIndexPinyinBean> mSourceDatas;
    //悬停
    private SuspensionDecoration mDecoration;

    /**
     * 国家列表数据
     * HashMap<国家/地区，区号>
     */
    private HashMap<String, String> listHashMap;

    public static void startResultAction(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, SelectCountryActivity.class);
        activity.startActivityForResult(intent, REQUEST_ZONE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        mContext = this;

        setColor();

        findByIdView();

        onCreateView();

        initData();

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, Object o, int position) {
                setResult(RESULT_OK, new Intent().putExtra(REQUEST_OUTPUT, mBodyDatas.get(position)));
                finish();
            }

            @Override
            public boolean onItemLongClick(ViewGroup parent, View view, Object o, int position) {
                return false;
            }
        });
    }

    private void setColor() {
        //设置状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.parseColor("#000000"));
        }
    }

    private void findByIdView() {
        ivBackClick = (ImageView) findViewById(R.id.iv_back_click);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        etSearch = (EditText) findViewById(R.id.et_search);
        ivSearchClick = (ImageView) findViewById(R.id.iv_search_click);
        rlBaseTitleBe = (RelativeLayout) findViewById(R.id.rl_base_title_be);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mIndexBar = (IndexBar) findViewById(R.id.indexBar);
        mTvSideBarHint = (TextView) findViewById(R.id.tvSideBarHint);

        ivBackClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectCountryActivity.super.onBackPressed();
            }
        });

        ivSearchClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示输入框
                etSearch.setVisibility(View.VISIBLE);
                //隐藏标题
                tvTitle.setVisibility(View.GONE);
            }
        });

        //监听输入框
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                }
                search();
            }
        });
    }

    /**
     * 检索城市
     */
    private void search() {
        String searchData = etSearch.getText().toString().trim();
        mBodyDatas.clear();
        for (CountryBean countryBean : mBodyDatas2) {
            String ageStr = countryBean.getCity() + "";
            //只要检索的字符,被包含,即可展示出来
            if (ageStr.contains(searchData)) {
                mBodyDatas.add(countryBean);
            }
        }
        //排序
        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);

        mAdapter.notifyDataSetChanged();

    }

    /**
     * onCreateView
     */
    private void onCreateView() {

        ////////////////////////////
        listHashMap = new HashMap<>();
        Resources res = getResources();
        for (int i = (int) 'a'; i < 'a' + 26; i++) {
            System.out.println(" " + (char) i);
            String parm = "smssdk_country_group_" + (char) i;
            int id = res.getIdentifier(parm, "array", getPackageName());
            String[] stringArray = getResources().getStringArray(id);
            for (String str : stringArray) {
                String[] split = str.split("[,]");
                listHashMap.put(split[0], split[1]);
            }
        }
        ////////////////////////////

        mRecyclerView.setLayoutManager(mManager = new LinearLayoutManager(mContext));

        mSourceDatas = new ArrayList<>();

        mAdapter = new CountrySelectAdapter(mContext, R.layout.item_select_country, mBodyDatas);

        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addItemDecoration(mDecoration = new SuspensionDecoration(this, mSourceDatas)
                .setmTitleHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, getResources().getDisplayMetrics()))
                .setColorTitleBg(0xffefefef)
                .setTitleFontSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()))
                .setColorTitleFont(mContext.getResources().getColor(android.R.color.black))
        );
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST));

        mIndexBar.setmPressedShowTextView(mTvSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
    }

    /**
     * 组织数据源
     */
    private void initData() {
        mBodyDatas = new ArrayList<>();
        mBodyDatas2 = new ArrayList<>();

        Iterator iter = listHashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            //
            CountryBean cityBean = new CountryBean();
            cityBean.setCity(key);//设置城市名称
            cityBean.setZone("+" + value);//设置国家区号(发短信用)
            mBodyDatas.add(cityBean);
            mBodyDatas2.add(cityBean);
        }

        //先排序
        mIndexBar.getDataHelper().sortSourceDatas(mBodyDatas);

        mAdapter.setDatas(mBodyDatas);
        mSourceDatas.addAll(mBodyDatas);

        mIndexBar.setmSourceDatas(mSourceDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(mSourceDatas);

    }

}
