package com.goldenasia.lottery.game;

import android.util.Log;
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
 * Created by ACE-PC on 2016/2/19.
 */
public class P3p5CommonGame extends Game {

    public P3p5CommonGame(Method method) {
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
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, false));
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
                    for (int i =0,size= chooseArray.size(); i<size; i++) {
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


    //直选 QSZX
    public static void QSZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"});
        game.setSupportInput(true);
    }

    //组选和值 QSZXHZ public static void QSZXHZ(Game game) {}
    //组三 QSZS
    public static void QSZS(Game game) {
        createPicklayout(game, new String[]{"组三"});
    }

    //组六 QSZL
    public static void QSZL(Game game) {
        createPicklayout(game, new String[]{"组六"});
    }

    //前二直选 QEZX
    public static void QEZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位"});
        game.setSupportInput(true);
    }

    //前二组选 QEZUX
    public static void QEZUX(final Game game) {
        createPicklayout(game, new String[]{"前二组选"});
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

    //一码不定位 QSYMBDW
    public static void QSYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //二码不定位 QSEMBDW
    public static void QSEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
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

    //定位胆 WXDW
    public static void WXDW(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
    }

     //五星直选 WXZX
     public static void WXZX(Game game) {
         createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
         game.setSupportInput(true);
     }
    //五星连选 WXLX
     public static void WXLX(Game game) {
         createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
         game.setSupportInput(true);
     }
    //组选120 ZUX120
     public static void ZUX120(Game game) {
         createPicklayout(game, new String[]{"组选120"});
     }
    //组选60 ZUX60
     public static void ZUX60(Game game) {
         createPicklayout(game, new String[]{"二重号位", "单号位"});
     }
    //组选30 ZUX30
     public static void ZUX30(Game game) {
         createPicklayout(game, new String[]{"二重号位", "单号位"});
     }
    //组选20 ZUX20
     public static void ZUX20(Game game) {
         createPicklayout(game, new String[]{"三重号位", "单号位"});
     }
    //组选10 ZUX10
     public static void ZUX10(Game game) {
         createPicklayout(game, new String[]{"三重号位", "二重号位"});
     }
    //组选5 ZUX5
     public static void ZUX5(Game game) {
         createPicklayout(game, new String[]{"四重号位", "单号位"});
     }

    /**====================================手工录入=======================================================*/
    //直选 QSZX
     public static void QSZXInput(Game game) {
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
    //五星直选 WXZX
    public static void WXZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //五星连选 WXLX
    public static void WXLXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //直选随机 QSZX
    public static void QSZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //组三随机 QSZS
    public static void QSZSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //组六随机 QSZL
    public static void QSZLRandom(Game game) {
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

    //一码不定位随机 QSYMBDW
    public static void QSYMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //二码不定位随机 QSEMBDW
    public static void QSEMBDWRandom(Game game) {
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

    //定位胆随机 WXDW
    public static void WXDWRandom(Game game) {
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
