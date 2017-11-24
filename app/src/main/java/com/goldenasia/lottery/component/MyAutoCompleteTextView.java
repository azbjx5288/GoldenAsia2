package com.goldenasia.lottery.component;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.support.v7.widget.AppCompatAutoCompleteTextView;

/**
 * Created by ACE-PC on 2016/9/19.
 */
public class MyAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    public MyAutoCompleteTextView(Context context) {
        super(context);
    }
    public MyAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean enoughToFilter() {
        return true;
    }
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        performFiltering(getText(), KeyEvent.KEYCODE_UNKNOWN);
    }
}
