package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sakura on 2017/5/3.
 */

@RequestConfig(api = "?c=fin&a=showAccount", response = String.class)
public class ShowAccountCommand
{
    @SerializedName("bankId")
    private int bankID;
    @SerializedName("dt_id")
    private int dtID;
    @SerializedName("player_card_name")
    private String trueName;
    @SerializedName("deposit_amount")
    private float amount;
    private int frm = 4;
    
    public int getBankID()
    {
        return bankID;
    }
    
    public void setBankID(int bankID)
    {
        this.bankID = bankID;
    }
    
    public int getDtID()
    {
        return dtID;
    }
    
    public void setDtID(int dtID)
    {
        this.dtID = dtID;
    }
    
    public String getTrueName()
    {
        return trueName;
    }
    
    public void setTrueName(String trueName)
    {
        this.trueName = trueName;
    }
    
    public float getAmount()
    {
        return amount;
    }
    
    public void setAmount(float amount)
    {
        this.amount = amount;
    }
}
