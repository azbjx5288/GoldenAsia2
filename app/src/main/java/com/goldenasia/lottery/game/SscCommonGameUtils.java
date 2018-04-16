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
            case "SXDW"://定位胆 SXDW
                return yiLouSXDW( digit);
            case "EXZX"://后二直选: EXZX
                return yiLouSXDW( digit+3);
            case "EXLX"://后二连选 EXLX
                return yiLouSXDW( digit+3);
            case "EXZUX"://后二组选 EXZUX
                return yiLouEXZUX( 3,4);
            case "EXZUXBD"://后二包胆 EXZUXBD
                return yiLouEXZUX( 3,4);
            case "QEZX"://前二直选 QEZX
            case "QELX"://前二连选 QELX
                return yiLouSXDW( digit);
            case "QEZUX": //前二组选 QEZUX
            case "QEZUXBD"://前二包胆 QEZUXBD
                return yiLouEXZUX( 0,1);
            case "SXZX"://后三直选 SXZX
            case "SXLX"://后三连选 SXLX
                return yiLouSXDW( digit+2);
            case "SXZS": //后三组三 SXZS
            case "SXZL"://后三组六 SXZL
            case "HSZUXBD"://后三包胆 HSZUXBD
                return yiLouEXZUX( 2,3,4);


        }
        return yiLouSXDW( digit);
    }

    //后二组选 EXZUX
    private List<String> yiLouEXZUX(int... digits) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            list.add(getSingleYiLouEXZUXDate(String.valueOf(i), digits));
        }
        return list;
    }

    private String getSingleYiLouEXZUXDate(String number,int... digits){
        for (int j = 0;j < ConstantInformation.HISTORY_CODE_LIST.size(); j++) {
            for(int z=0;z<digits.length;z++){
                if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(j).substring(digits[z],digits[z]+1))) {//55209
                    return String.valueOf(j);
                }
            }
        }
        return "";
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
