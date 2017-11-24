package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 某彩种玩法信息查询
 * Created by ACE-PC on 2016/1/21.
 */
@RequestConfig(api = "?c=game&a=methodList")
public class MethodListCommand {
    /**Int	不为空	lotteryID，空表示查所有彩种*/
    private int lotteryID;
    /**
     * Int 不为空  methodGroupID 返回某彩种的所有玩法
     */
    private int methodGroupID;

    public int getMethodGroupID() {
        return methodGroupID;
    }

    public void setMethodGroupID(int methodGroupID) {
        this.methodGroupID = methodGroupID;
    }

    public int getLotteryID() {
        return lotteryID;
    }

    public void setLotteryID(int lotteryID) {
        this.lotteryID = lotteryID;
    }
}
