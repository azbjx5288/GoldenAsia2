package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.LotteriesHistoryCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryHistoryCode;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.HistoryCodeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by ACE-PC on 2016/3/7.
 */
public class ResultsFragment extends BaseFragment {

    private static final String TAG = HistoryCodeFragment.class.getSimpleName();

    private static final int LIST_HISTORY_CODE_ID = 1;

    @Bind(R.id.refresh_history_listviewcode)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.history_lottery_listviewcode)
    ListView listView;

    private static final int FIRST_PAGE = 1;

    private HistoryCodeAdapter adapter;
    private Lottery lottery;

    private List items = new ArrayList();
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        adapter = new HistoryCodeAdapter(items);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadCodeList(false, FIRST_PAGE));

        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getCount() != 0 && items.size() < totalCount && listView.getLastVisiblePosition() >= (listView.getCount() - 1) - endTrigger) {
                    loadCodeList(false, page + 1);
                }
            }
        });
        listView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
        isLoading = false;
        loadCodeList(true, FIRST_PAGE);
    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    private void loadCodeList(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        LotteriesHistoryCommand command = new LotteriesHistoryCommand();
        command.setLotteryID(lottery.getLotteryId());
        command.setCurPage(this.page);
        TypeToken typeToken = new TypeToken<RestResponse<LotteryHistoryCode>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, LIST_HISTORY_CODE_ID, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof LotteryHistoryCode) {
                items.addAll(((LotteryHistoryCode) restResponse.getData()).getIssue());
                totalCount = items.size();
                adapter.setData(items);
            } else {
                adapter.setData(null);
            }
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == LIST_HISTORY_CODE_ID) {
                if (response.getData() == null || !(response.getData() instanceof LotteryHistoryCode)) {
                    items.clear();
                } else {
                    totalCount = ((LotteryHistoryCode) response.getData()).getTotalNum();
                    if (page == FIRST_PAGE) {
                        items.clear();
                    }
                    items.addAll(((LotteryHistoryCode) response.getData()).getIssue());
                }
                adapter.setData(items);
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog=PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (request.getId() == LIST_HISTORY_CODE_ID){
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };
}
