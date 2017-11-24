package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Sakura on 2016/10/10.
 */
@RequestConfig(api = "?c=game&a=ggc&op=mycardsList")
public class GgcMyCardListCommand
{
    private int page;
    private int status;
    private int scrape_type = -1;
    private String start_time;
    private String end_time;

    public String getEnd_time()
    {
        return end_time;
    }

    public void setEnd_time(String end_time)
    {
        this.end_time = end_time;
    }

    public int getPage()
    {
        return page;
    }

    public void setPage(int page)
    {
        this.page = page;
    }

    public int getScrape_type()
    {
        return scrape_type;
    }

    public void setScrape_type(int scrape_type)
    {
        this.scrape_type = scrape_type;
    }

    public String getStart_time()
    {
        return start_time;
    }

    public void setStart_time(String start_time)
    {
        this.start_time = start_time;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}
