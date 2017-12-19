package com.goldenasia.lottery.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.DelRegLinkCommand;
import com.goldenasia.lottery.data.RegLinksBean;
import com.goldenasia.lottery.data.RegLinksCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.LinkListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Sakura on 2017/12/8.
 */

public class LinkManagement extends BaseFragment
{
    private static final int GET_LINKS = 1;
    private static final int DEL_LINKS = 2;
    @BindView(R.id.link_total)
    TextView linkTotal;
    @BindView(R.id.user_total)
    TextView userTotal;
    @BindView(R.id.link_list)
    RecyclerView linkList;
    Unbinder unbinder;

    private LinkListAdapter linkListAdapter;
    private LinearLayoutManager linearLayoutManager;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflateView(inflater, container, true, "链接管理", R.layout.link_management);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linkList.setLayoutManager(linearLayoutManager);
        linkList.setHasFixedSize(true);
        linkListAdapter = new LinkListAdapter();
        linkList.setAdapter(linkListAdapter);
        linkListAdapter.setOnDetailClickListner(new LinkListAdapter.OnDetailClickListner()
        {
            @Override
            public void onDetailClick(View view, RegLinksBean curData)
            {
                Bundle bundle = new Bundle();
                bundle.putSerializable("detail", curData);
                launchFragment(LinkDetailFragment.class, bundle);
            }
        });
        linkListAdapter.setOnDeleteClickListner(new LinkListAdapter.OnDeleteClickListner()
        {
            @Override
            public void onDeleteClick(View view, RegLinksBean curData)
            {
                CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
                builder.setMessage("删除链接后，将不能从该链接注册账号。\n" + "确定删除吗？");
                builder.setNegativeButton("确认删除", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                        DelRegLinkCommand delRegLinkCommand = new DelRegLinkCommand();
                        delRegLinkCommand.setSu_id(curData.getSu_id());
                        executeCommand(delRegLinkCommand, restCallback, DEL_LINKS);
                    }
                });
                builder.setPositiveButton("我再想想", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });
        
        getLinksRequest();
    }
    
    private void getLinksRequest()
    {
        RegLinksCommand regLinksCommand = new RegLinksCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<RegLinksBean>>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), regLinksCommand, typeToken,
                restCallback, GET_LINKS, this);
        restRequest.execute();
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case GET_LINKS:
                    if (response != null /*&& response.getData() != null*/)
                    {
                        ArrayList<RegLinksBean> list = new ArrayList<>();
                        if (response.getData() != null)
                            list = (ArrayList<RegLinksBean>) response.getData();
                        linkListAdapter.setData(list);
                        int userCount = 0;
                        for (RegLinksBean bean : list)
                        {
                            userCount += Integer.valueOf(bean.getUserNum());
                        }
                        linkTotal.setText(String.valueOf(list.size()));
                        userTotal.setText(String.valueOf(userCount));
                    }
                    break;
                case DEL_LINKS:
                    getLinksRequest();
                    break;
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 7003)
            {
                showToast("正在更新服务器请稍等", Toast.LENGTH_LONG);
                return true;
            } else if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, int state)
        {
        
        }
    };
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
