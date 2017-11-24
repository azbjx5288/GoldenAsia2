package com.goldenasia.lottery.data;

/**
 * Created by Sakura on 2016/10/10.
 */

public class GgcMoneyNumberEntity
{
    private String money;
    private int number;
    private boolean isRed;

    public String getMoney()
    {
        return money;
    }

    public void setMoney(String money)
    {
        this.money = money;
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public boolean isRed()
    {
        return isRed;
    }

    public void setRed(boolean red)
    {
        isRed = red;
    }
}
