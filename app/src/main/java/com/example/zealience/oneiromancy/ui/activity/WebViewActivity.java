package com.example.zealience.oneiromancy.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.steven.base.base.BaseActivity;

public class WebViewActivity extends BaseActivity {
    private WebView webView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        showTitle("");
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                String log = "错误信息：" + cm.message() + "。发生错误的代码行数："
                        + cm.lineNumber() + "。资源id："
                        + cm.sourceId();
                return true;
            }
        });
        webView.loadUrl("https://www.zhihu.com/topic/19556496/hot");
    }
}
