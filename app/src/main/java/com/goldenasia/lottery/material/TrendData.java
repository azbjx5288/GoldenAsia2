package com.goldenasia.lottery.material;

/**
 * Created by ACE-PC on 2016/3/11.
 */
public class TrendData {

    private String[] issue;
    private String[] codeOriginal;
    private String[][] codeData;
    private String[][] trendData;

    public String[] getIssue() {
        return issue;
    }

    public void setIssue(String[] issue) {
        this.issue = issue;
    }

    public String[] getCodeOriginal() {
        return codeOriginal;
    }

    public void setCodeOriginal(String[] codeOriginal) {
        this.codeOriginal = codeOriginal;
    }

    public String[][] getCodeData() {
        return codeData;
    }

    public void setCodeData(String[][] codeData) {
        this.codeData = codeData;
    }

    public String[][] getTrendData() {
        return trendData;
    }

    public void setTrendData(String[][] trendData) {
        this.trendData = trendData;
    }
}
