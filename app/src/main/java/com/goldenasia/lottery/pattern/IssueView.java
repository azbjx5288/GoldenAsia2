package com.goldenasia.lottery.pattern;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.view.DrawCodeView;

/**
 * Created by ACE-PC on 2016/3/11.
 */
public class IssueView {

    private static final String TAG = IssueView.class.getSimpleName();
    private LinearLayout allotedView;
    public IssueView(View trendView, String title){
        ((TextView) trendView.findViewById(R.id.issue_column_title)).setText(title);
        allotedView = (LinearLayout) trendView.findViewById(R.id.alloted);
    }

    public void setCodeData(String[] trendArray){
        if(trendArray==null||trendArray.length==0){
            return;
        }

        for(int i=0,length=trendArray.length;i<length;i++){
            LinearLayout codeView=(LinearLayout) LayoutInflater.from(allotedView.getContext()).inflate(R.layout.view_code,null, false);
            TextView codeText=((TextView)codeView.findViewById(R.id.code));
            codeText.setText(trendArray[i]);
            codeText.setTextColor(allotedView.getContext().getResources().getColor(R.color.app_font_dark_color));
            //隔行变色
            if (i % 2 == 0) {
                codeView.setBackgroundColor(Color.parseColor("#E7E9EE"));
            } else {
                codeView.setBackgroundColor(Color.parseColor("#EEF0F3"));
            }
            allotedView.addView(codeView);
        }

        /*for(int i = trendArray.length - 1; i >= 0; i--){

        }*/
    }

    public void requestLayout() {
    }
}
