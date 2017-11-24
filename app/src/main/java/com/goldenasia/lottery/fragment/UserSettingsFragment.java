package com.goldenasia.lottery.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.LogoutCommand;
import com.goldenasia.lottery.db.MmcWinHistoryDao;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.pattern.VersionChecker;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gan on 2017/10/11.
 */

public class UserSettingsFragment extends BaseFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "快速充值", R.layout.user_settings_fragment);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.password_setting,R.id.security_setting,R.id.card_setting,R.id.push_notification})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_setting:
                ArrayList<Class> passwors = new ArrayList();
                passwors.add(LoginPasswordSetting.class);
                passwors.add(CashPasswordSetting.class);
                TwoTableFragment.launch(getActivity(), "密码设置", new String[]{"登录密码", "资金密码"}, passwors);
                break;
            case R.id.security_setting:
                launchFragment(SecuritySetting.class);
                break;
            case R.id.card_setting:
                launchFragment(BankCardSetting.class);
                break;
            case R.id.push_notification:
                new VersionChecker(this).startCheck(true);
                break;


            default:
                break;
        }
    }


}
