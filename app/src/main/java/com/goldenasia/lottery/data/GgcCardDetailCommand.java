package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Sakura on 2016/10/10.
 */
@RequestConfig(api = "?c=game&a=ggc&op=detail")
public class GgcCardDetailCommand
{
    private String sc_id;

    public String getSc_id()
    {
        return sc_id;
    }

    public void setSc_id(String sc_id)
    {
        this.sc_id = sc_id;
    }
}
