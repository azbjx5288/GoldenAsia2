package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.LowerMember;
import com.goldenasia.lottery.data.LowerMemberCommand;
import com.goldenasia.lottery.data.LowerMemberList;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.ToastUtils;
import com.goldenasia.lottery.view.adapter.LowerMemberAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2016/5/2.
 */
public class LowerMemberSetting extends BaseFragment {
    @BindView(R.id.lower_search)
    EditText lowerSearch;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.search)
    Button search;

    @BindView(R.id.no_lowermember)
    RelativeLayout noLowermember;

    private static final int FIRST_PAGE = 1;

    private int page = FIRST_PAGE;
    private int totalCount;
    private boolean isLoading;
    private List items = new ArrayList();
    private LowerMemberAdapter adapter;

    private String username;

    public static void launch(BaseFragment fragment,  String username) {
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        FragmentLauncher.launch(fragment.getActivity(), LowerMemberSetting.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lower_member_setting, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        username=getArguments().getString("username");
        if(TextUtils.isEmpty(username)) {
            username = GoldenAsiaApp.getUserCentre().getUserName();
        }
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> lowerLoad(false, FIRST_PAGE));
        View headerView = LayoutInflater.from(getContext()).inflate(R.layout.lower_member_item_title, null, false);
        list.addHeaderView(headerView);
        adapter = new LowerMemberAdapter(items);
        list.setAdapter(adapter);
        final int endTrigger = 2; // load more content 2 items before the end
        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (list.getCount() != 0 && (items.size() + 1) < totalCount && list.getLastVisiblePosition() >= (list
                        .getCount() - 1) - endTrigger) {
                    lowerLoad(false, page + 1);
                }
            }
        });

        /*list.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) ->
                UserRebateModify.launch(LowerMemberSetting.this,(LowerMember)items.get(position-1))
        );*/

        //该用户下的下级
        list.setOnItemClickListener((AdapterView<?> parent, View v, int position, long id) ->
                LowerMemberSetting.launch(LowerMemberSetting.this,((LowerMember)items.get(position-1)).getUsername())
        );

        lowerLoad(true, FIRST_PAGE);

        lowerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    adapter.setData(items);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @OnClick({R.id.home, R.id.plus_user, R.id.search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home:
                getActivity().finish();
                break;
            case R.id.plus_user:
                /*ArrayList<Class> lowerMembers = new ArrayList();
                lowerMembers.add(RegisterSetting.class);
                lowerMembers.add(LinkAccountSetting.class);
                TwoTableFragment.launch(getActivity(), "注册下级", new String[]{"人工开户", "链接开户"}, lowerMembers, false);*/
                //Bundle bundle = new Bundle();
                //bundle.putSerializable("lottery", lottery);
                launchFragmentForResult(RegisterSetting.class, null, 1);
                break;
            case R.id.search:
                searchLower();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ConstantInformation.REFRESH_RESULT:
                lowerLoad(true, FIRST_PAGE);
                break;
        }
    }

    private void searchLower() {
        String searchInfo = lowerSearch.getText().toString();
        if (TextUtils.isEmpty(searchInfo)) {
            ToastUtils.showShortToast(getActivity(), "请输入搜索内容");
            return;
        }

        List<LowerMember> findList = new ArrayList<LowerMember>();
        for (Iterator<LowerMember> iterator = items.iterator(); iterator.hasNext(); ) {
            LowerMember nextMember = iterator.next();
            if (nextMember.getUsername().contains(searchInfo))
                findList.add(nextMember);
        }
        adapter.setData(findList);
    }

    private void lowerLoad(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        LowerMemberCommand command = new LowerMemberCommand();
        command.setUsername(username);
        command.setCurPage(this.page);
        command.setRange(0);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback, 0, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null) {
                if (page == FIRST_PAGE) {
                    items.clear();
                }
                LowerMemberList lowerMemberList = (LowerMemberList) restResponse.getData();
                if(lowerMemberList!=null)
                {
                    List<LowerMember> lowerMembers = lowerMemberList.getUsers();
                    LowerMember lowerMember = null;
                    for (LowerMember lower : lowerMembers) {
                        if (username.equals(lower.getUsername())) {
                            lowerMember = lower;
                        }
                    }
                    items.addAll(lowerMembers);
                    if (lowerMember != null) {
                        int index = items.indexOf(lowerMember);
                        if (index != -1) {
                            items.remove(index);
                        }
                    }
                    totalCount = lowerMemberList.getUsersCount();
                    if(items.size()>0){
                        adapter.setData(items);
                    }else{
                        noLowerMemberShowHints();
                    }
                }
            } else {
                adapter.setData(null);
            }
        }
        restRequest.execute();
    }

    //没有下级的情况
    private void noLowerMemberShowHints() {
        refreshLayout.setVisibility(View.GONE);
        noLowermember.setVisibility(View.VISIBLE);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            totalCount = ((LowerMemberList) response.getData()).getUsersCount();
            if (page == FIRST_PAGE) {
                items.clear();
            }
            List<LowerMember> lowerMembers = ((LowerMemberList) response.getData()).getUsers();
            LowerMember lowerMember = null;
            for (LowerMember lower : lowerMembers) {
                if (username.equals(lower.getUsername())) {
                    lowerMember = lower;
                }
            }
            items.addAll(lowerMembers);
            if (lowerMember != null) {
                int index = items.indexOf(lowerMember);
                if (index != -1) {
                    items.remove(index);
                }
            }
            if(items.size()>0){
                adapter.setData(items);
            }else{
                noLowerMemberShowHints();
            }
            return true;
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
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
            isLoading = state == RestRequest.RUNNING;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
