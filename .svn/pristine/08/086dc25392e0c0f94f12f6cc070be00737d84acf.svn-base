package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.GgcBuyBean;
import com.goldenasia.lottery.data.GgcCardListCommand;
import com.goldenasia.lottery.data.GgcCardListEntity;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.PayGgcCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.data.UserInfoCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.user.UserCentre;
import com.goldenasia.lottery.util.ToastUtils;
import com.goldenasia.lottery.view.SlideViewHelper;
import com.goldenasia.lottery.view.adapter.GgcCartAdapter;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 刮刮彩彩购物车
 * Created on 2016/10/04.
 *
 * @author Sakura
 */
public class ShoppingGgcFragment extends BaseFragment {
    private static final String TAG = ShoppingGgcFragment.class.getSimpleName();

    private static final int USER_INFO_ID = 1;
    private static final int CARD_LIST_ID = 2;
    private static final int BUY_ID = 3;
    private static final int TRACK_TURNED_PAGE_LOGIN = 1;
    private static final int TRACK_TURNED_PAGE_RECHARGE = 2;
    private static final int TRACK_TURNED_PAGE_PICK = 3;

    @BindView(R.id.card_list_for_sale)
    LinearLayout cardListForSale;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.five)
    TextView five;
    @BindView(R.id.ten)
    TextView ten;
    @BindView(R.id.twenty)
    TextView twenty;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.clear)
    TextView clear;
    @BindView(R.id.buy)
    Button buy;
    @BindView(R.id.cart)
    GridView cart;
    @BindView(R.id.price)
    TextView priceTextView;
    @BindView(R.id.amount)
    TextView amountTextView;
    @BindView(R.id.total_price)
    TextView totalTextView;
    @BindView(R.id.lottery_shopping_balance)
    TextView lotteryShoppingBalance;

    private Lottery lottery;
    private String packageId;
    private String scrapeType;
    private UserCentre userCentre;
    private GgcCardListCommand ggcCardListCommand;
    private GgcCardListEntity cardListEntity;
    private GgcCartAdapter cartAdapter;
    private GgcBuyBean buyBean;

    private int price;
    private ArrayList<String> cards;

    /**
     * 辅助用，投注异常时，上报到服务器的错误信息
     */
    private String unusualInfo;

    private SlideViewHelper slideViewHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "", R.layout.shopping_ggc_fragment);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        slideViewHelper = new SlideViewHelper(view);
        initData();
        initInfo();
        notifyInfo();
    }

    private void initData() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
        packageId = (String) getArguments().getSerializable("packageId");
        scrapeType = (String) getArguments().getSerializable("scrapeType");

        slideViewHelper.setScrapeType(scrapeType);
        slideViewHelper.setDrawables();
        slideViewHelper.setOnClickListener((index, checked) -> {
            cartAdapter.setData(slideViewHelper.getCheckCard(), scrapeType);
            notifyInfo();
        });
        ggcCardListCommand = new GgcCardListCommand();
        ggcCardListCommand.setPackageId(packageId);
        userCentre = GoldenAsiaApp.getUserCentre();
        cartAdapter = new GgcCartAdapter();
        cart.setAdapter(cartAdapter);
    }

    private void initInfo() {
        setTitle(lottery.getCname());
        lotteryShoppingBalance.setText(String.format("余额：%.3f", userCentre.getUserInfo().getBalance()));
        executeCommand(new UserInfoCommand(), restCallback, USER_INFO_ID);
        executeCommand(ggcCardListCommand, restCallback, CARD_LIST_ID);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            executeCommand(new UserInfoCommand(), restCallback, USER_INFO_ID);
            executeCommand(ggcCardListCommand, restCallback, CARD_LIST_ID);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void tipDialog(String title, String msg, final int track) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle(title);
        switch (track) {
            case TRACK_TURNED_PAGE_RECHARGE:
                builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
                builder.setNegativeButton("知道了", (dialog, which) -> {
                    dialog.dismiss();
                    if (track == TRACK_TURNED_PAGE_PICK) {
                        getActivity().finish();
                    }
                });
                builder.setPositiveButton("去充值", (dialog, which) -> {
                    dialog.dismiss();
                    launchFragment(RechargeApply.class);
                });
                break;
            default:
                builder.setLayoutSet(DialogLayout.SINGLE);
                builder.setPositiveButton("知道了", (dialog, which) -> {
                    dialog.dismiss();
                });
        }
        builder.create().show();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case USER_INFO_ID:
                    UserInfo userInfo = (UserInfo) response.getData();
                    userCentre.setUserInfo(userInfo);
                    if (userInfo != null) {
                        lotteryShoppingBalance.setText(String.format("余额：%.3f", userInfo.getBalance()));
                    }
                    break;
                case CARD_LIST_ID:
                    JsonString listString = (JsonString) response.getData();
                    cardListEntity = GsonHelper.fromJson(listString.getJson(), GgcCardListEntity.class);
                    price = cardListEntity.getScrape_type().getPrice();
                    initSaleCard();
                    notifyInfo();
                    getActivity().setResult(ConstantInformation.REFRESH_RESULT);
                    break;
                case BUY_ID:
                    buyBean = (GgcBuyBean) response.getData();
                    if (buyBean != null) {
                        slideViewHelper.clearChecked();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("buyBean", buyBean);
                        bundle.putSerializable("lottery", lottery);
                        bundle.putSerializable("scrapeType", scrapeType);
                        launchFragmentForResult(GgcBuySucceedFragment.class, bundle, 1);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            buy.setEnabled(true);
            buy.setBackgroundResource(R.drawable.button_type);
            if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            } else if (errCode == 2220) {
                showToast(errDesc, Toast.LENGTH_LONG);
                MobclickAgent.reportError(getActivity(), unusualInfo);
                Log.e(TAG, unusualInfo);
                return true;
            }
            getActivity().finish();
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

    @OnClick({R.id.two, R.id.five, R.id.ten, R.id.twenty, R.id.all, R.id.clear, R.id.buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.two:
                buyMultipleCard(2);
                break;
            case R.id.five:
                buyMultipleCard(5);
                break;
            case R.id.ten:
                buyMultipleCard(10);
                break;
            case R.id.twenty:
                buyMultipleCard(20);
                break;
            case R.id.all:
                slideViewHelper.pickAll();
                cartAdapter.setData(slideViewHelper.getCheckCard(), scrapeType);
                notifyInfo();
                break;
            case R.id.clear:
                slideViewHelper.clearChecked();
                cartAdapter.setData(slideViewHelper.getCheckCard(), scrapeType);
                notifyInfo();
                break;
            case R.id.buy:
                // ①判断：购物车中是否有投注
                if (cartAdapter.getCount() != 0) {
                    // ②判断：用户是否登录——被动登录
                    if (userCentre.isLogin()) {
                        submitBuy();
                    } else {
                        // 提示用户：登录去；界面跳转：用户登录界面
                        tipDialog("温馨提示", "请重新登录", TRACK_TURNED_PAGE_LOGIN);
                    }
                } else {
                    // 提示用户需要选择一注
                    tipDialog("温馨提示", "您需要选择一张卡片", TRACK_TURNED_PAGE_PICK);
                }
                break;
        }
    }


    private void submitBuy() {
        // ③判断：用户的余额是否满足投注需求
        if (price * cartAdapter.getCount() <= userCentre.getUserInfo().getBalance()) {
            // ④界面跳转：跳转到购买成功界面
            final PayGgcCommand payGgcCommand = new PayGgcCommand();
            payGgcCommand.setPackage_id(packageId);
            payGgcCommand.setScrape_type(scrapeType);
            StringBuilder stringBuilder = new StringBuilder();
            int length = cartAdapter.getCount();
            ArrayList<String> values = slideViewHelper.getCheckCard();
            int num = 0;
            for (Map.Entry<String, String> item : cardListEntity.getCards().entrySet()) {
                if (values.contains(item.getValue())) {
                    stringBuilder.append(item.getKey());
                    num++;
                    if (num != length) {
                        stringBuilder.append(",");
                    }
                }
            }
            payGgcCommand.setSc_ids(stringBuilder.toString());
            unusualInfo = ConstantInformation.gatherGgcInfo(getActivity(), payGgcCommand);
            TypeToken typeToken = new TypeToken<RestResponse<GgcBuyBean>>() {
            };
            RestRequestManager.executeCommand(getActivity(), payGgcCommand, typeToken, restCallback, BUY_ID,
                    this);
        } else {
            // 提示用户：充值去；界面跳转：用户充值界面
            tipDialog("温馨提示", "请充值", TRACK_TURNED_PAGE_RECHARGE);
        }
    }

    @OnItemClick(R.id.cart)
    public void cancelOrder(int position) {
        slideViewHelper.cancel(cartAdapter.getItem(position));
        cartAdapter.setData(slideViewHelper.getCheckCard(), scrapeType);
        notifyInfo();
    }

    private void initSaleCard() {
        cards = new ArrayList<>();
        for (String cardId : cardListEntity.getCards().values()) {
            cards.add(cardId);
        }
        slideViewHelper.setCardIds(cards);
    }

    private void buyMultipleCard(int num) {
        boolean isPick = slideViewHelper.pick(num);
        if (!isPick) {
            ToastUtils.showShortToast(getActivity(), "本卡包数量不足");
        }
        cartAdapter.setData(slideViewHelper.getCheckCard(), scrapeType);
        notifyInfo();
    }

    private void notifyInfo() {
        priceTextView.setText("面值：" + price + "元");
        amountTextView.setText("数量：" + cartAdapter.getCount() + "张");
        totalTextView.setText("总金额：" + price * cartAdapter.getCount() + "元");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cartAdapter.setData(slideViewHelper.getCheckCard(), scrapeType);
        switch (resultCode) {
            case ConstantInformation.REFRESH_RESULT:
                initData();
                initInfo();
                notifyInfo();
                break;
            case ConstantInformation.EXIT_RESULT:
                getActivity().setResult(ConstantInformation.LAUNCH_RESULT);
                getActivity().finish();
        }
    }
}
