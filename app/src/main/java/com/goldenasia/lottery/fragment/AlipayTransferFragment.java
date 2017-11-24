package com.goldenasia.lottery.fragment;


import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.AccountBean;
import com.goldenasia.lottery.data.AlipayTransferBean;
import com.goldenasia.lottery.data.DepositBean;
import com.goldenasia.lottery.data.ShowAccountCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.util.ToastUtils;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created By Sakura
 */
public class AlipayTransferFragment extends BaseFragment
{
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.bank_name)
    TextView bankName;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.copy_name)
    Button copyName;
    @BindView(R.id.account)
    TextView account;
    @BindView(R.id.copy_account)
    Button copyAccount;
    @BindView(R.id.step)
    Button step;
    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.warn_tip)
    TextView warnTip;
    
    private AlipayTransferBean bean;
    private String url;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    
    {
        // Inflate the layout for this fragment
        /*View view = inflater.inflate(R.layout.fragment_alipay_transfer, container, false);
        ButterKnife.bind(this, view);
        return view;*/
        return inflateView(inflater, container, "快速充值", R.layout.fragment_alipay_transfer);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initUI();
        initData();
        initInfo();
    }
    
    private void initUI()
    {
        title.getPaint().setFakeBoldText(true);
        tip.setVisibility(View.INVISIBLE);
        warnTip.setText(getClickableSpan());
        warnTip.setMovementMethod(LinkMovementMethod.getInstance());
    }
    
    private void initData()
    {
        Bundle bundle;
        bundle = getActivity().getIntent().getExtras();
        if (bundle != null)
        {
            bean = (AlipayTransferBean) bundle.getSerializable("TransferBean");
        }
    }
    
    private void initInfo()
    {
        ShowAccountCommand command = new ShowAccountCommand();
        if (bean != null)
        {
            command.setBankID(bean.getBankID());
            command.setDtID(bean.getDtID());
            command.setTrueName(bean.getTrueName());
            command.setAmount(bean.getAmount());
        }
        TypeToken typeToken = new TypeToken<RestResponse<AccountBean>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback,
                0, this);
        restRequest.execute();
    }
    
    public static void launch(BaseFragment fragment, AlipayTransferBean transferBean)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("TransferBean", transferBean);
        FragmentLauncher.launch(fragment.getActivity(), AlipayTransferFragment.class, bundle);
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case 0:
                    AccountBean accountBean = (AccountBean) response.getData();
                    bankName.setText(accountBean.getBank());
                    userName.setText(accountBean.getCard_name());
                    account.setText(accountBean.getCard_num());
                    url = accountBean.getHelpUrl();
                    String deposit = accountBean.getDeposit().getJson();
                    
                    if (!deposit.equals("\"\""))
                    {
                        DepositBean depositBean = GsonHelper.fromJson(deposit, DepositBean.class);
                        tip.setVisibility(View.VISIBLE);
                        tip.setText("请先支付未完成订单（该订单15分钟后自动关闭）\n充值金额： " + depositBean.getAmount() + "\n支付宝真实姓名：" +
                                depositBean.getPlayer_card_name());
                    }
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
            
        }
    };
    
    @OnClick(R.id.step)
    public void onViewClicked()
    {
        if (!"".equals(url) && null != url)
        {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            launchFragment(PayStepFragment.class, bundle);
        }
    }
    
    @OnClick({R.id.copy_name, R.id.copy_account})
    public void onViewClicked(View view)
    {
        ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService(Context
                .CLIPBOARD_SERVICE);
        ToastUtils.showShortToast(getActivity(), "已复制");
        switch (view.getId())
        {
            case R.id.copy_name:
                clipboardManager.setText(userName.getText());
                break;
            case R.id.copy_account:
                clipboardManager.setText(account.getText());
                break;
        }
    }
    
    /**
     * 获取可点击的SpannableString
     *
     * @return
     */
    private SpannableString getClickableSpan()
    {
        SpannableString spannableString = new SpannableString
                ("温馨提示：\n完成转账后，需等待5-10分钟，待支付宝提示“转账到账成功”，且充值金额与支付宝真实姓名如实填写就会自动上分，如未自动上分，请联系客服。");
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.font_color_red)
        ), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 69, 73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                launchFragment(ServiceCenterFragment.class);
            }
        }, 69, 73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.lhc_blue)), 69,
                73, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        
        return spannableString;
    }
}
