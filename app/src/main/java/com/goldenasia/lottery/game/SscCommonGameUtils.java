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
            case "QSZX"://前三直选 QSZX
            case "QSLX"://前三连选 QSLX
                return yiLouSXDW( digit);
            case "QSZS": //前三组三 QSZS
            case "QSZL"://前三组六 QSZL
            case "QSZUXBD"://前三包胆 QSZUXBD
                return yiLouEXZUX( 0,1,2);
            case "ZSZX"://中三直选 ZSZX
            case "ZSLX"://中三连选 ZSLX
                return yiLouSXDW( digit+1);
            case "ZSZS": //中三组三 ZSZS
            case "ZSZL"://中三组六 ZSZL
            case "ZSZUXBD"://中三包胆 ZSZUXBD
                return yiLouEXZUX( 1,2,3);
            case "SIXZX"://后四直选 SIXZX
                return yiLouSXDW( digit+1);
            case "QSIZX": //前四直选 QSIZX
                return yiLouSXDW( digit);
            case "WXZX": //五星直选 WXZX
            case "WXLX": //五星连选 WXLX
                return yiLouSXDW( digit);
            case "YMBDW"://后三一码不定位 YMBDW
            case "EMBDW"://后三二码不定位 EMBDW
                return yiLouEXZUX( 2,3,4);
            case "QSYMBDW"://前三一码不定位 QSYMBDW
            case "QSEMBDW"://前三二码不定位 QSEMBDW
                return yiLouEXZUX( 0,1,2);
            case "ZSYMBDW"://中三一码不定位 ZSYMBDW
            case "ZSEMBDW"://中三二码不定位 ZSEMBDW
                return yiLouEXZUX( 1,2,3);
            case "SXYMBDW"://四星一码不定位 SXYMBDW
            case "SXEMBDW"://四星二码不定位 SXEMBDW
                return yiLouEXZUX( 1,2,3,4);
            case "WXYMBDW"://五星一码不定位 WXYMBDW
            case "WXEMBDW"://五星二码不定位 WXEMBDW
            case "WXSMBDW"://五星三码不定位 WXSMBDW
                return yiLouEXZUX( 0,1,2,3,4);
            case "EXDXDS": //后二大小单双 EXDXDS
                return yiLouSXDW( digit+3);
            case "SXDXDS": //后三大小单双 SXDXDS
                return yiLouSXDW( digit+2);
            case "QEDXDS": //前二大小单双 QEDXDS
            case "QSDXDS": //前三大小单双 QSDXDS
                 return yiLouSXDW( digit);
            case "ZSDXDS": //中三大小单双 ZSDXDS
                return yiLouSXDW( digit+1);
            case "REZX"://任二直选 REZX
            case "RSZX"://任三直选 RSZX
            case "RSIZX"://任四直选 RSIZX
                return yiLouSXDW( digit);
            case "YFFS"://一帆风顺 YFFS
                return yiLouEXZUX( 0,1,2,3,4);


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


    //============================冷热start============================================================
    public List<String> getLengReList(String gameMethod,  int digit) {
        switch (gameMethod) {
            case "SXDW":////定位胆 SXDW
                return lengReSXDW( digit);
            case "EXZX"://后二直选: EXZX
                return lengReSXDW( digit+3);
            case "EXLX"://后二连选 EXLX
                return lengReSXDW( digit+3);
            case "EXZUX"://后二组选 EXZUX
                return lengReEXZUX( 3,4);
            case "EXZUXBD"://后二包胆 EXZUXBD
                return lengReEXZUX( 3,4);
            case "QEZX"://前二直选 QEZX
            case "QELX"://前二连选 QELX
                return lengReSXDW( digit);
            case "QEZUX": //前二组选 QEZUX
            case "QEZUXBD"://前二包胆 QEZUXBD
                return lengReEXZUX( 0,1);
            case "SXZX"://后三直选 SXZX
            case "SXLX"://后三连选 SXLX
                return lengReSXDW( digit+2);
            case "SXZS": //后三组三 SXZS
            case "SXZL"://后三组六 SXZL
            case "HSZUXBD"://后三包胆 HSZUXBD
                return lengReEXZUX( 2,3,4);
            case "QSZX"://前三直选 QSZX
            case "QSLX"://前三连选 QSLX
                return lengReSXDW( digit);
            case "QSZS": //前三组三 QSZS
            case "QSZL"://前三组六 QSZL
            case "QSZUXBD"://前三包胆 QSZUXBD
                return lengReEXZUX( 0,1,2);
            case "ZSZX"://中三直选 ZSZX
            case "ZSLX"://中三连选 ZSLX
                return lengReSXDW( digit+1);
            case "ZSZS": //中三组三 ZSZS
            case "ZSZL"://中三组六 ZSZL
            case "ZSZUXBD"://中三包胆 ZSZUXBD
                return lengReEXZUX( 1,2,3);
            case "SIXZX"://后四直选 SIXZX
                return lengReSXDW( digit+1);
            case "QSIZX": //前四直选 QSIZX
                return lengReSXDW( digit);
            case "WXZX": //五星直选 WXZX
            case "WXLX": //五星连选 WXLX
                return lengReSXDW( digit);
            case "YMBDW"://后三一码不定位 YMBDW
            case "EMBDW"://后三二码不定位 EMBDW
                return lengReEXZUX( 2,3,4);
            case "QSYMBDW"://前三一码不定位 QSYMBDW
            case "QSEMBDW"://前三二码不定位 QSEMBDW
                return lengReEXZUX( 0,1,2);
            case "ZSYMBDW"://中三一码不定位 ZSYMBDW
            case "ZSEMBDW"://中三二码不定位 ZSEMBDW
                return lengReEXZUX( 1,2,3);
            case "SXYMBDW"://四星一码不定位 SXYMBDW
            case "SXEMBDW"://四星二码不定位 SXEMBDW
                return lengReEXZUX( 1,2,3,4);
            case "WXYMBDW"://五星一码不定位 WXYMBDW
            case "WXEMBDW"://五星二码不定位 WXEMBDW
            case "WXSMBDW"://五星三码不定位 WXSMBDW
                return lengReEXZUX( 0,1,2,3,4);
            case "EXDXDS": //后二大小单双 EXDXDS
                return lengReSXDW( digit+3);
            case "SXDXDS": //后三大小单双 SXDXDS
                return lengReSXDW( digit+2);
            case "QEDXDS": //前二大小单双 QEDXDS
            case "QSDXDS": //前三大小单双 QSDXDS
                return lengReSXDW( digit);
            case "ZSDXDS": //中三大小单双 ZSDXDS
                return lengReSXDW( digit+1);
            case "REZX"://任二直选 REZX
            case "RSZX"://任三直选 RSZX
            case "RSIZX"://任四直选 RSIZX
                return lengReSXDW( digit);
            case "YFFS"://一帆风顺 YFFS
                return lengReEXZUX( 0,1,2,3,4);


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

    //后二组选 EXZUX
    private List<String> lengReEXZUX(int... digits) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i <= 9; i++) {
            list.add(getSingleLengReEXZUXDate(String.valueOf(i), digits));
        }
        return list;
    }

    private String getSingleLengReEXZUXDate(String number,int... digits){
        int  count=0;
        int minNumber= ConstantInformation.LENG_RE_COUNT >ConstantInformation.HISTORY_CODE_LIST.size()?ConstantInformation.HISTORY_CODE_LIST.size(): ConstantInformation.LENG_RE_COUNT;
        for (int j = 0;j < minNumber; j++) {
            for(int z=0;z<digits.length;z++){
                if (number.equals(ConstantInformation.HISTORY_CODE_LIST.get(j).substring(digits[z],digits[z]+1))) {//55209
                    count++;
                }
            }
        }
        return String.valueOf(count);
    }

    //============================冷热end============================================================
}
