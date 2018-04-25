package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sakura on 2017/3/16.
 */

public class GaBean implements Serializable
{
    
    /**
     * lotteryId : 101
     * cname : 赢三张
     * url : http://xxx/xx
     * desc : 一句话描述
     * playerNum : 1000
     */
    
    @SerializedName("lottery_id")
    private int lotteryId;
    private String cname;
    private String url;
    private String url_try;
    private String desc;
    @SerializedName("player_num")
    private String playerNum;
    
    public int getLotteryId() { return lotteryId;}
    
    public void setLotteryId(int lotteryId) { this.lotteryId = lotteryId;}
    
    public String getCname() { return cname;}
    
    public void setCname(String cname) { this.cname = cname;}
    
    public String getUrl() { return url;}
    
    public void setUrl(String url) { this.url = url;}
    
    public String getUrl_try()
    {
        return url_try;
    }
    
    public void setUrl_try(String url_try)
    {
        this.url_try = url_try;
    }
    
    public String getDesc() { return desc;}
    
    public void setDesc(String desc) { this.desc = desc;}
    
    public String getPlayerNum() { return playerNum;}
    
    public void setPlayerNum(String playerNum) { this.playerNum = playerNum;}
}
