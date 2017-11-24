package com.goldenasia.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.goldenasia.lottery.data.Order;
import com.goldenasia.lottery.data.OrderListCommand;
import com.goldenasia.lottery.data.OrderListResponse;
import com.goldenasia.lottery.data.OrderType;
import com.goldenasia.lottery.game.PromptManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 资金明细的某一种类别页面，“全部、投注、派奖、充值、提现”
 * Created by Alashi on 2016/1/19.
 */
public class BalanceDetailsFragment extends BaseFragment {
    private static final String TAG = BalanceDetailsFragment.class.getSimpleName();
    /**
     * 服务器分页从1开始
     */
    private static final int FIRST_PAGE = 1;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.list)
    ListView listView;

    private int totalCount;
    private List<Order> orders = new ArrayList<>();
    private SparseArray<String> orderTypes = new SparseArray<>();
    private int page = FIRST_PAGE;
    private boolean isLoading;

    /**
     * 其他的类别：全部、投注、派奖、充值、提现
     */
    private int orderType;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.refreshable_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadData(false, FIRST_PAGE));

        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getCount() != 0 && orders.size() < totalCount && listView.getLastVisiblePosition() >=
                        (listView.getCount() - 1) - endTrigger) {
                    loadData(false, page + 1);
                }
            }
        });

        listView.setAdapter(adapter);
        loadData(true, FIRST_PAGE);
    }

    /**
     * 0：全部
     * 1：投注，含撤单返款
     * 2：派奖，含撤销派奖
     * 3：充值
     * 4：提现， 含取消取现
     * 5: 转移资金
     */
    public void setOrderType(int type) {
        this.orderType = type;
    }

    /*@OnItemClick(R.id.list)
    public void onItemClick(int position) {
        Order order = orders.get(position);
        //401:投注; 301:投注返点; 303:撤单返款; 308:中奖; 413:撤消中奖;
        // 411:撤消返点
        int type = order.getType();
        if (type == 401 || type == 301 || type == 303 || type == 308 || type == 413 || type == 411) {
            Bet bet = new Bet();
            bet.setWrapId(order.getWrapId());
            if (order.getLotteryId() == 17)
                BetOrTraceDetailLhcFragment.launch(this, bet);
            else if (order.getLotteryId() == 15)
                BetOrTraceDetailMmcFragment.launch(this, bet);
            else
                BetOrTraceDetailFragment.launch(this, bet);
        }
    }*/

    private void loadData(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        OrderListCommand command = new OrderListCommand();
        command.setCurPage(page);
        command.setOrderType(orderType);
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, restCallback, 0, this);
        if (withCache) {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null) {
                OrderListResponse orderListResponse = (OrderListResponse) restResponse.getData();
                if (orderListResponse != null) {
                    orders.addAll(orderListResponse.getOrders());
                    if (orderListResponse.getTypes() != null) {
                        for (OrderType orderType : orderListResponse.getTypes()) {
                            orderTypes.put(orderType.getNum(), orderType.getDes());
                        }
                    }
                }
            }
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback<OrderListResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<OrderListResponse> response) {
            OrderListResponse orderListResponse = response.getData();
            totalCount = orderListResponse.getCount();
            if (page == FIRST_PAGE) {
                orders.clear();
            }
            orders.addAll(orderListResponse.getOrders());
            orderTypes.clear();
            for (OrderType orderType : orderListResponse.getTypes()) {
                orderTypes.put(orderType.getNum(), orderType.getDes());
            }
            adapter.notifyDataSetChanged();
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

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return orders == null ? 0 : orders.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.balance_details_item, parent,
                        false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Order order = orders.get(position);
            holder.name.setText(orderTypes.get(order.getType()));
            holder.rebates.setText(order.getAmount());
            holder.time.setText(order.getCreateTime());
            holder.money.setText(order.getBalance());
            return convertView;
        }
    };

    static class ViewHolder {
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.rebates)
        TextView rebates;
        @Bind(R.id.prize)
        TextView money;
        @Bind(R.id.time)
        TextView time;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }
}
