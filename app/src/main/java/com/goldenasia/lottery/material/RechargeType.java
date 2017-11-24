package com.goldenasia.lottery.material;

/**
 * Created by ACE-PC on 2016/11/21.
 */

public enum RechargeType {
    SCANCODE,   //扫描二维码
    APIPORT,    //API接口
    SDKPORT,     //SDK集成
    PHONETRANSFER,  // '4' => '手机网转',
    WECHATSCANCODE, //'8' => '微信扫码'
    ALIPAYSCANCODE  //'9' => '支付宝扫码',
}
