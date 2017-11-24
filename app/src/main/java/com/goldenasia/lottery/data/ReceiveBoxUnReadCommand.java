package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Gan on 2017/11/17.
 * 4.5.1	收件箱
 */
@RequestConfig(api = "?c=user&a=receiveBox", method = Request.Method.GET)
public class ReceiveBoxUnReadCommand {

    private  int isRead	;//是否已读，0：未读，1：已读
    private  int curPage=1;//当前页码，默认=1
    private  int pageSize=10;//每页记录数，默认=10



    public void setIsRead(int isRead) {
        this.isRead = isRead;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }


}
