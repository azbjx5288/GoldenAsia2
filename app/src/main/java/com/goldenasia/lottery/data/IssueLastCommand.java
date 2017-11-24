package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 开奖信息获取
 * Created by ACE-PC on 2016/1/22.
 */

@RequestConfig(api = "?c=game&a=getIssueInfo")
public class IssueLastCommand {
    private String op;

    private int lotteryId;

    private String issue;

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}

