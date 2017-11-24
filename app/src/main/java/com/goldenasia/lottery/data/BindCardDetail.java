package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class BindCardDetail {

    /**
     * bind_card_id : 25
     * user_id : 56931
     * username : proxy1
     * bank_id : 22
     * card_num : ****************627
     * province : 上海
     * city : 上海
     * branch : 上海市上海
     * create_time : 2016-05-05 16:04:00
     * status : 1
     * finish_admin_id : 0
     * remark :
     * ts : 2016-05-05 16:04:00
     */

    @SerializedName("bind_card_id")
    private String bindCardId;
    @SerializedName("user_id")
    private String userId;
    @SerializedName("username")
    private String username;
    @SerializedName("bank_id")
    private String bankId;
    @SerializedName("card_num")
    private String cardNum;
    @SerializedName("province")
    private String province;
    @SerializedName("city")
    private String city;
    @SerializedName("branch")
    private String branch;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("status")
    private String status;
    @SerializedName("finish_admin_id")
    private String finishAdminId;
    @SerializedName("remark")
    private String remark;
    @SerializedName("ts")
    private String ts;

    public void setBindCardId(String bindCardId) {
        this.bindCardId = bindCardId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFinishAdminId(String finishAdminId) {
        this.finishAdminId = finishAdminId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getBindCardId() {
        return bindCardId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getBankId() {
        return bankId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getBranch() {
        return branch;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getStatus() {
        return status;
    }

    public String getFinishAdminId() {
        return finishAdminId;
    }

    public String getRemark() {
        return remark;
    }

    public String getTs() {
        return ts;
    }
}
