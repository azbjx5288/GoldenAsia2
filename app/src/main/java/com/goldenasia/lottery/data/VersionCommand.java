package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 获取最新版本号
 * Created by Alashi on 2016/3/1.
 */
@RequestConfig(api = "?a=getLastVersion", method = Request.Method.GET, response = Version.class)
public class VersionCommand {
}
