package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Sakura on 2017/5/3.
 */

public class AlipayTransferBean implements Serializable
{
    @SerializedName("bankId")
    private int bankID;
    @SerializedName("dt_id")
    private int dtID;
    @SerializedName("player_card_name")
    private String trueName;
    @SerializedName("deposit_amount")
    private float amount;
    
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
