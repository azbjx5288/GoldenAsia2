package com.goldenasia.lottery.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Sakura on 2016/9/12.
 */

public class SharedPreferencesUtils
{
    private static final String TAG = "SharedPreferencesUtils";
    
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
    
    public static void putObject(Context context, String fileName, String key, Object object) throws Exception
    {
        if (object instanceof Serializable)
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try
            {
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                objectOutputStream.writeObject(object);
                sharedPreferences.edit().putString(key, new String(Base64.encode(byteArrayOutputStream.toByteArray(),
                        Base64.DEFAULT))).apply();
            } catch (IOException e)
            {
                Log.e("", "Put object failed. ");
            }
        } else
            throw new Exception("User must implements Serializable");
    }
    
    public static Object getObject(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(sharedPreferences
                .getString(key, ""), Base64.DEFAULT));
        Object object = null;
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            object = objectInputStream.readObject();
        } catch (IOException e)
        {
        } catch (ClassNotFoundException e)
        {
        }
        return object;
    }
}
