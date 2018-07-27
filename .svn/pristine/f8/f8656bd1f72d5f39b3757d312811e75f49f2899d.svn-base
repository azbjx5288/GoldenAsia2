package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.LhcLayout;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 六合彩生肖玩法
 * Created by Sakura on 2016/9/15.
 */

public class LhcSxGame extends LhcGame
{
    private boolean showAnimal;
    private ArrayList<String> pickSxList;
    /*全部12个*/
    private int[] sxList = new int[]{R.id.layout_mouse, R.id.layout_cow, R.id.layout_tiger, R.id.layout_rabbit, R.id
            .layout_dragon, R.id.layout_snake, R.id.layout_horse, R.id.layout_sheep, R.id.layout_monkey, R.id
            .layout_chicken, R.id.layout_dog, R.id.layout_pig};
    /*家禽6个*/
    private int[] poultryList = new int[]{R.id.layout_cow, R.id.layout_horse, R.id.layout_sheep, R.id
            .layout_chicken, R.id.layout_dog, R.id.layout_pig};
    /*野兽6个*/
    private int[] beastList = new int[]{R.id.layout_mouse, R.id.layout_tiger, R.id.layout_rabbit, R.id.layout_dragon,
            R.id.layout_snake, R.id.layout_monkey};

    public LhcSxGame(Method method)
    {
        super(method);
        pickSxList = new ArrayList<>();
        if (!"TMSX".equals(method.getName()))
        {
            showAnimal = false;
        } else
        {
            showAnimal = true;
        }
    }

    @Override
    public void onInflate()
    {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_lhc_sx, topLayout, true);
        ButterKnife.bind(this, topLayout);
        showMode(showAnimal);
    }

    @Override
    public void reset()
    {
        for (int i : sxList)
        {
            topLayout.findViewById(i).setSelected(false);
        }
        pickSxList.clear();
        notifyListener();
    }

    @OnClick({R.id.layout_mouse, R.id.layout_cow, R.id.layout_tiger, R.id.layout_rabbit, R.id.layout_dragon, R.id
            .layout_snake, R.id.layout_horse, R.id.layout_sheep, R.id.layout_monkey, R.id.layout_chicken, R.id
            .layout_dog, R.id.layout_pig})
    public void onLayoutClick(LhcLayout view)
    {
        if (view.isSelected())
        {
            view.setSelected(false);
            pickSxList.remove(view.getSx());
        } else
        {
            view.setSelected(true);
            pickSxList.add(view.getSx());
        }

        notifyListener();
    }

    public void onRandomCodes()
    {
        reset();
        switch (method.getName())
        {
            case "ERLX":
                randomAdd(random(0, 11, 2));
                break;
            case "SNLX":
                randomAdd(random(0, 11, 3));
                break;
            case "SILX":
                randomAdd(random(0, 11, 4));
                break;
            default:
                randomAdd(random(0, 11, 1));
        }
        /*if ("ERLX".equals(method.getName()))
        {
            randomAdd(random(0, 11, 2));
        } else if ("SNLX".equals(method.getName()))
        {
            randomAdd(random(0, 11, 3));
        } else
            randomAdd(random(0, 11, 1));*/
        notifyListener();
    }

    @Override
    public String getWebViewCode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        int length = pickSxList.size();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(pickSxList.get(i));
            if (i != length - 1)
            {
                stringBuilder.append('_');
            }
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());
        return jsonArray.toString();
    }

    @Override
    public String getSubmitCodes()
    {

        StringBuilder stringBuilder = new StringBuilder();
        int length = pickSxList.size();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(pickSxList.get(i));
            if (i != length - 1)
            {
                stringBuilder.append('_');
            }
        }
        return stringBuilder.toString();
    }

    private void showMode(boolean isShow)
    {
        if (isShow)
            topLayout.findViewById(R.id.animal).setVisibility(View.VISIBLE);
        else
            topLayout.findViewById(R.id.animal).setVisibility(View.GONE);
    }

    @OnClick({R.id.poultry, R.id.beast})
    public void onSelectAnimal(RadioButton radioButton)
    {
        reset();
        switch (radioButton.getId())
        {
            case R.id.poultry:
                for (int i : poultryList)
                    selectAdd(i);
                notifyListener();
                break;
            case R.id.beast:
                for (int i : beastList)
                    selectAdd(i);
                notifyListener();
                break;
        }
    }

    private void selectAdd(int id)
    {
        LhcLayout lastClick = (LhcLayout) topLayout.findViewById(id);
        lastClick.setSelected(true);
        pickSxList.add(lastClick.getSx());
        notifyListener();
    }

    private void randomAdd(ArrayList<Integer> textID)
    {
        for (int i : textID)
            switch (i)
            {
                case 0:
                    selectAdd(sxList[0]);
                    break;
                case 1:
                    selectAdd(sxList[1]);
                    break;
                case 2:
                    selectAdd(sxList[2]);
                    break;
                case 3:
                    selectAdd(sxList[3]);
                    break;
                case 4:
                    selectAdd(sxList[4]);
                    break;
                case 5:
                    selectAdd(sxList[5]);
                    break;
                case 6:
                    selectAdd(sxList[6]);
                    break;
                case 7:
                    selectAdd(sxList[7]);
                    break;
                case 8:
                    selectAdd(sxList[8]);
                    break;
                case 9:
                    selectAdd(sxList[9]);
                    break;
                case 10:
                    selectAdd(sxList[10]);
                    break;
                case 11:
                    selectAdd(sxList[11]);
                    break;
            }
    }
    
    @Override
    public void onClearPick(LhcGame game)
    {
        reset();
    }
}
