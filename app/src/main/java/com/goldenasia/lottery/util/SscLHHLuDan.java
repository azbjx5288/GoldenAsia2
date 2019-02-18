package com.goldenasia.lottery.util;

import android.util.Log;

import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Gan on 2019/1/22.
 * 时时彩龙虎和路单
 */

public class SscLHHLuDan {

    public static List<List<String>> getLongHuHeList(int char01, int char02) {
        /*List<String> list = ConstantInformation.HISTORY_CODE_LIST;
        List<List<String>> dataList = new ArrayList<>();
        List<String> arrList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String singleString = getLHH(Integer.parseInt(list.get(i).charAt(char01) + ""), Integer.parseInt(list.get(i).charAt(char02) + ""));
            if (arrList.size() > 0) {
                if (arrList.contains(singleString)) {
                    arrList.add(singleString);
                } else {
                    dataList.add(arrList);
                    arrList = new ArrayList<>();
                    arrList.add(singleString);
                }
            } else {
                arrList = new ArrayList<>();
                arrList.add(singleString);
            }
        }*/
        Map<String,String> map = sortMapByKey(ConstantInformation.HISTORY_CODE_MAP);
        List<List<String>> dataList = new ArrayList<>();
        List<String> arrList = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String singleString = getLHH(Integer.parseInt(entry.getValue().charAt(char01) + ""), Integer.parseInt(entry.getValue().charAt(char02) + ""));
            if (arrList.size() > 0) {
                if (arrList.contains(singleString)) {
                    arrList.add(singleString);
                } else {
                    dataList.add(arrList);
                    arrList = new ArrayList<>();
                    arrList.add(singleString);
                }
            } else {
                arrList = new ArrayList<>();
                arrList.add(singleString);
            }
        }
        return dataList;
    }

    private static String getLHH(int higher, int lower) {
        if (higher > lower)
            return "龙";
        else if (higher < lower)
            return "虎";
        else
            return "和";
    }

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap =((TreeMap) map).descendingMap();
        return sortMap;
    }
}
