package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

import java.util.Date;

/**
 * Created by Sakura on 2018/4/5.
 */
@RequestConfig(api = "?c=user&a=childPackageList", method = Request.Method.GET, response = MemberOrderResponse.class)
public class MemberOrderCommand
{
    private int lottery_id;//	1	彩票ID
    private String username;//	zdavy	用户名
    private String wrap_id;//	YF012608110000211T	订单号
    private int include_children;//	0或1	指定会员或指定会员及下级
    private String issue="";//	1	奖期编号
    private int status = -1;//	1	状态，默认-1
    private int check_prize_status = -1;//	1	是否中奖，1中奖，2未中奖，默认-1
    private int is_trace = 0;//	1	是否追号，0非追号，-255追号
    private Date start_time;//	2016-10-20	起始日期
    private Date end_time;//	2017-10-20	截止日期
    private int curPage;//	1	当前页码，每页50条
    
    public int getLottery_id()
    {
        return lottery_id;
    }
    
    public void setLottery_id(int lottery_id)
    {
        this.lottery_id = lottery_id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getWrap_id()
    {
        return wrap_id;
    }
    
    public void setWrap_id(String wrap_id)
    {
        this.wrap_id = wrap_id;
    }
    
    public int getInclude_children()
    {
        return include_children;
    }
    
    public void setInclude_children(int include_children)
    {
        this.include_children = include_children;
    }
    
    public String getIssue()
    {
        return issue;
    }
    
    public void setIssue(String issue)
    {
        this.issue = issue;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public int getCheck_prize_status()
    {
        return check_prize_status;
    }
    
    public void setCheck_prize_status(int check_prize_status)
    {
        this.check_prize_status = check_prize_status;
    }
    
    public int getIs_trace()
    {
        return is_trace;
    }
    
    public void setIs_trace(int is_trace)
    {
        this.is_trace = is_trace;
    }
    
    public void setStart_time(Date start_time)
    {
        this.start_time = start_time;
    }
    
    public void setEnd_time(Date end_time)
    {
        this.end_time = end_time;
    }
    
    public int getCurPage()
    {
        return curPage;
    }
    
    public void setCurPage(int curPage)
    {
        this.curPage = curPage;
    }
}
