package com.example.zealience.oneiromancy.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zealience.oneiromancy.R;
import com.example.zealience.oneiromancy.constant.KeyConstant;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.hjq.bar.OnTitleBarListener;
import com.steven.base.app.BaseApp;
import com.steven.base.base.AppManager;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.ProviderUtil;
import com.steven.base.util.ToastUitl;
import com.steven.base.widget.SheetDialog;

public class WebViewActivity extends BaseActivity implements OnTitleBarListener {
    private WebView webView;
    private ProgressBar mProgressBar;
    private String url;
    private String currentUrl;
    private int webType = 0;
    private SheetDialog sheetDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initPresenter() {

    }

    public static void startActivity(Context context, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra(KeyConstant.URL_BUNDLE_KEY, bundle);
        context.startActivity(intent, bundle);
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        showTitle("");
        getTitlebar().setOnTitleBarListener(this);
        setWhiteStatusBar(R.color.white);
        webView = (WebView) findViewById(R.id.webView);
        mProgressBar = (ProgressBar) findViewById(R.id.mProgressBar);
        boolean isShowShare = getIntent().getBundleExtra(KeyConstant.URL_BUNDLE_KEY).getBoolean(KeyConstant.ISSHOW_SHARE_KEY);
        WebSettings settings = webView.getSettings();
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDisplayZoomControls(false);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        webView.requestFocus();
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onConsoleMessage(ConsoleMessage cm) {
                String log = "错误信息：" + cm.message() + "。发生错误的代码行数："
                        + cm.lineNumber() + "。资源id："
                        + cm.sourceId();
                return true;
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                getTitlebar().setTitle(title);
            }

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {
                    mProgressBar.setProgress(progress);
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setProgress(progress);
                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                currentUrl = url;
                if (isShowShare) {
                    getTitlebar().setRightIcon(R.mipmap.icon_share);
                    webType = 0;
                } else {
                    getTitlebar().setRightIcon(R.mipmap.icon_web_more);
                    getTitlebar().setLeftIcon(R.mipmap.icon_close);
                    webType = 1;
                }
            }
        });
        url = getIntent().getBundleExtra(KeyConstant.URL_BUNDLE_KEY).getString(KeyConstant.URL_KEY);
        webView.loadUrl(url);
        initDialog();
    }

    private void initDialog() {
        sheetDialog = new SheetDialog(this, new String[]{"在浏览器中打开", "复制链接", "收藏"}, null);
        sheetDialog.isTitleShow(false);
        sheetDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (TextUtils.isEmpty(currentUrl)) {
                            ToastUitl.showShort("客官别急，页面还没加载完成呢");
                            return;
                        }
                        ProviderUtil.startChromeByUrl(WebViewActivity.this, currentUrl);
                        break;
                    case 1:
                        ProviderUtil.copyManager(WebViewActivity.this, currentUrl);
                        ToastUitl.showShort("已复制");
                        break;
                }
                sheetDialog.dismiss();
            }
        });
    }

    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    public void onTitleClick(View v) {

    }

    @Override
    public void onRightClick(View v) {
        if (webType == 0) {
            if (TextUtils.isEmpty(currentUrl)) {
                ToastUitl.showShort("客官别急，页面还没加载完成呢");
                return;
            }
            ProviderUtil.startLocalShareText(this, currentUrl);
        } else {
            sheetDialog.show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                webView.goForward();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);

    }
}
