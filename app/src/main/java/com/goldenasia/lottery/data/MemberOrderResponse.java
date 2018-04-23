package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Sakura on 2018/4/6.
 */

public class MemberOrderResponse
{
    /**
     * wrap_id :
     * issue :
     * is_trace : -1
     * check_prize_status : -1
     * include_children : 0
     * username : sakura
     * start_time : 2018-01-02 00:00:00
     * end_time : 2018-01-03 23:59:59
     * package : [{"package_id":"3","user_id":"56966","top_id":"57103","lottery_id":"1","issue":"20160726-051",
     * "trace_id":"0","single_num":"7","multiple":"1","cur_rebate":"0.126","modes":"1","amount":"14.000",
     * "prize":"0.0000","create_time":"2018-01-02 14:21:01","check_prize_status":"0","send_prize_status":"0",
     * "send_prize_time":"0000-00-00 00:00:00","cancel_status":"0","cancel_admin_id":"0","cancel_time":"0000-00-00
     * 00:00:00","frm":"1","user_ip":"203.82.46.58","proxy_ip":"203.82.46.58","server_ip":"ssc.la","ts":"2018-01-02
     * 15:03:17","wrap_id":"CQ60726051000000003P","lottery_name":"重庆时时彩","cancel_status_name":"未开奖","prize_value":"--"}]
     * pageList : [1/1] 总计1条记录 1 </span>上一页 下一页 首页 尾页</div>
     */
    
    private String wrap_id;
    private String issue;
    private int is_trace;
    private int check_prize_status;
    private int include_children;
    private String username;
    private String start_time;
    private String end_time;
    private String pageList;
    @SerializedName("package")
    private List<PackageBean> packageBeans;
    private String packageList;
    
    public String getWrap_id() { return wrap_id;}
    
    public void setWrap_id(String wrap_id) { this.wrap_id = wrap_id;}
    
    public String getIssue() { return issue;}
    
    public void setIssue(String issue) { this.issue = issue;}
    
    public int getIs_trace() { return is_trace;}
    
    public void setIs_trace(int is_trace) { this.is_trace = is_trace;}
    
    public int getCheck_prize_status() { return check_prize_status;}
    
    public void setCheck_prize_status(int check_prize_status) { this.check_prize_status = check_prize_status;}
    
    public int getInclude_children() { return include_children;}
    
    public void setInclude_children(int include_children) { this.include_children = include_children;}
    
    public String getUsername() { return username;}
    
    public void setUsername(String username) { this.username = username;}
    
    public String getStart_time() { return start_time;}
    
    public void setStart_time(String start_time) { this.start_time = start_time;}
    
    public String getEnd_time() { return end_time;}
    
    public void setEnd_time(String end_time) { this.end_time = end_time;}
    
    public String getPageList() { return pageList;}
    
    public void setPageList(String pageList) { this.pageList = pageList;}
    
    public List<PackageBean> getPackageBeans() { return packageBeans;}
    
    public void setPackageBeans(List<PackageBean> packageBeans) { this.packageBeans = packageBeans;}
}
