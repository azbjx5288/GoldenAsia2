package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.ContactCloudEditTextImpl;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.CustomScrollView;
import com.goldenasia.lottery.component.TagCloudViewEdit;
import com.goldenasia.lottery.data.LowerTips;
import com.goldenasia.lottery.data.LowerTipsCommand;
import com.goldenasia.lottery.data.SendMsgCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2017/2/3.
 */

public class WriteEmailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener,TagCloudViewEdit.OnTagClickListener {

    private static final int TRACE_SENDMSG_COMMAND = 1;
    private static final int TRACE_LOWER_ID = 2;

    @Bind(R.id.follower)
    RadioButton follower;
    @Bind(R.id.manner_radiogroup)
    RadioGroup mannerRadiogroup;
    @Bind(R.id.addressee)
    ContactCloudEditTextImpl addresseeText;
    @Bind(R.id.add_user)
    ImageView addUser;
    @Bind(R.id.title_text)
    EditText titleText;
    @Bind(R.id.multiline_text)
    EditText multilineText;
    @Bind(R.id.scrollView)
    CustomScrollView scrollView;
    @Bind(R.id.submit)
    Button submit;

    private SendMsgCommand sendMsgCommand;
    private UserCentre userCentre;
    private List<LowerTips> lowerList = new ArrayList<>();
    private HashMap<Integer, Boolean> map = new HashMap<>(0);//记录选择的位置

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_writeemail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userCentre = GoldenAsiaApp.getUserCentre();
        follower.setChecked(true);
        mannerRadiogroup.setOnCheckedChangeListener(this);

        addresseeText.setOnFocusChangeListener((View v, boolean hasFocus) -> {
            if (!hasFocus) {
                List<String> textSpan = addresseeText.getAllReturnStringList();
                if (textSpan.size() > 0) {
                    addresseeText.setText("");
                    for (String str : textSpan) {
                        addresseeText.addSpan(str, str);
                    }
                }
            }
        });
        init();
    }

    /**
     * 利用的是ScrollViewBy的原理在这里直接输入要到的位置的数值即可
     **/
    private void startScroll(int moveToY) {
        scrollView.roreydiuAotuScroll(moveToY);
    }

    private void init() {
        sendMsgCommand = new SendMsgCommand();
        loadMemberInfo();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //child=发给下级用户，parent为发给上级代理。
        switch (checkedId) {
            case R.id.prefect:
                sendMsgCommand.setTarget("parent");
                break;
            case R.id.follower:
                sendMsgCommand.setTarget("child");
                break;
            case R.id.ownership:
                sendMsgCommand.setTarget("parent,child");
                break;
        }
    }
    TagCloudViewEdit userTagCloud;
    List<String> tags;
    @OnClick(R.id.add_user)
    public void addUser() {
        if(lowerList.size()==0){
            showToast("请稍等,正在加载数据");
            return;
        }
        tags = new ArrayList<>();
        for (int i = 0; i < lowerList.size(); i++) {
            tags.add(lowerList.get(i).getUsername());
            map.put(i, false);
        }

        View view=LayoutInflater.from(getContext()).inflate(R.layout.user_cloud_choose, null, false);
        userTagCloud=(TagCloudViewEdit)view.findViewById(R.id.tag_cloud_view);
        userTagCloud.setTags(tags);
        userTagCloud.setOnTagClickListener(this);
        userTagCloud.setOnTagClickListener(new TagCloudViewEdit.OnTagClickListener() {
            @Override
            public void onTagClick(int position) {
                bindPositionView(position);
                LowerTips lowerTips=lowerList.get(position);
                List<String> textSpan = addresseeText.getAllReturnStringList();
                int index=textSpan.indexOf(lowerTips.getUsername());
                if(index!=-1){
                    showToast("止用户已经被选择");
                }else{
                    textSpan.add(textSpan.size(),lowerTips.getUsername());
                    if (textSpan.size()>0) {
                        addresseeText.setText("");
                        for (String str : textSpan) {
                            addresseeText.addSpan(str, str);
                        }
                    }
                }
            }
        });

        tipDialog("选择用户",view);
    }

    /**
     * 定点标签记录和view变化
     **/
    private void bindPositionView(int position) {
        for (int i = 0; i < lowerList.size(); i++) {
            if (i == position) {
                if (map.get(i)) {
                    map.put(i, false);
                } else {
                    map.put(i, true);
                }
            } else {
                if (map.get(i)) {
                    map.put(i, true);
                } else {
                    map.put(i, false);
                }
            }
        }
        userTagCloud.setTagsByPosition(map, tags);
        for (int i = 0; i < lowerList.size(); i++) {
            if (map.get(i)) {
                userTagCloud.getChildAt(i).setBackgroundResource(R.drawable.edit_style_yellow);
            }
        }
    }

    @OnClick(R.id.submit)
    public void sendemail() {
        String addressee = "";//addresseeText.getText.toString();
        String title = titleText.getText().toString();
        String multiline = multilineText.getText().toString();

        if (addressee.isEmpty()) {
            showToast("请输入用户名", Toast.LENGTH_SHORT);
            return;
        }

        if (title.isEmpty()) {
            showToast("请输入标题", Toast.LENGTH_SHORT);
            return;
        }

        if (multiline.isEmpty()) {
            showToast("请输入邮件内容", Toast.LENGTH_SHORT);
            return;
        }
        sendMsgCommand.setSelectChild(addressee);
        sendMsgCommand.setTitle(title);
        sendMsgCommand.setContent(multiline);

        executeCommand(sendMsgCommand, restCallback, TRACE_SENDMSG_COMMAND);
    }

    @Override
    public void onTagClick(int position) {
        if (position == -1) {
            Toast.makeText(getContext(), "点击末尾文字", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "点击 position : " + position, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取db 数据
     */
    private void loadMemberInfo() {
        LowerTipsCommand command = new LowerTipsCommand();
        command.setUsername(userCentre.getUserName());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<LowerTips>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, TRACE_LOWER_ID, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case TRACE_LOWER_ID:
                    if (response != null && response.getData() instanceof ArrayList) {
                        lowerList = (ArrayList) response.getData();
                        addresseeText.setFindInput(lowerList);
                    }
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                showToast("正在更新服务器请稍等");
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

        }
    };
}