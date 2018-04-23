package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

/**
 * Created by ACE-PC on 2016/2/19.
 */
public class SpecialGame extends Game
{
    public SpecialGame(Method method)
    {
        super(method);
    }
    
    @Override
    public void onInflate()
    {
        try
        {
            java.lang.reflect.Method function = getClass().getMethod(method.getName(), Game.class);
            function.invoke(null, this);
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    public String getWebViewCode()
    {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers)
        {
            jsonArray.add(transformSpecial(pickNumber.getCheckedNumber(), false, true));
        }
        return jsonArray.toString();
    }
    
    public String getSubmitCodes()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++)
        {
            builder.append(transformSpecial(pickNumbers.get(i).getCheckedNumber(), false, false));
            if (i != size - 1)
            {
                builder.append(",");
            }
        }
        return builder.toString();
    }
    
    public void onRandomCodes()
    {
        try
        {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    private static void addPickSpecialNumberGame(Game game, View topView, String title, int min, int max)
    {
        PickNumber pickNumberSpecial = new PickNumber(topView, title);
        pickNumberSpecial.setColumnAreaHideOrShow(false);
        pickNumberSpecial.getNumberGroupView().setNumber(min, max);
        game.addPickNumber(pickNumberSpecial);
    }
    
    public static View createDefaultPickLayout(ViewGroup container)
    {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }
    
    private static void createPicklayout(Game game, String[] name, int min, int max)
    {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++)
        {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickSpecialNumberGame(game, view, name[i], min, max);
            views[i] = view;
        }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
        
        game.setColumn(name.length);
    }
    
    //后二包点 EXBD
    public static void EXBD(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"二星包点"}, 0, 2 * 9);
    }
    
    //后二和值 EXHZ
    public static void EXHZ(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"二星和值"}, 0, 2 * 9);
    }
    
    //前二包点 QEBD
    public static void QEBD(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"二星包点"}, 0, 2 * 9);
    }
    
    //前二和值 QEHZ
    public static void QEHZ(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"二星和值"}, 0, 2 * 9);
    }
    
    //后三包点 SXBD
    public static void SXBD(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"三星包点"}, 0, 3 * 9);
    }
    
    //后三和值 SXHZ
    public static void SXHZ(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"三星和值"}, 0, 3 * 9);
    }
    
    //前三包点 QSBD
    public static void QSBD(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"三星包点"}, 0, 3 * 9);
    }
    
    //前三和值 QSHZ
    public static void QSHZ(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"三星和值"}, 0, 3 * 9);
    }
    
    //中三包点 ZSBD
    public static void ZSBD(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"三星包点"}, 0, 3 * 9);
    }
    
    //中三和值 ZSHZ
    public static void ZSHZ(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"三星和值"}, 0, 3 * 9);
    }
    
    //五星和值 WXHZ
    public static void WXHZ(Game game)
    {
        ConstantInformation.YI_LOU_IS_SUPPORTED=false;
        createPicklayout(game, new String[]{"五星和值"}, 0, 5 * 9);
    }
    
    /**
     * 福彩3D
     */
    //组选和值 SXZXHZ
    public static void SXZXHZ(Game game)
    {
        createPicklayout(game, new String[]{"组选和值"}, 1, (3 * 9) - 1);
    }
    
    /**
     * P3P5
     */
    //组选和值 QSZXHZ
    public static void QSZXHZ(Game game)
    {
        createPicklayout(game, new String[]{"组选和值"}, 1, (3 * 9) - 1);
    }
    
    //和值 JSHZ
    public static void JSHZ(Game game)
    {
        createPicklayout(game, new String[]{"和值"}, 3, (2 * 9));
    }
    
    
    //后二包点随机 EXBD
    public static void EXBDRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 2 * 9, 1));
        game.notifyListener();
    }
    
    //后二和值随机 EXHZ
    public static void EXHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 2 * 9, 1));
        game.notifyListener();
    }
    
    //前二包点随机 QEBD
    public static void QEBDRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 2 * 9, 1));
        game.notifyListener();
    }
    
    //前二和值随机 QEHZ
    public static void QEHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 2 * 9, 1));
        game.notifyListener();
    }
    
    //后三包点随机 SXBD
    public static void SXBDRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 3 * 9, 1));
        game.notifyListener();
    }
    
    //后三和值随机 SXHZ
    public static void SXHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 3 * 9, 1));
        game.notifyListener();
    }
    
    //前三包点随机 QSBD
    public static void QSBDRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 3 * 9, 1));
        game.notifyListener();
    }
    
    //前三和值随机 QSHZ
    public static void QSHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 3 * 9, 1));
        game.notifyListener();
    }
    
    //中三包点随机 ZSBD
    public static void ZSBDRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 3 * 9, 1));
        game.notifyListener();
    }
    
    //中三和值随机 ZSHZ
    public static void ZSHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 3 * 9, 1));
        game.notifyListener();
    }
    
    //组选和值随机 SXZXHZ
    public static void SXZXHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, (3 * 9) - 1, 1));
        game.notifyListener();
    }
    
    /**
     * P3P5
     */
    //组选和值随机 QSZXHZ
    public static void QSZXHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, (3 * 9) - 1, 1));
        game.notifyListener();
    }
    
    //五星和值 WXHZ
    public static void WXHZRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 5 * 9, 1));
        game.notifyListener();
    }
}
