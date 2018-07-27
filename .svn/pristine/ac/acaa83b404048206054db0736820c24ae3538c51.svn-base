package com.goldenasia.lottery.data;

/**
 * 4.3.6	会员报表
 * Created by Gan on 2017/10/23.
 */

import com.goldenasia.lottery.base.net.RequestConfig;

@RequestConfig(api = "?c=user&a=childReport")
public class ChildReportCommand {

    private String username;
    private String sortKey;
    private int sortDirection;
    private String startDate;//		2016-10-20	起始日期
    private String endDate	;//	String	2017-10-20	截止日期
    private int curPage	=1	;//	当前页码
    private int pageSize=20	;//	每页数量

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(int sortDirection) {
        this.sortDirection = sortDirection;
    }

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

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
