package com.goldenasia.lottery.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.DisplayUtil;

import java.util.List;


public class LuDanContentView extends View {
    private static final String TAG = LuDanContentView.class.getSimpleName();

    private TextPaint paint01 = new TextPaint();
    private TextPaint paint02 = new TextPaint();
    private TextPaint paint03 = new TextPaint();
    private TextPaint normalPaint = new TextPaint();
    private int itemHeight ;
    private int itemWidth ;
    private int horizontalGap ;
    private int verticalGap;

    private Drawable drawable01;
    private Drawable drawable02;
    private Drawable drawable03;


    private int column = 20;

    private String title ="万十路单";

    public LuDanContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LuDanContentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    private void init(Context context) {
        drawable01= ContextCompat.getDrawable(context, R.drawable.ssc_lhh_ld_ball01);
        drawable02= ContextCompat.getDrawable(context, R.drawable.ssc_lhh_ld_ball02);
        drawable03= ContextCompat.getDrawable(context, R.drawable.ssc_lhh_ld_ball03);
        paint01.setAntiAlias(true);
        paint01.setStyle(Paint.Style.FILL);
        paint01.setTextAlign(Paint.Align.CENTER);
        paint01.setFlags(Paint.ANTI_ALIAS_FLAG);

        itemHeight = DisplayUtil.dip2px(context,40);
        itemWidth= DisplayUtil.dip2px(context,40);
        verticalGap= DisplayUtil.dip2px(context,7);
        horizontalGap= DisplayUtil.dip2px(context,7);

        paint02.set(paint01);
        paint03.set(paint01);
        normalPaint.set(paint01);
        paint01.setColor(getResources().getColor(R.color.white));
        paint02.setColor(getResources().getColor(R.color.white));
        paint03.setColor(getResources().getColor(R.color.lhc_red));
        normalPaint.setColor(getResources().getColor(R.color.black));
        paint01.setTextSize(DisplayUtil.sp2px(getContext(),13));
        paint02.setTextSize(DisplayUtil.sp2px(getContext(),13));
        paint03.setTextSize(DisplayUtil.sp2px(getContext(),13));
        normalPaint.setTextSize(DisplayUtil.sp2px(getContext(),18));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG, "onMeasure(int widthMeasureSpec, int heightMeasureSpec) ....  ");
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        int  line = column+1;

        int specHeight = line * itemHeight + (line - 1) * verticalGap;

        setMeasuredDimension(specSize, specHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        float x, y;
        Paint.FontMetricsInt fontMetrics = paint01.getFontMetricsInt();
        float offTextY = (itemHeight - fontMetrics.bottom - fontMetrics.top) / 2;
        int offTextX = itemWidth / 2;

        canvas.drawText(title, offTextX*2, offTextY, normalPaint);
        canvas.translate(0, itemHeight);


        for (int i = 0; i < ConstantInformation.luDanDataList.size(); i++) {
            y = i * (itemHeight + verticalGap);

            String text;

            List<String> dataArr= ConstantInformation.luDanDataList.get(column-1-i);


            for(int j=0;j<dataArr.size();j++){
                x = j  * (itemWidth + horizontalGap);

                text=dataArr.get(j);

                Rect drawableRect = new Rect(0, 0, itemWidth, itemHeight);
                drawable01.setBounds(drawableRect);
                drawable02.setBounds(drawableRect);
                drawable03.setBounds(drawableRect);

                canvas.save();
                canvas.translate(x, y);

                if(text.equals("龙")){
                    drawable01.draw(canvas);
                }else  if(text.equals("虎")){
                    drawable02.draw(canvas);
                }else{
                    drawable03.draw(canvas);
                }

                if(text.equals("龙")){
                    canvas.drawText(text, offTextX, offTextY, paint01);
                }else  if(text.equals("虎")){
                    canvas.drawText(text, offTextX, offTextY, paint02);
                }else{
                    canvas.drawText(text, offTextX, offTextY, paint03);
                }

                canvas.restore();
            }

        }


    }

    public void setTitle(String title) {
        this.title = title;
    }

    //刷新界面
    public void refreshViewGroup() {
        requestLayout();
    }

}
