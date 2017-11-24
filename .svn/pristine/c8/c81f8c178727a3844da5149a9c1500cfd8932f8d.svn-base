package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.GgcAutoScrapeCommand;
import com.goldenasia.lottery.data.GgcCardEntity;
import com.goldenasia.lottery.data.GgcMyCardList;
import com.goldenasia.lottery.data.GgcMyCardListCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.GgcCardListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class GgcMyPackageFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener
{
    private static final String TAG = GgcMyPackageFragment.class.getSimpleName();

    private static final int CARD_LIST_COMMAND = 1;
    private static final int AUTO_COMMAND = 2;

    /**
     * 服务器分页从1开始
     */
    private static final int FIRST_PAGE = 1;

    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.grid)
    GridView gridView;
    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.auto)
    Button auto;
    @Bind(R.id.amount)
    TextView amount;
    @Bind(R.id.buy)
    Button buy;

    private GgcCardListAdapter adapter;
    private ArrayList<GgcCardEntity> dataList = new ArrayList<>();
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;
    private boolean isFirstTime = true;

    private TypeToken typeToken;
    private GgcMyCardList myCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflateView(inflater, container, true, "我的卡包", R.layout.fragment_ggc_my_package);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        typeToken = new TypeToken<RestResponse<GgcMyCardList>>() {};
        radioGroup.setOnCheckedChangeListener(this);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            if (isCovered())
            {
                loadCoveredList(false, FIRST_PAGE);
            } else
            {
                loadScrapedList(false, FIRST_PAGE);
            }
        });
        final int endTrigger = 2; // load more content 2 dataList before the end
        gridView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1)
            {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
            {
                if (gridView.getCount() != 0 && dataList.size() < totalCount && gridView.getLastVisiblePosition() >=
                        (gridView.getCount() - 1) - endTrigger)
                {
                    if (isCovered())
                    {
                        loadCoveredList(false, page + 1);
                    } else
                    {
                        loadScrapedList(false, page + 1);
                    }

                }
            }
        });
        adapter = new GgcCardListAdapter();
        gridView.setAdapter(adapter);
        initData();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (page == FIRST_PAGE)
        {
            if (isCovered())
            {
                loadCoveredList(isFirstTime, FIRST_PAGE);
            } else
            {
                loadScrapedList(isFirstTime, FIRST_PAGE);
            }
        }
        isFirstTime = false;
    }

    private void initData()
    {
        GgcMyCardListCommand listCommand = new GgcMyCardListCommand();
        listCommand.setStatus(0);
        RestRequestManager.executeCommand(getActivity(), listCommand, typeToken, restCallback, CARD_LIST_COMMAND, this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        refreshLayout.setRefreshing(false);
        isLoading = false;
        dataList.clear();
        switch (checkedId)
        {
            case R.id.covered:
                auto.setEnabled(true);
                loadCoveredList(false, FIRST_PAGE);
                break;
            case R.id.scraped:
                auto.setEnabled(false);
                loadScrapedList(false, FIRST_PAGE);
                break;
        }
    }

    private void loadCoveredList(boolean withCache, int curPage)
    {
        if (isLoading)
        {
            return;
        }
        page = curPage;
        GgcMyCardListCommand command = new GgcMyCardListCommand();
        command.setStatus(0);
        command.setPage(curPage);
        RestRequest restRequest = RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback,
                CARD_LIST_COMMAND, this);
        if (withCache)
        {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof GgcMyCardList)
            {
                myCardList = (GgcMyCardList) restResponse.getData();
                dataList.addAll(myCardList.getCards());
                totalCount = dataList.size();
                adapter.setData(dataList);
            } else
            {
                adapter.setData(null);
            }
        }
    }

    private void loadScrapedList(boolean withCache, int curPage)
    {
        if (isLoading)
        {
            return;
        }
        page = curPage;
        GgcMyCardListCommand command = new GgcMyCardListCommand();
        command.setStatus(1);
        command.setPage(curPage);
        RestRequest restRequest = RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback,
                CARD_LIST_COMMAND, this);
        if (withCache)
        {
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof GgcMyCardList)
            {
                myCardList = (GgcMyCardList) restResponse.getData();
                dataList.addAll(myCardList.getCards());
                totalCount = dataList.size();
                adapter.setData(dataList);
            } else
            {
                adapter.setData(null);
            }
        }
    }

    @OnItemClick(R.id.grid)
    public void onItemClick(int position)
    {
        GgcCardEntity entity = (GgcCardEntity) adapter.getItem(position);
        String cardId = entity.getSc_id();
        Bundle bundle = new Bundle();
        bundle.putSerializable("cardId", cardId);
        launchFragmentForResult(GgcCardDetailFragment.class, bundle, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (isCovered())
        {
            loadCoveredList(false, FIRST_PAGE);
        } else
        {
            loadScrapedList(false, FIRST_PAGE);
        }
    }

    private boolean isCovered()
    {
        return radioGroup.getCheckedRadioButtonId() == R.id.covered;
    }

    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case CARD_LIST_COMMAND:
                    myCardList = (GgcMyCardList) response.getData();
                    if (myCardList == null)
                    {
                        dataList.clear();
                    } else
                    {
                        totalCount = myCardList.getItemCount();
                        if (page == FIRST_PAGE)
                        {
                            dataList.clear();
                        }
                        dataList.addAll(myCardList.getCards());
                    }
                    adapter.setData(dataList);
                    amount.setText("共" + totalCount + "张");
                    break;
                case AUTO_COMMAND:
                    dataList = (ArrayList<GgcCardEntity>) response.getData();
                    adapter.setData(dataList);
                    amount.setText("共" + totalCount + "张");
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 7003)
            {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
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
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {
            if ((request.getId() == CARD_LIST_COMMAND))
            {
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.auto, R.id.buy})
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.auto:
                if (dataList != null)
                {
                    int length = dataList.size();
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < length; i++)
                    {
                        stringBuilder.append(String.valueOf(dataList.get(i).getSc_id()));
                        if (i < length - 1)
                            stringBuilder.append(",");
                    }
                    GgcAutoScrapeCommand command = new GgcAutoScrapeCommand();
                    command.setScIds(stringBuilder.toString());
                    TypeToken typeToken = new TypeToken<RestResponse<ArrayList<GgcCardEntity>>>() {};
                    RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, AUTO_COMMAND,
                            this);
                }
                break;
            case R.id.buy:
                getActivity().finish();
                break;
        }
    }
}