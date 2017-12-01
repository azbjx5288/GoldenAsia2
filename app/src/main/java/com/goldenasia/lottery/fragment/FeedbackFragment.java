package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.FeedBackCommand;
import com.goldenasia.lottery.game.PromptManager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Gan on 2017/10/11.
 * 意见反馈
 */

public class FeedbackFragment extends BaseFragment {

    private static final int ID_SUBMIT_INFO =1 ;

    @BindView(R.id.add_content)
    EditText mAddContentEditText;
    @BindView(R.id.add_title)
    EditText mAddTitleEditText;


    @BindView(R.id.residual_number)
    TextView residualNumber;

    @BindView(R.id.feeback_back)
    Button mFeebackBackButton;

    @BindView(R.id.feeback_submit)
    Button mFeebackSubmitButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "意见反馈", R.layout.feedback_fragment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAddContentEditText.addTextChangedListener(new TextWatcher());
    }

    @OnClick({R.id.feeback_back, R.id.feeback_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feeback_back://返回
                getActivity().finish();
                break;

            case R.id.feeback_submit://提交
                if (!check()) {
                    return;
                }

                FeedBackCommand command = new FeedBackCommand();
                command.setTitle(mAddTitleEditText.getText().toString());
                command.setContent(mAddContentEditText.getText().toString());
                executeCommand(command, restCallback, ID_SUBMIT_INFO);

                break;
            default:
                break;
        }

    }
    private boolean check() {
        String addTitle = mAddTitleEditText.getText().toString();

        if (addTitle.isEmpty()) {
            showToast("意见标题不能为空", Toast.LENGTH_SHORT);
            return false;
        }

        String addContent = mAddContentEditText.getText().toString();

        if (addContent.isEmpty()) {
            showToast("意见内容不能为空", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    class TextWatcher implements android.text.TextWatcher {
        private TextView view;

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void afterTextChanged(Editable s) {
            int num = s.length();
            num = 200 - num;
            residualNumber.setText(num + "/200");
        }
    }

    public void feedBackTipDialog(String msg) {
        CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
        builder.setMessage(msg);
        builder.setTitle("温馨提示");
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("知道了", (dialog, which) -> {
            dialog.dismiss();
            getActivity().finish();
        });
        builder.create().show();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_SUBMIT_INFO) {
                if ("提交成功".equals(response.getErrStr())) {
                    feedBackTipDialog("提交成功，感谢您的反馈！我们会尽快跟进处理！");
                } else {
                    tipDialog(response.getErrStr());
                }

            }
            return false;
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

        }
    };
}

