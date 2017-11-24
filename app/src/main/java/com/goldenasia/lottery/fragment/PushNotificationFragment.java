package com.goldenasia.lottery.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;


import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.VersionChecker;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.view.TimePickerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * Created by Gan on 2017/11/11.
 */

public class PushNotificationFragment extends BaseFragment {

    private  String WIN="win";
    private  String UPDATE="update";
    private  String MAIL="mail";
    private  String TIME1="time1";
    private  String TIME2="time2";

    @BindView(R.id.time_text_view)
    TextView time_text_view;
    @BindView(R.id.switch_win)
    Switch switch_win;
    @BindView(R.id.switch_update)
    Switch switch_update;
    @BindView(R.id.switch_mail)
    Switch switch_mail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "推送通知", R.layout.fragment_push_notification);
        ButterKnife.bind(this, view);

        switch_win.setChecked(SharedPreferencesUtils.getBoolean(getActivity(), ConstantInformation.APP_INFO, WIN));
        switch_update.setChecked(SharedPreferencesUtils.getBoolean(getActivity(), ConstantInformation.APP_INFO, UPDATE));
        switch_mail.setChecked(SharedPreferencesUtils.getBoolean(getActivity(), ConstantInformation.APP_INFO, MAIL));

        String time01=parseTimeIntToString(getInt(getActivity(), ConstantInformation.APP_INFO, TIME1));
        String time02=parseTimeIntToString(getInt(getActivity(), ConstantInformation.APP_INFO, TIME2));

        time_text_view.setText(time01+"-"+time02);
        return view;
    }

    private String parseTimeIntToString(int time){
        if(time<10){
            return "0"+time+":00";
        }else{
            return String.valueOf(time)+":00";
        }
    }

    private  int getInt(Context context, String fileName, String key)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    private void showTimePickerDialog() {
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.wheel_double_view, null);
        TimePickerLayout mTimePickerLayout=(TimePickerLayout)view.findViewById(R.id.time_picker);

        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setContentView(view);
        builder.setTitle("选择");
        builder.setLayoutSet(DialogLayout.UP_AND_DOWN);
        builder.setPositiveButton("确定", (dialog, which) -> {

            StringBuffer sb=new StringBuffer(mTimePickerLayout.getTime01());
            sb.append("-").append(mTimePickerLayout.getTime02());

            time_text_view.setText(sb.toString());


            SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, TIME1,mTimePickerLayout.getmCurrTime01Index());
            SharedPreferencesUtils.putInt(getActivity(), ConstantInformation.APP_INFO, TIME2,mTimePickerLayout.getmCurrTime02Index());


            dialog.dismiss();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();

    }

    @OnClick({R.id.time_rl_layout})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.time_rl_layout:
                showTimePickerDialog();
                break;
            case R.id.security_setting:
                launchFragment(SecuritySetting.class);
                break;
            case R.id.card_setting:
                launchFragment(BankCardSetting.class);
                break;
            case R.id.push_notification:
                new VersionChecker(this).startCheck(true);
                break;


            default:
                break;
        }
    }

    @OnCheckedChanged({R.id.switch_mail,R.id.switch_update,R.id.switch_win})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.switch_mail:
                if(isChecked){
                    SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.APP_INFO, MAIL, true);
                }else {
                    SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.APP_INFO, MAIL, false);
                }
                break;
            case R.id.switch_update:
                if(isChecked){
                    SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.APP_INFO, UPDATE, true);
                }else {
                    SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.APP_INFO, UPDATE, false);
                }
                break;
            case R.id.switch_win:
                if(isChecked){
                    SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.APP_INFO, WIN, true);
                }else {
                    SharedPreferencesUtils.putBoolean(getActivity(), ConstantInformation.APP_INFO, WIN, false);
                }
                break;
            default:
                break;
        }
    }


}
