package com.goldenasia.lottery.base.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.fragment.LoadingDialog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ACE-PC on 2016/11/21.
 */

public class VolleyRequest {
    private static final String TAG = VolleyRequest.class.getSimpleName();
    private static RetryPolicy sRetryPolicy = new DefaultRetryPolicy(30 * 1000, 3,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    public static StringRequest stringRequest;

    /**
     * get请求
     *
     * @param url
     * @param tag
     * @param vif
     */
    public static void requestGet(String url, String tag, VolleyInterface vif) {
        GoldenAsiaApp.getHttpQueue().cancelAll(tag);
        LoadingDialog.show(vif.context);
        stringRequest = new StringRequest(Request.Method.GET, url, vif.loadingListener(), vif.errorListener());
        stringRequest.setRetryPolicy(sRetryPolicy);
        stringRequest.setTag(tag);
        GoldenAsiaApp.getHttpQueue().add(stringRequest);
        GoldenAsiaApp.getHttpQueue().start();
    }

    /**
     * post请求（带有map传递参数）
     *
     * @param url
     * @param tag
     * @param params
     * @param vif
     */
    public static void requestPost(String url, String tag, final Map<String, String> params, VolleyInterface vif) {
        GoldenAsiaApp.getHttpQueue().cancelAll(tag);
        LoadingDialog.show(vif.context);
        stringRequest = new StringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("", "params:" + params);
                return params;
            }
        };
        stringRequest.setRetryPolicy(sRetryPolicy);
        stringRequest.setTag(tag);
        GoldenAsiaApp.getHttpQueue().add(stringRequest);
        GoldenAsiaApp.getHttpQueue().start();
    }

    /**
     * post请求（带有map传递参数）
     *
     * @param url
     * @param tag
     * @param params
     * @param vif
     */
    public static void requestPost(String url, String tag, final Map<String, String> params, Map<String, String> headers, VolleyInterface vif) {
        GoldenAsiaApp.getHttpQueue().cancelAll(tag);
        LoadingDialog.show(vif.context);
        stringRequest = new StringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.i("", "params:" + params);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }
        };
        stringRequest.setRetryPolicy(sRetryPolicy);
        stringRequest.setTag(tag);
        GoldenAsiaApp.getHttpQueue().add(stringRequest);
        GoldenAsiaApp.getHttpQueue().start();
    }

    /**
     * post请求(带有参数map 重写传递参数方法)
     *
     * @param url    地址
     * @param tag    标签
     * @param params 参数
     * @param vif    接口
     *               void
     */
    public static void requestSpecPost(String url, String tag, final Map<String, String> params, VolleyInterface vif) {
        GoldenAsiaApp.getHttpQueue().cancelAll(tag);
        LoadingDialog.show(vif.context);
        stringRequest = new StringRequest(Request.Method.POST, url, vif.loadingListener(), vif.errorListener()) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                System.out.println("params:" + params);
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                Map<String, String> params = getParams();
                if (params != null && params.size() > 0) {
                    try {
                        return params.get("data").getBytes("UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        };
        stringRequest.setRetryPolicy(sRetryPolicy);
        stringRequest.setTag(tag);
        GoldenAsiaApp.getHttpQueue().add(stringRequest);
        GoldenAsiaApp.getHttpQueue().start();
    }
}
