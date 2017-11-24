package com.goldenasia.lottery.data;

/**
 * Created by Sakura on 2017/5/5.
 */

public class DepositBean
{
    private String player_card_name;
    private float amount;
    
    public String getPlayer_card_name()
    {
        return player_card_name;
    }
    
    public void setPlayer_card_name(String player_card_name)
    {
        this.player_card_name = player_card_name;
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
