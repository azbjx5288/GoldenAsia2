package com.goldenasia.lottery.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.FindPasswordResponse;
import com.goldenasia.lottery.data.ForgetPwdStep1Command;
import com.goldenasia.lottery.data.ServiceSystemBean;
import com.goldenasia.lottery.data.ServiceSystemCommand;
import com.goldenasia.lottery.util.DigestUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 找回密码
 */

public class GoldenFindPasswordFragment extends BaseFragment {
    private static final String TAG = GoldenFindPasswordFragment.class.getSimpleName();

    @BindView(R.id.find_edit_account)
    EditText mUserName;
    @BindView(R.id.fund_password)
    EditText mPassword;
    @BindView(R.id.find_password_submit)
    Button mFindPasswordSubmit;

    private static final int SERVICE_SYSTEM = 4;

    private  final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "找回登录密码", R.layout.fragment_find_password);
        ButterKnife.bind(this, view);
        return view;
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
                contactCustomerService();
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

    private RestCallback restCallback2 = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            if(request.getId() ==SERVICE_SYSTEM){
                String jsonString= ((JsonString) response.getData()).getJson();
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
                showServiceDialog(list);
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {

        }
    };

    private void showServiceDialog( List<ServiceSystemBean> list){
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.service_dialog, null);
        ListView listView=view.findViewById(R.id.list_view);
        ImageView exit_pressed=view.findViewById(R.id.exit_pressed);
        List<Map<String, String>> listMap=new ArrayList<Map<String, String>>();
        // key值数组，适配器通过key值取value，与列表项组件一一对应
        String[] from = { "name"};
        for(int i=0;i<list.size();i++){
            Map<String, String> map=new HashMap<>();
            map.put("name",list.get(i).getName());
            listMap.add(map);
        }
        // 列表项组件Id 数组
        int[] to = { R.id.button_name };
        final SimpleAdapter adapter = new SimpleAdapter(getActivity(), listMap, R.layout.service_dialog_item, from, to);
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

    //===================6.0运行时权限需要添加的 start==========================================================//
    private void contactCustomerService(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)
        {
            executeCommand(new ServiceSystemCommand(), restCallback2,
                    SERVICE_SYSTEM);
        }else {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                executeCommand(new ServiceSystemCommand(), restCallback2,
                        SERVICE_SYSTEM);
            } else
            {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //===================6.0运行时权限需要添加的 end==========================================================//

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
