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
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.ForgetPwdStep2Command;
import com.goldenasia.lottery.data.LowerMemberList;
import com.goldenasia.lottery.data.ServiceSystemBean;
import com.goldenasia.lottery.data.ServiceSystemCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.view.adapter.CustometServiceAdapter;

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


public class GoldenResetPasswordFragment extends BaseFragment {
    private static final String TAG = GoldenResetPasswordFragment.class.getSimpleName();

    private String nvpair;

    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.password2)
    EditText password2;
    @BindView(R.id.reset_password_submit)
    Button mResetPasswordSubmit;

    private  final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflateView(inflater, container, "重置登录密码", R.layout.fragment_reset_password);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
    }

    private void applyArguments() {
        nvpair = getArguments().getString("nvpair");
    }

    @OnClick({R.id.reset_password_submit,R.id.contact_customer_service})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password_submit: //提交
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

        mResetPasswordSubmit.setEnabled(false);

        ForgetPwdStep2Command command = new ForgetPwdStep2Command();

        command.setPassword(password.getText().toString());
        command.setPassword2(password2.getText().toString());
        command.setNvpair(nvpair);
        command.setStep("2");
        executeCommand(command, restCallback,1);
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
//            tipDialog( "您的登录密码已重置为："+password.getText().toString());

            if (request.getId() == 1)
            {
                tipDialog( "登录密码重置成功！");
            } else if (request.getId() == 2)
            {
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

                if(list.size()>1){
                     showServiceDialog(list);
                }else if(list.size()==1){
                    ServiceSystemBean  ServiceSystemBean=list.get(0);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url", ServiceSystemBean.getUrl());
                    launchFragment(ServiceCenterFragment2.class, bundle);
                }else{

                }
            }
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

    private void showServiceDialog( List<ServiceSystemBean> list){
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.service_dialog, null);
        ListView listView=view.findViewById(R.id.list_view);
        ImageView exit_pressed=view.findViewById(R.id.exit_pressed);
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
        CustometServiceAdapter adapter=new CustometServiceAdapter(list);
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
            executeCommand(new ServiceSystemCommand(), restCallback,
                    2);
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
                executeCommand(new ServiceSystemCommand(), restCallback,
                        2);
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
