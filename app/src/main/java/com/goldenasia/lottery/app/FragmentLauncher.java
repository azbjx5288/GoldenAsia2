package com.goldenasia.lottery.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.service.BadgeIntentService;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

/**
 * Created by Alashi on 2015/12/18.
 */
public class FragmentLauncher extends AppCompatActivity {
    private static final String TAG = FragmentLauncher.class.getSimpleName();

    public static final String KEY_FRAGMENT_NAME = "fragment-name";

    /** 调用startActivityFromFragment的Fragment，避免源码的bug */
    private Fragment fragmentCaller;

    Fragment fragment=null;

    public static void launch(Context context, Class<? extends Fragment> fragment) {
        launch(context, fragment.getName());
    }

    public static void launch(Context context, String fragmentName) {
        Intent intent = new Intent(context, FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragmentName);
        context.startActivity(intent);
    }

    public static void launch(Context context, Class<? extends Fragment> fragment, Bundle bundle) {
        Intent intent = new Intent(context, FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragment.getName());
        if(bundle != null){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public static void launchForResult(Fragment fagment, Class<? extends Fragment> fragment, Bundle bundle, int requestCode) {
        Intent intent = new Intent(fagment.getActivity(), FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragment.getName());
        if(bundle != null){
            intent.putExtras(bundle);
        }
        fagment.startActivityForResult(intent, requestCode);
    }

    public static void launchForResult(Activity activity, String fragmentName, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, FragmentLauncher.class);
        intent.putExtra(KEY_FRAGMENT_NAME, fragmentName);
        if(bundle != null){
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        updateBadgeIntentService();
    }

    private  void updateBadgeIntentService(){
        startService(new Intent(this, BadgeIntentService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        updateBadgeIntentService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fragmentName = getIntent().getStringExtra(KEY_FRAGMENT_NAME);
        Preferences.saveString(this, "debug_last_launch_fragment", fragmentName);
        Log.i(TAG, "onCreate " + fragmentName);
        fragment= Fragment.instantiate(this, fragmentName);
        fragment.setArguments(getIntent().getExtras());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, fragment);
        //ft.replace(android.R.id.content, fragment);
        //ft.commit();
        ft.commitAllowingStateLoss();
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        fragmentCaller = fragment;
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (fragmentCaller != null) {
            fragmentCaller.onActivityResult(requestCode, resultCode, data);
            fragmentCaller = null;
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

   public Fragment getfragmentCaller(){
        return fragment;
    }

    private FragmentKeyListener keyListener;

    public void setKeyListener(FragmentKeyListener keyListener) {
        this.keyListener = keyListener;
    }
    //键监听实现
    interface FragmentKeyListener {
        boolean onKeyForward();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
        //all按键被点击了 就自己拦截实现掉
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyListener != null) {
                return keyListener.onKeyForward();
            }
        }
        return super.onKeyDown(keyCode, keyEvent);
    }
}
