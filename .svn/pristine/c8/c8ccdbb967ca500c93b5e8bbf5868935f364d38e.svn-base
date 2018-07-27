package com.goldenasia.lottery.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.goldenasia.lottery.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Sakura on 2016/9/6.
 * 秒秒彩老虎机
 */

public class MmcKuaiSanOneArmBanditView
{
    private static final String TAG = MmcKuaiSanOneArmBanditView.class.getSimpleName();
    public static final long START_DELAY = 0;
    public static final long TOAST_DELAY = 6300;
    public static final long INTERVAL = 7000;

    private Context context;
    private View view;

    private int[] numberShow;
    private Handler handler;

    private boolean isRunning;

    private ViewFlipper[] viewFlippers;
    private ViewFlipper viewFlipper1, viewFlipper2, viewFlipper3;

    public MmcKuaiSanOneArmBanditView(Context context, View view)
    {
        init(context, view);
    }
    
    private void init(Context context, View view)
    {
        this.context = context;
        this.view = view;
        handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                showNumber(msg.what);
            }
        };
        isRunning = false;
        
        viewFlipper1 = (ViewFlipper) view.findViewById(R.id.flipper1);
        viewFlipper2 = (ViewFlipper) view.findViewById(R.id.flipper2);
        viewFlipper3 = (ViewFlipper) view.findViewById(R.id.flipper3);
        viewFlippers = new ViewFlipper[]{viewFlipper1, viewFlipper2, viewFlipper3};
        initViewFlippers();
        numberShow = new int[viewFlippers.length];
    }
    
    private void initViewFlippers()
    {
        for (ViewFlipper viewFlipper : viewFlippers)
        {
            viewFlipper.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up_in));
            viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.slide_up_out));
        }
    }
    
    /**
     * 号码定格
     */
    private void showNumber(int position)
    {
        viewFlippers[position].setDisplayedChild(numberShow[position]);
    }
    
    /**
     * String转换为int数组
     *
     * @param openCode
     */
    private void convertNumber(String openCode)
    {
        for (int i = 0, length = numberShow.length; i < 3; i++)
        {
            numberShow[i] = openCode.charAt(i) - '0';
        }
    }
    
    /**
     * 开始滚动
     *
     * @param openCode
     */
    public void start(String openCode)
    {
        convertNumber(openCode);
        for (ViewFlipper viewFlipper : viewFlippers)
            viewFlipper.startFlipping();
        for (int i = 0, length = viewFlippers.length; i < length; i++)
            initTimer(i);
    }
    
    private void initTimer(int position)
    {
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                viewFlippers[position].stopFlipping();
                handler.sendEmptyMessage(position);
                timer.cancel();
            }
        }, 1000 + 1000 * (position + 1));
    }
    
    public boolean isRunning()
    {
        return isRunning;
    }
    
    public void setRunning(boolean running)
    {
        isRunning = running;
    }
}
