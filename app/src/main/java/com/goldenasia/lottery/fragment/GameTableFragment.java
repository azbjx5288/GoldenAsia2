package com.goldenasia.lottery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import com.goldenasia.lottery.component.TabPageAdapter;
import com.goldenasia.lottery.data.IssueEntity;
import com.goldenasia.lottery.data.LotteriesHistoryCommand;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.LotteryHistoryCode;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.data.MethodList;
import com.goldenasia.lottery.data.MethodListCommand;
import com.goldenasia.lottery.game.GameConfig;
import com.goldenasia.lottery.game.MenuController;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.MethodQueue;
import com.goldenasia.lottery.pattern.CustomViewPager;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.view.TableMenu;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2017/3/13.
 */

public class GameTableFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener, ViewPager
        .OnPageChangeListener, TableMenu.OnClickMethodListener
{
    private static final String TAG = GameTableFragment.class.getSimpleName();
    private static final int ID_METHOD_LIST = 1;
    
    
    @BindView(R.id.title_name)
    TextView titleName;
    @BindView(android.R.id.title)
    TextView titleView;
    @BindView(R.id.manual_input_botton)
    ImageView manualInputBotton;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.lotteryRadioButton)
    RadioButton lotteryRadioButton;
    @BindView(R.id.trendRadioButton)
    RadioButton trendRadioButton;
    @BindView(R.id.methodRadioButton)
    RadioButton methodRadioButton;
    @BindView(R.id.viewpager)
    CustomViewPager viewPager;
    @BindView(R.id.trend)
    ImageView trend;
    
    private MenuController menuController;
    private String[] radiotitle;
    private Lottery lottery;
    private List<Fragment> fragments = new ArrayList<>();
    private MethodQueue regularMethods;
    
    @OnClick(R.id.trend)
    public void openTrend()
    {
        Bundle bundle = new Bundle();
        bundle.putString("url", lottery.getChartUrl());
        launchFragment(TrendFragment.class, bundle);
    }
    
    public static void launch(BaseFragment fragment, Lottery lottery)
    {
        ArrayList gameList = new ArrayList();
        gameList.add(GameFragment.class);
        gameList.add(ResultsFragment.class);
        gameList.add(GameMethodInfoFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "");
        bundle.putStringArray("radiotitle", new String[]{"彩种", "开奖", "玩法说明"});
        bundle.putParcelableArrayList("fragmentlist", gameList);
        bundle.putSerializable("lottery", lottery);
        bundle.putSerializable("hasTab", true);
        bundle.putString("url", lottery.getChartUrl());
        FragmentLauncher.launch(fragment.getActivity(), GameTableFragment.class, bundle);
    }
    
    public static void launchMmc(BaseFragment fragment, Lottery lottery)
    {
        Log.i(TAG, "launchMmc");
        ArrayList gameList = new ArrayList();
        gameList.add(GameFragment.class);
        gameList.add(ResultsMmcFragment.class);
        gameList.add(GameMethodInfoFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "");
        bundle.putStringArray("radiotitle", new String[]{"彩种", "开奖", "玩法说明"});
        bundle.putParcelableArrayList("fragmentlist", gameList);
        bundle.putSerializable("lottery", lottery);
        bundle.putSerializable("hasTab", true);
        FragmentLauncher.launch(fragment.getActivity(), GameTableFragment.class, bundle);
    }
    
    public static void launchLhc(BaseFragment fragment, Lottery lottery)
    {
        ArrayList gameList = new ArrayList();
        gameList.add(GameLhcFragment.class);
        gameList.add(ResultsFragment.class);
        gameList.add(GameMethodInfoFragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("title", "");
        bundle.putStringArray("radiotitle", new String[]{"彩种", "开奖", "玩法说明"});
        bundle.putParcelableArrayList("fragmentlist", gameList);
        bundle.putSerializable("lottery", lottery);
        bundle.putSerializable("hasTab", true);
        bundle.putString("url", lottery.getChartUrl());
        FragmentLauncher.launch(fragment.getActivity(), GameTableFragment.class, bundle);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_game_table, container, false);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initTab();
        LoadWinHistory();
        initMenu();
        loadMenu();
        loadMethods();
    }

    /**
     * 加载开奖结果放在内存中
     */
    private  void  LoadWinHistory(){
        if(lottery.getLotteryId()==1//1: 重庆时时彩
                ||lottery.getLotteryId()==3//3:黑龙江时时彩
                ||lottery.getLotteryId()==4//4:新疆时时彩
                ||lottery.getLotteryId()==8//8:天津时时彩
                ||lottery.getLotteryId()==11//11:亚洲分分彩
                ||lottery.getLotteryId()==19//19:亚洲5分彩
                ||lottery.getLotteryId()==35//35:台湾五分彩
                ||lottery.getLotteryId()==37//37:亚洲2分彩)
         ) {
            LotteriesHistoryCommand command = new LotteriesHistoryCommand();
            command.setLotteryID(lottery.getLotteryId());
            command.setCurPage(1);
            command.setPerPage(200);
            TypeToken typeToken = new TypeToken<RestResponse<LotteryHistoryCode>>() {
            };
            RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, 2, this);
            restRequest.execute();
        }
    }

    /**
     * 初始化标签
     */
    private void initTab()
    {
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            setTitle(bundle.getString("title"));
            radiotitle = bundle.getStringArray("radiotitle");
            lottery = (Lottery) getArguments().getSerializable("lottery");
            titleName.setText(lottery.getCname());
            for (int i = 0; i < radiotitle.length; i++)
                if (i == 0)
                    lotteryRadioButton.setId(i);
                else if (i == 1)
                    trendRadioButton.setId(i);
                else
                    methodRadioButton.setId(i);
            ArrayList fragmentList = bundle.getParcelableArrayList("fragmentlist");
            for (int i = 0; i < fragmentList.size(); i++)
            {
                Class fragment = (Class) fragmentList.get(i);
                Bundle bundlelottery = new Bundle();
                bundlelottery.putSerializable("lottery", lottery);
                fragments.add(Fragment.instantiate(getActivity(), fragment.getName(), bundlelottery));
            }
            radioGroup.setOnCheckedChangeListener(this);
            TabPageAdapter tabPageAdapter = new TabPageAdapter(getFragmentManager(), fragments);
            viewPager.setAdapter(tabPageAdapter);
            viewPager.addOnPageChangeListener(this);
            radioGroup.check(radioGroup.getChildAt(0).getId());
            selectPage(0);
            if (!bundle.getBoolean("hasTab"))
                radioGroup.setVisibility(View.GONE);

            //防止ViewPager中的Fragment被销毁  --可以让ViewPager多缓存一个页面，这样上面的问题就得到了解决。
            viewPager.setOffscreenPageLimit(2);
        }
    }
    
    /**
     * 初始化菜单
     */
    private void initMenu()
    {
        menuController = new MenuController(getActivity(), lottery);
        menuController.setOnClickMethodListener(this);
    }
    
    /**
     * 加载菜单数据
     */
    private void loadMenu()
    {
        MethodListCommand methodListCommand = new MethodListCommand();
        methodListCommand.setLotteryID(lottery.getLotteryId());
        methodListCommand.setMethodGroupID(0);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MethodList>>>()
        {};
        RestRequestManager.executeCommand(getActivity(), methodListCommand, typeToken, restCallback, ID_METHOD_LIST,
                this);
    }
    
    private void loadMethods()
    {
        try
        {
            regularMethods = (MethodQueue) SharedPreferencesUtils.getObject(getActivity(),
                    ConstantInformation.REGULAR_METHODS, GoldenAsiaApp.getUserCentre().getUserID() + "_" + lottery
                            .getLotteryId());
        } catch (Exception e)
        {
            Log.d(TAG, "loadMethods: fail to load methods.");
        }
        if (regularMethods == null)
            regularMethods = new MethodQueue(9);
    }
    
    private void saveMethod2Xml(Method method)
    {
        String key = "game_config_method_" + GoldenAsiaApp.getUserCentre().getUserID() + "_" + lottery.getLotteryId();
        Preferences.saveString(getContext(), key, GsonHelper.toJson(method));
    }
    
    private void changeGameMethod(Method method)
    {
        if (method == null)
        {
            return;
        }
        switch (lottery.getLotteryId())
        {
            case 17:
            case 26:
                LHCFragment(method);
                break;
            default:
                GeneralFragment(method);
                break;
        }
    }
    
    private void GeneralFragment(Method method)
    {
        GameFragment gameFragment = (GameFragment) fragments.get(0);
        if (gameFragment.getGame() == null)
        {
            gameFragment.removeViews();
        } else
        {
            if (method.getName().equals(gameFragment.getGame().getMethod().getName()))
            {
                //同一个玩法，不用切换
                return;
            }
            gameFragment.getGame().destroy();
            menuController.addPreference(method);
            saveMethod2Xml(method);
            saveRegularMethods(method);
            //manualInputBotton.setText("手工录入");
            gameFragment.getGame().setExchange(true);
        }
        menuController.setCurrentMethod(method);
        titleView.setText(method.getCname());
        gameFragment.setGame(GameConfig.createGame(getActivity(), method, lottery));
        gameFragment.changeGameMethod(method);
        manualInputBotton.setVisibility(gameFragment.getGame().isSupportInput() ? View.VISIBLE : View.GONE);
        switch (lottery.getLotteryId()) {
            case 15://亚洲妙妙彩
            case 44://11选5秒秒彩
            case 45://快三秒秒彩
                trend.setVisibility(View.INVISIBLE);
                break;
            default:
                trend.setVisibility(View.VISIBLE);
        }
    }

    private void LHCFragment(Method method)
    {
        GameLhcFragment gameLhcFragment = (GameLhcFragment) fragments.get(0);
        if (gameLhcFragment.getLhcGame() == null)
        {
            gameLhcFragment.removeViews();
        } else
        {
            if (method.getName().equals(gameLhcFragment.getLhcGame().getMethod().getName()))
            {
                //同一个玩法，不用切换
                return;
            }
            gameLhcFragment.getLhcGame().destroy();
            menuController.addPreference(method);
            saveMethod2Xml(method);
            saveRegularMethods(method);
        }
        menuController.setCurrentMethod(method);
        titleView.setText(method.getCname());
        gameLhcFragment.setLhcGame(GameConfig.createLhcGame(method));
        gameLhcFragment.changeGameMethod();
        trend.setVisibility(View.VISIBLE);
    }
    
    private void saveRegularMethods(Method method)
    {
        regularMethods.offer(method);
    }
    
    private void updateMenu(ArrayList<MethodList> methodList)
    {
        menuController.setMethodList(methodList);
    }
    
    private Method defaultGameMethod(ArrayList<MethodList> methodList)
    {
        String key = "game_config_method_" + GoldenAsiaApp.getUserCentre().getUserID() + "_" + lottery.getLotteryId();
        Method methodDefault = GsonHelper.fromJson(Preferences.getString(getContext(), key, null), Method.class);
        if (methodDefault != null)
        {
            return methodDefault;
        } else
        {
            String name = null;
            switch (lottery.getLotteryId())
            {
                case 2://山东11选5
                case 6://江西11选5
                case 7://广东11选5
                case 16://11选5分分彩
                case 20://北京11选5
                case 21://上海11选5
                case 32:
                case 33:
                case 34:
                case 36://山西11选5
                    name = "SDRX5";
                    break;
                case 1://重庆时时彩
                case 4://新疆时时彩
                case 8://天津时时彩
                case 11://亚洲分分彩
                case 15://亚洲秒秒彩
                case 19://亚洲5分彩
                case 24:
                case 35:
                case 37:
                    name = "SXZX";
                    break;
                case 12://江苏快三
                case 13://快三分分彩
                case 22://安徽快三
                case 23://湖北快三
                case 41://河北快三
                case 42://河南快三
                case 43://福建快三
                    name = "JSHZ";
                    break;
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
                case 27://北京PK10
                    name = "LMGYH";
                    break;
                case 14://山东快乐扑克
                    name = "PKBX";//包选 PKBX
                    break;
                default:
                    break;
            }
            if (name == null)
            {
                return methodList.get(0).getChilds().get(0);
            }
            for (MethodList methods : methodList)
            {
                for (Method method : methods.getChilds())
                {
                    if (name.equals(method.getName()))
                    {
                        return method;
                    }
                }
            }
            return methodList.get(0).getChilds().get(0);
        }
    }
    
    @OnClick(android.R.id.home)
    public void backHome(View v)
    {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        getActivity().finish();
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
    }
    
    @Override
    public void onDestroyView()
    {
        try
        {
            SharedPreferencesUtils.putObject(getActivity(), ConstantInformation.REGULAR_METHODS, GoldenAsiaApp
                    .getUserCentre().getUserID() + "_" + lottery.getLotteryId(), regularMethods);
        } catch (Exception e)
        {
            Log.d(TAG, "onDestroyView: fail to save methods.");
        }
        super.onDestroyView();
    }
    
    @OnClick(android.R.id.home)
    public void finishFragment()
    {
        getActivity().finish();
    }
    
    @OnClick(R.id.title_text_layout)
    public void showOrHideMenu()
    {
        if (menuController.isShowing())
        {
            menuController.hide();
        } else
        {
            menuController.show(titleView);
        }
    }
    
    /**
     * 点击手工录入
     */
    @OnClick(R.id.manual_input_botton)
    public void manualInput()
    {
        //true 显示选号页面  false 显示录入
        if (((GameFragment) fragments.get(0)).getGame().isExchange())
        {
            //manualInputBotton.setText("选号页面");
            ((GameFragment) fragments.get(0)).getGame().setExchange(false);
        } else
        {
            //manualInputBotton.setText("手工录入");
            ((GameFragment) fragments.get(0)).getGame().setExchange(true);
        }
        ((GameFragment) fragments.get(0)).cutover();
    }
    
    
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId)
    {
        for (int i = 0; i < radiotitle.length; i++)
        {
            if (i == checkedId)
            {
                selectPage(i);
            }
            if (checkedId == trendRadioButton.getId())
            {
                Fragment resultsFragment = fragments.get(1);
                resultsFragment.onResume();
                //TrendFragment trendFragment= (TrendFragment) fragments.get(1);
                /*ChartTrendFragment chartFragment=(ChartTrendFragment)fragments.get(1);
                chartFragment.onResume();*/
            }
        }
    }
    
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
    
    }
    
    @Override
    public void onPageSelected(int position)
    {
        selectPage(position);
    }
    
    @Override
    public void onPageScrollStateChanged(int state)
    {
    
    }
    
    @Override
    public void onClickMethod(Method method)
    {
        menuController.hide();
        changeGameMethod(method);
    }
    
    private void selectPage(int position)
    {
        radioGroup.check(radioGroup.getChildAt(position).getId());
        viewPager.setCurrentItem(position, true);
        if (lottery.getLotteryId() == 15)
        {
            viewPager.setScanScroll(true);
        } else
        {
            viewPager.setScanScroll(true);
        }
    }
    
    private RestCallback restCallback = new RestCallback()
    {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response)
        {
            if (request.getId() == ID_METHOD_LIST)
            {
                ArrayList<MethodList> methodList = (ArrayList<MethodList>) response.getData();
                switch (lottery.getLotteryId())
                {
                    case 17:
                    case 26:
                        if (((GameLhcFragment) fragments.get(0)).getLhcGame() == null)
                        {
                            Method method = (Method) SharedPreferencesUtils.getObject(getActivity(), GoldenAsiaApp
                                    .getUserCentre().getUserID() + " lastPlay", lottery.getName());
                            if (method == null)
                                method = defaultGameMethod(methodList);
                            //Method method = defaultGameMethod(methodList);
                            saveMethod2Xml(method);
                            menuController.addPreference(method);
                            changeGameMethod(method);
                        }
                        break;
                    default:
                        if (((GameFragment) fragments.get(0)).getGame() == null)
                        {
                            Method method = (Method) SharedPreferencesUtils.getObject(getActivity(), GoldenAsiaApp
                                    .getUserCentre().getUserID() + " lastPlay", lottery.getName());
                            if (method == null)
                                method = defaultGameMethod(methodList);
                            //Method method = defaultGameMethod(methodList);
                            saveMethod2Xml(method);
                            menuController.addPreference(method);
                            changeGameMethod(method);
                        }
                        break;
                }
                updateMenu(methodList);
            }else if(request.getId() == 2){
                List<IssueEntity> items =((LotteryHistoryCode) response.getData()).getIssue();

                for (IssueEntity issueEntity:items){
                    ConstantInformation.HISTORY_CODE_LIST.add(issueEntity.getCode());
                }
            }
            return true;
        }
        
        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc)
        {
            if (errCode == 7003)
            {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006)
            {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }
        
        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state)
        {
        }
    };
}
