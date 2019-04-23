package com.example.zealience.oneiromancy.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.entity.UserDynamicEntity;
import com.hjq.bar.OnTitleBarListener;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.DateTimeHelper;
import com.steven.base.util.ToastUitl;

public class PublishDynamicActivity extends BaseActivity {

    /**
     * 说点什么吧
     */
    private EditText et_dynamic;

    private TextView tv_content_length;

    @Override
    public int getLayoutId() {
        return R.layout.activity_publish_dynamic;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("添加事件");
        setWhiteStatusBar(R.color.white);
        et_dynamic = (EditText) findViewById(R.id.et_dynamic);
        tv_content_length = (TextView) findViewById(R.id.tv_content_length);
        getTitlebar().setRightTitle("保存");
        getTitlebar().setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {

            }

            @Override
            public void onRightClick(View v) {
                String content = et_dynamic.getText().toString().trim();
                if (TextUtils.isEmpty(content)) {
                    ToastUitl.showTopToast(mContext, "内容不能为空");
                    return;
                }
                UserDynamicEntity dynamicEntity = new UserDynamicEntity();
                dynamicEntity.setDate(DateTimeHelper.getCurrentSystemTime());
                dynamicEntity.setContent(et_dynamic.getText().toString().trim());
                Intent intent = new Intent();
                intent.putExtra("dynamicEntity", dynamicEntity);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        et_dynamic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    getTitlebar().getRightView().setTextColor(ContextCompat.getColor(PublishDynamicActivity.this, R.color.color_333));
                } else {
                    getTitlebar().getRightView().setTextColor(ContextCompat.getColor(PublishDynamicActivity.this, R.color.color_999999));
                }
                tv_content_length.setText(s.length() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
