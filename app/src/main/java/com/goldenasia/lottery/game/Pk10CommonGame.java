package com.goldenasia.lottery.game;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.ManualInputView;
import com.goldenasia.lottery.pattern.OnAddListner;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * 北京PK10数字玩法
 * Created by Sakura on 2016/10/20.
 */
public class Pk10CommonGame extends Game {
    private static boolean isGYHZ;

    public Pk10CommonGame(Method method) {
        super(method);
        if ("GYHZ".equals(method.getName()))
            isGYHZ = true;
        else
            isGYHZ = false;
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

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public static void addInputLayout(Game game, int column) {
        ViewGroup manualInput = game.getManualInput();
        View view = createManualInputLayout(manualInput);
        ManualInputView manualInputView = new ManualInputView(view, game.lottery, column);
        game.addManualInputView(manualInputView);
        manualInput.addView(view);
    }

    //手工录入
    public void onInputInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Input", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public void displayInputView() {
        if (onManualEntryListener != null || manualInputView != null) {
            manualInputView.setOnAddListner(new OnAddListner() {
                @Override
                public void onAdd(ArrayList<String[]> chooseArray) {
                    ArrayList<String> codeArray = new ArrayList<>();
                    for (int i = 0, size = chooseArray.size(); i < size; i++) {
                        StringBuilder codeBuilder = new StringBuilder();
                        for (int j = 0, length = chooseArray.get(i).length; j < length; j++) {
                            codeBuilder.append(chooseArray.get(i)[j]);
                            if (j != length - 1) {
                                codeBuilder.append(",");
                            }
                        }
                        codeArray.add(codeBuilder.toString());
                    }
                    setSubmitArray(codeArray);
                    setSingleNum(codeArray.size());
                    onManualEntryListener.onManualEntry(codeArray.size());
                }
            });
        }
    }


    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        game.addPickNumber(pickNumber2);
    }

    public static View createManualInputLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.popup_write_comment, null, false);
    }


    public static View createDefaultPickLayout(ViewGroup container) {
        if (!isGYHZ)
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_pk10, null, false);
        else
            return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_pk10_gyhz, null, false);
    }

    private static void createPicklayout(Game game, String[] name) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
        game.setColumn(name.length);
    }

    //冠亚和 GYHZ
    public static void GYHZ(Game game) {
        createPicklayout(game, new String[]{"冠亚和"});
    }

    //猜前五 QWMC
    public static void QWMC(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名"});
        //game.setSupportInput(true);
    }

    //猜后五 HWMC
    public static void HWMC(Game game) {
        createPicklayout(game, new String[]{"第六名", "第七名", "第八名", "第九名", "第十名"});
        //game.setSupportInput(true);
    }

    //猜车位 CCW
    public static void CCW(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"});
        //game.setSupportInput(true);
    }

    //前二名直选 QEMZX
    public static void QEMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军"});
        //game.setSupportInput(true);
    }

    //后二名直选 HEMZX
    public static void HEMZX(Game game) {
        createPicklayout(game, new String[]{"第九名", "第十名"});
        //game.setSupportInput(true);
    }

    //前二名组选 QEMZUX
    public static void QEMZUX(Game game) {
        createPicklayout(game, new String[]{"前二"});
        //game.setSupportInput(true);
    }

    //后二名组选 HEMZUX
    public static void HEMZUX(Game game) {
        createPicklayout(game, new String[]{"后二"});
        //game.setSupportInput(true);
    }

    //前三名直选 QSMZX
    public static void QSMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军"});
        //game.setSupportInput(true);
    }

    //后三名直选 HSMZX
    public static void HSMZX(Game game) {
        createPicklayout(game, new String[]{"第八名", "第九名", "第十名"});
        //game.setSupportInput(true);
    }

    //前三名组六 QSMZL
    public static void QSMZL(Game game) {
        createPicklayout(game, new String[]{"前三"});
        //game.setSupportInput(true);
    }

    //后三名组六 HSMZL
    public static void HSMZL(Game game) {
        createPicklayout(game, new String[]{"后三"});
        //game.setSupportInput(true);
    }

    //前四名直选 QSIMZX
    public static void QSIMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名"});
        //game.setSupportInput(true);
    }

    //后四名直选 HSIMZX
    public static void HSIMZX(Game game) {
        createPicklayout(game, new String[]{"第七名", "第八名", "第九名", "第十名"});
        //game.setSupportInput(true);
    }

    //前四名组选 QSIMZUX
    public static void QSIMZUX(Game game) {
        createPicklayout(game, new String[]{"前四"});
        //game.setSupportInput(true);
    }

    //后四名组选 HSIMZUX
    public static void HSIMZUX(Game game) {
        createPicklayout(game, new String[]{"后四"});
        //game.setSupportInput(true);
    }

    //前五名直选 QWMZX
    public static void QWMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名"});
        //game.setSupportInput(true);
    }

    //后五名直选 HWMZX
    public static void HWMZX(Game game) {
        createPicklayout(game, new String[]{"第六名", "第七名", "第八名", "第九名", "第十名"});
        //game.setSupportInput(true);
    }

    //前五名组选 QWMZUX
    public static void QWMZUX(Game game) {
        createPicklayout(game, new String[]{"前五"});
        //game.setSupportInput(true);
    }

    //后五名组选 HWMZUX
    public static void HWMZUX(Game game) {
        createPicklayout(game, new String[]{"后五"});
        //game.setSupportInput(true);
    }

    //前三名不定位 QSMBDW
    public static void QSMBDW(Game game) {
        createPicklayout(game, new String[]{"前三"});
        //game.setSupportInput(true);
    }

    //后三名不定位 HSMBDW
    public static void HSMBDW(Game game) {
        createPicklayout(game, new String[]{"后三"});
        //game.setSupportInput(true);
    }

    //猜和值
    public static void CHZ(Game game) {
        isGYHZ = true;
        createPicklayout(game, new String[]{"猜和值"});
    }

    /*机选*/
    //猜前五 QWMC
    public static void QWMCRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 10, 1));
        game.notifyListener();
    }

    public static void OTHERRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers) {
            pickNumber.onRandom(random(0, 9, 7));
        }
    }
}
