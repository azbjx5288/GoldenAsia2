package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

import java.util.Date;

/**
 * 追号订单列表
 * Created by Alashi on 2016/1/21.
 */
@RequestConfig(api = "?c=game&a=traceList", method = Request.Method.GET, response = TraceListResponse.class)
public class TraceListCommand {
    private  int lottery_id;    //全部则不传值，否则传递彩种id
    private  int status=-1;    //状态[-1:全部,0:未开始,1:正在进行,2:已完成,3:已取消]
    private  Date start_time;   //2017-04-01	起始时间
    private  Date end_time; //2017-04-01	结束时间

    private int curPage;
    private int perPage = 20;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setLottery_id(int lottery_id) {
        this.lottery_id = lottery_id;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
