package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.goldenasia.lottery.R;

/**
 * Created by Sakura on 2016/9/21.
 */

public class LhcLayout extends LinearLayout {
    private String sx;
    private String tail;
    private String colorWave;

    public LhcLayout(Context context) {
        super (context);
    }

    public LhcLayout(Context context, AttributeSet attrs) {
        super (context, attrs);
        init (context, attrs);
    }

    public LhcLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super (context, attrs, defStyleAttr);
        init (context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes (attributeSet, R.styleable.LhcLayout);
        setSx (typedArray.getString (R.styleable.LhcLayout_sx));
        setTail (typedArray.getString (R.styleable.LhcLayout_tail));
        setColor (typedArray.getString (R.styleable.LhcLayout_color_wave));
    }

    public String getSx() {
        return sx;
    }

    public void setSx(String sx) {
        this.sx = sx;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        this.tail = tail;
    }

    public String getColor() {
        return colorWave;
    }

    public void setColor(String colorWave) {
        this.colorWave = colorWave;
    }
}
