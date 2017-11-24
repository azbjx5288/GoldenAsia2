package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.ForgetPwdStep1Command;
import com.goldenasia.lottery.data.FindPasswordResponse;
import com.goldenasia.lottery.util.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 找回密码
 */

public class GoldenFindPasswordFragment extends BaseFragment {
    private static final String TAG = GoldenFindPasswordFragment.class.getSimpleName();

    @Bind(R.id.find_edit_account)
    EditText mUserName;
    @Bind(R.id.fund_password)
    EditText mPassword;
    @Bind(R.id.find_password_submit)
    Button mFindPasswordSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_password, container, false);
        ButterKnife.bind(this, view);
        return inflateView(inflater, container, "找回密码", R.layout.fragment_find_password);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick({R.id.find_password_submit,R.id.contact_customer_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find_password_submit: //提交
                submit();
                break;
            case R.id.contact_customer_service: //联系客服
                launchFragment(ServiceCenterFragment.class);
                break;
        }
    }

    private void submit() {
        if (!check()) {
            return;
        }
        login();
    }

    private void login() {
        mFindPasswordSubmit.setEnabled(false);
        ForgetPwdStep1Command command = new ForgetPwdStep1Command();

        command.setUsername(mUserName.getText().toString());
        command.setEncsecpwd(DigestUtils.md5Hex(mPassword.getText().toString().getBytes()));
        command.setStep("1");
        command.setFrm(4);
        executeCommand(command, restCallback);
    }

    private boolean check() {
        String name = mUserName.getText().toString();
        String password = mPassword.getText().toString();

        if (name.isEmpty()) {
            showToast("请输入用户名", Toast.LENGTH_SHORT);
            return false;
        }

        if (password.isEmpty()) {
            showToast("请输入资金密码", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    private RestCallback restCallback = new RestCallback<FindPasswordResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<FindPasswordResponse> response) {
            Bundle bundle = new Bundle();
            bundle.putString("nvpair", response.getData().getNvpair());
            launchFragment(GoldenResetPasswordFragment.class,bundle);
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                showProgress("用户名 资金密码校验中");
            } else {
                hideProgress();
            }
            mFindPasswordSubmit.setEnabled(true);
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
