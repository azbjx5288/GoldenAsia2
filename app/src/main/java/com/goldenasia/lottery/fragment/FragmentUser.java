package com.goldenasia.lottery.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.LogoutCommand;
import com.goldenasia.lottery.data.LowerMemberCommand;
import com.goldenasia.lottery.data.LowerMemberList;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.data.UserInfoCommand;
import com.goldenasia.lottery.db.DBHelper;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.VersionChecker;
import com.goldenasia.lottery.util.SharedPreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;


/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 用户信息界面
 */

public class FragmentUser extends BaseFragment
{
    private static final String TAG = FragmentUser.class.getSimpleName();
    private static final int ID_LOGOUT = 1;
    
    private static final int ID_USER_INFO = 2;
    
    private static final int ID_LOWER_MEMBER = 3;
    
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_balance)
    TextView userBalance;
    @BindView(R.id.lower_member_count)
    TextView lowerMemberCount;//下级管理的数量
    
    private UserInfo userInfo;
    
    @BindView(R.id.only_agency_show)
    LinearLayout onlyAgencyShow;
    @BindView(R.id.notice)
    RelativeLayout noticeRelativeLayout;
    @BindView(R.id.feedback)
    RelativeLayout feebackRelativeLayout;
    @BindView(R.id.station_letter_badge)
    TextView station_letter_badge;
    @BindView(R.id.order_layout)
    LinearLayout orderLayout;
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();
        if (userInfo != null)
        {
            userName.setText(userInfo.getNickName());
            userBalance.setText(String.format("账号余额：%.4f", userInfo.getBalance()));
            if (userInfo.getLevel() == 10)
            {
                onlyAgencyShow.setVisibility(View.GONE);//代理账号显示
                orderLayout.setVisibility(View.GONE);
            } else
            {
                onlyAgencyShow.setVisibility(View.VISIBLE);
                orderLayout.setVisibility(View.VISIBLE);
            }
            
        }
        
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        //用户余额实时更新
        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        
        loadLowerMember();
        initMessageCount();
    }
    
    private void initMessageCount()
    {
        int count = ConstantInformation.MESSAGE_COUNT;//SharedPreferencesUtils.getInt(getActivity(),
        // ConstantInformation.APP_INFO, ConstantInformation.MESSAGE_COUNT);
        if (count <= 0)
        {
            count = 0;
        }
        
        Object tag = station_letter_badge.getTag();
        
        if (tag == null)
        {
            QBadgeView qBadgeView = new QBadgeView(getActivity());
            qBadgeView.bindTarget(station_letter_badge);
            qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
            qBadgeView.setBadgeNumber(count);
            station_letter_badge.setTag(qBadgeView);
        } else
        {
            QBadgeView qQBadgeView = (QBadgeView) tag;
            qQBadgeView.setBadgeNumber(count);
        }
        
    }
    
    private void loadLowerMember()
    {
        LowerMemberCommand command = new LowerMemberCommand();
        command.setUsername(userInfo.getUserName());
        command.setCurPage(1);
        command.setRange(1);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback,
                ID_LOWER_MEMBER, this);
        restRequest.execute();
    }
    
    @OnClick({R.id.service_center, R.id.settings, R.id.withdraw_deposit, R.id.transfer, R.id.notice, R.id.recharge, R
            .id.feedback, R.id.station_letter, R.id.lower_member_manager, R.id.add_member_ll, R.id.add_member_link, R
            .id.member_report,R.id.member_order, R.id.logout, R.id.version,})
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.service_center://客服
                launchFragment(ServiceCenterFragment.class);
                break;
            case R.id.settings://设置
                launchFragment(UserSettingsFragment.class);
                break;
            case R.id.recharge://充值
                launchFragment(RechargeApply.class);
                break;
            case R.id.transfer://转账
                if (userInfo.getLevel() == 10)
                {//根据用户类型来判断是否显示下级转账
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("hasTitle", "hasTitle");
                    launchFragment(TransferPlatformFragment.class, bundle);
                } else
                {
                    launchFragment(TransferFragment.class);
                }
                break;
            case R.id.withdraw_deposit://提现
                ArrayList<Class> withdraws = new ArrayList();
                withdraws.add(DrawFragment.class);
                withdraws.add(WithdrawListFragment.class);
                TwoTableFragment.launch(getActivity(), "快速提现", new String[]{"提取余额", "提款记录"}, withdraws);
                break;
            //            case R.id.rebates_setting:
            //                launchFragment(RebatesSetting.class);
            //                break;
            case R.id.notice://历史公告
                launchFragment(NoticeListFragment.class);
                break;
            
            
            case R.id.security_setting:
            
            case R.id.feedback:
                launchFragment(FeedbackFragment.class);
                break;
            case R.id.lower_member_manager:
                launchFragment(LowerMemberSetting.class);//下级管理
                break;
            case R.id.add_member_ll:
                launchFragmentForResult(RegisterSetting.class, null, 1);//立即开户
                break;
            case R.id.add_member_link:
                launchFragmentForResult(RegisterLink.class, null, 1);//链接开户
                break;
            case R.id.member_report:
                //MemberReportMainFragment.launch(this);
                String username = GoldenAsiaApp.getUserCentre().getUserName();
                MemberReportMainFragment2.launch(this, username);
                break;
            case R.id.member_order:
                launchFragment(MemberOrderFragment.class);
                Log.e(TAG, "onClick: " + "fuck");
                break;
            case R.id.station_letter:            //站内信
                launchFragment(FragmentMessageBox.class);
                break;
            case R.id.version:
                new VersionChecker(this).startCheck(true);
                break;
            case R.id.logout:
                new AlertDialog.Builder(getActivity()).setMessage("退出当前账号").setNegativeButton("取消", null)
                        .setPositiveButton("退出", (dialog, which) -> executeCommand(new LogoutCommand(), restCallback,
                                ID_LOGOUT)).create().show();
                break;
            
            default:
                break;
        }
    }
    
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && userName != null)
        {
            executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        }
    }
    
    private void handleExit()
    {
        GoldenAsiaApp.getUserCentre().logout();
        getActivity().finish();
        launchFragment(GoldenLoginFragment.class);
        RestRequestManager.cancelAll();
        deleteMmcWinHistoryDB();
        deleteMmcAwardNumber();
        //        deleteNoticeSP();
    }
    
    private void deleteMmcAwardNumber()
    {
        SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation.SSC_MMC_COUNT,
                1);//亚洲妙妙彩
        SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation
                .ESELECTF_MMC_COUNT, 1);//11选5秒秒彩
        SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, ConstantInformation
                .KUAISAN_MMC_COUNT, 1);//快三秒秒彩
    }
    
    /**
     * 账号退出后删除已经读取的Notice sp中的值
     */
    private void deleteNoticeSP()
    {
        SharedPreferencesUtils.putString(getActivity(), ConstantInformation.APP_INFO, ConstantInformation
                .NOTICE_READ, "");
    }
    
    public void deleteMmcWinHistoryDB()
    {
        DBHelper dBHelper = new DBHelper(getActivity());
        
        new Thread()
        {
            @Override
            public void run()
            {
                dBHelper.deleteAllTable();
            }
        }.start();
    }
    
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            if (request.getId() == ID_USER_INFO)
            {
                UserInfo userInfo = ((UserInfo) response.getData());
                GoldenAsiaApp.getUserCentre().setUserInfo(userInfo);
                if (userInfo != null)
                {
                    userName.setText(userInfo.getNickName());
                    userBalance.setText(String.format("账号余额：%.4f", userInfo.getBalance()));
                }
            } else if (request.getId() == ID_LOWER_MEMBER)
            {
                int totalCount = ((LowerMemberList) response.getData()).getUsersCount();
                lowerMemberCount.setText(String.valueOf(totalCount >= 1 ? totalCount - 1 : 0));
            } else if (request.getId() == ID_LOGOUT)
            {
                handleExit();
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (request.getId() == ID_LOGOUT)
            {
                handleExit();
                return true;
            } else if (errCode == 7003)
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
            if (request.getId() == ID_LOGOUT)
            {
                if (state == RestRequest.RUNNING)
                {
                    showProgress("退出中...");
                } else
                {
                    hideProgress();
                }
            }
        }
    };
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
}