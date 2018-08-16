package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.JsonString;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.EditChildRebateCommand;
import com.goldenasia.lottery.data.JcRebateOptions;
import com.goldenasia.lottery.data.LhcRebateOptions;
import com.goldenasia.lottery.data.NormalRebateOptions;
import com.goldenasia.lottery.data.RegChildCommand;
import com.goldenasia.lottery.data.RegChildRebateCommand;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.Register;
import com.goldenasia.lottery.pattern.RebateView;
import com.goldenasia.lottery.util.ClipboardUtils;
import com.goldenasia.lottery.util.UiUtils;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ACE-PC on 2016/5/3.
 */
public class LowerRebateSetting extends BaseFragment
{
    private static final String TAG = LowerRebateSetting.class.getSimpleName();
    private static final int RECBATE_TRACE_ID = 1;
    private static final int REG_TRACE_ID = 2;
    private static final int REG_LINK_ID = 3;
    private static final int SHOW_ID = 4;
    private static final int EDIT_ID = 5;
    
    @BindView(R.id.rebates_layout)
    LinearLayout rebateList;
    @BindView(R.id.submitbut)
    Button submitBtn;
    Unbinder unbinder;
    
    private Register register;
    private String openType;
    private int userID;
    private HashMap<String, JsonString> rangeMap = new HashMap<>();
    private ArrayList<RebateView> normalListView = new ArrayList<>();
    private ArrayList<RebateView> lhcListView = new ArrayList<>();
    private ArrayList<RebateView> jcListView = new ArrayList<>();
    private Map<String, Double> optNormalRebate = new HashMap<>();
    private Map<String, Double> lhcNormalRebate = new HashMap<>();
    private Map<String, Double> jcNormalRebate = new HashMap<>();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflateView(inflater, container, "调整返点", R.layout.lower_rebate_setting);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        register = (Register) getArguments().getSerializable("reg");
        openType = getArguments().getString("openType");
        if ("link".equals(openType))
            submitBtn.setText("生成链接");
        else if ("edit".equals(openType))
            userID = getArguments().getInt("userID");
        rebateLoad();
    }
    
    @OnClick(R.id.submitbut)
    public void registerBtn()
    {
        StringBuilder optNormal = new StringBuilder();
        for (Map.Entry<String, Double> entry : optNormalRebate.entrySet())
        {
            optNormal.append(entry.getKey()).append(":").append(String.format("%.6f", entry.getValue())).append(",");
        }
        StringBuilder lhcNormal = new StringBuilder();
        for (Map.Entry<String, Double> entry : lhcNormalRebate.entrySet())
        {
            lhcNormal.append(entry.getKey()).append(":").append(String.format("%.6f", entry.getValue())).append(",");
        }
        StringBuilder jcNormal = new StringBuilder();
        for (Map.Entry<String, Double> entry : jcNormalRebate.entrySet())
        {
            jcNormal.append(entry.getKey()).append(":").append(String.format("%.6f", entry.getValue())).append(",");
        }
        
        if ("edit".equals(openType))
        {
            EditChildRebateCommand editChildRebateCommand = new EditChildRebateCommand();
            editChildRebateCommand.setNormal_rebate(optNormal.substring(0, optNormal.length() - 1));
            editChildRebateCommand.setLhc_rebate(lhcNormal.substring(0, lhcNormal.length() - 1));
            editChildRebateCommand.setJc_rebate(jcNormal.substring(0, jcNormal.length() - 1));
            editChildRebateCommand.setUser_id(userID);
            executeCommand(editChildRebateCommand, restCallback, EDIT_ID);
        } else
        {
            RegChildCommand regChildCommand = new RegChildCommand();
            regChildCommand.setNormal_rebate(optNormal.substring(0, optNormal.length() - 1));
            regChildCommand.setLhc_rebate(lhcNormal.substring(0, lhcNormal.length() - 1));
            regChildCommand.setJc_rebate(jcNormal.substring(0, jcNormal.length() - 1));
            if ("manual".equals(openType))
            {
                regChildCommand.setOp("Register");
                regChildCommand.setType(register.getType());
                regChildCommand.setUsername(register.getUsername());
                regChildCommand.setNick_name(register.getNickname());
                regChildCommand.setPassword(register.getPassword());
                regChildCommand.setPassword2(register.getPassword2());
                executeCommand(regChildCommand, restCallback, REG_TRACE_ID);
            } else
            {
                regChildCommand.setOp("createCode");
                regChildCommand.setType(register.getType());
                regChildCommand.setChannel(register.getChannel());
                executeCommand(regChildCommand, restCallback, REG_LINK_ID);
            }
        }
        //clear();
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        clear();
        unbinder.unbind();
    }
    
    private View[] createNormallayout(List<NormalRebateOptions> normalRebateList, Context context)
    {
        View[] views = new View[normalRebateList.size()];
        for (int i = 0; i < normalRebateList.size(); i++)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.lottery_rebate_list_item, null, false);
            normalListView.add(new RebateView(view));
            views[i] = view;
        }
        
        return views;
    }
    
    private View[] createLHClayout(List<LhcRebateOptions> lhcRebateList, Context context)
    {
        View[] views = new View[lhcRebateList.size()];
        for (int i = 0; i < lhcRebateList.size(); i++)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.lottery_rebate_list_item, null, false);
            lhcListView.add(new RebateView(view));
            views[i] = view;
        }
        return views;
    }
    
    private View[] createJClayout(List<JcRebateOptions> jcRebateList, Context context)
    {
        View[] views = new View[jcRebateList.size()];
        for (int i = 0; i < jcRebateList.size(); i++)
        {
            View view = LayoutInflater.from(context).inflate(R.layout.lottery_rebate_list_item, null, false);
            jcListView.add(new RebateView(view));
            views[i] = view;
        }
        return views;
    }
    
    private void rebateGridView(HashMap<String, JsonString> rangeMap)
    {
        normalListView.clear();
        lhcListView.clear();
        jcListView.clear();
        rebateList.removeAllViews();
        Iterator<Map.Entry<String, JsonString>> it = rangeMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String, JsonString> entry = it.next();
            if (entry.getKey().equals("normal_rebate_options"))
            {
                if (!entry.getValue().getJson().isEmpty())
                {
                    int count = 0;
                    List<NormalRebateOptions> normalRebateList = GsonHelper.parseJsonArrayWithGson(entry.getValue()
                            .getJson(), NormalRebateOptions.class);
                    if (normalRebateList.size() > 0)
                    {
                        View[] views = createNormallayout(normalRebateList, getContext());
                        /*for (View view : views)
                        {
                            rebateList.addView(view);
                        }*/
                        for (int i = 0, length = views.length; i < length; i = i + 2)
                        {
                            LinearLayout linearLayout = new LinearLayout(getActivity());
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setWeightSum(2.0f);
                            views[i].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                                    .WRAP_CONTENT, 1.0f));
                            linearLayout.addView(views[i]);
                            if (i + 1 < length)
                            {
                                views[i + 1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                                        .WRAP_CONTENT, 1.0f));
                                linearLayout.addView(views[i + 1]);
                            }
                            
                            rebateList.addView(linearLayout, count);
                            count++;
                        }
                        View divideView = new View(getActivity());
                        divideView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                UiUtils.px2dip(getActivity(), 20)));
                        divideView.setBackgroundColor(Color.parseColor("#dfdfdf"));
                        rebateList.addView(divideView, count);
                        for (int i = 0, size = normalRebateList.size(); i < size; i++)
                        {
                            NormalRebateOptions normalRebate = normalRebateList.get(i);
                            normalListView.get(i).setNormalRebate(normalRebate, entry.getKey());
                            normalListView.get(i).setOnItemSelectedListener((String selected, String id, String key) ->
                            {
                                double optRebate = Double.parseDouble(selected.substring(selected.indexOf("(")+1,
                                        selected.lastIndexOf("%")
                                ));
                                optNormalRebate.put(id, (optRebate / 100));
                            });
                        }
                    }
                }
            } else if (entry.getKey().equals("lhc_rebate_options"))
            {
                if (!entry.getValue().getJson().isEmpty())
                {
                    List<LhcRebateOptions> lhcRebateList = GsonHelper.parseJsonArrayWithGson(entry.getValue().getJson
                            (), LhcRebateOptions.class);
                    if (lhcRebateList.size() > 0)
                    {
                        TextView lhcText = new TextView(getContext());
                        lhcText.setText("六合彩");
                        lhcText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                        lhcText.setPadding(20, 10, 0, 10);
                        lhcText.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        rebateList.addView(lhcText);
                        View[] views = createLHClayout(lhcRebateList, getContext());
                        /*for (View view : views)
                        {
                            rebateList.addView(view);
                        }*/
                        for (int i = 0, length = views.length; i < length; i = i + 2)
                        {
                            LinearLayout linearLayout = new LinearLayout(getActivity());
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setWeightSum(2.0f);
                            views[i].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                                    .WRAP_CONTENT, 1.0f));
                            linearLayout.addView(views[i]);
                            if (i + 1 < length)
                            {
                                views[i + 1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                                        .WRAP_CONTENT, 1.0f));
                                linearLayout.addView(views[i + 1]);
                            }
                            rebateList.addView(linearLayout);
                        }
                        View divideView = new View(getActivity());
                        divideView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                UiUtils.px2dip(getActivity(), 20)));
                        divideView.setBackgroundColor(Color.parseColor("#dfdfdf"));
                        rebateList.addView(divideView);
                        for (int i = 0, size = lhcRebateList.size(); i < size; i++)
                        {
                            LhcRebateOptions lhcRebate = lhcRebateList.get(i);
                            lhcListView.get(i).setLHCRebate(lhcRebate, entry.getKey());
                            lhcListView.get(i).setOnItemSelectedListener((String selected, String id, String key) ->
                            {
                                double optRebate = Double.parseDouble(selected.substring( selected.indexOf("(")+1,
                                        selected.lastIndexOf("%")
                                ));
                                lhcNormalRebate.put(id, (optRebate / 100));
                            });
                        }
                    }
                }
            } else if (entry.getKey().equals("jc_rebate_options"))
            {
                if (!entry.getValue().getJson().isEmpty())
                {
                    List<JcRebateOptions> jcRebateList = GsonHelper.parseJsonArrayWithGson(entry.getValue().getJson()
                            , JcRebateOptions.class);
                    if (jcRebateList.size() > 0)
                    {
                        TextView jcText = new TextView(getContext());
                        jcText.setText("竞彩");
                        jcText.setPadding(20, 10, 0, 10);
                        jcText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
                        jcText.setBackgroundColor(Color.parseColor("#f5f5f5"));
                        rebateList.addView(jcText);
                        View[] views = createJClayout(jcRebateList, getContext());
                        /*for (View view : views)
                        {
                            rebateList.addView(view);
                        }*/
                        for (int i = 0, length = views.length; i < length; i = i + 2)
                        {
                            LinearLayout linearLayout = new LinearLayout(getActivity());
                            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                            linearLayout.setWeightSum(2.0f);
                            views[i].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                                    .WRAP_CONTENT, 1.0f));
                            linearLayout.addView(views[i]);
                            if (i + 1 < length)
                            {
                                views[i + 1].setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams
                                        .WRAP_CONTENT, 1.0f));
                                linearLayout.addView(views[i + 1]);
                            }
                            rebateList.addView(linearLayout);
                        }
                        View divideView = new View(getActivity());
                        divideView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                UiUtils.px2dip(getActivity(), 20)));
                        divideView.setBackgroundColor(Color.parseColor("#dfdfdf"));
                        rebateList.addView(divideView);
                        for (int i = 0, size = jcRebateList.size(); i < size; i++)
                        {
                            JcRebateOptions jcRebate = jcRebateList.get(i);
                            jcListView.get(i).setJCRebate(jcRebate, entry.getKey());
                            jcListView.get(i).setOnItemSelectedListener((String selected, String id, String key) ->
                            {
                                double optRebate = Double.parseDouble(selected.substring(selected.indexOf("(")+1,
                                        selected.lastIndexOf("%")
                                ));
                                jcNormalRebate.put(id, (optRebate / 100));
                            });
                        }
                    }
                }
            }
        }
    }
    
    private void clear()
    {
        optNormalRebate.clear();
        lhcNormalRebate.clear();
        jcNormalRebate.clear();
    }
    
    private void rebateLoad()
    {
        if ("edit".equals(openType))
        {
            EditChildRebateCommand editChildRebateCommand = new EditChildRebateCommand();
            editChildRebateCommand.setOp("getChildRebateData");
            editChildRebateCommand.setUser_id(userID);
            TypeToken typeToken = new TypeToken<RestResponse<HashMap<String, JsonString>>>()
            {};
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), editChildRebateCommand,
                    typeToken, restCallback, RECBATE_TRACE_ID, this);
            /*RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof HashMap)
            {
                rangeMap = (HashMap<String, JsonString>) restResponse.getData();
                rebateGridView(rangeMap);
            } else*/
            restRequest.execute();
        } else
        {
            RegChildRebateCommand regChildRebateCommand = new RegChildRebateCommand();
            regChildRebateCommand.setOp("getRebateData");
            TypeToken typeToken = new TypeToken<RestResponse<HashMap<String, JsonString>>>()
            {};
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), regChildRebateCommand,
                    typeToken, restCallback, RECBATE_TRACE_ID, this);
            RestResponse restResponse = restRequest.getCache();
            if (restResponse != null && restResponse.getData() instanceof HashMap)
            {
                rangeMap = (HashMap<String, JsonString>) restResponse.getData();
                rebateGridView(rangeMap);
            } else
                restRequest.execute();
        }
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            switch (request.getId())
            {
                case RECBATE_TRACE_ID:
                    if (response != null && response.getData() instanceof HashMap)
                    {
                        rangeMap = (HashMap<String, JsonString>) response.getData();
                        rebateGridView(rangeMap);
                    }
                    break;
                case SHOW_ID:
                    if (response != null && response.getData() instanceof HashMap)
                    {
                        rangeMap = (HashMap<String, JsonString>) response.getData();
                        rebateGridView(rangeMap);
                    }
                    break;
                case REG_TRACE_ID:
                case EDIT_ID:
                    clear();
                    //showToast(response.getErrStr());
                    getActivity().setResult(ConstantInformation.REFRESH_RESULT);
                    getActivity().finish();
                    break;
                case REG_LINK_ID:
                    String url = response.getData().toString();
                    if (!TextUtils.isEmpty(url))
                    {
                        CustomDialog.Builder builder = new CustomDialog.Builder(getActivity());
                        builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
                        builder.setMessage(url);
                        builder.setNegativeButton("返回", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.setPositiveButton("复制", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                ClipboardUtils.copy(getActivity(), url, "开户链接");
                                /*ClipboardManager clipboardManager = (ClipboardManager) getActivity().getSystemService
                                        (Context.CLIPBOARD_SERVICE);
                                ClipData clipData = ClipData.newPlainText("开户链接",url);
                                clipboardManager.setPrimaryClip(clipData);
                                showToast("已复制到剪贴板");*/
                            }
                        });
                        builder.create().show();
                    }
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
        }
    };
}