package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.goldenasia.lottery.data.BetListResponse;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryMenu;
import com.goldenasia.lottery.data.LotteryMenuCommand;
import com.goldenasia.lottery.data.MemberOrderCommand;
import com.goldenasia.lottery.data.MemberOrderResponse;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.TimeUtils;
import com.goldenasia.lottery.view.adapter.GameHistoryAdapter;
import com.goldenasia.lottery.view.adapter.HistoryBetPopupWindowAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Sakura on 2018/4/5.
 */

public class MemberOrderFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener
{
    private static final String TAG = "MemberOrderFragment";
    private static final int LOTTERY_LIST_COMMAND = 0;//彩票种类信息查询
    private static final int CHILD_PACKAGE_LIST_COMMAND = 1;
    private static final int FIRST_PAGE = 1;//服务器分页从1开始
    
    @BindView(R.id.lotteryRadioButton)
    RadioButton lotteryRadioButton;
    @BindView(R.id.memberRadioButton)
    RadioButton memberRadioButton;
    @BindView(R.id.timeRadioButton)
    RadioButton timeRadioButton;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.member_account)
    EditText memberAccount;
    @BindView(R.id.order_id)
    EditText orderId;
    @BindView(R.id.query)
    Button queryButton;
    @BindView(R.id.list_view)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    Unbinder unbinder;
    
    private int mSelectLotteryPosition = -1;//彩种选中位置
    private int mSelectMemberPosition = 0;//状态选中位置
    private int mSelectTimePosition = 0;//时间选中位置
    
    private int mCurrentLotterySelectId = -1;//选择彩种ID
    private Date mCurrentTimeSelectStart;//起始时间
    private Date mCurrentTimeSelectEnd;//结束时间
    private int mCurrentMemberSelect = -1;//状态
    
    private RadioButton mCheckedView;
    
    private final int LOTTERY_SELECT = 1;//选择彩种查询
    private final int MEMBER_SELECT = 2;//会员查询
    private final int TIME_SELECT = 3; //时间查询
    
    private int mCurrentQueryConditionType = -1;
    
    private List items = new ArrayList();
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;
    private boolean isFirstTime = true;
    
    private GameHistoryAdapter gameHistoryAdapter;
    private BubblePopupWindow adapterPopupWindow;
    private HistoryBetPopupWindowAdapter popupAdapter;
    
    private ArrayList<Lottery> lotteries;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //View view = inflater.inflate(R.layout.fragment_member_order, container, false);
        View view =inflateView(inflater, container, false, "下级订单", R.layout.fragment_member_order);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        radioGroup.setOnCheckedChangeListener(this);
        
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() ->
        {
            loadMemberOrder(false, FIRST_PAGE);
        });
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
                    loadMemberOrder(false, page + 1);
                }
            }
        });
        gameHistoryAdapter = new GameHistoryAdapter(view.getContext());
        listView.setAdapter(gameHistoryAdapter);
        
        mCurrentTimeSelectStart = TimeUtils.getBeginDateOfToday();
        mCurrentTimeSelectEnd = TimeUtils.getEndDateOfToday();
        if (page == FIRST_PAGE)
        {
            //            if (isBet) {
            //                loadBetList(isFirstTime, FIRST_PAGE);
            //            } else {
            //                loadTraceList(isFirstTime, FIRST_PAGE);
            //            }
            //默认不加载缓存的数据
            loadMemberOrder(false, FIRST_PAGE);
        }
        isFirstTime = false;
        
        initPopupWindow();
        gameHistoryAdapter = new GameHistoryAdapter(view.getContext());
        listView.setAdapter(gameHistoryAdapter);
    }
    
    /**
     * 彩票种类信息查询
     */
    private void loadLotteryList()
    {
        LotteryMenuCommand command = new LotteryMenuCommand();
        command.setLotteryID(0);
        TypeToken typeToken = new TypeToken<RestResponse<LotteryMenu>>()
        {};
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, LOTTERY_LIST_COMMAND, this);
    }
    
    private void loadMemberOrder(boolean withCache, int page)
    {
        if (isLoading)
            return;
        
        MemberOrderCommand memberOrderCommand = new MemberOrderCommand();
        memberOrderCommand.setLottery_id(mCurrentLotterySelectId == -1 ? 0 : mCurrentLotterySelectId);
        memberOrderCommand.setUsername(memberAccount.getText().toString());
        memberOrderCommand.setWrap_id(orderId.getText().toString());
        memberOrderCommand.setInclude_children(mCurrentMemberSelect);
        memberOrderCommand.setStart_time(mCurrentTimeSelectStart);
        memberOrderCommand.setEnd_time(mCurrentTimeSelectEnd);
        memberOrderCommand.setCurPage(1);
        //TypeToken typeToken = new TypeToken<RestResponse<MemberOrderEntity>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), memberOrderCommand, /*typeToken,*/
                restCallback, CHILD_PACKAGE_LIST_COMMAND, this);
        
        if (withCache)
        {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof BetListResponse)
            {
                items.addAll(((BetListResponse) restResponse.getData()).getBets());
                totalCount = items.size();
                gameHistoryAdapter.setData(items);
            } else
            {
                gameHistoryAdapter.setData(null);
            }
        }
        restRequest.execute();
    }
    
    private void initPopupWindow()
    {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_history_bet_popupwindow, null);
        BubbleLinearLayout bubbleView = rootView.findViewById(R.id.popup_bubble);
        adapterPopupWindow = new BubblePopupWindow(rootView, bubbleView);
        adapterPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener()
        {
            @Override
            public void onDismiss()
            {
                if (mCheckedView.isChecked())
                {
                    radioGroup.clearCheck();
                }
            }
        });
        
        lotteries = new ArrayList<>();
        
        popupAdapter = new HistoryBetPopupWindowAdapter(getContext(), lotteries);
        popupAdapter.setOnIssueNoClickListener((Lottery lottery) ->
        {
            
            if (mCurrentQueryConditionType == LOTTERY_SELECT)
            {
                mCurrentLotterySelectId = lottery.getLotteryId();
                lotteryRadioButton.setText(lottery.getCname());
                mSelectLotteryPosition = mCurrentLotterySelectId;
                
            } else if (mCurrentQueryConditionType == MEMBER_SELECT)
            {
                selectPositionToMemberSelected(lottery.getLotteryId());
                memberRadioButton.setText(lottery.getCname());
                mSelectMemberPosition = lottery.getLotteryId();
                
            } else if (mCurrentQueryConditionType == TIME_SELECT)
            {
                selectPositionToTimeSelected(lottery.getLotteryId());
                timeRadioButton.setText(lottery.getCname());
                mSelectTimePosition = lottery.getLotteryId();
            }
            //loadBetList(false, FIRST_PAGE);
            adapterPopupWindow.dismiss();
        });
        ListView popListView = rootView.findViewById(R.id.listView);
        popListView.setAdapter(popupAdapter);
    }
    
    private void selectPositionToMemberSelected(int position)
    {
        switch (position)
        {
            case 0://指定会员下级
                mCurrentMemberSelect = 0;
                break;
            case 1://指定会员及下级
                mCurrentMemberSelect = 1;
                break;
            default:
                mCurrentMemberSelect = 0;
                break;
        }
    }
    
    private void selectPositionToTimeSelected(int position)
    {
        switch (position)
        {
            case 0://"今天"
                mCurrentTimeSelectStart = TimeUtils.getBeginDateOfToday();
                mCurrentTimeSelectEnd = TimeUtils.getEndDateOfToday();
                break;
            case 1://"昨天"
                mCurrentTimeSelectStart = TimeUtils.getBeginDateOfYesterday();
                mCurrentTimeSelectEnd = TimeUtils.getEndDateOfYesterday();
                break;
            case 2://"近3天"
                mCurrentTimeSelectStart = TimeUtils.getLatelyDateOfThree();
                mCurrentTimeSelectEnd = TimeUtils.getEndDateOfToday();
                break;
            case 3://"近7天"
                mCurrentTimeSelectStart = TimeUtils.getLatelyDateOfSeven();
                mCurrentTimeSelectEnd = TimeUtils.getEndDateOfToday();
                break;
            case 4://"近15天"
                mCurrentTimeSelectStart = TimeUtils.getLatelyDateOf15();
                mCurrentTimeSelectEnd = TimeUtils.getEndDateOfToday();
                break;
            case 5://"近一个月"
                mCurrentTimeSelectStart = TimeUtils.getLatelyDateOf30();
                mCurrentTimeSelectEnd = TimeUtils.getEndDateOfToday();
                break;
            default:
        }
    }
    
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id)
    {
        Lottery lottery;
        List<Lottery> lotteryList;
        switch (id)
        {
            case R.id.lotteryRadioButton:
                mCheckedView = lotteryRadioButton;
                if (!mCheckedView.isChecked())
                {
                    return;
                }
                mCurrentQueryConditionType = LOTTERY_SELECT;
                //彩种选择的时候 时间和状态选择就不可点击了，彩种全部显示出来后才让时间和状态选择就可以点击
                memberRadioButton.setEnabled(false);
                timeRadioButton.setEnabled(false);
                
                loadLotteryList();//彩票种类信息查询
                break;
            case R.id.memberRadioButton:
                mCheckedView = memberRadioButton;
                if (!mCheckedView.isChecked())
                {
                    return;
                }
                mCurrentQueryConditionType = MEMBER_SELECT;
                lotteryList = new ArrayList<>();
                String[] memberStates = new String[]{"指定会员", "指定会员及下级"};
                for (int i = 0; i < memberStates.length; i++)
                {
                    lottery = new Lottery();
                    lottery.setLotteryId(i);
                    lottery.setCname(memberStates[i]);
                    lotteryList.add(lottery);
                }
                
                popupAdapter.setData(lotteryList, mSelectMemberPosition);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);
                break;
            case R.id.timeRadioButton:
                mCheckedView = timeRadioButton;
                if (!mCheckedView.isChecked())
                {
                    return;
                }
                mCurrentQueryConditionType = TIME_SELECT;
                lotteryList = new ArrayList<>();
                String[] timeStates = new String[]{"今天", "昨天", "近3天", "近7天", "近15天", "近一个月"};
                for (int i = 0; i < timeStates.length; i++)
                {
                    lottery = new Lottery();
                    lottery.setLotteryId(i);
                    lottery.setCname(timeStates[i]);
                    lotteryList.add(lottery);
                }
                
                popupAdapter.setData(lotteryList, mSelectTimePosition);
                adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up);
                break;
        }
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case LOTTERY_LIST_COMMAND:
                    LotteryMenu lotteryMenu = (LotteryMenu) response.getData();
                    
                    ArrayList<Lottery> lotteryList = new ArrayList<>();
                    ArrayList<Lottery> sscLotteryList = new ArrayList<>();
                    ArrayList<Lottery> syxwLotteryList = new ArrayList<>();
                    ArrayList<Lottery> ksLotteryList = new ArrayList<>();
                    ArrayList<Lottery> lowLotteryList = new ArrayList<>();
                    ArrayList<Lottery> othersLotteryList = new ArrayList<>();
                    
                    Lottery headLottery = new Lottery();
                    headLottery.setLotteryId(-1);
                    headLottery.setCname("选择彩种");
                    lotteryList.add(0, headLottery);
                    
                    if (lotteryMenu.getSsc() != null)
                        sscLotteryList = lotteryMenu.getSsc();
                    if (lotteryMenu.getSyxw() != null)
                        syxwLotteryList = lotteryMenu.getSyxw();
                    if (lotteryMenu.getKs() != null)
                        ksLotteryList = lotteryMenu.getKs();
                    if (lotteryMenu.getLow() != null)
                        lowLotteryList = lotteryMenu.getLow();
                    if (lotteryMenu.getOthers() != null)
                        othersLotteryList = lotteryMenu.getOthers();
                    lotteryList.addAll(sscLotteryList);
                    lotteryList.addAll(syxwLotteryList);
                    lotteryList.addAll(ksLotteryList);
                    lotteryList.addAll(lowLotteryList);
                    lotteryList.addAll(othersLotteryList);
                    
                    popupAdapter.setData(lotteryList, mSelectLotteryPosition);
                    
                    adapterPopupWindow.showArrowTo(mCheckedView, BubbleStyle.ArrowDirection.Up, 1);
                    
                    timeRadioButton.setEnabled(true);
                    memberRadioButton.setEnabled(true);
                    
                    break;
                case CHILD_PACKAGE_LIST_COMMAND:
                    if (response.getData() == null || !(response.getData() instanceof MemberOrderResponse))
                    {
                        items.clear();
                    } else
                    {
                        totalCount = ((MemberOrderResponse) response.getData()).getPackageBeans().size();
                        if (page == FIRST_PAGE)
                        {
                            items.clear();
                        }
                        items.addAll(((MemberOrderResponse) response.getData()).getPackageBeans());
                    }
                    gameHistoryAdapter.setData(items);
                    
                    break;
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
            if ((request.getId() == CHILD_PACKAGE_LIST_COMMAND))
            {
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
                //解决由于条件切换 没有合适的数据 要清空listview数据
                //                items.clear();
                //                adapter.setData(items);
            }
        }
    };
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
    
    @OnClick(R.id.query)
    public void onViewClicked()
    {
        loadMemberOrder(false, 1);
    }
}
