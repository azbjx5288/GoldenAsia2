package com.goldenasia.lottery.component;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.goldenasia.lottery.R;

import in.srain.cube.views.ptr.util.PtrLocalDisplay;

public class RentalsSunDrawable extends Drawable implements Animatable {

    private static final float SCALE_START_PERCENT = 0.3f;
    private static final int ANIMATION_DURATION = 1000;

    private final static float SKY_RATIO = 0.65f;

    private static final float SUN_FINAL_SCALE = 0.75f;

    private static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();

    private View mParent;
    private Matrix mMatrix;
    private Animation mAnimation;

    private int mTop;
    private int mScreenWidth;

    private int mSkyHeight;

    private float mSunTopOffset;

    private float mPercent = 0.0f;
    private float mRotate = 0.0f;

    private Bitmap mSun;

    private boolean isRefreshing = false;

    private Context mContext;
    private int mTotalDragDistance;

    public RentalsSunDrawable(Context context, View parent) {
        mContext = context;
        mParent = parent;

        mMatrix = new Matrix();

        initiateDimens();
        createBitmaps();
        setupAnimations();
    }

    private Context getContext() {
        return mContext;
    }


    private void initiateDimens() {
        PtrLocalDisplay.init(mContext);
        mTotalDragDistance = PtrLocalDisplay.dp2px(60);

        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        mSkyHeight = (int) (SKY_RATIO * mScreenWidth);
        mSunTopOffset = (mTotalDragDistance * 0.5f);

        mTop = 0;
    }

    private void createBitmaps() {
        mSun = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_xialashuaxin);
        Log.i("查看图片高度", "宽->" + mSun.getWidth() + "   高->" + mSun.getHeight());
        mSun = Bitmap.createScaledBitmap(mSun, mSun.getWidth(), mSun.getHeight(), true);
    }

    public void offsetTopAndBottom(int offset) {
        mTop = offset;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        final int saveCount = canvas.save();
        canvas.translate(0, mTotalDragDistance - mTop);
        drawSun(canvas);
        canvas.restoreToCount(saveCount);
    }

    private void drawSun(Canvas canvas) {
        Matrix matrix = mMatrix;
        matrix.reset();

        float dragPercent = mPercent;
        if (dragPercent > 1.0f) { // Slow down if pulling over set height
            dragPercent = (dragPercent + 9.0f) / 10;
        }

        float sunRadius = (float) mSun.getWidth();

        float offsetX = mScreenWidth / 2 - sunRadius / 2;
        float offsetY = mSunTopOffset + (mTotalDragDistance / 2) * (1.0f - dragPercent); // Move the sun up

        float scalePercentDelta = dragPercent - SCALE_START_PERCENT;
        if (scalePercentDelta > 0) {
            float scalePercent = scalePercentDelta / (1.0f + SCALE_START_PERCENT);
            float sunScale = 1.0f + (1.0f - SUN_FINAL_SCALE) * scalePercent;

            matrix.preTranslate(offsetX + (sunRadius - sunRadius * sunScale), offsetY * (2.0f - sunScale));
            matrix.preScale(sunScale, sunScale);
        } else {
            matrix.postTranslate(offsetX, offsetY);
        }
        canvas.drawBitmap(mSun, matrix, null);
    }

    public void setPercent(float percent) {
        mPercent = percent;
        setRotate(percent);
    }

    public void setRotate(float rotate) {
        mRotate = rotate;
        mParent.invalidate();
        invalidateSelf();
    }

    public void resetOriginals() {
        setPercent(0);
        setRotate(0);
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        super.setBounds(left, top, right, mSkyHeight + top);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public void start() {
        mAnimation.reset();
        isRefreshing = true;
        mParent.startAnimation(mAnimation);
    }

    @Override
    public void stop() {
        mParent.clearAnimation();
        isRefreshing = false;
        resetOriginals();
    }

    private void setupAnimations() {
        mAnimation = new Animation() {
            @Override
            public void applyTransformation(float interpolatedTime, Transformation t) {
                setRotate(interpolatedTime);
            }
        };
        mAnimation.setRepeatCount(Animation.INFINITE);
        mAnimation.setRepeatMode(Animation.RESTART);
        mAnimation.setInterpolator(LINEAR_INTERPOLATOR);
        mAnimation.setDuration(ANIMATION_DURATION);
    }

    public int getTotalDragDistance() {
        return mTotalDragDistance;
    }
}
