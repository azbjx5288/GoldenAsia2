package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.data.UserInfo;

import butterknife.BindView;
import butterknife.OnClick;

public class FragmentHistory extends BaseFragment{
    private static final String TAG = FragmentHistory.class.getSimpleName();

    @BindView(R.id.game_reports)
    RelativeLayout game_reports;

    @BindView(R.id.game_reports_view)
    View game_reports_view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "游戏记录", R.layout.fragment_history);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UserInfo userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();

        if (userInfo.getLevel() == 10) {  //非代理账号显示
            game_reports.setVisibility(View.VISIBLE);
            game_reports_view.setVisibility(View.VISIBLE);
        } else {
            game_reports.setVisibility(View.GONE);
            game_reports_view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.bet_history, R.id.trace_history, R.id.balance_details,R.id.ga_history,R.id.game_reports})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bet_history:
                FragmentHistoryBetOrTrace.launchBet(this);
                break;
            case R.id.trace_history:
                FragmentHistoryBetOrTrace.launchTrace(this);
                break;

            case R.id.balance_details://资金明细
                launchFragment(BalanceTableFragment.class);
                break;

            case R.id.ga_history://GA记录
                launchFragment(FragmentHistoryGA.class);
                break;
            case R.id.game_reports://游戏报表
                GameReportMainFragment.launch(this);
                break;

            default:
                break;
        }
    }

}