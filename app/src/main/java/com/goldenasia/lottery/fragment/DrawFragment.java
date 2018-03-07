package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.CardDetail;
import com.goldenasia.lottery.data.CardDetailCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.data.UserInfoCommand;
import com.goldenasia.lottery.data.WithdrawCommand;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 提现页面
 * Created by Alashi on 2016/3/17.
 */
public class DrawFragment extends BaseFragment {

    private static final int ID_USER_INFO = 1;
    private static final int ID_CARD_INFO = 2;
    private static final int ID_APPLY_WITHDRAW = 3;

    private static final int INVALID_INDEX = -1;

    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.prize)
    TextView money;
    @BindView(R.id.card_number)
    TextView cardNumber;
    @BindView(R.id.card_area)
    TextView cardArea;
    @BindView(R.id.card_branch)
    TextView cardBranch;
    @BindView(R.id.fund_password)
    EditText fundPassword;
    @BindView(R.id.draw_money)
    EditText drawMoneyEditText;
    @BindView(R.id.card_info)
    ViewGroup cardInfo;
    @BindView(R.id.card_info_no_card)
    View cardInfoNoCard;

    private ArrayList<CardDetail> cardDetails;
    private int lastCheckIndex = INVALID_INDEX;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.withdraw_deposit, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        money.setText(String.format("%.4f", GoldenAsiaApp.getUserCentre().getUserInfo().getBalance()));
        loadInfo();
    }

    private void loadInfo() {
        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<CardDetail>>>() {
        };
        RestRequestManager.executeCommand(getActivity(), new CardDetailCommand(), typeToken, restCallback,
                ID_CARD_INFO, this);
    }

    @OnClick(R.id.submit)
    public void onClickSubmit() {
        //不为空
        if (TextUtils.isEmpty(fundPassword.getText())) {
            Toast.makeText(getActivity(), "请输入资金密码", Toast.LENGTH_SHORT).show();
            return;
        }
        //长度6~15，不能只有数字也不能只有字母
        if (fundPassword.getText().toString().matches("^[a-zA-Z0-9]{6,15}$")) {
            Toast.makeText(getActivity(), "资金密码长度为6~15，不能只有数字也不能只有字母", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(drawMoneyEditText.getText())) {
            Toast.makeText(getActivity(), "请输入提款金额", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastCheckIndex == INVALID_INDEX) {
            Toast.makeText(getActivity(), "请选择银行卡", Toast.LENGTH_SHORT).show();
            return;
        }

        double drawMoney = Double.parseDouble(drawMoneyEditText.getText().toString());
        if (drawMoney < 100) {
            Toast.makeText(getActivity(), "提款金额不能小于100元", Toast.LENGTH_SHORT).show();
            return;
        }
        if (drawMoney > 150000) {
            Toast.makeText(getActivity(), "提款金额不能大于150000元", Toast.LENGTH_SHORT).show();
            return;
        }

        submit();
    }

    private void submit() {
        CardDetail checkCard = cardDetails.get(lastCheckIndex);

        //资金密码两次MD5加密
        String  pFundPassword= DigestUtils.md5Hex(fundPassword.getText().toString());
        pFundPassword= DigestUtils.md5Hex(pFundPassword);

        WithdrawCommand command = new WithdrawCommand();
        command.setSecurityPassword(pFundPassword);
        command.setWithdrawBankId(checkCard.getBankId());
        command.setWithdrawAmount(Double.parseDouble(drawMoneyEditText.getText().toString()));
        command.setBindCardId(checkCard.getBindCardId());
        command.setWithdrawCardNumber(checkCard.getCardNumber());
        command.setProvince(checkCard.getProvince());
        command.setCity(checkCard.getCity());
        command.setBranchName(checkCard.getBranch());

        executeCommand(command, restCallback, ID_APPLY_WITHDRAW);

        showProgress("正在申请提现...");
    }

    private RestCallback restCallback = new RestCallback() {

        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case ID_USER_INFO:
                    UserInfo userInfo = ((UserInfo) response.getData());
                    GoldenAsiaApp.getUserCentre().setUserInfo(userInfo);
                    if (userInfo != null) {
                        money.setText(String.format("%.4f", userInfo.getBalance()));
                    }
                    break;

                case ID_CARD_INFO:
                    cardDetails = (ArrayList<CardDetail>) response.getData();
                    refreshCardLayout();
                    break;

                case ID_APPLY_WITHDRAW:
                    showToast("申请提现成功", Toast.LENGTH_LONG);
                    resetUI();
                    loadInfo();
                    break;
                default:
                    break;
            }
            return false;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (request.getId() == ID_CARD_INFO) {
                showToast("无法加载银行卡信息", Toast.LENGTH_LONG);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state != RestRequest.RUNNING) {
                hideProgress();
            }
        }
    };

    private void resetUI() {
        fundPassword.setText("");
        drawMoneyEditText.setText("");
        lastCheckIndex = INVALID_INDEX;
        cardNumber.setText("");
        cardArea.setText("");
        cardBranch.setText("");
        for (int i = 0; i < cardInfo.getChildCount(); i++) {
            ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(i);
            tmp.setVisibility(View.GONE);
            CheckBox checkBox = (CheckBox) tmp.findViewById(R.id.card_item_checkbox);
            checkBox.setChecked(false);
        }
    }

    private void refreshCardLayout() {
        if (cardDetails == null || cardDetails.size() == 0) {
            cardInfoNoCard.setVisibility(View.VISIBLE);
            cardInfo.setVisibility(View.GONE);
            //replaceFragment(BankCardSetting.class, null);
            TextView tipText=new TextView(getActivity());
            tipText.setHeight(150);
            tipText.setGravity(Gravity.CENTER);
            tipText.setText("暂无绑定银行卡，请先绑定银行卡!");
            CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
            builder.setContentView(tipText);
            builder.setTitle("温馨提示");
            builder.setLayoutSet(DialogLayout.SINGLE);
            builder.setPositiveButton("知道了", (dialog, which) -> {
                dialog.dismiss();
                launchFragment(BankCardSetting.class);
            });
                builder.create().show();
            return;
        } else {
            cardInfoNoCard.setVisibility(View.GONE);
            cardInfo.setVisibility(View.VISIBLE);
            removeAllMenu();
            if (cardDetails.size() < 3) {
                //addMenuItem(R.drawable.plus, v -> replaceFragment(AddBankCard.class, null));
                addMenuItem(R.drawable.add, v -> launchFragment(AddBankCard.class));
            }
        }

        for (int i = 0; i < cardInfo.getChildCount(); i++) {
            ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(i);
            if (i >= 0 && i < cardDetails.size()) {
                tmp.setVisibility(View.VISIBLE);
                tmp.setOnClickListener(onClickCardListener);
                CardDetail cardDetail = cardDetails.get(i);
                tmp.setTag(i);
                ((ImageView) tmp.findViewById(R.id.card_item_logo)).setImageResource(getLogo(cardDetail.getBankId()));
            } else {
                tmp.setVisibility(View.GONE);
            }
        }

        scrollView.scrollTo(0, 0);
    }

    private static int getLogo(int backId) {
        switch (backId) {
            case 1://工商银行
                return R.drawable.bank_logo_gs;
            case 2://农业银行
                return R.drawable.bank_logo_ny;
            case 3://建设银行
                return R.drawable.bank_logo_js;
            case 4://招商银行
                return R.drawable.bank_logo_zs;
            case 5://交通银行
                return R.drawable.bank_logo_jt;
            case 6://中信银行
                return R.drawable.bank_logo_zx;
            case 7://邮政储汇
                return R.drawable.bank_logo_yz;
            case 8://中国光大银行
                return R.drawable.bank_logo_gd;
            case 9://民生银行
                return R.drawable.bank_logo_ms;
            case 10://上海浦东发展银行
                return R.drawable.bank_logo_pf;
            case 11://兴业银行
                return R.drawable.bank_logo_xy;
            case 12://广发银行
                return R.drawable.bank_logo_gf;
            case 13://平安银行
                return R.drawable.bank_logo_pa;
            case 15://华夏银行
                return R.drawable.bank_logo_hx;
            case 16://东莞银行',
                return R.drawable.bank_logo_dg;
            case 17://渤海银行',
                return R.drawable.bank_logo_bh;
            case 18://杭州银行',
                return R.drawable.bank_logo_hz;
            case 19://浙商银行',
                return R.drawable.bank_logo_zsyh;
            case 20://北京银行',
                return R.drawable.bbank_logo_j;
            case 21://广州银行',
                return R.drawable.bank_logo_gz;
            case 22://中国银行',
                return R.drawable.bank_logo_zg;
            default:
                return R.drawable.cycleviewpager_default_pic;
        }
    }

    private View.OnClickListener onClickCardListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (int) v.getTag();
            if (lastCheckIndex != INVALID_INDEX) {
                ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(lastCheckIndex);
                CheckBox checkBox = (CheckBox) tmp.findViewById(R.id.card_item_checkbox);
                checkBox.setChecked(false);
                cardNumber.setText("");
                cardArea.setText("");
                cardBranch.setText("");
                if (lastCheckIndex == index) {
                    lastCheckIndex = INVALID_INDEX;
                    return;
                }
            }

            lastCheckIndex = index;
            ViewGroup tmp = (ViewGroup) cardInfo.getChildAt(lastCheckIndex);
            CheckBox checkBox = (CheckBox) tmp.findViewById(R.id.card_item_checkbox);
            checkBox.setChecked(true);

            CardDetail checkCard = cardDetails.get(lastCheckIndex);
            cardNumber.setText(checkCard.getCardNumber());
            cardArea.setText(checkCard.getProvince() + "-" + checkCard.getCity());
            cardBranch.setText(checkCard.getBranch());
        }
    };
}
