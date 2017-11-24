package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2016/5/4.
 */

@RequestConfig(api = "?c=user&a=regChild", response = String.class)
public class RegChildRebateCommand {
    private String op;
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        this.op = op;
    }
}
