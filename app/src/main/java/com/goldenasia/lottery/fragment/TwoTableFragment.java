package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.data.UserInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 两页Table的页面
 * Created by Alashi on 2016/3/17.
 */
public class TwoTableFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager
        .OnPageChangeListener {

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;
    @Bind(R.id.viewpager)
    ViewPager viewPager;

    private boolean status=true;
    private UserInfo userInfo;
    private String[] radiotitle;
    private List<Fragment> fragments = new ArrayList<>();

    public static void launch(Context context, String title, String[] radio1title, ArrayList fragmentList) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putStringArray("radiotitle", radio1title);
        bundle.putParcelableArrayList("fragmentlist", fragmentList);
        FragmentLauncher.launch(context, TwoTableFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        return inflateView(inflater, container, "", R.layout.fragment_many_table);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();
        radioGroup.setOnCheckedChangeListener(this);
        TabPageAdapter tabPageAdapter = new TabPageAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(tabPageAdapter);
        viewPager.setOnPageChangeListener(this);
        radioGroup.check(radioGroup.getChildAt(0).getId());

//        selectPage(0);
    }

    protected void initData() {
        Bundle bundle = getArguments();
        setTitle(bundle.getString("title"));
        radiotitle = bundle.getStringArray("radiotitle");
        for (int i = 0; i < radiotitle.length; i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.table_radiobutton, radioGroup, false);
            radioButton.setId(i);
            radioButton.setText(radiotitle[i]);//style
            radioGroup.addView(radioButton);
        }
        ArrayList fragmentList = bundle.getParcelableArrayList("fragmentlist");
        for (int i = 0; i < fragmentList.size(); i++) {
            Class fragment = (Class) fragmentList.get(i);
            fragments.add(Fragment.instantiate(getActivity(), fragment.getName()));
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < radiotitle.length; i++) {
            if (i == checkedId) {
                selectPage(i);
            }
        }
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

    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
        tipInfo(position);
    }

    //提示配置
    private void tipInfo(int position){
        if(fragments.get(position).getClass().getSimpleName().equals("CashPasswordSetting")) {
            if (TextUtils.isEmpty(userInfo.getRealName())&&status) {
                TextView tipText = new TextView(getActivity());
                tipText.setHeight(150);
                tipText.setGravity(Gravity.CENTER);
                tipText.setText("您还没有设置资金密码，请先在安全资料中设置");
                CustomDialog.Builder builder = new CustomDialog.Builder(getContext());
                builder.setContentView(tipText);
                builder.setTitle("温馨提示");
                builder.setLayoutSet(DialogLayout.SINGLE);
                builder.setPositiveButton("知道了", (dialog, which) -> {
                    launchFragment(SecuritySetting.class);
                    dialog.dismiss();
                    getActivity().finish();
                });
                CustomDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                status=false;
            }
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        status=true;
        userInfo = GoldenAsiaApp.getUserCentre().getUserInfo();
    }

}
