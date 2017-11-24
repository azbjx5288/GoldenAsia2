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
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.GaBean;
import com.goldenasia.lottery.data.GaGameListCommand;
import com.goldenasia.lottery.data.GaGameListResponse;
import com.goldenasia.lottery.data.GaListCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;
import com.goldenasia.lottery.view.adapter.GaPopupWindowAdapter;
import com.goldenasia.lottery.view.adapter.GameGAHistoryAdapter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Gan on 2017/10/24.
 * GA记录
 */
public class FragmentHistoryGA extends BaseFragment implements RadioGroup.OnCheckedChangeListener{
    private static final String TAG = FragmentHistoryGA.class.getSimpleName();
    private static final int GA_TYPE_ID = 1;//选择游戏
    private static final int GA_LIST_ID = 2;//查询游戏记录

    private static final int FIRST_PAGE = 1;//服务器分页从1开始

    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;
    @BindView(R.id.total_money)
    TextView totalMoneyTextView;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.gaRadioButton)
    RadioButton lotteryRadioButton;
    @BindView(R.id.timeRadioButton)
    RadioButton timeRadioButton;

    private GameGAHistoryAdapter adapter;
    private List<GaGameListResponse.ListBean> items = new ArrayList();//根据时间查询出来的ALL数据
    private List<GaGameListResponse.ListBean> itemsByGameId = new ArrayList();//符合当前选择游戏的数据  用于计算总盈亏
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;

    private BubblePopupWindow adapterPopupWindow;

    private GaPopupWindowAdapter mTransferAdapter;
    private List<GaBean> mHistoryBetPopupList = null;

    private int mSelectGaTypePosition = -1;//游戏选中位置
    private int mSelectTimePosition = 0;//时间选中位置

    //这三个参数 后台查询时候  要传入的
    private int mCurrentLotterySelectId = -1;//选择彩种ID
    private String mCurrentTimeSelectStart;//起始时间
    private String mCurrentTimeSelectEnd;//结束时间

    private RadioButton mCheckedView;

    private final int GA_TYPE_SELECT = 1;//选择彩种查询
    private final int TIME_SELECT = 2;//时间查询

    private int mCurrentQueryConditionType = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "GA记录", R.layout.fragment_history_ga);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        radioGroup.setOnCheckedChangeListener(this);

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            loadGAList(false, FIRST_PAGE);
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
                    loadGAList(false, page + 1);
                }
            }
        });
        adapter = new GameGAHistoryAdapter(items);
        listView.addHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.item_game_ga_history_head,
                listView, false));//添加头部
        listView.setAdapter(adapter);
        mCurrentTimeSelectStart= TimeUtils.getBeginStringOfYesterday();
        mCurrentTimeSelectEnd=TimeUtils.getEndStringOfYesterday();
        if (page == FIRST_PAGE) {
            //默认不加载缓存的数据
            loadGAList(false, FIRST_PAGE);
        }

        initPopupWindow();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * 游戏种类信息查询
     */
    private void loadGATypeList() {
        GaListCommand gaListCommand = new GaListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<GaBean>>>()
        {};
        RestRequestManager.executeCommand(getActivity(), gaListCommand, typeToken, restCallback, GA_TYPE_ID, this);
    }

    private void loadGAList(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        GaGameListCommand command = new GaGameListCommand();
        if(mCurrentLotterySelectId!=-1) {
//            command.setLottery_id(mCurrentLotterySelectId);
        }
        command.setStartDate(mCurrentTimeSelectStart);
        command.setEndDate(mCurrentTimeSelectEnd);
        command.setCurPage(page);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback,
                GA_LIST_ID, this);
        restRequest.execute();

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        GaBean gaBean = new GaBean();
        List<GaBean> lotteryList = new ArrayList<GaBean>();

        switch (checkedId) {
            case R.id.gaRadioButton:
                mCheckedView=lotteryRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = GA_TYPE_SELECT;

                //彩种选择的时候 时间和状态选择就不可点击了，彩种全部显示出来后才让时间和状态选择就可以点击
                timeRadioButton.setEnabled(false);

                loadGATypeList();
                break;

            case R.id.timeRadioButton:
                mCheckedView=timeRadioButton;
                if(!mCheckedView.isChecked()){
                    return;
                }
                mCurrentQueryConditionType = TIME_SELECT;

                lotteryList = new ArrayList<GaBean>();
                String[] timeStates = new String[]{"昨天","近3天","近7天","近15天","当月","近35天"};
                for (int i = 0; i < timeStates.length; i++) {
                    gaBean = new GaBean();
                    gaBean.setLotteryId(i);
                    gaBean.setCname(timeStates[i]);
                    lotteryList.add(gaBean);
                }

                mTransferAdapter.setData(lotteryList, mSelectTimePosition);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);

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

        mHistoryBetPopupList = new ArrayList<GaBean>();

        mTransferAdapter = new GaPopupWindowAdapter(getContext(), mHistoryBetPopupList);
        mTransferAdapter.setOnIssueNoClickListener((GaBean gaBean) -> {

            if (mCurrentQueryConditionType == GA_TYPE_SELECT) {
                mCurrentLotterySelectId = gaBean.getLotteryId();
                lotteryRadioButton.setText(gaBean.getCname());
                mSelectGaTypePosition=mCurrentLotterySelectId;
                refreshListView();

                refreshTotalMoney();
            } else if (mCurrentQueryConditionType == TIME_SELECT) {
                selectPositionToTimeSelected(gaBean.getLotteryId());
                timeRadioButton.setText(gaBean.getCname());
                mSelectTimePosition=gaBean.getLotteryId();
                loadGAList(false, FIRST_PAGE);
            }else{

            }

            adapterPopupWindow.dismiss();
        });
        ListView listView = (ListView) rootView.findViewById(R.id.listView);
        listView.setAdapter(mTransferAdapter);
    }

    private void selectPositionToTimeSelected(int position) {
        switch (position){
            case 0://"昨天"
                mCurrentTimeSelectStart=TimeUtils.getBeginStringOfYesterday();
                mCurrentTimeSelectEnd=TimeUtils.getEndStringOfYesterday();
                break;
            case 1://"近3天"
                mCurrentTimeSelectStart=TimeUtils.getLatelyStringOfThree();
                mCurrentTimeSelectEnd=TimeUtils.getEndStringOfToday();
                break;
            case 2://"近7天"
                mCurrentTimeSelectStart=TimeUtils.getLatelyStringOfSeven();
                mCurrentTimeSelectEnd=TimeUtils.getEndStringOfToday();
                break;
            case 3://"近15天"
                mCurrentTimeSelectStart=TimeUtils.getLatelyStringOf15();
                mCurrentTimeSelectEnd=TimeUtils.getEndStringOfToday();
                break;
            case 4://"当月"
                mCurrentTimeSelectStart=TimeUtils.getStringBeginDayOfCurrentMonth();
                mCurrentTimeSelectEnd=TimeUtils.getEndStringOfToday();
                break;
            case 5://"近35天"
                mCurrentTimeSelectStart=TimeUtils.getLatelyStringOf35();
                mCurrentTimeSelectEnd=TimeUtils.getEndStringOfToday();
                break;
            default:
        }
    }

    private void refreshTotalMoney() {
        double totalMoney=0.0;

        for(int  i=0;i<itemsByGameId.size();i++){
            GaGameListResponse.ListBean  bean=itemsByGameId.get(i);
            double winLost=Double.parseDouble(bean.getGa_win_lose());
            totalMoney+=winLost;
        }

        String payamtStr = this.getResources().getString(R.string.ga_history_total_win_lost);
        payamtStr = StringUtils.replaceEach(payamtStr, new String[]{"PAYAMT"}, new String[]{String.valueOf(totalMoney)});

        totalMoneyTextView.setText(Html.fromHtml(payamtStr));
    }

    private void refreshListView() {
        if(mCurrentLotterySelectId==-1){
            itemsByGameId.clear();
            itemsByGameId.addAll(items);
            adapter.setData(items);

        }else{
            itemsByGameId.clear();
            for(int  i=0;i<items.size();i++){
                GaGameListResponse.ListBean bean=items.get(i);
                if(mCurrentLotterySelectId==Integer.parseInt(bean.getGame_id())){
                    itemsByGameId.add(bean);
                }
            }
            adapter.setData(itemsByGameId);
        }

    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case GA_TYPE_ID: { //游戏种类信息查询
                    ArrayList<GaBean> gaList = (ArrayList<GaBean>) response.getData();
                    GaBean  headGaBean=new GaBean();
                    headGaBean.setLotteryId(-1);
                    headGaBean.setCname("选择游戏");
                    gaList.add(0,headGaBean);

                    mTransferAdapter.setData(gaList, mSelectGaTypePosition);

                    adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up,1);
                    timeRadioButton.setEnabled(true);
                    break;
                }
                case GA_LIST_ID: {
                    JsonString listString = (JsonString) response.getData();
                    JsonParser parser = new JsonParser();
                    JsonElement el = parser.parse(listString.getJson());
                    String jsonString=el.getAsString();
                    GaGameListResponse  GaGameListResponse=GsonHelper.fromJson(jsonString,GaGameListResponse.class);
                    if (GaGameListResponse == null || GaGameListResponse.getList()==null||GaGameListResponse.getList().size()==0) {
                        items.clear();
                        totalCount = Integer.parseInt(GaGameListResponse.getParam().getSum());
                    } else {
                        totalCount = Integer.parseInt(GaGameListResponse.getParam().getSum());
                        if (page == FIRST_PAGE) {
                            items.clear();
                        }
                        items.addAll(GaGameListResponse.getList());
                    }

                    refreshListView();

                    refreshTotalMoney();
                    break;
                }
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
            if (request.getId() == GA_LIST_ID ) {
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
                //解决由于条件切换 没有合适的数据 要清空listview数据
//                items.clear();
//                adapter.setData(items);
            }
        }
    };

}