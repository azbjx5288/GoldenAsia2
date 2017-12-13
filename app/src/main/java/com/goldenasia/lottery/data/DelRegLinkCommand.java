package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Sakura on 2017/12/8.
 */

@RequestConfig(api = "?c=user&a=deleteRegLink")
public class DelRegLinkCommand
{
    private String su_id;
    
    public String getSu_id()
    {
        return su_id;
    }
    
    public void setSu_id(String su_id)
    {
        this.su_id = su_id;
    }
}
