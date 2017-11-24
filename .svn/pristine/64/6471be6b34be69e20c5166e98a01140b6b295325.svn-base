package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

/**
 * Created by ACE-PC on 2016/2/19.
 */
public class KsCommonGame extends Game
{
    
    public KsCommonGame(Method method)
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
            jsonArray.add(transform(pickNumber.getCheckedNumber(), true, true));
        }
        return jsonArray.toString();
    }
    
    public String getSubmitCodes()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++)
        {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, false));
            if (i != size - 1)
            {
                builder.append(",");
            }
        }
        return builder.toString();
    }
    
    public static View createDefaultPickLayout(ViewGroup container)
    {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column2, null, false);
    }
    
    private static void addPickTextGame(Game game, View topView, String title, String[] disText)
    {
        PickNumber pickNumberText = new PickNumber(topView, title);
        pickNumberText.getNumberGroupView().setNumber(1, disText.length);
        pickNumberText.setDisplayText(disText);
        pickNumberText.setNumberStyle(null);
        pickNumberText.setColumnAreaHideOrShow(false);
        game.addPickNumber(pickNumberText);
    }
    
    private static void createPicklayout(Game game, String[] name, String[][] disText)
    {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++)
        {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickTextGame(game, view, name[i], disText[i]);
            views[i] = view;
        }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
        
        game.setColumn(name.length);
    }
    
    /**
     *
     * **/
    private static String[] displayText(Boolean flag, int max)
    {
        String[] disText = new String[max];
        
        for (int i = 0; i < max; i++)
        {
            int distext = i + 1;
            if (flag == null)
            {
                disText[i] = distext + "" + distext + "" + distext;
            } else if (flag)
            {
                disText[i] = distext + "" + distext;
            } else
            {
                disText[i] = distext + "";
            }
        }
        
        return disText;
    }
    
    //二不同号 JSEBT
    public static void JSEBT(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"二不同号"}, display);
    }
    
    //三连号通选 JSSLTX
    public static void JSSLTX(Game game)
    {
        String[][] display = {{"三连号通选"}};
        createPicklayout(game, new String[]{"三连号通选"}, display);
    }
    
    //和值 JSHZ
    public static void JSHZ(Game game)
    {
    }

    /*//三同号单选 JSSTDX
    public static void JSSTDX(Game game) {
        String[][] display = {displayText(null, 6)};
        createPicklayout(game, new String[]{"三同号单选"}, display);
    }*/

    /*//二同号单选 JSETDX
    public static void JSETDX(Game game) {
        String[][] display = {displayText(true, 6), displayText(false, 6)};
        createPicklayout(game, new String[]{"同号", "不同号"}, display);
    }*/
    
    //三同号通选 JSSTTX
    public static void JSSTTX(Game game)
    {
        String[][] display = {{"三同号通选"}};
        createPicklayout(game, new String[]{"三同号通选"}, display);
    }

/*    //二同号复选 JSETFX
    public static void JSETFX(Game game) {
        String[][] display = {displayText(true, 6)};
        createPicklayout(game, new String[]{"二同号复选"}, display);
    }*/
    
    //三不同号 JSSBT
    public static void JSSBT(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"三不同号"}, display);
    }
    
    //猜1不出 CYBUC
    public static void CYBUC(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"选1号"}, display);
    }
    
    //猜2不出 CEBUC
    public static void CEBUC(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"选2号"}, display);
    }
    
    //猜3不出 CSBUC
    public static void CSBUC(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"选3号"}, display);
    }
    
    //猜1必出 CYBIC
    public static void CYBIC(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"选1号"}, display);
    }
    
    //猜2必出 CEBIC
    public static void CEBIC(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"选2号"}, display);
    }
    
    //猜3必出 CSBIC
    public static void CSBIC(Game game)
    {
        String[][] display = {displayText(false, 6)};
        createPicklayout(game, new String[]{"选3号"}, display);
    }
}
