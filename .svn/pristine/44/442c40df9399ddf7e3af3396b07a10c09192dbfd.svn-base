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
import com.goldenasia.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * Created by ACE-PC on 2016/2/19.
 */
public class Fc3dCommonGame extends Game {

    public Fc3dCommonGame(Method method) {
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
            jsonArray.add(transform(pickNumber.getCheckedNumber(), true, true));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, true));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        game.addPickNumber(pickNumber2);
    }


    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    public static View createManualInputLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.popup_write_comment, null, false);
    }

    public static void addInputLayout(Game game, int column) {
        ViewGroup manualInput = game.getManualInput();
        View view = createManualInputLayout(manualInput);
        ManualInputView manualInputView = new ManualInputView(view, game.lottery, column);
        game.addManualInputView(manualInputView);
        manualInput.addView(view);
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

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
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
                    int notes = 0;
                    if (getMethod().getName().equals("WXDW")) {
                        for (String[] wxdw : chooseArray) {
                            for (String d : wxdw) {
                                if (!d.equals("-")) {
                                    notes += 1;
                                }
                            }
                        }
                    } else {
                        notes = chooseArray.size();
                    }
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
                    setSingleNum(notes);
                    onManualEntryListener.onManualEntry(notes);
                }
            });
        }
    }


    //直选 SXZX
    public static void SXZX(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"});
        game.setSupportInput(true);
    }

    //直选和值 SXHZ public static void SXHZ(Game game) {}

    //组三 SXZS
    public static void SXZS(Game game) {
        createPicklayout(game, new String[]{"组三"});
    }

    //组六 SXZL
    public static void SXZL(Game game) {
        createPicklayout(game, new String[]{"组六"});
    }

    //混合组选 SXHHZX public static void SXHHZX(Game game) {}
    //组选和值 SXZXHZ public static void SXZXHZ(Game game) {}

    //一码不定位 YMBDW
    public static void YMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //二码不定位 EMBDW
    public static void EMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //前二直选 QEZX
    public static void QEZX(final Game game) {
        createPicklayout(game, new String[]{"百位", "十位"});
        game.setSupportInput(true);
    }

    //前二组选 QEZUX
    public static void QEZUX(Game game) {
        createPicklayout(game, new String[]{"前二组选"});
        final PickNumber pickNumber = game.pickNumbers.get(0);
        pickNumber.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick() {
                ArrayList<Integer> numList = pickNumber.getCheckedNumber();
                if (numList.size() > 7) {
                    game.onCustomDialog("当前最多只能选择7个号码");
                    OTHERRandom(game);
                }
                game.notifyListener();
            }
        });
    }

    //后二直选 EXZX
    public static void EXZX(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"});
        game.setSupportInput(true);
    }

    //后二组选 EXZUX
    public static void EXZUX(final Game game) {
        createPicklayout(game, new String[]{"后二组选"});
        final PickNumber pickNumber = game.pickNumbers.get(0);
        pickNumber.setChooseItemClickListener(() -> {
            ArrayList<Integer> numList = pickNumber.getCheckedNumber();
            if (numList.size() > 7) {
                game.onCustomDialog("当前最多只能选择7个号码");
                OTHERRandom(game);
            }
            game.notifyListener();
        });
    }

    //定位胆 SXDW
    public static void SXDW(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"});
    }

    /**
     * ==========================================手工录入===================================================
     */
    //直选 SXZX
    public static void SXZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前二直选 QEZX
    public static void QEZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //后二直选 EXZX
    public static void EXZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //直选随机 SXZX
    public static void SXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //组三随机 SXZS
    public static void SXZSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //组六随机 SXZL
    public static void SXZLRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 3));
        game.notifyListener();
    }

    //前二直选随机 QEZX
    public static void QEZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //前二组选随机 QEZUX
    public static void QEZUXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //一码不定位随机 YMBDW
    public static void YMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //二码不定位随机 EMBDW
    public static void EMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //后二直选随机 EXZX
    public static void EXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //后二组选随机 EXZUX
    public static void EXZUXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //定位胆随机 SXDW
    public static void SXDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    public static void OTHERRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers) {
            pickNumber.onRandom(random(0, 9, 7));
        }
    }
}
