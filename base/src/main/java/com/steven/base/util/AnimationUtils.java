package com.steven.base.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * @user steven
 * @createDate 2019/3/5 18:05
 * @description 自定义
 */
public class AnimationUtils {
    /**
     * 平移动画
     *
     * @param view         view
     * @param fromY        开始位置
     * @param toY          结束位置
     * @param duration     动画时间
     * @param interpolator 差值器
     */
    public static void translationView(View view, float fromY, float toY, int duration, TimeInterpolator interpolator, Animator.AnimatorListener listener) {
        ObjectAnimator animator;
        animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.addListener(listener);
        animator.start();
    }

    /**
     * 平移动画
     *
     * @param view         view
     * @param fromY        开始位置
     * @param toY          结束位置
     * @param duration     动画时间
     * @param interpolator 差值器
     */
    public static void translationView(View view, float fromY, float toY, int duration, TimeInterpolator interpolator) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setInterpolator(interpolator);
        animator.setDuration(duration);
        animator.start();
    }

    public static void translationViewX(View view, float fromX, float toX, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", fromX, toX);
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 渐变动画
     *
     * @param view      view
     * @param fromAlpha 开始透明度
     * @param toAplha   结束透明度
     * @param duration  时间
     */
    public static void alphaView(View view, float fromAlpha, float toAplha, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", fromAlpha, toAplha);
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 旋转动画
     *
     * @param view        view
     * @param from
     * @param to
     * @param duration    时间
     * @param repeatCount 选择周数
     */
    public static void rotationView(View view, float from, float to, int duration, int repeatCount) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "rotation", from, to);
        animator.setRepeatCount(repeatCount);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(duration);
        animator.start();
    }

    /**
     * 放大缩小动画
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleView(View view, int duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.5f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.5f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }

    /**
     * @param view     view
     * @param duration 时间
     * @param fromY
     * @param toY
     */
    public static void scanLineAnim(View view, float fromY, float toY, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", fromY, toY);
        animator.setDuration(duration);
        animator.start();
        animator.setRepeatCount(-1);
    }

    /**
     * 首页卡片显示动画
     *
     * @param view1    view 从小到大显示布局（弹性）
     * @param view2    view 渐变 变大
     * @param duration 时间
     */
    public static void foldingShowView(View view1, final View view2, int duration, final boolean isShowView2) {
        boolean isShowView1 = view1.getVisibility() == View.VISIBLE; // view1已经显示，不需要执行动画
        boolean view2Show = view2.getVisibility() == View.VISIBLE; // view2已经显示，不需要执行动画
        if (isShowView1 && view2Show) {
            view2.setVisibility(isShowView2 ? View.VISIBLE : View.GONE);
            return; // 两个veiw都显示了，直接return
        }
        view1.setVisibility(View.VISIBLE);
        // 放大动画
        ObjectAnimator view1ScaleX = ObjectAnimator.ofFloat(view1, "scaleX", 0, .5f, 1, .95f, 1, 1.1f, 1).setDuration(duration);
        ObjectAnimator view1ScaleY = ObjectAnimator.ofFloat(view1, "scaleY", 0, .5f, 1, 1.1f, 1, .95f, 1).setDuration(duration);
        view1ScaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (isShowView2) {
                    view2.setVisibility(View.VISIBLE); // 第一个动画执行完毕，显示第二个需要执行动画的布局文件(服务商显示布局2，否则不显示)
                } else {
                    view2.setVisibility(View.GONE);
                }
            }
        });
        // 渐变 Y轴放大
        view2.setPivotX(view2.getWidth() / 2);
        view2.setPivotY(0);
        ObjectAnimator view2ScaleY = ObjectAnimator.ofFloat(view2, "scaleY", 0, .5f, 1, .95f, 1).setDuration(duration * 2);
        ObjectAnimator view2Alpha = ObjectAnimator.ofFloat(view2, "alpha", 0, 1).setDuration((long) (duration * 1.5));
        // 动画延迟
        if (!isShowView1) { // view1没有显示，动画延迟
            view2ScaleY.setStartDelay(duration);
            view2Alpha.setStartDelay(duration);
        }
        AnimatorSet set = new AnimatorSet();
        if (!isShowView1 && isShowView2) { // 显示view2
            set.playTogether(view1ScaleX, view1ScaleY
                    , view2ScaleY, view2Alpha
            );
        } else if (!isShowView1 && !isShowView2) { // 不显示view2，只显示veiw1
            set.playTogether(view1ScaleX, view1ScaleY
            );
        } else if (isShowView2) {
            view2.setVisibility(View.VISIBLE);
            set.playTogether(view2ScaleY, view2Alpha
            );
        }
        set.start();
    }


    /**
     * 首页卡片隐藏动画
     *
     * @param view1    view 从大到小隐藏布局
     * @param view2    view 渐变 变小
     * @param duration 时间
     */
    public static void foldingHideView(final View view1, final View view2, int duration) {
        boolean isShowView2 = view2.getVisibility() == View.VISIBLE;
        ObjectAnimator view2ScaleY = null;
        ObjectAnimator view2Alpha = null;
        if (isShowView2) { // 只有布局2显示的时候才需要初始化view2动画
            // 渐变 Y轴缩小
            view2ScaleY = ObjectAnimator.ofFloat(view2, "scaleY", 1, 0.95f, 1, 0.5f, 0).setDuration(duration / 2);
            view2Alpha = ObjectAnimator.ofFloat(view2, "alpha", 1, 0).setDuration(duration / 2);
            view2Alpha.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    view2.setVisibility(View.GONE); // 隐藏布局
                }
            });
        }
        // 缩小动画
        ObjectAnimator view1ScaleX = ObjectAnimator.ofFloat(view1, "scaleX", 1, 1.1f, 1, 0.95f, 1, 0.5f, 0).setDuration((long) (duration * 0.75f));
        ObjectAnimator view1ScaleY = ObjectAnimator.ofFloat(view1, "scaleY", 1, 0.95f, 1, 1.1f, 1, 0.5f, 0).setDuration((long) (duration * 0.75f));
        view1ScaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view1.setVisibility(View.GONE);
            }
        });
        // 如果view2不显示则不需要动画延迟
        if (isShowView2) {
            view1ScaleX.setStartDelay(duration / 2);
            view1ScaleY.setStartDelay(duration / 2);
        }
        AnimatorSet set = new AnimatorSet();
        if (isShowView2) {
            set.playTogether(view2ScaleY, view2Alpha,
                    view1ScaleX, view1ScaleY
            );
        } else {
            set.playTogether(view1ScaleX, view1ScaleY);
        }
        set.start();
    }

    /**
     * 普通平移动画，控件执行完动画回到原来的位置
     *
     * @param view     view
     * @param fromY    开始位置
     * @param toY      结束位置
     * @param duration 动画时间
     */
    public static void translation(View view, float fromY, float toY, int duration, Animation.AnimationListener listener) {
        TranslateAnimation animation = new TranslateAnimation(0, 0, fromY, toY);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setDuration(duration);
        animation.setAnimationListener(listener);
        view.startAnimation(animation);
    }

    /**
     * 渐变
     * 隐藏View，相当于Visibility==GONE
     */
    public static void setVisibilityGone(final View view, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }


    /**
     * 渐变
     * 显示View，相当于Visibility==VISIBLE
     */
    public static void setVisibilityVisible(final View view, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
        animator.setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setAlpha(0f);
                view.setVisibility(View.VISIBLE);
            }
        });
        animator.start();
    }

    /**
     * 放大View
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleBigView(View view, int duration, Animator.AnimatorListener listener) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.addListener(listener);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * 放大View
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleBigView(View view, int duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * 缩小View
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleSmallView(View view, int duration, Animator.AnimatorListener listener) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.addListener(listener);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }
    /**
     * 缩小View
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleSmallView(View view, int duration) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", 1.5f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", 1.5f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new LinearInterpolator());
        set.start();
    }

    /**
     * 根据传入的值 放大缩小view
     *
     * @param view     view
     * @param duration 时间
     */
    public static void scaleView(View view, int duration, float... values) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, "scaleX", values);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, "scaleY", values);
        AnimatorSet set = new AnimatorSet();
        set.play(animatorX).with(animatorY);
        set.setDuration(duration);
        set.setInterpolator(new AnticipateOvershootInterpolator());
        set.start();
    }

    /**
     * 数字自增长动画
     *
     * @param mStartValue
     * @param mEndValue
     */
    public static void raiseNumberBypercentage(int mStartValue, float mEndValue, int duration, final TextView mCompletedGoalValue) {
        ValueAnimator animator = ValueAnimator.ofFloat(mStartValue, mEndValue);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCompletedGoalValue.setText(NumberUtil.formitNewNumberTwoPoint((float) animation.getAnimatedValue()) + "%");
            }
        });
        animator.start();
    }

    /**
     * 数字自增长动画
     *
     * @param mStartValue
     * @param mEndValue
     */
    public static void raiseNumberByInt(int mStartValue, int mEndValue, int duration, final TextView mCompletedGoalValue) {
        ValueAnimator animator = ValueAnimator.ofInt(mStartValue, mEndValue);
        animator.setDuration(duration);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCompletedGoalValue.setText(animation.getAnimatedValue().toString());
            }
        });
        animator.start();
    }
}
