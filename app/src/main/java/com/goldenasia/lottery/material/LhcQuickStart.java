package com.goldenasia.lottery.material;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.goldenasia.lottery.R;

/**
 * Created by Sakura on 2017/8/11.
 */

public class LhcQuickStart extends View
{
    private int selectedID;
    private View view;
    private int[] ids = new int[]{R.id.single_num, R.id.small_single, R.id.combine_single, R.id.poultry, R.id.beast,
            R.id.double_num, R.id.small_double, R.id.combine_double, R.id.mouse, R.id.dragon, R.id.monkey, R.id
            .big_num, R.id.big_single, R.id.combine_big, R.id.cow, R.id.snake, R.id.chicken, R.id.small_num, R.id
            .big_double, R.id.combine_small, R.id.tiger, R.id.horse, R.id.dog, R.id.head_0, R.id.tail_0, R.id.tail_5,
            R.id.rabbit, R.id.sheep, R.id.pig, R.id.head_1, R.id.tail_1, R.id.tail_6, R.id.red, R.id.blue, R.id
            .green, R.id.head_2, R.id.tail_2, R.id.tail_7, R.id.red_single, R.id.blue_single, R.id.green_single, R.id
            .head_3, R.id.tail_3, R.id.tail_8, R.id.red_double, R.id.blue_double, R.id.green_double, R.id.head_4, R
            .id.tail_4, R.id.tail_9, R.id.red_big, R.id.blue_big, R.id.green_big, R.id.clear, R.id.red_small, R.id
            .blue_small, R.id.green_small};
    private OnQuickListner onQuickListner;
    
    public LhcQuickStart(Context context, View view)
    {
        super(context);
        this.view = view;
        init();
    }
    
    public LhcQuickStart(Context context, View view, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        this.view = view;
        init();
    }
    
    public interface OnQuickListner
    {
        void onQuickStart(int id);
    }
    
    private void init()
    {
        OnClickListener onClickListener = new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (onQuickListner != null)
                {
                    v.setSelected(true);
                    
                    if (selectedID == 0)
                        selectedID = v.getId();
                    else if (selectedID == v.getId())
                    {
                        view.findViewById(selectedID).setSelected(false);
                        selectedID = 0;
                    } else
                    {
                        view.findViewById(selectedID).setSelected(false);
                        selectedID = v.getId();
                    }
                    
                    onQuickListner.onQuickStart(selectedID);
                }
            }
        };
        for (int id : ids)
        {
            view.findViewById(id).setOnClickListener(onClickListener);
        }
    }
    
    public void setOnQuickListner(OnQuickListner onQuickListner)
    {
        this.onQuickListner = onQuickListner;
    }
    
    /*@OnClick({R.id.single_num, R.id.small_single, R.id.combine_single, R.id.poultry, R.id.beast, R.id.double_num, R
            .id.small_double, R.id.combine_double, R.id.mouse, R.id.dragon, R.id.monkey, R.id.big_num, R.id
            .big_single, R.id.combine_big, R.id.cow, R.id.snake, R.id.chicken, R.id.small_num, R.id.big_double, R.id
            .combine_small, R.id.tiger, R.id.horse, R.id.dog, R.id.head_0, R.id.tail_0, R.id.tail_5, R.id.rabbit, R
            .id.sheep, R.id.pig, R.id.head_1, R.id.tail_1, R.id.tail_6, R.id.red, R.id.blue, R.id.green, R.id.head_2,
            R.id.tail_2, R.id.tail_7, R.id.red_single, R.id.blue_single, R.id.green_single, R.id.head_3, R.id.tail_3,
            R.id.tail_8, R.id.red_double, R.id.blue_double, R.id.green_double, R.id.head_4, R.id.tail_4, R.id.tail_9,
            R.id.red_big, R.id.blue_big, R.id.green_big, R.id.clear, R.id.red_small, R.id.blue_small, R.id.green_small})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.single_num:
                break;
            case R.id.small_single:
                break;
            case R.id.combine_single:
                break;
            case R.id.poultry:
                break;
            case R.id.beast:
                break;
            case R.id.double_num:
                break;
            case R.id.small_double:
                break;
            case R.id.combine_double:
                break;
            case R.id.mouse:
                break;
            case R.id.dragon:
                break;
            case R.id.monkey:
                break;
            case R.id.big_num:
                break;
            case R.id.big_single:
                break;
            case R.id.combine_big:
                break;
            case R.id.cow:
                break;
            case R.id.snake:
                break;
            case R.id.chicken:
                break;
            case R.id.small_num:
                break;
            case R.id.big_double:
                break;
            case R.id.combine_small:
                break;
            case R.id.tiger:
                break;
            case R.id.horse:
                break;
            case R.id.dog:
                break;
            case R.id.head_0:
                break;
            case R.id.tail_0:
                break;
            case R.id.tail_5:
                break;
            case R.id.rabbit:
                break;
            case R.id.sheep:
                break;
            case R.id.pig:
                break;
            case R.id.head_1:
                break;
            case R.id.tail_1:
                break;
            case R.id.tail_6:
                break;
            case R.id.red:
                break;
            case R.id.blue:
                break;
            case R.id.green:
                break;
            case R.id.head_2:
                break;
            case R.id.tail_2:
                break;
            case R.id.tail_7:
                break;
            case R.id.red_single:
                break;
            case R.id.blue_single:
                break;
            case R.id.green_single:
                break;
            case R.id.head_3:
                break;
            case R.id.tail_3:
                break;
            case R.id.tail_8:
                break;
            case R.id.red_double:
                break;
            case R.id.blue_double:
                break;
            case R.id.green_double:
                break;
            case R.id.head_4:
                break;
            case R.id.tail_4:
                break;
            case R.id.tail_9:
                break;
            case R.id.red_big:
                break;
            case R.id.blue_big:
                break;
            case R.id.green_big:
                break;
            case R.id.clear:
                break;
            case R.id.red_small:
                break;
            case R.id.blue_small:
                break;
            case R.id.green_small:
                break;
        }
    }*/
}
