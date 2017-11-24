package com.goldenasia.lottery.game;

import android.text.Html;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.CowLayout;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.goldenasia.lottery.R.id.cow_cow;
import static com.goldenasia.lottery.R.id.others;

/**
 * 六合彩生肖玩法
 * Created by Sakura on 2016/9/15.
 */

public class CowCowGame extends Game
{
    @Bind(cow_cow)
    TextView cowCow;
    @Bind(R.id.cow_1)
    TextView cow1;
    @Bind(R.id.cow_2)
    TextView cow2;
    @Bind(R.id.cow_3)
    TextView cow3;
    @Bind(R.id.cow_4)
    TextView cow4;
    @Bind(R.id.cow_5)
    TextView cow5;
    @Bind(R.id.cow_6)
    TextView cow6;
    @Bind(R.id.cow_7)
    TextView cow7;
    @Bind(R.id.cow_8)
    TextView cow8;
    @Bind(R.id.cow_9)
    TextView cow9;
    @Bind(R.id.other)
    TextView other;
    private ArrayList<String> pickCowList;
    private int[] cowList = new int[]{R.id.cowcow, R.id.cow1, R.id.cow2, R.id.cow3, R.id.cow4, R.id.cow5, R.id.cow6,
            R.id.cow7, R.id.cow8, R.id.cow9, others};
    
    public CowCowGame(Method method)
    {
        super(method);
        pickCowList = new ArrayList<>();
    }
    
    @Override
    public void onInflate()
    {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_cow_cow, topLayout, true);
        ButterKnife.bind(this, topLayout);
        init();
    }
    
    private void init()
    {
        cowCow.setText(Html.fromHtml("例：<font color='#e84F4F'>127</font>19"));
        cow1.setText(Html.fromHtml("例：<font color='#e84F4F'>1</font>0<font color='#e84F4F'>7</font>1<font " +
                "color='#e84F4F'>2</font>"));
        cow2.setText(Html.fromHtml("例：<font color='#e84F4F'>1</font>0<font color='#e84F4F'>7</font>2<font " +
                "color='#e84F4F'>2</font>"));
        cow3.setText(Html.fromHtml("例：<font color='#e84F4F'>2</font>1<font color='#e84F4F'>35</font>2"));
        cow4.setText(Html.fromHtml("例：<font color='#e84F4F'>1</font>0<font color='#e84F4F'>7</font>4<font " +
                "color='#e84F4F'>2</font>"));
        cow5.setText(Html.fromHtml("例：<font color='#e84F4F'>1</font>3<font color='#e84F4F'>7</font>2<font " +
                "color='#e84F4F'>2</font>"));
        cow6.setText(Html.fromHtml("例：<font color='#e84F4F'>000</font>06"));
        cow7.setText(Html.fromHtml("例：<font color='#e84F4F'>1</font>5<font color='#e84F4F'>7</font>2<font " +
                "color='#e84F4F'>2</font>"));
        cow8.setText(Html.fromHtml("例：<font color='#e84F4F'>1</font>0<font color='#e84F4F'>7</font>8<font " +
                "color='#e84F4F'>2</font>"));
        cow9.setText(Html.fromHtml("例：<font color='#e84F4F'>145</font>09"));
        other.setText(Html.fromHtml("例：10322"));
    }
    
    @Override
    public void reset()
    {
        for (int i : cowList)
        {
            topLayout.findViewById(i).setSelected(false);
        }
        pickCowList.clear();
        notifyListener();
    }

    public void tipDialog(String msg) {
        CustomDialog.Builder builder = new CustomDialog.Builder(topLayout.getContext());
        builder.setMessage(msg);
        builder.setTitle("温馨提示");
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("知道了", (dialog, which) -> {
            dialog.dismiss();
        });
        builder.create().show();
    }

    @OnClick({R.id.cowcow, R.id.cow1, R.id.cow2, R.id.cow3, R.id.cow4, R.id.cow5, R.id.cow6, R.id.cow7, R.id.cow8, R
            .id.cow9, others})
    public void onLayoutClick(CowLayout view)
    {
        if(!view.isSelected()&&pickCowList.size()>=10){
            tipDialog("当前最多只能选择10个号码");
            return;
        }
        if (view.isSelected())
        {
            view.setSelected(false);
            pickCowList.remove(view.getLabel());
        } else
        {
            view.setSelected(true);
            pickCowList.add(view.getLabel());
        }
        
        notifyListener();
    }

    /*public void onRandomCodes()
    {
        ArrayList<Integer> textid = random(0, 11, 1);
        reset();
        switch (textid.get(0))
        {
            case 0:
                selectAdd(cowList[0]);
                break;
            case 1:
                selectAdd(cowList[1]);
                break;
            case 2:
                selectAdd(cowList[2]);
                break;
            case 3:
                selectAdd(cowList[3]);
                break;
            case 4:
                selectAdd(cowList[4]);
                break;
            case 5:
                selectAdd(cowList[5]);
                break;
            case 6:
                selectAdd(cowList[6]);
                break;
            case 7:
                selectAdd(cowList[7]);
                break;
            case 8:
                selectAdd(cowList[8]);
                break;
            case 9:
                selectAdd(cowList[9]);
                break;
        }
        notifyListener();
    }*/
    
    @Override
    public String getWebViewCode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        int length = pickCowList.size();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(pickCowList.get(i));
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
        int length = pickCowList.size();
        for (int i = 0; i < length; i++)
        {
            stringBuilder.append(pickCowList.get(i));
            if (i != length - 1)
            {
                stringBuilder.append('_');
            }
        }
        return stringBuilder.toString();
    }

    /*private void selectAdd(int id)
    {
        View lastClick = (View) topLayout.findViewById(id);
        lastClick.setSelected(true);
        //pickCowList.add(lastClick.getExample());
        notifyListener();
    }*/
    
    @Override
    public void onClearPick(Game game)
    {
        reset();
    }
}
