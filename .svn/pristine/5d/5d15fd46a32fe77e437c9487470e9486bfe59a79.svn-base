package com.goldenasia.lottery.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.Preferences;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.data.MethodList;
import com.goldenasia.lottery.data.MethodListCommand;
import com.goldenasia.lottery.game.Game;
import com.goldenasia.lottery.game.GameConfig;
import com.goldenasia.lottery.game.LhcGame;
import com.goldenasia.lottery.game.MenuController;
import com.goldenasia.lottery.game.OnSelectedListener;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ShoppingCart;
import com.goldenasia.lottery.material.Ticket;
import com.goldenasia.lottery.view.TableMenu;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 秒秒彩选号界面
 * Created by Sakura on 2016/09/05.
 */
public class GameMmcFragment extends BaseFragment implements OnSelectedListener, TableMenu.OnClickMethodListener {
    private static final String TAG = GameMmcFragment.class.getSimpleName();

    private static final int ID_METHOD_LIST = 1;

    WebView webView;
    @Bind(android.R.id.title)
    TextView titleView;
    @Bind(R.id.pick_notice)
    TextView pickNoticeView;
    @Bind(R.id.pick_game_layout)
    LinearLayout pickGameLayout;
    @Bind(R.id.choose_done_button)
    Button chooseDoneButton;
    @Bind(R.id.lottery_choose_bottom)
    RelativeLayout chooseBottomLayout;

    private Lottery lottery;
    private Game game;
    private MenuController menuController;
    private ShoppingCart shoppingCart;

    public static void launch(BaseFragment fragment, Lottery lottery) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        FragmentLauncher.launch(fragment.getActivity(), GameMmcFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflateView(inflater, container, "投注", R.layout.game_mmc_fragment);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        initMenu();
        loadMethodFromXml();
        loadMenu();
    }

    private void applyArguments() {
        shoppingCart = ShoppingCart.getInstance();
        lottery = (Lottery) getArguments().getSerializable("lottery");
        setTitle(lottery.getCname());
    }

    private void loadWebViewIfNeed() {
        if (webView != null) {
            return;
        }
        chooseBottomLayout.postDelayed(() -> {
            synchronized (chooseBottomLayout) {
                if (isFinishing()) {
                    return;
                }
                if (webView != null) {
                    update2WebView();
                    return;
                }
                webView = new WebView(getActivity());
                chooseBottomLayout.addView(webView, 1, 1);
                initWebView();
                webView.loadUrl("file:///android_asset/web/game.html");
            }
        }, 400);
    }

    private void loadMethodFromXml() {
        String key = "game_config_method_" + GoldenAsiaApp.getUserCentre().getUserID() + "_" + lottery.getLotteryId();
        Method method = GsonHelper.fromJson(Preferences.getString(getContext(), key, null), Method.class);

        if (method != null) {
            changeGameMethod(method);
        }
    }

    private void saveMethod2Xml(Method method) {
        String key = "game_config_method_" + GoldenAsiaApp.getUserCentre().getUserID() + "_" + lottery.getLotteryId();
        Preferences.saveString(getContext(), key, GsonHelper.toJson(method));
    }

    private void initMenu() {
        menuController = new MenuController(getActivity(), lottery);
        menuController.setOnClickMethodListener(this);
    }

    private void loadMenu() {
        MethodListCommand methodListCommand = new MethodListCommand();
        methodListCommand.setLotteryID(lottery.getLotteryId());
        methodListCommand.setMethodGroupID(0);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MethodList>>>() {
        };
        RestRequestManager.executeCommand(getActivity(), methodListCommand, typeToken, restCallback, ID_METHOD_LIST,
                this);
    }

    private void updateMenu(ArrayList<MethodList> methodList) {
        menuController.setMethodList(methodList);
    }

    @Override
    public void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        if (game != null) {
            game.destroy();
        }
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(android.R.id.home)
    public void finishFragment() {
        getActivity().finish();
    }

    @OnClick(R.id.title_text_layout)
    public void showOrHideMenu() {
        Log.d(TAG, "showOrHideMenu: ");
        if (menuController.isShowing()) {
            menuController.hide();
        } else {
            menuController.show(titleView);
        }
    }

    @OnClick(R.id.help)
    public void showHelp() {
        //点击“帮助”按钮，显示帮助信息
    }

    @OnClick(R.id.pick_random)
    public void onRandom() {
        if (game != null) {
            game.onRandomCodes();
        }
    }

    @OnClick(R.id.choose_done_button)
    public void onChooseDone() {
        if (game == null) {
            return;
        }
        if (game.getSingleNum() > 0) {
            String codes = game.getSubmitCodes();
            Ticket ticket = new Ticket();
            ticket.setChooseMethod(game.getMethod());
            ticket.setChooseNotes(game.getSingleNum());
            ticket.setCodes(codes);

            shoppingCart.init(lottery);
            shoppingCart.addTicket(ticket);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        launchFragmentForResult(ShoppingMmcFragment.class, bundle, 1);
    }

    private void initWebView() {
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterface(), "androidJs");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (game != null) {
            game.reset();
        }
    }

    @Override
    public void onClickMethod(Method method) {
        menuController.hide();
        changeGameMethod(method);
    }

    private class JsInterface {
        @JavascriptInterface
        public String getData() {
            if (game == null) {
                return "";
            }
            return game.getWebViewCode();
        }

        @JavascriptInterface
        public String getMethodName() {
            if (game == null) {
                return "";
            }
            return game.getMethod().getName();
        }

        @JavascriptInterface
        public void result(int singleNum, boolean isDup) {
            Log.d(TAG, "result() called with: " + "singleNum = [" + singleNum + "], isDup = [" + isDup + "]");
            if (game == null) {
                return;
            }
            game.setNumState(singleNum, isDup);
            webView.post(updatePickNoticeRunnable);
        }
    }

    private void update2WebView() {
        if (webView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("calculate();", null);
        } else {
            webView.loadUrl("javascript:calculate();");
        }
    }

    private Runnable updatePickNoticeRunnable = new Runnable() {
        @Override
        public void run() {
            if (game == null) {
                return;
            }
            pickNoticeView.setText(String.format("选择%d注", game.getSingleNum()));
            if (game.getSingleNum() > 0) {
                chooseDoneButton.setEnabled(game.getSingleNum() > 0);
            } else {
                chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
            }
        }
    };

    private void changeGameMethod(Method method) {
        if (method == null) {
            return;
        }

        if (game == null) {
            pickGameLayout.removeAllViews();
        } else {
            if (method.getName().equals(game.getMethod().getName())) {
                //同一个玩法，不用切换
                return;
            }
            game.destroy();
            menuController.addPreference(method);
            saveMethod2Xml(method);
        }
        menuController.setCurrentMethod(method);
        titleView.setText(method.getCname());
        pickNoticeView.setText("选择0注");
        chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
        game = GameConfig.createGame(getActivity(),method,lottery);
        game.inflate(pickGameLayout);
        game.setOnSelectedListener(this);

        loadWebViewIfNeed();
    }

    private Method defaultGameMethod(ArrayList<MethodList> methodList) {
        String name = null;
        switch (lottery.getLotteryId()) {
            case 2://山东11选5
            case 6://江西11选5
            case 7://广东11选5
            case 16://11选5分分彩
            case 20://北京11选5
            case 21://上海11选5
            case 36://山西11选5
                name = "SDRX5";
                break;
            case 1://重庆时时彩
            case 4://新疆时时彩
            case 8://天津时时彩
            case 11://亚洲分分彩
            case 15://亚洲秒秒彩
            case 19://亚洲5分彩
                name = "SXZX";
                break;
            case 12:
            case 13:
            case 22:
            case 23:
            case 9://福彩3D
                name = "SXZX";
                break;
            case 10://P3p5
                name = "QSZX";
                break;
            case 17://六合彩
            case 26://六合彩分分彩
                name = "TMZX";
                break;
            default:
                break;
        }

        if (name == null) {
            return methodList.get(0).getChilds().get(0);
        }

        for (MethodList methods : methodList) {
            for (Method method : methods.getChilds()) {
                if (name.equals(method.getName())) {
                    return method;
                }
            }
        }

        return methodList.get(0).getChilds().get(0);
    }

    //game.setOnSelectedListener(this)的回调
    @Override
    public void onChanged(Game game) {
        loadWebViewIfNeed();
        update2WebView();
    }

    @Override
    public void onChanged(LhcGame lhcGame) {

    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_METHOD_LIST) {
                ArrayList<MethodList> methodList = (ArrayList<MethodList>) response.getData();
                if (game == null) {
                    Method method = defaultGameMethod(methodList);
                    saveMethod2Xml(method);
                    menuController.addPreference(method);
                    changeGameMethod(method);
                }
                updateMenu(methodList);
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };
}
