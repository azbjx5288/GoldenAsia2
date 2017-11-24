package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ACE-PC on 2016/3/1.
 */
public class LotteryHistoryCode {


    /**
     * issue : [{"issue_id":"750716","issue":"20160301-0886","code":"91829"},{"issue_id":"750715","issue":"20160301-0885","code":"48628"},{"issue_id":"750714","issue":"20160301-0884","code":"73373"}]
     * totalNum : 19
     */

    @SerializedName("totalNum")
    private int totalNum;
    /**
     * issue_id : 750716
     * issue : 20160301-0886
     * code : 91829
     */

    @SerializedName("issue")
    private List<IssueEntity> issue;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public List<IssueEntity> getIssue() {
        return issue;
    }

    public void setIssue(List<IssueEntity> issue) {
        this.issue = issue;
    }
}
