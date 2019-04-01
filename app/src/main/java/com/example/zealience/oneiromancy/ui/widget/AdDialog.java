package com.example.zealience.oneiromancy.ui.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;
import com.example.zealience.oneiromancy.util.PageJumpUtil;
import com.steven.base.R;
import com.steven.base.app.GlideApp;
import com.steven.base.util.BitmapUtil;
import com.steven.base.util.DisplayUtil;

/**
 * @user steven
 * @createDate 2019/3/29 16:11
 * @description 弹屏广告
 */
public class AdDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private float finalWidth;        //最终宽度
    private float finalHeight;    //最终高度
    private int padding = 50;        //屏幕边距间隔
    private int picWidth;        //图片真实宽度
    private int picHeight;        //图片真实高度
    private String mUrl;
    private String mFuctionUrl;
    private int toBottomDistance = 0;


    public AdDialog(Context context, String url, String fuctionUrl) {
        // 更改样式,把背景设置为透明的
        super(context, R.style.CustomProgressDialog);
        this.context = context;
        mUrl = url;
        this.mFuctionUrl = fuctionUrl;
    }

    public AdDialog(Context context, String url, String fuctionUrl, int toBottomDistance) {
        // 更改样式,把背景设置为透明的
        super(context, R.style.CustomProgressDialog);
        this.context = context;
        mUrl = url;
        this.toBottomDistance = toBottomDistance;
        this.mFuctionUrl = fuctionUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_advertisement);
        calculateHeightAndWidth();
        //初始化布局的位置
        initLayoutParams();
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 按实际图片比例对其的宽高进行缩放
     */
    private void calculateHeightAndWidth() {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        final int screenWidth = dm.widthPixels;
        final int screenHeight = dm.heightPixels;
        //获取图片真正的宽高
        GlideApp.with(context)
                .load(mUrl)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        Bitmap bitmap = BitmapUtil.drawableToBitmap(resource);
                        picWidth = bitmap.getWidth();
                        picHeight = bitmap.getHeight();
                        if (picHeight > picWidth) {
                            //真实图片高度大于宽度时
                            finalHeight = screenWidth * 3 / 4 * picHeight / picWidth;
                            finalWidth = screenWidth * 3 / 4;
                        } else {
                            //真实图片宽度大于高度时
                            finalWidth = screenWidth - padding * 2;
                            finalHeight = ((picHeight * 100) / picWidth) * finalWidth / 100;
                        }
                        if ((int) finalWidth > screenWidth || (int) finalHeight > screenHeight) {
                            //放大后的比例超出屏幕时
                            finalWidth = picWidth;
                            finalHeight = picHeight;
                        }
                        initAdView(finalWidth, finalHeight);
                    }
                });
    }

    @SuppressLint("ResourceType")
    private void initAdView(float width, float height) {
        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.rl_ad);
        ImageView ivAd = new ImageView(context);
        ivAd.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ivAd.setId(1);
        ivAd.setOnClickListener(this);
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams((int) width, (int) height);
        params1.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params1.setMargins(0, 0, 0, DisplayUtil.dip2px(context, 10));
        relativeLayout.addView(ivAd, params1);
        DrawableCrossFadeFactory drawableCrossFadeFactory = new DrawableCrossFadeFactory.Builder(300).setCrossFadeEnabled(true).build();
        GlideApp.with(context)
                .load(mUrl)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                .dontAnimate()
                .transition(DrawableTransitionOptions.with(drawableCrossFadeFactory))
                .into(ivAd);
        ImageView ivCancel = new ImageView(context);
        ivCancel.setId(2);
        ivCancel.setOnClickListener(this);
        ivCancel.setImageResource(R.mipmap.icon_close_adv);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.topMargin = DisplayUtil.dip2px(context, 20);
        params2.gravity = Gravity.CENTER_HORIZONTAL;
        relativeLayout.addView(ivCancel, params2);
    }

    // 初始化布局的参数
    private void initLayoutParams() {
        Window window = getWindow();
        // 布局的参数
        WindowManager.LayoutParams params = window.getAttributes();
        if (toBottomDistance == 0) {
            params.gravity = Gravity.CENTER_HORIZONTAL;
        } else {
            window.setWindowAnimations(R.style.dialogWindowAnim); //设置窗口弹出动画
            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            params.y = toBottomDistance;
        }
        //根据x，y坐标设置窗口需要显示的位置
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case 1:
                PageJumpUtil.jumpToAppointpage(context,
                        mFuctionUrl, "");
                dismiss();
                break;
            case 2:
                dismiss();
                break;
        }
    }
}
