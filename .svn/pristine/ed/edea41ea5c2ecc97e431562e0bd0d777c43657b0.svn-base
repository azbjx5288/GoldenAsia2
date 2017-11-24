package com.goldenasia.lottery.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 刮刮彩卡片（带监听器）
 * Created by Sakura on 2016/9/22.
 */

public class EraseView extends View {
    private static final String TAG = "EraseView";
    private Bitmap bitmap;
    private int bitmapWidth;
    private int bitmapHeight;

    private Bitmap coverBitmap;
    private int coverId;

    private Path path;
    private Canvas mCanvas;
    private Paint paint;

    private boolean isPart;
    private boolean isMove = true;
    private Handler handler;
    private MyThread myThread;
    private int messageCount;
    private int[] pixels;
    private double percent;

    private AutoOpenListner autoOpenListner;

    public EraseView(Context context) {
        super(context);
    }

    public EraseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(int coverId, boolean isPart) {
        this.coverId = coverId;
        this.isPart = isPart;
        init();
        invalidate();
    }

    private void init() {
        if (isPart) {
            percent = 0;
            myThread = new MyThread();
            myThread.start();
        }
    }

    public void autoOpen() {
        isMove = false;
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (coverId != 0) {
            if (mCanvas == null) {
                EraseBitmp();
            }
            canvas.drawBitmap(bitmap, 0, 0, null);
            mCanvas.drawPath(path, paint);
        }
        super.onDraw(canvas);
    }

    public void EraseBitmp() {
        coverBitmap = BitmapFactory.decodeResource(getResources(), coverId);
        bitmap = Bitmap.createBitmap(coverBitmap.getWidth(), coverBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        //coverBitmap = CreateBitmap(Color.GRAY, getWidth(), getHeight());
        //coverBitmap = BitmapFactory.decodeStream(getResources().openRawResource(R.drawable.expression));
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(getResources().getDisplayMetrics().density * 30);
        path = new Path();
        mCanvas = new Canvas(bitmap);
        mCanvas.drawBitmap(coverBitmap, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isMove) {
            float ax = event.getX();
            float ay = event.getY();

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                path.reset();
                path.moveTo(ax, ay);
                invalidate();
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                path.lineTo(ax, ay);
                invalidate();
                if (isPart)
                    computeScale();
                return true;
            }
        }

        return super.onTouchEvent(event);
    }

    public Bitmap CreateBitmap(int color, int width, int height) {
        int[] rgb = new int[width * height];

        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = color;
        }

        return Bitmap.createBitmap(rgb, width, height, Bitmap.Config.ARGB_8888);
    }

    /**
     * 计算百分比
     */
    private void computeScale() {
        handler.removeMessages(0);
        Message msg = handler.obtainMessage(0);
        msg.obj = ++messageCount;
        handler.sendMessage(msg);
    }

    /**
     * 异步线程，作用是创建handler接收处理消息。
     *
     * @author Administrator
     */
    class MyThread extends Thread {

        public MyThread() {
        }

        @Override
        public void run() {
            super.run();
            /*
             * 创建 handler前先初始化Looper.
			 */
            Looper.prepare();

            handler = new Handler() {
                @Override
                public void dispatchMessage(Message msg) {
                    super.dispatchMessage(msg);
                    // 只处理最后一次的百分比
                    if ((Integer) (msg.obj) != messageCount) {
                        return;
                    }
                    if (bitmap == null) {
                        return;
                    }
                    // 取出像素点
                    synchronized (bitmap) {
                        bitmapWidth = coverBitmap.getWidth();
                        bitmapHeight = coverBitmap.getHeight();
                        if (pixels == null) {
                            pixels = new int[bitmapWidth * bitmapHeight];
                        }
                        bitmap.getPixels(pixels, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);
                    }

                    int sum = pixels.length;
                    int num = 0;
                    for (int i = 0; i < sum; i++) {
                        if (pixels[i] == 0) {
                            num++;
                        }
                    }
                    percent = num / (double) sum * 100;
                    Log.e("percent:", percent + "");
                    if (percent >= 60) {
                        isMove = false;
                        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                        postInvalidate();
                        if (autoOpenListner != null)
                            autoOpenListner.onAuto(isMove);
                    }
                }
            };
            /*
             * 启动该线程的消息队列
			 */
            Looper.loop();
        }
    }

    public interface AutoOpenListner {
        public void onAuto(boolean isMove);
    }

    public AutoOpenListner getAutoOpenListner() {
        return autoOpenListner;
    }

    public void setAutoOpenListner(AutoOpenListner autoOpenListner) {
        this.autoOpenListner = autoOpenListner;
    }

    public void destroy() {
        if (handler != null) {
            handler.removeMessages(0);
            handler.getLooper().quit();
        }
        if (bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
            bitmap = null;
        }
        if (coverBitmap != null && !coverBitmap.isRecycled()) {
            coverBitmap.recycle();
            coverBitmap = null;
        }
    }
}
