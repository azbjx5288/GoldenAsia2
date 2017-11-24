package com.goldenasia.lottery.component;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldenasia.lottery.R;

/**
 * 显示第几次开奖
 * Created by Sakura on 2016/9/9.
 */

public class RollCountPopupWindow extends PopupWindow {
    private static final String TAG = RollCountPopupWindow.class.getSimpleName();
    private static final long DURATION = 1000;

    TextView countTextView;

    private Handler handler;

    public RollCountPopupWindow(Context context, int rollCount) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popupwinddow_roll_count, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        countTextView = (TextView) view.findViewById(R.id.count);
        countTextView.setText("第" + rollCount + "次开奖");

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, DURATION);
    }
}
