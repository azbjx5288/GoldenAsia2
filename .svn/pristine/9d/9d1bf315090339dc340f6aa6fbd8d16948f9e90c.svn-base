package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.EraseView;
import com.goldenasia.lottery.data.GgcAutoScrapeCommand;
import com.goldenasia.lottery.data.GgcCardDetailCommand;
import com.goldenasia.lottery.data.GgcCardEntity;
import com.goldenasia.lottery.data.GgcDetail;
import com.goldenasia.lottery.data.GgcMoneyNumberEntity;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.WindowUtils;
import com.goldenasia.lottery.view.adapter.GgcCodeAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Sakura on 2016/10/7.
 */

public class GgcCardDetailFragment extends BaseFragment
{
    private static final String TAG = "GgcCardDetailFragment";
    private static final int INIT_ID = 1;
    private static final int AUTO_ID = 2;
    private static final int AUTO_OPEN_ORDER = 0x123;

    @Bind(android.R.id.home)
    ImageButton home;
    @Bind(R.id.auto)
    TextView auto;
    @Bind(R.id.left_pic)
    EraseView leftPic;
    @Bind(R.id.right_pic)
    EraseView rightPic;
    @Bind(R.id.prize_code)
    EraseView prizeCode;
    @Bind(R.id.my_code)
    EraseView myCode;
    @Bind(R.id.left_base)
    ImageView leftBase;
    @Bind(R.id.right_base)
    ImageView rightBase;
    @Bind(R.id.prize_base)
    LinearLayout prizeBase;
    @Bind(R.id.my_base)
    GridView myBase;
    @Bind(R.id.base)
    RelativeLayout base;
    @Bind(R.id.toast)
    TextView toast;

    private String cardId;
    private String status;
    private String prize;
    private GgcCardDetailCommand detailCommand;
    private GgcDetail detail;
    private ArrayList<Integer> prizeCodeList;
    private ArrayList<GgcMoneyNumberEntity> myCodeList;
    private GgcCodeAdapter codeAdapter;
    private int height;

    /*private int[] sxImages = new int[]{R.drawable.sxk_sx9, R.drawable.sxk_sx2, R.drawable.sxk_sx3, R.drawable
            .sxk_sx4, R.drawable.sxk_sx5, R.drawable.sxk_sx6, R.drawable.sxk_sx7, R.drawable.sxk_sx8, R.drawable
            .sxk_sx1, R.drawable.sxk_sx10, R.drawable.sxk_sx11, R.drawable.sxk_sx12};
    private int[] jqImages = new int[]{R.drawable.jq0, R.drawable.jq1, R.drawable.jq2, R.drawable.jq3, R.drawable
            .jq4, R.drawable.jq5, R.drawable.jq6, R.drawable.jq7, R.drawable.jq8, R.drawable.jq9};*/
    private HashMap<String, Integer> sxMap = new HashMap<String, Integer>()
    {{
        put("shu", R.drawable.sxk_sx1);
        put("niu", R.drawable.sxk_sx2);
        put("hu", R.drawable.sxk_sx3);
        put("tu", R.drawable.sxk_sx4);
        put("long", R.drawable.sxk_sx5);
        put("she", R.drawable.sxk_sx6);
        put("ma", R.drawable.sxk_sx7);
        put("yang", R.drawable.sxk_sx8);
        put("hou", R.drawable.sxk_sx9);
        put("ji", R.drawable.sxk_sx10);
        put("gou", R.drawable.sxk_sx11);
        put("zhu", R.drawable.sxk_sx12);
    }};
    private HashMap<String, Integer> jqMap = new HashMap<String, Integer>()
    {{
        put("0", R.drawable.jq0);
        put("1", R.drawable.jq1);
        put("2", R.drawable.jq2);
        put("3", R.drawable.jq3);
        put("4", R.drawable.jq4);
        put("5", R.drawable.jq5);
        put("6", R.drawable.jq6);
        put("7", R.drawable.jq7);
        put("8", R.drawable.jq8);
        put("9", R.drawable.jq9);
    }};


    private MyHandler myHandler;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        setFitSystem(false);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_ggc_card_detail, container, false);
        ButterKnife.bind(this, view);
        WindowUtils.hideSystemUI(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initPosition();
    }

    @Override
    public void onDestroyView()
    {
        leftPic.destroy();
        rightPic.destroy();
        prizeCode.destroy();
        myCode.destroy();
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({android.R.id.home, R.id.auto})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case android.R.id.home:
                getActivity().setResult(ConstantInformation.EXIT_RESULT);
                getActivity().finish();
                break;
            case R.id.auto:
                GgcAutoScrapeCommand command = new GgcAutoScrapeCommand();
                command.setScIds(cardId);
                TypeToken typeToken = new TypeToken<RestResponse<ArrayList<GgcCardEntity>>>()
                {};
                RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, AUTO_ID, this);
                break;
        }
    }

    private void initData()
    {
        height = WindowUtils.getHeight(getActivity());
        cardId = (String) getArguments().getSerializable("cardId");
        detailCommand = new GgcCardDetailCommand();
        detailCommand.setSc_id(cardId);
        myHandler = new MyHandler();
        prizeCodeList = new ArrayList<>();
        myCodeList = new ArrayList<>();
        codeAdapter = new GgcCodeAdapter();
        myBase.setAdapter(codeAdapter);
        TypeToken typeToken = new TypeToken<RestResponse<GgcDetail>>()
        {};
        RestRequestManager.executeCommand(getActivity(), detailCommand, typeToken, restCallback, INIT_ID, this);
    }

    private void initPosition()
    {
        if (height == 480)
        {

        } else if (height == 720)
        {
        } else if (height == 1080)
        {
        } else if (height == 1440)
        {
            setPosition(40, 120, 180, 40);
        }
    }

    private void setPosition(float top, float center, float bottom, float middle)
    {
        setMarginTop(leftBase, top);
        setMarginTop(leftPic, top);
        setMarginTop(rightBase, top);
        setMarginTop(rightPic, top);

        setMarginTop(prizeBase, center);
        setMarginTop(prizeCode, center);

        setMarginTop(myBase, bottom);
        setMarginTop(myCode, bottom);

        setMarginTop(toast, middle);
    }

    private void setMarginTop(View view, float top)
    {
        view.setY(top);
    }

    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case INIT_ID:
                    detail = (GgcDetail) response.getData();
                    if (detail != null)
                    {
                        prize = detail.getPrize();
                        status = detail.getStatus();
                        myCodeList = detail.getNumbers().getPlay2().getMy_numbers();
                        prizeCodeList = detail.getNumbers().getPlay2().getOpen_numbers();
                        int length = myCodeList.size();
                        for (int i = 0; i < length; i++)
                        {
                            for (int j : prizeCodeList)
                            {
                                if (myCodeList.get(i).getNumber() == j)
                                {
                                    myCodeList.get(i).setRed(true);
                                }
                            }
                        }
                        codeAdapter.setData(myCodeList);
                        initBitmap(detail.getScrape_type());
                    }
                    break;
                case AUTO_ID:
                    autoOpenAll();
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

    private void initBitmap(String scrapeType)
    {
        for (int i : detail.getNumbers().getPlay2().getOpen_numbers())
        {
            TextView textView = new TextView(getActivity());
            textView.setBackgroundColor(Color.TRANSPARENT);
            textView.setLayoutParams(new LinearLayout.LayoutParams(0, WindowManager.LayoutParams.MATCH_PARENT, 1));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setText(i + "");
            prizeBase.addView(textView);
        }

        if ("1".equals(status))
        {
            auto.setEnabled(false);
            if ("1".equals(scrapeType))
            {
                base.setBackgroundResource(R.drawable.sxggk_bodbg);
                /*leftBase.setImageResource(sxImages[detail.getNumbers().getPlay1()[0] - 1]);
                rightBase.setImageResource(sxImages[detail.getNumbers().getPlay1()[1] - 1]);*/
                leftBase.setImageResource(sxMap.get(detail.getNumbers().getPlay1()[0]));
                rightBase.setImageResource(sxMap.get(detail.getNumbers().getPlay1()[1]));
                prizeBase.setBackgroundResource(R.drawable.sxk_zjhm_bg);
                myBase.setBackgroundResource(R.drawable.sxk_wdhm_bg);
                if (!"0.00".equals(prize))
                {
                    toast.setText(prize);
                    toast.setBackgroundResource(R.drawable.sxkjbj);
                }

                leftPic.setVisibility(View.GONE);
                rightPic.setVisibility(View.GONE);
                prizeCode.setVisibility(View.GONE);
                myCode.setVisibility(View.GONE);
            } else if ("2".equals(scrapeType))
            {
                base.setBackgroundResource(R.drawable.zqggk_bodbg);
                leftBase.setImageResource(jqMap.get(detail.getNumbers().getPlay1()[0]));
                rightBase.setImageResource(jqMap.get(detail.getNumbers().getPlay1()[1]));
                prizeBase.setBackgroundResource(R.drawable.zqggk_zjqyqyhm_kjbg);
                myBase.setBackgroundResource(R.drawable.zqggk_wdqyhm_kjbg);
                if (!"0.00".equals(prize))
                {
                    toast.setText(prize);
                    toast.setBackgroundResource(R.drawable.zqkjbj);
                }

                leftPic.setVisibility(View.GONE);
                rightPic.setVisibility(View.GONE);
                prizeCode.setVisibility(View.GONE);
                myCode.setVisibility(View.GONE);
            }
        } else
        {
            if ("1".equals(scrapeType))
            {
                base.setBackgroundResource(R.drawable.sxggk_bodbg);
                leftBase.setImageResource(sxMap.get(detail.getNumbers().getPlay1()[0]));
                rightBase.setImageResource(sxMap.get(detail.getNumbers().getPlay1()[1]));
                prizeBase.setBackgroundResource(R.drawable.sxk_zjhm_bg);
                myBase.setBackgroundResource(R.drawable.sxk_wdhm_bg);

                leftPic.init(R.drawable.sxk_gsxta, false);
                rightPic.init(R.drawable.sxk_gsxta, false);
                prizeCode.init(R.drawable.sxk_zjhmta, false);
                myCode.init(R.drawable.sxk_wdhmta, true);
            } else if ("2".equals(scrapeType))
            {
                base.setBackgroundResource(R.drawable.zqggk_bodbg);
                leftBase.setImageResource(jqMap.get(detail.getNumbers().getPlay1()[0]));
                rightBase.setImageResource(jqMap.get(detail.getNumbers().getPlay1()[1]));
                prizeBase.setBackgroundResource(R.drawable.zqggk_zjqyqyhm_kjbg);
                myBase.setBackgroundResource(R.drawable.zqggk_wdqyhm_kjbg);

                leftPic.init(R.drawable.zqggk_wdjqsta, false);
                rightPic.init(R.drawable.zqggk_wdjqsta, false);
                prizeCode.init(R.drawable.zqggk_zjqyqyhta, false);
                myCode.init(R.drawable.zqggk_wdqyhmta, true);
            }
            myCode.setAutoOpenListner(new EraseView.AutoOpenListner()
            {
                @Override
                public void onAuto(boolean isMove)
                {
                    if (isFinishing())
                    {
                        return;
                    }
                    if (!isMove)
                        myHandler.sendEmptyMessage(AUTO_OPEN_ORDER);
                }
            });
        }
    }

    private void autoOpenAll()
    {
        if (isFinishing())
        {
            return;
        }

        auto.setEnabled(false);
        leftPic.autoOpen();
        rightPic.autoOpen();
        prizeCode.autoOpen();
        myCode.autoOpen();
        if (!"0.00".equals(prize))
        {
            if ("1".equals(detail.getScrape_type()))
            {
                toast.setBackgroundResource(R.drawable.sxkjbj);
            } else
            {
                toast.setBackgroundResource(R.drawable.zqkjbj);
            }
            toast.setText(prize);
        }
    }

    private class MyHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            if (msg.what == AUTO_OPEN_ORDER)
                autoOpenAll();
        }
    }
}
