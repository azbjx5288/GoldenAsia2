package com.goldenasia.lottery.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.data.ReceiveBoxResponse;
import com.goldenasia.lottery.data.ReceiveBoxUnReadCommand;
import com.goldenasia.lottery.fragment.GoldenLoginFragment;
import com.goldenasia.lottery.fragment.Splash1Fragment;
import com.goldenasia.lottery.fragment.Splash2Fragment;
import com.goldenasia.lottery.fragment.Splash3Fragment;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.leolin.shortcutbadger.ShortcutBadger;

public class SplashActivity extends FragmentActivity implements ViewPager.OnPageChangeListener
{
    private static final int REQUEST_CODE = 1001;
    /**
     * 在cache与BuildConfig.VERSION_CODE版本不一致时，需要重新登录
     */
    private static Boolean isSameVersion;
    
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    
    private ArrayList<Fragment> fragments;

    private int RECEIVE = 0;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        
        init();
        verify();
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();

        //友盟推送的 开启和关闭判断
        isStopUPush();
    }

    private void isStopUPush() {
        String WIN="win";

        if(!SharedPreferencesUtils.getBoolean(this, ConstantInformation.APP_INFO, WIN)){
            PushAgent mPushAgent = PushAgent.getInstance(this);
            //当关闭友盟+推送时
            mPushAgent.disable(new IUmengCallback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onFailure(String s, String s1) {

                }
            });
        }
    }

    private void init()
    {
        fragments = new ArrayList<>();
        fragments.add(new Splash1Fragment());
        fragments.add(new Splash2Fragment());
        fragments.add(new Splash3Fragment());
        
        viewPager.setAdapter(new TabPageAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
    }
    
    private void verify()
    {
        if (SharedPreferencesUtils.getBoolean(this, ConstantInformation.APP_INFO, "is_first_start_" +
                ConstantInformation.getVersionCode(this)))
        {
            SharedPreferencesUtils.putBoolean(this, ConstantInformation.APP_INFO, "is_first_start_" +
                    ConstantInformation.getVersionCode(this), false);
        } else
        {
            skip();
        }
    }
    
    private void skip()
    {
        if (!isInSameVersion())
        {
            GoldenAsiaApp.getUserCentre().logout();
            RestRequestManager.cancelAll();
            
            Preferences.saveInt(SplashActivity.this, ConstantInformation.APP_INFO, BuildConfig.VERSION_CODE);
            isSameVersion = true;
        }
        
        if (GoldenAsiaApp.getUserCentre().isLogin())
        {
            startActivityForResult(new Intent(SplashActivity.this, ContainerActivity.class), REQUEST_CODE);
        } else
        {
            FragmentLauncher.launchForResult(SplashActivity.this, GoldenLoginFragment.class.getName(), null,
                    REQUEST_CODE);
        }
    }
    
    @Override
    protected void onResume()
    {
        super.onResume();
        MobclickAgent.onResume(this);

        //添加桌面角标(BadgeNumber)
        loadReceiveBox();
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void loadReceiveBox() {
        ReceiveBoxUnReadCommand command = new ReceiveBoxUnReadCommand();
        command.setIsRead(0);
        TypeToken typeToken = new TypeToken<RestResponse<ReceiveBoxResponse>>() {
        };

        RestRequestManager.executeCommand(this, command, typeToken, restCallback, RECEIVE, this);
    }

    private RestCallback restCallback = new RestCallback<ReceiveBoxResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ReceiveBoxResponse> response) {

            if (request.getId() == RECEIVE) {
                ReceiveBoxResponse receiveBoxResponse = (ReceiveBoxResponse) (response.getData());
                int totalCount =receiveBoxResponse.getList().size();// Integer.parseInt(receiveBoxResponse.getCount());//解决服务端 返回数据 有缓存的 问题

                if(totalCount<=0){
                    totalCount=0;
                }else{
                    totalCount=Math.max(0,Math.min(totalCount,99));
                }

                ShortcutBadger.applyCount(SplashActivity.this, totalCount);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        finish();
    }
    
    public boolean isInSameVersion()
    {
        if (isSameVersion == null)
        {
            isSameVersion = Preferences.getInt(this, ConstantInformation.APP_INFO, 0) == BuildConfig.VERSION_CODE;
        }
        return isSameVersion;
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    }
    
    @Override
    public void onPageSelected(int position)
    {
    }
    
    @Override
    public void onPageScrollStateChanged(int state)
    {
    }
}
