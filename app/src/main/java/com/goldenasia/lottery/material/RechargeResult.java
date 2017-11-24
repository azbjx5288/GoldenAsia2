package com.goldenasia.lottery.material;import android.app.Activity;import android.content.Intent;import android.net.Uri;import android.util.Log;import com.android.volley.NetworkResponse;import com.android.volley.VolleyError;import com.goldenasia.lottery.base.net.VolleyInterface;import com.goldenasia.lottery.base.net.VolleyRequest;import com.goldenasia.lottery.data.JVYAlipayPay;import com.goldenasia.lottery.data.JVYWeChatPay;import com.goldenasia.lottery.fragment.QRCodeFragment;import com.google.gson.Gson;import com.umeng.analytics.MobclickAgent;import java.util.HashMap;import java.util.Map;/** * 充值申请封装 * Created by ACE-PC on 2016/11/21. */public class RechargeResult {    private Activity activity;    private Gson gson;    private String memo;    private int status;    private OnStatusListener onStatusListener;    public RechargeResult(Activity activity, double money, String params, RechargeType type) {        this.activity = activity;        this.gson = new Gson();        distribute(money, params, type);    }    public RechargeResult(Activity activity, double money, String params, RechargeType type, OnStatusListener onStatusListener) {        this.activity = activity;        this.gson = new Gson();        this.onStatusListener = onStatusListener;        distribute(money, params, type);    }    public void setOnStatusListener(OnStatusListener onStatusListener) {        this.onStatusListener = onStatusListener;    }    private void distribute(double money, String params, RechargeType type) {        switch (type) {            case ALIPAYSCANCODE:                initAlipayScanCode(params);                break;            case WECHATSCANCODE:                initWeChatScanCode(params);                break;            case APIPORT:                break;            case SDKPORT:                initSDKPort(money, params);                break;        }    }    private void initWeChatScanCode(String result) {        try {            JVYWeChatPay jvyWeChatPay = gson.fromJson(result, JVYWeChatPay.class);            Map<String, String> params = new HashMap<>();            params.put("version", jvyWeChatPay.getVersion());            params.put("is_phone", String.valueOf(jvyWeChatPay.getIsPhone()));            params.put("is_frame", String.valueOf(jvyWeChatPay.getIsFrame()));            params.put("pay_type", String.valueOf(jvyWeChatPay.getPayType()));            params.put("agent_id", jvyWeChatPay.getAgentId());            params.put("agent_bill_id", String.valueOf(jvyWeChatPay.getAgentBillId()));            params.put("pay_amt", jvyWeChatPay.getPayAmt());            params.put("notify_url", jvyWeChatPay.getNotifyUrl());            params.put("user_ip", jvyWeChatPay.getUserIp());            params.put("agent_bill_time", jvyWeChatPay.getAgentBillTime());            params.put("goods_name", jvyWeChatPay.getGoodsName());            params.put("remark", jvyWeChatPay.getRemark());            params.put("sign", jvyWeChatPay.getSign());            VolleyRequest.requestPost(jvyWeChatPay.getSubmitUrl(), "", params, new VolleyInterface(activity, VolleyInterface.listener, VolleyInterface.errorListener) {                @Override                public void onSuccess(String result) {                    //访问结果                    QRCodeFragment qrCode = new QRCodeFragment();                    Intent intent = new Intent(activity.getApplicationContext(), qrCode.getClass());                    intent.putExtra("payamt", String.valueOf(jvyWeChatPay.getPayAmt()));                    intent.putExtra("html", result);                    intent.putExtra("type","wechat");                    activity.startActivity(intent);                    qrCode.setOnStatusListener(new QRCodeFragment.OnStatusListener() {                        @Override                        public void onStatus(int status, String memo) {                        }                    });                    if (onStatusListener != null) {                        onStatusListener.onStatus(200, "处理完成");                        MobclickAgent.reportError(activity, "处理完成\t" + result);                    }                }                @Override                public void onError(VolleyError error) {                    NetworkResponse networkResponse = error.networkResponse;                    if (networkResponse != null) {                        if (jvyWeChatPay.getIsPhone() == 2 && networkResponse.statusCode == 302) {                            Uri uri = Uri.parse(networkResponse.headers.get("Location"));                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);                            activity.startActivity(intent);                            if (onStatusListener != null) {                                onStatusListener.onStatus(200, "充值成功");                            }                        }                    }                }            });        } catch (Exception e) {            if (onStatusListener != null) {                onStatusListener.onStatus(202, "充值失败，处理尚未完成");                MobclickAgent.reportError(activity, result + "\n" + e);            }        }    }    private void initAlipayScanCode(String result) {        Log.i("支付宝",result);        try {            JVYAlipayPay jvyAlipayPay = gson.fromJson(result, JVYAlipayPay.class);            Map<String, String> params = new HashMap<>();            params.put("version", jvyAlipayPay.getVersion());            params.put("is_phone", String.valueOf(jvyAlipayPay.getIsPhone()));            params.put("pay_type", String.valueOf(jvyAlipayPay.getPayType()));            params.put("agent_id", jvyAlipayPay.getAgentId());            params.put("agent_bill_id", jvyAlipayPay.getAgentBillId());            params.put("pay_amt", String.valueOf(jvyAlipayPay.getPayAmt()));            params.put("notify_url", jvyAlipayPay.getNotifyUrl());            params.put("user_ip", jvyAlipayPay.getUserIp());            params.put("agent_bill_time", jvyAlipayPay.getAgentBillTime());            params.put("goods_name", jvyAlipayPay.getGoodsName());            params.put("sign", jvyAlipayPay.getSign());            VolleyRequest.requestPost(jvyAlipayPay.getSubmitUrl(), "", params, new VolleyInterface(activity, VolleyInterface.listener, VolleyInterface.errorListener) {                @Override                public void onSuccess(String result) {                    //访问结果                    QRCodeFragment qrCode = new QRCodeFragment();                    Intent intent = new Intent(activity.getApplicationContext(), qrCode.getClass());                    intent.putExtra("payamt", String.valueOf(jvyAlipayPay.getPayAmt()));                    intent.putExtra("html", result);                    intent.putExtra("type","alipay");                    activity.startActivity(intent);                    qrCode.setOnStatusListener(new QRCodeFragment.OnStatusListener() {                        @Override                        public void onStatus(int status, String memo) {                        }                    });                }                @Override                public void onError(VolleyError error) {                    NetworkResponse networkResponse = error.networkResponse;                    if (networkResponse != null) {                        if (jvyAlipayPay.getIsPhone() == 2 && networkResponse.statusCode == 302) {                            Uri uri = Uri.parse(networkResponse.headers.get("Location"));                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);                            activity.startActivity(intent);                            if (onStatusListener != null) {                                onStatusListener.onStatus(200, "充值成功");                            }                        }                    }                }            });        } catch (Exception e) {            if (onStatusListener != null) {                onStatusListener.onStatus(202, "充值失败，处理尚未完成");                MobclickAgent.reportError(activity, result + "\n" + e);            }        }    }    private void initSDKPort(double money, String result) {    }    public String getMemo() {        return memo;    }    public void setMemo(String memo) {        this.memo = memo;    }    public int getStatus() {        return status;    }    public void setStatus(int status) {        this.status = status;    }    /**     * 选中监听器     */    public interface OnStatusListener {        void onStatus(int status, String memo);    }}