package com.goldenasia.lottery.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.fragment.GoldenLoginFragment;
import com.goldenasia.lottery.util.WindowUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Alashi on 2016/1/12.
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1001;
    /**
     * 在cache与BuildConfig.VERSION_CODE版本不一致时，需要重新登录
     */
    private static Boolean isSameVersion;

    @BindView(R.id.count_down)
    TextView countDown;

    private CountDownTimer countDownTimer;
    private int count = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        WindowUtils.hideSystemUI(this);

        countDown.setTextColor(ContextCompat.getColor(this, R.color.white));
        countDownTimer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                countDown.setText("跳过" + count--);
            }

            @Override
            public void onFinish() {
                skip();
            }
        };

        countDownTimer.start();

        PushAgent.getInstance(this).onAppStart();
    }

    private void skip() {
        if (!isInSameVersion()) {
            GoldenAsiaApp.getUserCentre().logout();
            RestRequestManager.cancelAll();

            Preferences.saveInt(MainActivity.this, "app-version-code", BuildConfig.VERSION_CODE);
            isSameVersion = true;
        }

        if (GoldenAsiaApp.getUserCentre().isLogin()) {
            startActivityForResult(new Intent(MainActivity.this, ContainerActivity.class), REQUEST_CODE);
        } else {
            FragmentLauncher.launchForResult(MainActivity.this, GoldenLoginFragment.class.getName(), null,
                    REQUEST_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            finish ( );
    }

    public boolean isInSameVersion() {
        if (isSameVersion == null) {
            isSameVersion = Preferences.getInt(this, "app-version-code", 0) == BuildConfig.VERSION_CODE;
        }
        return isSameVersion;
    }

    @OnClick(R.id.count_down)
    public void onClick() {
        countDownTimer.cancel();
        skip();
    }
}
