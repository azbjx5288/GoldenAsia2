package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.ContainerActivity;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.material.ConstantInformation;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sakura on 2017/5/10.
 */

public class Splash3Fragment extends BaseFragment {
    private static final int REQUEST_CODE = 1001;
    /**
     * 在cache与BuildConfig.VERSION_CODE版本不一致时，需要重新登录
     */
    private static Boolean isSameVersion;

    @BindView(R.id.enter)
    TextView enter;

    public TextView getEnter() {
        return enter;
    }

    public void setEnter(TextView enter) {
        this.enter = enter;
    }

    private OnEnterListner onEnterListner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_splash3, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface OnEnterListner {
        void onEnter();
    }

    public void setOnEnterListner(OnEnterListner onEnterListner) {
        this.onEnterListner = onEnterListner;
    }

    public boolean isInSameVersion() {
        if (isSameVersion == null) {
            isSameVersion = Preferences.getInt(getActivity(), ConstantInformation.APP_INFO, 0) == BuildConfig.VERSION_CODE;
        }
        return isSameVersion;
    }

    private void skip() {
        if (!isInSameVersion()) {
            GoldenAsiaApp.getUserCentre().logout();
            RestRequestManager.cancelAll();

            Preferences.saveInt(getActivity(), ConstantInformation.APP_INFO, BuildConfig.VERSION_CODE);
            isSameVersion = true;
        }

        if (GoldenAsiaApp.getUserCentre().isLogin()) {
            startActivityForResult(new Intent(getActivity(), ContainerActivity.class), REQUEST_CODE);
        } else {
            FragmentLauncher.launchForResult(getActivity(), GoldenLoginFragment.class.getName(), null, REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getActivity().finish();
    }

    @OnClick(R.id.enter)
    public void onViewClicked() {
        skip();
    }
}
