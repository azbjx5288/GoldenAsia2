package com.goldenasia.lottery.pattern;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;

/**
 * Created by ACE-PC on 2017/6/2.
 */

public class CodeViewDefault {

    private static final String TAG = CodeView.class.getSimpleName();
    private LinearLayout codeLayout;
    public CodeViewDefault(View trendView, String title){
        ((TextView) trendView.findViewById(R.id.code_column_title)).setText(title);
        codeLayout = (LinearLayout) trendView.findViewById(R.id.codeLayout);
    }

    public void setCodeData(String[] trendArray){
        if(trendArray==null||trendArray.length==0){
            return;
        }

        for(int i = trendArray.length - 1; i >= 0; i--){
            LinearLayout codeView=(LinearLayout)LayoutInflater.from(codeLayout.getContext()).inflate(R.layout.view_code,null, false);
            TextView codeText=((TextView)codeView.findViewById(R.id.code));
            codeText.setText(trendArray[i]);
            codeText.setTextColor(codeLayout.getContext().getResources().getColor(R.color.app_main_support));
            //隔行变色
            if (i % 2 == 0) {
                codeView.setBackgroundColor(Color.parseColor("#E7E9EE"));
            } else {
                codeView.setBackgroundColor(Color.parseColor("#EEF0F3"));
            }
            codeLayout.addView(codeView);
        }
    }

    public void requestLayout() {
       /* if (drawCodeView != null && !drawCodeView.isLayoutRequested()) {
            drawCodeView.requestLayout();
        }*/
    }
}
