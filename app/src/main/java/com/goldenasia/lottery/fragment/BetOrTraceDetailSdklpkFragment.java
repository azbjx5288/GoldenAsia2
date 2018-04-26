package com.goldenasia.lottery.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
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
import com.goldenasia.lottery.data.Bet;
import com.goldenasia.lottery.data.BetDetailCommand;
import com.goldenasia.lottery.data.BetDetailResponse;
import com.goldenasia.lottery.data.CancelPackageCommand;
import com.goldenasia.lottery.data.CancelTraceCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Trace;
import com.goldenasia.lottery.data.TraceDetailCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.UiUtils;
import com.google.gson.reflect.TypeToken;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;


public class BetOrTraceDetailSdklpkFragment extends BaseFragment {
    private static final String TAG = BetOrTraceDetailSdklpkFragment.class.getSimpleName();

    private static final int BET_DETAIL_ID = 1;
    private static final int TRACE_DETAIL_ID = 2;
    private static final int CANCEL_PACKAGE_ID = 3;
    private static final int CANCEL_TRACE_ID = 4;

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.issue)
    TextView issue;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.wrap_id)
    TextView wrap_id;
    @BindView(R.id.create_time)
    TextView create_time;
    @BindView(R.id.multiple)
    TextView multiple;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.prizeMode)
    TextView prizeMode;
    @BindView(R.id.modeName)
    TextView modeName;
    @BindView(R.id.prize)
    TextView prize;
    @BindView(R.id.openCode_image01)
    ImageView openCode_image01;
    @BindView(R.id.openCode_tv01)
    TextView openCode_tv01;
    @BindView(R.id.openCode_image02)
    ImageView openCode_image02;
    @BindView(R.id.openCode_tv02)
    TextView openCode_tv02;
    @BindView(R.id.openCode_image03)
    ImageView openCode_image03;
    @BindView(R.id.openCode_tv03)
    TextView openCode_tv03;
    @BindView(R.id.list_view)
    ListView list_view;


    @BindView(R.id.button)
    Button button;

    private boolean isBet;
    private Bet bet;
    private Trace trace;
    private Lottery lottery;
    private String[] pkids;

    private BetDetailResponse mBetDetailResponse;

    public static void launch(BaseFragment fragment, Bet bet) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", true);
        bundle.putBoolean("type", false);
        bundle.putString("Bet", GsonHelper.toJson(bet));
        FragmentLauncher.launch(fragment.getActivity(), BetOrTraceDetailSdklpkFragment.class, bundle);
    }

    public static void launch(BaseFragment fragment, Trace trace) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", false);
        bundle.putBoolean("type", false);
        bundle.putString("Trace", GsonHelper.toJson(trace));
        FragmentLauncher.launch(fragment.getActivity(), BetOrTraceDetailSdklpkFragment.class, bundle);
    }

    public static void launch(BaseFragment fragment,Lottery lottery, Bet bet) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", true);
        bundle.putBoolean("type", true);
        bundle.putSerializable("lottery", lottery);
        bundle.putString("Bet", GsonHelper.toJson(bet));
        FragmentLauncher.launch(fragment.getActivity(), BetOrTraceDetailSdklpkFragment.class, bundle);
    }

    public static void launch(BaseFragment fragment,Lottery lottery, Trace trace) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBet", false);
        bundle.putBoolean("type", true);
        bundle.putSerializable("lottery", lottery);
        bundle.putString("Trace", GsonHelper.toJson(trace));
        FragmentLauncher.launch(fragment.getActivity(), BetOrTraceDetailSdklpkFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isBet = getArguments().getBoolean("isBet");
        return inflateView(inflater, container, isBet ? "注单详情" : "追号详情", R.layout.bet_or_trace_detail_sdklpk);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments().getBoolean("type")){
            lottery = (Lottery) getArguments().getSerializable("lottery");
        }
        if (isBet) {
            loadBetData();
        } else {
            //处理刮刮乐
            loadTraceData();
        }
    }

    private void loadBetData() {
        bet = GsonHelper.fromJson(getArguments().getString("Bet"), Bet.class);
        if (bet == null) {
            Toast.makeText(getActivity(), "无效注单参数", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        refreshBet(false);
    }

    private void loadTraceData() {
        trace = GsonHelper.fromJson(getArguments().getString("Trace"), Trace.class);
        if (trace == null) {
            Toast.makeText(getActivity(), "无效追号参数", Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        refreshTrace(true);
    }

    private void refreshTrace(boolean withCache) {
        TraceDetailCommand command = new TraceDetailCommand();
        command.setId(trace.getWrapId());
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback, TRACE_DETAIL_ID, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null) {
                JsonString jsonString = (JsonString) restResponse.getData();
                if (jsonString != null) {
//                    jsonDataForWeb = jsonString.getJson();
//                    update2WebView();
                }
            }
        }
        restRequest.execute();
    }

    private void refreshBet(boolean withCache) {
        BetDetailCommand command = new BetDetailCommand();
        command.setId(bet.getWrapId());

        TypeToken typeToken = new TypeToken<RestResponse<BetDetailResponse>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, BET_DETAIL_ID, this);
    }

    private class JsInterface {
        @JavascriptInterface
        public String getData() {
            return "";
        }

        @JavascriptInterface
        public void changeUi(final int lotteryId, final boolean supportCancel, final String[] pkids) {
            BetOrTraceDetailSdklpkFragment.this.pkids = pkids;
            button.post(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, GoldenAsiaApp.getUserCentre().getLottery(lotteryId) + "");
                    if(!getArguments().getBoolean("type")){
                        lottery = GoldenAsiaApp.getUserCentre().getLottery(lotteryId);
                    }
                    if (lottery == null) {
                        findViewById(R.id.bottom).setVisibility(View.GONE);
                    }

                    button.setTag(supportCancel);
                    if (isBet) {
                        button.setText(supportCancel ? "撤单" : "去购彩");
                    } else {
                        if (supportCancel && BetOrTraceDetailSdklpkFragment.this.pkids != null && BetOrTraceDetailSdklpkFragment.this.pkids.length > 0) {
                            button.setText("撤单");
                        } else {
                            button.setText("去购彩");
                        }
                    }
                }
            });
        }

        @JavascriptInterface
        public void allowCancelTrace(final boolean allow) {
            button.post(() -> {
                if (allow) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("取消追号")
                            .setMessage("确定要取消追号？")
                            .setPositiveButton("取消追号", (dialog, which) -> {
                                CancelTraceCommand command = new CancelTraceCommand();
                                command.setWrapId(trace.getWrapId());
                                command.setPkids(Arrays.asList(pkids));
                                executeCommand(command, restCallback, CANCEL_TRACE_ID);
                            })
                            .setNegativeButton("保留追号", null)
                            .create().show();
                } else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("提醒")
                            .setMessage("已经过了取消追号的时间，不能取消追号")
                            .setNegativeButton("确定", null)
                            .create().show();
                }
            });
        }
    }

    @OnClick(R.id.button)
    public void onClick(View v) {
        boolean supportCancel = (v.getTag()==null?true:(boolean) v.getTag());//为返回数据异常所做的防崩溃处理\
        if(!getArguments().getBoolean("type")){
            lottery = GoldenAsiaApp.getUserCentre().getLottery(mBetDetailResponse.getBet().getLotteryId());
        }
        if (!supportCancel) {
            GameTableFragment.launch(BetOrTraceDetailSdklpkFragment.this, lottery);
            return;
        }

        if (isBet) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("取消订单")
                    .setMessage("确定要取消订单？")
                    .setPositiveButton("取消订单", (dialog, which) -> {
                        CancelPackageCommand command = new CancelPackageCommand();
                        command.setWrapId(bet.getWrapId());
                        executeCommand(command, restCallback, CANCEL_PACKAGE_ID);
                    })
                    .setNegativeButton("保留订单", null)
                    .create().show();
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                webView.evaluateJavascript("reviewStatus();", null);
//            } else {
//                webView.loadUrl("javascript:reviewStatus();");
//            }
        }
    }

    private RestCallback restCallback = new RestCallback<BetDetailResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<BetDetailResponse> response) {
            switch (request.getId()) {
                case BET_DETAIL_ID:
                    mBetDetailResponse = response.getData();
                    refreshView();
                    break;
                case TRACE_DETAIL_ID:
                   // jsonDataForWeb = ((JsonString) response.getData()).getJson();
                    refreshView();
                    break;
                case CANCEL_PACKAGE_ID:
                    refreshBet(false);
                    break;
                case CANCEL_TRACE_ID:
                    refreshTrace(false);
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                if (request.getId() == BET_DETAIL_ID) {
                    showProgress("进行中...");
                }
            } else {
                hideWaitProgress();
            }
        }
    };

    private void refreshView() {
        name.setText(mBetDetailResponse.getCame());
        issue.setText(mBetDetailResponse.getBet().getIssue());

        switch(mBetDetailResponse.getBet().getCheckPrizeStatus()){
            case 0:
                if(mBetDetailResponse.getBet().getCancelStatus()==0){
                    status.setText("未开奖");
                    status.setTextColor(Color.parseColor("#adadad"));
                }else if(mBetDetailResponse.getBet().getCancelStatus() ==1){
                    status.setText("用户撤单");
                    status.setTextColor(Color.parseColor("#505050"));
                }else if(mBetDetailResponse.getBet().getCancelStatus() ==2){
                    status.setText("追中撤单");
                    status.setTextColor(Color.parseColor("#505050"));
                }else if(mBetDetailResponse.getBet().getCancelStatus() ==3){
                    status.setText("出号撤单");
                    status.setTextColor(Color.parseColor("#505050"));
                }else if(mBetDetailResponse.getBet().getCancelStatus() ==4){
                    status.setText("未开撤单");
                    status.setTextColor(Color.parseColor("#505050"));
                }else if(mBetDetailResponse.getBet().getCancelStatus() ==9){
                    status.setText("管理员撤单");
                    status.setTextColor(Color.parseColor("#505050"));
                }else{
                    status.setText("未知状态");
                    status.setTextColor(Color.parseColor("#505050"));
                }

                break;
            case 1:
                status.setText("已中奖");
                status.setTextColor(Color.parseColor("#b31045"));
                break;
            case 2:
                status.setText("未中奖");
                status.setTextColor(Color.parseColor("#39a78a"));
                break;
        }

        wrap_id.setText(mBetDetailResponse.getBet().getWrapId());
        create_time.setText(mBetDetailResponse.getBet().getCreateTime());
        multiple.setText(mBetDetailResponse.getBet().getMultiple()+"");
        amount.setText(mBetDetailResponse.getBet().getAmount()+"元");

        prizeMode.setText(mBetDetailResponse.getPrizeMode()+"");

        switch (mBetDetailResponse.getBet().getModes()){
            case "1":
                modeName.setText("元");
                break;
            case  "0.1":
                modeName.setText("元");
                break;
            case  "0.01":
                modeName.setText("分");
                break;
            case  "0.001":
                modeName.setText("厘");
                break;
        }
        //开奖号码
        String codeOpen=mBetDetailResponse.getOpenCode();
        if(codeOpen.length()>=7) {
            openCode_tv01.setText(codeOpen.charAt(0) + "");
            openCode_tv02.setText(codeOpen.charAt(3) + "");
            openCode_tv03.setText(codeOpen.charAt(6) + "");

            //图片
            openCode_image01.setImageDrawable(letterToDrawable(codeOpen.charAt(1) + ""));
            openCode_image02.setImageDrawable(letterToDrawable(codeOpen.charAt(4) + ""));
            openCode_image03.setImageDrawable(letterToDrawable(codeOpen.charAt(7) + ""));
        }

        prize.setText(mBetDetailResponse.getBet().getPrize()+"元");

        list_view.setAdapter(new MyAdapter());

        boolean supportCancel=mBetDetailResponse.getBet().getCancelStatus()==0&&mBetDetailResponse.getBet().getCheckPrizeStatus()==0;
        button.setText(supportCancel ? "撤单" : "去购彩");
        button.setTag(supportCancel);
    }

    //根据字母判定花色  花色♠s,♥h ♣c ♦d(一律小写)
    private Drawable letterToDrawable(String letter){
        if ("s".equals(letter)){
            return UiUtils.getDrawable(getContext(),R.drawable.ht_icon);
        }else if("h".equals(letter)){
            return UiUtils.getDrawable(getContext(),R.drawable.hongt_icon);
        }else if("c".equals(letter)){
            return UiUtils.getDrawable(getContext(),R.drawable.mh_icon);
        }else if("d".equals(letter)){
            return UiUtils.getDrawable(getContext(),R.drawable.fk_icon);
        }else{
            return null;
        }
    }

    class  MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mBetDetailResponse.getProjects().size();
        }

        @Override
        public Object getItem(int position) {
            return mBetDetailResponse.getProjects();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bet_or_trace_detail_sdklpk_item, null);

            TextView ll_name=  (TextView) convertView.findViewById(R.id.ll_name);
            TextView ll_code=  (TextView) convertView.findViewById(R.id.ll_code);
            TextView single_num=  (TextView) convertView.findViewById(R.id.single_num);
            LinearLayout prize_modell = (LinearLayout) convertView.findViewById(R.id.prizeMode);

            BetDetailResponse.ProjectsEntity projectsEntity=mBetDetailResponse.getProjects().get(position);

            ll_name.setText(projectsEntity.getCname());
            ll_code.setText(projectsEntity.getCode());
            single_num.setText(projectsEntity.getSingleNum()+"注");
            if(projectsEntity.getPrizeMode().size()==1){
                TextView tv=new TextView(getContext());
                BetDetailResponse.ProjectsEntity.PrizeMode prizeModeP= projectsEntity.getPrizeMode().get(0);
                tv.setText(prizeModeP.getPrize()+"/"+
                        (Float.parseFloat(prizeModeP.getCurRebate())*100)
                        +"%"
                );
                prize_modell.addView(tv);
            }else{
                for(int j=0;j<projectsEntity.getPrizeMode().size();j++){
                    TextView tv=new TextView(getContext());
                    BetDetailResponse.ProjectsEntity.PrizeMode prizeModeP= projectsEntity.getPrizeMode().get(0);
                    tv.setText(prizeModeP.getLevel()+"等："+prizeModeP.getPrize()+"/"+
                            (Float.parseFloat(prizeModeP.getCurRebate())*100)
                            +"%;"
                    );
                    prize_modell.addView(tv);
                }
            }


            return convertView;
        }
    }
}
