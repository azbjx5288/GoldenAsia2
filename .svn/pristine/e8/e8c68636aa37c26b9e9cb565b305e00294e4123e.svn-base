package com.goldenasia.lottery.pattern;

import android.app.Activity;
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
 * 销售倒计时
 * Created by ACE-PC on 2016/2/5.
 */
public class TitleTimingSalesView {
    private static final long TRACK_INTERVAL_SHORT = 8;
    private static final long TRACK_INTERVAL_LONG = 50;
    private static final int ISSUE_TRACE_ID = 2;

    private Activity activity;
    private View view;
    private TextView salesIssueView;
    private CountdownView salesTimeView;

    /**
     * 销售状态
     */
    private boolean isSelling;
    /**
     * 请求次数
     */
    private int requestCount;

    private Lottery lottery;
    private String issue;
    private String issueLast;
    private PeriodInfo periodInfo;
    private OnSalesIssueListener onSalesIssueListener;
    private DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public TitleTimingSalesView(Activity activity, View view, Lottery lottery) {
        this.view = view;
        this.activity = activity;
        this.lottery = lottery;
        init();
    }

    public TitleTimingSalesView(View view) {
        this.view = view;
        init();
    }

    private Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public PeriodInfo getPeriodInfo() {
        return periodInfo;
    }

    public void setPeriodInfo(PeriodInfo periodInfo) {
        this.periodInfo = periodInfo;
    }

    public String getIssueLast() {
        return issueLast;
    }

    public void setIssueLast(String issueLast) {
        this.issueLast = issueLast;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public OnSalesIssueListener getOnSalesIssueListener() {
        return onSalesIssueListener;
    }

    public void setOnSalesIssueListener(OnSalesIssueListener onSalesIssueListener) {
        this.onSalesIssueListener = onSalesIssueListener;
    }

    private void init() {
        salesIssueView = (TextView) view.findViewById(R.id.timing_sales_issue_label);
        salesTimeView = (CountdownView) view.findViewById(R.id.timing_sales_time);
        isSelling = true;
        requestCount = 0;
        issue = "";
        issueLast = "";
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        salesTimeView.setOnCountdownEndListener((cv) -> {
            //当销售结束后 开启开奖倒计时
            if (isSelling) {
                waitForOpenLottery(getInterval());
            } else {
                getSalesInfo();
            }
        });
        salesTimeView.start(0);
        getAwardPeriod();
    }

    public void stop() {
        if (salesTimeView != null)
            salesTimeView.stop();
        closeRequest();
    }

    /**
     * 初使化开奖与销售信息
     *
     * @param periodInfo
     */
    public void initLotteryInfoHead(PeriodInfo periodInfo) {
        if (periodInfo != null)
            updateSalesInfo(periodInfo);
        else{
            if (onSalesIssueListener != null) {
                onSalesIssueListener.onSalesIssue("");
            }
        }
    }

    /**
     * 更新开奖与销售信息
     *
     * @param periodInfo
     */
    private void updateSalesInfo(PeriodInfo periodInfo) {
        int daySales = 0, hourSales = 0, minuteSales = 0, secondSales = 0;
        if (periodInfo.getSalesTime() != null) {
            daySales = periodInfo.getSalesTime().getDay() > 0 ? periodInfo.getSalesTime().getDay() : 0;
            hourSales = periodInfo.getSalesTime().getHour() > 0 ? periodInfo.getSalesTime().getHour() : 0;
            minuteSales = periodInfo.getSalesTime().getMinute() > 0 ? periodInfo.getSalesTime().getMinute() : 0;
            secondSales = periodInfo.getSalesTime().getSecond() > 0 ? periodInfo.getSalesTime().getSecond() : 0;
        }
        updateSalesLottery(periodInfo.getSalesIssue(), daySales * ONE_DAY + hourSales * ONE_HOUR + minuteSales * ONE_MINUTE + secondSales * ONE_SECOND);
    }

    /**
     * 更新销售信息
     */
    private void updateSalesLottery(String salesIssue, long time) {
        Date curDate = new Date(System.currentTimeMillis());
        isSelling = true;
        salesTimeView.start(time);
        salesIssueView.setText(salesIssue != null && salesIssue.length() > 0 ? salesIssue : df.format(curDate) + "-" + "****期");
        if (onSalesIssueListener != null)
            onSalesIssueListener.onSalesIssue(salesIssue != null && salesIssue.length() > 0 ? salesIssue : "");
    }

    /**
     * 销售完成后 等待开奖倒计时触发
     */
    private void waitForOpenLottery(long second) {
        isSelling = false;
        salesTimeView.start(0 * ONE_HOUR + 0 * ONE_MINUTE + second * ONE_SECOND);
    }

    /**
     * 获取销售
     */
    private void getSalesInfo() {
        getAwardPeriod();
        if (periodInfo != null) {
            requestCount = 0;
            updateSalesInfo(periodInfo);
        } else {
            if (requestCount >= 3) {
                salesTimeView.stop();
                return;
            }
            waitForOpenLottery(getInterval());
            requestCount++;
            return;
        }
    }

    private long getInterval() {
        long interval = 0;
        if (getLottery() != null) {
            switch (getLottery().getLotteryId()) {
                case 11:
                case 13:
                case 16:
                case 49:
                    interval = TRACK_INTERVAL_SHORT;
                    break;
                default:
                    interval = TRACK_INTERVAL_LONG;
                    break;
            }
        }
        return interval;
    }

    /**
     * 加截开奖信息
     */
    private void getAwardPeriod() {
        IssueInfoCommand command = new IssueInfoCommand();
        command.setLotteryId(lottery.getLotteryId());
        command.setOp("getCurIssue");
        TypeToken typeToken = new TypeToken<RestResponse<IssueInfo>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(activity, command, typeToken, restCallback, ISSUE_TRACE_ID, this);
        restRequest.execute();
    }

    /**
     * 解析奖期信息
     */
    private void updateIssueInfo(IssueInfo issueInfo) {
        if (issueInfo == null)
            setPeriodInfo(new PeriodInfo());
        else {
            int index = issueInfo.getServerTime().indexOf("/");
            String currenttime = index != -1 ? issueInfo.getServerTime().replaceAll("/", "-") : "";
            PeriodInfo periodInfo = new PeriodInfo();
            periodInfo.setSalesTime(ConstantInformation.getLasttime(currenttime, issueInfo.getIssueInfo().getEndtime()));
            periodInfo.setSalesIssue(issueInfo.getIssueInfo().getIssue());
            setIssue(issueInfo.getIssueInfo().getIssue());
            /*if (issueInfo.getLastIssueInfo() != null) {
                setIssueLast(issueInfo.getLastIssueInfo().getIssue());
                periodInfo.setOpenIssue(issueInfo.getLastIssueInfo().getIssue());
                periodInfo.setOpenCode(issueInfo.getLastIssueInfo().getCode());
            } else {
                periodInfo.setOpenIssue("");
                periodInfo.setOpenCode("");
            }*/
            setPeriodInfo(periodInfo);
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ISSUE_TRACE_ID) {
                updateIssueInfo((IssueInfo) response.getData());
                initLotteryInfoHead(getPeriodInfo());
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 8002) {
                return true;
            } else if (errCode == 7003) {
                Toast.makeText(activity, "止奖期未开放销售", Toast.LENGTH_LONG).show();
                stop();
                return true;
            } else if (errCode == 7006) {
                stop();
                CustomDialog dialog = PromptManager.showCustomDialog(activity, "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

    private void closeRequest() {
        RestRequestManager.cancelAll(this);
    }

    public interface OnSalesIssueListener {
        void onSalesIssue(String issue);
    }
}
