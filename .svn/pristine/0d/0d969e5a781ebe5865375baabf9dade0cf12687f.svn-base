package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.Register;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/2.
 */
public class RegisterSetting extends BaseFragment {
    private static final String TAG = RegisterSetting.class.getSimpleName();

    @BindView(R.id.reg_edituser)
    EditText regEdituser;
    @BindView(R.id.proxy)
    RadioButton proxy;
    @BindView(R.id.user)
    RadioButton user;
    @BindView(R.id.testuser)
    RadioButton testUser;
    @BindView(R.id.user_type)
    RadioGroup userType;
    @BindView(R.id.reg_editpass)
    EditText regEditpass;
    @BindView(R.id.reg_surepass)
    EditText regSurepass;
    @BindView(R.id.nickname)
    EditText nickname;

    private Register registerData;
    private UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflateView(inflater, container, true, "注册下级", R.layout.register_setting);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();
        if (userInfo.getLevel() != 1) {
            testUser.setVisibility(View.GONE);
        }
        userType.setOnCheckedChangeListener((group, checkedId) -> {
            switch (group.getCheckedRadioButtonId()) {
                case R.id.proxy:
                    registerData.setType(1);
                    break;
                case R.id.user:
                    registerData.setType(2);
                    break;
                case R.id.testuser:
                    registerData.setType(3);
                    break;
            }
        });
    }

    private void init() {
        registerData = new Register();
        user.setChecked(true);
        //registerData.setType(1);
        userType.check(R.id.proxy);
    }

    @OnClick({R.id.rebates_settingbut})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rebates_settingbut:
                if (verification()) {
                    registerData.setUsername(regEdituser.getText().toString());
                    registerData.setNickname(nickname.getText().toString());
                    registerData.setPassword(regEditpass.getText().toString());
                    registerData.setPassword2(regSurepass.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reg", registerData);
                    bundle.putString("openType","manual");
                    launchFragmentForResult(LowerRebateSetting.class, bundle, 1);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ConstantInformation.REFRESH_RESULT:
                getActivity().setResult(ConstantInformation.REFRESH_RESULT);
                getActivity().finish();
        }
    }

    private boolean verification() {
        if (TextUtils.isEmpty(regEdituser.getText().toString())) {
            Toast.makeText(getContext(), "请输入用户名", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!(regEdituser.getText().toString().charAt(0) <= 'Z' && regEdituser.getText().toString().charAt(0) >= 'A'
                || regEdituser.getText().toString().charAt(0) <= 'z' && regEdituser.getText().toString().charAt(0) >=
                'a')) {
            Toast.makeText(getContext(), "用户名必须以字母开头", Toast.LENGTH_LONG).show();
            return false;
        }

        String userNameReg="^[A-Za-z0-9_]+$";//英文和数字
        Pattern pAll= Pattern.compile(userNameReg);
        Matcher mAll = pAll.matcher(regEdituser.getText().toString());
        if (!mAll.matches()) {
            Toast.makeText(getContext(), "用户名只能是字母或者数字或者下划线", Toast.LENGTH_LONG).show();
            return false;
        }

        if(regEdituser.getText().toString().length()>16||regEdituser.getText().toString().length()<5){
            Toast.makeText(getContext(), "用户名只能是长度为5-16位", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(nickname.getText().toString())) {
            Toast.makeText(getContext(), "请输入昵称", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(regEditpass.getText().toString())) {
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_LONG).show();
            return false;
        }
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (!regEditpass.getText().toString().matches(regex)) {
            Toast.makeText(getActivity(), "密码格式不正确", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(regSurepass.getText().toString())) {
            Toast.makeText(getContext(), "请输入确认密码", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!regEditpass.getText().toString().equals(regSurepass.getText().toString())) {
            Toast.makeText(getContext(), "密码与确认密码不相同", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        init();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        getActivity().finish();
        super.onDestroyView();
    }
}
