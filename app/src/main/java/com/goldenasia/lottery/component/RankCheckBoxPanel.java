package com.goldenasia.lottery.component;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.goldenasia.lottery.R;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Sakura on 2017/5/18.
 * 名次选择面板
 */

public class RankCheckBoxPanel implements CompoundButton.OnCheckedChangeListener
{
    private Context context;
    private View view;
    private CheckBox cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10;
    private CheckBox[] checkBoxes = new CheckBox[]{cb1, cb2, cb3, cb4, cb5, cb6, cb7, cb8, cb9, cb10};
    private int[] ids = new int[]{R.id.cb1, R.id.cb2, R.id.cb3, R.id.cb4, R.id.cb5, R.id.cb6, R.id.cb7, R.id.cb8, R
            .id.cb9, R.id.cb10};
    private SparseBooleanArray ranks = new SparseBooleanArray();
    private OnChooseRankClickListener onChooseRankClickListener;
    
    public RankCheckBoxPanel(Context context, View view)
    {
        this.context = context;
        this.view = view;
        for (int i = 0; i < 10; i++)
        {
            checkBoxes[i] = (CheckBox) view.findViewById(ids[i]);
            checkBoxes[i].setOnCheckedChangeListener(this);
            ranks.put(i, false);
        }
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (onChooseRankClickListener != null)
        {
            if (isChecked)
                ranks.put(ArrayUtils.indexOf(ids, buttonView.getId()), true);
            else
                ranks.put(ArrayUtils.indexOf(ids, buttonView.getId()), false);
            onChooseRankClickListener.onChooseRankClick(ranks);
        }
    }
    
    public interface OnChooseRankClickListener
    {
        void onChooseRankClick(SparseBooleanArray ranks);
    }
    
    public void initCheck()
    {
        for (int i = 0; i < 10; i++)
        {
            if (i < 2)
            {
                checkBoxes[i].setChecked(true);
                ranks.put(i, true);
            } else
            {
                checkBoxes[i].setChecked(false);
                ranks.put(i, false);
            }
        }
    }
    
    public void setOnChooseRankClickListener(OnChooseRankClickListener onChooseRankClickListener)
    {
        this.onChooseRankClickListener = onChooseRankClickListener;
    }
    
    public SparseBooleanArray getRanks()
    {
        return ranks;
    }
    
    public void setRanks(SparseBooleanArray ranks)
    {
        this.ranks = ranks;
    }
}
