package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.GgcAutoScrapeCommand;
import com.goldenasia.lottery.data.GgcBuyBean;
import com.goldenasia.lottery.data.GgcCardEntity;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.view.adapter.GgcCardListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * 刮刮彩购买成功页面
 * Created by Sakura on 2016/10/6.
 */

public class GgcBuySucceedFragment extends BaseFragment
{
    private static final String TAG = "GgcBuySucceedFragment";

    private static final int AUTO_ID = 1;
    private static final int BACK_RESULT = 1;

    @BindView(R.id.toast)
    TextView toast;
    @BindView(R.id.list)
    GridView list;
    @BindView(R.id.auto)
    Button auto;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.buy)
    Button buy;

    private Lottery lottery;
    private GgcBuyBean buyBean;
    private String scrapeType;
    private LinkedHashMap<Integer, String> cards;
    private int length;
    private ArrayList<GgcCardEntity> dataList;
    private GgcCardListAdapter adapter;
    private GgcAutoScrapeCommand autoScrapeCommand;
    private ArrayList<GgcCardEntity> cardEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflateView(inflater, container, "", R.layout.ggc_buy_succeed);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initInfo();
    }

    private void initData()
    {
        lottery = (Lottery) getArguments().getSerializable("lottery");
        buyBean = (GgcBuyBean) getArguments().getSerializable("buyBean");
        scrapeType = (String) getArguments().getSerializable("scrapeType");
        cards = buyBean.getCards();
        length = buyBean.getLength();
        dataList = new ArrayList<>();
        for (int cardId : cards.keySet())
        {
            String cardNumber = cards.get(cardId);
            GgcCardEntity cardEntity = new GgcCardEntity();
            cardEntity.setSc_id(String.valueOf(cardId));
            cardEntity.setSerial_num(cardNumber);
            cardEntity.setScrape_type(scrapeType);
            dataList.add(cardEntity);
        }
        adapter = new GgcCardListAdapter();
        list.setAdapter(adapter);
        adapter.setData(dataList);
        autoScrapeCommand = new GgcAutoScrapeCommand();
        getActivity().setResult(ConstantInformation.REFRESH_RESULT);
    }

    private void initInfo()
    {
        setTitle(lottery.getCname());
        toast.setText("恭喜您成功购买" + length + "张\n祝您好运！");
        amount.setText("共" + length + "张");
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }

    @OnClick({R.id.auto, R.id.buy})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.auto:
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < length; i++)
                {
                    stringBuilder.append(dataList.get(i).getSc_id());
                    if (i < length - 1)
                        stringBuilder.append(",");
                }
                autoScrapeCommand.setScIds(stringBuilder.toString());
                TypeToken typeToken = new TypeToken<RestResponse<ArrayList<GgcCardEntity>>>() {};
                RestRequestManager.executeCommand(getActivity(), autoScrapeCommand, typeToken, restCallback, AUTO_ID,
                        this);
                break;
            case R.id.buy:
                getActivity().finish();
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
                case AUTO_ID:
                    cardEntities = (ArrayList<GgcCardEntity>) response.getData();
                    for (int i = 0; i < length; i++)
                    {
                        dataList.get(i).setPrize(cardEntities.get(i).getPrize());
                        dataList.get(i).setStatus(cardEntities.get(i).getStatus());
                    }
                    adapter.setData(dataList);
                    auto.setEnabled(false);
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            } else if (errCode == 2220)
            {
                showToast(errDesc, Toast.LENGTH_LONG);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {

        }
    };

    @OnItemClick(R.id.list)
    public void openDetail(int position)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardId", dataList.get(position).getSc_id());
        launchFragmentForResult(GgcCardDetailFragment.class, bundle, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity().setResult(ConstantInformation.EXIT_RESULT);
        getActivity().finish();
    }
}
