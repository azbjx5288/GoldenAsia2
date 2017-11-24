package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * 公告与banner的列表项
 * Created by Alashi on 2016/1/19.
 */
public class Notice {
    
    
    /**
     * ad_id : 1
     * ap_id : 4
     * title : 100万大奖
     * content : upload/ad/201702150222596366.png
     * link : http://test.com/?a={jcgame_url}&b={mytest}
     * target : _blank
     * start_time : 2017-02-15 00:00:00
     * end_time : 2017-02-17 23:59:59
     * status : 1
     * create_time : 2017-02-15 14:27:00
     * ts : 2017-02-15 15:58:17
     */
    
    @SerializedName("notice_id")
    private int noticeID;
    @SerializedName("ad_id")
    private int adId;
    @SerializedName("ap_id")
    private int apId;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("link")
    private String link;
    @SerializedName("target")
    private String target;
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
    @SerializedName("status")
    private String status;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("ts")
    private String ts;
    
    public int getNoticeID()
    {
        return noticeID;
    }
    
    public void setNoticeID(int noticeID)
    {
        this.noticeID = noticeID;
    }
    
    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public int getApId() {
        return apId;
    }

    public void setApId(int apId) {
        this.apId = apId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
