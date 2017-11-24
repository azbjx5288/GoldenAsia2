package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Gan on 2017/10/26.
 */
@RequestConfig(api = "?c=game&a= gagamelist")
public class GaGameListCommand {
    private String startDate;
    private String endDate;
    private int curPage;
    private int perPage = 20;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
