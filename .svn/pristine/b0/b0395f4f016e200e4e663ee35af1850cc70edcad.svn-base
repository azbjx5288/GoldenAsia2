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
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.fragment.GoldenLoginFragment;
import com.goldenasia.lottery.fragment.Splash1Fragment;
import com.goldenasia.lottery.fragment.Splash2Fragment;
import com.goldenasia.lottery.fragment.Splash3Fragment;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends FragmentActivity implements ViewPager.OnPageChangeListener
{
    private static final int REQUEST_CODE = 1001;
    /**
     * 在cache与BuildConfig.VERSION_CODE版本不一致时，需要重新登录
     */
    private static Boolean isSameVersion;
    
    @Bind(R.id.viewPager)
    ViewPager viewPager;
    
    private ArrayList<Fragment> fragments;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        
        init();
        verify();
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
    }
    
    @Override
    protected void onPause()
    {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    
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
