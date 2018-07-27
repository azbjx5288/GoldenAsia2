package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
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
import com.goldenasia.lottery.data.ChildReport;
import com.goldenasia.lottery.data.ChildReportCommand;
import com.goldenasia.lottery.data.GaBean;
import com.goldenasia.lottery.data.MemberReportBean;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;
import com.goldenasia.lottery.view.adapter.GaPopupWindowAdapter;
import com.goldenasia.lottery.view.adapter.MemberReportSubAdapter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gan on 2017/11/10.
 * 会员报表2
 * 界面对第一个报表  有所修改
 */

public class MemberReportMainFragment2 extends BaseFragment implements RadioGroup.OnCheckedChangeListener {
    private  final int  LOAD_LIST= 0;//报表数据查询

    @BindView(R.id.main_money_sum)
    TextView main_money_sum;
    @BindView(R.id.amount_money)
    TextView amount_money;
    @BindView(R.id.prize_money)
    TextView prize_money;
    @BindView(R.id.rebate_money)
    TextView rebate_money;

    private RadioButton mCheckedView;

    @BindView(R.id.platRadioButton)
    RadioButton platRadioButton;
    @BindView(R.id.timeRadioButton)
    RadioButton timeRadioButton;

    private final int PLAT_TYPE_SELECT = 1;//平台查询
    private final int TIME_SELECT = 2;//时间查询

    private GaPopupWindowAdapter mTransferAdapter;
    private BubblePopupWindow adapterPopupWindow;

    private int mCurrentQueryConditionType = -1;

    private int mCurrentPlat = 0;//游戏选中位置
    private int mCurrentTime = 0;//当前时间

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;
    private static final int FIRST_PAGE = 1;//服务器分页从1开始
    private List items =new ArrayList<MemberReportBean>();
    private int totalCount;
    private int page = FIRST_PAGE;
    private MemberReportSubAdapter adapter;
    private boolean isLoading;

    private String username;

    private List<ChildReport.ListBean> mChildReportList= new ArrayList();
    private ChildReport.TotalInfoBean childReportTotalInfo=new ChildReport.TotalInfoBean();

    public static void launch(BaseFragment fragment,  String username) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        FragmentLauncher.launch(fragment.getActivity(), MemberReportMainFragment2.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member_report_main_fragment2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup.setOnCheckedChangeListener(this);

        username=getArguments().getString("username");

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            lowerLoad(FIRST_PAGE);
        });
        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getCount() != 0 && items.size() < totalCount && listView.getLastVisiblePosition() >=
                        (listView.getCount() - 1) - endTrigger) {
                    lowerLoad(page + 1);
                }
            }
        });
        //该用户的报表
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChildReport.ListBean listBean=mChildReportList.get(position);

                if (listBean.getLevel().equals("10")) {//非代理账号显示
                    //不做处理

                } else {
                    String username = listBean.getUsername();
                    MemberReportMainFragment2.launch(MemberReportMainFragment2.this,username);
                }

            }
        });
        adapter = new MemberReportSubAdapter(getActivity(),items);
        listView.setAdapter(adapter);
        if (page == FIRST_PAGE) {
            //默认不加载缓存的数据
            lowerLoad(FIRST_PAGE);
        }

        initPopupWindow();
    }

    @OnClick({R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void initPopupWindow() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history_bet_popupwindow, null);
        BubbleLinearLayout bubbleView = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        adapterPopupWindow = new BubblePopupWindow(rootView, bubbleView);
        adapterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(mCheckedView.isChecked()) {
                    radioGroup.clearCheck();
                }
            }
        });

        List<GaBean> mHistoryBetPopupList = new ArrayList<GaBean>();

        mTransferAdapter = new GaPopupWindowAdapter(getContext(), mHistoryBetPopupList);
        mTransferAdapter.setOnIssueNoClickListener((GaBean gaBean) -> {

            if (mCurrentQueryConditionType == PLAT_TYPE_SELECT) {
                platRadioButton.setText(gaBean.getCname());
                mCurrentPlat=gaBean.getLotteryId();
                refreshListView();

                refreshHeadView();

            } else if (mCurrentQueryConditionType == TIME_SELECT) {
                timeRadioButton.setText(gaBean.getCname());
                mCurrentTime=gaBean.getLotteryId();
                lowerLoad(FIRST_PAGE);

            }else{

            }

            adapterPopupWindow.dismiss();
        });
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mTransferAdapter);

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        List<GaBean> lotteryList = new ArrayList<GaBean>();

        switch (checkedId) {
            case R.id.platRadioButton:
                mCheckedView=platRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = PLAT_TYPE_SELECT;

                timeRadioButton.setEnabled(false);

                lotteryList = new ArrayList<GaBean>();
                String[] platStates = new String[]{"全部平台","彩票","GA游戏"};
                for (int i = 0; i < platStates.length; i++) {
                    GaBean gaBean = new GaBean();
                    gaBean.setLotteryId(i);
                    gaBean.setCname(platStates[i]);
                    lotteryList.add(gaBean);
                }

                mTransferAdapter.setData(lotteryList, mCurrentPlat);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);

                timeRadioButton.setEnabled(true);

                break;

            case R.id.timeRadioButton:
                mCheckedView=timeRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = TIME_SELECT;

                platRadioButton.setEnabled(false);

                lotteryList = new ArrayList<GaBean>();
                String[] timeStates = new String[]{"今天","昨天","近3天","近7天","近15天","当月","近35天"};
                for (int i = 0; i < timeStates.length; i++) {
                    GaBean gaBean = new GaBean();
                    gaBean.setLotteryId(i);
                    gaBean.setCname(timeStates[i]);
                    lotteryList.add(gaBean);
                }

                mTransferAdapter.setData(lotteryList, mCurrentTime);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);

                platRadioButton.setEnabled(true);
                break;
        }
    }

    private void lowerLoad(int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        ChildReportCommand command = new ChildReportCommand();
        command.setUsername(username);
        command.setSortKey("profit_and_lost");
        command.setSortDirection(1);

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

        command.setCurPage(this.page);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback,LOAD_LIST, this);

        restRequest.execute();
    }

    private void refreshListView(){
        switch (mCurrentPlat){
            case 0:
                refreshListView01();
                break;
            case 1:
                refreshListView02();
                break;
            case 2:
                refreshListView03();
                break;
            default:
                refreshListView01();
                break;
        }
    }

    //全部平台listview
    private void refreshListView01() {

        items = new ArrayList<MemberReportBean>();

        for (int i = 0; i < mChildReportList.size(); i++) {
            MemberReportBean memberReportBean = new MemberReportBean();
            ChildReport.ListBean childReport = mChildReportList.get(i);

            memberReportBean.setLevel(childReport.getLevel());
            //不分平台的
            memberReportBean.setItem_1(childReport.getUsername());
            memberReportBean.setItem_2(String.valueOf(childReport.getUser_inactive_days()));
            memberReportBean.setItem_3(String.valueOf(childReport.getUser_balance()));
            memberReportBean.setItem_4(String.valueOf(childReport.getTotal_deposit()));
            memberReportBean.setItem_5(String.valueOf(childReport.getTotal_withdraw()));
            memberReportBean.setItem_10(String.valueOf(childReport.getTotal_contribute_rebate()));

            //分平台的
            memberReportBean.setItem_6(String.valueOf(childReport.getTotal_amount() + childReport.getGa_buy_amount()));
            memberReportBean.setItem_7(String.valueOf(childReport.getTotal_prize() + childReport.getGa_prize()));
            memberReportBean.setItem_8(String.valueOf(childReport.getTotal_rebate() + childReport.getGa_rebate()));
            memberReportBean.setItem_9(String.valueOf(childReport.getProfit_and_lost() + childReport.getGa_game_win()));
            items.add(memberReportBean);
        }

        adapter.setData(items);
    }

    //彩票平台listview
    private void refreshListView02() {

        items = new ArrayList<MemberReportBean>();

        for (int i = 0; i < mChildReportList.size(); i++) {
            MemberReportBean memberReportBean = new MemberReportBean();
            ChildReport.ListBean childReport = mChildReportList.get(i);

            memberReportBean.setLevel(childReport.getLevel());

            //不分平台的
            memberReportBean.setItem_1(childReport.getUsername());
            memberReportBean.setItem_2(String.valueOf(childReport.getUser_inactive_days()));
            memberReportBean.setItem_3(String.valueOf(childReport.getUser_balance()));
            memberReportBean.setItem_4(String.valueOf(childReport.getTotal_deposit()));
            memberReportBean.setItem_5(String.valueOf(childReport.getTotal_withdraw()));
            memberReportBean.setItem_10(String.valueOf(childReport.getTotal_contribute_rebate()));

            //分平台的
            memberReportBean.setItem_6(String.valueOf(childReport.getTotal_amount()));
            memberReportBean.setItem_7(String.valueOf(childReport.getTotal_prize()));
            memberReportBean.setItem_8(String.valueOf(childReport.getTotal_rebate()));
            memberReportBean.setItem_9(String.valueOf(childReport.getProfit_and_lost()));
            items.add(memberReportBean);
        }
        adapter.setData(items);
    }

    //GA游戏平台listview
    private void refreshListView03() {

        items = new ArrayList<MemberReportBean>();

        for (int i = 0; i < mChildReportList.size(); i++) {
            MemberReportBean memberReportBean = new MemberReportBean();
            ChildReport.ListBean childReport = mChildReportList.get(i);

            memberReportBean.setLevel(childReport.getLevel());

            //不分平台的
            memberReportBean.setItem_1(childReport.getUsername());
            memberReportBean.setItem_2(String.valueOf(childReport.getUser_inactive_days()));
            memberReportBean.setItem_3(String.valueOf(childReport.getUser_balance()));
            memberReportBean.setItem_4(String.valueOf(childReport.getTotal_deposit()));
            memberReportBean.setItem_5(String.valueOf(childReport.getTotal_withdraw()));
            memberReportBean.setItem_10("0");

            //分平台的
            memberReportBean.setItem_6(String.valueOf(childReport.getGa_buy_amount()));
            memberReportBean.setItem_7(String.valueOf(childReport.getGa_prize()));
            memberReportBean.setItem_8(String.valueOf(childReport.getGa_rebate()));
            memberReportBean.setItem_9(String.valueOf(childReport.getGa_game_win()));
            items.add(memberReportBean);
        }

        adapter.setData(items);

    }

    private void refreshHeadView() {

        switch (mCurrentPlat){
            case 0:
                refreshHeadView01();
                break;
            case 1:
                refreshHeadView02();
                break;
            case 2:
                refreshHeadView03();
                break;
            default:
                refreshHeadView01();
                break;
        }

    }

    private void refreshHeadView01() {
        String sumMoney=String.valueOf(childReportTotalInfo.getFinalX()+childReportTotalInfo.getGa_game_win());//彩票盈利总和+GA盈利总和

        String amount=String.valueOf(childReportTotalInfo.getAmount()+childReportTotalInfo.getGa_buy_amount());
        String prize=String.valueOf(childReportTotalInfo.getPrize()+childReportTotalInfo.getGa_prize());
        String rebate=String.valueOf(childReportTotalInfo.getRebate()+childReportTotalInfo.getGa_rebate());

        main_money_sum.setText(sumMoney);

        String payamtStr = this.getResources().getString(R.string.ga_history_total_win_lost);
        payamtStr = StringUtils.replaceEach(payamtStr, new String[]{"PAYAMT"}, new String[]{String.valueOf(sumMoney)});

        main_money_sum.setText(Html.fromHtml(payamtStr));

        amount_money.setText(amount);
        prize_money.setText(prize);
        rebate_money.setText(rebate);

    }

    private void refreshHeadView02() {
        String sumMoney=String.valueOf(childReportTotalInfo.getFinalX());//彩票盈利总和

        String amount=String.valueOf(childReportTotalInfo.getAmount());
        String prize=String.valueOf(childReportTotalInfo.getPrize());
        String rebate=String.valueOf(childReportTotalInfo.getRebate());

        String payamtStr = this.getResources().getString(R.string.ga_history_total_win_lost);
        payamtStr = StringUtils.replaceEach(payamtStr, new String[]{"PAYAMT"}, new String[]{String.valueOf(sumMoney)});

        main_money_sum.setText(Html.fromHtml(payamtStr));

        amount_money.setText(amount);
        prize_money.setText(prize);
        rebate_money.setText(rebate);
    }

    private void refreshHeadView03() {
        String sumMoney=String.valueOf(childReportTotalInfo.getGa_game_win());//彩票盈利总和+GA盈利总和

        String amount=String.valueOf(childReportTotalInfo.getGa_buy_amount());
        String prize=String.valueOf(childReportTotalInfo.getGa_prize());
        String rebate=String.valueOf(childReportTotalInfo.getGa_rebate());

        String payamtStr = this.getResources().getString(R.string.ga_history_total_win_lost);
        payamtStr = StringUtils.replaceEach(payamtStr, new String[]{"PAYAMT"}, new String[]{String.valueOf(sumMoney)});

        main_money_sum.setText(Html.fromHtml(payamtStr));

        amount_money.setText(amount);
        prize_money.setText(prize);
        rebate_money.setText(rebate);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            JsonString listString = (JsonString) response.getData();
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(listString.getJson());

            String jsonString = el.getAsString();

            ChildReport childReport = GsonHelper.fromJson(jsonString, ChildReport.class);

            if (childReport == null || childReport.getList()==null||childReport.getList().size()==0) {
                mChildReportList.clear();
                totalCount = childReport.getParam().getSum();
            } else {
                totalCount = childReport.getParam().getSum();
                if (page == FIRST_PAGE) {
                    mChildReportList.clear();
                }
                mChildReportList.addAll(childReport.getList());
            }

            refreshListView();

            childReportTotalInfo=childReport.getTotalInfo();

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
            if (request.getId() == LOAD_LIST) {
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
