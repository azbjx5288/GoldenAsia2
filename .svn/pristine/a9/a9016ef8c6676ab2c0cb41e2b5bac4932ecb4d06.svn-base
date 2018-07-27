package com.goldenasia.lottery.view;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.WheelView2;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.SharedPreferencesUtils;

import java.util.ArrayList;

/**
 * TimePickerLayout
 * put two WheelView2 into  LinearLayout
 * 时间段 二级选择
 * https://github.com/helloJp/WheelView
 *
 * Created by Gan on 2017/11/11.
 */

public class TimePickerLayout extends LinearLayout {

    private WheelView2 mTime01Picker;
    private WheelView2 mTime02Picker;

    private int mCurrTime01Index = 0;
    private int mCurrTime02Index = 0;

    private  String TIME1="time1";
    private  String TIME2="time2";

    private ArrayList<String> mTimeList = new ArrayList<>();

    public TimePickerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCurrTime01Index= getInt(context, ConstantInformation.APP_INFO, TIME1);
        mCurrTime02Index= getInt(context, ConstantInformation.APP_INFO, TIME2);

        getDate();
    }

    public TimePickerLayout(Context context) {
        this(context, null);
    }


    private  int getInt(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    private void getDate() {
        for(int i=0;i<24;i++){
            if(i<10){
                mTimeList.add("0"+i+":00");
            }else{
                mTimeList.add(i+":00");
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_time_picker, this);

        mTime01Picker = (WheelView2) findViewById(R.id.time01_wv);
        mTime02Picker = (WheelView2) findViewById(R.id.time02_wv);


        mTime01Picker.setData(mTimeList);
        mTime01Picker.setDefault(mCurrTime01Index);

        mTime02Picker.setData(mTimeList);
        mTime02Picker.setDefault(mCurrTime02Index);


        mTime01Picker.setOnSelectListener(new WheelView2.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                if (mCurrTime01Index != id) {
                    mCurrTime01Index = id;
                }

            }

            @Override
            public void selecting(int id, String text) {
            }
        });

        mTime02Picker.setOnSelectListener(new WheelView2.OnSelectListener() {

            @Override
            public void endSelect(int id, String text) {
                if (text.equals("") || text == null)
                    return;
                if (mCurrTime02Index != id) {
                    mCurrTime02Index = id;
                }
            }

            @Override
            public void selecting(int id, String text) {

            }
        });

    }

    public String getTime01() {
        if (mTime01Picker == null) {
            return null;
        }
        return mTime01Picker.getSelectedText();
    }

    public String getTime02() {
        if (mTime02Picker == null) {
            return null;
        }
        return mTime02Picker.getSelectedText();
    }

    public int getmCurrTime01Index() {
        return mTime01Picker.getSelected();
    }

    public int getmCurrTime02Index() {
        return mTime02Picker.getSelected();
    }

}
