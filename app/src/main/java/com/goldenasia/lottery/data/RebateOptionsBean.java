package com.goldenasia.lottery.data;

import java.io.Serializable;

/**
 * Created by Sakura on 2017/12/8.
 */

public class RebateOptionsBean implements Serializable
{
    /**
     * property_name :
     * rebate :
     */
    
    private String property_name;
    private String method_name;
    private String rebate;
    
    public String getProperty_name() { return property_name;}
    
    public void setProperty_name(String property_name) { this.property_name = property_name;}
    
    public String getMethod_name()
    {
        return method_name;
    }
    
    public void setMethod_name(String method_name)
    {
        this.method_name = method_name;
    }
    
    public String getRebate() { return rebate;}
    
    public void setRebate(String rebate) { this.rebate = rebate;}
}
