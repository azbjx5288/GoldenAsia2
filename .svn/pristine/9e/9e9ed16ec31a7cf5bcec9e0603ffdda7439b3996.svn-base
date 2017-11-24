package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.JcRebateOptions;
import com.goldenasia.lottery.data.LhcRebateOptions;
import com.goldenasia.lottery.data.LowerMember;
import com.goldenasia.lottery.data.NormalRebateOptions;
import com.goldenasia.lottery.data.RegChildRebateCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.pattern.RebateView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by ACE-PC on 2017/2/16.
 */

public class UserRebateModify extends BaseFragment {

    private static final int RECBATE_TRACE_ID = 1;
    private static final int REG_TRACE_ID = 2;

    @Bind(R.id.userinfo_username)
    TextView userInfoUserName;
    @Bind(R.id.modifybates_layout)
    LinearLayout rebateList;

    private HashMap<String, JsonString> rangeMap = new HashMap<>();
    private ArrayList<RebateView> normalListView = new ArrayList<>();
    private ArrayList<RebateView> lhcListView = new ArrayList<>();
    private ArrayList<RebateView> jcListView = new ArrayList<>();
    private Map<String,Double> optNormalRebate=new HashMap<>();
    private Map<String,Double> lhcNormalRebate=new HashMap<>();
    private Map<String,Double> jcNormalRebate=new HashMap<>();

    private LowerMember userInfo;

    public static void launch(BaseFragment fragment, LowerMember lowerMember) {
        Bundle bundle = new Bundle();
        bundle.putString("lower", GsonHelper.toJson(lowerMember));
        FragmentLauncher.launch(fragment.getActivity(), UserRebateModify.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "修改返点", R.layout.userrebate_modify);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo = GsonHelper.fromJson(getArguments().getString("lower"),LowerMember.class);
        userInfoUserName.setText("用户名:"+userInfo.getUsername());
        rebateLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clear();
    }

    private void clear(){
        optNormalRebate.clear();
        lhcNormalRebate.clear();
        jcNormalRebate.clear();
    }

    private View[] createNormallayout(List<NormalRebateOptions> normalRebateList, Context context) {
        View[] views = new View[normalRebateList.size()];
        for (int i = 0; i < normalRebateList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.lottery_rebate_list_item, null, false);
            normalListView.add(new RebateView(view));
            views[i] = view;
        }
        return views;
    }

    private View[] createLHClayout(List<LhcRebateOptions> lhcRebateList, Context context) {
        View[] views = new View[lhcRebateList.size()];
        for (int i = 0; i < lhcRebateList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.lottery_rebate_list_item, null, false);
            lhcListView.add(new RebateView(view));
            views[i] = view;
        }
        return views;
    }

    private View[] createJClayout(List<JcRebateOptions> jcRebateList, Context context) {
        View[] views = new View[jcRebateList.size()];
        for (int i = 0; i < jcRebateList.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.lottery_rebate_list_item, null, false);
            jcListView.add(new RebateView(view));
            views[i] = view;
        }
        return views;
    }

    private void rebateListView(HashMap<String, JsonString> rangeMap){
        normalListView.clear();
        lhcListView.clear();
        jcListView.clear();
        rebateList.removeAllViews();
        Iterator<Map.Entry<String, JsonString>> it = rangeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, JsonString> entry = it.next();
            if (entry.getKey().equals("normal_rebate_options")) {
                if(!entry.getValue().getJson().isEmpty()){
                    List<NormalRebateOptions> normalRebateList = GsonHelper.parseJsonArrayWithGson(entry.getValue().getJson(), NormalRebateOptions.class);
                    if (normalRebateList.size() > 0) {
                        LinearLayout normalRebateLinearLayout=new LinearLayout(getContext());
                        normalRebateLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        normalRebateLinearLayout.setOrientation(LinearLayout.VERTICAL);

                        LinearLayout normalRebateLinearLayout2=new LinearLayout(getContext());
                        normalRebateLinearLayout2.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                        View[] views=createNormallayout(normalRebateList,getContext());
                        for (int i=0;i<views.length;i++){
                            View view=views[i];
                            if(i==0){
                                normalRebateLinearLayout.addView(view);
                            }else{
                                normalRebateLinearLayout2.addView(view);
                                if(i%2==0){
                                    View viewNormal=new View(getContext());
                                    viewNormal.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1));
                                    normalRebateLinearLayout2.addView(viewNormal);
                                }
                            }
                        }
                        rebateList.addView(normalRebateLinearLayout);
                        rebateList.addView(normalRebateLinearLayout2);


                        for(int i=0,size=normalRebateList.size();i<size;i++){
                            NormalRebateOptions normalRebate=normalRebateList.get(i);
                            RebateView rebateView=normalListView.get(i);
                            if(i==0){
                                rebateView.setRebatesViewGroup(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                            }else{
                                rebateView.setRebatesViewGroup(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1));
                            }
                            rebateView.setNormalRebate(normalRebate,entry.getKey());
                            rebateView.setOnItemSelectedListener((String selected,String id, String key) ->{
                                double optRebate=Double.parseDouble(selected.substring(0,selected.length()-1));
                                optNormalRebate.put(id,(optRebate/100));
                            });
                        }
                    }
                }
            } else if (entry.getKey().equals("lhc_rebate_options")) {
                if(!entry.getValue().getJson().isEmpty()){
                    List<LhcRebateOptions> lhcRebateList = GsonHelper.parseJsonArrayWithGson(entry.getValue().getJson(), LhcRebateOptions.class);
                    if (lhcRebateList.size() > 0) {
                        LinearLayout lhcRebateLinearLayout=new LinearLayout(getContext());
                        lhcRebateLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        lhcRebateLinearLayout.setOrientation(LinearLayout.VERTICAL);
                        TextView lhcText=new TextView(getContext());
                        lhcText.setText("六合彩");
                        lhcRebateLinearLayout.addView(lhcText);

                        View[] views=createLHClayout(lhcRebateList,getContext());
                        for (View view : views){
                            lhcRebateLinearLayout.addView(view);
                        }

                        rebateList.addView(lhcRebateLinearLayout);

                        for(int i=0,size=lhcRebateList.size();i<size;i++){
                            LhcRebateOptions lhcRebate=lhcRebateList.get(i);
                            lhcListView.get(i).setLHCRebate(lhcRebate,entry.getKey());
                            lhcListView.get(i).setOnItemSelectedListener((String selected,String id, String key) ->{
                                double optRebate=Double.parseDouble(selected.substring(0,selected.length()-1));
                                lhcNormalRebate.put(id,(optRebate/100));
                            });
                        }
                    }
                }
            } else if(entry.getKey().equals("jc_rebate_options")){
                if(!entry.getValue().getJson().isEmpty()){
                    List<JcRebateOptions> jcRebateList = GsonHelper.parseJsonArrayWithGson(entry.getValue().getJson(), JcRebateOptions.class);
                    if (jcRebateList.size() > 0) {
                        LinearLayout jcRebateLinearLayout=new LinearLayout(getContext());
                        jcRebateLinearLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
                        jcRebateLinearLayout.setOrientation(LinearLayout.VERTICAL);
                        TextView jcText=new TextView(getContext());
                        jcText.setText("竞彩");
                        jcRebateLinearLayout.addView(jcText);

                        View[] views=createJClayout(jcRebateList,getContext());
                        for (View view : views){
                            jcRebateLinearLayout.addView(view);
                        }
                        rebateList.addView(jcRebateLinearLayout);

                        for(int i=0,size=jcRebateList.size();i<size;i++){
                            JcRebateOptions jcRebate=jcRebateList.get(i);
                            jcListView.get(i).setJCRebate(jcRebate,entry.getKey());
                            jcListView.get(i).setOnItemSelectedListener((String selected,String id, String key) ->{
                                double optRebate=Double.parseDouble(selected.substring(0,selected.length()-1));
                                jcNormalRebate.put(id,(optRebate/100));
                            });
                        }
                    }
                }
            }
        }
    }

    private void rebateLoad() {
        RegChildRebateCommand command=new RegChildRebateCommand();
        command.setOp("getRebateData");
        TypeToken typeToken = new TypeToken<RestResponse<HashMap<String, JsonString>>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, RECBATE_TRACE_ID, this);
        RestResponse restResponse = restRequest.getCache();
        if (restResponse != null && restResponse.getData() instanceof HashMap) {
            rangeMap=(HashMap<String, JsonString>) restResponse.getData();
            rebateListView(rangeMap);
        }
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()){
                case RECBATE_TRACE_ID:
                    if(response != null && response.getData() instanceof HashMap){
                        rangeMap=(HashMap<String, JsonString>) response.getData();
                        rebateListView(rangeMap);
                    }
                    break;
                case REG_TRACE_ID:
                    clear();
                    showToast(response.getErrStr());
                    getActivity().finish();
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
