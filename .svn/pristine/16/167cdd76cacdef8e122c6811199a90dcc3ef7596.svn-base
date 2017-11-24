package com.goldenasia.lottery.db;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Gan on 2017/9/28.
 */
@DatabaseTable()
public class MmcWinHistory {
    @DatabaseField(generatedId = true)
    private int uid;
    @DatabaseField
    private String count;// 开奖次数
    @DatabaseField
    private String number;//开奖号码
    @DatabaseField
    private String betMoney;//投注
    @DatabaseField
    private String winMoney;//奖金


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(String betMoney) {
        this.betMoney = betMoney;
    }

    public String getWinMoney() {
        return winMoney;
    }

    public void setWinMoney(String winMoney) {
        this.winMoney = winMoney;
    }

    public MmcWinHistory() {
    }

    public MmcWinHistory(int uid, String count, String number, String betMoney, String winMoney) {
        this.uid = uid;
        this.count = count;
        this.number = number;
        this.betMoney = betMoney;
        this.winMoney = winMoney;
    }
}
