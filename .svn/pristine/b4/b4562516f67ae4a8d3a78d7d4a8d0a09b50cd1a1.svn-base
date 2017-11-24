package com.goldenasia.lottery.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * 刮刮彩的横向滑动View
 * Created by Alashi on 2016/10/13.
 */

public class SlideView extends View {
    private static final String TAG = "SlideView";

    private Drawable mainDrawable;
    private Drawable bgDrawable;
    private Drawable checkedDrawable;
    private Drawable uncheckedDrawable;

    private float screenRatio;
    private int focusWidth;
    private int focusHeight;
    private int noFocusWidth;
    private int noFocusHeight;
    private float baseLineY;

    private int move = NO_MOVE; //
    private static final int NO_MOVE = 0;
    private static final int MOVE_RIGHT = 1;
    private static final int MOVE_LEFT = -1;

    private MyAnimation myAnimation;
    private float downX;
    private float minMove;
    private OnChangedListener listener;
    private GestureDetector gestureDetector;
    private Rect clickRect = new Rect();

    public interface OnChangedListener{
        void OnChanged(boolean left);
        void onClick();
        boolean isChecked();
    }

    public SlideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public void setListener(OnChangedListener listener) {
        this.listener = listener;
    }

    public void setMainDrawable(Drawable mainDrawable) {
        this.mainDrawable = mainDrawable;
        requestLayout();
    }

    public void setBgDrawable(Drawable bgDrawable)
    {
        this.bgDrawable = bgDrawable;
    }

    public void setCheckedDrawable(Drawable checkedDrawable)
    {
        this.checkedDrawable = checkedDrawable;
    }

    public void setUncheckedDrawable(Drawable uncheckedDrawable)
    {
        this.uncheckedDrawable = uncheckedDrawable;
    }

    private void init(AttributeSet attrs) {
        /*Resources re = getContext().getResources();
        mainDrawable = re.getDrawable(R.drawable.sxggk_hd_da);
        bgDrawable = re.getDrawable(R.drawable.sxguaguaka_hdbg);
        uncheckedDrawable = re.getDrawable(R.drawable.queding);
        checkedDrawable = re.getDrawable(R.drawable.suiji);*/
        minMove = getResources().getDisplayMetrics().density * 10;

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (listener == null) {
                    return false;
                }
                if (clickRect.contains((int) e.getX(), (int) e.getY())) {
                    listener.onClick();
                }
                invalidate();
                //Log.d(TAG, "onSingleTapUp: " + Arrays.deepToString(getCheckedNumber().toArray()));
                return true;
            }

        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        screenRatio = 1.0f * widthSize / bgDrawable.getIntrinsicWidth();
        int heightSize = (int) (screenRatio * bgDrawable.getIntrinsicHeight());

        focusWidth = (int) (screenRatio * mainDrawable.getIntrinsicWidth());
        focusHeight = (int) (screenRatio * mainDrawable.getIntrinsicHeight());
        noFocusWidth = (int) (0.9f * focusWidth);
        noFocusHeight = (int) (0.9f * focusHeight);

        baseLineY = 0.18f * getHeight();

        int left = (int) ((widthSize - focusWidth) * 0.5);
        int top = (int) baseLineY;
        clickRect.set(left, top, left + focusWidth, top + focusHeight);
        bgDrawable.setBounds(0, 0, widthSize, heightSize);
        checkedDrawable.setBounds(0, 0, (int)(checkedDrawable.getIntrinsicWidth() * screenRatio),
                (int)(checkedDrawable.getIntrinsicHeight() * screenRatio));
        uncheckedDrawable.setBounds(0, 0, (int)(uncheckedDrawable.getIntrinsicWidth() * screenRatio),
                (int)(uncheckedDrawable.getIntrinsicHeight() * screenRatio));
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        bgDrawable.draw(canvas);

        if (myAnimation != null && !myAnimation.hasEnded()) {
            if (move == MOVE_RIGHT) {
                drawMoveRight(canvas, myAnimation.progress);
            } else {
                drawMoveLeft(canvas, myAnimation.progress);
            }
        } else {
            myAnimation = null;
            move = NO_MOVE;
            drawStatic(canvas);
        }
    }

    private void drawMoveLeft(Canvas canvas, float progress) {
        if (true){
            //左的，先左平移
            canvas.save();
            float dx = -progress * noFocusWidth * 0.4f;
            canvas.translate(dx, baseLineY + focusHeight - noFocusHeight);
            mainDrawable.setBounds(0, 0, noFocusWidth, noFocusHeight);
            mainDrawable.draw(canvas);
            canvas.translate(noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.translate(noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.restore();
        }

        if (true){
            //中间大图缩小，先左移动
            int dHeight = (int) ((focusHeight - noFocusHeight) * progress);
            int dWidth = (int) ((focusWidth - noFocusWidth) * progress);
            canvas.save();
            float dx = noFocusWidth * 0.4f * 2
                    + (1 - progress) * ((getWidth() - focusWidth) * 0.5f - noFocusWidth * 0.4f * 2);

            canvas.translate(dx, baseLineY + dHeight);
            mainDrawable.setBounds(0, 0, focusWidth - dWidth, focusHeight - dHeight);
            mainDrawable.draw(canvas);
            canvas.restore();
        }

        if (true){
            //右边的：中间右一变大(最后做)；其他向左平移
            canvas.save();
            float dx = noFocusWidth * 0.4f - progress * noFocusWidth * 0.4f;
            canvas.translate(dx, baseLineY + focusHeight - noFocusHeight);
            canvas.translate(getWidth()-noFocusWidth, 0);
            mainDrawable.setBounds(0, 0, noFocusWidth, noFocusHeight);
            mainDrawable.draw(canvas);
            canvas.translate(-noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.translate(-noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.restore();
        }

        if (true){
            //中间右一变大
            canvas.save();
            int dHeight = (int) ((focusHeight - noFocusHeight) * progress);
            int dWidth = (int) ((focusWidth - noFocusWidth) * progress);
            float dx = noFocusWidth * 0.4f - progress * noFocusWidth * 0.4f;
            canvas.translate(dx-dWidth, baseLineY + focusHeight - noFocusHeight-dHeight);
            canvas.translate(getWidth()-noFocusWidth-noFocusWidth * 0.4f * 3, 0);
            mainDrawable.setBounds(0, 0, noFocusWidth + dWidth, noFocusHeight + dHeight);
            mainDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void drawMoveRight(Canvas canvas, float progress) {
        //左边的：中间左边第一张，放大动作(最后做)；其他向右平移
        {
            canvas.save();
            float dx = -noFocusWidth * 0.4f + progress * noFocusWidth * 0.4f;
            canvas.translate(dx, baseLineY + focusHeight - noFocusHeight);
            mainDrawable.setBounds(0, 0, noFocusWidth, noFocusHeight);
            mainDrawable.draw(canvas);
            canvas.translate(noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.translate(noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.restore();
            //左边的end
        }

        {
            //右边的：向右平移
            canvas.save();
            float dx = progress * noFocusWidth * 0.4f;
            canvas.translate(dx, baseLineY + focusHeight - noFocusHeight);
            canvas.translate(getWidth() - noFocusWidth, 0);
            mainDrawable.setBounds(0, 0, noFocusWidth, noFocusHeight);
            mainDrawable.draw(canvas);
            canvas.translate(-noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.translate(-noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.restore();
            //右边的end
        }

        {
            //中间的：向右缩小
            canvas.save();
            int dHeight = (int) ((focusHeight - noFocusHeight) * progress);
            int dWidth = (int) ((focusWidth - noFocusWidth) * progress);
            canvas.translate(dWidth + progress * noFocusWidth * 0.4f, baseLineY + dHeight);
            canvas.translate((getWidth() - focusWidth) * 0.5f, 0);
            mainDrawable.setBounds(0, 0, focusWidth - dWidth, focusHeight - dHeight);
            mainDrawable.draw(canvas);
            canvas.restore();
            //中间的大图的end
        }

        {
            //中间左一，放大
            canvas.save();
            float dx = -noFocusWidth * 0.4f + progress * noFocusWidth * 0.4f;
            canvas.translate(dx, baseLineY + focusHeight - noFocusHeight);
            canvas.translate(noFocusWidth * 0.4f * 2, 0);
            int dHeight = (int) ((focusHeight - noFocusHeight) * progress);
            int dWidth = (int) ((focusWidth - noFocusWidth) * progress);
            canvas.translate(0, -dHeight);
            mainDrawable.setBounds(0, 0, noFocusWidth + dWidth, noFocusHeight + dHeight);
            canvas.translate(noFocusWidth * 0.4f, 0);
            mainDrawable.draw(canvas);
            canvas.restore();
        }
    }

    private void drawStatic(Canvas canvas) {
        //左边的
        canvas.save();
        canvas.translate(0, baseLineY + focusHeight - noFocusHeight);
        mainDrawable.setBounds(0, 0, noFocusWidth, noFocusHeight);
        mainDrawable.draw(canvas);
        canvas.translate(noFocusWidth * 0.4f, 0);
        mainDrawable.draw(canvas);
        canvas.translate(noFocusWidth * 0.4f, 0);
        mainDrawable.draw(canvas);
        canvas.restore();
        //左边的end

        //右边的
        canvas.save();
        canvas.translate(0, baseLineY + focusHeight - noFocusHeight);
        canvas.translate(getWidth()-noFocusWidth, 0);
        mainDrawable.setBounds(0, 0, noFocusWidth, noFocusHeight);
        mainDrawable.draw(canvas);
        canvas.translate(-noFocusWidth * 0.4f, 0);
        mainDrawable.draw(canvas);
        canvas.translate(-noFocusWidth * 0.4f, 0);
        mainDrawable.draw(canvas);
        canvas.restore();
        //右边的end

        //中间的大图
        canvas.save();
        canvas.translate(0, baseLineY);
        canvas.translate((getWidth() - focusWidth) * 0.5f, 0);
        mainDrawable.setBounds(0, 0, focusWidth, focusHeight);
        mainDrawable.draw(canvas);
        if (listener != null) {
            if (listener.isChecked()) {
                canvas.translate(focusWidth - 2 * checkedDrawable.getBounds().width(),
                        checkedDrawable.getBounds().height());
                checkedDrawable.draw(canvas);
            } else {
                canvas.translate(focusWidth - 2 * uncheckedDrawable.getBounds().width(),
                        uncheckedDrawable.getBounds().height());
                uncheckedDrawable.draw(canvas);
            }
        }
        canvas.restore();
        //中间的大图的end
    }

    public void slideRight() {
        Log.d(TAG, "slideRight: ");
        move = MOVE_RIGHT;
        myAnimation = new MyAnimation();
        startAnimation(myAnimation);
        if (listener != null) {
            listener.OnChanged(false);
        }
    }

    public void slideLeft() {
        Log.d(TAG, "slideLeft: ");
        move = MOVE_LEFT;
        myAnimation = new MyAnimation();
        startAnimation(myAnimation);
        if (listener != null) {
            listener.OnChanged(true);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)){
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(event.getX() - downX) > minMove) {
                    if (event.getX() - downX > 0) {
                        slideRight();
                    } else {
                        slideLeft();
                    }
                }
                break;
        }
        return true;
    }

    private class MyAnimation extends Animation{
        private float progress;

        public MyAnimation() {
            setDuration(300);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            progress = interpolatedTime;
            invalidate();
        }
    }
}
