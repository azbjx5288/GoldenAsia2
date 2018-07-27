package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 快三彩系的颜色玩法
 * Created by gan on 2017/11/28.
 */

public class KuaiSanYS extends Game
{
    private ArrayList<CharSequence> pickTailList;
    private int[] tailList = new int[]{R.id.btn_tx01, R.id.btn_tx02, R.id.btn_tx03, R.id.btn_tx04};

    public KuaiSanYS(Method method)
    {
        super(method);
        pickTailList = new ArrayList<>();
    }

    @Override
    public void onInflate()
    {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_kuaisan_ys, topLayout, true);
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

    @OnClick({R.id.btn_tx01, R.id.btn_tx02, R.id.btn_tx03, R.id.btn_tx04})
    public void onLayoutClick(TextView view)
    {
        if (view.isSelected())
        {
            view.setSelected(false);
            pickTailList.remove(view.getText());
        } else
        {
            view.setSelected(true);
            pickTailList.add(view.getText());
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
            stringBuilder.append(i);
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());
        return jsonArray.toString();
    }

    //确定按钮按下后 带到 购物车中的
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

}
