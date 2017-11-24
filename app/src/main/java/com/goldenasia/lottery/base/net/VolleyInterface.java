package com.goldenasia.lottery.base.net;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.goldenasia.lottery.fragment.LoadingDialog;

/**
 * Created by ACE-PC on 2016/11/21.
 */

public abstract class VolleyInterface {

    public Context context;
    public static Response.Listener<String> listener;
    public static Response.ErrorListener errorListener;

    public VolleyInterface(Context context,Response.Listener<String> listener,Response.ErrorListener errorListener) {
        this.context = context;
        this.listener=listener;
        this.errorListener=errorListener;
    }

    public abstract void onSuccess(String result);
    public abstract void onError(VolleyError error);

    /**
     * 成功回调
     * @return Response.Listener<String>
     */
    public Response.Listener<String> loadingListener(){
        listener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                LoadingDialog.dismiss(context);
                onSuccess(response);
            }
        };
        return listener;
    }

    /**
     * 失败回调
     * @return
     */
    public Response.ErrorListener errorListener(){
        errorListener=new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LoadingDialog.dismiss(context);
                onError(error);
            }
        };
        return errorListener;
    }

}
