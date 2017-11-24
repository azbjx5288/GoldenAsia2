package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.IssueEntity;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.material.TrendData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACE-PC on 2017/5/19.
 */

public class ChartPage {
    private static final String TAG = ChartPage.class.getSimpleName();

    private Context context;
    private LinearLayout view;

    LinearLayout issuelayout; //奖期
    LinearLayout codelayout;   //开奖号码
    LinearLayout chartlayout;  //走势图

    //开奖号码分格显示
    private int codeStyle = 1;
    private Lottery lottery;
    private List<IssueEntity> items;
    private TrendData trend;
    private ArrayList<IssueView> issueListView = new ArrayList<>();
    private ArrayList<CodeView> codeListView = new ArrayList<>();
    private ArrayList<CodeViewDefault> codeListDefaultView = new ArrayList<>();
    private ArrayList<TrendView> trendListView = new ArrayList<>();
    private View[] issueViews = null;
    private View[] codeViews = null;
    private View[] chartViews = null;
    private Drawable[] checkedDrawable;
    private int[] colorChartList = new int[]{
            Color.parseColor("#129FB4"),
            Color.parseColor("#ee7e45"),
            Color.parseColor("#6ed25b"),
            Color.parseColor("#1ab1ff"),
            Color.parseColor("#9678ff"),
            Color.parseColor("#129FB4"),
            Color.parseColor("#ee7e45"),
            Color.parseColor("#6ed25b"),
            Color.parseColor("#1ab1ff"),
            Color.parseColor("#9678ff")
    };  //万位：129FB4　千位：ee7e45　百位：6ed25b   十位：1ab1ff   个位：9678ff

    public ChartPage(Context context, Lottery lottery, List<IssueEntity> items) {
        this.context = context;
        this.lottery = lottery;
        this.items = items;
        this.view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.chart_page, view,false);
        issuelayout = (LinearLayout) view.findViewById(R.id.view_issue);
        codelayout = (LinearLayout) view.findViewById(R.id.linear_codelayout);
        chartlayout = (LinearLayout) view.findViewById(R.id.linear_chartlayout);
        initData();
    }

    public View getChartView() {
        return view;
    }

    public void initData() {
        int[] ids = {
                R.drawable.bg_chart_circle_wan_ball,
                R.drawable.bg_chart_circle_qian_ball,
                R.drawable.bg_chart_circle_bai_ball,
                R.drawable.bg_chart_circle_shi_ball,
                R.drawable.bg_chart_circle_ge_ball,
                R.drawable.bg_chart_circle_wan_ball,
                R.drawable.bg_chart_circle_qian_ball,
                R.drawable.bg_chart_circle_bai_ball,
                R.drawable.bg_chart_circle_shi_ball,
                R.drawable.bg_chart_circle_ge_ball
        };
        checkedDrawable = new Drawable[ids.length];
        for (int i = 0; i < ids.length; i++) {
            checkedDrawable[i] = context.getResources().getDrawable(ids[i]);
        }
        resolveCode(items);
        displayStyle();
        addChartData();
    }

    public void setData(List<IssueEntity> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        this.items = items;

        resolveCode(items);
        displayStyle();
        addChartData();
    }

    private void displayStyle() {
        issueListView.clear();
        codeListView.clear();
        codeListDefaultView.clear();
        trendListView.clear();
        issuelayout.removeAllViews();
        chartlayout.removeAllViews();
        codelayout.removeAllViews();

        //期号
        createIssueLayout(new String[]{"期号"});
        //开奖号码　风格显示　格式
        switch (codeStyle) {
            case 1:
                createCodeLayout(new String[]{"开奖号码"});
                break;
            default:
                createCodeLayoutDefault(new String[]{"开奖号码"});
                break;
        }
        switch (lottery.getLotteryType()) {
            case 2://山东11选5 6
                createSyxwlayout(new String[]{"万位", "千位", "百位", "十位", "个位"});
                break;
            case 1://重庆时时彩
            case 4: //3D
                if (trend != null) {
                    if (trend.getTrendData().length > 3) {
                        createSsclayout(new String[]{"万位", "千位", "百位", "十位", "个位"});
                    } else {
                        createSsclayout(new String[]{"百位", "十位", "个位"});
                    }
                }
                break;
            case 6: //快三
                createKslayout(new String[]{"百位", "十位", "个位"});
                break;
            case 3://六合彩
                createMarkSixlayout(new String[]{"特码"});
                break;
            case 8://北京PK10
                createSyxwlayout(new String[]{"冠", "亚", "季", "四", "五", "六", "七", "八", "九", "十"});
                break;
            default:
                break;
        }
    }
    /**
     * 创建开奖期号与开奖号码布局
     *
     * @param name
     */
    private void createIssueLayout(String[] name){
        issueViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_issue, null, false);
            createIssueView(view, name[i]);
            issueViews[i] = view;
        }
    }

    /**
     * 创建开奖期号与开奖号码布局
     *
     * @param name
     */
    private void createCodeLayout(String[] name) {
        codeViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_code_column, null, false);
            createCodeView(view, name[i]);
            codeViews[i] = view;
        }
    }

    /**
     * 创建开奖期号与开奖号码布局 Default
     *
     * @param name
     */
    private void createCodeLayoutDefault(String[] name) {
        codeViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_code_column_default, null, false);
            createCodeViewDefault(view, name[i]);
            codeViews[i] = view;
        }
    }

    /**
     * 创建开奖期号与开奖号码
     *
     * @param v
     * @param title
     */
    private void createIssueView(View v, String title) {
        IssueView issueView = new IssueView(v, title);
        issueListView.add(issueView);
    }
    /**
     * 创建开奖期号与开奖号码
     *
     * @param v
     * @param title
     */
    private void createCodeView(View v, String title) {
        CodeView codeView = new CodeView(v, title);
        codeListView.add(codeView);
    }

    /**
     * 创建开奖期号与开奖号码
     *
     * @param v
     * @param title
     */
    private void createCodeViewDefault(View v, String title) {
        CodeViewDefault codeView = new CodeViewDefault(v, title);
        codeListDefaultView.add(codeView);
    }

    /**
     * 添加开奖期号与开奖号码视图
     */
    private void addIssueView() {
        if (issueViews == null || issueViews.length == 0) {
            return;
        }
        for (View view : issueViews) {
            codelayout.addView(view);
        }
    }


    /**
     * 添加开奖期号与开奖号码视图
     */
    private void addCodeView() {
        if (codeViews == null || codeViews.length == 0) {
            return;
        }
        for (View view : codeViews) {
            codelayout.addView(view);
        }
    }

    /**
     * 创建时时彩走势图表布局
     *
     * @param name
     */
    private void createSsclayout(String[] name) {
        chartViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_trend_column, null, false);
            createTrendView(view, name[i], i);
            chartViews[i] = view;
        }
    }
    
    private void createKslayout(String[] name) {
        chartViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_trend_column4, null, false);
            createTrendView(view, name[i], i);
            chartViews[i] = view;
        }
    }

    /**
     * 创建十一选五走势图表布局
     *
     * @param name
     */
    private void createSyxwlayout(String[] name) {
        chartViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_trend_column2, null, false);
            createTrendView(view, name[i], i);
            chartViews[i] = view;
        }
    }

    /**
     * 创建六合彩走势图表布局
     *
     * @param name
     */
    private void createMarkSixlayout(String[] name) {
        chartViews = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.view_trend_column3, null, false);
            createTrendView(view, name[i], i);
            chartViews[i] = view;
        }
    }

    /**
     * 创建走势
     *
     * @param v
     * @param title
     */
    private void createTrendView(View v, String title, int position) {
        TrendView trendView = new TrendView(v, title);
        trendView.setLinkBallLineColor(colorChartList[position]);
        trendView.setCheckedDrawable(checkedDrawable[position]);
        trendListView.add(trendView);
    }

    /**
     * 添加开奖期号与开奖号码视图
     */
    private void addTrendView() {
        if (chartViews == null || chartViews.length == 0) {
            return;
        }
        for (View view : chartViews) {
            chartlayout.addView(view);
        }
    }

    private void resolveCode(List<IssueEntity> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        int item = 0;
    
        
        if (trend != null) {
            if (trend.getTrendData().length > 3) {
                createSsclayout(new String[]{"万位", "千位", "百位", "十位", "个位"});
            } else {
                createSsclayout(new String[]{"百位", "十位", "个位"});
            }
        }
        
        switch (lottery.getLotteryType()) {
            case 2://山东11选5 6
            case 3: //六合彩
            case 8: //PK10
                item = data.get(0).getCode().split(" ").length;
                break;
            case 1://重庆时时彩
            case 4: //3D
            case 6: //快三
                item = data.get(0).getCode().length();
                break;
            default:
                break;
        }
        String[] codeOriginal = new String[data.size()];
        String[] issues = new String[data.size()];
        String[][] codeData = new String[data.size()][item];
        String[][] trendData = new String[item][data.size()];
        String[][] trendSixData = new String[1][data.size()];
        for (int i = 0, size = data.size(); i < size; i++) {
            IssueEntity code = data.get(i);
            if(code.getIssue().length()>4){
                issues[i] = code.getIssue().substring(4,code.getIssue().length());
            }else{
                issues[i] = code.getIssue();
            }
            codeOriginal[i] = code.getCode();
            switch (lottery.getLotteryType()) {
                case 2://山东11选5 6
                case 8: //PK10
                    String[] syxwitem = code.getCode().split(" ");
                    for (int j = 0, length = syxwitem.length; j < length; j++) {
                        codeData[i][j] = syxwitem[j];
                        trendData[j][i] = syxwitem[j];
                    }
                    break;
                case 3: //六合彩
                    String[] sixitem = code.getCode().split(" ");
                    for (int j = 0, length = sixitem.length; j < length; j++) {
                        codeData[i][j] = sixitem[j];
                        if (length - 1 == j) {
                            trendSixData[0][i] = sixitem[j];
                        }
                    }
                    break;
                case 1://重庆时时彩
                case 4: //3D
                case 6: //快三
                    String[] sscitem = code.getCode().split("");
                    StringBuffer sb = new StringBuffer();
                    for (int s = 0, length = sscitem.length; s < length; s++) {
                        if ("".equals(sscitem[s])) {
                            continue;
                        }
                        sb.append(sscitem[s]);
                        if (s != sscitem.length - 1) {
                            sb.append(";");
                        }
                    }
                    //用String的split方法分割，得到数组
                    sscitem = sb.toString().split(";");
                    for (int j = 0, length = sscitem.length; j < length; j++) {
                        codeData[i][j] = sscitem[j];
                        trendData[j][i] = sscitem[j];
                    }
                    break;
                default:
                    break;
            }
        }

        trend = new TrendData();
        trend.setIssue(issues);
        trend.setCodeData(codeData);
        trend.setCodeOriginal(codeOriginal);
        if (lottery.getLotteryId() == 17 || lottery.getLotteryId() == 26) {
            trend.setTrendData(trendSixData);
        } else {
            trend.setTrendData(trendData);
        }
    }

    private void addChartData() {
        if (chartViews != null && chartViews.length == 0 && chartViews != null && chartViews.length == 0) {
            Log.e(TAG, "没有创建视图布局");
            return;
        }
        if (trend != null) {
            addIssueView();
            for (int i = 0,size = issueListView.size(); i < size; i++) {
                IssueView issueView = issueListView.get(i);
                issueView.setCodeData(trend.getIssue());
                issueView.requestLayout();
            }
            addCodeView();
            for (int i = 0, size = codeListView.size(); i < size; i++) {
                switch (codeStyle) {
                    case 1: {
                        CodeView codeView = codeListView.get(i);
                        codeView.setCodeData(trend.getCodeData());
                        codeView.requestLayout();
                        break;
                    }
                    default: {
                        CodeViewDefault codeViewDefault = codeListDefaultView.get(i);
                        codeViewDefault.setCodeData(trend.getCodeOriginal());
                        break;
                    }
                }
            }

            addTrendView();
            for (int i = 0, size = trendListView.size(); i < size; i++) {
                TrendView trendView = trendListView.get(i);
                trendView.setTrendData(trend.getTrendData()[i]);
                trendView.requestLayout();
            }
        }
    }

    public void showHideLines(boolean isChecked) {
        if (trend != null) {
            for (int i = 0, size = trendListView.size(); i < size; i++) {
                TrendView view = trendListView.get(i);
                view.showHideLines(isChecked);
            }
        }
    }

}
