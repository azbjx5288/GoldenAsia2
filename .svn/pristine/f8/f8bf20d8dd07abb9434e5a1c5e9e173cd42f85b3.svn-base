package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.GameReportCommand;
import com.goldenasia.lottery.data.GameReportResponse;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/31.
 * 游戏报表
 */

public class GameReportSubFragment extends BaseFragment {
    private final int LOAD_LIST = 0;//报表数据查询

    private boolean isFirstTime = true;

    private GameReportResponse mGameReportResponse;

    private int mCurrentTime = 4;

    private GameReportMainFragment mGameReportMainFragment;

    @BindView(R.id.tv_1)
    TextView tv_1;
    @BindView(R.id.tv_2)
    TextView tv_2;
    @BindView(R.id.tv_3)
    TextView tv_3;
    @BindView(R.id.tv_4)
    TextView tv_4;
    @BindView(R.id.tv_5)
    TextView tv_5;
    @BindView(R.id.tv_6)
    TextView tv_6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.game_report_sub_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCurrentTime = getArguments().getInt("key");

        loadDate();

        isFirstTime = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentLauncher fragmentLauncher = (FragmentLauncher) getActivity();
        mGameReportMainFragment = (GameReportMainFragment) fragmentLauncher.getfragmentCaller();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (mGameReportMainFragment == null) {
            return;
        }
        if (mGameReportResponse == null) {
            return;
        }
        //相当于Fragment的onResume
        if (isVisibleToUser && !isFirstTime) {

            refreshSubLayout();

            refreshHeadView();
        } else {
            //相当于Fragment的onPause

        }
    }

    private void loadDate() {
        GameReportCommand command = new GameReportCommand();

        switch (mCurrentTime) {
            case 0://今天
                command.setStartDate(TimeUtils.getBeginStringOfToday());
                command.setEndDate(TimeUtils.getEndStringOfToday());
                break;
            case 1://昨天
                command.setStartDate(TimeUtils.getBeginStringOfYesterday());
                command.setEndDate(TimeUtils.getEndStringOfYesterday());
                break;
            case 2://近三天
                command.setStartDate(TimeUtils.getLatelyStringOfThree());
                command.setEndDate(TimeUtils.getEndStringOfToday());
                break;
            case 3://近七天
                command.setStartDate(TimeUtils.getLatelyStringOfSeven());
                command.setEndDate(TimeUtils.getEndStringOfToday());
                break;
            case 4://近15天
                command.setStartDate(TimeUtils.getLatelyStringOf15());
                command.setEndDate(TimeUtils.getEndStringOfToday());
                break;
            case 5://当月
                command.setStartDate(TimeUtils.getStringBeginDayOfCurrentMonth());
                command.setEndDate(TimeUtils.getEndStringOfToday());
                break;
            case 6://近35天
                command.setStartDate(TimeUtils.getLatelyStringOf35());
                command.setEndDate(TimeUtils.getEndStringOfToday());
                break;
        }

        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback, LOAD_LIST, this);

        restRequest.execute();
    }

    public void changePlatForm(int platformType) {
        switch (platformType) {
            case 1:
                refreshSubLayout01();
                refreshHeadView01();
                break;
            case 2:
                refreshSubLayout02();
                refreshHeadView02();
                break;
            case 3:
                refreshSubLayout03();
                refreshHeadView03();
                break;
            default:
        }
    }

    private void refreshSubLayout() {
        int platformType = 1;
        if (mGameReportMainFragment != null) {
            platformType = mGameReportMainFragment.getPlatformType();
        }
        switch (platformType) {
            case 1:
                refreshSubLayout01();
                break;
            case 2:
                refreshSubLayout02();
                break;
            case 3:
                refreshSubLayout03();
                break;
            default:
        }
    }

    //全部平台listview
    private void refreshSubLayout01() {
        //不分平台的
        tv_1.setText(mGameReportResponse.getUser_balance());
        tv_3.setText(String.valueOf(mGameReportResponse.getTotal_deposit()));
        tv_5.setText(String.valueOf(mGameReportResponse.getTotal_withdraw()));
        //分平台的
        tv_2.setText(String.valueOf(mGameReportResponse.getTotal_amount() + mGameReportResponse.getGa_buy_amount()));
        tv_4.setText(String.valueOf(mGameReportResponse.getTotal_prize() + mGameReportResponse.getGa_prize()));
        tv_6.setText(String.valueOf(mGameReportResponse.getTotal_rebate() + mGameReportResponse.getGa_rebate()));
    }

    //彩票平台listview
    private void refreshSubLayout02() {
        //不分平台的
        tv_1.setText(mGameReportResponse.getUser_balance());
        tv_3.setText(String.valueOf(mGameReportResponse.getTotal_deposit()));
        tv_5.setText(String.valueOf(mGameReportResponse.getTotal_withdraw()));
        //分平台的
        tv_2.setText(String.valueOf(mGameReportResponse.getTotal_amount()));
        tv_4.setText(String.valueOf(mGameReportResponse.getTotal_prize()));
        tv_6.setText(String.valueOf(mGameReportResponse.getTotal_rebate()));
    }

    //GA游戏平台listview
    private void refreshSubLayout03() {
        //不分平台的
        tv_1.setText(mGameReportResponse.getUser_balance());
        tv_3.setText(String.valueOf(mGameReportResponse.getTotal_deposit()));
        tv_5.setText(String.valueOf(mGameReportResponse.getTotal_withdraw()));
        //分平台的
        tv_2.setText(String.valueOf(mGameReportResponse.getGa_buy_amount()));
        tv_4.setText(String.valueOf(mGameReportResponse.getGa_prize()));
        tv_6.setText(String.valueOf(mGameReportResponse.getGa_rebate()));
    }

    //总盈利显示
    private void refreshHeadView() {
        int platformType = 1;
        if (mGameReportMainFragment != null) {
            platformType = mGameReportMainFragment.getPlatformType();
        }
        switch (platformType) {
            case 1:
                refreshHeadView01();
                break;
            case 2:
                refreshHeadView02();
                break;
            case 3:
                refreshHeadView03();
                break;
            default:
        }
    }

    private void refreshHeadView01() {
        String sumMoney = String.valueOf(mGameReportResponse.getProfit_and_lost() + mGameReportResponse.getGa_game_win());

        HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
        keyMap.put("key", mCurrentTime);
        HashMap<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("value", sumMoney);
        mGameReportMainFragment.changeMoneySumText(keyMap, valueMap);
    }

    private void refreshHeadView02() {
        String sumMoney = String.valueOf(mGameReportResponse.getProfit_and_lost());//彩票总赢利

        HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
        keyMap.put("key", mCurrentTime);
        HashMap<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("value", sumMoney);
        mGameReportMainFragment.changeMoneySumText(keyMap, valueMap);
    }

    private void refreshHeadView03() {
        String sumMoney = String.valueOf(mGameReportResponse.getGa_game_win());//GA盈亏

        HashMap<String, Integer> keyMap = new HashMap<String, Integer>();
        keyMap.put("key", mCurrentTime);
        HashMap<String, String> valueMap = new HashMap<String, String>();
        valueMap.put("value", sumMoney);
        mGameReportMainFragment.changeMoneySumText(keyMap, valueMap);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            JsonString listString = (JsonString) response.getData();

            mGameReportResponse = GsonHelper.fromJson(listString.getJson(), GameReportResponse.class);

            refreshSubLayout();

            refreshHeadView();

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
