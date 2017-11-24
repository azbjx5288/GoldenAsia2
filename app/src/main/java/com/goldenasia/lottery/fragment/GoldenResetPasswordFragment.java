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
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.ForgetPwdStep2Command;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GoldenResetPasswordFragment extends BaseFragment {
    private static final String TAG = GoldenResetPasswordFragment.class.getSimpleName();

    private String nvpair;

    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.password2)
    EditText password2;
    @Bind(R.id.reset_password_submit)
    Button mResetPasswordSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        ButterKnife.bind(this, view);
        return inflateView(inflater, container, "重置密码", R.layout.fragment_reset_password);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applyArguments();
    }

    private void applyArguments() {
        nvpair = (String) getArguments().getSerializable("nvpair");
    }

    @OnClick({R.id.reset_password_submit,R.id.contact_customer_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password_submit: //提交
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

        mResetPasswordSubmit.setEnabled(false);

        ForgetPwdStep2Command command = new ForgetPwdStep2Command();

        command.setPassword(password.getText().toString());
        command.setPassword2(password2.getText().toString());
        command.setNvpair(nvpair);
        command.setStep("2");
        command.setSessionid(GoldenAsiaApp.getUserCentre().getSession());
        executeCommand(command, restCallback);
    }

    private boolean check() {
        String passwordStr = password.getText().toString();
        String password2Str = password2.getText().toString();

        if (passwordStr.isEmpty()) {
            showToast("请输入输入新登录密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (password2Str.isEmpty()) {
            showToast("请输入输入确认新登录密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (passwordStr.length() < 6 || password2Str.length() < 6) {
            showToast("新密码太短，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (passwordStr.length() > 20 || password2Str.length() > 20) {
            showToast("新密码太长，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        if (!passwordStr.equals(password2Str)) {
            showToast("输入的新密码不一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (!passwordStr.matches(regex)) {
            showToast("新密码必须包含数字和字母，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            tipDialog( "您的登录密码已重置为："+password.getText().toString());
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            showToast("登录密码重置失败：" + errDesc, Toast.LENGTH_SHORT);
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                showProgress("正在重置密码.....");
            } else {
                hideProgress();
            }
            mResetPasswordSubmit.setEnabled(true);
        }
    };

    public void tipDialog(String msg) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle("温馨提示");
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("知道了", (dialog, which) -> {
            dialog.dismiss();
            getActivity().finish();
            launchFragment(GoldenLoginFragment.class);
        });
        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
