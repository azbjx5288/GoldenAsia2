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
import com.goldenasia.lottery.pattern.VersionChecker;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 首页
 */

public class FragmentHome extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    private static final String TAG = FragmentHome.class.getSimpleName();

    @BindView(R.id.radioGroupHome)
    RadioGroup radioGroup;
    @BindView(R.id.viewPagerHome)
    ViewPager viewPager;

    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "金亚洲", R.layout.fragment_home);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new VersionChecker(this).startCheck();
        init();
        initView();
        selectPage(0);
    }

    private void init() {
        /*Fragment lotteryFragment = FragmentDelayer.newInstance(1, LotteryFragment.class.getName(), null);
        Fragment gaFragment = FragmentDelayer.newInstance(2, GaFragment.class.getName(), null);*/
        Fragment lotteryFragment = new LotteryFragment();
        Fragment gaFragment = new GaMainFragment();
        Fragment agFragment = new AGFragment();
        Fragment sbFragment = new SbFragment();
        fragments.add(lotteryFragment);
        fragments.add(gaFragment);
        fragments.add(agFragment);
        fragments.add(sbFragment);
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
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.lotteryRadioButton:
                selectPage(0);
                break;
            case R.id.gaRadioButton:
                selectPage(1);
                break;
            case R.id.agRadioButton:
                selectPage(2);
                break;
            case R.id.sbRadioButton:
                selectPage(3);
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
