package com.goldenasia.lottery.game;

import com.goldenasia.lottery.util.NumbericUtils;

import java.util.ArrayList;

/**
 * Created by Gan on 2018/1/5.
 * 北京PK10数字玩法需要的工具类
 */

public class Pk10CommonGameUtils {


    //后二名直选 每一注 不能有 相同的 例如：1，1
    public  ArrayList<String> HEMZX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(NumbericUtils.hasEqualsArr(singleCodeArr)){
                codeArray.clear();
                return  codeArray;
            }else if(singleCodeArr.length!=2){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append(",");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //后三名直选 每一注 不能有 相同的 例如：1，2，3
    public  ArrayList<String> HSMZX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(NumbericUtils.hasEqualsArr(singleCodeArr)){
                codeArray.clear();
                return  codeArray;
            }else if(singleCodeArr.length!=3){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append(",");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //后四名直选 每一注 不能有 相同的 例如：1，2，3，4
    public  ArrayList<String> HSIMZX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(NumbericUtils.hasEqualsArr(singleCodeArr)){
                codeArray.clear();
                return  codeArray;
            }else if(singleCodeArr.length!=4){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append(",");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //后五名直选 每一注 不能有 相同的 例如：1，2，3，4，5
    public  ArrayList<String> HWMZX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(NumbericUtils.hasEqualsArr(singleCodeArr)){
                codeArray.clear();
                return  codeArray;
            }else if(singleCodeArr.length!=5){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append(",");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //后二名组选 可以有相同的 但是 每注 必须 2位
    public  ArrayList<String> HEMZUX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(singleCodeArr.length!=2){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append("_");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //后三名组选 HSMZL 可以有相同的 但是 每注 必须 3位
    public  ArrayList<String> HSMZL(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(singleCodeArr.length!=3){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append("_");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //后四名组选 可以有相同的 但是 每注 必须 4位
    public  ArrayList<String> HSIMZUX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(singleCodeArr.length!=4){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append("_");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }
    //后五名组选 可以有相同的 但是 每注 必须 5位
    public  ArrayList<String> HWMZUX(ArrayList<String[]> chooseArray){
        ArrayList<String> codeArray = new ArrayList<>();

        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();
            String[] singleCodeArr=chooseArray.get(i);

            if(singleCodeArr.length!=5){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = singleCodeArr.length; j < length; j++) {
                String  charChoose=singleCodeArr[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append("_");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

    //前三名不定位
    public ArrayList<String> QSMBDW(ArrayList<String[]> chooseArray) {
        ArrayList<String> codeArray = new ArrayList<>();
        for (int i = 0; i < chooseArray.size(); i++) {
            StringBuilder codeBuilder = new StringBuilder();

            String[] chooseLineArray=chooseArray.get(i);
            if(chooseLineArray.length!=1){
                codeArray.clear();
                return  codeArray;
            }

            for (int j = 0, length = chooseArray.get(i).length; j < length; j++) {
                String  charChoose=chooseArray.get(i)[j];

                if(!NumbericUtils.isNumericChar(charChoose)){
                    codeArray.clear();
                    return  codeArray;
                }else{
                    codeBuilder.append(charChoose);
                    if (j != length - 1) {
                        codeBuilder.append(",");
                    }
                }

            }
            codeArray.add(codeBuilder.toString());
        }
        return codeArray;
    }

}
