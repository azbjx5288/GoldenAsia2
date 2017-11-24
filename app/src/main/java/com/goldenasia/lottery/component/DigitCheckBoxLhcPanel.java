package com.goldenasia.lottery.component;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ConstantInformation;

/**
 * Created by Gan on 2017/10/18.
 * 六合彩位数选择面板
 */

public class DigitCheckBoxLhcPanel implements CompoundButton.OnCheckedChangeListener {
    private Context context;
    private View view;
    private int digit = 0;
    private CheckBox[] checkBoxes = new CheckBox[6];
    private final  int[] ids = new int[]{R.id.one, R.id.two, R.id.three, R.id.four, R.id.five, R.id.six};
    private SparseBooleanArray digits = new SparseBooleanArray();
    private OnChooseDigitClickListener onChooseDigitClickListener;

    public DigitCheckBoxLhcPanel(Context context, View view, int digit) {
        this.context = context;
        this.view = view;
        this.digit = digit;
        for (int i = 0; i < 6; i++) {
            checkBoxes[i] = (CheckBox) view.findViewById(ids[i]);
            checkBoxes[i].setOnCheckedChangeListener(this);
            digits.put(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i], false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.one:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.LhcZmrx.ONE, true);
                    else
                        digits.put(ConstantInformation.LhcZmrx.ONE, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.two:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.LhcZmrx.TWO, true);
                    else
                        digits.put(ConstantInformation.LhcZmrx.TWO, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.three:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.LhcZmrx.THREE, true);
                    else
                        digits.put(ConstantInformation.LhcZmrx.THREE, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.four:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.LhcZmrx.FOUR, true);
                    else
                        digits.put(ConstantInformation.LhcZmrx.FOUR, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.five:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.LhcZmrx.FIVE, true);
                    else
                        digits.put(ConstantInformation.LhcZmrx.FIVE, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
            case R.id.six:
                if (onChooseDigitClickListener != null) {
                    if (isChecked)
                        digits.put(ConstantInformation.LhcZmrx.SIX, true);
                    else
                        digits.put(ConstantInformation.LhcZmrx.SIX, false);
                    onChooseDigitClickListener.onChooseDigitClick(digits);
                }
                break;
        }
    }

    public interface OnChooseDigitClickListener {
        void onChooseDigitClick(SparseBooleanArray digits);
    }

    public void initCheck() {
        for (int i = 0; i < 6; i++) {
            if (i < digit) {
                checkBoxes[i].setChecked(true);
                digits.put(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i], true);
            } else {
                checkBoxes[i].setChecked(false);
                digits.put(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i], false);
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
