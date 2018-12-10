package com.goldenasia.lottery.util;


import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Gan on 2018/9/18.
 */

public class ResultsUtils {

    //用
    public static String getLastStringFromString01(String code, String split) {
        String[] codeArr = code.split(split);
        String tv1String = codeArr[codeArr.length - 1];
        return tv1String;
    }

    public static int getLastIntFromString(String code, String split) {
        String[] codeArr = code.split(split);
        String tv1String = codeArr[codeArr.length - 1];

        return Integer.parseInt(tv1String);
    }

    public static String[] getStringArrFromString(String code, String split) {
        String[] codeArr = code.split(split);
        return codeArr;
    }

    public static int[] getIntArrFromString(String code, String split, int size) {
        String[] codeArr = code.trim().split(split);
        int[] newArr = new int[size];

        int count = 0;

        for (int i = 0; i < codeArr.length; i++) {
            if (TextUtils.isEmpty(codeArr[i])) {
                continue;
            }
            try {
                newArr[count++] = Integer.parseInt(codeArr[i]);
            } catch (Exception e) {

            }

        }

        return newArr;
    }

    public static int[] getIntArrFromString(String code, String split) {
        String[] codeArr = code.trim().split(split);
        int[] newArr = new int[codeArr.length];

        for (int i = 0; i < codeArr.length; i++) {
            newArr[i] = Integer.parseInt(codeArr[i]);
        }

        return newArr;
    }

    public static ArrayList<Integer> getIntegerArrFromString(String code, String split) {
        String[] codeArr = code.trim().split(split);
        ArrayList<Integer> newArr = new ArrayList<>();

        for (int i = 0; i < codeArr.length; i++) {
            if (TextUtils.isEmpty(codeArr[i])) {
                continue;
            }
            newArr.add(Integer.parseInt(codeArr[i]));
        }

        return newArr;
    }

    public static String getShengXiaoFromCode(int code) {
        if (ConstantInformation.MOUSE_NUM.contains(code)) {
            return "鼠";
        } else if (ConstantInformation.COW_NUM.contains(code)) {
            return "牛";
        } else if (ConstantInformation.TIGER_NUM.contains(code)) {
            return "虎";
        } else if (ConstantInformation.RABBIT_NUM.contains(code)) {
            return "兔";
        } else if (ConstantInformation.DRAGON_NUM.contains(code)) {
            return "龙";
        } else if (ConstantInformation.SNAKE_NUM.contains(code)) {
            return "蛇";
        } else if (ConstantInformation.HORSE_NUM.contains(code)) {
            return "马";
        } else if (ConstantInformation.SHEEP_NUM.contains(code)) {
            return "羊";
        } else if (ConstantInformation.MONKEY_NUM.contains(code)) {
            return "猴";
        } else if (ConstantInformation.CHICKEN_NUM.contains(code)) {
            return "鸡";
        } else if (ConstantInformation.DOG_NUM.contains(code)) {
            return "狗";
        } else if (ConstantInformation.PIG_NUM.contains(code)) {
            return "猪";
        }

        return "";
    }

    public static String getSeBoFromCode(int code) {
        switch (code) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                return "红波";
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                return "蓝波";
            case 5:
            case 6:
            case 11:
            case 16:
            case 17:
            case 21:
            case 22:
            case 27:
            case 28:
            case 32:
            case 33:
            case 38:
            case 39:
            case 43:
            case 44:
            case 49:
                return "绿波";

        }

        return "";
    }

    public static String getWeiShuFromCode(String code) {
        char sec = code.charAt(1);
        return sec + "尾";
    }

    public static int getSum(String code, String split) {
        String[] codeArr = code.split(split);
        int sum = 0;
        for (int i = 0; i < codeArr.length; i++) {
            if(!TextUtils.isEmpty(codeArr[i])){
                sum = sum + Integer.parseInt(codeArr[i]);
            }
        }

        return sum;
    }

    public static int getZongXiao(String code, String split) {
        String[] codeArr = code.split(split);

        String[] allShengXiao = new String[7];

        for (int i = 0; i < codeArr.length; i++) {
            allShengXiao[i] = getShengXiaoFromCode(Integer.parseInt(codeArr[i]));
        }
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < allShengXiao.length; i++) {
            if (!list.contains(allShengXiao[i])) {
                list.add(allShengXiao[i]);
            }
        }


        return list.size();
    }

    public static String getZuXuanResult(int mode, ArrayList<Integer> list) {
        int size = list.size();
        ArrayList<Integer> doneList = new ArrayList<>();
        int match2 = 0;
        int match3 = 0;
        switch (mode) {
            case 1:
                if (size == 3) {
                    for (int i = 0; i < size - 1; i++) {
                        for (int j = i + 1; j < size; j++)
                            if (list.get(i) == list.get(j))
                                match2++;
                    }
                    switch (match2) {
                        case 0:
                            return "组六";
                        case 1:
                            return "组三";
                        case 3:
                            return "豹子";
                    }
                    return "";
                }
                break;
            case 2:
                if (size == 4) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "豹子";
                        if (NumbericUtils.getApperTimes(i, list) == 3)
                            return "组选4";
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match2 == 2)
                        return "组选6";
                    else if (match2 == 1)
                        return "组选12";
                    else
                        return "组选24";
                }
                break;
            case 3:
                if (size == 5) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 5)
                            return "豹子";
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "组选5";
                        if (NumbericUtils.getApperTimes(i, list) == 3) {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match3 == 1 && match2 == 1)
                        return "组选10";
                    else if (match3 == 1 && match2 < 1)
                        return "组选20";
                    else if (match2 == 2)
                        return "组选30";
                    else if (match2 == 1 && match3 < 1)
                        return "组选60";
                    else
                        return "组选120";
                }
                break;
            case 4:
                if (size == 5) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "四季发财";
                        if (NumbericUtils.getApperTimes(i, list) == 3) {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match3 == 1)
                        return "三星报喜";
                    else if (match2 == 1 || match2 == 2)
                        return "好事成双";
                    else
                        return "一帆风顺";
                }
                break;
            case 5:
                if (size == 3) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 3) {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match3 == 1)
                        return "三同号";
                    else if (match2 == 1)
                        return "二同号";
                    else if (NumbericUtils.isConsecutiveNumbers(list))
                        return "三连号";
                    else
                        return "-";
                }
                break;
        }
        return "";
    }

    //不同字体的显示在前面
    public static Spanned showDiffColorBefore(String diffContent, String normalContent) {
        //首先是拼接字符串
        String content = "<font color=\"#ee7e45\">" + diffContent + "</font>" + normalContent;
        return Html.fromHtml(content);
    }

    //不同字体的显示在后面
    public static Spanned showDiffColorAfter(String normalContent, String diffContent) {
        //首先是拼接字符串
        String content = normalContent + "<font color=\"#ee7e45\">" + diffContent + "</font>";
        return Html.fromHtml(content);
    }

    //不同字体的显示在中间
    public static Spanned showDiffColorCenter(String normalContent1, String diffContent, String normalContent2) {
        //首先是拼接字符串
        String content = normalContent1 + "<font color=\"#ee7e45\">" + diffContent + "</font>" + normalContent2;
        return Html.fromHtml(content);
    }

    //获得快乐扑克的形态
    public static String geiKuaiLePukeXT(String codeOpen) {
        String color1 = codeOpen.charAt(1) + "";
        String color2 = codeOpen.charAt(4) + "";
        String color3 = codeOpen.charAt(7) + "";

        int num1 = getNumFromChar(codeOpen.charAt(0));
        int num2 = getNumFromChar(codeOpen.charAt(3));
        int num3 = getNumFromChar(codeOpen.charAt(6));

        sort(num1,num2,num3);


        if (color1 .equals(color2)  && color2 .equals(color3) ) {
            if(num1+1==num2&&num2+1==num3){
                return "同花顺";
            }else{
                return "同花";
            }

        } else {
            if(num1==num2&&num2==num3){
                return "豹子";
            }else if(num1==num2||num2==num3){
                return "对子";
            }else if(num1+1==num2&&num2+1==num3){
                return "顺子";
            }else{
                return "散牌";
            }
        }
    }

    private static int getNumFromChar(char c){
        switch (c){
            case 'A':
                return 1;
            case 'J':
                return 11;
            case 'Q':
                return 12;
            case 'K':
                return 13;
            case 'T':
                return 10;
            default:
                return Integer.parseInt(""+ c);
        }


    }

    //从小打大
    private  static void sort(int a1, int b2, int c3) {
        int temp = 0;
        if (b2 < a1) {
            temp = a1;
            a1 = b2;
            b2 = temp;
        }
        if (c3 < a1) {
            temp = a1;
            a1 = c3;
            c3 = temp;
        }
        if (c3 < b2) {
            temp = b2;
            b2 = c3;
            c3 = temp;
        }
    }

}
