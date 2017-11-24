package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

/**
 * 申请提现
 * Created by Alashi on 2016/3/17.
 */
@RequestConfig(api = "?c=user&a=applyWithdraw")
public class WithdrawCommand {
    /** 资金密码 */
    @SerializedName("secpassword")
    private String secPassword;

    /** 提款银行ID */
    @SerializedName("withdraw_bank_id")
    private int withdrawBankId;

    /** 提款金额 */
    @SerializedName("withdraw_amount")
    private double withdrawAmount;

    /** 已绑定银行卡ID */
    @SerializedName("bind_card_id")
    private int bindCardId;

    /** 提款银行卡号 */
    @SerializedName("withdraw_card_num")
    private String withdrawCardNumber;

    /** 省 */
    private String province;
    /** 城市 */
    private String city;
    /** 分行名称 */
    @SerializedName("branch_name")
    private String branchName;

    public void setSecurityPassword(String secPassword) {
        this.secPassword = secPassword;
    }

    public void setWithdrawBankId(int withdrawBankId) {
        this.withdrawBankId = withdrawBankId;
    }

    public void setWithdrawAmount(double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public void setBindCardId(int bindCardId) {
        this.bindCardId = bindCardId;
    }

    public void setWithdrawCardNumber(String withdrawCardNumber) {
        this.withdrawCardNumber = withdrawCardNumber;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
