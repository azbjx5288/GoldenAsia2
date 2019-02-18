package com.goldenasia.lottery.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.ContainerActivity;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.LoginCommand;
import com.goldenasia.lottery.data.ServiceSystemBean;
import com.goldenasia.lottery.data.ServiceSystemCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.VersionChecker;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.view.adapter.CustometServiceAdapter;
import com.gyf.barlibrary.ImmersionBar;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @BindView(R.id.checkbox_save)
    CheckBox save;
    @BindView(R.id.login_login_btn)
    Button loginBtn;

    private static final int SERVICE_SYSTEM = 4;

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_golden_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new VersionChecker(getActivity()).startCheck();
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
        save.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.ACCOUNT_INFO, "checkboxSave", isChecked);
            }
        });
        save.setChecked(SharedPreferencesUtils.getBoolean(getActivity(), ConstantInformation.ACCOUNT_INFO, "checkboxSave", false));
    }

    @OnClick({R.id.login_login_btn, R.id.login_account_edit_clear, R.id.login_password_edit_clear, R.id.forget_password, R.id.contact_customer_service})
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
                contactCustomerService();
                break;
        }
    }

    //===================6.0运行时权限需要添加的 start==========================================================//
    private void contactCustomerService() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
            executeCommand(new ServiceSystemCommand(), restCallback2,
                    SERVICE_SYSTEM);
        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)
            ) {
                showDialog01();
            } else {
                showDialog02();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                executeCommand(new ServiceSystemCommand(), restCallback2,
                        SERVICE_SYSTEM);
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //===================6.0运行时权限需要添加的 end==========================================================//

    private void login() {
        if (loginBtn.isEnabled()) {
            loginBtn.setEnabled(false);
        } else {
            return;
        }
        Log.i(TAG, "login()");
        LoginCommand command = new LoginCommand();
        // 2016/9/14
        account = userName.getText().toString();
        pass = DigestUtils.md5Hex(password.getText().toString());
        if (save.isChecked()) {
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "userName", account);
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "password", password
                    .getText().toString());
        } else {
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "userName", "");
            SharedPreferencesUtils.putString(getActivity(), ConstantInformation.ACCOUNT_INFO, "password", "");
        }
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

    private RestCallback restCallback2 = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == SERVICE_SYSTEM) {
                String jsonString = ((JsonString) response.getData()).getJson();
                List<ServiceSystemBean> list = new ArrayList<ServiceSystemBean>();
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ServiceSystemBean serviceSystemBean = new ServiceSystemBean();
                        serviceSystemBean.setName(jsonObject.getString("name"));
                        serviceSystemBean.setUrl(jsonObject.getString("url"));
                        list.add(serviceSystemBean);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (list.size() > 1) {
                    showServiceDialog(list);
                } else if (list.size() == 1) {
                    ServiceSystemBean ServiceSystemBean = list.get(0);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url", ServiceSystemBean.getUrl());
                    launchFragment(ServiceCenterFragment2.class, bundle);
                } else {

                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {

        }
    };

    private void showServiceDialog(List<ServiceSystemBean> list) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.service_dialog, null);
        ListView listView = view.findViewById(R.id.list_view);
        ImageView exit_pressed = view.findViewById(R.id.exit_pressed);
//        List<Map<String, String>> listMap=new ArrayList<Map<String, String>>();
//        // key值数组，适配器通过key值取value，与列表项组件一一对应
//        String[] from = { "name"};
//        for(int i=0;i<list.size();i++){
//            Map<String, String> map=new HashMap<>();
//            map.put("name",list.get(i).getName());
//            listMap.add(map);
//        }
//        // 列表项组件Id 数组
//        int[] to = { R.id.button_name };
//        final SimpleAdapter adapter = new SimpleAdapter(getActivity(), listMap, R.layout.service_dialog_item, from, to);
        CustometServiceAdapter adapter = new CustometServiceAdapter(list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("url", list.get(arg2).getUrl());
                launchFragment(ServiceCenterFragment2.class, bundle);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final AlertDialog dialog = builder.create();

        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        exit_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


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

        String userNameReg = "^[A-Za-z0-9_]+$";//英文和数字
        Pattern pAll = Pattern.compile(userNameReg);
        Matcher mAll = pAll.matcher(userName.getText().toString());

        if (!mAll.matches()) {
            Toast.makeText(getContext(), "用户名只能是字母或者数字或者下划线", Toast.LENGTH_LONG).show();
            return false;
        }
        if (userName.getText().toString().length() > 16 || userName.getText().toString().length() < 5) {
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
    public void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).statusBarColor(R.color.login_in_background).statusBarColorTransformEnable(false).keyboardEnable(true).init();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
