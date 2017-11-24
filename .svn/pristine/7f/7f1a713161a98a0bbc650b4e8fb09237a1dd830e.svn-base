package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

import java.util.List;

/**
 * 提交订单
 * Created by ACE-PC on 2016/2/4.
 */
@RequestConfig(api = "?c=game&a=play", response = String.class)
public class PayMoneyCommand {

    private int lotteryId;		    //11	彩种ID
    private String issue;		    //20160126-0774	奖期
    private int prizeMode;		//1 表示最大奖金，0最小奖金 0	  默认为0
    private String modes;		    //1	圆角分模式 0.1 0.01
    private String codes;	        //char	271:8,8,8	投注号
    private int multiple;		    //1	倍数
    private int traceNum;	        //	期数
    private List<TraceData> traceData;          //traceData[0][issue]:20160126-0775 traceData[0][multiple]:1 traceData[0][ issue]:20160126-0776 traceData[0][multiple]:1
    private boolean stopOnWin;		//0(追中不停)1(追中即停)	追中即停
    private String token;	        //1453787628864ie69o9oyi3mgr77uvppedx03o4yx5qxk	订单唯一凭据

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

    public int isPrizeMode() {
        return prizeMode;
    }

    public void setPrizeMode(int prizeMode) {
        this.prizeMode = prizeMode;
    }

    public String getModes() {
        return modes;
    }

    public void setModes(String modes) {
        this.modes = modes;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public int getTraceNum() {
        return traceNum;
    }

    public void setTraceNum(int traceNum) {
        this.traceNum = traceNum;
    }

    public List<TraceData> getTraceData() {
        return traceData;
    }

    public void setTraceData(List<TraceData> traceData) {
        this.traceData = traceData;
    }

    public boolean isStopOnWin() {
        return stopOnWin;
    }

    public void setStopOnWin(boolean stopOnWin) {
        this.stopOnWin = stopOnWin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
