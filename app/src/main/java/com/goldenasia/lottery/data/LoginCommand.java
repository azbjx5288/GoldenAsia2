package com.goldenasia.lottery.data;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 手机客户端登录验证
 * Created by Alashi on 2016/1/5.
 */
@RequestConfig(api = "?c=user&a=login", response = UserInfo.class,request = LoginRequest.class)
public class LoginCommand {
    /**
     * String(10)	不可为空	用户名 （绑定手机号登录功能二期实现）
     */
    private String username;
    /**
     * String(32)	不可为空	用户密码，经过md5加密，长度为32的字符串。
     */
    private String encpassword;
    /**
     * 字符串	不可为空	随机产生的长度为6的字符串
     */
    private String str;
    private String verify = "login";
    private int devicecode = 4;
    private int version;

    public LoginCommand() {
        str = String.format("BUILD_TYPE[%s],VERSION_CODE[%s],VERSION_NAME[%s]", BuildConfig.BUILD_TYPE, BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncpassword() {
        return encpassword;
    }

    public void setEncpassword(String encpassword) {
        this.encpassword = encpassword;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
