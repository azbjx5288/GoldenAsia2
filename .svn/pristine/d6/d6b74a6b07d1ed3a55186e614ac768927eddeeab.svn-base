package com.goldenasia.lottery.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.CycleViewPager;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.component.ScrollGridView;
import com.goldenasia.lottery.component.ViewFactory;
import com.goldenasia.lottery.data.BannerListCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryMenu;
import com.goldenasia.lottery.data.LotteryMenuCommand;
import com.goldenasia.lottery.data.Notice;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.user.UserCentre;
import com.goldenasia.lottery.view.adapter.FavouriteAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Sakura on 2017/3/14.
 */

public class LotteryFragment extends BaseFragment
{
    private static final String TAG = LotteryFragment.class.getSimpleName();
    
    private static final int LOTTERY_TRACE_ID = 1;
    private static final int BANNER_LIST_ID = 2;
    
    @Bind(R.id.ssc_gridview)
    ScrollGridView sscGridview;
    @Bind(R.id.syxw_gridview)
    ScrollGridView syxwGridview;
    @Bind(R.id.ks_gridview)
    ScrollGridView ksGridview;
    @Bind(R.id.low_gridview)
    ScrollGridView lowGridview;
    @Bind(R.id.others_gridview)
    ScrollGridView othersGridview;
    @Bind(R.id.edit_favourite)
    ImageView editFavourite;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    /*@Bind(R.id.box_bottom_layout)
    LinearLayout boxBottomLayout;*/
    
    private TextView sscDesc;
    private TextView syxwDesc;
    private TextView ksDesc;
    private TextView lowDesc;
    
    private ImageAdapter sscAdapter;
    private ImageAdapter syxwAdapter;
    private ImageAdapter ksAdapter;
    private ImageAdapter lowAdapter;
    private ImageAdapter othersAdapter;
    private CycleViewPager cycleViewPager;
    private ArrayList<Lottery> lotteryList = new ArrayList<>();
    private ArrayList<Lottery> sscLotteryList = new ArrayList<>();
    private ArrayList<Lottery> syxwLotteryList = new ArrayList<>();
    private ArrayList<Lottery> ksLotteryList = new ArrayList<>();
    private ArrayList<Lottery> lowLotteryList = new ArrayList<>();
    private ArrayList<Lottery> othersLotteryList = new ArrayList<>();
    private ArrayList<Lottery> favouriteLotteryList = new ArrayList<>();
    private ArrayList<Notice> notices;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_lottery, container,
                false);
        ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        //new VersionChecker(this).startCheck();
        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.fragment_cycle_viewpager_content);
        initView();
        loadBanner();
        initGridView();
    }
    
    private void initView()
    {
        View sscLayout = findViewById(R.id.ssc);
        ImageView sscImage = (ImageView) sscLayout.findViewById(R.id.icon);
        sscImage.setImageResource(R.drawable.ic_group_ssc);
        TextView sscName = (TextView) sscLayout.findViewById(R.id.name);
        sscName.setText("时时彩");
        sscDesc = (TextView) sscLayout.findViewById(R.id.desc);
        sscDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color
                .app_main_support));
        
        View syxwLayout = findViewById(R.id.syxw);
        ImageView syxwImage = (ImageView) syxwLayout.findViewById(R.id.syxw)
                .findViewById(R.id.icon);
        syxwImage.setImageResource(R.drawable.ic_group_11x5);
        TextView syxwName = (TextView) syxwLayout.findViewById(R.id.name);
        syxwName.setText("11选5");
        syxwDesc = (TextView) syxwLayout.findViewById(R.id.desc);
        syxwDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color
                .app_main_support));
        
        View ksLayout = findViewById(R.id.ks);
        ImageView ksImage = (ImageView) ksLayout.findViewById(R.id.ks)
                .findViewById(R.id.icon);
        ksImage.setImageResource(R.drawable.ico_group_k3);
        TextView ksName = (TextView) ksLayout.findViewById(R.id.name);
        ksName.setText("快三");
        ksDesc = (TextView) ksLayout.findViewById(R.id.desc);
        ksDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color
                .app_main_support));
        
        View lowLayout = findViewById(R.id.low);
        ImageView lowImage = (ImageView) lowLayout.findViewById(R.id.low)
                .findViewById(R.id.icon);
        lowImage.setImageResource(R.drawable.ico_group_fctc);
        TextView lowName = (TextView) lowLayout.findViewById(R.id.name);
        lowName.setText("低频彩");
        lowDesc = (TextView) lowLayout.findViewById(R.id.desc);
        lowDesc.setTextColor(ContextCompat.getColor(getActivity(), R.color
                .app_main_support));
        
        /*scrollView.setFocusable(false);
        scrollView.smoothScrollTo(0, 20);*/
    }
    
    private void loadBanner()
    {
        BannerListCommand command = new BannerListCommand();
        command.setPageName("index");
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Notice>>>()
        {};
        RestRequest restRequest = RestRequestManager.createRequest
                (getActivity(), command, typeToken, restCallback,
                        BANNER_LIST_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList)
        {
            updateBanner((ArrayList<Notice>) restResponse.getData());
        }
        restRequest.execute();
    }
    
    private void updateBanner(ArrayList<Notice> notices)
    {
        this.notices = notices;
        if (notices == null || notices.size() == 0)
        {
            return;
        }
        initialize();
    }
    
    @SuppressLint("NewApi")
    private void initialize()
    {
        if (notices == null || notices.size() == 0)
        {
            return;
        }
        UserCentre userCentre = GoldenAsiaApp.getUserCentre();
        List<View> views = new ArrayList<>();
        // 将最后一个view添加进来
        views.add(ViewFactory.getImageView(getActivity(), userCentre.getUrl
                (notices.get(notices.size() - 1).getContent()), /*notices.get
                (notices.size() - 1).getTitle()*/""));
        for (int i = 0, size = notices.size(); i < size; i++)
        {
            views.add(ViewFactory.getImageView(getActivity(), userCentre
                    .getUrl(notices.get(i).getContent()), /*notices
                    .get(i).getTitle()*/""));
        }
        // 将第一个view添加进来
        views.add(ViewFactory.getImageView(getActivity(), userCentre.getUrl
                (notices.get(0).getContent()), /*notices.get
                (0).getTitle()*/""));
        
        cycleViewPager.setCycle(true);
        cycleViewPager.setData(views, mAdCycleViewListener);
        cycleViewPager.setWheel(true);
    }
    
    /**
     * 彩种大厅展示
     */
    private void initGridView()
    {
        //实现GridView
        sscAdapter = new ImageAdapter();
        sscAdapter.setExpandable(true);
        syxwAdapter = new ImageAdapter();
        syxwAdapter.setExpandable(true);
        ksAdapter = new ImageAdapter();
        ksAdapter.setExpandable(true);
        lowAdapter = new ImageAdapter();
        lowAdapter.setExpandable(true);
        othersAdapter = new ImageAdapter();
        othersAdapter.setExpandable(false);
        lotteryListLoad();
    }
    
    @OnClick(R.id.edit_favourite)
    public void onEdit()
    {
        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
        builder.setContentView(popupFavourite());
        builder.setTitle("所有彩种");
        builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                editFavourite();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.dismiss();
            }
        });
        CustomDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    
    private View popupFavourite()
    {
        View convertView = LayoutInflater.from(getActivity()).inflate(R
                .layout.popup_favourite, null);
        GridView gridView = (GridView) convertView.findViewById(R.id
                .favourite_grid_view);
        FavouriteAdapter adapter = new FavouriteAdapter();
        adapter.setData(lotteryList);
        gridView.setAdapter(adapter);
        
        return convertView;
    }
    
    private void editFavourite()
    {
        if (favouriteLotteryList != null && favouriteLotteryList.size() > 0)
        {
            
        }
    }
    
    private void initFavourite()
    {
        /*if (SharedPreferencesUtils.getBoolean(getActivity(),
        ConstantInformation.FAVOURITE_INFO, "IS_FIRST"))
        {
            int size = ConstantInformation.CURRENT_LOTTERY_ID_LIST.size();
        }*/
    }
    
    @OnClick({R.id.ssc, R.id.syxw, R.id.ks, R.id.low})
    public void onExpand(View view)
    {
        switch (view.getId())
        {
            case R.id.ssc:
                if (sscGridview.getVisibility() == View.VISIBLE)
                {
                    sscGridview.setVisibility(View.GONE);
                    //boxBottomLayout.setVisibility(View.GONE);
                } else
                {
                    sscGridview.setVisibility(View.VISIBLE);
                    //boxBottomLayout.setVisibility(View.VISIBLE);
                    syxwGridview.setVisibility(View.GONE);
                    ksGridview.setVisibility(View.GONE);
                    lowGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.syxw:
                if (syxwGridview.getVisibility() == View.VISIBLE)
                {
                    syxwGridview.setVisibility(View.GONE);
                    //boxBottomLayout.setVisibility(View.GONE);
                } else
                {
                    syxwGridview.setVisibility(View.VISIBLE);
                    //boxBottomLayout.setVisibility(View.VISIBLE);
                    sscGridview.setVisibility(View.GONE);
                    ksGridview.setVisibility(View.GONE);
                    lowGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.ks:
                if (ksGridview.getVisibility() == View.VISIBLE)
                {
                    ksGridview.setVisibility(View.GONE);
                    //boxBottomLayout.setVisibility(View.GONE);
                } else
                {
                    ksGridview.setVisibility(View.VISIBLE);
                    //boxBottomLayout.setVisibility(View.VISIBLE);
                    sscGridview.setVisibility(View.GONE);
                    syxwGridview.setVisibility(View.GONE);
                    lowGridview.setVisibility(View.GONE);
                }
                break;
            case R.id.low:
                if (lowGridview.getVisibility() == View.VISIBLE)
                {
                    lowGridview.setVisibility(View.GONE);
                    //boxBottomLayout.setVisibility(View.GONE);
                } else
                {
                    lowGridview.setVisibility(View.VISIBLE);
                    //boxBottomLayout.setVisibility(View.VISIBLE);
                    sscGridview.setVisibility(View.GONE);
                    syxwGridview.setVisibility(View.GONE);
                    ksGridview.setVisibility(View.GONE);
                }
                break;
        }
    }
    
    @OnItemClick(R.id.ssc_gridview)
    public void onSscClick(int position)
    {
        launchLottery(sscLotteryList, position);
    }
    
    @OnItemClick(R.id.syxw_gridview)
    public void onSyxwClick(int position)
    {
        launchLottery(syxwLotteryList, position);
    }
    
    @OnItemClick(R.id.ks_gridview)
    public void onKsClick(int position)
    {
        launchLottery(ksLotteryList, position);
    }
    
    @OnItemClick(R.id.low_gridview)
    public void onLowClick(int position)
    {
        launchLottery(lowLotteryList, position);
    }
    
    @OnItemClick(R.id.others_gridview)
    public void onItemClick(int position)
    {
        launchLottery(othersLotteryList, position);
    }
    
    private void launchLottery(ArrayList<Lottery> list, int position)
    {
        if (list.size() > 0)
        {
            Lottery lottery = list.get(position);
            if (lottery.isAvailable())
                switch (lottery.getLotteryId())
                {
                    case 15://亚洲妙妙彩
                        GameTableFragment.launchMmc(LotteryFragment.this,
                                lottery);
                        break;
                    case 17://香港六合彩
                    case 26://六合分分彩
                        GameTableFragment.launchLhc(LotteryFragment.this,
                                lottery);
                        break;
                    case 28://刮刮彩
                        GameGgcFragment.launch(LotteryFragment.this, lottery);
                        break;
                    default:
                        GameTableFragment.launch(LotteryFragment.this, lottery);
                        break;
                }
            else
            {
                tipDialog(lottery.getStopReason());
            }
        } else
        {
            tipDialog("数据正在加载请稍等");
        }
    }
    
    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new
            CycleViewPager.ImageCycleViewListener()
    {
        @Override
        public void onImageClick(int position, View imageView)
        {
            if (TextUtils.isEmpty(notices.get(position).getLink()))
            {
            } else
            {
                Bundle bundle = new Bundle();
                bundle.putString("url", notices.get(position).getLink());
                launchFragment(WebViewFragment.class, bundle);
            }
        }
    };
    
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (cycleViewPager != null)
        {
            cycleViewPager.onDestroyView();
        }
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    
    //为GridView定义自己的适配器
    public class ImageAdapter extends BaseAdapter
    {
        private ArrayList<Lottery> data;
        private boolean isExpandable;
        
        private void setData(ArrayList<Lottery> data)
        {
            this.data = data;
            notifyDataSetChanged();
        }
        
        public boolean isExpandable()
        {
            return isExpandable;
        }
        
        public void setExpandable(boolean expandable)
        {
            isExpandable = expandable;
        }
        
        private class GirdHolder
        {
            ImageView logo;
            TextView name;
            ImageView hot_lab;
            ImageView new_lab;
        }
        
        public int getCount()
        {
            return data.size();
        }
        
        public Object getItem(int position)
        {
            return null;
        }
        
        public long getItemId(int position)
        {
            return position;
        }
        
        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent)
        {
            GirdHolder holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_fragment_home_gridview, null);
                if (isExpandable)
                    convertView.setBackgroundResource(R.drawable
                            .background_dark_border);
                holder = new GirdHolder();
                holder.logo = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.hot_lab = (ImageView) convertView.findViewById(R.id.hot_lab);
                holder.new_lab = (ImageView) convertView.findViewById(R.id.new_lab);
                convertView.setTag(holder);
            } else
            {
                holder = (GirdHolder) convertView.getTag();
            }
            
            if (data != null && data.size() > 0)
            {
                Lottery lottery = data.get(position);
                holder.logo.setImageResource(ConstantInformation
                        .getLotteryLogo(lottery.getLotteryId(), lottery
                                .isAvailable()));
                holder.name.setText(lottery.getCname());
                if (lottery.isHot())
                {
                    holder.hot_lab.setVisibility(View.VISIBLE);
                    holder.new_lab.setVisibility(View.GONE);
                } else if (lottery.isNew())
                {
                    holder.hot_lab.setVisibility(View.GONE);
                    holder.new_lab.setVisibility(View.VISIBLE);
                } else
                {
                    holder.hot_lab.setVisibility(View.GONE);
                    holder.new_lab.setVisibility(View.GONE);
                }
                convertView.setId(lottery.getLotteryId());
            } else
            {
                holder.logo.setImageResource(R.drawable.jia);
                holder.name.setText("");
            }
            
            return convertView;
        }
    }
    
    private void lotteryListLoad()
    {
        LotteryMenuCommand lotteryListCommand = new LotteryMenuCommand();
        lotteryListCommand.setLotteryID(0);
        TypeToken typeToken = new TypeToken<RestResponse<LotteryMenu>>()
        {};
        RestRequest restRequest = RestRequestManager.createRequest
                (getActivity(), lotteryListCommand, typeToken, restCallback,
                        LOTTERY_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof
                LotteryMenu)
        {
            LotteryMenu lotteryMenu = (LotteryMenu) restResponse.getData();
            if (lotteryMenu.getSsc() != null)
                sscLotteryList = lotteryMenu.getSsc();
            if (lotteryMenu.getSyxw() != null)
                syxwLotteryList = lotteryMenu.getSyxw();
            if (lotteryMenu.getKs() != null)
                ksLotteryList = lotteryMenu.getKs();
            if (lotteryMenu.getLow() != null)
                lowLotteryList = lotteryMenu.getLow();
            if (lotteryMenu.getOthers() != null)
                othersLotteryList = lotteryMenu.getOthers();
            lotteryList.addAll(sscLotteryList);
            lotteryList.addAll(syxwLotteryList);
            lotteryList.addAll(ksLotteryList);
            lotteryList.addAll(lowLotteryList);
            lotteryList.addAll(othersLotteryList);
            GoldenAsiaApp.getUserCentre().setLotteryList(lotteryList);
            //othersAdapter.notifyDataSetChanged();
            sscAdapter.setData(sscLotteryList);
            sscGridview.setAdapter(sscAdapter);
            
            syxwAdapter.setData(syxwLotteryList);
            syxwGridview.setAdapter(syxwAdapter);
            
            ksAdapter.setData(ksLotteryList);
            ksGridview.setAdapter(ksAdapter);
            
            lowAdapter.setData(lowLotteryList);
            lowGridview.setAdapter(lowAdapter);
            
            othersAdapter.setData(othersLotteryList);
            othersGridview.setAdapter(othersAdapter);
            
            sscDesc.setText("畅销组合/" + sscLotteryList.size() + "个");
            syxwDesc.setText("震撼开售/" + syxwLotteryList.size() + "个");
            ksDesc.setText("人品爆发/" + ksLotteryList.size() + "个");
        }
        restRequest.execute();
    }
    
    private RestCallback restCallback = new RestCallback<ArrayList<?>>()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse
                response)
        {
            switch (request.getId())
            {
                case LOTTERY_TRACE_ID:
                    LotteryMenu lotteryMenu = (LotteryMenu) response.getData();
                    if (lotteryMenu.getSsc() != null)
                        sscLotteryList = lotteryMenu.getSsc();
                    if (lotteryMenu.getSyxw() != null)
                        syxwLotteryList = lotteryMenu.getSyxw();
                    if (lotteryMenu.getKs() != null)
                        ksLotteryList = lotteryMenu.getKs();
                    if (lotteryMenu.getLow() != null)
                        lowLotteryList = lotteryMenu.getLow();
                    if (lotteryMenu.getOthers() != null)
                        othersLotteryList = lotteryMenu.getOthers();
                    lotteryList.addAll(sscLotteryList);
                    lotteryList.addAll(syxwLotteryList);
                    lotteryList.addAll(ksLotteryList);
                    lotteryList.addAll(lowLotteryList);
                    lotteryList.addAll(othersLotteryList);
                    GoldenAsiaApp.getUserCentre().setLotteryList(lotteryList);
                    //othersAdapter.notifyDataSetChanged();
                    sscAdapter.setData(sscLotteryList);
                    sscGridview.setAdapter(sscAdapter);
                    
                    syxwAdapter.setData(syxwLotteryList);
                    syxwGridview.setAdapter(syxwAdapter);
                    
                    ksAdapter.setData(ksLotteryList);
                    ksGridview.setAdapter(ksAdapter);
                    
                    lowAdapter.setData(lowLotteryList);
                    lowGridview.setAdapter(lowAdapter);
                    
                    othersAdapter.setData(othersLotteryList);
                    othersGridview.setAdapter(othersAdapter);
                    
                    sscDesc.setText("畅销组合/" + sscLotteryList.size() + "个");
                    syxwDesc.setText("热门组合/" + syxwLotteryList.size() + "个");
                    ksDesc.setText("震撼开售/" + ksLotteryList.size() + "个");
                    lowDesc.setText("人品爆发/" + lowLotteryList.size() + "个");
                    break;
                case BANNER_LIST_ID:
                    updateBanner((ArrayList<Notice>) response.getData());
                    break;
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String
                errDesc)
        {
            if (errCode == 7003)
            {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast
                        .LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog
                        (getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest
                .RestState int state)
        {
            
        }
    };
}
