package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 公告列表
 * Created by Alashi on 2016/1/19.
 */
@RequestConfig(api = "?c=default&a=noticeList", method = Request.Method.GET)
public class NoticeListCommand {
}
