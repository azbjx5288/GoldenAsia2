package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
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
import com.goldenasia.lottery.data.BindCardDetail;
import com.goldenasia.lottery.data.BindCardDetailCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.BankCardAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class BankCardSetting extends BaseFragment {
    private static final int BANKCARD_TRACE_ID = 1;

    @Bind(R.id.bindcard_list)
    ListView bindcardList;
    @Bind(R.id.perfectbut)
    Button perfectbut;
    @Bind(R.id.tip)
    TextView tip;

    private List items = new ArrayList();
    private BankCardAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "银行卡管理", R.layout.bankcard_setting);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new BankCardAdapter(items);
        bindcardList.setAdapter(adapter);
        TextView  textView= new TextView(getActivity());
        textView.setText("如需解绑银行卡，请联系在线客服");
        textView.setGravity(Gravity.CENTER);
        bindcardList.addFooterView(textView);
    }

    @Override
    public void onResume() {
        init();
        super.onResume();
    }

    private void init(){
        if(TextUtils.isEmpty(GoldenAsiaApp.getUserCentre().getUserInfo().getRealName())){
            tip.setVisibility(View.VISIBLE);
            perfectbut.setVisibility(View.VISIBLE);
        }
        loadData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.add_bankcardbut,R.id.perfectbut})
    public void operate(View v){
        switch (v.getId()){
            case R.id.add_bankcardbut:
                launchFragment(AddBankCard.class);
                break;
            case R.id.perfectbut:
                launchFragment(SecuritySetting.class);
                break;
        }
        getActivity().finish();
    }

    private void loadData() {
        BindCardDetailCommand command = new BindCardDetailCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<BindCardDetail>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, BANKCARD_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            items = (ArrayList) restResponse.getData();
            adapter.setData(items);
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId()==BANKCARD_TRACE_ID) {
                items = (ArrayList) response.getData();
                adapter.setData(items);
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
