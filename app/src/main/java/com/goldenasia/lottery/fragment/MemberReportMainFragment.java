package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.pattern.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gan on 2017/10/13.
 * 会员报表
 */

public class MemberReportMainFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{
    @Bind(R.id.title)
    TextView title;

    @Bind(R.id.radioGroup)
    RadioGroup radioGroup;

    @Bind(R.id.tab_menu1)
    RadioButton tabMenu1;
    @Bind(R.id.tab_menu2)
    RadioButton tabMenu2;
    @Bind(R.id.tab_menu3)
    RadioButton tabMenu3;
    @Bind(R.id.tab_menu4)
    RadioButton tabMenu4;
    @Bind(R.id.tab_menu5)
    RadioButton tabMenu5;

    @Bind(R.id.viewpager)
    CustomViewPager viewPager;

    private BubblePopupWindow bubblePopupWindow;

    private List<Fragment> fragments = new ArrayList<>();
    private String[] radiotitle;

    public static void launch(BaseFragment fragment) {
        ArrayList gameList = new ArrayList();
        gameList.add(MemberReportSubFragment.class);
        gameList.add(MemberReportSubFragment.class);
        gameList.add(MemberReportSubFragment.class);
        gameList.add(MemberReportSubFragment.class);
        gameList.add(MemberReportSubFragment.class);

        Bundle bundle = new Bundle();
        bundle.putString("title", "");
        bundle.putStringArray("radiotitle", new String[]{"界面1", "界面2","界面3", "界面4","界面5"});
        bundle.putParcelableArrayList("fragmentlist", gameList);
        bundle.putSerializable("hasTab", true);

        FragmentLauncher.launch(fragment.getActivity(), MemberReportMainFragment.class, bundle);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.member_report_main_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTab();
        initPopupWindow();
    }

    private void initTab() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            radiotitle = bundle.getStringArray("radiotitle");
            for (int i = 0; i < radiotitle.length; i++)
                if (i == 0)
                    tabMenu1.setId(i);
                else if (i == 1)
                    tabMenu2.setId(i);
                else if (i == 2)
                    tabMenu3.setId(i);
                else if (i == 3)
                    tabMenu4.setId(i);
                else
                    tabMenu5.setId(i);


            ArrayList fragmentList = bundle.getParcelableArrayList("fragmentlist");
            for (int i = 0; i < fragmentList.size(); i++) {
                Class fragment = (Class) fragmentList.get(i);
                Bundle bundlelottery = new Bundle();
                bundlelottery.putInt("key",i);
                fragments.add(Fragment.instantiate(getActivity(), fragment.getName(), bundlelottery));
//                MemberReportSubFragment memberReportSubFragment=new MemberReportSubFragment();
//                memberReportSubFragment.setArguments(bundlelottery);
//                fragments.add(memberReportSubFragment);
//                fragments.add(memberReportSubFragment);
//                fragments.add(memberReportSubFragment);
//                fragments.add(memberReportSubFragment);
//                fragments.add(memberReportSubFragment);




            }

            radioGroup.setOnCheckedChangeListener(this);
            TabPageAdapter tabPageAdapter = new TabPageAdapter(getFragmentManager(), fragments);
            viewPager.setAdapter(tabPageAdapter);
            radioGroup.check(radioGroup.getChildAt(0).getId());
            selectPage(0);
        }
    }

    @OnClick({R.id.title_text_layout,R.id.tab_menu1,R.id.tab_menu2,R.id.tab_menu3,R.id.tab_menu4,R.id.tab_menu5,R.id.back})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_text_layout://切换平台
//                ArrayList<Class> passwors = new ArrayList();
//                passwors.add(LoginPasswordSetting.class);
//                passwors.add(CashPasswordSetting.class);
//                TwoTableFragment.launch(getActivity(), "密码设置", new String[]{"登录密码", "资金密码"}, passwors);

                bubblePopupWindow.showArrowTo(v, BubbleStyle.ArrowDirection.Up,0);

                break;
            case R.id.tab_menu1:
                launchFragment(SecuritySetting.class);
                break;
            case R.id.card_setting:
                launchFragment(BankCardSetting.class);
                break;
            case R.id.back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    private void initPopupWindow(){
        View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.member_report_popupwindow, null);
        BubbleLinearLayout bubbleLinearLayout = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        bubblePopupWindow = new BubblePopupWindow(rootView, bubbleLinearLayout);
        RadioGroup platform_group = (RadioGroup) rootView.findViewById(R.id.platform_group);
        RadioButton platform_one = (RadioButton) rootView.findViewById(R.id.platform_one);
        RadioButton platform_two = (RadioButton) rootView.findViewById(R.id.platform_two);
        RadioButton platform_three = (RadioButton) rootView.findViewById(R.id.platform_three);

//        prizeModeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bubblePopupWindow.showArrowTo(v, BubbleStyle.ArrowDirection.Down);
//            }
//        });
        platform_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.platform_one:
                        changePlatform(1);


                        bubblePopupWindow.dismiss();
                        title.setText("全部平台");
                        break;
                    case R.id.platform_two:

                        changePlatform(2);

                        bubblePopupWindow.dismiss();
                        title.setText("彩票");
                        break;
                    case R.id.platform_three:
                        changePlatform(3);

                        bubblePopupWindow.dismiss();
                        title.setText("GA游戏");
                        break;
                }
            }
        });
    }

    /**
     * 1 代表 全部平台
     * 2 代表 彩票平台
     * 3 代表 GA游戏平台
     * @param platformType
     */
    private  void  changePlatform(int  platformType){
        MemberReportSubFragment memberReportSubFragment;
        switch (platformType){
            case 1:
                memberReportSubFragment=(MemberReportSubFragment)(fragments.get(viewPager.getCurrentItem()));
                memberReportSubFragment.reLoad(1);

                break;
            case 2:
                memberReportSubFragment=(MemberReportSubFragment)(fragments.get(viewPager.getCurrentItem()));
                memberReportSubFragment.reLoad(2);
                break;
            case 3:
                memberReportSubFragment=(MemberReportSubFragment)(fragments.get(viewPager.getCurrentItem()));
                memberReportSubFragment.reLoad(3);
                break;
            default:
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < fragments.size(); i++) {
            if (i == checkedId) {
                selectPage(i);
            }
        }
    }

    private void selectPage(int position) {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
        viewPager.setScanScroll(false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
