package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.util.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手工录入
 * 特殊字符、数字显示风格、
 * Created by ACE-PC on 2016/9/20.
 */

public class ManualInputEntry {
    private static final String TAG = ManualInputEntry.class.getSimpleName();
    private Context context;
    /**
     * 手工录入字符串
     **/
    private String enterstr;

    private int bits = 0, min = 0, max = 0;
    private ArrayList<String[]> chooseArray = new ArrayList<>();
    private ArrayList<String[]> alterArray = new ArrayList<>();
    /**
     * numberStyle 数字显示风格，true: 6, false: 06 null: 123
     */
    private boolean numberStyle = true;

    public ManualInputEntry(Context context, String enterstr, int bits, boolean numberStyle, int min, int max) {
        this.context = context;
        this.enterstr = enterstr;
        this.bits = bits;
        this.numberStyle = numberStyle;
        this.min = min;
        this.max = max;
        initSpecStr();
    }

    public ArrayList<String[]> getChooseArray() {
        /*if (column == 1) {
            chooseArray = duplicate();
        }*/
        return chooseArray;
    }

    private boolean isNumberStyle() {
        return numberStyle;
    }

    /**
     * 初始化去除 特殊字符
     */
    private void initSpecStr() {
        if (enterstr.isEmpty()) {
            Toast.makeText(context, "请输入大底号码", Toast.LENGTH_LONG).show();
            return;
        }
        chooseArray.clear();
        ruleAnalysis(enterstr);
    }

    private static final String REGEX = "^.*[(\\w)|(\\s)|(,)|(，)|(;)|(；)|(\\|)|(｜)|(:)|(：)].*$";
    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        return isMatches(str, REGEX);
    }

    public static boolean isMatches(String str, String regex) {
        return str.matches(regex);
    }

    /**
     * 解析正确选号
     */
    private void ruleAnalysis(String orgStr) {
        if (isNumberStyle()) {
            //判断号码风格 true 6 123 123 123,123|123 验证规则
            String[] verifyCode = orgStr.split("\\s|,|，|;|；|\\||｜");
            for (int i = 0, length = verifyCode.length; i < length; i++) {
                if (!verifyCode[i].isEmpty()) {
                    //验证含有规则字符
                    if (verifyNumeralFormat(verifyCode[i])) {
                        findSameColumnSingle(removeEmpty(verifyCode[i]));
                    } else {
                        if (NumbericUtils.isNumeric(verifyCode[i])) {
                            //单位号码判断纯数字
                            findSameColumnSingle(removeEmpty(verifyCode[i]));
                        } else {
                            //不是纯数字，提取为纯数字
                            String number = filterNumber(verifyCode[i]);
                            if (filterColumn(number)) {
                                findSameColumnSingle(removeEmpty(verifyCode[i]));
                            }
                        }
                    }
                }
            }
        } else {
            //判断号码风格 true 06 直选号码 010203 01 02 03
            String[] verifyCode = orgStr.split("\\n|,|，|;|；|\\||｜");
            StringBuilder codeString = new StringBuilder();
            for (int i = 0, length = verifyCode.length; i < length; i++) {
                if (!verifyCode[i].isEmpty()) {
                    if (verifyOrFormat(verifyCode[i])) {
                        //验证含有规则字符
                        String[] ruleSplit = verifyCode[i].split("\\s");
                        findSameColumnDouble(ruleSplit);
                    } else if (verifyOrSplitTwo(verifyCode[i], bits)) {
                        if (verifyCode[i].length() % 2 == 0) {
                            // verifyCode[i].split("[(?:0[1-9]?|1[0-1]|11)]");
                            StringBuffer s1 = new StringBuffer(verifyCode[i]);
                            for (int index = 2; index < verifyCode[i].length(); index += 3) {
                                s1.insert(index, ',');
                            }
                            String[] ruleSplit = s1.toString().split(",");
                            findSameColumnDouble(ruleSplit);
                        }

                    } else {
                        //重新查看 验证是否两位号
                        if (verifyOrSplitTwo(verifyCode[i])) {
                            if (Integer.parseInt(verifyCode[i]) <= max) {
                                codeString.append(verifyCode[i]);
                                if (i != length - 1) {
                                    codeString.append(",");
                                }
                            }
                        } else if (NumbericUtils.isNumeric(verifyCode[i])) {
                            String unpack = unpackStr(verifyCode[i]);
                            String[] ruleSplit = unpack.split(",|，");
                            findSameColumnDouble(ruleSplit);
                        } else {
                            //不是纯数字，提取为纯数字
                            String number = filterNumber(verifyCode[i]);
                            Integer[] repeat = removaRepeat(transformStringOrInt(number.split("")));
                            findSameColumnDouble(transformIntOrString(repeat, false));
                        }
                    }
                }
            }
            //单号集合
            String[] solitary = codeString.toString().split(",|，");
            if (solitary.length >= bits) {
                String[] ruleSplit = codeString.toString().split(",|，");
                Integer[] repeat = removaRepeat(transformStringOrInt(ruleSplit));
                findSameColumnDouble(transformIntOrString(repeat, false));
            }
        }
    }

    private ArrayList<String> duplicate() {
        return new ArrayList(new HashSet(chooseArray));
    }

    /**==================================== 时时彩 ==============================================================*/
    /**
     * 验证相同栏位 true 6
     */
    private void findSameColumnSingle(String[] orgStr) {
        if (orgStr.length == bits) {
            boolean verify = false;
            for (int r = 0, ruleLength = orgStr.length; r < ruleLength; r++) {
                if (!orgStr[r].isEmpty() && NumbericUtils.isNumeric(orgStr[r])) {
                    if (Integer.parseInt(orgStr[r]) <= max) {
                        verify = true;
                    } else {
                        verify = false;
                    }
                } else {
                    verify = false;
                }
            }
            if (verify) {
                chooseArray.add(orgStr);
            }
        } else if (orgStr.length > bits) {
            Integer[] num = transformStringOrInt(orgStr);
            List<String> arrayList = removeDuplicateWithOrder(combine(num, bits));
            for (int i = 0; i < arrayList.size(); i++) {
                String[] array = removeEmpty(arrayList.get(i));
                findSameColumnSingle(array);
            }
        }
    }

    /**
     * ===================================================十一选五============================================================/*
     * /** 处理正确规则 数字
     */
    private void findSameColumnDouble(String[] orgStr) {
        if (orgStr.length == bits) {
            boolean verify = false;
            for (int r = 0, ruleLength = orgStr.length; r < ruleLength; r++) {
                if (!orgStr[r].isEmpty() && NumbericUtils.isNumeric(orgStr[r])) {
                    if (Integer.parseInt(orgStr[r]) <= max && Integer.parseInt(orgStr[r]) != 0) {
                        verify = true;
                    } else {
                        verify = false;
                    }
                } else {
                    verify = false;
                }
            }
            if (verify && checkRepeat(orgStr)) {
                chooseArray.add(orgStr);
            }
        } else if (orgStr.length > bits) {
            Integer[] num = new Integer[orgStr.length];
            for (int i = 0, length = orgStr.length; i < length; i++) {
                if (!orgStr[i].isEmpty() && NumbericUtils.isNumeric(orgStr[i])) {
                    num[i] = Integer.parseInt(orgStr[i]);
                }
            }

            Integer[] repeatNum = removaRepeat(num);
            List arrayList = combine(repeatNum, bits);
            for (int i = 0; i < arrayList.size(); i++) {
                String[] array = transformIntOrString((Integer[]) arrayList.get(i), false);
                StringBuilder uniteBuilder = new StringBuilder();
                for (int j = 0, length = array.length; j < length; j++) {
                    uniteBuilder.append(array[j]);
                    if (j != length - 1) {
                        uniteBuilder.append(",");
                    }
                }
                ruleAnalysis(uniteBuilder.toString());
            }
        }
    }

    private String[] transformIntOrString(Integer[] split, boolean numberStyle) {
        String[] string = new String[split.length];
        for (int i = 0, length = split.length; i < length; i++) {
            string[i] = String.format(numberStyle ? "%d" : "%02d", split[i]);
        }
        return string;
    }

    private Integer[] transformStringOrInt(String[] split) {
        ArrayList<Integer> array = new ArrayList<>();
        for (int i = 0, length = split.length; i < length; i++) {
            if (!split[i].isEmpty() && NumbericUtils.isNumeric(split[i])) {
                array.add(Integer.parseInt(split[i]));
            }
        }
        Integer[] num = new Integer[array.size()];
        for (int i = 0; i < array.size(); i++) {
            num[i] = array.get(i);
        }
        return num;
    }

    private String[] removeEmpty(String enterstr) {
        String[] enter = enterstr.split("");
        List<String> tmp = new ArrayList<String>();
        for (String str : enter) {
            if (str != null && str.length() != 0) {
                tmp.add(str);
            }
        }
        enter = tmp.toArray(new String[0]);
        return enter;
    }

    /**
     * 去掉重复数字
     */
    private Integer[] removaRepeat(Integer[] repeat) {
        List<Integer> tempList = new ArrayList<>();
        for (Integer i : repeat) {
            if (!tempList.contains(i)) {
                tempList.add(i);
            }
        }
        Integer[] num = new Integer[tempList.size()];
        for (Integer i = 0, length = tempList.size(); i < length; i++) {
            num[i] = tempList.get(i);
        }
        return num;
    }

    public List<String> removeDuplicateWithOrder(List<Integer[]> list) {
        List<String> order = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            StringBuffer odStr = new StringBuffer();
            for (int j = 0, length = list.get(i).length; j < length; j++) {
                odStr.append(list.get(i)[j]);
            }
            order.add(odStr.toString());
        }
        return getNoRepeatList(order);
    }

    // 利用 HashSet的特性
    public List<String> getNoRepeatList(List<String> list) {
        return new ArrayList<String>(new HashSet<String>(list));
    }

    //判断数组中是否有重复值
    public boolean checkRepeat(String[] array) {
        Set<String> set = new HashSet<String>();
        for (String str : array) {
            set.add(str);
        }
        if (set.size() != array.length) {
            return false;//有重复
        } else {
            return true;//不重复
        }
    }

    /**
     * 无特殊符号、 纯数字 分隔 双位号
     **/
    private String unpackStr(String orgStr) {
        if (orgStr.isEmpty()) {
            return orgStr;
        }
        String[] sp = orgStr.split("");
        ArrayList<Integer> arrayList = new ArrayList<>();
        StringBuilder keepBuilder = new StringBuilder();
        boolean flag = true;
        for (int i = 0, len = sp.length; i < len; i++) {
            if (!sp[i].isEmpty()) {
                int num = Integer.parseInt(sp[i]);
                if (num == 1 || num == 0) {
                    if (flag && num != 0) {
                        flag = false;
                        arrayList.add(num);
                    } else if (keepBuilder.length() < 2) {
                        keepBuilder.append(num);
                    }
                } else if (num != 0) {
                    arrayList.add(num);
                }
            }
        }
        if (keepBuilder.length() == 2) {
            String k = keepBuilder.toString();
            if (Integer.parseInt(k) != 0) {
                arrayList.add(Integer.parseInt(k));
            }
        }
        StringBuilder dataBuilder = new StringBuilder();
        for (int i = 0, size = arrayList.size(); i < size; i++) {
            dataBuilder.append(arrayList.get(i));
            if (i != size - 1) {
                dataBuilder.append(",");
            }
        }
        return dataBuilder.toString();
    }

    /**
     * 验证十一选五格式
     */
    public boolean verifyOrSplitTwo(String orgStr, int col) {
        Pattern p = Pattern.compile("^(?:0[1-9]?|1[0-1]|11){" + col + "}$");
        Matcher m = p.matcher(orgStr);
        return m.matches();
    }

    public boolean verifyOrSplitTwo(String orgStr) {
        Pattern p = Pattern.compile("^(?:0[1-9]?|1[0-1]|11)$");
        Matcher m = p.matcher(orgStr);
        return m.matches();
    }

    /**
     * 验证匹配空格格式 \s 各种空格表示1次或多次
     */
    private boolean verifyOrFormat(String orgStr) {
        Pattern p = Pattern.compile("\\s+");
        Matcher m = p.matcher(orgStr);
        return m.find();
    }

    /**
     * 验证数字格式 \d [0-9]表示1次或多次
     */
    private boolean verifyNumeralFormat(String orgStr) {
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(orgStr);
        return m.find();
    }

    /**
     * @param number
     * @return
     * @Title : filterNumber
     * @Type : FilterStr
     * @Description : 过滤出数字
     */
    private String filterNumber(String number) {
        number = number.replaceAll("[^(0-9)]", "");
        return number;
    }

    /**
     * @param number
     * @return
     * @Title : filterColumn
     * @Type : FilterStr
     * @Description : 过滤出选号位数
     */
    private boolean filterColumn(String number) {
        return bits <= number.length();
    }

    /**
     * 从n个数字中选择m个数字
     *
     * @param a
     * @param m
     * @return
     */
    public List<Integer[]> combine(Integer[] a, int m) {
        int n = a.length;
        List<Integer[]> result = new ArrayList<>();
        if (m > n) {
            Toast.makeText(context, "错误！数组a中只有" + n + "个元素。" + m + "大于" + 2 + "!!!", Toast.LENGTH_LONG).show();
            return result;
        }

        Integer[] bs = new Integer[n];
        for (int i = 0; i < n; i++) {
            bs[i] = 0;
        }
        //初始化
        for (int i = 0; i < m; i++) {
            bs[i] = 1;
        }
        boolean flag = true;
        boolean tempFlag = false;
        int pos = 0;
        int sum = 0;
        //首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
        do {
            sum = 0;
            pos = 0;
            tempFlag = true;
            result.add(print(bs, a, m));

            for (int i = 0; i < n - 1; i++) {
                if (bs[i] == 1 && bs[i + 1] == 0) {
                    bs[i] = 0;
                    bs[i + 1] = 1;
                    pos = i;
                    break;
                }
            }
            //将左边的1全部移动到数组的最左边
            for (int i = 0; i < pos; i++) {
                if (bs[i] == 1) {
                    sum++;
                }
            }
            for (int i = 0; i < pos; i++) {
                if (i < sum) {
                    bs[i] = 1;
                } else {
                    bs[i] = 0;
                }
            }
            //检查是否所有的1都移动到了最右边
            for (int i = n - m; i < n; i++) {
                if (bs[i] == 0) {
                    tempFlag = false;
                    break;
                }
            }
            if (tempFlag == false) {
                flag = true;
            } else {
                flag = false;
            }
        } while (flag);
        result.add(print(bs, a, m));

        return result;
    }

    private Integer[] print(Integer[] bs, Integer[] a, int m) {
        Integer[] result = new Integer[m];
        int pos = 0;
        for (int i = 0; i < bs.length; i++) {
            if (bs[i] == 1) {
                result[pos] = a[i];
                pos++;
            }
        }
        return result;
    }
}
