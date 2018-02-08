package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.LowerMember;
import com.goldenasia.lottery.data.LowerMemberCommand;
import com.goldenasia.lottery.data.LowerMemberList;
import com.goldenasia.lottery.data.Platform;
import com.goldenasia.lottery.data.TransferLowerMemberCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.user.UserCentre;
import com.goldenasia.lottery.util.NumbericTextWatcher;
import com.goldenasia.lottery.view.adapter.TransferAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gan on 2017/10/2.
 * 下级转账
 */

public class TransferLowerMemberFragment extends BaseFragment {
    private static final String TAG = TransferLowerMemberFragment.class.getSimpleName();
    private static final int TRACE_TRANSFER_ROUTE = 1;//获取 下级名称
    private static final int TRACE_TRANSFER_SUBMIT = 2;//转账


    @BindView(R.id.account_name)
    EditText accountName;
    @BindView(R.id.transfer_amount)
    EditText transferAmount;
    @BindView(R.id.funds_password)
    EditText fundsPassword;

    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.text_lower_member)
    EditText lowerMember;
    @BindView(R.id.image_from)
    ImageView mImageFrom;

    private int lowerMemberUserId=-1;//对方用户ID
    private String lowerMemberUserName;//对方用户Name
    private String mTransferAmount;//转账金额


    private BubblePopupWindow adapterPopupWindow;
    private List items = new ArrayList();
    private UserCentre userCentre;
    private TransferAdapter mAdapter;
    private View mClickView;

    private static final int FIRST_PAGE = 1;
    private int page = FIRST_PAGE;
    private int totalCount;
    ListView issuenoList=null;
    final int endTrigger = 2; // load more content 2 items before the end

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_transfer_lower_member, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userCentre = GoldenAsiaApp.getUserCentre();
        items=new ArrayList<Platform>();

        initPopupWindow();

        transferAmount.addTextChangedListener(new NumbericTextWatcher(8, 2));//整数位最大8位，小数两位
    }

    private void initPopupWindow() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.customized_tips_sub, null);
        BubbleLinearLayout bubbleView = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        adapterPopupWindow = new BubblePopupWindow(rootView, bubbleView);
        mAdapter = new TransferAdapter(getContext(),items);
        mAdapter.setOnIssueNoClickListener((Platform platform) -> {
            lowerMemberUserId=platform.getId();
            lowerMemberUserName=platform.getName();
            lowerMember.setText(lowerMemberUserName);
            lowerMember.setSelection(lowerMemberUserName.length());
            adapterPopupWindow.dismiss();
        });
         issuenoList = (ListView) rootView.findViewById(R.id.issueNoList);

        issuenoList.setAdapter(mAdapter);

        issuenoList.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (issuenoList.getCount() != 0 && (items.size() + 1) < totalCount && issuenoList.getLastVisiblePosition() >= (issuenoList
                        .getCount() - 1) - endTrigger) {
                    netLoadPopupWindow( page + 1);
                }
            }
        });
    }

    @OnClick({R.id.image_from})
    public void showPopupWindow(View v) {
        mClickView=v;
        mImageFrom.setEnabled(false);
        netLoadPopupWindow(FIRST_PAGE);
    }


    @OnClick(R.id.btn_submit)
    public void btnSubmit() {
        if (TextUtils.isEmpty(lowerMember.getText())) {
            showToast("请选择下级", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(accountName.getText())) {
            showToast("请输入开户人姓名", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(transferAmount.getText())) {
            showToast("请输入转移金额", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(fundsPassword.getText())) {
            showToast("请输入资金密码", Toast.LENGTH_SHORT);
            return;
        }

        mTransferAmount=transferAmount.getText().toString();
        double drawMoney = Double.parseDouble(mTransferAmount);
        if(drawMoney<=0){
            Toast.makeText(getActivity(), "转账金额无效", Toast.LENGTH_SHORT).show();
            return;
        }
        if (VerifyMoney(drawMoney)) {
            Toast.makeText(getActivity(), "整数位最大8位，小数两位", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSubmit.setEnabled(false);
        TransferLowerMemberCommand fundsCommand = new TransferLowerMemberCommand();
        fundsCommand.setUser_id(lowerMemberUserId);
        fundsCommand.setReal_name(accountName.getText().toString());
        fundsCommand.setAmount(Double.parseDouble(mTransferAmount));
        fundsCommand.setSecpassword(fundsPassword.getText().toString());

        executeCommand(fundsCommand, restCallback, TRACE_TRANSFER_SUBMIT);
    }

    private void netLoadPopupWindow(int page) {
        this.page = page;
        LowerMemberCommand command = new LowerMemberCommand();
        command.setUsername(userCentre.getUserName());
        command.setCurPage(this.page);
        command.setRange(0);

        executeCommand(command, restCallback, TRACE_TRANSFER_ROUTE);
    }

    //金额验证 整数位最大8位，小数两位
    public static boolean VerifyMoney(double str){
        Pattern pattern=Pattern.compile("\\\\d{1,8}\\\\.\\\\d{1,2}");
        Matcher match=pattern.matcher(String.valueOf(str));
        if(match.matches()==false){
            return false;
        }else{
            return true;
        }
    }
    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case TRACE_TRANSFER_ROUTE:
                    if (response != null) {
                        LowerMemberList lowerMemberList = (LowerMemberList) response.getData();
                        List<LowerMember> lowerMembers = lowerMemberList.getUsers();

                        if (page == FIRST_PAGE) {
                            items.clear();
                            totalCount = lowerMemberList.getUsersCount();
                        }

                        for (LowerMember lower : lowerMembers) {
                            if (!userCentre.getUserInfo().getUserName().equals(lower.getUsername())//去掉当前用户
                                    &&lower.getStatus()==8//去掉冻结用户 status=8表示正常，不等于8的不显示出来
                            ) {
                                Platform platform = new Platform(lower.getUserId(),lower.getUsername(),lower.getUsername());
                                items.add(platform);
                            }
                        }
                        //是否是模糊查询
                        String searchInfo = lowerMember.getText().toString();
//                        if("".equals(searchInfo)) { //不是模糊查询
                            if(items.size()>0){
                                mAdapter.setData(items,lowerMemberUserId);
                                if(page == FIRST_PAGE) {
                                    adapterPopupWindow.showArrowTo(mClickView, BubbleStyle.ArrowDirection.Up);
                                }
                            }else{
                                tipDialog("当前用户没有下级...");
                                return true;
                            }

//                        }else{//是模糊查询
//                            if (items.size() + 1 < totalCount) {
//                                netLoadPopupWindow( page + 1);
//                                return true;
//                            }
//                            List<Platform> findList = new ArrayList<Platform>();
//                            for (Iterator<Platform> iterator = items.iterator(); iterator.hasNext(); ) {
//                                Platform nextMember = iterator.next();
//                                if (nextMember.getCnname().contains(searchInfo))
//                                    findList.add(nextMember);
//                            }
//                            if(findList.size()>0){
//                                mAdapter.setData(findList,lowerMemberUserId);
//                                adapterPopupWindow.showArrowTo(mClickView, BubbleStyle.ArrowDirection.Up);
//                            }else{
//                                tipDialog("当前用户没有下级...");
//                                return true;
//                            }
//                        }
                        mImageFrom.setEnabled(true);
                    }
                    break;
                case TRACE_TRANSFER_SUBMIT:
                    //您向zerotest成功转账67.88元
                    if ("给下级转账成功".equals(response.getErrStr())) {
                        tipDialog( "您向"+lowerMemberUserName+"成功转账"+mTransferAmount+"元");
                    }else{
                        tipDialog(response.getErrStr());
                    }

                    break;
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
            if (state == RestRequest.RUNNING) {
            } else {
                btnSubmit.setEnabled(true);
                hideWaitProgress();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setListViewHeightBasedOnChildren(ListView listView,SwipeRefreshLayout refreshLayout) {
        Log.i(TAG,"setListViewHeightBasedOnChildren");
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                500,
                totalHeight
                        + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
        );
        refreshLayout.setLayoutParams(lp);
    }
}
