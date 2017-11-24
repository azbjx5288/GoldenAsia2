package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.ChildReport;
import com.goldenasia.lottery.data.ChildReportCommand;
import com.goldenasia.lottery.data.MemberReportBean;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;
import com.goldenasia.lottery.view.adapter.MemberReportSubAdapter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/13.
 * 会员报表
 */

public class MemberReportSubFragment extends BaseFragment {
    private static final String TAG = MemberReportSubFragment.class.getSimpleName();
    private  final int  LOAD_LIST= 0;//报表数据查询

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list_view)
    ListView listView;

    private MemberReportSubAdapter adapter;
    private  final int FIRST_PAGE = 1;//服务器分页从1开始
    private int page = FIRST_PAGE;
    private List items =new ArrayList<MemberReportBean>();
    private int totalCount;
    private boolean isLoading;
    private boolean isFirstTime = true;

    private List<ChildReport.ListBean> mChildReportList= new ArrayList();
    private ChildReport.TotalInfoBean childReportTotalInfo=new ChildReport.TotalInfoBean();

    private int mCurrentTime = 0;
    private String username;
    private MemberReportMainFragment mMemberReportMainFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_report_sub_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCurrentTime = getArguments().getInt("key");
        username = GoldenAsiaApp.getUserCentre().getUserName();

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            lowerLoad(false, FIRST_PAGE);
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
                    lowerLoad(false, page + 1);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.i(TAG,mChildReportList.get(position).getUsername()+"");
            }
        });
        adapter = new MemberReportSubAdapter(getActivity(),items);
        if (page == FIRST_PAGE) {

            lowerLoad(false, FIRST_PAGE);
        }
        listView.setAdapter(adapter);
        isFirstTime = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FragmentLauncher  fragmentLauncher=(FragmentLauncher)getActivity();
        mMemberReportMainFragment=(MemberReportMainFragment)fragmentLauncher.getfragmentCaller();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(mMemberReportMainFragment==null){
            return;
        }

        //相当于Fragment的onResume
        if (isVisibleToUser&&!isFirstTime) {

            refreshListView();
            refreshHeadView();
        } else {
            //相当于Fragment的onPause

        }
    }

    private void lowerLoad(boolean withCache, int page) {
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

        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null) {
                if (page == FIRST_PAGE) {
                    items.clear();
                }
                JsonString listString = (JsonString) restResponse.getData();
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

                refreshListView01();

            } else {
                adapter.setData(null);
            }
        }

        restRequest.execute();
    }

    public void changePlatForm(int  platformType) {
        switch (platformType){
            case 1:
                refreshListView01();
                refreshHeadView01();
                break;
            case 2:
                refreshListView02();
                refreshHeadView02();
                break;
            case 3:
                refreshListView03();
                refreshHeadView03();
                break;
            default:
        }
    }

    private void refreshListView(){
        int platformType= 1;
        if(mMemberReportMainFragment!=null){
             platformType= mMemberReportMainFragment.getPlatformType();
        }
        switch (platformType){
            case 1:
                refreshListView01();
                break;
            case 2:
                refreshListView02();
                break;
            case 3:
                refreshListView03();
                break;
            default:
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

        int platformType= 1;
        if(mMemberReportMainFragment!=null){
            platformType= mMemberReportMainFragment.getPlatformType();
        }
        switch (platformType){
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
        String sumMoney=String.valueOf(childReportTotalInfo.getFinalX()+childReportTotalInfo.getGa_game_win());//彩票盈利总和+GA盈利总和

        String amount=String.valueOf(childReportTotalInfo.getAmount()+childReportTotalInfo.getGa_buy_amount());
        String prize=String.valueOf(childReportTotalInfo.getPrize()+childReportTotalInfo.getGa_prize());
        String rebate=String.valueOf(childReportTotalInfo.getRebate()+childReportTotalInfo.getGa_rebate());

        HashMap<String,Integer> keyMap=new HashMap<String,Integer>();
        keyMap.put("key",mCurrentTime);
        HashMap<String,String> valueMap=new HashMap<String,String>();
        valueMap.put("value",sumMoney);//总盈利

        valueMap.put("amount",amount);//总投注
        valueMap.put("prize",prize);//总中奖
        valueMap.put("rebate",rebate);//总返点
        mMemberReportMainFragment.changeMoneySumText(keyMap,valueMap);
    }

    private void refreshHeadView02() {
        String sumMoney=String.valueOf(childReportTotalInfo.getFinalX());//彩票盈利总和

        String amount=String.valueOf(childReportTotalInfo.getAmount());
        String prize=String.valueOf(childReportTotalInfo.getPrize());
        String rebate=String.valueOf(childReportTotalInfo.getRebate());

        HashMap<String,Integer> keyMap=new HashMap<String,Integer>();
        keyMap.put("key",mCurrentTime);
        HashMap<String,String> valueMap=new HashMap<String,String>();
        valueMap.put("value",sumMoney);//总盈利

        valueMap.put("amount",amount);//总投注
        valueMap.put("prize",prize);//总中奖
        valueMap.put("rebate",rebate);//总返点
        mMemberReportMainFragment.changeMoneySumText(keyMap,valueMap);
    }

    private void refreshHeadView03() {
        String sumMoney=String.valueOf(childReportTotalInfo.getGa_game_win());//彩票盈利总和+GA盈利总和

        String amount=String.valueOf(childReportTotalInfo.getGa_buy_amount());
        String prize=String.valueOf(childReportTotalInfo.getGa_prize());
        String rebate=String.valueOf(childReportTotalInfo.getGa_rebate());

        HashMap<String,Integer> keyMap=new HashMap<String,Integer>();
        keyMap.put("key",mCurrentTime);
        HashMap<String,String> valueMap=new HashMap<String,String>();
        valueMap.put("value",sumMoney);//总盈利

        valueMap.put("amount",amount);//总投注
        valueMap.put("prize",prize);//总中奖
        valueMap.put("rebate",rebate);//总返点
        mMemberReportMainFragment.changeMoneySumText(keyMap,valueMap);
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
    public void onDestroyView() {
        super.onDestroyView();
    }
}
