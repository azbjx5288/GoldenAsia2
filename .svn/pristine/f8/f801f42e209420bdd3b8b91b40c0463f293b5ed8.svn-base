package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 追号订单详情
 * Created by Alashi on 2016/1/21.
 */
@RequestConfig(api = "?c=game&a=traceDetail", method = Request.Method.GET)
public class TraceDetailCommand {
    @SerializedName("wrap_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
