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

public class LhcColorGame extends LhcGame {
    private ArrayList<String> pickColorList;
    private int[] colorList = new int[]{R.id.layout_red, R.id.layout_blue, R.id.layout_green};

    public LhcColorGame(Method method) {
        super(method);
        pickColorList = new ArrayList<>();
    }

    @Override
    public void onInflate() {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_lhc_color, topLayout, true);
        ButterKnife.bind(this, topLayout);
    }

    @Override
    public void reset() {
        for (int i : colorList) {
            topLayout.findViewById(i).setSelected(false);
        }
        pickColorList.clear();
        notifyListener();
    }

    @OnClick({R.id.layout_red, R.id.layout_blue, R.id.layout_green})
    public void onLayoutClick(LhcLayout view) {
        if (view.isSelected()) {
            view.setSelected(false);
            pickColorList.remove(view.getColor());
        } else {
            view.setSelected(true);
            pickColorList.add(view.getColor());
        }

        notifyListener();
    }

    public void onRandomCodes() {
        ArrayList<Integer> textid = random(0, 2, 1);
        reset();
        switch (textid.get(0)) {
            case 0:
                selectAdd(colorList[0]);
                break;
            case 1:
                selectAdd(colorList[1]);
                break;
            case 2:
                selectAdd(colorList[2]);
                break;
        }
        notifyListener();
    }

    @Override
    public String getWebViewCode() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = pickColorList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(pickColorList.get(i));
            if (i != length - 1) {
                stringBuilder.append('_');
            }
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());
        return jsonArray.toString();
    }

    @Override
    public String getSubmitCodes() {

        StringBuilder stringBuilder = new StringBuilder();
        int length = pickColorList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(pickColorList.get(i));
            if (i != length - 1) {
                stringBuilder.append('_');
            }
        }
        return stringBuilder.toString();
    }

    private void selectAdd(int id) {
        LhcLayout lastClick = (LhcLayout) topLayout.findViewById(id);
        lastClick.setSelected(true);
        pickColorList.add(lastClick.getColor());
        notifyListener();
    }

    @Override
    public void onClearPick(LhcGame game) {
        reset();
    }
}
