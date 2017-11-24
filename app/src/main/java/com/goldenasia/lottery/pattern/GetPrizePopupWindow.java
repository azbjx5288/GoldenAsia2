package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.AutoFitTextView;
import com.goldenasia.lottery.util.NumbericUtils;

/**
 * 中奖弹窗
 * Created by Sakura on 2016/9/9.
 */

public class GetPrizePopupWindow extends PopupWindow
{
    private static final String TAG = GetPrizePopupWindow.class.getSimpleName();
    private static final long DURATION = 1000;

    private Context context;
    private AutoFitTextView countTextView;

    private double prize;
    private Handler handler;

    public GetPrizePopupWindow(Context context, double prize)
    {
        super(context);
        this.context = context;
        this.prize = prize;
        View view = LayoutInflater.from(context).inflate(R.layout.popupwinddow_roll_count, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        countTextView = (AutoFitTextView) view.findViewById(R.id.count);
        countTextView.setText("恭喜中奖" + NumbericUtils.formatPrize(prize) + "元");

        handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                dismiss();
            }
        }, DURATION);
    }
}
