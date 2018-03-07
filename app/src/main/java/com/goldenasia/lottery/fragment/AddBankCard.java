package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.component.MyTextWatcher;
import com.goldenasia.lottery.component.WheelView;
import com.goldenasia.lottery.data.BankOpen;
import com.goldenasia.lottery.data.BankOpenCommand;
import com.goldenasia.lottery.data.BindCardCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.CheckBankNumber;
import com.goldenasia.lottery.material.ConstantInformation;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class AddBankCard extends BaseFragment {
    private static final int BINDCARD_TRACE_ID = 1;
    private static final int BANKNAME_TRACE_ID = 2;

    @BindView(R.id.bank_area)
    RelativeLayout bankArea;
    @BindView(R.id.choose_bank)
    TextView chooseBank;
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.province)
    TextView province;
    @BindView(R.id.city)
    TextView city;
    @BindView(R.id.detailed)
    EditText detailed;
    @BindView(R.id.security_password)
    EditText securityPassword;

    private List<BankOpen> bankOpens=new ArrayList<>();;
    private String[] bankname;
    private View bankView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "添加银行卡", R.layout.addbankcard_setting);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadBankData();
        cardNumber.setFilters(new InputFilter[] { new InputFilter.LengthFilter(25) });
        MyTextWatcher cardnumberWatcher = new MyTextWatcher(cardNumber);
        cardnumberWatcher.setTextWatcherChanging((boolean textbool) -> {

         });
        cardNumber.addTextChangedListener(cardnumberWatcher);

        bankArea.setOnClickListener((v) -> {
           String[] bank=bankname!=null?bankname:new String[0];
            bankView(bank,1);
            bankChoose();
        });
        province.setOnClickListener((v) ->{
            bankView(ConstantInformation.getProvince(),2);
            bankChoose();
        });
        city.setOnClickListener((v) ->{
            String pr=province.getText().toString();
            pr=pr.equals("省份")?ConstantInformation.getProvince()[0]:pr;
            bankView(ConstantInformation.getCityList(pr),3);
            bankChoose();
        });
    }

    private void bankView(String[] data,int type){
        bankView = LayoutInflater.from(getActivity()).inflate(R.layout.wheel_single_view, null);
        WheelView wv = (WheelView) bankView.findViewById(R.id.bank_wheel_view);
        wv.setOffset(1);
        wv.setItems(Arrays.asList(data));
        wv.setSeletion(0);
        wv.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                switch (type){
                    case 1:
                        chooseBank.setText(item);
                        break;
                    case 2:
                        province.setText(item);
                        break;
                    case 3:
                        city.setText(item);
                        break;
                }
            }
        });
    }

    private void bankChoose(){
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setContentView(bankView);
        builder.setTitle("选择");
        builder.setLayoutSet(DialogLayout.UP_AND_DOWN);
        builder.setPositiveButton("确定", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @OnClick(R.id.submitbank)
    public void submitbank(){
        if(verification()){
            int bankid=0;
            for(int i=0;i<bankOpens.size();i++){
                BankOpen bank=bankOpens.get(i);
                if(bank.getBankName().equals(chooseBank.getText().toString()))
                    bankid=bank.getBankId();
            }
            String cardNo = cardNumber.getText().toString().replace(" ", "");

            String  pFundPassword= DigestUtils.md5Hex(securityPassword.getText().toString());
            pFundPassword= DigestUtils.md5Hex(pFundPassword);

            BindCardCommand command = new BindCardCommand();
            command.setBind_card_id(bankid);
            command.setBind_card_num(cardNo);
            command.setProvince(province.getText().toString());
            command.setCity(city.getText().toString());
            command.setBranch_name(detailed.getText().toString());
            command.setSecpwd(pFundPassword);
            executeCommand(command,restCallback,BINDCARD_TRACE_ID);
        }
    }

    private boolean verification(){
        if (chooseBank.getText().toString().equals("请选开户行")) {
            Toast.makeText(getContext(), "请选开户银行", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(cardNumber.getText().toString())) {
            Toast.makeText(getContext(), "请输入银行卡号", Toast.LENGTH_LONG).show();
            return false;
        }
        String cardNo = cardNumber.getText().toString().replace(" ", "");
        String isBankCard = CheckBankNumber.luhmCheck(cardNo);

        if (!isBankCard.equals("true")) {
            Toast.makeText(getActivity(), isBankCard, Toast.LENGTH_LONG).show();
            return false;
        }

        if (province.getText().toString().equals("省份")){
            Toast.makeText(getActivity(), "请选择省份", Toast.LENGTH_LONG).show();
            return false;
        }

        if (city.getText().toString().equals("城市")) {
            Toast.makeText(getContext(), "请选择城市", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(detailed.getText().toString())) {
            Toast.makeText(getContext(), "请输入开户支行", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(securityPassword.getText().toString())) {
            Toast.makeText(getContext(), "请输入资金密码", Toast.LENGTH_LONG).show();
            return false;
        }

        //长度6~20，不能只有数字也不能只有字母
        if (securityPassword.getText().toString().matches("^[a-zA-Z0-9]{6,20}$")) {
            Toast.makeText(getActivity(), "资金密码长度为6~15，不能只有数字也不能只有字母", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loadBankData() {
        BankOpenCommand command = new BankOpenCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<BankOpen>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BANKNAME_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            bankOpens=(ArrayList)restResponse.getData();
            bankData();
        }
        restRequest.execute();
    }

    private void bankData(){
        bankname=new String[bankOpens.size()];
        for(int i=0,size=bankOpens.size();i<size; i++){
            BankOpen b = bankOpens.get(i);
            bankname[i]=b.getBankName();
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if(request.getId()==BINDCARD_TRACE_ID){
                launchFragment(BankCardSetting.class);
                getActivity().finish();
            }else if(request.getId()==BANKNAME_TRACE_ID){
                if (response.getData() instanceof ArrayList) {
                    bankOpens=(ArrayList)response.getData();
                    bankData();
                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };
}
