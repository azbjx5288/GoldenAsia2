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

public class LuDanView2 {
    private Context context;
    private View digitsPanelLudanContent;
    private LuDanContentView luDanContentview;
    private TextView ludan_title;

    private LuDanView2(View topView) {
        this.context = topView.getContext();
        digitsPanelLudanContent = topView.inflate(context, R.layout.digits_panel_ludan_content2, null);
        luDanContentview = digitsPanelLudanContent.findViewById(R.id.lu_dan_contentview);
        ludan_title = digitsPanelLudanContent.findViewById(R.id.ludan_title);
        initLuDanData();
    }

    private void initLuDanData() {
        List<List<String>> luDanDataList;
        switch (SharedPreferencesUtils.getInt(context, ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 1)) {
            case 1://一V二 一V三 一V四 一V五 二V三 二V四 二V五 三V四 三V五 四V五
                ludan_title.setText("一V二路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 1);
                break;
            case 2:
                ludan_title.setText("一V三路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 2);
                break;
            case 3:
                ludan_title.setText("一V四路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 3);
                break;
            case 4:
                ludan_title.setText("一V五路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 4);
                break;
            case 5:
                ludan_title.setText("二V三路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(1, 2);
                break;
            case 6:
                ludan_title.setText("二V四路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(1, 3);
                break;
            case 7:
                ludan_title.setText("二V五路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(1, 4);
                break;
            case 8:
                ludan_title.setText("三V四路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(2, 3);
                break;
            case 9:
                ludan_title.setText("三V五路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(2, 4);
                break;
            case 10:
                ludan_title.setText("四V五路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(3, 4);
                break;
            default:
                ludan_title.setText("一V二路单");
                luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 1);
                break;
        }
        luDanContentview.setDataList(luDanDataList);
    }

    public View getLudanPanel() {
        return digitsPanelLudanContent;
    }

    public static LuDanView2 from(View view) {
        LuDanView2 luDanView = new LuDanView2(view);
        return luDanView;
    }

    public void setLuDanList(List<List<String>> luDanDataList) {

        switch (SharedPreferencesUtils.getInt(context, ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 1)) {
            case 1://一V二 一V三 一V四 一V五 二V三 二V四 二V五 三V四 三V五 四V五
                ludan_title.setText("一V二路单");
                break;
            case 2:
                ludan_title.setText("一V三路单");
                break;
            case 3:
                ludan_title.setText("一V四路单");
                break;
            case 4:
                ludan_title.setText("一V五路单");
                break;
            case 5:
                ludan_title.setText("二V三路单");
                break;
            case 6:
                ludan_title.setText("二V四路单");
                break;
            case 7:
                ludan_title.setText("二V五路单");
                break;
            case 8:
                ludan_title.setText("三V四路单");
                break;
            case 9:
                ludan_title.setText("三V五路单");
                break;
            case 10:
                ludan_title.setText("四V五路单");
                break;
            default:
                ludan_title.setText("一V二路单");
                break;
        }
        luDanContentview.setDataList(luDanDataList);
    }

    public void refreshView() {
        initLuDanData();
    }
}
