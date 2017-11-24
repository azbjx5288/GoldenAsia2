package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ACE-PC on 2016/6/24.
 */
public class RechargeConfig implements Serializable {

    /**
     * dt_id : 30
     * bank_id : 212
     * pay_name : 乐付微信
     * card_id : 12
     * trade_type : 8
     * shopURL : http://test3rd.jinyazhou88.org/lePay.php
     * min_limit : 1
     * max_limit : 50000
     * card_codes :
     * is_href : 1
     */

    @SerializedName("dt_id")
    private int dtId;
    @SerializedName("bank_id")
    private int bankId;
    @SerializedName("pay_name")
    private String payName;
    @SerializedName("card_id")
    private int cardId;
    @SerializedName("trade_type")
    private int tradeType;
    @SerializedName("shopURL")
    private String shopURL;
    @SerializedName("min_limit")
    private float minLimit;
    @SerializedName("max_limit")
    private float maxLimit;
    @SerializedName("card_codes")
    private String cardCodes;
    @SerializedName("is_href")
    private boolean isHref;

    public int getDtId() {
        return dtId;
    }

    public void setDtId(int dtId) {
        this.dtId = dtId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public int getTradeType() {
        return tradeType;
    }

    public void setTradeType(int tradeType) {
        this.tradeType = tradeType;
    }

    public String getShopURL() {
        return shopURL;
    }

    public void setShopURL(String shopURL) {
        this.shopURL = shopURL;
    }

    public float getMinLimit() {
        return minLimit;
    }

    public void setMinLimit(float minLimit) {
        this.minLimit = minLimit;
    }

    public float getMaxLimit() {
        return maxLimit;
    }

    public void setMaxLimit(float maxLimit) {
        this.maxLimit = maxLimit;
    }

    public String getCardCodes() {
        return cardCodes;
    }

    public void setCardCodes(String cardCodes) {
        this.cardCodes = cardCodes;
    }

    public boolean isHref() {
        return isHref;
    }

    public void setHref(boolean href) {
        isHref = href;
    }
}
