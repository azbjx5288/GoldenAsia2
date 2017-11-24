package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.data.Bet;

import butterknife.OnClick;

public class FragmentHistory extends BaseFragment{
    private static final String TAG = FragmentHistory.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "游戏记录", R.layout.fragment_history);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @OnClick({R.id.bet_history, R.id.trace_history, R.id.balance_details,R.id.ga_history})
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

            default:
                break;
        }
    }

}