package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2017/1/16.
 */
public class JVYWeChatPay {

    /**
     * submitUrl : http://101.200.201.251/weixin/Payment
     * version : 1
     * is_phone : 1
     * is_frame : 1
     * pay_type : 30
     * agent_id : ivy8601
     * agent_bill_id : JKH2017011617130524450067
     * pay_amt : 66.0
     * notify_url : http://116.66.238.29/jkhWechatBack.php
     * user_ip : 10.50.200.74
     * agent_bill_time : 20170116171305
     * goods_name : Q币充值卡
     * remark : wechat
     * sign : d41d8cd98f00b204e9800998ecf8427e
     */

    @SerializedName("submitUrl")
    private String submitUrl;
    @SerializedName("version")
    private String version;
    @SerializedName("is_phone")
    private int isPhone;
    @SerializedName("is_frame")
    private int isFrame;
    @SerializedName("pay_type")
    private int payType;
    @SerializedName("agent_id")
    private String agentId;
    @SerializedName("agent_bill_id")
    private String agentBillId;
    @SerializedName("pay_amt")
    private String payAmt;
    @SerializedName("notify_url")
    private String notifyUrl;
    @SerializedName("user_ip")
    private String userIp;
    @SerializedName("agent_bill_time")
    private String agentBillTime;
    @SerializedName("goods_name")
    private String goodsName;
    @SerializedName("remark")
    private String remark;
    @SerializedName("sign")
    private String sign;

    public String getSubmitUrl() {
        return submitUrl;
    }

    public void setSubmitUrl(String submitUrl) {
        this.submitUrl = submitUrl;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getIsPhone() {
        return isPhone;
    }

    public void setIsPhone(int isPhone) {
        this.isPhone = isPhone;
    }

    public int getIsFrame() {
        return isFrame;
    }

    public void setIsFrame(int isFrame) {
        this.isFrame = isFrame;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentBillId() {
        return agentBillId;
    }

    public void setAgentBillId(String agentBillId) {
        this.agentBillId = agentBillId;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getUserIp() {
        return userIp;
    }

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }

    public String getAgentBillTime() {
        return agentBillTime;
    }

    public void setAgentBillTime(String agentBillTime) {
        this.agentBillTime = agentBillTime;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
