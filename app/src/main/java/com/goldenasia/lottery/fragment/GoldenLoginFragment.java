package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.ContainerActivity;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.LoginCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.VersionChecker;
import com.goldenasia.lottery.util.SharedPreferencesUtils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 登录页面
 * Created on 2015/12/31.
 *
 * @author ACE
 */

public class GoldenLoginFragment extends BaseFragment {
    private static final String TAG = GoldenLoginFragment.class.getSimpleName();
    private String account;
    private String pass;

    @BindView(R.id.login_edit_account)
    EditText userName;
    @BindView(R.id.login_edit_password)
    EditText password;
   /* @BindView(R.id.login_account_edit_clear)
    View userNameClear;
    @BindView(R.id.login_password_edit_clear)
    View passwordClear;*/
    @BindView(R.id.save)
    CheckBox save;
    @BindView(R.id.login_login_btn)
    Button loginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_golden_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new VersionChecker(this).startCheck();
        account = SharedPreferencesUtils.getString(getActivity(), ConstantInformation.ACCOUNT_INFO, "userName");
        pass = SharedPreferencesUtils.getString(getActivity(), ConstantInformation.ACCOUNT_INFO, "password");
        if ("".equals(account) && "".equals(pass)) {
            /*userNameClear.setVisibility(View.INVISIBLE);
            passwordClear.setVisibility(View.INVISIBLE);*/
        } else {
            userName.setText(account);
            password.setText(pass);
        }
        userName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (userName.getText().toString().length() > 0) {
                    userNameClear.setVisibility(View.VISIBLE);
                } else {
                    userNameClear.setVisibility(View.INVISIBLE);
                }*/
            }
        });

        userName.setOnEditorActionListener((v, actionId, event) -> false);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                /*if (password.getText().toString().length() > 0) {
                    passwordClear.setVisibility(View.VISIBLE);
                } else {
                    passwordClear.setVisibility(View.INVISIBLE);
                }*/
            }
        });

        password.setOnEditorActionListener((v, actionId, event) -> {
            if (checkUserInfo()) {
                login();
            }
            return false;
        });

        String name = GoldenAsiaApp.getUserCentre().getUserName();
        if (!TextUtils.isEmpty(name)) {
            userName.setText(name);
            password.requestFocus();
        }
    }

    @OnClick({R.id.login_login_btn, R.id.login_account_edit_clear, R.id.login_password_edit_clear,R.id.forget_password,R.id.contact_customer_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_account_edit_clear: //帐号清空 点帐号清空时默认清空密码
                userName.setText(null);
                password.setText(null);
                userName.requestFocus();
                break;
            case R.id.login_password_edit_clear://密码清空
                password.setText(null);
                password.requestFocus();
                break;
            case R.id.login_login_btn: //帐号登录
                if (checkUserInfo()) {
                    login();
                }
                break;
            case R.id.forget_password: //忘记密码
                launchFragment(GoldenFindPasswordFragment.class);
                break;
            case R.id.contact_customer_service: //联系客服
                launchFragment(ServiceCenterFragment.class);
                break;
        }
    }

    private void login() {
        if(loginBtn.isEnabled()){
            loginBtn.setEnabled(false);
        }else{
            return;
        }
        Log.i(TAG, "login()");
        LoginCommand command = new LoginCommand();
        // 2016/9/14
        account = userName.getText().toString();
        pass = DigestUtils.md5Hex(password.getText().toString());
        /*if (save.isChecked())
        {
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "userName", account);
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "password", password
                    .getText().toString());
        } else
        {
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "userName", "");
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "password", "");
        }*/
        command.setUsername(account);
        command.setEncpassword(pass);
        command.setVersion(BuildConfig.VERSION_CODE);
        executeCommand(command, restCallback);
    }

    private RestCallback restCallback = new RestCallback<UserInfo>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<UserInfo> response) {
            Log.i(TAG, "onRestComplete(RestRequest request, RestResponse<UserInfo> response)");
            loginBtn.setEnabled(true);
            startActivity(new Intent(getActivity(), ContainerActivity.class));
            getActivity().finish();
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            Log.i(TAG, "onRestError(RestRequest request, int errCode, String errDesc)");
            loginBtn.setEnabled(true);
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            Log.i(TAG, "onRestStateChanged(RestRequest request, @RestRequest.RestState int state)");
            if (state == RestRequest.RUNNING) {
                showProgress("登录中");
            } else {
                hideProgress();
            }

        }
    };

    /**
     * 用户信息验证
     */
    private boolean checkUserInfo() {
        if (TextUtils.isEmpty(userName.getText().toString())) {
            Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!(userName.getText().toString().charAt(0) <= 'Z' && userName.getText().toString().charAt(0) >= 'A'
                || userName.getText().toString().charAt(0) <= 'z' && userName.getText().toString().charAt(0) >=
                'a')) {
            Toast.makeText(getContext(), "用户名必须以字母开头", Toast.LENGTH_LONG).show();
            return false;
        }

        String userNameReg="^[A-Za-z0-9_]+$";//英文和数字
        Pattern pAll= Pattern.compile(userNameReg);
        Matcher mAll = pAll.matcher(userName.getText().toString());

        if (!mAll.matches()) {
            Toast.makeText(getContext(), "用户名只能是字母或者数字或者下划线", Toast.LENGTH_LONG).show();
            return false;
        }
        if(userName.getText().toString().length()>16||userName.getText().toString().length()<5){
            Toast.makeText(getContext(), "用户名只能是长度为5-16位", Toast.LENGTH_LONG).show();
            return false;
        }
        if (TextUtils.isEmpty(password.getText().toString())) {
            if (BuildConfig.DEBUG) {
                //测试版本时，自动填写密码为"a123456"
                password.setText("a123456");
                return true;
            }
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
