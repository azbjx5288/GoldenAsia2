package com.goldenasia.lottery.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Sakura on 2016/9/12.
 */

public class SharedPreferencesUtils
{
    public static void putString(Context context, String fileName, String key, String value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();
    }
    
    public static String getString(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }
    
    public static void putInt(Context context, String fileName, String key, int value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key, value).apply();
    }
    
    public static int getInt(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, -1);
    }
    
    public static void putLong(Context context, String fileName, String key, long value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putLong(key, value).apply();
    }
    
    public static long getLong(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getLong(key, -1L);
    }
    
    public static void putBoolean(Context context, String fileName, String key, Boolean value)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }
    
    public static Boolean getBoolean(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, true);
    }
}
