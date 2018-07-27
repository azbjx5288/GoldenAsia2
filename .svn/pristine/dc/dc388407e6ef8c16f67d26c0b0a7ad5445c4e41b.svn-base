package com.goldenasia.lottery.util;

import android.text.Html;
import android.text.TextUtils;

import com.goldenasia.lottery.material.Ticket;

import java.util.List;

/**
 * Created by Gan on 2018/6/27.
 * 单挑模式工具类
 */

public class DanTiaoUtils {

    //是否弹出单挑模式 对话框
    public String isShowDialog(int lotteryId,List<Ticket> tickets){

        switch (lotteryId){
            case 1://重庆时时彩
            case 3://黑龙江时时彩
            case 4://新疆时时彩
            case 8://天津时时彩
            case 11://亚洲分分彩
            case 49://腾讯分分差
            case 19://亚洲5分彩
            case 35://台湾五分彩
            case 37://亚洲2分彩
                return  sscIsShowDialog(tickets);
        }
        return null;
    }

    //时时彩是否弹出单挑模式 对话框
    private String sscIsShowDialog(List<Ticket> tickets) {
        /*有单挑的玩法名称*/
        StringBuilder hasDanTiaoMethods=new StringBuilder();

        for(int i=0;i<tickets.size();i++){
            Ticket ticket=tickets.get(i);

            switch (ticket.getChooseMethod().getCname()){
                case "五星直选":
                    if(ticket.getChooseNotes()<1000){
                        hasDanTiaoMethods.append("<font color=\'#8F0000\'>\"五星直选\"</font> ");
                        hasDanTiaoMethods.append("<font color=\'#8F0000\'>\"四星直选\"</font> ");
                    }
                    break;
                default:
                    ;
            }
        }

        if(!TextUtils.isEmpty(hasDanTiaoMethods)){
            return hasDanTiaoMethods.append("投注含单挑注单，单挑注单盈利上限为3万元，是否继续投注？").toString();
        }

        return null;
    }

}
