package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.ChangePasswordCommand;
import com.goldenasia.lottery.data.UserInfo;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 资金密码设置
 * Created by Alashi on 2016/5/2.
 */
public class CashPasswordSetting extends BaseFragment {
    @Bind(R.id.now_password)
    EditText nowPassword;
    @Bind(R.id.new_password)
    EditText newPassword;
    @Bind(R.id.new_password_verify)
    EditText newPasswordVerify;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return inflater.inflate(R.layout.cash_password, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        if (!(check())) {
            return;
        }
        ChangePasswordCommand command = new ChangePasswordCommand();
        command.setSa("modifySecurityPassword");
        command.setOldsecpassword(nowPassword.getText().toString());
        command.setSecpassword(newPassword.getText().toString());
        command.setSecpassword2(newPasswordVerify.getText().toString());

        executeCommand(command, callback);
    }

    private RestCallback callback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            showToast("资金密码修改成功", Toast.LENGTH_SHORT);
            nowPassword.setText("");
            newPassword.setText("");
            newPasswordVerify.setText("");
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            showToast("资金密码修改失败：" + errDesc, Toast.LENGTH_SHORT);
            return true;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                showProgress("正在修改资金密码...");
            } else {
                hideProgress();
            }
        }
    };

    private boolean check() {
        String now = nowPassword.getText().toString();
        String newP = newPassword.getText().toString();
        String newPv = newPasswordVerify.getText().toString();

        if (now.isEmpty()) {
            showToast("请输入当前密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (newP.isEmpty()) {
            showToast("请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (newPv.isEmpty()) {
            showToast("请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (newPv.length() < 6 || newP.length() < 6) {
            showToast("新密码太短，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (newPv.length() > 20 || newP.length() > 20) {
            showToast("新密码太长，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        if (!newP.equals(newPv)) {
            showToast("输入的新密码不一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (now.equals(newP)) {
            showToast("当前密码和新密码一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (!newP.matches(regex)) {
            showToast("新密码必须包含数字和字母，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
