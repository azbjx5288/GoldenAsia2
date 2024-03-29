package com.goldenasia.lottery.material;

/**
 * 追号规则
 * Created by ACE-PC on 2016/3/30.
 */
public class ChaseRuleData {
    private int type=0;
    private int multiple=1;
    private int multipleTurn=1;
    private int gainMode=0;
    private int issueGap=1;
    private int agoValue=0;
    private int laterValue=0;

    public ChaseRuleData(){

    }

    public ChaseRuleData(int type, int multiple, int multipleTurn, int gainMode, int issueGap, int agoValue, int laterValue) {
        this.type = type;
        this.multiple = multiple;
        this.multipleTurn = multipleTurn;
        this.gainMode = gainMode;
        this.issueGap = issueGap;
        this.agoValue = agoValue;
        this.laterValue = laterValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getIssueGap() {
        return issueGap;
    }

    public void setIssueGap(int issueGap) {
        this.issueGap = issueGap;
    }

    public int getMultipleTurn() {
        return multipleTurn;
    }

    public void setMultipleTurn(int multipleTurn) {
        this.multipleTurn = multipleTurn;
    }

    public int getGainMode() {
        return gainMode;
    }

    public void setGainMode(int gainMode) {
        this.gainMode = gainMode;
    }

    public int getAgoValue() {
        return agoValue;
    }

    public void setAgoValue(int agoValue) {
        this.agoValue = agoValue;
    }

    public int getLaterValue() {
        return laterValue;
    }

    public void setLaterValue(int laterValue) {
        this.laterValue = laterValue;
    }


}
