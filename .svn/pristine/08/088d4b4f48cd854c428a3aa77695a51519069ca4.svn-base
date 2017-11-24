package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 追号撤单
 * Created by Alashi on 2016/2/8.
 */
@RequestConfig(api = "?c=game&a=cancelTrace")
public class CancelTraceCommand {
    @SerializedName("wrap_id")
    private String wrapId;
    private List<String> pkids;

    public void setWrapId(String wrapId) {
        this.wrapId = wrapId;
    }

    public void setPkids(List<String> pkids) {
        this.pkids = pkids;
    }
}
