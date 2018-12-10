package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

public class CollectScheme {

    /**
     * collection_id : 1
     * user_id : 17338
     * lottery _id : 1
     * method_id : 1
     * code : 1,2,3|2,3,4
     * single_num : 1
     * prize_mode : 1
     * cname : 前三直选
     * ts : 2015-02-18 13:45:44
     */

    @SerializedName("collection_id")
    private long collectionId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("lottery _id")
    private int lotteryId; // FIXME check this code
    @SerializedName("method_id")
    private int methodId;
    @SerializedName("code")
    private String code;
    @SerializedName("single_num")
    private int singleNum;
    @SerializedName("prize_mode")
    private int prizeMode;
    @SerializedName("cname")
    private String cname;
    @SerializedName("ts")
    private String ts;

    public boolean isChecked;

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public int getMethodId() {
        return methodId;
    }

    public void setMethodId(int methodId) {
        this.methodId = methodId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(int singleNum) {
        this.singleNum = singleNum;
    }

    public int getPrizeMode() {
        return prizeMode;
    }

    public void setPrizeMode(int prizeMode) {
        this.prizeMode = prizeMode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
