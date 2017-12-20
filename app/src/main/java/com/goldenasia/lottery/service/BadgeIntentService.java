package com.goldenasia.lottery.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Build;

import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.ReceiveBoxResponse;
import com.goldenasia.lottery.data.ReceiveBoxUnReadCommand;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import me.leolin.shortcutbadger.ShortcutBadger;

public class BadgeIntentService extends IntentService {
    private UserCentre userCentre;

    public BadgeIntentService() {
        super("BadgeIntentService");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        userCentre = GoldenAsiaApp.getUserCentre();
        if(userCentre.isLogin()&&userCentre.getUserInfo()!=null) {
            loadReceiveBox();
        }
    }

    private void loadReceiveBox() {
        ReceiveBoxUnReadCommand command = new ReceiveBoxUnReadCommand();
        command.setIsRead(0);
        TypeToken typeToken = new TypeToken<RestResponse<ReceiveBoxResponse>>() {
        };

        RestRequestManager.executeCommand(this, command, typeToken, restCallback, 0, this);
    }


    private RestCallback restCallback = new RestCallback<ReceiveBoxResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ReceiveBoxResponse> response) {

                ReceiveBoxResponse receiveBoxResponse = (ReceiveBoxResponse) (response.getData());
                int totalCount =receiveBoxResponse.getList().size();// Integer.parseInt(receiveBoxResponse.getCount());//解决服务端 返回数据 有缓存的 问题
                if(totalCount==10){
                    totalCount=Integer.parseInt(receiveBoxResponse.getCount());
                }

                if(totalCount<=0){
                    totalCount=0;
                }else{
                    totalCount=Math.max(0,Math.min(totalCount,99));
                }

                //消息的 数量
                ConstantInformation.MESSAGE_COUNT=totalCount;
//                SharedPreferencesUtils.putInt(SplashActivity.this, ConstantInformation.APP_INFO, ConstantInformation.MESSAGE_COUNT,totalCount);

                ShortcutBadger.applyCount(GoldenAsiaApp.getInstance(), totalCount);

            if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
                startService(new Intent(BadgeIntentService.this, BadgeIntentForXiaomiService.class).putExtra("badgeCount", totalCount));
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };
}
