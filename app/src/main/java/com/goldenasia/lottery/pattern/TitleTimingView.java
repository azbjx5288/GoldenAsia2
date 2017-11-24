package com.goldenasia.lottery.pattern;

import android.app.Activity;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CountdownView;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.IssueInfo;
import com.goldenasia.lottery.data.IssueInfoCommand;
import com.goldenasia.lottery.data.IssueLastCommand;
import com.goldenasia.lottery.data.LastIssueInfo;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.PeriodInfo;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.goldenasia.lottery.material.ConstantInformation.ONE_DAY;
import static com.goldenasia.lottery.material.ConstantInformation.ONE_HOUR;
import static com.goldenasia.lottery.material.ConstantInformation.ONE_MINUTE;
import static com.goldenasia.lottery.material.ConstantInformation.ONE_SECOND;

/**
 * 开奖与销售 倒计时
 * Created by ACE-PC on 2016/1/18.
 */
public class TitleTimingView
{
    private String Tag = TitleTimingView.class.getSimpleName();
    private final long TRACK_INTERVAL_SHORT = 8;
    private final long TRACK_INTERVAL_LONG = 50;
    private static final int ISSUE_TRACE_ID = 2;
    private static final int OPEN_ISSUE_TRACE_ID = 3;
    private static final long RETRY_INTERVAL = 15 * ONE_SECOND;
    
    private Activity activity;
    private View view;
    private TextView agoIssueTextView;
    private TextView openCodeTextView;
    private TextView salesIssueView;
    private CountdownView salesTimeView;
    
    private boolean isSelling;
    private Lottery lottery;
    private String issue;
    private String issueLast;
    private LastIssueInfo lastIssueInfo;
    private PeriodInfo periodInfo;
    private DateFormat df = new SimpleDateFormat("yyyyMMdd");
    
    private Handler getLastHandler;
    private Handler getCurHandler;
    private Runnable getLastRunnable;
    private Runnable getCurRunnable;
    
    /**
     * 带Context的构造器
     *
     * @param view
     */
    
    public TitleTimingView(Activity activity, View view, Lottery lottery)
    {
        this.view = view;
        this.activity = activity;
        this.lottery = lottery;
        init();
    }
    
    public TitleTimingView(LayoutInflater inflater, int id)
    {
        this.view = inflater.inflate(id, null);
        init();
    }
    
    private Lottery getLottery()
    {
        return lottery;
    }
    
    public void setLottery(Lottery lottery)
    {
        this.lottery = lottery;
    }
    
    public PeriodInfo getPeriodInfo()
    {
        return periodInfo;
    }
    
    public void setPeriodInfo(PeriodInfo periodInfo)
    {
        this.periodInfo = periodInfo;
    }
    
    public LastIssueInfo getLastIssueInfo()
    {
        return lastIssueInfo;
    }
    
    public void setLastIssueInfo(LastIssueInfo lastIssueInfo)
    {
        this.lastIssueInfo = lastIssueInfo;
    }
    
    public String getIssueLast()
    {
        return issueLast;
    }
    
    public void setIssueLast(String issueLast)
    {
        this.issueLast = issueLast;
    }
    
    public String getIssue()
    {
        return issue;
    }
    
    public void setIssue(String issue)
    {
        this.issue = issue;
    }
    
    private void init()
    {
        
        agoIssueTextView = (TextView) view.findViewById(R.id.timing_title_agoissue);
        openCodeTextView = (TextView) view.findViewById(R.id.timing_title_opennumber);
        salesIssueView = (TextView) view.findViewById(R.id.timing_title_salesissue);
        salesTimeView = (CountdownView) view.findViewById(R.id.timing_title_salestime);
        
        isSelling = true;
        issue = "";
        issueLast = "";
        
        getCurHandler = new Handler();
        getCurRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                getSalesInfo();
            }
        };
        
        getLastHandler = new Handler();
        getLastRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                getCode();
                getLastHandler.removeCallbacks(this);
            }
        };
        
        setListener();
        //salesTimeView.start((long) 0);
        getAwardPeriod();
    }
    
    /**
     * 设置监听
     */
    private void setListener()
    {
        salesTimeView.setOnCountdownEndListener((cv) ->
        {
            //当销售结束后 开启开奖倒计时
            if (isSelling)
            {
                waitForOpenLottery(getInterval());
            } else
            {
                getSalesInfo();
            }
        });
    }
    
    public void stop()
    {
        if (salesTimeView != null)
            salesTimeView.stop();
        closeRequest();
    }
    
    /**
     * 初使化开奖与销售信息
     *
     * @param periodInfo
     */
    public void initLotteryInfoHead(PeriodInfo periodInfo)
    {
        if (periodInfo != null)
            setSalesLottery(periodInfo);
        else
            setSalesLottery(new PeriodInfo());
    }
    
    private void setSalesLottery(PeriodInfo periodInfo)
    {
        updateSalesInfo(periodInfo);
        updateSalesLottery(periodInfo.getOpenIssue(), periodInfo.getOpenCode());
    }
    
    private void updateSalesInfo(PeriodInfo periodInfo)
    {
        int daySales = 0, hourSales = 0, minuteSales = 0, secondSales = 0;
        if (periodInfo.getSalesTime() != null)
        {
            isSelling = true;
            daySales = periodInfo.getSalesTime().getDay() > 0 ? periodInfo.getSalesTime().getDay() : 0;
            hourSales = periodInfo.getSalesTime().getHour() > 0 ? periodInfo.getSalesTime().getHour() : 0;
            minuteSales = periodInfo.getSalesTime().getMinute() > 0 ? periodInfo.getSalesTime().getMinute() : 0;
            secondSales = periodInfo.getSalesTime().getSecond() > 0 ? periodInfo.getSalesTime().getSecond() : 0;
            Log.e("peri", "day:" + daySales + ",hour:" + hourSales + ",minute:" + minuteSales + ",second:" +
                    secondSales);
        }
        setUpdateSalesLottery(periodInfo.getSalesIssue(), daySales * ONE_DAY + hourSales * ONE_HOUR + minuteSales *
                ONE_MINUTE + secondSales * ONE_SECOND);
        getIntervalTimer(getInterval());
    }
    
    /**
     * 更新销售信息
     */
    private void setUpdateSalesLottery(String salesIssue, long time)
    {
        Date curDate = new Date(System.currentTimeMillis());
        salesTimeView.start(time);
        salesIssueView.setText(salesIssue != null && salesIssue.length() > 0 ? salesIssue : df.format(curDate) + "-"
                + "****期");
    }
    
    /**
     * 更新开奖号码
     */
    private void updateSalesLottery(String openIssue, String codeOpen)
    {
        Date curDate = new Date(System.currentTimeMillis());
        agoIssueTextView.setText(openIssue != null && openIssue.length() > 0 ? openIssue : df.format(curDate) + "-" +
                "****期");
        openCodeTextView.setText(codeOpen != null && codeOpen.length() > 0 ? codeOpen : "-\t-\t-\t-\t-");
        getIntervalTimer(getInterval());
    }
    
    /**
     * 销售完成后 等待开奖倒计时触发
     */
    private void waitForOpenLottery(long second)
    {
        isSelling = false;
        salesTimeView.start((long) 0 * ONE_HOUR + 0 * ONE_MINUTE + second);
    }
    
    /**
     * 获取销售
     */
    private void getSalesInfo()
    {
        getAwardPeriod();
        if (periodInfo != null)
        {
            updateSalesInfo(periodInfo);
            if (TextUtils.isEmpty(periodInfo.getOpenCode()))
                getCurHandler.postDelayed(getCurRunnable, RETRY_INTERVAL);
            else if (getCurHandler != null)
                getCurHandler.removeCallbacks(getCurRunnable);
        } else
            salesTimeView.stop();
        
    }
    
    /**
     * 获取号码
     */
    private void getCode()
    {
        updateOpenPeriod();
        if (getLastIssueInfo() != null)
        {
            String lastCode = "";
            if (getLastIssueInfo().getIssueInfo() != null)
            {
                lastCode = getLastIssueInfo().getIssueInfo().getCode();
                updateSalesLottery(getLastIssueInfo().getIssueInfo().getIssue(),lastCode );
                if (TextUtils.isEmpty(lastCode))
                    getLastHandler.postDelayed(getLastRunnable, RETRY_INTERVAL);
                else /*if (getLastHandler != null)*/
                    getLastHandler.removeCallbacks(getLastRunnable);
            }
        }
    }
    
    /**
     * 间隔获取
     */
    private void getIntervalTimer(long interval)
    {
        salesTimeView.setOnCountdownIntervalListener(interval, (cv, t) -> getCode());
    }
    
    private long getInterval()
    {
        long interval = 0;
        if (getLottery() != null)
        {
            /*switch (getLottery().getLotteryId())
            {
                case 11:
                case 13:
                case 15:
                case 16:
                case 19:
                case 25:
                case 26:
                    interval = TRACK_INTERVAL_SHORT * 1000;
                    break;
                default:
                    interval = TRACK_INTERVAL_LONG * 1000;
                    break;
            }*/
            interval = TRACK_INTERVAL_SHORT * 1000;
        }
        return interval;
    }
    
    /**
     * 加截开奖信息  @param lottery
     * getCurIssue
     */
    private void getAwardPeriod()
    {
        IssueInfoCommand command = new IssueInfoCommand();
        command.setLotteryId(lottery.getLotteryId());
        command.setOp("getCurIssue");
        TypeToken typeToken = new TypeToken<RestResponse<IssueInfo>>()
        {};
        RestRequest restRequest = RestRequestManager.createRequest(activity, command, typeToken, restCallback,
                ISSUE_TRACE_ID, this);
        /*RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof IssueInfo)
        {
            updateIssueInfo((IssueInfo) restResponse.getData());
            initLotteryInfoHead(getPeriodInfo());
        }*/
        restRequest.execute();
    }
    
    /**
     * getLastIssueCode
     */
    private void updateOpenPeriod()
    {
        IssueLastCommand command = new IssueLastCommand();
        command.setLotteryId(lottery.getLotteryId());
        command.setOp("getLastIssueCode");
        command.setIssue(getIssueLast());
        TypeToken typeToken = new TypeToken<RestResponse<LastIssueInfo>>()
        {};
        RestRequest restRequest = RestRequestManager.createRequest(activity, command, typeToken, restCallback,
                OPEN_ISSUE_TRACE_ID, this);
        /*RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof LastIssueInfo)
        {
            LastIssueInfo issueCode = (LastIssueInfo) restResponse.getData();
            setLastIssueInfo(issueCode);
        }*/
        restRequest.execute();
    }
    
    /**
     * 解析奖期信息
     */
    private void updateIssueInfo(IssueInfo issueInfo)
    {
        PeriodInfo periodInfo = new PeriodInfo();
        if (issueInfo == null)
            setPeriodInfo(new PeriodInfo());
        else
        {
            int index = issueInfo.getServerTime().indexOf("/");
            String currenttime = index != -1 ? issueInfo.getServerTime().replaceAll("/", "-") : "";
            periodInfo.setSalesTime(ConstantInformation.getLasttime(currenttime, issueInfo.getIssueInfo().getEndtime
                    ()));
            periodInfo.setSalesIssue(issueInfo.getIssueInfo().getIssue());
            setIssue(issueInfo.getIssueInfo().getIssue());
            if (issueInfo.getLastIssueInfo() != null)
            {
                setIssueLast(issueInfo.getLastIssueInfo().getIssue());
                periodInfo.setOpenIssue(issueInfo.getLastIssueInfo().getIssue());
                periodInfo.setOpenCode(issueInfo.getLastIssueInfo().getCode());
            } else
            {
                periodInfo.setOpenIssue("");
                periodInfo.setOpenCode("");
            }
            setPeriodInfo(periodInfo);
        }
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            if (request.getId() == ISSUE_TRACE_ID)
            {
                updateIssueInfo((IssueInfo) response.getData());
                initLotteryInfoHead(getPeriodInfo());
            } else if (request.getId() == OPEN_ISSUE_TRACE_ID)
            {
                LastIssueInfo issueCode = (LastIssueInfo) response.getData();
                setLastIssueInfo(issueCode);
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 8002)
            {
                return true;
            } else if (errCode == 7003)
            {
                Toast.makeText(activity, "止奖期未开放销售", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006)
            {
                stop();
                CustomDialog dialog = PromptManager.showCustomDialog(activity, "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {
        }
    };
    
    private void closeRequest()
    {
        RestRequestManager.cancelAll(this);
        if (getLastHandler != null)
            getLastHandler.removeCallbacks(getLastRunnable);
        if (getCurHandler != null)
            getCurHandler.removeCallbacks(getCurRunnable);
    }
}

