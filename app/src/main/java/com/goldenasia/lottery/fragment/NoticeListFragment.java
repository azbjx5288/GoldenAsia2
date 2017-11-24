package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.Notice;
import com.goldenasia.lottery.data.NoticeListCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by Alashi on 2016/1/19.
 */
public class NoticeListFragment extends BaseFragment {
    private static final String TAG = NoticeListFragment.class.getSimpleName();

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.list)
    ListView listView;

    private ArrayList<Notice> notices;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "公告", R.layout.refreshable_list_fragment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadData(false));
        listView.setAdapter(adapter);
        loadData(true);
    }

    private void loadData(boolean withCache) {
        NoticeListCommand command = new NoticeListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Notice>>>() {
        };
        if (withCache) {
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command,
                    typeToken, restCallback, 0, this);
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof ArrayList) {
                notices = (ArrayList<Notice>) restResponse.getData();
            }
            restRequest.execute();
        } else {
            RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, 0, this);
        }
    }

    private RestCallback restCallback = new RestCallback<ArrayList<Notice>>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ArrayList<Notice>> response) {
            notices = response.getData();
            adapter.notifyDataSetChanged();
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7006) {
                CustomDialog dialog=PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
        }
    };

    private BaseAdapter adapter = new BaseAdapter() {

        @Override
        public int getCount() {
            return notices == null ? 0 : notices.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_list_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Notice notice = notices.get(position);
            holder.title.setText(notice.getTitle());
            holder.time.setText(notice.getStartTime());

            return convertView;
        }
    };

    @OnItemClick(R.id.list)
    public void onItemClick(int position) {
        NoticeDetailsFragment.launch(this, true, notices.get(position));
    }

    static class ViewHolder {
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.time)
        TextView time;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }
}
