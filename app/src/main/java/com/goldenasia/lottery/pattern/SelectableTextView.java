package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Sakura on 2017/8/11.
 */

public class SelectableTextView extends android.support.v7.widget.AppCompatTextView implements View.OnClickListener
{
    public SelectableTextView(Context context)
    {
        super(context);
        this.setOnClickListener(this);
    }
    
    public SelectableTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v)
    {
        if (v.isSelected())
            v.setSelected(false);
        else
            v.setSelected(true);
    }
}
