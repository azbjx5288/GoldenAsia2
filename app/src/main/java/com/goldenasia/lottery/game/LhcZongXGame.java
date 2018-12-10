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
 * 六合彩总肖玩法
 * Created by gan on 2017/11/13.
 */

public class LhcZongXGame extends LhcGame {
    private ArrayList<CharSequence> pickTailList;
    private int[] tailList = new int[]{R.id.textView01, R.id.textView02, R.id.textView03, R.id.textView04, R.id.textView05, R.id
            .textView06};

    public LhcZongXGame(Method method) {
        super(method);
        pickTailList = new ArrayList<>();
    }

    @Override
    public void onInflate() {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_lhc_zongx, topLayout, true);
        ButterKnife.bind(this, topLayout);
    }

    @Override
    public void reset() {
        for (int i : tailList) {
            topLayout.findViewById(i).setSelected(false);
        }
        pickTailList.clear();
        notifyListener();
    }

    @OnClick({R.id.textView01, R.id.textView02, R.id.textView03, R.id.textView04, R.id.textView05, R.id
            .textView06})
    public void onLayoutClick(TextView view) {
        if (view.isSelected()) {
            view.setSelected(false);
            pickTailList.remove(view.getText());
        } else {
            view.setSelected(true);
            pickTailList.add(view.getText());
        }

        notifyListener();
    }


    @Override
    public String getWebViewCode() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = pickTailList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(pickTailList.get(i));
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
        int length = pickTailList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(pickTailList.get(i));
            if (i != length - 1) {
                stringBuilder.append('_');
            }
        }
        return stringBuilder.toString();
    }


    @Override
    public void onClearPick(LhcGame game) {
        reset();
    }
}
