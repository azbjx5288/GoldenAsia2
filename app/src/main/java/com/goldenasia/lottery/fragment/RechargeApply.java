package com.goldenasia.lottery.fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.JsonValidator;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.base.net.VolleyErrorHelper;
import com.goldenasia.lottery.base.net.VolleyInterface;
import com.goldenasia.lottery.base.net.VolleyRequest;
import com.goldenasia.lottery.component.AdaptHighListView;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.AlipayTransferBean;
import com.goldenasia.lottery.data.RechargeConfig;
import com.goldenasia.lottery.data.RechargeConfigCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.Recharge;
import com.goldenasia.lottery.material.RechargeType;
import com.goldenasia.lottery.user.UserCentre;
import com.goldenasia.lottery.util.ToastUtils;
import com.goldenasia.lottery.view.adapter.RechargeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by ACE-PC on 2016/6/10.
 */
@RuntimePermissions
public class RechargeApply extends BaseFragment {
    private static final String TAG = RechargeApply.class.getSimpleName();

    private static final int TRACE_RECHARGE_CONFIG_COMMAND = 1;
    @BindView(R.id.recharge_username)
    TextView username;
    @BindView(R.id.edit_orderamount)
    EditText editOrderamount;
    @BindView(R.id.basic_layout)
    LinearLayout basicLayout;

    @BindView(R.id.mode_label_tip)
    TextView modeLabelTip;
    @BindView(R.id.recharge_method)
    ScrollView methodLayout;
    @BindView(R.id.recharge_type)
    AdaptHighListView listView;
    @BindView(R.id.submit_layout)
    LinearLayout submitLayout;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.empty_view)
    TextView emptyView;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.mode_label)
    TextView modeLabel;
    @BindView(R.id.true_name)
    EditText trueName;
    @BindView(R.id.transfer)
    LinearLayout transfer;
    @BindView(R.id.tip)
    TextView tip;

    private RechargeAdapter rechargeAdapter;
    private boolean isMethod = true;
    private UserCentre userCentre;
    private List<RechargeConfig> config = new ArrayList<RechargeConfig>();
    private RechargeConfig cardRecharge = null;
    private double money;
    private int alipayTransferDtID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "快速充值", R.layout.recharge_apply);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        rechargeAdapter = new RechargeAdapter(config);
        rechargeAdapter.setOnRechargeMethodClickListener((RechargeConfig card, int position) -> {
            cardRecharge = card;
        });
        listView.setAdapter(rechargeAdapter);
        listView.setVisibility(View.VISIBLE);
        emptyView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        emptyView.setVisibility(View.GONE);
        listView.setEmptyView(emptyView);
        listView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    methodLayout.requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        setListViewHeightBasedOnChildren(listView);
        configLoad();
        InitUI();
    }

    private void applyArguments() {
        userCentre = GoldenAsiaApp.getUserCentre();
    }

    private void InitUI() {
        basicLayout.setVisibility(View.GONE);
        transfer.setVisibility(View.GONE);
        tip.setVisibility(View.GONE);
        username.setText(userCentre.getUserName());
        editOrderamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                int len = s.toString().length();
                if (len == 1 && text.equals("0")) {
                    s.clear();
                }
            }
        });
        if (cardRecharge != null) {
            editOrderamount.setHint("建议充值" + cardRecharge.getMinLimit() + "元以上金额");
        }
        btnSubmit.setOnClickListener((View v) -> {
            if (cardRecharge == null) {
                return;
            }
            if (isMethod) {
                if (cardRecharge.isHref()) {
                    Intent mIntent = new Intent(getActivity(), PayHtml.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("rconfig", cardRecharge);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                    return;
                } else {
                    isMethod = false;
                    basicLayout.setVisibility(View.VISIBLE);
                    methodLayout.setVisibility(View.GONE);
                    btnSubmit.setText("发起充值");
                    if (cardRecharge != null) {
                        switch (cardRecharge.getBankId()) {
                            case 101:
                                transfer.setVisibility(View.VISIBLE);
                                tip.setVisibility(View.VISIBLE);
                                break;
                            case 203:
                                //callPhone();
                                break;
                            case 210:
                                //callPhone();
                                break;
                        }
                    }
                }
            } else {
                pay();
            }

        });
    }

    //智付申请权限
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void callPhone() {
        dinpayLoad(money);
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showPhone(PermissionRequest request) {
        new AlertDialog.Builder(getActivity()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                request.proceed();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                request.cancel();
            }
        }).setCancelable(false).setMessage("使用智付需要获取手机通话权限").show();
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void onPhoneDenied() {
        ToastUtils.showShortToast(getActivity(), "获取手机通话权限才可使用智付");
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void onPhoneNeverAsk() {
        ToastUtils.showShortToast(getActivity(), "若想使用智付，请到“设置-应用程序-金亚洲”中开启手机通话权限");
    }

    //支付宝申请权限
    @NeedsPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
    void callCard() {
        switch (cardRecharge.getTradeType()) {
            case 8:
                jkhWeChatPayLoad(money);
                break;
            case 9:
                jkhAlipayPayLoad(money); //汇元支付宝
                break;
        }
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showCard(PermissionRequest request) {
        new AlertDialog.Builder(getActivity()).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                request.proceed();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(@NonNull DialogInterface dialog, int which) {
                request.cancel();
            }
        }).setCancelable(false).setMessage("使用支付宝扫码需要获取读写SD卡权限").show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onCardDenied() {
        ToastUtils.showShortToast(getActivity(), "获取读写SD卡权限才可使用支付宝");
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void onCardNeverAsk() {
        ToastUtils.showShortToast(getActivity(), "若想使用支付宝，请到“设置-应用程序-金亚洲”中开启读写SD卡权限");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        RechargeApplyPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void pay() {
        money = 0;
        String moneyStr = editOrderamount.getText().toString();
        if (moneyStr.isEmpty()) {
            showToast("请输入充值金额", Toast.LENGTH_SHORT);
            return;
        }
        money = Double.valueOf(moneyStr);
        //增加非空判断
        if (config.size() == 0) {
            showToast("暂无充值渠道,请联系客服!", Toast.LENGTH_SHORT);
            return;
        }
        if (cardRecharge.getMinLimit() > money) {
            showToast("充值金额最小" + cardRecharge.getMinLimit() + "元", Toast.LENGTH_SHORT);
            return;
        }
        if (money > cardRecharge.getMaxLimit()) {
            showToast("充值金额最大" + cardRecharge.getMaxLimit() + "元", Toast.LENGTH_SHORT);
            return;
        }
        if (cardRecharge == null) {
            return;
        }
        switch (cardRecharge.getBankId()) {
            case 101:
                jkhAlipayTransferLoad(money, trueName.getText().toString());
                break;
            case 203:
                //dinpayLoad(money);
                RechargeApplyPermissionsDispatcher.callPhoneWithCheck(this);
                break;
            case 210:
                //jkhpayLoad(money);
                RechargeApplyPermissionsDispatcher.callCardWithCheck(this);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 智付支付
     *
     * @param moneyStr
     */
    @Deprecated
    private void dinpayLoad(double moneyStr) {
        /*Map<String, String> params = new HashMap<>();
        params.put("username", userCentre.getUserName());
        params.put("user_id", userCentre.getUserID());
        params.put("deposit_amount", String.valueOf(moneyStr));
        params.put("device_code", String.valueOf(4));
        params.put("card_id", String.valueOf(cardRecharge.getCardId()));
        params.put("bank_id", String.valueOf(cardRecharge.getBankId()));
        params.put("from_system", "android_sys");

        VolleyRequest.requestPost(cardRecharge.getShopURL(), "", params, new VolleyInterface(getContext(),
                VolleyInterface.listener, VolleyInterface.errorListener) {
            @Override
            public void onSuccess(String result) {
                //访问结果
                if (!TextUtils.isEmpty(result)) {
                    if (new JsonValidator().validate(result)) {
                        Recharge.createRecharge(getActivity(), moneyStr, result, RechargeType.SDKPORT, (int status,
                                                                                                        String memo) ->
                        {
                            if (status != 200) {
                                tipDialog(memo);
                            }
                            if (isMethod) {
                                isMethod = false;
                                basicLayout.setVisibility(View.VISIBLE);
                                methodLayout.setVisibility(View.GONE);
                                btnSubmit.setText("下一步");
                            }
                        });
                    } else {
                        tipDialog("充值服务异常，请联系客服");
                    }
                } else {
                    tipDialog("充值请求失败，请联系客服");
                }
            }

            @Override
            public void onError(VolleyError error) {
                tipDialog(VolleyErrorHelper.getMessage(error, getContext()));
            }
        });*/
    }

    /**
     * 支付宝转账
     *
     * @param moneyStr
     */
    private void jkhAlipayTransferLoad(double moneyStr, String name) {
        if (cardRecharge == null) {
            return;
        }
        if (TextUtils.isEmpty(trueName.getText())) {
            ToastUtils.showLongToast(getActivity(), "请输入支付宝真实姓名");
            return;
        }
        boolean hasTransfer = false;
        for (RechargeConfig rechargeConfig : config) {
            if (rechargeConfig.getBankId() == 101) {
                hasTransfer = true;
                alipayTransferDtID = rechargeConfig.getDtId();
                break;
            }
        }
        if (!hasTransfer)
            return;

        AlipayTransferBean bean = new AlipayTransferBean();
        bean.setBankID(101);
        bean.setDtID(alipayTransferDtID);
        bean.setTrueName(trueName.getText().toString());
        bean.setAmount(Float.valueOf(editOrderamount.getText().toString()));
        AlipayTransferFragment.launch(RechargeApply.this, bean);
    }

    /**
     * 汇元微信
     *
     * @param moneyStr
     */
    private void jkhWeChatPayLoad(double moneyStr) {
        if (cardRecharge == null) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("payType", "mobileWechat");
        params.put("user_id", userCentre.getUserID());
        params.put("username", userCentre.getUserName());
        params.put("deposit_amount", String.valueOf(moneyStr));
        params.put("client_ip", ConstantInformation.getLocalIpAddress(getContext()));
        params.put("device_code", String.valueOf(4));
        params.put("card_id", String.valueOf(cardRecharge.getCardId()));
        params.put("bank_id", String.valueOf(cardRecharge.getBankId()));

        VolleyRequest.requestPost(cardRecharge.getShopURL(), "", params, new VolleyInterface(getContext(),
                VolleyInterface.listener, VolleyInterface.errorListener) {
            @Override
            public void onSuccess(String result) {
                //访问结果
                if (!TextUtils.isEmpty(result)) {
                    if (new JsonValidator().validate(result)) {
                        Recharge.createRecharge(getActivity(), moneyStr, result, RechargeType.WECHATSCANCODE, (int status, String memo) ->
                        {
                            if (status != 200) {
                                tipDialog(memo);
                            }
                            if (isMethod) {
                                isMethod = false;
                                basicLayout.setVisibility(View.VISIBLE);
                                methodLayout.setVisibility(View.GONE);
                                btnSubmit.setText("下一步");
                            }
                        });
                    } else {
                        tipDialog("充值服务异常，请联系客服");
                    }
                } else {
                    tipDialog("充值请求失败，请联系客服");
                }
            }

            @Override
            public void onError(VolleyError error) {
                tipDialog(VolleyErrorHelper.getMessage(error, getContext()));
            }
        });
    }

    /**
     * 汇元支付宝
     *
     * @param moneyStr
     */
    private void jkhAlipayPayLoad(double moneyStr) {
        if (cardRecharge == null) {
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("payType", "mobileAli");
        params.put("user_id", userCentre.getUserID());
        params.put("username", userCentre.getUserName());
        params.put("deposit_amount", String.valueOf(moneyStr));
        params.put("client_ip", ConstantInformation.getLocalIpAddress(getContext()));
        params.put("device_code", String.valueOf(4));
        params.put("card_id", String.valueOf(cardRecharge.getCardId()));
        params.put("bank_id", String.valueOf(cardRecharge.getBankId()));

        VolleyRequest.requestPost(cardRecharge.getShopURL(), "", params, new VolleyInterface(getContext(),
                VolleyInterface.listener, VolleyInterface.errorListener) {
            @Override
            public void onSuccess(String result) {
                //访问结果
                if (!TextUtils.isEmpty(result)) {
                    if (new JsonValidator().validate(result)) {
                        Recharge.createRecharge(getActivity(), moneyStr, result, RechargeType.ALIPAYSCANCODE, (int status, String memo) ->
                        {
                            if (status != 200) {
                                tipDialog(memo);
                            }
                            if (isMethod) {
                                isMethod = false;
                                basicLayout.setVisibility(View.VISIBLE);
                                methodLayout.setVisibility(View.GONE);
                                btnSubmit.setText("下一步");
                            }
                        });
                    } else {
                        tipDialog("充值服务异常，请联系客服");
                    }
                } else {
                    tipDialog("充值请求失败，请联系客服");
                }
            }

            @Override
            public void onError(VolleyError error) {
                tipDialog(VolleyErrorHelper.getMessage(error, getContext()));
            }
        });
    }

    private void configLoad() {
        RechargeConfigCommand configCommand = new RechargeConfigCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<RechargeConfig>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), configCommand, typeToken, restCallback, TRACE_RECHARGE_CONFIG_COMMAND, this);
        restRequest.execute();
    }


    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case TRACE_RECHARGE_CONFIG_COMMAND: {
                    if (response != null && response.getData() instanceof ArrayList) {
                        config = (ArrayList<RechargeConfig>) response.getData();
                        modeLabelTip.setVisibility(View.GONE);
                        rechargeAdapter.setData(config);
                    }
                    break;
                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 8311) {
                methodLayout.setVisibility(View.GONE);
                submitLayout.setVisibility(View.GONE);
                modeLabelTip.setVisibility(View.VISIBLE);
                return true;
            } else if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            } else if (errCode == 7022) {
                modeLabel.setVisibility(View.GONE);
                emptyView.setText("请在“银行卡管理”中添加银行卡才能完成充值");
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        launchFragment(BankCardSetting.class);
                        getActivity().finish();
                    }
                });
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {

        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    /**
     * 动态设置ListView的高度
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
