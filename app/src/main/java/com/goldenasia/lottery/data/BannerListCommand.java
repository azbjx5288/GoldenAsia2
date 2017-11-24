package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Banner列表
 * Created by Alashi on 2016/1/19.
 */
@RequestConfig(api = "?c=default&a=bannerList", method = Request.Method.GET)
public class BannerListCommand
{
    private String pageName;
    
    public String getPageName()
    {
        return pageName;
    }
    
    public void setPageName(String pageName)
    {
        this.pageName = pageName;
    }
}
