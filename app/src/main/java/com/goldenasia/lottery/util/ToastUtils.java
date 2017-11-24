package com.goldenasia.lottery.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Sakura on 2016/9/8.
 */

public class ToastUtils
{
    private static Toast toast;
    
    public static void cancelToast()
    {
        if (toast != null)
            toast.cancel();
    }
    
    public static void showLongToast(Context context, String text)
    {
        if (context != null)
        {
            toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
            toast.show();
        }
    }
    
    public static void showShortToast(Context context, String text)
    {
        if (context != null)
        {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    
    public static void showLongToast(Context context, int resId)
    {
        if (context != null)
        {
            toast = Toast.makeText(context, resId, Toast.LENGTH_LONG);
            toast.show();
        }
    }
    
    public static void showShortToast(Context context, int resId)
    {
        if (context != null)
        {
            toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    
    public static void showShortToastLocation(Context context, String text, int location, int xOffset, int yOffset)
    {
        if (context != null)
        {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(location, xOffset, yOffset);
            toast.show();
        }
    }
}
