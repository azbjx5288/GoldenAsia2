package com.goldenasia.lottery.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
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
import com.goldenasia.lottery.service.BadgeIntentService;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengCallback;
import com.umeng.message.PushAgent;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private static final int REQUEST_CODE = 1001;
    /**
     * 在cache与BuildConfig.VERSION_CODE版本不一致时，需要重新登录
     */
    private static Boolean isSameVersion;

    private ViewPager viewPager;

    private ArrayList<Fragment> fragments;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        viewPager = findViewById(R.id.viewPager);

        installProcess();
        init();
        verify();
        //统计应用启动数据
        PushAgent.getInstance(this).onAppStart();

        //友盟推送的 开启和关闭判断
//        isStopUPush();
    }

    private void isStopUPush() {
        String WIN = "win";

        if (!SharedPreferencesUtils.getBoolean(this, ConstantInformation.APP_INFO, WIN)) {
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

    private void init() {
        fragments = new ArrayList<>();
        fragments.add(new Splash1Fragment());
        fragments.add(new Splash2Fragment());
        fragments.add(new Splash3Fragment());

        viewPager.setAdapter(new TabPageAdapter(getSupportFragmentManager(), fragments));
        viewPager.addOnPageChangeListener(this);
    }

    private void verify() {
        if (SharedPreferencesUtils.getBoolean(this, ConstantInformation.APP_INFO, "is_first_start_" +
                ConstantInformation.getVersionCode(this))) {
            SharedPreferencesUtils.putBoolean(this, ConstantInformation.APP_INFO, "is_first_start_" +
                    ConstantInformation.getVersionCode(this), false);
        } else {
            skip();
        }
    }

    private void skip() {
        if (!isInSameVersion()) {
            GoldenAsiaApp.getUserCentre().logout();
            RestRequestManager.cancelAll();

            Preferences.saveInt(SplashActivity.this, ConstantInformation.APP_INFO, BuildConfig.VERSION_CODE);
            isSameVersion = true;
        }

        if (GoldenAsiaApp.getUserCentre().isLogin()) {
            startActivityForResult(new Intent(SplashActivity.this, ContainerActivity.class), REQUEST_CODE);
        } else {
            startService(new Intent(this, BadgeIntentService.class));
            FragmentLauncher.launchForResult(SplashActivity.this, GoldenLoginFragment.class.getName(), null,REQUEST_CODE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public boolean isInSameVersion() {
        if (isSameVersion == null) {
            isSameVersion = Preferences.getInt(this, ConstantInformation.APP_INFO, 0) == BuildConfig.VERSION_CODE;
        }
        return isSameVersion;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10086 && resultCode == RESULT_OK) {
            installProcess();
        } else {
            finish();
        }
    }

    //安装应用的流程
    private void installProcess() {
        String apkPath = getExternalCacheDir().getAbsolutePath();
        File apk = new File(apkPath, "jyz.apk");
        if (!apk.exists()) {
            return;
        }
        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("安装应用需要打开未知来源权限，请去设置中开启权限");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startInstallPermissionSettingActivity();
                        }
                    }
                });
                builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
                return;
            }
        }
        //有权限，开始安装应用程序
        installApk(apk);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        Uri packageURI = Uri.parse("package:" + getPackageName());
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, packageURI);
        startActivityForResult(intent, 10086);
    }

    //安装应用
    private void installApk(File apk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apk), "application/vnd.android.package-archive");
        } else {//Android7.0之后获取uri要用contentProvider
            Uri uri = getUriFromFile(getBaseContext(), apk);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getBaseContext().startActivity(intent);
    }

    public static Uri getUriFromFile(Context context, File file) {
        Uri imageUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(context, "com.goldenasia.lottery.fileprovider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(file);
        }
        return imageUri;
    }

}
