package com.goldenasia.lottery.component;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ConstantInformation;

/**
 * Created by Sakura on 2017/5/18.
 * 位数选择面板
 */

public class DigitCheckBoxPanel implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private View view;
    private int digit = 0;
    private CheckBox one, ten, hundred, thousand, myriabit;
    private CheckBox[] checkBoxes = new CheckBox[]{one, ten, hundred, thousand, myriabit};
    private int[] ids = new int[]{R.id.one, R.id.ten, R.id.hundred, R.id.thousand, R.id.myriabit};
    private SparseBooleanArray digits = new SparseBooleanArray();
    private OnChooseDigitClickListener onChooseDigitClickListener;

    public DigitCheckBoxPanel(Context context, View view, int digit) {
        this.context = context;
        this.view = view;
        this.digit = digit;
        for (int i = 0; i < 5; i++) {
            checkBoxes[i] = (CheckBox) view.findViewById(ids[i]);
            checkBoxes[i].setOnCheckedChangeListener(this);
            digits.put(ConstantInformation.DIGIT_KEYS[i], false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.one:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.ONE, true);
                    else
                        digits.put(ConstantInformation.ONE, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.ten:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.TEN, true);
                    else
                        digits.put(ConstantInformation.TEN, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.hundred:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.HUNDRED, true);
                    else
                        digits.put(ConstantInformation.HUNDRED, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.thousand:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.THOUSAND, true);
                    else
                        digits.put(ConstantInformation.THOUSAND, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.myriabit:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.MYRIABIT, true);
                    else
                        digits.put(ConstantInformation.MYRIABIT, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
        }
    }

    public interface OnChooseDigitClickListener {
        void onChooseDigitClick(SparseBooleanArray digits);
    }

    public void initCheck() {
        for (int i = 0; i < 5; i++) {
            if (i < digit) {
                checkBoxes[i].setChecked(true);
                digits.put(ConstantInformation.DIGIT_KEYS[i], true);
            } else {
                checkBoxes[i].setChecked(false);
                digits.put(ConstantInformation.DIGIT_KEYS[i], false);
            }
        }
        view.invalidate();
    }

    public void setOnChooseDigitClickListener(OnChooseDigitClickListener onChooseDigitClickListener) {
        this.onChooseDigitClickListener = onChooseDigitClickListener;
    }

    public SparseBooleanArray getDigits() {
        return digits;
    }

    public void setDigits(SparseBooleanArray digits) {
        this.digits = digits;
    }
}
