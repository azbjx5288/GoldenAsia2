package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.IssueEntity;
import com.goldenasia.lottery.data.LotteriesHistoryCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryHistoryCode;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.pattern.ChartPage;
import com.goldenasia.lottery.view.adapter.IssueNoAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by ACE-PC on 2016/3/7.
 */
public class ChartTrendFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = ChartTrendFragment.class.getSimpleName();
    private static final int LIST_HISTORY_CODE_ID = 1;
    private static final int FIRST_PAGE = 1;

    @Bind(R.id.scrollview_frame)
    PtrClassicFrameLayout scrollviewLayout;
    @Bind(R.id.scrollview)
    ScrollView scrollview;
    @Bind(R.id.linearLayout)
    LinearLayout linearLayout;

    @Bind(R.id.amountText)
    TextView amountText;
    @Bind(R.id.isNeedLink)
    CheckBox isNeedLink;

    private Lottery lottery;
    private ChartPage chartPage;
    private int page = FIRST_PAGE, amount = 20;
    private BubblePopupWindow mBubblePopupWindow;
    private List<IssueEntity> items = new ArrayList<>();
    private List<Integer> datalist = Arrays.asList(20, 40, 50, 100, 200);

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history_charttrend, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.customized_tips, null);
        BubbleLinearLayout bubbleView = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        mBubblePopupWindow = new BubblePopupWindow(rootView, bubbleView);

        isNeedLink.setChecked(true);
        isNeedLink.setOnCheckedChangeListener(this);

        chartPage = new ChartPage(getContext(), lottery, items);

        IssueNoAdapter adapter = new IssueNoAdapter(getContext(), datalist);
        adapter.setOnIssueNoClickListener(new IssueNoAdapter.OnIssueNoClickListener() {
            @Override
            public void onIssueNoListener(int position) {
                int number = datalist.get(position);
                if (amount != number) {
                    amount = number;
                    amountText.setText(number + "");
                    loadCodeList(FIRST_PAGE, amount);
                }
                mBubblePopupWindow.dismiss();
            }

            @Override
            public void onInitialAmount(int position) {
                int number = datalist.get(position);
                amount = number;
                amountText.setText(number + "");
            }
        });
        ListView issuenoList = (ListView) rootView.findViewById(R.id.issueNoList);
        issuenoList.setAdapter(adapter);

        /*// header
        final RentalsSunHeaderView header = new RentalsSunHeaderView(getContext());
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
        header.setUp(scrollviewLayout);*/

        scrollviewLayout.setResistance(1.7f);
        scrollviewLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        scrollviewLayout.setDurationToClose(200);
        scrollviewLayout.setDurationToCloseHeader(1000);
        scrollviewLayout.setPullToRefresh(false);
        scrollviewLayout.setKeepHeaderWhenRefresh(true);
        scrollviewLayout.setLoadingMinTime(1000);
        scrollviewLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollview, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                scrollviewLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadCodeList(FIRST_PAGE, amount);
                    }
                }, 1000);
            }
        });

    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    @OnClick(R.id.selectdonw)
    public void selectDonw(View v) {
        switch (v.getId()) {
            case R.id.selectdonw:
                mBubblePopupWindow.showArrowTo(v, BubbleStyle.ArrowDirection.Up);
                break;
        }
    }

    private void loadCodeList(int page, int number) {
        this.page = page;
        LotteriesHistoryCommand command = new LotteriesHistoryCommand();
        command.setLotteryID(lottery.getLotteryId());
        command.setCurPage(page);
        command.setPerPage(number);
        TypeToken typeToken = new TypeToken<RestResponse<LotteryHistoryCode>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, LIST_HISTORY_CODE_ID, this);
        restRequest.execute();
    }


    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == LIST_HISTORY_CODE_ID) {
                items.clear();
                linearLayout.removeAllViews();
                items.addAll(((LotteryHistoryCode) response.getData()).getIssue());
                chartPage.setData(items);
                chartPage.showHideLines(isNeedLink.isChecked());
                linearLayout.addView(chartPage.getChartView());
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
            if (state == RestRequest.RUNNING&&scrollviewLayout!=null) {
                scrollviewLayout.refreshComplete();
            }
        }
    };

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        chartPage.showHideLines(isChecked);
    }

    @Override
    public void onResume() {
        //设置延时自动刷新数据
        if (!isFinishing()&&scrollviewLayout!=null) {
            scrollviewLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (scrollviewLayout!=null)
                        scrollviewLayout.autoRefresh(true);
                }
            }, 500);
        }
        super.onResume();
    }

}
