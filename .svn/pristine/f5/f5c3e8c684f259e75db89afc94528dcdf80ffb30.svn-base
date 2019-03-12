package com.goldenasia.lottery.util;

import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gan on 2019/1/22.
 * 时时彩龙虎和路单
 */

public class SscLHHLuDan {

    public static  List<List<String>> getLongHuHeList(int char01, int char02 ) {
        List<String> list=  ConstantInformation.HISTORY_CODE_LIST;
        List< List<String>> dataList=new ArrayList<>() ;
        List<String> arrList=new ArrayList<String>();
        for(int i=0;i<list.size();i++){
            String single=list.get(i);
            String singleString=getLHH(Integer.parseInt(single.charAt(char01)+""),Integer.parseInt(single.charAt(char02)+""));
            if(arrList.size()>0) {
                if (arrList.contains(singleString)) {
                    arrList.add(singleString);
                } else {
                    dataList.add(arrList);
                    if(dataList.size()>=20){
                        break;
                    }
                    arrList=new ArrayList<String>();
                    arrList.add(singleString);
                }
            }else{
                arrList=new ArrayList<String>();
                arrList.add(singleString);
            }
        }

        return dataList;
    }

    private  static  String getLHH(int higher, int lower)
    {
        if (higher > lower)
            return "龙";
        else if (higher < lower)
            return "虎";
        else
            return "和";
    }
}
