package com.goldenasia.lottery.app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.data.ReceiveBoxResponse;
import com.goldenasia.lottery.data.ReceiveBoxUnReadCommand;
import com.goldenasia.lottery.fragment.FragmentHistory;
import com.goldenasia.lottery.fragment.FragmentHome;
import com.goldenasia.lottery.fragment.FragmentUser;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.service.BadgeIntentService;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

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
    private TextView  btn_meRadioButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_container);
        init();
        initView();
        selectPage(0); // 默认选中首页
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();
        //添加桌面角标(BadgeNumber)
        if(ConstantInformation.MESSAGE_COUNT==-1){
            loadReceiveBox();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
        MobclickAgent.onResume(this);
        initMessageCount();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
        MobclickAgent.onPause(this);
        updateBadgeIntentService();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        updateBadgeIntentService();
}

    private void loadReceiveBox() {
        ReceiveBoxUnReadCommand command = new ReceiveBoxUnReadCommand();
        command.setIsRead(0);
        TypeToken typeToken = new TypeToken<RestResponse<ReceiveBoxResponse>>() {
        };

        RestRequestManager.executeCommand(this, command, typeToken, restCallback, 0, this);
    }

    private  void updateBadgeIntentService(){
        startService(new Intent(this, BadgeIntentService.class));
    }

    private void refreshBadge(int totalCount) {
        Object  tag=btn_meRadioButton.getTag();
        if(tag==null){
            QBadgeView qBadgeView=new QBadgeView(ContainerActivity.this);
            qBadgeView.bindTarget(btn_meRadioButton);
            qBadgeView.setBadgeGravity(Gravity.START | Gravity.TOP);
            qBadgeView.setBadgeNumber(totalCount);
            btn_meRadioButton.setTag(qBadgeView);
        }else{
            QBadgeView qQBadgeView=(QBadgeView)tag;
            qQBadgeView.setBadgeNumber(totalCount);
        }
    }

    private void initMessageCount() {
        int  count=ConstantInformation.MESSAGE_COUNT;//SharedPreferencesUtils.getInt(this, ConstantInformation.APP_INFO, ConstantInformation.MESSAGE_COUNT);
        if(count<=0){
            count=0;
        }

        refreshBadge(count);
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
        btn_meRadioButton = (TextView) findViewById(R.id.badge_view);
        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
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

    private RestCallback restCallback = new RestCallback<ReceiveBoxResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ReceiveBoxResponse> response) {

            if (request.getId() == 0) {
                ReceiveBoxResponse receiveBoxResponse = (ReceiveBoxResponse) (response.getData());
                int totalCount =receiveBoxResponse.getList().size();// Integer.parseInt(receiveBoxResponse.getCount());// 解决服务端 返回数据 有缓存的 问题
                if(totalCount==10){
                    totalCount=Integer.parseInt(receiveBoxResponse.getCount());
                }
                if(totalCount<=0){
                    totalCount=0;
                }else{
                    totalCount=Math.max(0,Math.min(totalCount,99));
                }

                ConstantInformation.MESSAGE_COUNT=totalCount;

                refreshBadge(totalCount);

            }

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

}
