package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.component.TabPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/2.
 * 包含两个转账  平台转账和下级转账
 */

public class TransferFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager
        .OnPageChangeListener{
    private static final String TAG = TransferFragment.class.getSimpleName();

    @BindView(R.id.radioGroupTransfer)
    RadioGroup radioGroup;
    @BindView(R.id.viewPagerTransfer)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(inflater, container, "资金转移", R.layout.fragment_transfer);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initView();
        selectPage(0);
    }

    private void init() {
        Fragment platformFragment = new TransferPlatformFragment();
        Fragment fundFragment = new TransferLowerMemberFragment();
        fragments.add(platformFragment);
        fragments.add(fundFragment);
    }

    private void initView() {
        radioGroup.setOnCheckedChangeListener(this);
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.addOnPageChangeListener(this);
        radioGroup.check(radioGroup.getChildAt(0).getId());
    }

    /**
     * 选择某页
     *
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.platformRadioButton:
                selectPage(0);
                break;
            case R.id.fundRadioButton:
                selectPage(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
