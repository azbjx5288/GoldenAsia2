package com.goldenasia.lottery.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.LogoutCommand;
import com.goldenasia.lottery.data.LowerMemberCommand;
import com.goldenasia.lottery.data.LowerMemberList;
import com.goldenasia.lottery.data.ServiceSystemBean;
import com.goldenasia.lottery.data.ServiceSystemCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.data.UserInfoCommand;
import com.goldenasia.lottery.db.DBHelper;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.VersionChecker;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.view.adapter.CustometServiceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;


/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 用户信息界面
 */

public class FragmentUser extends BaseFragment {
    private static final String TAG = FragmentUser.class.getSimpleName();
    private static final int ID_LOGOUT = 1;

    private static final int ID_USER_INFO = 2;

    private static final int ID_LOWER_MEMBER = 3;

    private static final int SERVICE_SYSTEM = 4;

    private static final int ID_USER_INFO_REFRESH = 5;

    private final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 6;

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
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();
        if (userInfo != null) {
            userName.setText(userInfo.getNickName());
            userBalance.setText(String.format("账号余额：%.4f", userInfo.getBalance()));
            if (userInfo.getLevel() == 10) {
                onlyAgencyShow.setVisibility(View.GONE);//代理账号显示
                orderLayout.setVisibility(View.GONE);
            } else {
                onlyAgencyShow.setVisibility(View.VISIBLE);
                orderLayout.setVisibility(View.VISIBLE);
            }

        }
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshableView);
        //设置刷新时动画的颜色，可以设置4个
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showToast("正在刷新");
                executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO_REFRESH);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        //用户余额实时更新
        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        loadLowerMember();
        initMessageCount();
    }

    private void initMessageCount() {
        int count = ConstantInformation.MESSAGE_COUNT;//SharedPreferencesUtils.getInt(getActivity(),
        // ConstantInformation.APP_INFO, ConstantInformation.MESSAGE_COUNT);
        if (count <= 0) {
            count = 0;
        }

        Object tag = station_letter_badge.getTag();

        if (tag == null) {
            QBadgeView qBadgeView = new QBadgeView(getActivity());
            qBadgeView.bindTarget(station_letter_badge);
            qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
            qBadgeView.setBadgeNumber(count);
            station_letter_badge.setTag(qBadgeView);
        } else {
            QBadgeView qQBadgeView = (QBadgeView) tag;
            qQBadgeView.setBadgeNumber(count);
        }

    }

    private void loadLowerMember() {
        LowerMemberCommand command = new LowerMemberCommand();
        command.setUsername(userInfo.getUserName());
        command.setCurPage(1);
        command.setRange(1);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback,
                ID_LOWER_MEMBER, this);
        restRequest.execute();
    }

    private void showServiceDialog(List<ServiceSystemBean> list) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.service_dialog, null);
        ListView listView = view.findViewById(R.id.list_view);
        ImageView exit_pressed = view.findViewById(R.id.exit_pressed);
//        List<Map<String, String>> listMap=new ArrayList<Map<String, String>>();
//        // key值数组，适配器通过key值取value，与列表项组件一一对应
//        String[] from = { "name"};
//        for(int i=0;i<list.size();i++){
//            Map<String, String> map=new HashMap<>();
//            map.put("name",list.get(i).getName());
//            listMap.add(map);
//        }
//        // 列表项组件Id 数组
//        int[] to = { R.id.button_name };
//        final SimpleAdapter adapter = new SimpleAdapter(getActivity(), listMap, R.layout.service_dialog_item, from, to);
        CustometServiceAdapter adapter = new CustometServiceAdapter(list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("url", list.get(arg2).getUrl());
                launchFragment(ServiceCenterFragment2.class, bundle);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final AlertDialog dialog = builder.create();

        dialog.setView(view, 0, 0, 0, 0);// 设置边距为0,保证在2.x的版本上运行没问题

        exit_pressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //===================6.0运行时权限需要添加的 start==========================================================//
    private void contactCustomerService() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                &&
                ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
            executeCommand(new ServiceSystemCommand(), restCallback, SERVICE_SYSTEM);
        } else {

         /*   ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);*/

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    &&ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)
                    ) {
                showDialog01();
            }else{
                showDialog02();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                executeCommand(new ServiceSystemCommand(), restCallback,
                        SERVICE_SYSTEM);
            } else {
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    //===================6.0运行时权限需要添加的 end==========================================================//


    @OnClick({R.id.service_center, R.id.settings, R.id.withdraw_deposit, R.id.transfer, R.id.notice, R.id.recharge, R
            .id.feedback, R.id.station_letter, R.id.lower_member_manager, R.id.add_member_ll, R.id.add_member_link, R
            .id.member_report, R.id.member_order, R.id.logout, R.id.version})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.service_center://客服
                contactCustomerService();

                break;
            case R.id.settings://设置
                launchFragment(UserSettingsFragment.class);
                break;
            case R.id.recharge://充值
                launchFragment(RechargeApply.class);
                break;
            case R.id.transfer://转账
                if (TextUtils.isEmpty(userInfo.getRealName())) {
                    CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                    builder.setMessage("请先设置安全资料");
                    builder.setTitle("温馨提示");
                    builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
                    builder.setNegativeButton("取消", (dialog, which) ->
                    {
                        dialog.dismiss();
                    });
                    builder.setPositiveButton("去设置", (dialog, which) ->
                    {
                        launchFragment(SecuritySetting.class);
                        dialog.dismiss();
//                        getActivity().finish();
                    });
                    builder.create().show();
                    return;
                }

                if (userInfo.getLevel() == 10) {//根据用户类型来判断是否显示下级转账
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("hasTitle", "hasTitle");
                    launchFragment(TransferPlatformFragment.class, bundle);
                } else {
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
                new VersionChecker(getActivity()).startCheck(true);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && userName != null) {
            executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
        }
    }

    private void handleExit() {
        GoldenAsiaApp.getUserCentre().logout();
        getActivity().finish();
        launchFragment(GoldenLoginFragment.class);
        RestRequestManager.cancelAll();
        deleteMmcWinHistoryDB();
        deleteMmcAwardNumber();
        //        deleteNoticeSP();
    }

    private void deleteMmcAwardNumber() {
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
    private void deleteNoticeSP() {
        SharedPreferencesUtils.putString(getActivity(), ConstantInformation.APP_INFO, ConstantInformation
                .NOTICE_READ, "");
    }

    public void deleteMmcWinHistoryDB() {
        DBHelper dBHelper = new DBHelper(getActivity());

        new Thread() {
            @Override
            public void run() {
                dBHelper.deleteAllTable();
            }
        }.start();
    }


    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_USER_INFO) {
                UserInfo userInfoP = ((UserInfo) response.getData());
                userInfo=userInfoP;
                GoldenAsiaApp.getUserCentre().setUserInfo(userInfoP);
                if (userInfoP != null) {
                    userName.setText(userInfoP.getNickName());
                    userBalance.setText(String.format("账号余额：%.4f", userInfoP.getBalance()));
                }
            } else if (request.getId() == ID_USER_INFO_REFRESH) {
                UserInfo userInfo = ((UserInfo) response.getData());
                GoldenAsiaApp.getUserCentre().setUserInfo(userInfo);
                if (userInfo != null) {
                    userName.setText(userInfo.getNickName());
                    userBalance.setText(String.format("账号余额：%.4f", userInfo.getBalance()));
                }

                swipeRefreshLayout.setRefreshing(false);
            } else if (request.getId() == ID_LOWER_MEMBER) {
                int totalCount = ((LowerMemberList) response.getData()).getUsersCount();
                lowerMemberCount.setText(String.valueOf(totalCount >= 1 ? totalCount - 1 : 0));
            } else if (request.getId() == ID_LOGOUT) {
                handleExit();
            } else if (request.getId() == SERVICE_SYSTEM) {
                String jsonString = ((JsonString) response.getData()).getJson();
                List<ServiceSystemBean> list = new ArrayList<ServiceSystemBean>();
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ServiceSystemBean serviceSystemBean = new ServiceSystemBean();
                        serviceSystemBean.setName(jsonObject.getString("name"));
                        serviceSystemBean.setUrl(jsonObject.getString("url"));
                        list.add(serviceSystemBean);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (list.size() > 1) {
                    showServiceDialog(list);
                } else if (list.size() == 1) {
                    ServiceSystemBean ServiceSystemBean = list.get(0);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url", ServiceSystemBean.getUrl());
                    launchFragment(ServiceCenterFragment2.class, bundle);
                } else {

                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (request.getId() == ID_LOGOUT) {
                handleExit();
                return true;
            } else if (errCode == 7003) {
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
            if (request.getId() == ID_LOGOUT) {
                if (state == RestRequest.RUNNING) {
                    showProgress("退出中...");
                } else {
                    hideProgress();
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}