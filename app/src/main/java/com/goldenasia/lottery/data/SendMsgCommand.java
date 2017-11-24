package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2017/2/7.
 */
@RequestConfig(api = "?c=user&a=sendMsg", response = String.class)
public class SendMsgCommand {
    private String target="child";
    private String title;
    private String content;
    private String selectChild;

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSelectChild(String selectChild) {
        this.selectChild = selectChild;
    }
}
