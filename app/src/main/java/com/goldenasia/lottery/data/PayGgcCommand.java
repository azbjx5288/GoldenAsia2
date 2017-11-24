package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 刮刮彩提交订单
 * Created by Sakura on 2016/10/6.
 */
@RequestConfig(api = "?c=game&a=ggc&op=buy", response = String.class)
public class PayGgcCommand
{
    private String sc_ids;
    private String package_id;
    private String scrape_type;

    public String getPackage_id()
    {
        return package_id;
    }

    public void setPackage_id(String package_id)
    {
        this.package_id = package_id;
    }

    public String getSc_ids()
    {
        return sc_ids;
    }

    public void setSc_ids(String sc_ids)
    {
        this.sc_ids = sc_ids;
    }

    public String getScrape_type()
    {
        return scrape_type;
    }

    public void setScrape_type(String scrape_type)
    {
        this.scrape_type = scrape_type;
    }
}
