package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

class SyxwSDWXHZGame  extends Game {

    public SyxwSDWXHZGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transformSpecial(pickNumber.getCheckedNumber(), false, true));
        }

        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transformSpecial(pickNumbers.get(i).getCheckedNumber(), false, false));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public static View createDefaultPickLayout(ViewGroup container) {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_11xw_hzs, null, false);
    }

    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        game.addPickNumber(pickNumber2);
    }

    private static void addViewLayout(Game game, View[] views) {
        ViewGroup topLayout = game.getTopLayout();

        for (View view : views) {
            topLayout.addView(view);
        }
    }

    private static void createPicklayout(Game game, String[] name) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }

        addViewLayout(game, views);

        game.setColumn(name.length);
    }


    //猜和值
    public static void SDWXHZ(Game game) {
        createPicklayout(game, new String[]{"和值数"});
    }

}
