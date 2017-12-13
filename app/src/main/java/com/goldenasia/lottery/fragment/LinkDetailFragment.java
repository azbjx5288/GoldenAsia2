package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.component.ScrollGridView;
import com.goldenasia.lottery.data.RegLinksBean;
import com.goldenasia.lottery.util.ClipboardUtils;
import com.goldenasia.lottery.view.adapter.LinkDetailAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Sakura on 2017/12/12.
 */

public class LinkDetailFragment extends BaseFragment
{
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.link)
    TextView link;
    @BindView(R.id.ssc_gridview)
    ScrollGridView sscGridview;
    @BindView(R.id.lhc_gridview)
    ScrollGridView lhcGridview;
    @BindView(R.id.jc_gridview)
    ScrollGridView jcGridview;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    Unbinder unbinder;
    
    private LinkDetailAdapter sscAdapter;
    private LinkDetailAdapter lhcAdapter;
    private LinkDetailAdapter jcAdapter;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        View view = inflateView(inflater, container, true, "链接详情", R.layout.link_detail);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initAdapter();
        initData();
    }
    
    private void initAdapter()
    {
        sscAdapter = new LinkDetailAdapter();
        sscGridview.setAdapter(sscAdapter);
        lhcAdapter = new LinkDetailAdapter();
        lhcGridview.setAdapter(lhcAdapter);
        jcAdapter = new LinkDetailAdapter();
        jcGridview.setAdapter(jcAdapter);
    }
    
    private void initData()
    {
        RegLinksBean regLinksBean = (RegLinksBean) getArguments().getSerializable("detail");
        if (regLinksBean != null)
        {
            switch (regLinksBean.getType())
            {
                case "1":
                    type.setText("账号类型：代理");
                    break;
                case "2":
                    type.setText("账号类型：用户");
                    break;
            }
            link.setText("链接地址：" + regLinksBean.getLink());
            sscAdapter.setData(regLinksBean.getNormal_rebate_options(), "normal_rebate_options");
            lhcAdapter.setData(regLinksBean.getLhc_rebate_options(), null);
            jcAdapter.setData(regLinksBean.getJc_rebate_options(), null);
        }
    }
    
    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
    
    @OnClick({R.id.check, R.id.copy})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.check:
                if (scrollView.getVisibility() == View.GONE)
                    scrollView.setVisibility(View.VISIBLE);
                else
                    scrollView.setVisibility(View.GONE);
                break;
            case R.id.copy:
                ClipboardUtils.copy(getActivity(), link.getText().toString(), "代理链接");
                break;
        }
    }
}
