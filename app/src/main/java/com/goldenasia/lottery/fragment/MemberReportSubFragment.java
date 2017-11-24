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
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.BetListResponse;
import com.goldenasia.lottery.data.ChildReport;
import com.goldenasia.lottery.data.ChildReportCommand;
import com.goldenasia.lottery.data.ChildReportTotalInfo;
import com.goldenasia.lottery.data.MemberReportBean;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;
import com.goldenasia.lottery.view.adapter.MemberReportSubAdapter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/13.
 * 会员报表
 */

public class MemberReportSubFragment extends BaseFragment {
    private static final int LIST_COMMAND = 0;//彩票种类信息查询

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.list_view)
    ListView listView;

    private MemberReportSubAdapter adapter;
    private static final int FIRST_PAGE = 1;//服务器分页从1开始
    private int page = FIRST_PAGE;
    private List items = new ArrayList();
    private int totalCount;
    private boolean isLoading;
    private boolean isFirstTime = true;

    private List<ChildReport> mChildReportList;
    private ChildReportTotalInfo mChildReportTotalInfoList;

    private  int  mCurrentTime=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_report_sub_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mCurrentTime=getArguments().getInt("key");

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            lowerLoad(false,FIRST_PAGE);
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
                    lowerLoad(false,page + 1);
                }
            }
        });
       // listView.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.member_report_sub_head,
        //        listView, false));//添加头部
        adapter = new MemberReportSubAdapter(items);
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (page == FIRST_PAGE) {

            lowerLoad(false,FIRST_PAGE);
        }
        isFirstTime = false;
    }



//    @OnClick({R.id.title_text_layout,R.id.tab_menu1,R.id.tab_menu2,R.id.tab_menu3,R.id.tab_menu4,R.id.tab_menu5,R.id.back})
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.title_text_layout:
////                ArrayList<Class> passwors = new ArrayList();
////                passwors.add(LoginPasswordSetting.class);
////                passwors.add(CashPasswordSetting.class);
////                TwoTableFragment.launch(getActivity(), "密码设置", new String[]{"登录密码", "资金密码"}, passwors);
//
//
//                break;
//            case R.id.tab_menu1:
//                launchFragment(SecuritySetting.class);
//                break;
//            case R.id.card_setting:
//                launchFragment(BankCardSetting.class);
//                break;
//            case R.id.back:
//                getActivity().finish();
//                break;
//            default:
//                break;
//        }
//    }

    public void reLoad(int platformType) {
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

    private void lowerLoad(boolean withCache,int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        ChildReportCommand command = new ChildReportCommand();

        switch (mCurrentTime){
            case 0://今天
                command.setStartDate(TimeUtils.getStringDayBegin());
                command.setEndDate(TimeUtils.getStringDayEnd());
                break;
            case 1://近三天
                command.setStartDate(TimeUtils.getStringBeginDayOfThree());
                command.setEndDate(TimeUtils.getStringDayEnd());
                break;
            case 2://近七天
                command.setStartDate(TimeUtils.getStringBeginDayOfSeven());
                command.setEndDate(TimeUtils.getStringDayEnd());
                break;
            case 3://当月
                command.setStartDate(TimeUtils.getStringBeginDayOfCurrentMonth());
                command.setEndDate(TimeUtils.getStringDayEnd());
                break;
            case 4://近35天
                command.setStartDate(TimeUtils.getStringBeginDayOf35());
                command.setEndDate(TimeUtils.getStringDayEnd());
                break;
        }

        command.setCurPage(this.page);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback, LIST_COMMAND, this);

        restRequest.execute();

    }

    /*解析以下格式
     {"list":{
     "57190":{"user_id":"57190","username":"gan0913","reg_time":"2017-09-13 12:32:20","last_time":"2017-10-25 12:43:52","level":"1","is_test":"0","parent_id":"56912","total_deposit":0,
     "total_withdraw":0,"total_balance":9.99999857019E9,"total_amount":0,"total_prize":0,"total_rebate":0,"total_contribute_rebate":0,"ga_game_win":0,"ga_buy_amount":0,"ga_prize":0,"ga_rebate":0,
     "total_win":0,"user_inactive_days":"1天"},
     "57212":{"user_id":"57212","username":"gan123","reg_time":"2017-09-25 14:12:48","last_time":"2017-10-24 12:37:50","level":"2","is_test":"0","parent_id":"57190",
     "total_deposit":0,"total_withdraw":0,"total_balance":0,"total_amount":0,"total_prize":0,"total_rebate":0,"total_contribute_rebate":0,"ga_game_win":0,"ga_buy_amount":0,"ga_prize":0,"ga_rebate":0,
     "total_win":0,"user_inactive_days":"2天"},*/
    private List<ChildReport> parseChildReport(JSONObject jsonObject) {
        List<ChildReport> childReportList = new ArrayList<ChildReport>();

        try {
            JSONObject listJSONObject = (JSONObject) jsonObject.get("list");

            Iterator<String> iterator = listJSONObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();//"57219"  这种key

                ChildReport childReport = new ChildReport();

                JSONObject beanJSONObject = (JSONObject) listJSONObject.get(key);
                childReport.setUser_id(beanJSONObject.getString("user_id"));
                childReport.setUsername(beanJSONObject.getString("username"));
                childReport.setReg_time(beanJSONObject.getString("reg_time"));
                childReport.setLast_time(beanJSONObject.getString("last_time"));
                childReport.setLevel(beanJSONObject.getString("level"));
                childReport.setIs_test(beanJSONObject.getString("is_test"));
                childReport.setParent_id(beanJSONObject.getString("parent_id"));
                childReport.setTotal_deposit(beanJSONObject.getInt("total_deposit"));
                childReport.setTotal_withdraw(beanJSONObject.getInt("total_withdraw"));
                childReport.setTotal_balance(beanJSONObject.getDouble("total_balance"));
                childReport.setTotal_amount(beanJSONObject.getInt("total_amount"));
                childReport.setTotal_prize(beanJSONObject.getDouble("total_prize"));
                childReport.setTotal_rebate(beanJSONObject.getInt("total_rebate"));
                childReport.setTotal_contribute_rebate(beanJSONObject.getInt("total_contribute_rebate"));
                childReport.setGa_game_win(beanJSONObject.getInt("ga_game_win"));
                childReport.setGa_buy_amount(beanJSONObject.getInt("ga_buy_amount"));
                childReport.setGa_prize(beanJSONObject.getInt("ga_prize"));
                childReport.setGa_rebate(beanJSONObject.getInt("ga_rebate"));
                childReport.setTotal_win(beanJSONObject.getInt("total_win"));
                childReport.setUser_inactive_days(beanJSONObject.getString("user_inactive_days"));

                childReportList.add(childReport);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return childReportList;
    }

    /*解析以下格式
    "totalInfo":{--各数据项总和
        "total_balance":3564.0984,
        "total_deposit":0,
        "total_withdraw":0,
        "total_amount":376.48,
        "total_rebate":0,
        "total_contribute_rebate":0,
        "total_prize":3417.0003,
        "ga_game_win":0,
        "ga_buy_amount":0,
        "ga_prize":0,
        "ga_rebate":0,
        "profit_and_lost":3040.5203
    }*/
    private ChildReportTotalInfo parseChildReportTotalInfo(JSONObject jsonObject) {
        ChildReportTotalInfo childReportTotalInfo = new ChildReportTotalInfo();
        try {
            JSONObject beanJSONObject = (JSONObject) jsonObject.get("totalInfo");

            childReportTotalInfo.setTotal_balance(beanJSONObject.getDouble("total_balance"));
            childReportTotalInfo.setTotal_deposit(beanJSONObject.getInt("total_deposit"));
            childReportTotalInfo.setTotal_withdraw(beanJSONObject.getInt("total_withdraw"));
            childReportTotalInfo.setTotal_amount(beanJSONObject.getDouble("total_amount"));
            childReportTotalInfo.setTotal_rebate(beanJSONObject.getInt("total_rebate"));
            childReportTotalInfo.setTotal_contribute_rebate(beanJSONObject.getInt("total_contribute_rebate"));
            childReportTotalInfo.setTotal_prize(beanJSONObject.getDouble("total_prize"));
            childReportTotalInfo.setGa_game_win(beanJSONObject.getInt("ga_game_win"));
            childReportTotalInfo.setGa_buy_amount(beanJSONObject.getInt("ga_buy_amount"));
            childReportTotalInfo.setGa_prize(beanJSONObject.getInt("ga_prize"));
            childReportTotalInfo.setGa_rebate(beanJSONObject.getInt("ga_rebate"));
            childReportTotalInfo.setProfit_and_lost(beanJSONObject.getDouble("profit_and_lost"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return childReportTotalInfo;
    }

    //全部平台listview
    private void refreshListView01(){
        MemberReportBean memberReportBean=new MemberReportBean();

        items=new ArrayList<MemberReportBean>();

        ChildReport childReport=null;
        for(int  i=0;i<mChildReportList.size();i++){
            childReport=mChildReportList.get(i);

            //不分平台的
            memberReportBean.setItem_1(childReport.getUsername());
            memberReportBean.setItem_2(childReport.getUser_inactive_days());
            memberReportBean.setItem_3(String.valueOf(childReport.getTotal_balance()));
            memberReportBean.setItem_4(String.valueOf(childReport.getTotal_deposit()));
            memberReportBean.setItem_5(String.valueOf(childReport.getTotal_withdraw()));

            //分平台的
            memberReportBean.setItem_6(String.valueOf(childReport.getTotal_amount()+childReport.getGa_buy_amount()));
            memberReportBean.setItem_7(String.valueOf(childReport.getTotal_prize()+childReport.getGa_prize()));
            memberReportBean.setItem_8(String.valueOf(childReport.getTotal_rebate()+childReport.getGa_rebate()));
            memberReportBean.setItem_9(String.valueOf(childReport.getTotal_win()+childReport.getGa_game_win()));
            items.add(memberReportBean);
        }

        adapter.setData(items);
    }

    //彩票平台listview
    private void refreshListView02(){
        MemberReportBean memberReportBean=new MemberReportBean();
        items=new ArrayList<MemberReportBean>();

        ChildReport childReport=null;
        for(int  i=0;i<mChildReportList.size();i++){
            childReport=mChildReportList.get(i);

            //不分平台的
            memberReportBean.setItem_1(childReport.getUsername());
            memberReportBean.setItem_2(childReport.getUser_inactive_days());
            memberReportBean.setItem_3(String.valueOf(childReport.getTotal_balance()));
            memberReportBean.setItem_4(String.valueOf(childReport.getTotal_deposit()));
            memberReportBean.setItem_5(String.valueOf(childReport.getTotal_withdraw()));

            //分平台的
            /*"total_amount":376.48, --彩票投注
            "total_prize":3417.0003, --彩票中奖
            "total_rebate":0, --彩票返点
            "total_win":0, --彩票盈亏
            */
            memberReportBean.setItem_6(String.valueOf(childReport.getTotal_amount()));
            memberReportBean.setItem_7(String.valueOf(childReport.getTotal_prize()));
            memberReportBean.setItem_8(String.valueOf(childReport.getTotal_rebate()));
            memberReportBean.setItem_9(String.valueOf(childReport.getTotal_win()));
            items.add(memberReportBean);
        }
        adapter.setData(items);
    }

    //GA游戏平台listview
    private void refreshListView03(){
        MemberReportBean memberReportBean=new MemberReportBean();

        items=new ArrayList<MemberReportBean>();

        ChildReport childReport=null;
        for(int  i=0;i<mChildReportList.size();i++){
            childReport=mChildReportList.get(i);

            //不分平台的
            memberReportBean.setItem_1(childReport.getUsername());
            memberReportBean.setItem_2(childReport.getUser_inactive_days());
            memberReportBean.setItem_3(String.valueOf(childReport.getTotal_balance()));
            memberReportBean.setItem_4(String.valueOf(childReport.getTotal_deposit()));
            memberReportBean.setItem_5(String.valueOf(childReport.getTotal_withdraw()));

            //分平台的
            /*"ga_game_win":0, --GA盈亏
            "ga_buy_amount":0, --GA投注
            "ga_prize":0, --GA中奖
            "ga_rebate":0, --GA返点*/
            memberReportBean.setItem_6(String.valueOf(childReport.getGa_buy_amount()));
            memberReportBean.setItem_7(String.valueOf(childReport.getGa_prize()));
            memberReportBean.setItem_8(String.valueOf(childReport.getGa_rebate()));
            memberReportBean.setItem_9(String.valueOf(childReport.getGa_game_win()));
            items.add(memberReportBean);
        }

        adapter.setData(items);
    }

    private void refreshHeadView() {

    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            JsonString listString = (JsonString) response.getData();
            JsonParser parser = new JsonParser();
            JsonElement el = parser.parse(listString.getJson());

            try {
                JSONObject jsonObject = new JSONObject(el.getAsString());

                 mChildReportList = parseChildReport(jsonObject);

                 mChildReportTotalInfoList = parseChildReportTotalInfo(jsonObject);


                totalCount = ((JSONObject) jsonObject.get("param")).getInt("sum");



//                JSONObject listJSONObject = (JSONObject) jsonObject.get("list");
//                JSONObject totalInfoJSONObject = (JSONObject) jsonObject.get("totalInfo");


            } catch (JSONException e) {
                e.printStackTrace();
            }

            refreshListView01();

            refreshHeadView();
            // 将 json 转化 成 Map泛型
//            Map<String,Object> map = gson.fromJson(el, new TypeToken<Map<String,Object>>() {}.getType());


            //把JsonElement对象转换成JsonPrimitive
//            JsonPrimitive jsonPrimitive = null;
//            if(el.isJsonPrimitive()){
//                jsonPrimitive = el.getAsJsonPrimitive();
//            }


//            GsonHelper.fromJson(listString.getJson(), GgcCardListEntity.class);
//            totalCount = ((LowerMemberList) response.getData()).getUsersCount();
//            if (page == FIRST_PAGE) {
//                items.clear();
//            }
//            List<LowerMember> lowerMembers = ((LowerMemberList) response.getData()).getUsers();
//            LowerMember lowerMember = null;
//            for (LowerMember lower : lowerMembers) {
//                if (userCentre.getUserInfo().getUserName().equals(lower.getUsername())) {
//                    lowerMember = lower;
//                }
//            }
//            items.addAll(lowerMembers);
//            if (lowerMember != null) {
//                int index = items.indexOf(lowerMember);
//                if (index != -1) {
//                    items.remove(index);
//                }
//            }
//            adapter.setData(items);
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
            if (request.getId() == LIST_COMMAND){
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
        ButterKnife.unbind(this);
    }
}
