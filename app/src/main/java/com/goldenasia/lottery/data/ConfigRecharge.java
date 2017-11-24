package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/11/18.
 */

public class ConfigRecharge {
    /**
     * min_deposit_limit : 1
     * max_yee_deposit_limit : 50000
     * common_max_deposit_limit : 10000
     * din_max_deposit_limit : 1000000
     * ips_max_deposit_limit : 100000
     * online_alipay_day_max_deposit_limit : 150000
     * online_alipay_max_deposit_limit : 2000
     * hna_max_deposit_limit : 1000000
     * eypal_max_deposit_limit : 1000000
     * guo_max_deposit_limit : 1000000
     */

    @SerializedName("min_deposit_limit")
    private int minDepositLimit;
    @SerializedName("max_yee_deposit_limit")
    private int maxYeeDepositLimit;
    @SerializedName("common_max_deposit_limit")
    private int commonMaxDepositLimit;
    @SerializedName("din_max_deposit_limit")
    private int dinMaxDepositLimit;
    @SerializedName("ips_max_deposit_limit")
    private int ipsMaxDepositLimit;
    @SerializedName("online_alipay_day_max_deposit_limit")
    private int onlineAlipayDayMaxDepositLimit;
    @SerializedName("online_alipay_max_deposit_limit")
    private int onlineAlipayMaxDepositLimit;
    @SerializedName("hna_max_deposit_limit")
    private int hnaMaxDepositLimit;
    @SerializedName("eypal_max_deposit_limit")
    private int eypalMaxDepositLimit;
    @SerializedName("guo_max_deposit_limit")
    private int guoMaxDepositLimit;

    public int getMinDepositLimit() {
        return minDepositLimit;
    }

    public void setMinDepositLimit(int minDepositLimit) {
        this.minDepositLimit = minDepositLimit;
    }

    public int getMaxYeeDepositLimit() {
        return maxYeeDepositLimit;
    }

    public void setMaxYeeDepositLimit(int maxYeeDepositLimit) {
        this.maxYeeDepositLimit = maxYeeDepositLimit;
    }

    public int getCommonMaxDepositLimit() {
        return commonMaxDepositLimit;
    }

    public void setCommonMaxDepositLimit(int commonMaxDepositLimit) {
        this.commonMaxDepositLimit = commonMaxDepositLimit;
    }

    public int getDinMaxDepositLimit() {
        return dinMaxDepositLimit;
    }

    public void setDinMaxDepositLimit(int dinMaxDepositLimit) {
        this.dinMaxDepositLimit = dinMaxDepositLimit;
    }

    public int getIpsMaxDepositLimit() {
        return ipsMaxDepositLimit;
    }

    public void setIpsMaxDepositLimit(int ipsMaxDepositLimit) {
        this.ipsMaxDepositLimit = ipsMaxDepositLimit;
    }

    public int getOnlineAlipayDayMaxDepositLimit() {
        return onlineAlipayDayMaxDepositLimit;
    }

    public void setOnlineAlipayDayMaxDepositLimit(int onlineAlipayDayMaxDepositLimit) {
        this.onlineAlipayDayMaxDepositLimit = onlineAlipayDayMaxDepositLimit;
    }

    public int getOnlineAlipayMaxDepositLimit() {
        return onlineAlipayMaxDepositLimit;
    }

    public void setOnlineAlipayMaxDepositLimit(int onlineAlipayMaxDepositLimit) {
        this.onlineAlipayMaxDepositLimit = onlineAlipayMaxDepositLimit;
    }

    public int getHnaMaxDepositLimit() {
        return hnaMaxDepositLimit;
    }

    public void setHnaMaxDepositLimit(int hnaMaxDepositLimit) {
        this.hnaMaxDepositLimit = hnaMaxDepositLimit;
    }

    public int getEypalMaxDepositLimit() {
        return eypalMaxDepositLimit;
    }

    public void setEypalMaxDepositLimit(int eypalMaxDepositLimit) {
        this.eypalMaxDepositLimit = eypalMaxDepositLimit;
    }

    public int getGuoMaxDepositLimit() {
        return guoMaxDepositLimit;
    }

    public void setGuoMaxDepositLimit(int guoMaxDepositLimit) {
        this.guoMaxDepositLimit = guoMaxDepositLimit;
    }
}
