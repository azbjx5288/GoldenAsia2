package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.EditSecurityCommand;
import com.goldenasia.lottery.data.UserInfo;
import com.goldenasia.lottery.data.UserInfoCommand;

import org.apache.commons.codec.digest.DigestUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 安全资料
 * Created by Alashi on 2016/5/2.
 */
public class SecuritySetting extends BaseFragment {
    private static final int ID_USER_INFO = 1;
    private static final int ID_SUBMIT_INFO  = 2;

    @BindView(R.id.state_empty)
    LinearLayout stateEmpty;
    @BindView(R.id.state_empty_name)
    EditText stateEmptyName;
    @BindView(R.id.new_password)
    EditText newPassword;
    @BindView(R.id.new_password_verify)
    EditText newPasswordVerify;
    @BindView(R.id.state_empty_mail)
    EditText stateEmptyMail;

    @BindView(R.id.state_done)
    LinearLayout stateDone;
    @BindView(R.id.name)
    TextView name;

    @BindView(R.id.bottom)
    LinearLayout bottom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "安全资料", R.layout.security_setting);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        if (!check()) {
            return;
        }

        EditSecurityCommand command = new EditSecurityCommand();
        command.setRealName(stateEmptyName.getText().toString());
        //资金密码MD5加密
        command.setSecpassword(DigestUtils.md5Hex(newPassword.getText().toString()));
        command.setSecpassword2( DigestUtils.md5Hex(newPasswordVerify.getText().toString()));
        //command.setEmail(stateEmptyMail.getText().toString());

        executeCommand(command, restCallback, ID_SUBMIT_INFO);
    }

    private boolean check() {
        String name = stateEmptyName.getText().toString();
        String newP = newPassword.getText().toString();
        String newPv = newPasswordVerify.getText().toString();
        //String emil = stateEmptyMail.getText().toString();

        if (name.isEmpty()) {
            showToast("请输入取款人姓名", Toast.LENGTH_SHORT);
            return false;
        }
        if (!name.matches("[\\u4E00-\\u9FA5]*")) {
            showToast("请输入正确的取款人姓名", Toast.LENGTH_SHORT);
            return false;
        }
        if (newP.isEmpty()) {
            showToast("请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (newPv.isEmpty()) {
            showToast("请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (newPv.length() < 6 || newP.length() < 6) {
            showToast("新密码太短，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }
        if (newPv.length() > 20 || newP.length() > 20) {
            showToast("新密码太长，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        if (!newP.equals(newPv)) {
            showToast("输入的新密码不一样，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        if (!newP.matches(regex)) {
            showToast("新密码必须包含数字和字母，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        /*if (emil.isEmpty()) {
            showToast("请输入安全邮箱", Toast.LENGTH_SHORT);
            return false;
        }
        if (!emil.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")) {
            showToast("请输入正确的安全邮箱", Toast.LENGTH_SHORT);
            return false;
        }*/

        return true;
    }

    private void updateUI(boolean isEmpty) {
        stateEmpty.setVisibility(isEmpty? View.VISIBLE : View.GONE);
        bottom.setVisibility(isEmpty? View.VISIBLE : View.GONE);

        stateDone.setVisibility(isEmpty? View.GONE : View.VISIBLE);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_SUBMIT_INFO){
                showToast("安全信息设置成功", Toast.LENGTH_SHORT);
                executeCommand(new UserInfoCommand(), restCallback, ID_USER_INFO);
            } else if (request.getId() == ID_USER_INFO) {
                UserInfo userInfo = ((UserInfo) response.getData());
                GoldenAsiaApp.getUserCentre().setUserInfo(userInfo);
                if(userInfo.getRealName().length()>0){
                    name.setText(userInfo.getRealName().subSequence(0,1)+"***");//显示姓名的第一个字加三个*
                }
                updateUI(TextUtils.isEmpty(userInfo.getRealName()));
            }
            return false;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {

        }
    };
}
