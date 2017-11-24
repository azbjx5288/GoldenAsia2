package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 订单详情
 * Created by Alashi on 2016/1/21.
 */
@RequestConfig(api = "?c=game&a=packageDetail", method = Request.Method.GET, response = BetDetailResponse.class)
public class BetDetailCommand {
    @SerializedName("wrap_id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
