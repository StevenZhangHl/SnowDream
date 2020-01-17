package com.example.zealience.oneiromancy.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseActivity;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

public class CaptureActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_return_capture;
    private LinearLayout ll_light;
    private ImageView iv_light;

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        iv_light = (ImageView) findViewById(R.id.iv_light);
        iv_return_capture = (ImageView) findViewById(R.id.iv_return_capture);
        ll_light = (LinearLayout) findViewById(R.id.ll_light);
        CaptureFragment captureFragment = new CaptureFragment();
        CodeUtils.setFragmentArgs(captureFragment, R.layout.fragment_capture);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, captureFragment).commit();
        ll_light.setOnClickListener(this);
        iv_return_capture.setOnClickListener(this);
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }

        @Override
        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(RESULT_OK, resultIntent);
            CaptureActivity.this.finish();
        }
    };
    private boolean isLightEnable = false;

    @Override
    public void onClick(View v) {
        if (v == iv_return_capture) {
            finish();
        }
        if (v == ll_light) {
            if (isLightEnable) {
                CodeUtils.isLightEnable(false);
                isLightEnable = false;
                iv_light.setImageResource(R.mipmap.icon_light_close);
            } else {
                CodeUtils.isLightEnable(true);
                iv_light.setImageResource(R.mipmap.icon_light_open);
                isLightEnable = true;
            }
        }
    }
}
