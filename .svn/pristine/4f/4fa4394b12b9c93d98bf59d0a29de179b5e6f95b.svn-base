package com.goldenasia.lottery.component;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ConstantInformation;

/**
 * Created by Sakura on 2017/5/18.
 * 位数选择面板
 */

public class InterestingPanel implements CompoundButton.OnCheckedChangeListener
{
    private Context context;
    private View view;
    private int selection = -1;
    private CheckBox up, down, odd, even, clear, up_odd, up_even, down_odd, down_even;
    private CheckBox[] checkBoxes = new CheckBox[]{up, down, odd, even, clear, up_odd, up_even, down_odd, down_even};
    private int[] ids = new int[]{R.id.up, R.id.down, R.id.odd, R.id.even, R.id.clear, R.id.up_odd, R.id.up_even, R
            .id.down_odd, R.id.down_even};
    private OnChooseModeClickListener onChooseModeClickListener;
    
    public InterestingPanel(Context context, View view)
    {
        this.context = context;
        this.view = view;
        for (int i = 0; i < 9; i++)
        {
            checkBoxes[i] = (CheckBox) view.findViewById(ids[i]);
            checkBoxes[i].setOnCheckedChangeListener(this);
        }
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        switch (buttonView.getId())
        {
            case R.id.up:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.UP);
                    }
                }
                break;
            case R.id.down:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.DOWN);
                    }
                }
                break;
            case R.id.odd:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.ODD);
                    }
                }
                break;
            case R.id.even:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.EVEN);
                    }
                }
                break;
            case R.id.clear:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.CLEAR);
                    }
                }
                break;
            case R.id.up_odd:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.UP_ODD);
                    }
                }
                break;
            case R.id.up_even:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.UP_EVEN);
                    }
                }
                break;
            case R.id.down_odd:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.DOWN_ODD);
                    }
                }
                break;
            case R.id.down_even:
                if (onChooseModeClickListener != null)
                {
                    clearCheck();
                    if (isChecked)
                    {
                        buttonView.setChecked(true);
                        onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.DOWN_EVEN);
                    }
                }
                break;
        }
    }
    
    public interface OnChooseModeClickListener
    {
        void onChooseModeClick(int id);
    }
    
    public void initCheck()
    {
        for (int i = 0; i < 9; i++)
        {
            checkBoxes[i].setChecked(false);
        }
        view.invalidate();
    }
    
    private void clearCheck()
    {
        for (CheckBox checkBox : checkBoxes)
        {
            checkBox.setChecked(false);
        }
        if (onChooseModeClickListener != null)
            onChooseModeClickListener.onChooseModeClick(ConstantInformation.Kl8.CLEAR);
    }
    
    public void setOnChooseModeClickListener(OnChooseModeClickListener onChooseDigitClickListener)
    {
        this.onChooseModeClickListener = onChooseDigitClickListener;
    }
    
    public int getSelection()
    {
        return selection;
    }
    
    public void setSelection(int selection)
    {
        this.selection = selection;
    }
}
