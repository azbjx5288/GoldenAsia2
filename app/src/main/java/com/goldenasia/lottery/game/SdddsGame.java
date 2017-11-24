package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 11选5“定单双, SDDDS”
 * Created by User on 2016/2/23.
 */
public class SdddsGame extends Game {

    private TextView lastClick;

    public SdddsGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.sddds, topLayout, true);
        ButterKnife.bind(this, topLayout);
    }

    @Override
    public void reset() {
        if (lastClick != null) {
            lastClick.setSelected(false);
        }
        notifyListener();
    }

    @OnClick({R.id.sddds_0, R.id.sddds_1, R.id.sddds_2, R.id.sddds_3, R.id.sddds_4, R.id.sddds_5})
    public void onTextClick(TextView view) {
        if (lastClick != null) {
            lastClick.setSelected(false);
        }

        if (lastClick != view) {
            view.setSelected(true);
            lastClick = view;
        } else {
            lastClick = null;
        }

        notifyListener();
    }

    public void onRandomCodes() {
        ArrayList<Integer> textid = random(0, 6, 1);
        if (lastClick != null) {
            lastClick.setSelected(false);
        }
        switch (textid.get(0)) {
            case 0:
                lastClick = (TextView) topLayout.findViewById(R.id.sddds_0);
                lastClick.setSelected(true);
                break;
            case 1:
                lastClick = (TextView) topLayout.findViewById(R.id.sddds_1);
                lastClick.setSelected(true);
                break;
            case 2:
                lastClick = (TextView) topLayout.findViewById(R.id.sddds_2);
                lastClick.setSelected(true);
                break;
            case 3:
                lastClick = (TextView) topLayout.findViewById(R.id.sddds_3);
                lastClick.setSelected(true);
                break;
            case 4:
                lastClick = (TextView) topLayout.findViewById(R.id.sddds_4);
                lastClick.setSelected(true);
                break;
            case 5:
                lastClick = (TextView) topLayout.findViewById(R.id.sddds_5);
                lastClick.setSelected(true);
                break;
        }
        notifyListener();
    }

    @Override
    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(lastClick == null ? "" : lastClick.getText().toString());
        return jsonArray.toString();
    }

    @Override
    public String getSubmitCodes() {
        return lastClick == null ? "" : lastClick.getText().toString();
    }

    public void onClearPick(Game game) {
        if (lastClick != null) {
            lastClick.setSelected(false);
        }
        notifyListener();
    }
}
