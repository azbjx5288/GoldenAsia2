package com.goldenasia.lottery.game;

import android.view.LayoutInflater;

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

public class LhcTailGame extends LhcGame
{
    private ArrayList<String> pickTailList;
    private int[] tailList = new int[]{R.id.layout0, R.id.layout1, R.id.layout2, R.id.layout3, R.id.layout4, R.id
            .layout5, R.id.layout6, R.id.layout7, R.id.layout8, R.id.layout9};

    public LhcTailGame(Method method)
    {
        super(method);
        pickTailList = new ArrayList<>();
    }

    @Override
    public void onInflate()
    {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_lhc_tail, topLayout, true);
        ButterKnife.bind(this, topLayout);
    }

    @Override
    public void reset()
    {
        for (int i : tailList)
        {
            topLayout.findViewById(i).setSelected(false);
        }
        pickTailList.clear();
        notifyListener();
    }

    @OnClick({R.id.layout0, R.id.layout1, R.id.layout2, R.id.layout3, R.id.layout4, R.id.layout5, R.id.layout6, R.id
            .layout7, R.id.layout8, R.id.layout9})
    public void onLayoutClick(LhcLayout view)
    {
        if (view.isSelected())
        {
            view.setSelected(false);
            pickTailList.remove(view.getTail());
        } else
        {
            view.setSelected(true);
            pickTailList.add(view.getTail());
        }

        notifyListener();
    }

    public void onRandomCodes()
    {
        ArrayList<Integer> textid = random(0, 11, 1);
        reset();
        switch (textid.get(0))
        {
            case 0:
                selectAdd(tailList[0]);
                break;
            case 1:
                selectAdd(tailList[1]);
                break;
            case 2:
                selectAdd(tailList[2]);
                break;
            case 3:
                selectAdd(tailList[3]);
                break;
            case 4:
                selectAdd(tailList[4]);
                break;
            case 5:
                selectAdd(tailList[5]);
                break;
            case 6:
                selectAdd(tailList[6]);
                break;
            case 7:
                selectAdd(tailList[7]);
                break;
            case 8:
                selectAdd(tailList[8]);
                break;
            case 9:
                selectAdd(tailList[9]);
                break;
        }
        notifyListener();
    }

    @Override
    public String getWebViewCode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        int length = pickTailList.size();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(pickTailList.get(i));
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
        int length = pickTailList.size();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(pickTailList.get(i));
            if (i != length - 1)
            {
                stringBuilder.append('_');
            }
        }
        return stringBuilder.toString();
    }

    private void selectAdd(int id)
    {
        LhcLayout lastClick = (LhcLayout) topLayout.findViewById(id);
        lastClick.setSelected(true);
        pickTailList.add(lastClick.getTail());
        notifyListener();
    }
    
    @Override
    public void onClearPick(LhcGame game)
    {
        reset();
    }
}
