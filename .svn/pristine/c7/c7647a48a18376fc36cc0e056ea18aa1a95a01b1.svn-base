package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.LotteriesHistory;
import com.goldenasia.lottery.data.LotteriesHistoryCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryListCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.HistoryLotteryAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created on 2016/1/19.
 *
 * @author ACE
 * @功能描述: 历史开奖
 */
public class FragmentLotteryTrend extends BaseFragment {
    private static final String TAG = FragmentLotteryTrend.class.getSimpleName();

    private static final int LOTTERY_HISTORY_TRACE_ID = 1;
    private static final int LOTTERY_TRACE_ID = 2;

    @Bind(R.id.refresh_history_listview)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.history_lottery_listview)
    ListView historyLV;

    private List<List<LotteriesHistory>> historyList;
    private HistoryLotteryAdapter historyLotteryAdapter;
    private ArrayList<Lottery> lotteryList;
    private boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "开奖走势", R.layout.fragment_lotterytrend);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lotteryListLoad();
        historyLotteryAdapter = new HistoryLotteryAdapter();
        refreshLayout.setRefreshing(true);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(this::LotteriesHistoryLoad);
        historyLV.setAdapter(historyLotteryAdapter);

        historyLotteryAdapter.setClickListener(new HistoryLotteryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, String cname, boolean flag) {
                if (lotteryList != null && lotteryList.size() > 0) {
                    Lottery lottery = null;
                    for (Lottery l : lotteryList) {
                        if (l.getCname().equals(cname))
                            lottery = l;
                    }

                    if (lottery != null) {
                        if (flag) {
                            /*if (lottery.getLotteryId() == 17 || lottery.getLotteryId() == 26)
                                GameLhcFragment.launch(FragmentLotteryTrend.this, lottery);
                            else {
//                                GameFragment.launch(FragmentLotteryTrend.this, lottery);
                            }*/
                        } else {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("lottery", lottery);
                            launchFragment(HistoryCodeFragment.class, bundle);
                        }
                    }
                }
            }
        });

        LotteriesHistoryLoad();
    }

    private void LotteriesHistoryLoad() {
        LotteriesHistoryCommand lotteryListCommand = new LotteriesHistoryCommand();
        TypeToken typeToken = new TypeToken<RestResponse<List<List<LotteriesHistory>>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), lotteryListCommand, typeToken,
                restCallback, LOTTERY_HISTORY_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            historyList = (List<List<LotteriesHistory>>) restResponse.getData();
            historyLotteryAdapter.refresh(historyList);
        } else
            historyLotteryAdapter.refresh(null);
        restRequest.execute();
    }

    private void lotteryListLoad() {
        LotteryListCommand command = new LotteryListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Lottery>>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, LOTTERY_TRACE_ID, this);
    }

    private RestCallback restCallback = new RestCallback() {

        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case LOTTERY_HISTORY_TRACE_ID:
                    historyList = (List<List<LotteriesHistory>>) response.getData();
                    historyLotteryAdapter.refresh(historyList);
                    break;
                case LOTTERY_TRACE_ID:
                    lotteryList = (ArrayList<Lottery>) response.getData();
                    break;
            }
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

            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
            isLoading = state == RestRequest.RUNNING;

        }
    };
}