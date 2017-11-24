package com.goldenasia.lottery.data;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.user.UserCentre;

/**
 * Created by Alashi on 2016/1/6.
 */
public class LoginRequest extends RestRequest {
    public LoginRequest(Context context) {
        super(context);
    }

    @Override
    protected void onBackgroundResult(NetworkResponse networkResponse, RestResponse response) {
        UserCentre userCentre = GoldenAsiaApp.getUserCentre();
        if (response.getErrNo() != 0) {
            userCentre.saveSession(null);
            return;
        }
        userCentre.saveLoginInfo(((LoginCommand)command).getUsername(),((LoginCommand)command).getEncpassword());
        userCentre.setUserInfo((UserInfo) response.getData());

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
