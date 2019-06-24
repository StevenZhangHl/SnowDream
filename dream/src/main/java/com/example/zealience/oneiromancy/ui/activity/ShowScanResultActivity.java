package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class ShowScanResultActivity extends BaseActivity {
    private TextView tv_scan_result;

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_scan_result;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("扫描结果");
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);
        Bundle bundle = getIntent().getExtras();
        tv_scan_result.setText(bundle.getString(CodeUtils.RESULT_STRING));
    }
}
