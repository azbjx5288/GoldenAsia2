package com.goldenasia.lottery.data;

/**
 * Created by Sakura on 2016/9/30.
 * 刮刮彩玩法（卡包类型）返回信息
 */

public class ScrapeType
{
    /**
     * 1 : {"st_id":1,"type_name":"金亚洲生肖刮刮卡","price":10}
     * 2 : {"st_id":2,"type_name":"金亚洲足球刮刮卡","price":5}
     */
    private int st_id;
    private String type_name;
    private double price;

    public int getSt_id() { return st_id;}

    public void setSt_id(int st_id) { this.st_id = st_id;}

    public String getType_name() { return type_name;}

    public void setType_name(String type_name) { this.type_name = type_name;}

    public double getPrice() { return price;}

    public void setPrice(int price) { this.price = price;}
}
