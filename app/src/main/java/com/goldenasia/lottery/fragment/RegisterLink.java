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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/2.
 */
public class RegisterLink extends BaseFragment
{
    private static final String TAG = RegisterLink.class.getSimpleName();
    
    @BindView(R.id.proxy)
    RadioButton proxy;
    @BindView(R.id.user)
    RadioButton user;
    @BindView(R.id.testuser)
    RadioButton testUser;
    @BindView(R.id.user_type)
    RadioGroup userType;
    @BindView(R.id.channel)
    EditText channel;
    
    private Register registerData;
    private UserInfo userInfo;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflateView(inflater, container, true, "注册下级", R.layout.register_link);
        ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        addMenuItem("管理", new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                launchFragment(LinkManagement.class);
            }
        });
        userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();
        if (userInfo.getLevel() != 1)
        {
            testUser.setVisibility(View.GONE);
        }
        userType.setOnCheckedChangeListener((group, checkedId) ->
        {
            switch (group.getCheckedRadioButtonId())
            {
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
    
    private void init()
    {
        registerData = new Register();
        user.setChecked(true);
        //registerData.setType(1);
        userType.check(R.id.proxy);
    }
    
    @OnClick({R.id.rebates_settingbut})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.rebates_settingbut:
                if (verification())
                {
                    registerData.setChannel(channel.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("reg", registerData);
                    bundle.putString("openType", "link");
                    launchFragmentForResult(LowerRebateSetting.class, bundle, 1);
                }
                break;
        }
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode)
        {
            case ConstantInformation.REFRESH_RESULT:
                getActivity().setResult(ConstantInformation.REFRESH_RESULT);
                getActivity().finish();
        }
    }
    
    private boolean verification()
    {
        if (TextUtils.isEmpty(channel.getText().toString()))
        {
            Toast.makeText(getContext(), "请输入渠道", Toast.LENGTH_LONG).show();
            return false;
        }
        
        return true;
    }
    
    @Override
    public void onResume()
    {
        init();
        super.onResume();
    }
    
    @Override
    public void onDestroyView()
    {
        getActivity().finish();
        super.onDestroyView();
    }
}
