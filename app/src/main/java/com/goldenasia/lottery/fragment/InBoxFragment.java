package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.ReceiveBoxCommand;
import com.goldenasia.lottery.data.ReceiveBoxResponse;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.InBoxAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2017/2/3.
 */

public class InBoxFragment extends BaseFragment {
    private static final String TAG = InBoxFragment.class.getSimpleName();
    private int LIST = 0;
    private int DELETE = 1;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.ll_edit)
    RelativeLayout ll_edit;

    private List<ReceiveBoxResponse.ListBean> list = new ArrayList<ReceiveBoxResponse.ListBean>();

    private static final int FIRST_PAGE = 1;//服务器分页从1开始
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;

    private InBoxAdapter adapter;

    private boolean mStateIsEdit = false;
    private boolean mStateIsSelectAll = false;
    @BindView(R.id.select_all)
    Button select_all;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.edit)
    TextView edit;
    private ArrayList <String> mUnreadMtIdList =new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            loadData(false, FIRST_PAGE);
        });
        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getCount() != 0 && list.size() < totalCount && listView.getLastVisiblePosition() >=
                        (listView.getCount() - 1) - endTrigger) {
                    loadData(false, page + 1);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ReceiveBoxResponse.ListBean bean = list.get(position);

                if (mStateIsEdit) {

                    bean.setState(!bean.isState());
                    list.set(position, bean);


                    adapter.setList(list, mUnreadMtIdList);

                    setSelectAllState();
                } else {
                    mUnreadMtIdList.add(bean.getMt_id());
                    BoxDetailsFragment.launch(InBoxFragment.this, bean.getMsg_id(), "receive");
                }

            }
        });
        adapter = new InBoxAdapter(mStateIsEdit);

        listView.setAdapter(adapter);
        if (page == FIRST_PAGE) {
            //默认不加载缓存的数据
            loadData(false, FIRST_PAGE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        page = FIRST_PAGE;

        adapter.notifyDataSetChanged();
    }

    @OnClick({android.R.id.home, R.id.edit, R.id.select_all, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.edit:
                mStateIsEdit = !mStateIsEdit;

                if(mStateIsEdit){
                    edit.setText("取消");
                    ll_edit.setVisibility(View.VISIBLE);
                }else{
                    edit.setText("编辑");
                    ll_edit.setVisibility(View.GONE);
                }
                adapter.setmStateIsEdit(mStateIsEdit);

                break;
            case R.id.select_all:

                if (mStateIsSelectAll) {
                    mStateIsSelectAll = false;
                    selectAll(mStateIsSelectAll);
                    select_all.setText("全选");
                } else {

                    mStateIsSelectAll = true;
                    selectAll(mStateIsSelectAll);
                    select_all.setText("取消全选");
                }

                break;
            case R.id.delete:
                deleteBox();
                break;

        }
    }

    private void setSelectAllState() {
        int sum = list.size();

        int selectCount = 0;
        for (int i = 0; i < list.size(); i++) {
            ReceiveBoxResponse.ListBean bean = list.get(i);
            if (bean.isState()) {
                selectCount++;
            }
        }

        if (sum == selectCount) {
            mStateIsSelectAll = true;
            select_all.setText("取消全选");
        } else {
            mStateIsSelectAll = false;
            select_all.setText("全选");
        }
    }

    private void deleteBox() {
        StringBuffer deleteBoxIds = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            ReceiveBoxResponse.ListBean bean = list.get(i);
            if (bean.isState()) {
                deleteBoxIds.append(bean.getMt_id()).append(",");
            }
        }

        if ("".equals(deleteBoxIds.toString())) {
            showToast("请选择要删除的选项");
           return;
        }else{
            deleteBoxIds.deleteCharAt(deleteBoxIds.length() - 1);
        }

        ReceiveBoxCommand command = new ReceiveBoxCommand();
        command.setOp("delete");
        command.setDeleteItems(deleteBoxIds.toString());

        executeCommand(command, restCallback, DELETE);
    }

    private void selectAll(boolean stateIsSelectAll) {
        List<ReceiveBoxResponse.ListBean> newList = new ArrayList<ReceiveBoxResponse.ListBean>();
        for (ReceiveBoxResponse.ListBean bean : list) {
            bean.setState(stateIsSelectAll);
            newList.add(bean);
        }
        list = newList;
        adapter.setList(list, mUnreadMtIdList);
    }

    private void loadData(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        ReceiveBoxCommand command = new ReceiveBoxCommand();
        command.setCurPage(page);

        TypeToken typeToken = new TypeToken<RestResponse<ReceiveBoxResponse>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, LIST, this);
    }

    private RestCallback restCallback = new RestCallback<ReceiveBoxResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ReceiveBoxResponse> response) {

            if (request.getId() == LIST) {
                ReceiveBoxResponse receiveBoxResponse = (ReceiveBoxResponse) (response.getData());

                totalCount =Integer.parseInt(receiveBoxResponse.getCount());//  receiveBoxResponse.getList().size();
                if (page == FIRST_PAGE) {
                    list.clear();
                }
                list.addAll(receiveBoxResponse.getList());

                adapter.setList(list, mUnreadMtIdList);

                if(mStateIsEdit){
                    setSelectAllState();
                }

            } else if (request.getId() == DELETE) {
                showToast(response.getErrStr());
                loadData(false, FIRST_PAGE);
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
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
            isLoading = state == RestRequest.RUNNING;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
