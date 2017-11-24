package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2017/1/20.
 */
@RequestConfig(api = "?c=fin&a=tranMoney&op=show", method = Request.Method.GET)
public class TransferRouteCommand {
}
