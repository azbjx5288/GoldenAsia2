package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Sakura on 2017/3/9.
 */

public class LotteryMenu
{
    private ArrayList<Lottery> ssc;
    @SerializedName("115")
    private ArrayList<Lottery> syxw;
    private ArrayList<Lottery> ks;
    private ArrayList<Lottery> low;
    private ArrayList<Lottery> qw;
    private ArrayList<Lottery> pk10;
    private ArrayList<Lottery> others;
    
    public ArrayList<Lottery> getSsc()
    {
        return ssc;
    }
    
    public void setSsc(ArrayList<Lottery> ssc)
    {
        this.ssc = ssc;
    }
    
    public ArrayList<Lottery> getSyxw()
    {
        return syxw;
    }
    
    public void setSyxw(ArrayList<Lottery> syxw)
    {
        this.syxw = syxw;
    }
    
    public ArrayList<Lottery> getKs()
    {
        return ks;
    }
    
    public void setKs(ArrayList<Lottery> ks)
    {
        this.ks = ks;
    }
    
    public ArrayList<Lottery> getLow()
    {
        return low;
    }
    
    public void setLow(ArrayList<Lottery> low)
    {
        this.low = low;
    }
    
    public ArrayList<Lottery> getQw()
    {
        return qw;
    }
    
    public void setQw(ArrayList<Lottery> qw)
    {
        this.qw = qw;
    }
    
    public ArrayList<Lottery> getPk10()
    {
        return pk10;
    }
    
    public void setPk10(ArrayList<Lottery> pk10)
    {
        this.pk10 = pk10;
    }
    
    public ArrayList<Lottery> getOthers()
    {
        return others;
    }
    
    public void setOthers(ArrayList<Lottery> others)
    {
        this.others = others;
    }
}
