package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Sakura on 2018/5/22.
 */

@RequestConfig(api = "?c=user&a=editChildRebate", response = String.class)
public class EditChildRebateCommand
{
    private String op;
    private int user_id;
    private String normal_rebate;
    private String lhc_rebate;
    private String jc_rebate;
    
    public String getOp()
    {
        return op;
    }
    
    public void setOp(String op)
    {
        this.op = op;
    }
    
    public int getUser_id()
    {
        return user_id;
    }
    
    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }
    
    public String getNormal_rebate()
    {
        return normal_rebate;
    }
    
    public void setNormal_rebate(String normal_rebate)
    {
        this.normal_rebate = normal_rebate;
    }
    
    public String getLhc_rebate()
    {
        return lhc_rebate;
    }
    
    public void setLhc_rebate(String lhc_rebate)
    {
        this.lhc_rebate = lhc_rebate;
    }
    
    public String getJc_rebate()
    {
        return jc_rebate;
    }
    
    public void setJc_rebate(String jc_rebate)
    {
        this.jc_rebate = jc_rebate;
    }
}
