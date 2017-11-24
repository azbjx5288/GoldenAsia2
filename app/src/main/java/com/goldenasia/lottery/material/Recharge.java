package com.goldenasia.lottery.material;

import android.app.Activity;

/**
 * Created by ACE-PC on 2016/12/5.
 */

public class Recharge {

    public static RechargeResult createRecharge(Activity activity, double money, String params, RechargeType type) {
        return new RechargeResult(activity,money, params, type);
    }

    public static RechargeResult createRecharge(Activity activity, double money, String params, RechargeType type,RechargeResult.OnStatusListener onStatusListener) {
        return new RechargeResult(activity,money, params, type,onStatusListener);
    }
}
