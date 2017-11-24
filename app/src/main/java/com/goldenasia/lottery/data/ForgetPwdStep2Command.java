package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * 4.1.10	用户忘记密码接口
 * Created by Gan on 2017/10/4.
 */
@RequestConfig(api = "?c=user&a=forgetPwd")
public class ForgetPwdStep2Command {
    /**
     * 1(默认值)	验证资金密码是否正确
     * 2	重置新密码
     */
    private String step;

    /**
     * 随机长度为6的字符串
     */
    private String sign="";


    /**
     * 	Step=1时: 用户名
     */
    private String username;
    /**
     * Step=1时: Md5后的资金密码
     */
    private String encsecpwd;
    /**
     * Step=1时:设备[IOS =5|安卓=4]
     */
   private int frm;
    /**
     * Step=2时: 未加密的新登录密码
     */
    private String password;
    /**
     *Step=2时:未加密再次输入的新登录密码
     */
    private String password2;

    /**
     * Step=2时: step1返回的验证参数
     */

    private String nvpair;

    private String sessionid;


    public ForgetPwdStep2Command() {

        for(int i=0;i<6;i++){
            int intVal=(int)(Math.random()*26+97);
            sign=sign+(char)intVal;
        }
    }

    public int getFrm() {
        return frm;
    }

    public void setFrm(int frm) {
        this.frm = frm;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncsecpwd() {
        return encsecpwd;
    }

    public void setEncsecpwd(String encsecpwd) {
        this.encsecpwd = encsecpwd;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getNvpair() {
        return nvpair;
    }

    public void setNvpair(String nvpair) {
        this.nvpair = nvpair;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
}
