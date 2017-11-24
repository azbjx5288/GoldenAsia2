package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 修改密码（登录密码或资金密码）
 * Created by Alashi on 2016/5/2.
 */
@RequestConfig(api = "?c=user&a=changePassword")
public class ChangePasswordCommand {
    private String sa;//modifyPassword或modifySecurityPassword	修改登录密码\修改资金密码

    private String oldpassword;//当sa=modifyPassword时
    private String password;//当sa=modifyPassword时
    private String password2;//当sa=modifyPassword时

    private String oldsecpassword;//当sa=modifySecurityPassword时
    private String secpassword;//当sa=modifySecurityPassword时
    private String secpassword2;//当sa=modifySecurityPassword时

    public void setSa(String sa) {
        this.sa = sa;
    }

    public void setOldpassword(String oldpassword) {
        this.oldpassword = oldpassword;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public void setOldsecpassword(String oldsecpassword) {
        this.oldsecpassword = oldsecpassword;
    }

    public void setSecpassword(String secpassword) {
        this.secpassword = secpassword;
    }

    public void setSecpassword2(String secpassword2) {
        this.secpassword2 = secpassword2;
    }
}
