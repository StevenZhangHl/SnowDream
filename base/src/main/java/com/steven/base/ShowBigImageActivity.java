package com.steven.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.hw.ycshareelement.YcShareElement;
import com.hw.ycshareelement.transition.IShareElements;
import com.hw.ycshareelement.transition.ShareElementInfo;
import com.hw.ycshareelement.transition.TextViewStateSaver;
import com.jaeger.library.StatusBarUtil;
import com.steven.base.app.GlideApp;
import com.steven.base.base.BaseActivity;
import com.steven.base.util.GlideFileHelper;
import com.steven.base.widget.SheetDialog;
import com.steven.base.widget.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ShowBigImageActivity extends BaseActivity {
    public static final String INTENT_IMGURLS = "imgurls";
    public static final String INTENT_LIST = "imageResouceId";
    public static final String INTENT_POSITION = "position";
    private List<View> guideViewList = new ArrayList<View>();
    private LinearLayout guideGroup;
    private GlideFileHelper glideFileHelper;
    private ViewPager viewPager;

    public static void startImagePagerActivity(Context activity, List<String> imgUrls, int position) {
        Intent intent = new Intent(activity, ShowBigImageActivity.class);
        intent.putStringArrayListExtra(INTENT_IMGURLS, new ArrayList<String>(imgUrls));
        intent.putExtra(INTENT_POSITION, position);
        activity.startActivity(intent);
    }

    public static void startImageForResouceId(AppCompatActivity activity, List<Integer> imageResouceIds, int position) {
        Intent intent = new Intent(activity, ShowBigImageActivity.class);
        intent.putIntegerArrayListExtra(INTENT_LIST, new ArrayList<Integer>(imageResouceIds));
        intent.putExtra(INTENT_POSITION, position);
        activity.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_show_big_image;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void setFitsSystemWindows() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // 隐藏系统状态栏
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        YcShareElement.postStartTransition(this);
        // 保存图片工具类
        glideFileHelper = new GlideFileHelper(mContext);
        viewPager = (ViewPagerFixed) findViewById(R.id.pager);
        guideGroup = findViewById(R.id.guideGroup);
        int startPos = getIntent().getIntExtra(INTENT_POSITION, 0);
        ArrayList<String> imgUrls = getIntent().getStringArrayListExtra(INTENT_IMGURLS);
        ArrayList<Integer> imageResouceIds = getIntent().getIntegerArrayListExtra(INTENT_LIST);

        ImageAdapter mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(imgUrls, imageResouceIds);
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < guideViewList.size(); i++) {
                    guideViewList.get(i).setSelected(i == position ? true : false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(startPos);

        addGuideView(guideGroup, startPos, imgUrls, imageResouceIds);
    }

    private void addGuideView(LinearLayout guideGroup, int startPos, ArrayList<String> imgUrls, ArrayList<Integer> imageResouceIds) {
        // 大于一张图片添加引导点
        if (imgUrls != null && imgUrls.size() > 1) {
            guideViewList.clear();
            for (int i = 0; i < imgUrls.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
        // 大于一张图片添加引导点
        if (imageResouceIds != null && imageResouceIds.size() > 1) {
            guideViewList.clear();
            for (int i = 0; i < imageResouceIds.size(); i++) {
                View view = new View(this);
                view.setBackgroundResource(R.drawable.selector_guide_bg);
                view.setSelected(i == startPos ? true : false);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getResources().getDimensionPixelSize(R.dimen.gudieview_width),
                        getResources().getDimensionPixelSize(R.dimen.gudieview_heigh));
                layoutParams.setMargins(10, 0, 0, 0);
                guideGroup.addView(view, layoutParams);
                guideViewList.add(view);
            }
        }
    }

    private class ImageAdapter extends PagerAdapter implements IShareElements {

        private List<String> datas;
        private List<Integer> imgRIds;
        private LayoutInflater inflater;
        private Context context;
        private PhotoView imageView;

        public void setDatas(List<String> datas, List<Integer> iamgeRIds) {
            if (datas != null && datas.size() > 0) {
                this.datas = datas;
            }
            if (iamgeRIds != null && iamgeRIds.size() > 0) {
                this.imgRIds = iamgeRIds;
            }
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if (datas != null && datas.size() > 0) {
                return datas.size();
            }
            if (imgRIds != null && imgRIds.size() > 0) {
                return imgRIds.size();
            }
            return 0;
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_pager_image, container, false);
            if (view != null) {
                imageView = (PhotoView) view.findViewById(R.id.image);
                // 点击图片
                imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        onBackPressed();
                    }
                });

                imageView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final SheetDialog dialog = new SheetDialog(mContext, new String[]{"保存到本地"}, null);
                        dialog.isTitleShow(false)
                                .cancelText(getString(R.string.disagree))
                                .show();

                        dialog.setOnOperItemClickL(new OnOperItemClickL() {
                            @Override
                            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                                dialog.dismiss();
                                if (datas != null && datas.size() > 0) {
                                    glideFileHelper.savePicture(datas.get(viewPager.getCurrentItem()));
                                }
                                if (imgRIds != null && imgRIds.size() > 0) {
                                }
                            }
                        });

                        return true;
                    }
                });


                //loading
                final ProgressBar loading = new ProgressBar(context);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout) view).addView(loading);

                String imgurl = null;
                Integer imgRId = -1;
                if (datas != null && datas.size() > 0) {
                    imgurl = datas.get(position);
                }
                if (imgRIds != null && imgRIds.size() > 0) {
                    imgRId = imgRIds.get(position);
                }
                loading.setVisibility(View.VISIBLE);
                GlideApp.with(context)
                        .load(TextUtils.isEmpty(imgurl) ? imgRId : imgurl)
                        .thumbnail(0.1f)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                loading.setVisibility(View.GONE);
                                return false;
                            }
                        })
                        .into(imageView);
                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


        @Override
        public ShareElementInfo[] getShareElements() {
            return new ShareElementInfo[]{new ShareElementInfo(imageView, new TextViewStateSaver())};
        }
    }
}
