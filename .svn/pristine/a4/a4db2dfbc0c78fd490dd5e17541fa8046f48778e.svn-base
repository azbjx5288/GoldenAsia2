package com.goldenasia.lottery.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Sakura on 2016/9/15.
 */
public class NumbericUtils
{
    public static boolean isNumeric(String str)
    {
        int length = str.length();
        for (int i = length; --i >= 0; )
        {
            if (!Character.isDigit(str.charAt(i)))
            {
                return false;
            }
        }
        return true;
    }
    
    public static String formatPrize(double prize)
    {
        DecimalFormat decimalFormat = new DecimalFormat("######0.00");
        return String.format("%.2f", Double.valueOf(decimalFormat.format(prize)));
    }
    
    public static ArrayList<String[]> delDupWithOrder(ArrayList<String[]> arrayList)
    {
        ArrayList<String[]> newList = new ArrayList<>();
        newList.addAll(arrayList);
        for (int i = 0, size = arrayList.size(); i < size - 1; i++)
        {
            for (int j = i + 1; j < size; j++)
            {
                if (Arrays.equals(arrayList.get(i), arrayList.get(j)))
                    newList.remove(arrayList.get(j));
            }
        }
        return newList;
    }
    
    public static boolean hasDupString(String[] strings)
    {
        Set<String> set = new HashSet<>();
        for (int i = 0, length = strings.length; i < length; i++)
        {
            set.add(strings[i]);
        }
        if (strings.length > set.size())
            return true;
        else
            return false;
    }
    
    public static boolean hasDupArray(ArrayList<String[]> arrayList)
    {
        for (int i = 0, size = arrayList.size(); i < size - 1; i++)
        {
            for (int j = i + 1; j < size; j++)
            {
                if (Arrays.equals(arrayList.get(i), arrayList.get(j)))
                    return true;
            }
        }
        return false;
    }
    
    public static ArrayList<String[]> delDup(ArrayList<String[]> arrayList)
    {
        ArrayList<String[]> newList = new ArrayList<>();
        newList.addAll(arrayList);
        for (int i = 0, size = arrayList.size(); i < size - 1; i++)
        {
            Arrays.sort(arrayList.get(i));
            for (int j = i + 1; j < size; j++)
            {
                Arrays.sort(arrayList.get(j));
                if (Arrays.equals(arrayList.get(i), arrayList.get(j)))
                    newList.remove(arrayList.get(j));
            }
        }
        //Collections.reverse(newList);
        return newList;
    }
}
