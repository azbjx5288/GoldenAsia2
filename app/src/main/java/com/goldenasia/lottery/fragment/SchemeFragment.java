package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.StickyHeaderListView;
import com.goldenasia.lottery.data.CollectScheme;
import com.goldenasia.lottery.data.DeleteCollectCommand;
import com.goldenasia.lottery.data.GetCollectCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ShoppingCart;
import com.goldenasia.lottery.material.Ticket;
import com.goldenasia.lottery.view.adapter.OnValueChangeListener;
import com.goldenasia.lottery.view.adapter.SectionAdapter;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;


public class SchemeFragment extends BaseFragment {

    private static final int GET_COLLECT_TRACK_ID = 1;
    private static final int DELETE_COLLECT_TRACK_ID = 2;


    @BindView(R.id.scheme_listview)
    StickyHeaderListView listView;

    private Lottery lottery;
    private SectionAdapter adapter;
    private List<CollectScheme> collectSchemes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "收藏夹", R.layout.fragment_scheme);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        initView();
    }

    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
    }

    /**
     * 初使化View
     */
    private void initView() {
        adapter = new SectionAdapter(lottery);
        listView.setAdapter(adapter);
        listView.setEmptyView(findViewById(R.id.layout_empty));
        adapter.setOnDeleteChangeListener(new SectionAdapter.OnDeleteChangeListener() {
            @Override
            public void onDeleteChange(CollectScheme item) {
                Iterator<CollectScheme> it = collectSchemes.iterator();
                while (it.hasNext()) {
                    CollectScheme itemCollect = it.next();
                    if (itemCollect.getCollectionId() == item.getCollectionId()) {
                        it.remove();
                    }
                }
                adapter.addData(collectSchemes);
                DeleteCollect("" + item.getCollectionId());
            }
        });

        adapter.setOnManyDeleteChangeListener(new SectionAdapter.OnManyDeleteChangeListener() {
            @Override
            public void onManyDeleteChange(List<CollectScheme> items) {
                StringBuilder builder = new StringBuilder();
                if (items != null) {
                    for (int i = 0, size = items.size(); i < size; i++) {
                        CollectScheme item = items.get(i);
                        builder.append(item.getCollectionId());
                        if (i != size - 1) {
                            builder.append(",");
                        }
                    }
                    collectSchemes.removeAll(items);
                    adapter.addData(collectSchemes);
                    DeleteCollect(builder.toString());
                }
            }
        });

        adapter.setOnBettingPlanListener(new SectionAdapter.OnBettingPlanListener() {
            @Override
            public void onBetting(List<CollectScheme> items) {
                if (items != null) {
                    for (CollectScheme item:items) {
                        Method method = new Method();
                        method.setMethodId(item.getMethodId());
                        method.setCname(item.getCname());

                        Ticket ticket = new Ticket();
                        ticket.setChooseMethod(method);
                        ticket.setCodes(item.getCode());
                        ticket.setChooseNotes(item.getSingleNum());
                        ShoppingCart.getInstance().init(lottery);
                        ShoppingCart.getInstance().addTicket(ticket);

                        ShoppingCart.getInstance().setPrizeMode(item.getPrizeMode() > 0 ? 1 : 0);
                    }

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("lottery", lottery);
                    bundle.putBoolean("page",true);
                    switch (lottery.getLotteryId()) {
                        case 15://亚洲妙妙彩
                            launchFragmentForResult(ShoppingMmcFragment.class, bundle, 1);
                            break;
                        case 44://11选5秒秒彩
                            launchFragmentForResult(Shopping11Select5MmcFragment.class, bundle, 1);
                            break;
                        case 45://快三秒秒彩
                            launchFragmentForResult(ShoppingKuaiSanMmcFragment.class, bundle, 1);
                            break;
                        default:
                            launchFragmentForResult(ShoppingFragment.class, bundle, 1);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        GetCollectLoad();
    }

    private void GetCollectLoad() {
        GetCollectCommand command = new GetCollectCommand();
        command.setLotteryId(lottery.getLotteryId());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<CollectScheme>>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, GET_COLLECT_TRACK_ID, this);
    }

    private void DeleteCollect(String id) {
        DeleteCollectCommand command = new DeleteCollectCommand();
        command.setDeleteItems(id);
        executeCommand(command, restCallback, DELETE_COLLECT_TRACK_ID);
    }

    private RestCallback restCallback = new RestCallback() {

        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case GET_COLLECT_TRACK_ID:
                    collectSchemes = (List<CollectScheme>) response.getData();
                    adapter.addData(collectSchemes);
                    break;
                case DELETE_COLLECT_TRACK_ID:
                    showToast(request.getErrDesc());
                    break;
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

        }
    };
}
