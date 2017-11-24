package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 获取公告或banner的详情
 * Created by User on 2016/2/19.
 */
@RequestConfig(api = "?a=noticeDetail", method = Request.Method.GET,
        response = NoticeDetail.class)
public class NoticeDetailCommand {

    private int type;
    private int nid;

    public void setType(int type) {
        this.type = type;
    }

    public void setNoticeid(int nid) {
        this.nid = nid;
    }
}
