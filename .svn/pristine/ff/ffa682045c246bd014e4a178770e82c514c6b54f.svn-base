package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2017/1/20.
 */
@RequestConfig(api = "?c=fin&a=tranMoney&op=tran")
public class TransferFundsCommand {
    private int tranFrom ;  // 转出方 0=JYZ；1=PT；2=JC；3=GA
    private int tranTo ;   // 转入方 0=JYZ；1=PT；2=JC；3=GA
    private double tranAmount ; //转账金额
    private String tranPass ; // 'zijinmima' 资金密码

    public int getTranFrom() {
        return tranFrom;
    }

    public void setTranFrom(int tranFrom) {
        this.tranFrom = tranFrom;
    }

    public int getTranTo() {
        return tranTo;
    }

    public void setTranTo(int tranTo) {
        this.tranTo = tranTo;
    }

    public double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public String getTranPass() {
        return tranPass;
    }

    public void setTranPass(String tranPass) {
        this.tranPass = tranPass;
    }
}
