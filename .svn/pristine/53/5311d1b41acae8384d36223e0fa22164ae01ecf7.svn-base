package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.goldenasia.lottery.R;

/**
 * Created by Sakura on 2016/9/21.
 */

public class CowLayout extends LinearLayout
{
    private String label;
    private String example;

    public CowLayout(Context context)
    {
        super(context);
    }

    public CowLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context, attrs);
    }

    public CowLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attributeSet)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CowLayout);
        setLabel(typedArray.getString(R.styleable.CowLayout_label));
        setExample(typedArray.getString(R.styleable.CowLayout_example));
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getExample()
    {
        return example;
    }

    public void setExample(String example)
    {
        this.example = example;
    }
}
