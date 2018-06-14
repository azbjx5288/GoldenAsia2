package com.goldenasia.lottery.game;

import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gan on 2018/5/7.
 * 处理11选5冷热和遗漏数据的工具类
 */

public class SyxwCommonGameUtils {
    private String[] pickNumbers= new String[]{"01","02","03","04","05","06","07","08","09","10","11"};

    //获得遗漏的集合
    public List<String> getYiLouList(String gameMethod, int digit) {
        switch (gameMethod) {
            case "SDQSDWD"://前三定位胆 SDQSDWD
            case "SDLX2"://乐选二: SDLX2
            case "SDLX3"://乐选三 SDLX3
            case "SDQSZX"://前三直选 SDQSZX
            case "SDQEZX": //前二直选 SDQEZX
                return yiLouSXDW( digit);
        }
        return yiLouSXDW( digit);
    }

    private List<String> yiLouSXDW( int digit) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < pickNumbers.length; i++) {
            list.add(getYiLouNumber(pickNumbers[i], digit));
        }
        return list;
    }

    //number号码  0到9这种的
    //digit位数 十位百位 这种的
    private String getYiLouNumber( String number, int digit) {
        for (int i = 0; i < ConstantInformation.HISTORY_CODE_LIST.size(); i++) {
            if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(i).substring(digit*3,digit*3+2))) {//01 02 03 04 05
                return String.valueOf(i);
            }
        }
        return ConstantInformation.HISTORY_CODE_LIST.size()+"";
    }


    //============================冷热start============================================================
    public List<String> getLengReList(String gameMethod,  int digit) {
        switch (gameMethod) {
            case "SDQSDWD"://前三定位胆 SDQSDWD
            case "SDLX2"://乐选二: SDLX2
            case "SDLX3"://乐选三 SDLX3
            case "SDQSZX"://前三直选 SDQSZX
            case "SDQEZX": //前二直选 SDQEZX
                return lengReSXDW( digit);
        }
        return lengReSXDW( digit);
    }

    private List<String> lengReSXDW( int digit) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <  pickNumbers.length; i++) {
            list.add(getlengReNumber(  pickNumbers[i], digit));
        }
        return list;
    }

    //number号码  0到9这种的
    //digit位数 十位百位 这种的
    private String getlengReNumber( String number, int digit) {
        int  count=0;
        int minNumber= ConstantInformation.LENG_RE_COUNT >ConstantInformation.HISTORY_CODE_LIST.size()?ConstantInformation.HISTORY_CODE_LIST.size(): ConstantInformation.LENG_RE_COUNT;
        for (int i = 0; i < minNumber; i++) {
            if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(i).substring(digit*3,digit*3+2))) {//55209
                count++;
            }
        }
        return String.valueOf(count);
    }
    //============================冷热end============================================================
}
