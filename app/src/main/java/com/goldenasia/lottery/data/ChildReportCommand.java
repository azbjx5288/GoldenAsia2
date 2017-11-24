package com.goldenasia.lottery.data;

/**
 * 4.3.6	会员报表
 * Created by Gan on 2017/10/23.
 */

import com.goldenasia.lottery.base.net.RequestConfig;

@RequestConfig(api = "?c=user&a=childReport")
public class ChildReportCommand {

    private String startDate;//		2016-10-20	起始日期
    private String endDate	;//	String	2017-10-20	截止日期
    private int curPage	=1	;//	当前页码
    private int perPage=20	;//	每页数量

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

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
