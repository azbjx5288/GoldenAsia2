package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2017/2/14.
 */
@RequestConfig(api = "?c=user&a=sendMsg&op=getChilds")
public class LowerTipsCommand {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }
}
