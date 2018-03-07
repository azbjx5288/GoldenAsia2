package com.goldenasia.lottery.game;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * Created by Sakura on 2016/10/25.
 */
public class TextMultipleGame extends Game
{
    private static String[] digitText = new String[]{"大", "小", "单", "双"};
    private static String[] dragonTigerText = new String[]{"龙", "虎"};
    private static ArrayList<String[]> twoSameSinglePickText = new ArrayList<String[]>()
    {
        {
            add(new String[]{"11", "22", "33", "44", "55", "66"});
            add(new String[]{"1", "2", "3", "4", "5", "6"});
        }
    };
    private static String[] twoSameMulPickText = new String[]{"11", "22", "33", "44", "55", "66"};
    private static String[] threeSameSinglePickText = new String[]{"111", "222", "333", "444", "555", "666"};
    private static String[] raceText = new String[]{"快", "慢"};
    private static String[] daXiaoText = new String[]{"大", "小"};
    private static String[] danShuangText = new String[]{"单", "双"};
    private static String[] yanSeText = new String[]{"全红", "全黑", "1红2黑", "2红1黑"};
    
    private static int TYPE;
    private static final int TYPE_DIGIT = 1;
    private static final int TYPE_DRAGON_TIGER = 2;
    private static final int TYPE_ETDX = 3;
    private static final int TYPE_ETFX = 4;
    private static final int TYPE_STDX = 5;
    private static final int TYPE_LMGYH = 6;
    private static final int TYPE_JSPK = 7;
    private static final int TYPE_LMMC = 8;
    private static final int TYPE_LMLH = 9;
    private static final int TYPE_JSDX=10; //大小
    private static final int TYPE_JSDS=11;//单双
    private static final int TYPE_JSYS=12;//颜色
    private static final int TYPE_JSETFX =13;//二同号复选 JSETFX

    public TextMultipleGame(Method method)
    {
        super(method);
        switch (method.getName())
        {
            case "WXHZDXDS":
                setHasRandom(true);
                break;
            case "JSPK":
                setRanking(true);
                break;
            default:
                /*setHasRandom(false);
                setRanking(false);*/
        }
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
        if (isRanking())
            jsonArray.add(transformRankJsonArray(ranks));
        for (PickNumber pickNumber : pickNumbers)
        {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), true, true));
        }
        return jsonArray.toString();
    }
    
    public String getSubmitCodes()
    {
        StringBuilder builder = new StringBuilder();
        if (isRanking())
            builder.append(transformRank(ranks));
        switch (TYPE)
        {
            case TYPE_DIGIT:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtext(pickNumbers.get(i).getCheckedNumber(), digitText, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_DRAGON_TIGER:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), dragonTigerText, true,
                            false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_ETDX:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    if (i == 0)
                    {
                        builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(),
                                twoSameSinglePickText.get(i), false, false));
                        builder.append(",");
                    } else
                    {
                        /*ArrayList<Integer> arr0 = pickNumbers.get(0).getCheckedNumber();
                        ArrayList<Integer> submitArr = new ArrayList<>();
                        for (int j = 0; j < pickNumbers.get(i).getCheckedNumber().size(); j++)
                        {
                            if (!arr0.contains(pickNumbers.get(i).getCheckedNumber().get(j)))
                                submitArr.add(pickNumbers.get(i).getCheckedNumber().get(j));
                        }*/
                        builder.append(transformtext(pickNumbers.get(i).getCheckedNumber(), twoSameSinglePickText.get
                                (i), false));
                    }
                }
                break;
            case TYPE_ETFX:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), twoSameMulPickText,
                            true, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_STDX:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(),
                            threeSameSinglePickText, true, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_LMGYH:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), digitText, false,
                            false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_JSPK:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), raceText, false, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_LMMC:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), digitText, false, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_LMLH:
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), dragonTigerText, false, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_JSDX: //大小
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(),
                            daXiaoText, true, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_JSDS://单双
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(),
                            danShuangText, true, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_JSYS://颜色
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(),
                            yanSeText, false, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            case TYPE_JSETFX://二同号复选 JSETFX
                for (int i = 0, size = pickNumbers.size(); i < size; i++)
                {
                    builder.append(transformtextSpecial(pickNumbers.get(i).getCheckedNumber(), twoSameMulPickText,
                            false, false));
                    if (i != size - 1)
                    {
                        builder.append(",");
                    }
                }
                break;
            default:
                break;
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
            Log.i("TextMultipleGame", "onInflate: " + "//" + method.getCname() + "随机 " + method.getName() + " public " +
                    "" + "" + "" + "" + "" + "static void " + method.getName() + "Random" + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }
    
    public static View createDefaultPickLayout(ViewGroup container)
    {
        if (TYPE == TYPE_JSPK)
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_rank, null, false);
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }
    
    public static View createRankPickLayout(ViewGroup container)
    {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_rank, null, false);
    }
    
    private static void addPickTextGame(Game game, View topView, String title, String[] disText, boolean chooseMode)
    {
        PickNumber pickNumberText = new PickNumber(topView, title);
        pickNumberText.getNumberGroupView().setNumber(1, disText.length);
        pickNumberText.setDisplayText(disText);
        pickNumberText.setNumberStyle(null);
        pickNumberText.setChooseMode(chooseMode);
        pickNumberText.setColumnAreaHideOrShow(false);
        game.addPickNumber(pickNumberText);
    }
    
    private static void createPicklayout(Game game, String[] name, String[] disText, boolean chooseMode)
    {
        View[] views = new View[name.length];
        if (!game.isRanking())
            for (int i = 0; i < name.length; i++)
            {
                View view = createDefaultPickLayout(game.getTopLayout());
                addPickTextGame(game, view, name[i], disText, chooseMode);
                views[i] = view;
            }
        else
            for (int i = 0; i < name.length; i++)
            {
                View view = createRankPickLayout(game.getTopLayout());
                game.initRankPanel(view);
                addPickTextGame(game, view, name[i], disText, chooseMode);
                views[i] = view;
            }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
        
        game.setColumn(name.length);
    }
    
    private static void createPicklayout(Game game, String[] name, ArrayList<String[]> disText, boolean chooseMode)
    {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++)
        {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickTextGame(game, view, name[i], disText.get(i), chooseMode);
            views[i] = view;
        }
        
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views)
        {
            topLayout.addView(view);
        }
        
        game.setColumn(name.length);
    }
    
    //冠亚和 LMGYH
    public static void LMGYH(Game game)
    {
        TYPE = TYPE_LMGYH;
        createPicklayout(game, new String[]{"冠亚和"}, digitText, false);
    }
    
    //名次 LMMC
    public static void LMMC(Game game)
    {
        TYPE = TYPE_LMMC;
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"},
                digitText, false);
    }
    
    //龙虎 LMLH
    public static void LMLH(Game game)
    {
        TYPE = TYPE_LMLH;
        createPicklayout(game, new String[]{"1V10", "2V9", "3V8", "4V7", "5V6"}, dragonTigerText, false);
    }
    
    //五星和值大小单双 WXHZDXDS
    public static void WXHZDXDS(Game game)
    {
        TYPE = TYPE_DIGIT;
        createPicklayout(game, new String[]{"五星和值大小单双"}, digitText, false);
    }
    
    //五星和值大小单双随机 WXHZDXDSRandom
    public static void WXHZDXDSRandom(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 4, 1));
        game.notifyListener();
    }
    
    //二同号单选 JSETDX
    public static void JSETDX(Game game)
    {
        TYPE = TYPE_ETDX;
        createPicklayout(game, new String[]{"同号", "不同号"}, twoSameSinglePickText, false);
    }
    
    //二同号复选 JSETFX
    public static void JSETFX(Game game)
    {
        TYPE = TYPE_JSETFX;//二同号复选 JSETFX
        createPicklayout(game, new String[]{"二同号复选"}, twoSameMulPickText, false);
    }
    
    //三同号单选 JSSTDX
    public static void JSSTDX(Game game)
    {
        TYPE = TYPE_STDX;
        createPicklayout(game, new String[]{"三同号单选"}, threeSameSinglePickText, false);
    }
    //JSDX  大小
    public static void JSDX(Game game)
    {
        TYPE = TYPE_JSDX;
        createPicklayout(game, new String[]{"大小"}, daXiaoText, false);
    }
    //JSDS   单双
    public static void JSDS(Game game)
    {
        TYPE = TYPE_JSDS;
        createPicklayout(game, new String[]{"单双"}, danShuangText, false);
    }
    //JSYS 颜色
    public static void JSYS(Game game)
    {
        TYPE = TYPE_JSYS;
        createPicklayout(game, new String[]{"颜色"}, yanSeText, false);
    }
    //竞速 JSPK
    public static void JSPK(Game game)
    {
        TYPE = TYPE_JSPK;
        createPicklayout(game, new String[]{"竞速"}, raceText, false);
    }
    
    //后二大小单双 EXDXDS
    public static void EXDXDS(Game game)
    {
        TYPE = TYPE_DIGIT;
        createPicklayout(game, new String[]{"十位", "个位"}, digitText, false);
    }
    
    //后三大小单双 SXDXDS
    public static void SXDXDS(Game game)
    {
        TYPE = TYPE_DIGIT;
        createPicklayout(game, new String[]{"百位", "十位", "个位"}, digitText, false);
    }
    
    //前二大小单双 QEDXDS
    public static void QEDXDS(Game game)
    {
        TYPE = TYPE_DIGIT;
        createPicklayout(game, new String[]{"百位", "十位"}, digitText, false);
    }
    
    //前三大小单双 QSDXDS
    public static void QSDXDS(Game game)
    {
        TYPE = TYPE_DIGIT;
        createPicklayout(game, new String[]{"万位","千位","百位"}, digitText, false);
    }
    
    //中三大小单双 ZSDXDS
    public static void ZSDXDS(Game game)
    {
        TYPE = TYPE_DIGIT;
        createPicklayout(game, new String[]{"千位","百位", "十位"}, digitText, false);
    }
    
    //清空
    public void onClearPick(Game game)
    {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onClearPick();
        game.notifyListener();
        
        if (manualInputView != null)
        {
            manualInputView.getInputEditText().setText("");
            setSingleNum(0);
            if (onManualEntryListener != null)
            {
                onManualEntryListener.onManualEntry(0);
            }
        }
    }
}
