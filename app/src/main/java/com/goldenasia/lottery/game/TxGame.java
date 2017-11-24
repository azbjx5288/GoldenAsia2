package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.google.gson.JsonArray;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 快三“通选”
 * Created by Sakura on 2017/7/3.
 */
public class TxGame extends Game
{
    private static int TYPE;
    private static int TYPE_STHDS = 1;
    private static int TYPE_SLHDS = 2;
    private TextView lastClick;
    
    public TxGame(Method method)
    {
        super(method);
        switch (method.getName())
        {
            case "JSSTTX":
                TYPE = TYPE_STHDS;
                break;
            default:
                TYPE = TYPE_SLHDS;
        }
    }
    
    @Override
    public void onInflate()
    {
        if (TYPE == TYPE_STHDS)
            LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_sthtx, topLayout, true);
        else
            LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_slhtx, topLayout, true);
        ButterKnife.bind(this, topLayout);
    }
    
    @Override
    public void reset()
    {
        if (lastClick != null)
        {
            lastClick.setSelected(false);
        }
        notifyListener();
    }
    
    @OnClick({R.id.btn_tx})
    public void onTextClick(TextView view)
    {
        if (lastClick != null)
        {
            lastClick.setSelected(false);
        }
        
        if (lastClick != view)
        {
            view.setSelected(true);
            lastClick = view;
        } else
        {
            lastClick = null;
        }
        
        notifyListener();
    }
    
    @Override
    public String getWebViewCode()
    {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(lastClick == null ? "" : lastClick.getText().toString());
        return jsonArray.toString();
    }
    
    @Override
    public String getSubmitCodes()
    {
        String submitStr = "";
        if (TYPE == TYPE_STHDS)
            submitStr = "111_222_333_444_555_666";
        else
            submitStr = "123_234_345_456";
        return lastClick == null ? "" : submitStr;
    }
    
    public void onClearPick(Game game)
    {
        if (lastClick != null)
        {
            lastClick.setSelected(false);
        }
        notifyListener();
    }
}
