package com.goldenasia.lottery.app;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.fragment.FragmentHistory;
import com.goldenasia.lottery.fragment.FragmentHome;
import com.goldenasia.lottery.fragment.FragmentUser;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import q.rorbin.badgeview.QBadgeView;

/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 界面容器
 */

public class ContainerActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, ViewPager
        .OnPageChangeListener {
    private static final String TAG = ContainerActivity.class.getSimpleName();

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;

    private Drawable[] unselectedIcon;
    private Drawable[] selectedIcon;
    private List<Fragment> fragments = new ArrayList<Fragment>();

    RadioButton btnMeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        init();
        initView();
        selectPage(0); // 默认选中首页
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
//        new QBadgeView(ContainerActivity.this).bindTarget(btnMeBtn).setBadgeNumber(5);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    protected void init() {
        /*int[] ids = {R.drawable.ic_tap_home_gray, R.drawable.ic_tab_classify, R.drawable.ic_tab_discover, R.drawable
                .ic_tap_my_gray};
        unselectedIcon = new Drawable[ids.length];
        selectedIcon = new Drawable[ids.length];
        ColorStateList colorStateList = ColorStateList.valueOf(Color.parseColor("#256f67"));
        for (int i = 0; i < ids.length; i++) {
            unselectedIcon[i] = getResources().getDrawable(ids[i]);
            selectedIcon[i] = DrawableCompat.wrap(getResources().getDrawable(ids[i]).mutate());
            DrawableCompat.setTintList(selectedIcon[i], colorStateList);
            unselectedIcon[i].setBounds(0, 0, unselectedIcon[i].getMinimumWidth(), unselectedIcon[i].getMinimumHeight
                    ());
            selectedIcon[i].setBounds(0, 0, selectedIcon[i].getMinimumWidth(), selectedIcon[i].getMinimumHeight());
        }*/

        Fragment homeFragment = new FragmentHome();
        //Fragment classifyFragment = FragmentDelayer.newInstance(R.drawable.ic_tab_classify, FragmentLotteryTrend.class.getName(), null);
        Fragment discoverFragment = FragmentDelayer.newInstance(R.drawable.ic_tab_discover, FragmentHistory.class.getName(), null);
        Fragment meFragment = FragmentDelayer.newInstance(R.drawable.ic_tap_my_gray, FragmentUser.class.getName(), null);
        fragments.add(homeFragment);
        //fragments.add(classifyFragment);
        fragments.add(discoverFragment);
        fragments.add(meFragment);
    }

    private void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        btnMeBtn = (RadioButton) findViewById(R.id.btn_me);
        mRadioGroup.setOnCheckedChangeListener(this);
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(tabPageAdapter);
        mViewPager.addOnPageChangeListener(this);
        mRadioGroup.check(mRadioGroup.getChildAt(0).getId());
    }

    /**
     * 选择某页
     *
     * @param position 页面的位置
     */
    private void selectPage(int position) {
        mRadioGroup.check(mRadioGroup.getChildAt(position).getId());
        // 将所有的tab的icon变成灰色的
        /*for (int i = 0; i < mRadioGroup.getChildCount(); i++) {
            RadioButton child = (RadioButton) mRadioGroup.getChildAt(i);
            child.setCompoundDrawables(null, unselectedIcon[i], null, null);
        }*/
        // 切换页面
        mViewPager.setCurrentItem(position, true);
        // 改变图标
        /*RadioButton select = (RadioButton) mRadioGroup.getChildAt(position);
        select.setCompoundDrawables(null, selectedIcon[position], null, null);*/
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

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.btn_home: // 首页选中
                selectPage(0);
                break;
            /*case R.id.btn_classify: // 分类选中
                selectPage(1);
                break;*/
            case R.id.btn_discover: // 发现选中
                selectPage(1);
                break;
            case R.id.btn_me: // 个人中心选中
                selectPage(2);
                break;
        }
    }
}
