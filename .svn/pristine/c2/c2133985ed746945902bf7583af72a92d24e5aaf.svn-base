package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.util.SscLHHLuDan;
import com.goldenasia.lottery.view.LuDanContentView;

import java.util.List;

public class LuDanView {
    private Context context;
    private View digitsPanelLudanContent;
    private LuDanContentView luDanContentview;
    private TextView ludan_title;

    private LuDanView(View topView) {
        this.context = topView.getContext();
        digitsPanelLudanContent = topView.inflate(context, R.layout.digits_panel_ludan_content, null);
        luDanContentview = digitsPanelLudanContent.findViewById(R.id.lu_dan_contentview);
        ludan_title = digitsPanelLudanContent.findViewById(R.id.ludan_title);
        initLuDanData();
    }

    private void initLuDanData() {
        List<List<String>> luDanDataList;
        switch (SharedPreferencesUtils.getInt(context, ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck, 1)) {
            case 1:
                ludan_title.setText("万千路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(0, 1);
                break;
            case 2:
                ludan_title.setText("万百路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(0, 2);
                break;
            case 3:
                ludan_title.setText("万十路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(0, 3);
                break;
            case 4:
                ludan_title.setText("万个路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(0, 4);
                break;
            case 5:
                ludan_title.setText("千百路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(1, 2);
                break;
            case 6:
                ludan_title.setText("千十路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(1, 3);
                break;
            case 7:
                ludan_title.setText("千个路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(1, 4);
                break;
            case 8:
                ludan_title.setText("百十路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(2, 3);
                break;
            case 9:
                ludan_title.setText("百个路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(2, 4);
                break;
            case 10:
                ludan_title.setText("十个路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(3, 4);
                break;
            default:
                ludan_title.setText("万千路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList(0, 1);
                break;
        }
        luDanContentview.setDataList(luDanDataList);
    }

    public View getLudanPanel() {
        return digitsPanelLudanContent;
    }

    public static LuDanView from(View view) {
        LuDanView luDanView = new LuDanView(view);
        return luDanView;
    }

    public void setLuDanList(List<List<String>> luDanDataList) {
        luDanContentview.setDataList(luDanDataList);
    }

    public void refreshView() {
        initLuDanData();
    }
}
