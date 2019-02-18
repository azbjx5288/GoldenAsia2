package com.goldenasia.lottery.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.goldenasia.lottery.BuildConfig;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.util.ToastUtils;
import com.goldenasia.lottery.util.WindowUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created By Sakura
 */
public class GAGameFragment extends BaseFragment {
    private String url;

    @BindView(R.id.ga_web)
    WebView webView;
    @BindView(R.id.loading_layout)
    LinearLayout loadingLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setFitSystem(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ga_game, container, false);
        ButterKnife.bind(this, view);
        WindowUtils.hideSystemUI(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    private void init() {
        Bundle bundle;
        bundle = getActivity().getIntent().getExtras();
        try {
            url = bundle.getString("url");
        } catch (Exception e) {
            ToastUtils.showShortToast(getActivity(), "进入游戏失败");
            getActivity().finish();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
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
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingLayout.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });
    }

    public static void launch(BaseFragment fragment, String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        FragmentLauncher.launch(fragment.getActivity(), GAGameFragment.class, bundle);
    }

    @Override
    public void onDestroyView() {
        if (webView != null)
            webView.destroy();
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (webView != null) {
            webView.destroy();
        }
    }

    private class JsInterface {
        /**
         * 给网页调用，网页点“离开”游戏时使用 --> androidJs.finishGame()
         */
        @JavascriptInterface
        public void finishGame() {
            getActivity().finish();
        }

        @JavascriptInterface
        public void goToWebView(String url) {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            launchFragment(WebViewFragment.class, bundle);
        }
    }
}
