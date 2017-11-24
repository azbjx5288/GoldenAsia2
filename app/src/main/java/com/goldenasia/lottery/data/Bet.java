package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alashi on 2016/1/22.
 */
public class Bet {
    /*check_prize_status
    中奖判断状态(0:未判断;1:中奖;2:未中奖)
    send_prize_status
    派奖状态(0:未派奖;1:已派奖)
    cancel_status
    是否撤单(0未撤单 1用户撤单 2追中撤单 3出号撤单 4未开撤单 9管理员撤单
    */
    /**
     * package_id : 27087918
     * user_id : 1269
     * top_id : 1269
     * lottery_id : 11
     * issue : 20160122-0814
     * trace_id : 0
     * single_num : 3
     * multiple : 1
     * cur_rebate : 0.128
     * modes : 1
     * amount : 6.00
     * prize : 0.0000
     * create_time : 2016-01-22 14:33:46
     * check_prize_status : 2
     * send_prize_status : 0
     * send_prize_time : 2016-01-22 14:35:02
     * cancel_status : 0
     * cancel_admin_id : 0
     * cancel_time : 0000-00-00 00:00:00
     * user_ip : 203.82.46.57
     * proxy_ip : 203.82.46.57
     * server_ip : ssc.la
     * ts : 2016-01-22 14:35:02
     * username : zddavy
     * user_status : 8
     * wrap_id : YF0122081427087918P
     * prize_mode : 1700
     */

    @SerializedName("package_id")
    private int packageId;
    @SerializedName("user_id")
    private int userId;
    @SerializedName("top_id")
    private int topId;
    @SerializedName("lottery_id")
    private int lotteryId;
    private String issue;
    @SerializedName("trace_id")
    private String traceId;
    @SerializedName("single_num")
    private int singleNum;
    private int multiple;
    @SerializedName("cur_rebate")
    private float curRebate;
    private String modes;
    private float amount;
    private String prize;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("check_prize_status")
    private int checkPrizeStatus;
    @SerializedName("send_prize_status")
    private int sendPrizeStatus;
    @SerializedName("send_prize_time")
    private String sendPrizeTime;
    @SerializedName("cancel_status")
    private int cancelStatus;
    @SerializedName("cancel_admin_id")
    private int cancelAdminId;
    @SerializedName("cancel_time")
    private String cancelTime;
    @SerializedName("user_ip")
    private String userIp;
    @SerializedName("proxy_ip")
    private String proxyIp;
    @SerializedName("server_ip")
    private String serverIp;
    private String ts;
    @SerializedName("username")
    private String userName;
    @SerializedName("user_status")
    private int userStatus;
    @SerializedName("wrap_id")
    private String wrapId;
    @SerializedName("prize_mode")
    private float prizeMode;

    public int getPackageId() {
        return packageId;
    }

    public int getUserId() {
        return userId;
    }

    public int getTopId() {
        return topId;
    }

    public int getLotteryId() {
        return lotteryId;
    }

    public String getIssue() {
        return issue;
    }

    public String getTraceId() {
        return traceId;
    }

    public int getSingleNum() {
        return singleNum;
    }

    public int getMultiple() {
        return multiple;
    }

    public float getCurRebate() {
        return curRebate;
    }

    public String getModes() {
        return modes;
    }

    public float getAmount() {
        return amount;
    }

    public String getPrize() {
        return prize;
    }

    public String getCreateTime() {
        return createTime;
    }

    /** 0:未判断;1:中奖;2:未中奖 */
    public int getCheckPrizeStatus() {
        return checkPrizeStatus;
    }

    /**派奖状态(0:未派奖;1:已派奖)*/
    public int getSendPrizeStatus() {
        return sendPrizeStatus;
    }

    public String getSendPrizeTime() {
        return sendPrizeTime;
    }

    /**是否撤单(0未撤单 1用户撤单 2追中撤单 3出号撤单 4未开撤单 9管理员撤单*/
    public int getCancelStatus() {
        return cancelStatus;
    }

    public int getCancelAdminId() {
        return cancelAdminId;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public String getUserIp() {
        return userIp;
    }

    public String getProxyIp() {
        return proxyIp;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getTs() {
        return ts;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public String getWrapId() {
        return wrapId;
    }

    public void setWrapId(String wrapId){
        this.wrapId=wrapId;
    }

    public float getPrizeMode() {
        return prizeMode;
    }
}
