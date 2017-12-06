package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.ReceiveBoxResponse;
import com.goldenasia.lottery.data.ReceiveBoxUnReadCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.QBadgeView;

public class FragmentMessageBox extends BaseFragment {
    private static final String TAG = FragmentMessageBox.class.getSimpleName();

    private int RECEIVE = 0;

    @BindView(R.id.in_box_fragment)
    RelativeLayout in_box_fragment;

    @BindView(R.id.in_box_text)
    TextView in_box_text;

    @BindView(R.id.in_box_badge)
    TextView in_box_badge;

    @BindView(R.id.out_box_fragment)
    View out_box_fragment;

    @BindView(R.id.write_email_fragment)
    View write_email_fragment;

    @BindView(R.id.in_box_text_img)
    ImageView in_box_text_img;
    @BindView(R.id.tabUserItemTextArr)
    ImageView tabUserItemTextArr;

    private  String MAIL="mail";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "站内信", R.layout.fragment_message_box);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void loadReceiveBox() {
        ReceiveBoxUnReadCommand command = new ReceiveBoxUnReadCommand();
        command.setIsRead(0);
        TypeToken typeToken = new TypeToken<RestResponse<ReceiveBoxResponse>>() {
        };

        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, RECEIVE, this);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume");
        super.onResume();
        if( SharedPreferencesUtils.getBoolean(getActivity(), ConstantInformation.APP_INFO,MAIL)) {
            int  totalCount=ConstantInformation.MESSAGE_COUNT;

            Object  tag=in_box_text.getTag();
            if(tag==null){
                QBadgeView qBadgeView=new QBadgeView(getActivity());
                qBadgeView.bindTarget(in_box_badge);
                qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
                qBadgeView.setBadgeNumber(totalCount);
                in_box_text.setTag(qBadgeView);
            }else{
                QBadgeView qQBadgeView=(QBadgeView)tag;
                qQBadgeView.setBadgeNumber(totalCount);
            }
        }else{
            Object  tag=in_box_text.getTag();

            if(tag!=null){
                QBadgeView qQBadgeView=(QBadgeView)tag;
                qQBadgeView.setBadgeNumber(0);
            }
        }
    }

    @OnClick({R.id.in_box_fragment, R.id.out_box_fragment, R.id.write_email_fragment})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.in_box_fragment:
                launchFragment(InBoxFragment.class);
                break;
            case R.id.out_box_fragment:
                launchFragment(OutBoxFragment.class);
                break;
            case R.id.write_email_fragment:
                launchFragment(WriteEmailFragment.class);
                break;

            default:
                break;
        }
    }

    private RestCallback restCallback = new RestCallback<ReceiveBoxResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ReceiveBoxResponse> response) {

            if (request.getId() == RECEIVE) {
                ReceiveBoxResponse receiveBoxResponse = (ReceiveBoxResponse) (response.getData());
                int totalCount =receiveBoxResponse.getList().size();// Integer.parseInt(receiveBoxResponse.getCount());//解决服务端 返回数据 有缓存的 问题
                if(totalCount>0){
                    totalCount =-1;
                }else{
                    totalCount =0;
                }

//                new QBadgeView(getActivity()).bindTarget(in_box_text).setBadgeGravity(Gravity.TOP | Gravity.CENTER).setBadgeNumber(totalCount);

                Object  tag=in_box_badge.getTag();

                if(tag==null){
                    QBadgeView qBadgeView=new QBadgeView(getActivity());
                    qBadgeView.bindTarget(in_box_badge);
                    qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
                    qBadgeView.setBadgeNumber(totalCount);
                    in_box_badge.setTag(qBadgeView);
                }else{
                    QBadgeView qQBadgeView=(QBadgeView)tag;
                    qQBadgeView.setBadgeNumber(totalCount);
                }
            }

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

}