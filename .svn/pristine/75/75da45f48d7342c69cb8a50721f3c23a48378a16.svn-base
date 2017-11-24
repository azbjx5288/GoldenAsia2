package com.goldenasia.lottery.data;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.user.UserCentre;

/**
 * 重置密码step1  session的保存
 * Created by Gan on 2017/10/5.
 */
public class ForgetPwdRequest extends RestRequest {
    public ForgetPwdRequest(Context context) {
        super(context);
    }

    @Override
    protected void onBackgroundResult(NetworkResponse networkResponse, RestResponse response) {
        UserCentre userCentre = GoldenAsiaApp.getUserCentre();
        if (response.getErrNo() != 0) {
            userCentre.saveSession(null);
            return;
        }

        String cookie = networkResponse.headers.get("Set-Cookie");
        if (cookie == null) {
            return;
        }
        String[] cookies = cookie.split(";");
        for (String s: cookies) {
            if (s.startsWith("sscSESSID=")) {
                userCentre.saveSession(s);
                break;
            }
        }
    }
}
