package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.game.Game;
import com.goldenasia.lottery.game.LhcGame;
import com.goldenasia.lottery.game.OnSelectedListener;
import com.goldenasia.lottery.material.ShoppingCart;
import com.goldenasia.lottery.material.Ticket;
import com.goldenasia.lottery.pattern.TitleTimingView;
import com.goldenasia.lottery.util.ToastUtils;
import com.phillipcalvin.iconbutton.IconButton;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 选号界面
 * Created by Alashi on 2016/2/17.
 */
public class GameFragment extends BaseFragment implements OnSelectedListener {
    private static final String TAG = GameFragment.class.getSimpleName();
    private static final int FLAG_MAX_PRIZE = 1;
    private static final int FLAG_MIN_PRIZE = 0;

    WebView webView;
    @BindView(R.id.pick_game_layout)
    LinearLayout pickGameLayout;
    @BindView(R.id.manualinput_layout)
    LinearLayout manualinputLayout;
    @BindView(R.id.pick_random)
    Button pickRandom;
    @BindView(R.id.pick_clear)
    IconButton pickClear;
    @BindView(R.id.pick_notice)
    TextView pickNotice;
    @BindView(R.id.choose_done_button)
    Button chooseDoneButton;
    @BindView(R.id.lottery_choose_bottom)
    RelativeLayout lotteryChooseBottom;
    @BindView(R.id.pick_title_view)
    View titleTimeView;
    @BindView(R.id.prize_mode_layout)
    RelativeLayout prizeModeLayout;
    @BindView(R.id.prize_mode_show)
    TextView prizeModeShow;

    private TitleTimingView timingView;
    private Lottery lottery;
    private Game game;
    private BubblePopupWindow bubblePopupWindow;
    private BubbleLinearLayout bubbleLinearLayout;
    private RadioGroup prizeGroup;
    private RadioButton maxButton;
    private RadioButton minButton;
    private ShoppingCart shoppingCart;
    private int prizeMode = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        loadTimingView();
        initRandom();
        initPrizeMode();
    }

    private void applyArguments() {
        shoppingCart = ShoppingCart.getInstance();
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    private void loadTimingView() {
        if (lottery.getLotteryId() == 15) {
            titleTimeView.setVisibility(View.GONE);
        } else {
            timingView = new TitleTimingView(getActivity(), findViewById(R.id.pick_title_view), lottery);
        }
    }

    private void initRandom() {
        if (lottery.getLotteryId() == 27)
            pickRandom.setVisibility(View.GONE);
    }

    private void initPrizeMode() {
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.popup_prize_mode, null);
        bubbleLinearLayout = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        bubblePopupWindow = new BubblePopupWindow(rootView, bubbleLinearLayout);
        prizeGroup = (RadioGroup) rootView.findViewById(R.id.prize_group);
        maxButton = (RadioButton) rootView.findViewById(R.id.max);
        minButton = (RadioButton) rootView.findViewById(R.id.min);

        prizeModeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bubblePopupWindow.showArrowTo(v, BubbleStyle.ArrowDirection.Down);
            }
        });
        prizeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.max:
                        changePrizeMode(FLAG_MIN_PRIZE, game.getMethod().getMaxRebate(), game.getMethod().getMaxRebatePrize());
                        break;
                    case R.id.min:
                        changePrizeMode(FLAG_MAX_PRIZE , game.getMethod().getMinRebate(), game.getMethod().getMinRebatePrize());
                        break;
                }
            }
        });
    }

    private void changePrizeMode(int prizeMode, float rebate, float rebatePrize) {
        this.prizeMode = prizeMode;
        prizeModeShow.setText(rebatePrize + "/" + String.format("%.1f%%", rebate * 100));
        bubblePopupWindow.dismiss();
    }

    private void initPrizes() {
        prizeGroup.check(R.id.min);
        prizeModeShow.setText(game.getMethod().getMinRebatePrize() + "/" + String.format("%.1f%%", game.getMethod().getMinRebate() * 100));
        maxButton.setText(game.getMethod().getMaxRebatePrize() + "/" + String.format("%.1f%%", game.getMethod().getMaxRebate() * 100));
        minButton.setText(game.getMethod().getMinRebatePrize() + "/" + String.format("%.1f%%", game.getMethod().getMinRebate() * 100));
        if (game.getMethod().getMinRebate() == game.getMethod().getMaxRebate())
            maxButton.setVisibility(View.GONE);
        else
            maxButton.setVisibility(View.VISIBLE);
    }

    private void loadWebViewIfNeed() {
        if (webView != null) {
            return;
        }
        lotteryChooseBottom.postDelayed(new Runnable() {
            @Override
            public void run() {
                synchronized (getActivity()) {
                    if (isFinishing()) {
                        return;
                    }
                    if (webView != null) {
                        update2WebView();
                        return;
                    }
                    webView = new WebView(getActivity());
                    lotteryChooseBottom.addView(webView, 1, 1);
                    initWebView();
                    webView.loadUrl("file:///android_asset/web/game.html");
                }
            }
        }, 0);
    }

    @Override
    public void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        if (timingView != null) {
            timingView.stop();
        }
        if (game != null) {
            game.destroy();
        }
        if (webView != null) {
            webView.destroy();
        }
        if (shoppingCart != null)
            shoppingCart.setPrizeMode(-1);
        super.onDestroyView();
    }

    /**
     * 清空
     */
    @OnClick(R.id.pick_clear)
    public void onClearPick() {
        if (game != null) {
            game.getSubmitArray();
            game.onClearPick(game);
            pickNotice.setText(String.format("共%d注", game.getSingleNum()));
        }
    }

    /**
     * 随机
     */
    @OnClick(R.id.pick_random)
    public void onRandom() {
        if (game != null) {
            game.onRandomCodes();
        }
    }

    /**
     * 投注
     */
    @OnClick(R.id.choose_done_button)
    public void onChooseDone() {
        if (game == null) {
            return;
        }

        if (shoppingCart.getCodesMap().size() != 0 && shoppingCart.getPrizeMode() != -1 && prizeMode != shoppingCart.getPrizeMode()) {
            ToastUtils.showShortToast(getActivity(), "本次投注与购物车订单的奖金模式不一致，需分开投注");
            return;
        }
        if (game.getSingleNum() > 0 && game.isExchange() == true) {
            switch (game.getMethod().getName()) {
                case "QSHHZX":
                case "SXHHZX":
                case "ZSHHZX":
                case "RSHHZX":
                    taskWay();
                    break;
                default:
                    String codes = game.getSubmitCodes();
                    Ticket ticket = new Ticket();
                    ticket.setChooseMethod(game.getMethod());
                    ticket.setChooseNotes(game.getSingleNum());
                    ticket.setCodes(codes);
                    shoppingCart.init(lottery);
                    shoppingCart.addTicket(ticket);
            }
        } else if (game.getSingleNum() > 0 && !game.isExchange()) {
            taskWay();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        shoppingCart.setPrizeMode(prizeMode);
        if (lottery.getLotteryId() != 15) {
            launchFragmentForResult(ShoppingFragment.class, bundle, 1);
        } else {
            launchFragmentForResult(ShoppingMmcFragment.class, bundle, 1);
        }
    }

    @OnClick(R.id.pick_notice)
    public void calculate() {
        if (game == null) {
            return;
        }
        if (pickNotice != null) {
            pickNotice.setText(String.format("共%d注", game.getSingleNum()));
        }
        if (chooseDoneButton != null) {
            if (game.getSingleNum() > 0 && chooseDoneButton != null) {
                chooseDoneButton.setEnabled(true);
            } else {
                chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
            }
        }
    }

    @OnClick(R.id.prize_mode_layout)
    public void onPrizeModeClicked() {

    }

    private void initWebView() {
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterface(), "androidJs");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (game != null) {
            game.reset();
        }
    }

    private class JsInterface {
        @JavascriptInterface
        public String getData() {
            if (game == null) {
                return "";
            }
            return game.getWebViewCode();
        }

        @JavascriptInterface
        public String getMethodName() {
            if (game == null) {
                return "";
            }
            Log.e(TAG, "玩法->" + game.getMethod().getName());
            return game.getMethod().getName();
        }

        @JavascriptInterface
        public void result(int singleNum, boolean isDup) {
            Log.d(TAG, "result() called with: " + "singleNum = [" + singleNum + "], isDup = [" + isDup + "]");
            if (game == null) {
                return;
            }
            game.setNumState(singleNum, isDup);
            webView.post(updatePickNoticeRunnable);
        }
    }

    private void update2WebView() {
        if (webView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("calculate();", null);
        } else {
            webView.loadUrl("javascript:calculate();");
        }
    }

    private Runnable updatePickNoticeRunnable = new Runnable() {
        @Override
        public void run() {
            if (game == null) {
                return;
            }
            if (pickNotice != null)
                pickNotice.setText(String.format("共%d注", game.getSingleNum()));
            if (game.getSingleNum() > 0 && prizeMode != -1 && chooseDoneButton != null) {
                chooseDoneButton.setEnabled(game.getSingleNum() > 0);
            } else {
                chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
            }
        }
    };

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void removeViews() {
        if (pickGameLayout != null && pickGameLayout.getChildCount() > 0) {
            pickGameLayout.removeAllViews();
        }
    }

    public void changeGameMethod(Method method) {
        if (pickNotice != null)
            pickNotice.setText("共0注");
        chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
        game.inflate(pickGameLayout);
        game.setOnSelectedListener(this);
        game.setExchange(true);
        cutover();
        if (game.isSupportInput()) {  //支持手机录入
            game.onManualInput(lottery, manualinputLayout);
        } else {
            game.destroyInput();
        }
        /*if (game.isHasRandom())
            pickRandom.setVisibility(View.VISIBLE);
        else
            pickRandom.setVisibility(View.GONE);*/
        loadWebViewIfNeed();
        initPrizes();
    }

    //该方法会计算注数
    @Override
    public void onChanged(Game game) {
        if("REZX".equals(game.getMethod().getName())////任二直选 REZX
                && !game.isExchange()){
            game.sscRenXuan2ManualMethodResult();
        }else if("RSZX".equals(game.getMethod().getName())//任三直选 RSZX
                && !game.isExchange()){
            game.sscRenXuan3ManualMethodResult();
        }else if("RSIZX".equals(game.getMethod().getName())//任四直选 RSIZX
                && !game.isExchange()){
            game.sscRenXuan4ManualMethodResult();
        }else{
            loadWebViewIfNeed();
            update2WebView();
        }
    }

    @Override
    public void onChanged(LhcGame lhcGame) {

    }

    public void cutover() {
        if (game != null) {
            game.onClearPick(game);
            if (game.isSupportInput() && !game.isExchange()) {
                pickGameLayout.setVisibility(View.GONE);
                manualinputLayout.setVisibility(View.VISIBLE);
                pickRandom.setVisibility(View.GONE);
                pickClear.setVisibility(View.VISIBLE);
                game.setOnManualEntryListener((int note) ->
                {
                    if (pickNotice != null)
                        pickNotice.setText(String.format("共%d注", note));
                    if (note > 0) {
                        chooseDoneButton.setEnabled(note > 0);
                    } else {
                        chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
                    }
                });
            } else {
                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(manualinputLayout.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                pickGameLayout.setVisibility(View.VISIBLE);
                manualinputLayout.setVisibility(View.GONE);
                /*if (lottery.getLotteryId() != 27)
                    pickRandom.setVisibility(View.VISIBLE);*/
            }
        }
    }

    private void taskWay() {
        shoppingCart.init(lottery);
        List<String> codes = game.getSubmitArray();
        int index = 0;
        ExecutorService ex = Executors.newFixedThreadPool(10);
        int dealSize = 2000;
        List<Future<List<Ticket>>> futures = new ArrayList<>(10);
        //分配
        for (int i = 0; i < 10; i++, index += dealSize) {
            int start = index;
            if (start >= codes.size())
                break;
            int end = start + dealSize;
            end = end > codes.size() ? codes.size() : end;
            futures.add(ex.submit(new Task(codes, start, end)));//运行Callable任务可以拿到一个Future对象，表示异步计算的结果
        }
        try {
            //处理 合并操作
            List<Ticket> result = new ArrayList<>();
            for (Future<List<Ticket>> future : futures) {
                result.addAll(future.get());
            }
            shoppingCart.addTicketList(result);
            game.onClearPick(game);
        } catch (Exception e) {
            MobclickAgent.reportError(getActivity(), TAG + "->" + e.getMessage());
        }
    }

    private class Task implements Callable<List<Ticket>> {

        private List<String> list;
        private int start;
        private int end;

        public Task(List<String> list, int start, int end) {
            this.list = list;
            this.start = start;
            this.end = end;
        }

        @Override
        public List<Ticket> call() throws Exception {
            List<Ticket> retList = new ArrayList<>();
            for (int i = start; i < end; i++) {
                //你的处理逻辑
                Ticket ticket = new Ticket();
                ticket.setChooseMethod(game.getMethod());
                switch (lottery.getLotteryType()) {
                    case 1://时时彩
                    case 4://福彩3D
                        if (game.getMethod().getName().equals("WXDW")) {
                            String[] wxdw = list.get(i).split(",");
                            int notes = 0;
                            for (String d : wxdw) {
                                if (!d.equals("-")) {
                                    notes += 1;
                                }
                            }
                            ticket.setChooseNotes(notes);
                        }  else if(game.getMethod().getName().equals("RSHHZX")){
                            ticket.setChooseNotes( game.getSingleNum());
                        } else if(game.getMethod().getName().equals("REZX")//任二直选 RSZX
                                && !game.isExchange()){
                             ticket.setChooseNotes( game.getSingleNum());
                        }else if("RSZX".equals(game.getMethod().getName())//任三直选 RSZX
                                && !game.isExchange()){
                            ticket.setChooseNotes( game.getSingleNum());
                        }else if("RSIZX".equals(game.getMethod().getName())//任四直选 RSIZX
                                && !game.isExchange()){
                            ticket.setChooseNotes( game.getSingleNum());
                        }else {
                            ticket.setChooseNotes(game.getColumn() > 1 ? 1 : game.getSingleNum());
                        }
                        ticket.setCodes(list.get(i));
                        break;
                    case 2://十一选五
                        ticket.setCodes(list.get(i));
                        switch (game.getMethod().getName()) {
                            case "SDQSZUX":
                            case "SDQEZUX":
                            case "SDRX2":
                            case "SDRX3":
                            case "SDRX4":
                            case "SDRX5":
                            case "SDRX6":
                            case "SDRX7":
                            case "SDRX8":
                                ticket.setChooseNotes(1);
                                break;
                            default:
                                ticket.setChooseNotes(1);
                        }
                        break;
                }
                retList.add(ticket);
            }
            //返回处理结果
            return retList;
        }
    }
}
