package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

import java.util.Date;

/**
 * Created by ACE-PC on 2016/5/3.
 */

@RequestConfig(api = "?c=user&a=getChildList",method = Request.Method.GET, response = LowerMemberList.class)
public class LowerMemberCommand {
    private String username;
    private Date regStartDate;
    private Date regEndDate;
    private int range=0;  //0=只查直接下级 1=所有下级
    private int curPage=1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public Date getRegEndDate() {
        return regEndDate;
    }

    public void setRegEndDate(Date regEndDate) {
        this.regEndDate = regEndDate;
    }

    public Date getRegStartDate() {
        return regStartDate;
    }

    public void setRegStartDate(Date regStartDate) {
        this.regStartDate = regStartDate;
    }
}
