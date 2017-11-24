package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.Notice;
import com.goldenasia.lottery.data.NoticeDetail;
import com.goldenasia.lottery.data.NoticeDetailCommand;
import com.goldenasia.lottery.game.PromptManager;

import butterknife.Bind;

/**
 * 公告或banner的详情页
 * Created by Alashi on 2016/1/19.
 */
public class NoticeDetailsFragment extends BaseFragment {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.time)
    TextView time;
    @Bind(R.id.content)
    TextView content;

    public static void launch(BaseFragment fragment, boolean isNotice, Notice notice) {
        Bundle bundle = new Bundle();
        bundle.putString("notice", GsonHelper.toJson(notice));
        bundle.putBoolean("isNotice", isNotice);
        FragmentLauncher.launch(fragment.getActivity(), NoticeDetailsFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "详情", R.layout.notice_details);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Notice notice = GsonHelper.fromJson(getArguments().getString("notice"), Notice.class);
        boolean isNotice = getArguments().getBoolean("isNotice");
        loadNoticeDetail(isNotice, notice);
    }

    private void loadNoticeDetail(boolean isNotice, Notice notice) {
        NoticeDetailCommand command = new NoticeDetailCommand();
        command.setNoticeid(notice.getAdId());
        command.setType(isNotice ? 4 : 3);//4:公告；3:banner
        command.setNoticeid(notice.getNoticeID());
        executeCommand(command, restCallback);
    }

    private RestCallback restCallback = new RestCallback<NoticeDetail>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<NoticeDetail> response) {
            NoticeDetail noticeDetail = response.getData();
            title.setText(noticeDetail.getTitle());
            time.setText(noticeDetail.getStartTime());
            content.setText(Html.fromHtml(noticeDetail.getContent()));
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog=PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                showProgress("加载中...");
            } else {
                hideProgress();
            }
        }
    };
}
