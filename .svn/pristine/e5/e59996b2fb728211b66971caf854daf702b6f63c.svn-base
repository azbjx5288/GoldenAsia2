package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.component.MmcOneArmBanditView;
import com.goldenasia.lottery.component.RollCountPopupWindow;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.data.MmcEntity;
import com.goldenasia.lottery.data.PayMmcCommand;
import com.goldenasia.lottery.data.PayMoneyCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.data.UserInfoCommand;
import com.goldenasia.lottery.db.MmcElevenSelectFiveWinHistory;
import com.goldenasia.lottery.db.MmcElevenSelectFiveWinHistoryDao;
import com.goldenasia.lottery.db.MmcWinHistory;
import com.goldenasia.lottery.db.MmcWinHistoryDao;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.ShoppingCart;
import com.goldenasia.lottery.pattern.ChaseMmcLottery;
import com.goldenasia.lottery.pattern.ChaseMmcWinView;
import com.goldenasia.lottery.pattern.ChooseTips;
import com.goldenasia.lottery.pattern.GetPrizePopupWindow;
import com.goldenasia.lottery.pattern.ShroudViewNoChase;
import com.goldenasia.lottery.user.UserCentre;
import com.goldenasia.lottery.util.DanTiaoUtils;
import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.util.ToastUtils;
import com.goldenasia.lottery.view.adapter.ShoppingMmcAdapter;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 秒秒彩购物车
 * Created on 2016/09/05.
 *
 * @author Sakura
 */
public class ShoppingMmcFragment extends BaseFragment
{
    private static final String TAG = ShoppingMmcFragment.class.getSimpleName();
    
    private static final int BUY_TRACE_ID = 1;
    private static final int ID_USER_INFO = 2;
    private static final int TRACK_TURNED_PAGE_LOGIN = 1;
    private static final int TRACK_TURNED_PAGE_RECHARGE = 2;
    private static final int TRACK_TURNED_PAGE_PICK = 3;
    private static final int TRACK_TURNED_PAGE_DANTIAO= 4;//单挑
    private static final int CUSTOMER_OPEN = 1;

    @BindView(R.id.shopping_list)
    ListView shoppingList;
    @BindView(R.id.lottery_shopping_buy)
    Button shoppingBuyButton;
    @BindView(R.id.chase_mmc_button)
    ImageButton chaseMmcButton;
    
    private View viewLayout;
    private MmcOneArmBanditView mmcOneArmBanditView;
    private ShoppingMmcAdapter adapter;
    private ShroudViewNoChase shroudViewNoChase;
    private ChooseTips chooseTips;
    private Lottery lottery;
    private ShoppingCart cart;
    private UserCentre userCentre;

    private boolean isInTraceState;
    private int rollCount;
    private double prize;
    private double tempPrize;
    private int multiple = 1;//仅用于单次开奖
    private int openCount;
    private int[] traceData;
    private boolean winStop;
    private double totalCost;

    private Handler rollHandler;
    private Handler toastHandler;
    private Runnable rollRunnable;
    private Runnable toastRunnable;

    /**
     * 辅助用，投注异常时，上报到服务器的错误信息
     */
    private String unusualInfo;

    private int mAwardsNumber =1;//开奖次数

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflateView(inflater, container, "投注", R.layout.shopping_mmc_fragment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        viewLayout = view;
        parameter();
        initInfo();
        initOAB();
        loadFunction();
        loadListInfo();
        planPrompt();
        countAwardsNumber();
    }

    /*计算开奖次数*/
    private void countAwardsNumber() {
        switch (lottery.getLotteryId()) {
            case 15://亚洲妙妙彩
                mAwardsNumber=SharedPreferencesUtils.getInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation.SSC_MMC_COUNT,1);
                break;
            case 44://11选5秒秒彩
                mAwardsNumber=SharedPreferencesUtils.getInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation.ESELECTF_MMC_COUNT,1);
                break;
            default:
                mAwardsNumber=1;
        }
    }

    private void parameter()
    {
        lottery = (Lottery) getArguments().getSerializable("lottery");
        userCentre = GoldenAsiaApp.getUserCentre();
        cart = ShoppingCart.getInstance();
    }

    private void initInfo()
    {
        cart.init(lottery);
        setTitle(lottery.getCname());
        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
    }

    /**
     * 初始化秒秒彩老虎机
     */
    private void initOAB()
    {
        mmcOneArmBanditView = new MmcOneArmBanditView(getActivity(), findViewById(R.id.one_arm_bandit));
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        }
    }

    private void loadFunction()
    {
        shroudViewNoChase = new ShroudViewNoChase(findViewById(R.id.shopping_bottom));
        chooseTips = new ChooseTips(findViewById(R.id.shopping_choosetip));
    }

    private void loadListInfo()
    {
        adapter = new ShoppingMmcAdapter();
        adapter.setOnDeleteClickListener(this::planPrompt);
        shoppingList.setAdapter(adapter);
        shroudViewNoChase.setModeItemListener((multiple, lucreMode) ->
        {
            cart.setPlanBuyRule(multiple, false);
            this.multiple = multiple;
            planPrompt();
        });
    }

    public void planPrompt()
    {
        chooseTips.setTipsText(String.format("%d", cart.getPlanNotes()), String.format("%.3f", cart.getPlanAmount()),
                String.format("%.3f", userCentre.getUserInfo().getBalance()));
    }

    @Override
    public void onPause() {
        super.onPause();
        saveAwardsNumber();
    }

    private void saveAwardsNumber() {
        switch (lottery.getLotteryId()) {
            case 15://亚洲妙妙彩
                SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation.SSC_MMC_COUNT,mAwardsNumber);
                break;
            case 44://11选5秒秒彩
                SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation.ESELECTF_MMC_COUNT,mAwardsNumber);
                break;
            default:
        }
    }

    @Override
    public void onDestroyView()
    {
        cart.init(lottery);
        super.onDestroyView();
        if (rollHandler != null)
        {
            rollHandler.removeCallbacks(rollRunnable);
        }
        if (toastHandler != null)
        {
            toastHandler.removeCallbacks(toastRunnable);
        }
    }

    @OnClick(R.id.chase_mmc_button)
    public void chase()
    {
        // ①判断：购物车中是否有投注
        if (cart.getPlanNotes() == 0)
        {
            tipDialog("温馨提示", "您需要选择一注", TRACK_TURNED_PAGE_PICK, true);
            return;
        }
        isInTraceState = true;
        ChaseMmcLottery chaseMmcLottery = new ChaseMmcLottery(getContext());
        chaseMmcLottery.setOnChaseStartListener((int openCount, int[] traceData, boolean winStop, double totalCost) ->
        {
            this.openCount = openCount;
            this.traceData = traceData;
            this.winStop = winStop;
            this.totalCost = totalCost;

            // ②判断：用户是否登录——被动登录
            if (userCentre.isLogin())
            {
                // ③判断：用户的余额是否满足投注需求
                if (totalCost <= userCentre.getUserInfo().getBalance())
                {
                    // ④界面跳转：跳转到自定义倍数的设置界面
                    final PayMmcCommand payMmcCommand = new PayMmcCommand();
                    payMmcCommand.setLotteryId(lottery.getLotteryId());
                    payMmcCommand.setPrizeMode(cart.getPrizeMode() > 0 ? 1 : 0/*!(userCentre.getBonusMode(lottery
                    .getLotteryId()) == 0)*/);
                    payMmcCommand.setModes(cart.getLucreMode().getModes());
                    payMmcCommand.setCodes(cart.getCodeStr());
                    payMmcCommand.setTraceNum(cart.getTraceNumber());
                    payMmcCommand.setToken(ConstantInformation.randomToken());
                    payMmcCommand.setTraceData(traceData);
                    payMmcCommand.setStopOnWin(winStop);
                    payMmcCommand.setOpenCounts(openCount);
                    payMmcCommand.setTraceNum(traceData.length);

                    TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MmcEntity>>>()
                    {};
                    RestRequestManager.executeCommand(getActivity(), payMmcCommand, typeToken, restCallback,
                            BUY_TRACE_ID, this);

                    ToastUtils.showShortToast(getActivity(), "开奖中……");
                } else
                {
                    // 提示用户：充值去；界面跳转：用户充值界面
                    tipDialog("温馨提示", "余额不足，请充值", TRACK_TURNED_PAGE_RECHARGE, true);
                }
            } else
            {
                // 提示用户：登录去；界面跳转：用户登录界面
                tipDialog("温馨提示", "请重新登录", TRACK_TURNED_PAGE_LOGIN, true);
            }
        });
    }

    private void rollAgain()
    {
        // ①判断：购物车中是否有投注
        if (cart.getPlanNotes() == 0)
        {
            tipDialog("温馨提示", "您需要选择一注", TRACK_TURNED_PAGE_PICK, true);
            return;
        }
        isInTraceState = true;
        // ②判断：用户是否登录——被动登录
        if (userCentre.isLogin())
        {
            // ③判断：用户的余额是否满足投注需求
            if (totalCost <= userCentre.getUserInfo().getBalance())
            {
                // ④界面跳转：跳转到自定义倍数的设置界面
                final PayMmcCommand payMmcCommand = new PayMmcCommand();
                payMmcCommand.setLotteryId(lottery.getLotteryId());
                payMmcCommand.setPrizeMode(cart.getPrizeMode() > 0 ? 1 : 0/*!(userCentre.getBonusMode(lottery
                .getLotteryId()) == 0)*/);
                payMmcCommand.setModes(cart.getLucreMode().getModes());
                payMmcCommand.setCodes(cart.getCodeStr());
                payMmcCommand.setTraceNum(cart.getTraceNumber());
                payMmcCommand.setToken(ConstantInformation.randomToken());
                payMmcCommand.setTraceData(traceData);
                payMmcCommand.setStopOnWin(winStop);
                payMmcCommand.setOpenCounts(openCount);
                payMmcCommand.setTraceNum(CUSTOMER_OPEN);

                TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MmcEntity>>>()
                {};
                RestRequestManager.executeCommand(getActivity(), payMmcCommand, typeToken, restCallback,
                        BUY_TRACE_ID, this);
            } else
            {
                // 提示用户：充值去；界面跳转：用户充值界面
                tipDialog("温馨提示", "余额不足，请充值", TRACK_TURNED_PAGE_RECHARGE, true);
            }
        } else
        {
            // 提示用户：登录去；界面跳转：用户登录界面
            tipDialog("温馨提示", "请重新登录", TRACK_TURNED_PAGE_LOGIN, true);
        }
    }

    @OnClick(R.id.lottery_shopping_buy)
    public void buySingle()
    {
        // ①判断：购物车中是否有投注
        if (!cart.isEmpty())
        {
            // ②判断：用户是否登录——被动登录
            if (userCentre.isLogin())
            {
                // ③判断：用户的余额是否满足投注需求
                if (cart.getPlanAmount() <= userCentre.getUserInfo().getBalance())
                {
                    // ④界面跳转：跳转到追期和倍投的设置界面

                    DanTiaoUtils danTiaoUtils=new DanTiaoUtils();
                    String danTiaoString=danTiaoUtils.isShowDialog(lottery.getLotteryId(),ShoppingCart.getInstance().getCodesMap());

                    if(TextUtils.isEmpty(danTiaoString)){
                        verifyData();
                        ToastUtils.showShortToast(getActivity(), "开奖中……");
                    }else{
                        tipDialog("提示",danTiaoString,  TRACK_TURNED_PAGE_DANTIAO,false);
                        return;
                    }

                } else
                {
                    // 提示用户：充值去；界面跳转：用户充值界面
                    tipDialog("温馨提示", "余额不足，请充值", TRACK_TURNED_PAGE_RECHARGE, true);
                }
            } else
            {
                // 提示用户：登录去；界面跳转：用户登录界面
                tipDialog("温馨提示", "请重新登录", TRACK_TURNED_PAGE_LOGIN, true);
            }
        } else
        {
            // 提示用户需要选择一注
            tipDialog("温馨提示", "您需要选择一注", TRACK_TURNED_PAGE_PICK, true);
        }
    }

    /**
     * 数据验证
     */
    private void verifyData()
    {
        isInTraceState = false;
        shoppingBuyButton.setEnabled(false);
        chaseMmcButton.setEnabled(false);
        enableHomeButton(false);
        //BigDecimal becimal = new BigDecimal(cart.getPlanAmount());
        //double planAmount = becimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        final PayMoneyCommand command = new PayMoneyCommand();
        command.setLotteryId(lottery.getLotteryId());
        command.setIssue("0");
        command.setPrizeMode(cart.getPrizeMode() > 0 ? 1 : 0);
        command.setModes(cart.getLucreMode().getModes());
        command.setCodes(cart.getCodeStr());
        command.setMultiple(multiple);
        command.setTraceNum(cart.getTraceNumber());
        command.setStopOnWin(cart.isStopOnWin());
        command.setToken(ConstantInformation.randomToken());

        unusualInfo = ConstantInformation.gatherInfo(getActivity(), command);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MmcEntity>>>()
        {};
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, BUY_TRACE_ID, this);
    }

    private void tipDialog(String title, String msg, final int track, final boolean withFace)
    {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle(title);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.alert_dialog_mmc_layout, null);
        builder.setDisplayLayout(view);
        switch (track)
        {
            case TRACK_TURNED_PAGE_RECHARGE:
                builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
                builder.setNegativeButton("知道了", (dialog, which) ->
                {
                    dialog.dismiss();
                    if (track == TRACK_TURNED_PAGE_PICK)
                    {
                        getActivity().finish();
                    }
                });
                builder.setPositiveButton("去充值", (dialog, which) ->
                {
                    dialog.dismiss();
                    launchFragment(RechargeApply.class);
                });
                break;
            case TRACK_TURNED_PAGE_DANTIAO:
                builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
                builder.setNegativeButton("取消", (dialog, which) ->
                {
                    dialog.dismiss();
                    if (track == TRACK_TURNED_PAGE_PICK)
                    {
                        getActivity().finish();
                    }
                });
                builder.setPositiveButton("确认", (dialog, which) ->
                {
                    dialog.dismiss();
                    verifyData();
                    ToastUtils.showShortToast(getActivity(), "开奖中……");
                });
                break;
            default:
                builder.setLayoutSet(DialogLayout.SINGLE);
                builder.setPositiveButton("知道了", (dialog, which) ->
                {
                    dialog.dismiss();
                });
        }
        ImageView faceView = (ImageView) view.findViewById(R.id.face);
        if (withFace == true)
        {
            faceView.setVisibility(View.VISIBLE);
        } else
        {
            faceView.setVisibility(View.GONE);
        }
        builder.create().show();
    }

    /**
     * 循环做开奖动画处理
     */
    private void roll(ArrayList<MmcEntity> openCodeList)
    {
        enableHomeButton(false);
        chaseMmcButton.setEnabled(false);
        shoppingBuyButton.setEnabled(false);

        int length = openCodeList.size();
        rollCount = 0;
        prize = 0.00;
        tempPrize = 0.00;

        toastRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                GetPrizePopupWindow getPrizePopupWindow = new GetPrizePopupWindow(getActivity(), tempPrize);
                getPrizePopupWindow.showAtLocation(viewLayout, Gravity.CENTER, 0, 0);
            }
        };
        rollHandler = new Handler();
        rollRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                //滚动完毕
                if (rollCount < length)
                {
                    mmcOneArmBanditView.start(openCodeList.get(rollCount).getOpenCode());
                    tempPrize = openCodeList.get(rollCount).getPrize();
                    prize += tempPrize;
                    //连续开奖才会提示第几次开奖
                    if (isInTraceState == true)
                    {
                        RollCountPopupWindow rollCountPopupWindow = new RollCountPopupWindow(getActivity(), rollCount
                                + 1);
                        rollCountPopupWindow.setAnimationStyle(R.style.popwindow_anim_style);
                        rollCountPopupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);
                        //中奖才会弹出恭喜中奖
                        if (tempPrize > 0)
                        {
                            toastHandler = new Handler();
                            toastHandler.postDelayed(toastRunnable, MmcOneArmBanditView.TOAST_DELAY);
                        }
                    }
                    rollCount++;
                    rollHandler.postDelayed(this, MmcOneArmBanditView.INTERVAL);
                } else
                {
                    //亚洲秒秒彩的中奖情况插入数据库中start
                    insertMmcWinHistory(openCodeList, cart.getPlanAmount());
                    //亚洲秒秒彩的中奖情况插入数据库中end

                    //中奖弹窗
                    if (prize > 0)
                    {
                        ChaseMmcWinView.BuilderView chaseMmcWin = new ChaseMmcWinView.BuilderView(getContext());
                        chaseMmcWin.setWinners("恭喜中奖" + NumbericUtils.formatPrize(prize) + "元");
                        chaseMmcWin.setOpenCodeList(openCodeList);
                        chaseMmcWin.setSingleOrMore(false);
                        chaseMmcWin.setOnPlayAgainListener(() ->
                        {
                            if (isInTraceState == true)
                            {
                                rollAgain();
                            } else
                                playAgain();
                        });
                        chaseMmcWin.setOnCancelListener(() ->
                        {
                            cancel();
                        });
                        chaseMmcWin.create();
                    }
                    //未中奖弹窗
                    else
                    {
                        ChaseMmcWinView.BuilderView chaseMmcWin = new ChaseMmcWinView.BuilderView(getContext());
                        chaseMmcWin.setWinners("再接再厉");
                        chaseMmcWin.setOpenCodeList(openCodeList);
                        chaseMmcWin.setOnPlayAgainListener(() ->
                        {
                            if (isInTraceState == true)
                            {
                                rollAgain();
                            } else
                                playAgain();
                        });
                        chaseMmcWin.setOnCancelListener(() ->
                        {
                            cancel();
                        });
                        if (isInTraceState == false)
                        {
                            chaseMmcWin.setSingleOrMore(true);
                            chaseMmcWin.setOnPlayDoubleListner(() ->
                            {
                                playDouble();
                            });
                        }
                        chaseMmcWin.create();
                    }
                    reset();
                    rollHandler.removeCallbacks(this);
                }
            }
        };
        rollHandler.postDelayed(rollRunnable, MmcOneArmBanditView.START_DELAY);
    }

    private void playAgain()
    {
        buySingle();
    }

    private void cancel()
    {
    }

    private void playDouble()
    {
        multiple *= 2;
        shroudViewNoChase.getDoubleText().setQuantity(multiple);
        cart.setPlanBuyRule(multiple, false);
        playAgain();
    }
    
    private void reset()
    {
        setTitle(lottery.getCname());
        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        chaseMmcButton.setEnabled(true);
        shoppingBuyButton.setEnabled(true);
        enableHomeButton(true);
    }
    
    private void saveLastMethod()
    {
        Method method = (Method) getArguments().getSerializable("lastMethod");
        if (method != null)
        {
            String fileName = GoldenAsiaApp.getUserCentre().getUserID() + " lastPlay";
            try
            {
                SharedPreferencesUtils.putObject(getActivity(), fileName, lottery.getName(), method);
            } catch (Exception e)
            {
            }
        }
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case BUY_TRACE_ID:
                    ArrayList<MmcEntity> openCodeList = (ArrayList<MmcEntity>) response.getData();
                    if (openCodeList != null)
                    {
                        roll(openCodeList);
                        saveLastMethod();
                    }
                    break;
                case ID_USER_INFO:
                    UserInfo userInfo = ((UserInfo) response.getData());
                    userCentre.setUserInfo(userInfo);
                    planPrompt();
                    break;
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            enableHomeButton(true);
            if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            } else if (errCode == 2220)
            {
                showToast(errDesc, Toast.LENGTH_LONG);
                MobclickAgent.reportError(getActivity(), unusualInfo);
                Log.e(TAG, unusualInfo);
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {
            chaseMmcButton.setEnabled(true);
            shoppingBuyButton.setEnabled(true);
        }
    };
    
    //中奖情况插入数据库中
    private void insertMmcWinHistory(ArrayList<MmcEntity> openCodeList, double betMoney)
    {
        switch (lottery.getLotteryId()) {
            case 15://亚洲妙妙彩
                insertSscMmcWinHistory(openCodeList, betMoney);
                break;
            case 44://11选5秒秒彩
                insertElevenSelectFiveMmcWinHistory(openCodeList, betMoney);
                break;
        }
    }

    //亚洲秒秒彩的
    private void insertSscMmcWinHistory(ArrayList<MmcEntity> openCodeList, double betMoney){
        MmcWinHistoryDao mmcWinHistoryDao = new MmcWinHistoryDao(getActivity());
        MmcEntity mmcEntity = null;
        MmcWinHistory mmcWinHistory = null;

        for (int i = 0; i < openCodeList.size(); i++)
        {
            mmcEntity = openCodeList.get(i);
            mmcWinHistory = new MmcWinHistory();

            mmcWinHistory.setCount(String.valueOf(mAwardsNumber));
            mmcWinHistory.setNumber(mmcEntity.getOpenCode());

            //投注金额 保留小数点三位后
            BigDecimal bd = new BigDecimal(betMoney);
            bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
            mmcWinHistory.setBetMoney(String.valueOf(bd));

            //奖金金额 保留小数点二位后
            bd = new BigDecimal(mmcEntity.getPrize());
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            mmcWinHistory.setWinMoney(String.valueOf(bd));
            mmcWinHistoryDao.savaMmcWinHistory(mmcWinHistory);
            mAwardsNumber++;
        }
    }

    //11选5秒秒彩
    private void insertElevenSelectFiveMmcWinHistory(ArrayList<MmcEntity>  openCodeList, double betMoney){
        MmcElevenSelectFiveWinHistoryDao mmcWinHistoryDao = new MmcElevenSelectFiveWinHistoryDao(getActivity());
        MmcEntity mmcEntity = null;
        MmcElevenSelectFiveWinHistory mmcWinHistory = null;

        for (int i = 0; i < openCodeList.size(); i++)
        {
            mmcEntity = openCodeList.get(i);
            mmcWinHistory = new MmcElevenSelectFiveWinHistory();

            mmcWinHistory.setCount(String.valueOf(mAwardsNumber));
            mmcWinHistory.setNumber(mmcEntity.getOpenCode());

            //投注金额 保留小数点三位后
            BigDecimal bd = new BigDecimal(betMoney);
            bd = bd.setScale(3, BigDecimal.ROUND_HALF_UP);
            mmcWinHistory.setBetMoney(String.valueOf(bd));

            //奖金金额 保留小数点二位后
            bd = new BigDecimal(mmcEntity.getPrize());
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            mmcWinHistory.setWinMoney(String.valueOf(bd));
            mmcWinHistoryDao.savaMmcWinHistory(mmcWinHistory);
            mAwardsNumber++;
        }
    }
}
