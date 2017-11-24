package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2016/1/26.
 */
@RequestConfig(api = "?c=game&a=lotteriesHistory",method = Request.Method.GET)
public class LotteriesHistoryCommand {
    private int lotteryID=0;
    private int curPage;
    private int perPage=10;

    public int getLotteryID() {
        return lotteryID;
    }

    public void setLotteryID(int lotteryID) {
        this.lotteryID = lotteryID;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }
}
