package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.goldenasia.lottery.util.UiUtils;
import com.goldenasia.lottery.view.adapter.HistoryCodeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ACE-PC on 2016/3/7.
 */
public class ResultsFragment extends BaseFragment
{
    private static final String TAG = HistoryCodeFragment.class.getSimpleName();
    
    private static final int LIST_HISTORY_CODE_ID = 1;
    
    @BindView(R.id.refresh_history_listviewcode)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.history_lottery_listviewcode)
    ListView listView;
    
    private static final int FIRST_PAGE = 1;
    
    private HistoryCodeAdapter adapter;
    private Lottery lottery;
    private String methodName = "";
    private View headerView;
    
    private List items = new ArrayList();
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_history_result, container, false);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadCodeList(false, FIRST_PAGE));
        
        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1)
            {
            
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (listView.getCount() != 0 && items.size() < totalCount && listView.getLastVisiblePosition() >=
                        (listView.getCount() - 1) - endTrigger)
                {
                    loadCodeList(false, page + 1);
                }
            }
        });
        initAdapter(methodName);
        refreshLayout.setRefreshing(false);
        isLoading = false;
        loadCodeList(true, FIRST_PAGE);
    }
    
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
        initAdapter(methodName);
    }
    
    private void initAdapter(String methodName)
    {
        if (headerView != null && listView.getHeaderViewsCount() != 0)
            listView.removeHeaderView(headerView);
        adapter = new HistoryCodeAdapter(items, lottery.getLotteryId(), lottery.getLotteryType(), methodName,
                getActivity());
        ArrayList<String> titles = new ArrayList<>();
        switch (methodName)
        {
            case "WXDW":
            case "QEDXDS":
            case "EXDXDS":
            case "QSDXDS":
            case "ZSDXDS":
            case "SXDXDS":
            case "REZX":
            case "RSZX":
            case "RSIZX":
            case "REZUX":
            case "RSZS":
            case "RSZL":
            case "RSHHZX":
            case "YMBDW":
            case "EMBDW":
            case "QSYMBDW":
            case "QSEMBDW":
            case "ZSYMBDW":
            case "ZSEMBDW":
            case "SXYMBDW":
            case "SXEMBDW":
            case "WXYMBDW":
            case "WXEMBDW":
            case "WXSMBDW":
            case "SDQSDWD":
            case "SDQEZX":
            case "SDQEZUX":
            case "SDDTQEZUX":
            case "SDEXZX":
            case "SDEXZUX":
            case "SDDTEXZUX":
            case "SDQSZX":
            case "SDQSZUX":
            case "SDDTQSZUX":
            case "SDSXZX":
            case "SDSXZUX":
            case "SDDTSXZUX":
            case "SDQSBDW":
            case "SDLX2":
            case "SDLX3":
            case "SDLX4":
            case "SDLX5":
            case "SDRX1":
            case "SDRX2":
            case "SDRX3":
            case "SDRX4":
            case "SDRX5":
            case "SDRX6":
            case "SDRX7":
            case "SDRX8":
            case "SDDTRX1":
            case "SDDTRX2":
            case "SDDTRX3":
            case "SDDTRX4":
            case "SDDTRX5":
            case "SDDTRX6":
            case "SDDTRX7":
            case "SDDTRX8":
                titles.add("大小");
                titles.add("单双");
                break;
            case "EXZX":
            case "EXLX":
            case "EXZUX":
            case "EXZUXBD":
            case "EXBD":
            case "EXHZ":
            case "QEZX":
            case "QELX":
            case "QEZUX":
            case "QEZUXBD":
            case "QEBD":
            case "QEHZ":
            case "JSEBT":
            case "JSSBT":
            case "JSHZ":
            case "CYBUC":
            case "CEBUC":
            case "CSBUC":
            case "CYBIC":
            case "CEBIC":
            case "CSBIC":
            case "JSDX":
            case "JSDS":
                titles.add("和值");
                titles.add("大小");
                titles.add("单双");
                break;
            case "EXKD":
            case "QEKD":
            case "QSKD":
            case "ZSKD":
            case "SXKD":
                titles.add("和值");
                titles.add("跨度");
                titles.add("大小");
                break;
            case "QSBD":
            case "ZSBD":
            case "SXBD":
            case "WXHZDXDS":
                titles.add("和值");
                titles.add("和大小");
                titles.add("和单双");
                break;
            case "QSIZX":
            case "SIXZX":
                titles.add("和值");
                titles.add("和尾");
                titles.add("跨度");
                break;
            case "WXHZ":
                titles.add("和值");
                titles.add("和单双");
                titles.add("和尾");
                break;
            case "RELHH":
                titles.add("万\n千");
                titles.add("万\n百");
                titles.add("万\n十");
                titles.add("万\n个");
                titles.add("千\n百");
                titles.add("千\n十");
                titles.add("千\n个");
                titles.add("百\n十");
                titles.add("百\n个");
                titles.add("十\n个");
                break;
            case "NIUNIU":
                titles.add("和值");
                titles.add("牛牛");
                break;
            case "QSZX":
            case "QSLX":
            case "QSZS":
            case "QSZL":
            case "QSHHZX":
            case "QSZUXBD":
            case "QSHZ":
            case "ZSZX":
            case "ZSLX":
            case "ZSZS":
            case "ZSZL":
            case "ZSHHZX":
            case "ZSZUXBD":
            case "ZSHZ":
            case "SXLX":
            case "SXZS":
            case "SXZL":
            case "SXHHZX":
            case "SXZUXBD":
                titles.add("和值");
                titles.add("组选");
                titles.add("大小");
                break;
            case "SXHZ":
                if (lottery.getLotteryId() == 1)
                {
                    titles.add("和值");
                    titles.add("组选");
                    titles.add("大小");
                    break;
                } else
                {
                    titles.add("和值");
                    titles.add("组选");
                    break;
                }
            case "SXZX":
                if (lottery.getLotteryId() == 1)
                {
                    titles.add("和值");
                    titles.add("组选");
                    titles.add("大小");
                    break;
                } else
                {
                    titles.add("和值");
                    titles.add("组选");
                    break;
                }
            case "QSIZUX4":
            case "QSIZUX6":
            case "QSIZUX12":
            case "QSIZUX24":
            case "ZUX4":
            case "ZUX6":
            case "ZUX12":
            case "ZUX24":
                titles.add("和值");
                titles.add("组选");
                titles.add("跨度");
                break;
            case "WXZX":
            case "WXLX":
            case "ZUX5":
            case "ZUX10":
            case "ZUX20":
            case "ZUX30":
            case "ZUX60":
            case "ZUX120":
            case "YFFS":
            case "HSCS":
            case "SXBX":
            case "SJFC":
                titles.add("和值");
                titles.add("跨度");
                titles.add("组选");
                break;
            case "SDDDS":
                titles.add("定单双");
                break;
            case "SDCZW":
                titles.add("中位数");
                titles.add("单双");
                break;
            case "JSETDX":
            case "JSSTDX":
            case "JSETFX":
            case "JSSTTX":
            case "JSSLTX":
                titles.add("和值");
                titles.add("大小");
                titles.add("形态");
                break;
            case "JSYS":
                titles.add("和值");
                titles.add("大小");
                titles.add("颜色");
                break;
            default:
        }
        
        if (titles.size() > 0)
        {
            headerView = getHeader(titles);
            listView.addHeaderView(headerView);
        }
        listView.setAdapter(adapter);
    }
    
    private View getHeader(ArrayList<String> strings)
    {
        View header = LayoutInflater.from(getActivity()).inflate(R.layout.header_result, null);
        LinearLayout layout = header.findViewById(R.id.layout);
        //for (String s : strings)
        for (int i = 0, size = strings.size(); i < size; i++)
        {
            TextView textView = new TextView(getActivity());
            ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                    .MATCH_PARENT, 1.0f);
            textView.setLayoutParams(layoutParams);
            //textView.setPadding(UiUtils.px2dip(getActivity(),5),0,0,0);
            textView.setText(strings.get(i));
            textView.setTextColor(getResources().getColor(R.color.app_font_ash_color));
            textView.setGravity(Gravity.CENTER);
            layout.addView(textView);
            
            if (i < size - 1)
            {
                View divideLine = new View(getActivity());
                layoutParams = new LinearLayout.LayoutParams(UiUtils.getPixelsFromDp(getActivity(), 1), ViewGroup
                        .LayoutParams.MATCH_PARENT);
                divideLine.setLayoutParams(layoutParams);
                divideLine.setBackgroundColor(getResources().getColor(R.color.divide));
                layout.addView(divideLine);
            }
        }
        
        return header;
    }
    
    private void applyArguments()
    {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }
    
    private void loadCodeList(boolean withCache, int page)
    {
        if (isLoading)
        {
            return;
        }
        this.page = page;
        LotteriesHistoryCommand command = new LotteriesHistoryCommand();
        command.setLotteryID(lottery.getLotteryId());
        command.setCurPage(this.page);
        TypeToken typeToken = new TypeToken<RestResponse<LotteryHistoryCode>>()
        {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback,
                LIST_HISTORY_CODE_ID, this);
        if (withCache)
        {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof LotteryHistoryCode)
            {
                items.addAll(((LotteryHistoryCode) restResponse.getData()).getIssue());
                totalCount = items.size();
                adapter.setData(items);
            } else
            {
                adapter.setData(null);
            }
        }
        restRequest.execute();
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            if (request.getId() == LIST_HISTORY_CODE_ID)
            {
                if (response.getData() == null || !(response.getData() instanceof LotteryHistoryCode))
                {
                    items.clear();
                } else
                {
                    totalCount = ((LotteryHistoryCode) response.getData()).getTotalNum();
                    if (page == FIRST_PAGE)
                    {
                        items.clear();
                    }
                    items.addAll(((LotteryHistoryCode) response.getData()).getIssue());
                }
                adapter.setData(items);
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 7003)
            {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {
            if (request.getId() == LIST_HISTORY_CODE_ID)
            {
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };
}
