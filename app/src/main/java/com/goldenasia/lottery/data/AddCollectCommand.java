package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

@RequestConfig(api = "?c=game&a=collect",response = String.class)
public class AddCollectCommand {

    private int lotteryId;
    private int prizeMode;
    private String codes;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setPrizeMode(int prizeMode) {
        this.prizeMode = prizeMode;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }
}
