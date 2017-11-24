package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.LhcPickNumber;

import java.util.ArrayList;

/**
 * 六合彩直选玩法
 * Created by Sakura on 2016/9/15.
 */

public class LhcRxGame extends LhcGame
{
    public LhcRxGame(Method method)
    {
        super(method);
    }
    
    @Override
    public void onInflate()
    {
        try
        {
            java.lang.reflect.Method function = getClass().getMethod(method.getName(), LhcGame.class);
            function.invoke(null, this);
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    public void onRandomCodes()
    {
        try
        {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Random", LhcGame.class);
            function.invoke(null, this);
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    @Override
    public void onClearPick(LhcGame game){
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onClearPick();
        game.notifyListener();
    }
    
    private static void addPickNumber2Game(LhcGame game, View topView, String title)
    {
        LhcPickNumber pickNumber2 = new LhcPickNumber(topView, title);
        pickNumber2.setPickColumnArea(false);
        game.addPickNumber(pickNumber2);
    }
    
    public static View createDefaultPickLayout(ViewGroup container)
    {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_lhc_num, null, false);
    }
    
    private static void createPicklayout(LhcGame game, String[] name)
    {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++)
        {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
    }
    
    //三中三 SSLM
    public static void SSLM(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //二中二 EELM
    public static void EELM(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //三中二 SELM
    public static void SELM(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //四中二 SIELM
    public static void SIELM(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //四中三 SISLM
    public static void SISLM(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //四中四 SISILM
    public static void SISILM(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //正特五不中 ZTBZ5
    public static void ZTBZ5(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //正特六不中 ZTBZ6
    public static void ZTBZ6(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //正特七不中 ZTBZ7
    public static void ZTBZ7(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //正特八不中 ZTBZ8
    public static void ZTBZ8(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //正特九不中 ZTBZ9
    public static void ZTBZ9(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    //正特十不中 ZTBZ10
    public static void ZTBZ10(LhcGame game)
    {
        createPicklayout(game, new String[]{""});
    }
    
    /*机选*/
    //三中三随机 SSLMRandom
    public static void SSLMRandom(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 3, new ArrayList<>()));
        game.notifyListener();
    }
    
    //二中二随机 EELMRandom
    public static void EELMRandom(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 2, new ArrayList<>()));
        game.notifyListener();
    }
    
    //三中二随机 SELMRandom
    public static void SELMRandom(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 3, new ArrayList<>()));
        game.notifyListener();
    }
    
    //四中二随机 SIELMRandom
    public static void SIELMRandom(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 4, new ArrayList<>()));
        game.notifyListener();
    }
    
    //四中三随机 SISLMRandom
    public static void SISLMRandom(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 4, new ArrayList<>()));
        game.notifyListener();
    }
    
    //四中四随机 SISILMRandom
    public static void SISILMRandom(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 4, new ArrayList<>()));
        game.notifyListener();
    }
    
    //正特五不中随机 ZTBZ5Random
    public static void ZTBZ5Random(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 5, new ArrayList<>()));
        game.notifyListener();
    }
    
    //正特六不中随机 ZTBZ6Random
    public static void ZTBZ6Random(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 6, new ArrayList<>()));
        game.notifyListener();
    }
    
    //正特七不中随机 ZTBZ7Random
    public static void ZTBZ7Random(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 7, new ArrayList<>()));
        game.notifyListener();
    }
    
    //正特八不中随机 ZTBZ8Random
    public static void ZTBZ8Random(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 8, new ArrayList<>()));
        game.notifyListener();
    }
    
    //正特九不中随机 ZTBZ9Random
    public static void ZTBZ9Random(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 9, new ArrayList<>()));
        game.notifyListener();
    }
    
    //正特十不中随机 ZTBZ10Random
    public static void ZTBZ10Random(LhcGame game)
    {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 49, 10, new ArrayList<>()));
        game.notifyListener();
    }
}
