package com.goldenasia.lottery.game;

import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gan on 2018/4/12.
 */

public class SscCommonGameUtils {

    //获得遗漏的集合
    public List<String> getYiLouList(String gameMethod, int digit) {
        switch (gameMethod) {
            case "SXDW":////定位胆 SXDW
                return yiLouSXDW( digit);
        }
        return yiLouSXDW( digit);
    }

    private List<String> yiLouSXDW( int digit) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            list.add(getYiLouNumber(String.valueOf(i), digit));
        }
        return list;
    }

    //number号码  0到9这种的
    //digit位数 十位百位 这种的
    private String getYiLouNumber( String number, int digit) {
        for (int i = 0; i < ConstantInformation.HISTORY_CODE_LIST.size(); i++) {
            if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(i).substring(digit,digit+1))) {//55209
                return String.valueOf(i);
            }
        }
        return "";
    }


    //========================================================================================
    public List<String> getLengReList(String gameMethod,  int digit) {
        switch (gameMethod) {
            case "SXDW":////定位胆 SXDW
                return lengReSXDW( digit);
        }
        return lengReSXDW( digit);
    }

    private List<String> lengReSXDW( int digit) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            list.add(getlengReNumber( String.valueOf(i), digit));
        }
        return list;
    }

    //number号码  0到9这种的
    //digit位数 十位百位 这种的
    private String getlengReNumber( String number, int digit) {
        int  count=0;
        int minNumber= ConstantInformation.LENG_RE_COUNT >ConstantInformation.HISTORY_CODE_LIST.size()?ConstantInformation.HISTORY_CODE_LIST.size(): ConstantInformation.LENG_RE_COUNT;
        for (int i = 0; i < minNumber; i++) {
            if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(i).substring(digit,digit+1))) {//55209
                count++;
            }
        }
        return String.valueOf(count);
    }

}
