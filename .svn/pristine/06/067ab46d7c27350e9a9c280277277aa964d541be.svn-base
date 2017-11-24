package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Gan on 2017/10/2.
 * 下级转账
 */
@RequestConfig(api = "?c=user&a= transferMoney")
public class TransferLowerMemberCommand {
    /**
     * 对方用户ID
     */
    private  int user_id ;  // 转给下一级
    /**
     * 对方真实姓名
     */
    private String real_name ;   // 开户人姓名
    /**
     * 转入金额
     */
    private double amount ; //转账金额
    /**
     *  资金密码 明文
     */
    private String secpassword ; // 资金密码

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getSecpassword() {
        return secpassword;
    }

    public void setSecpassword(String secpassword) {
        this.secpassword = secpassword;
    }
}
