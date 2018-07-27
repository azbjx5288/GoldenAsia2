package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Gan on 2017/11/17.
 * 4.5.1	收件箱 消息详情
 */
@RequestConfig(api = "?c=user&a=viewMsg", method = Request.Method.GET)
public class ReceiveBoxDetailCommand {

    private  int msg_id	;//消息id，对应收发件箱列表的msg_id
    private String msgType;//类型，receive:收件箱，send:发件箱

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
