package com.goldenasia.lottery.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import com.goldenasia.lottery.component.ViewFactory;
import com.goldenasia.lottery.data.BannerListCommand;
import com.goldenasia.lottery.data.GaBean;
import com.goldenasia.lottery.data.GaListCommand;
import com.goldenasia.lottery.data.Notice;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.user.UserCentre;
import com.goldenasia.lottery.view.adapter.GaAdapter;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

/**
 * Created by Sakura on 2017/3/14.
 */

public class GaFragment extends BaseFragment implements GaAdapter.OnPlayListner {
    private static final String TAG = GaFragment.class.getSimpleName();

    private static final int GA_TRACE_ID = 1;
    private static final int BANNER_LIST_ID = 2;

    @BindView(R.id.ga_listview)
    ListView gaListview;

    private CycleViewPager cycleViewPager;
    private GaAdapter gaAdapter;
    private ArrayList<GaBean> gaList = new ArrayList<>();
    private ArrayList<Notice> notices;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_ga, container, false);
        View view = inflateView(inflater, container, "GA游戏", R.layout.fragment_ga);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager().findFragmentById(R.id
                .fragment_cycle_viewpager_content_ga);
        loadBanner();
        initListView();
    }

    private void loadBanner() {
        BannerListCommand command = new BannerListCommand();
        command.setPageName("ga");
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<Notice>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback,
                BANNER_LIST_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            updateBanner((ArrayList<Notice>) restResponse.getData());
        }
        restRequest.execute();
    }

    private void updateBanner(ArrayList<Notice> notices) {
        this.notices = notices;
        if (notices == null || notices.size() == 0) {
            return;
        }
        initialize();
    }

    @SuppressLint("NewApi")
    private void initialize() {
        if (notices == null || notices.size() == 0) {
            return;
        }
        UserCentre userCentre = GoldenAsiaApp.getUserCentre();
        List<View> views = new ArrayList<>();
        // 将最后一个view添加进来
        views.add(ViewFactory.getImageView(getActivity(), userCentre.getUrl(notices.get(notices.size() - 1)
                .getContent()), /*notices.get(notices.size() - 1).getTitle()*/""));
        for (int i = 0, size = notices.size(); i < size; i++) {
            views.add(ViewFactory.getImageView(getActivity(), userCentre.getUrl(notices.get(i).getContent()), /*notices
                    .get(i).getTitle()*/""));
        }
        // 将第一个view添加进来
        views.add(ViewFactory.getImageView(getActivity(), userCentre.getUrl(notices.get(0).getContent()), /*notices.get
                (0).getTitle()*/""));

        cycleViewPager.setCycle(true);
        cycleViewPager.setData(views, mAdCycleViewListener);
        cycleViewPager.setWheel(true);
    }

    /**
     * 彩种大厅展示
     */
    private void initListView() {
        gaAdapter = new GaAdapter();
        gaAdapter.setOnPlayListner(this);
        gaListLoad();
    }

    @OnItemClick(R.id.ga_listview)
    public void onItemClick(int position) {
        /*GaBean gaBean = gaList.get(position);
        Log.i(TAG,"onItemClick gaBean.getLotteryId()="+"lotteryid_"+gaBean.getLotteryId());
        //友盟数据埋点start
        HashMap<String,String> m =new HashMap<String,String>();
        m.put("__ct__", String.valueOf("1"));
        MobclickAgent.onEventValue(getActivity(), "lotteryid_"+gaBean.getLotteryId(), null,  +1);
        //友盟数据埋点end
        GAGameFragment.launch(GaFragment.this, gaBean);*/
    }

    @Override
    public void onPlay(int position) {
        GaBean gaBean = gaList.get(position);
        Log.i(TAG, "onItemClick gaBean.getLotteryId()=" + "lotteryid_" + gaBean.getLotteryId());
        //友盟数据埋点start
        HashMap<String, String> m = new HashMap<String, String>();
        m.put("__ct__", String.valueOf("1"));
        MobclickAgent.onEventValue(getActivity(), "lotteryid_" + gaBean.getLotteryId(), null, +1);
        //友盟数据埋点end
        GAGameFragment.launch(GaFragment.this, gaBean.getUrl());
    }

    @Override
    public void onTrial(int position) {
        GaBean gaBean = gaList.get(position);
        GAGameFragment.launch(GaFragment.this, gaBean.getUrl_try());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    private void gaListLoad() {
        GaListCommand gaListCommand = new GaListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<GaBean>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), gaListCommand, typeToken,
                restCallback, GA_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof ArrayList) {
            gaList = (ArrayList<GaBean>) restResponse.getData();
            if (gaList != null && gaList.size() > 0) {
                gaAdapter.setData(gaList);
                gaListview.setAdapter(gaAdapter);
            }
        }
        restRequest.execute();
    }

    private CycleViewPager.ImageCycleViewListener mAdCycleViewListener = new CycleViewPager.ImageCycleViewListener() {
        @Override
        public void onImageClick(int position, View imageView) {
            if (TextUtils.isEmpty(notices.get(position).getLink())) {
            } else {
                /*String encode = Base64.encodeToString(GoldenAsiaApp.getUserCentre().getUserID().getBytes(),
                        Base64.DEFAULT);*/
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(notices.get(position).getLink() +
                        "&frm=4&encry=" + encode));
                startActivity(browserIntent);*/
                //Intent intent = new Intent(getActivity(), WebViewFragment.class);
                Bundle bundle = new Bundle();
                //bundle.putString("url", notices.get(position).getLink() + "&frm=4&encry=" + encode);
                bundle.putString("url", notices.get(position).getLink());
                launchFragment(WebViewSbFragment.class, bundle);
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (cycleViewPager != null) {
            cycleViewPager.onDestroyView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private RestCallback restCallback = new RestCallback<ArrayList<?>>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case GA_TRACE_ID:
                    gaList = (ArrayList<GaBean>) response.getData();
                    if (gaList != null && gaList.size() > 0) {
                        gaAdapter.setData(gaList);
                        gaListview.setAdapter(gaAdapter);
                    }
                    //GoldenAsiaApp.getUserCentre().setLotteryList(gaList);
                    break;
                case BANNER_LIST_ID:
                    updateBanner((ArrayList<Notice>) response.getData());
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
