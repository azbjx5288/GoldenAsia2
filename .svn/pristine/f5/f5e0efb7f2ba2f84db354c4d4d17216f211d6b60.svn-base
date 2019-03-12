package com.goldenasia.lottery.pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.Html;
import android.widget.Toast;

import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.data.Version;
import com.goldenasia.lottery.data.VersionCommand;
import com.goldenasia.lottery.service.UpdateService;

import util.UpdateAppUtils;

/**
 * 负责版本检查，弹窗提醒，强制更新
 * Created by Alashi on 2016/3/1.
 */
public class VersionChecker implements RestCallback<Version> {
    private static final String SHARED_KEY = "last-time-check-version";
    /**
     * 最小提醒间隔
     */
    private static final long SPACE_MIN_TIME = 1000L * 60 * 30;//30分钟

    private Activity activity;
    //用户主动触发版本检查时，需要提醒进度
    private boolean isUserAction;
    private Version version;
    private AlertDialog dialog;

    public VersionChecker(Activity activity) {
        this.activity = activity;
    }

    public void startCheck() {
        startCheck(false);
    }

    public void startCheck(boolean isUserAction) {
        this.isUserAction = isUserAction;
        RestRequestManager.executeCommand(activity, new VersionCommand(), this, 0, activity);
    }

    private void handleUpgrade() {
        /*Uri uri = Uri.parse("http://" + version.getSiteMainDomain() + "/download/" + version.getFileName());
        Log.e(TAG, "handleUpgrade: " + uri.toString());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        fragment.getActivity().startActivity(intent);*/

        if (version.isForce()) {
            //延时调用Activity.finish()，否则上面startActivity弹出的选择对话框后出现的很慢
            //new Handler().postDelayed(() -> fragment.getActivity().finish(), 500);
            dialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(activity).setMessage
                    ("下载完成后将自动提示安装\n（如果下载失败，请重启应用或登录官网扫码下载）");
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            Intent intent = new Intent(activity, UpdateService.class);
            intent.putExtra("updateUrl", version.getFile());
            activity.startService(intent);
        }
    }

    private void showDialog() {
        if (activity.isFinishing()) {
            return;
        }
        /*AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getActivity()).setTitle("新版本").setMessage(Html
                .fromHtml(version.getUpdateDescribe())).setPositiveButton("马上升级", (dialog, which) -> handleUpgrade());

        if (!version.isForce()) {
            builder.setNegativeButton("稍后升级", (dialog, which) -> dialog.dismiss());
        }

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();*/
        CharSequence charSequence;
        String content = version.getUpdateDescribe();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(content,Html.FROM_HTML_MODE_LEGACY);
        } else {
            charSequence = Html.fromHtml(content);
        }
        UpdateAppUtils.from(activity)
            .checkBy(UpdateAppUtils.CHECK_BY_VERSION_CODE)
            .serverVersionCode(version.getVersionNumber())
            .serverVersionName(version.getVersionNumber() + "")
            .apkPath(version.getFile())
            .updateInfo(charSequence.toString())
            .isForce(version.isForce())
            .update();
    }

    /**
     * 获取版本号(内部识别号)
     */
    private static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean onRestComplete(RestRequest request, RestResponse<Version> response) {
        version = response.getData();
        if (version == null) {
            return true;
        }
        if (version.getVersionNumber() > getVersionCode(request.getContext())) {
            long lastTime = Preferences.getLong(activity, SHARED_KEY, 0);
            if (version.isForce() || Math.abs(System.currentTimeMillis() - lastTime) > SPACE_MIN_TIME) {
                showDialog();
                Preferences.saveLong(activity, SHARED_KEY, System.currentTimeMillis());
            }
        } else if (isUserAction) {
            Toast.makeText(activity, "已经是最新版本。目前版本号为" + getVersionCode(request.getContext()), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onRestError(RestRequest request, int errCode, String errDesc) {
        if (isUserAction) {
            Toast.makeText(activity, "版本检查失败，请稍后重试", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        if (isUserAction) {
            if (state == RestRequest.RUNNING) {
                Toast.makeText(activity, "正在检查版本...", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
