package com.goldenasia.lottery.game;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.ManualInputView;
import com.goldenasia.lottery.pattern.OnAddListner;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * 北京PK10数字玩法
 * Created by Sakura on 2016/10/20.
 */
public class Pk10CommonGame extends Game {
    private static boolean isGYHZ;
    private static Pk10CommonGameUtils2 mPk10CommonGameUtils2 = new Pk10CommonGameUtils2();

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
                    ArrayList<String> codeArray = dealDiffPlay(chooseArray);

                    setSubmitArray(codeArray);
                    setSingleNum(codeArray.size());
                    onManualEntryListener.onManualEntry(codeArray.size());
                }
            });
        }
    }

    /*处理不同玩法的手工录入*/
    private ArrayList<String> dealDiffPlay(ArrayList<String[]> chooseArray) {

        Pk10CommonGameUtils pk10CommonGameUtils = new Pk10CommonGameUtils();

        switch (method.getName()) {
            case "HEMZX": //后二名直选
                return pk10CommonGameUtils.HEMZX(chooseArray);
            case "HEMZUX": //后二名组选
                return pk10CommonGameUtils.HEMZUX(chooseArray);
            case "QEMZX": //前二名直选  每一注 不能有 相同的 例如：1，1
                return pk10CommonGameUtils.HEMZX(chooseArray);
            case "QEMZUX": //前二名组选
                return pk10CommonGameUtils.HEMZUX(chooseArray);

            case "HSMZX": //后三名直选
                return pk10CommonGameUtils.HSMZX(chooseArray);
            case "HSMZL": //后三名组六
                return pk10CommonGameUtils.HSMZL(chooseArray);
            case "QSMZX": //前三名直选
                return pk10CommonGameUtils.HSMZX(chooseArray);
            case "QSMZL": //前三名组六
                return pk10CommonGameUtils.HSMZL(chooseArray);

            case "HSIMZX": //后四名直选
                return pk10CommonGameUtils.HSIMZX(chooseArray);
            case "HSIMZUX": //后四名组选
                return pk10CommonGameUtils.HSIMZUX(chooseArray);
            case "QSIMZX": //前四名直选
                return pk10CommonGameUtils.HSIMZX(chooseArray);
            case "QSIMZUX": //前四名组选
                return pk10CommonGameUtils.HSIMZUX(chooseArray);

            case "HWMZX"://后五名直选
                return pk10CommonGameUtils.HWMZX(chooseArray);
            case "HWMZUX": //后五名组选
                return pk10CommonGameUtils.HWMZUX(chooseArray);
            case "QWMZX": //前五名直选
                return pk10CommonGameUtils.HWMZX(chooseArray);
            case "QWMZUX": //前五名组选
                return pk10CommonGameUtils.HWMZUX(chooseArray);

            case "QSMBDW"://前三名不定位
                return pk10CommonGameUtils.QSMBDW(chooseArray);
            case "HSMBDW"://后三名不定位
                return pk10CommonGameUtils.QSMBDW(chooseArray);

            case "CCW"://猜车位
                return pk10CommonGameUtils.CCW(chooseArray);
            case "QWMC"://猜前五 QWMC
                return pk10CommonGameUtils.QWMC(chooseArray);
            case "HWMC":////猜后五 HWMC
                return pk10CommonGameUtils.QWMC(chooseArray);


            default:
                ArrayList<String> codeArray = new ArrayList<>();
                for (int i = 0; i < chooseArray.size(); i++) {
                    StringBuilder codeBuilder = new StringBuilder();
                    for (int j = 0, length = chooseArray.get(i).length; j < length; j++) {
                        String charChoose = chooseArray.get(i)[j];

                        if (!NumbericUtils.isNumericChar(charChoose)) {
                            codeArray.clear();
                            return codeArray;
                        } else {
                            codeBuilder.append(charChoose);
                            if (j != length - 1) {
                                codeBuilder.append(",");
                            }
                        }

                    }
                    codeArray.add(codeBuilder.toString());
                }
                return codeArray;
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

    private static void createPicklayout(Game game, String[] name, String gameMethod) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }

        addViewLayout(game, views, gameMethod);

        game.setColumn(name.length);
    }

    //冠亚和 GYHZ
    public static void GYHZ(Game game) {
        createPicklayout(game, new String[]{"冠亚和"}, ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //猜前五 QWMC
    public static void QWMC(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名"}, "QWMC");
        game.setSupportInput(true);
    }

    //猜后五 HWMC
    public static void HWMC(Game game) {
        createPicklayout(game, new String[]{"第六名", "第七名", "第八名", "第九名", "第十名"}, "HWMC");
        game.setSupportInput(true);
    }

    //猜车位 CCW
    public static void CCW(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名", "第六名", "第七名", "第八名", "第九名", "第十名"}, "CCW");
        game.setSupportInput(true);
    }

    //前二名直选 QEMZX
    public static void QEMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军"}, "QEMZX");
        game.setSupportInput(true);
    }

    //后二名直选 HEMZX
    public static void HEMZX(Game game) {
        createPicklayout(game, new String[]{"第九名", "第十名"}, "HEMZX");
        game.setSupportInput(true);
    }

    //前二名组选 QEMZUX
    public static void QEMZUX(Game game) {
        createPicklayout(game, new String[]{"前二"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //后二名组选 HEMZUX
    public static void HEMZUX(Game game) {
        createPicklayout(game, new String[]{"后二"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //前三名直选 QSMZX
    public static void QSMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军"}, "QSMZX");
        game.setSupportInput(true);
    }

    //后三名直选 HSMZX
    public static void HSMZX(Game game) {
        createPicklayout(game, new String[]{"第八名", "第九名", "第十名"}, "HSMZX");
        game.setSupportInput(true);
    }

    //前三名组六 QSMZL
    public static void QSMZL(Game game) {
        createPicklayout(game, new String[]{"前三"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //后三名组六 HSMZL
    public static void HSMZL(Game game) {
        createPicklayout(game, new String[]{"后三"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //前四名直选 QSIMZX
    public static void QSIMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名"}, "QSIMZX");
        game.setSupportInput(true);
    }

    //后四名直选 HSIMZX
    public static void HSIMZX(Game game) {
        createPicklayout(game, new String[]{"第七名", "第八名", "第九名", "第十名"}, "HSIMZX");
        game.setSupportInput(true);
    }

    //前四名组选 QSIMZUX
    public static void QSIMZUX(Game game) {
        createPicklayout(game, new String[]{"前四"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //后四名组选 HSIMZUX
    public static void HSIMZUX(Game game) {
        createPicklayout(game, new String[]{"后四"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //前五名直选 QWMZX
    public static void QWMZX(Game game) {
        createPicklayout(game, new String[]{"冠军", "亚军", "季军", "第四名", "第五名"}, "QWMZX");
        game.setSupportInput(true);
    }

    //后五名直选 HWMZX
    public static void HWMZX(Game game) {
        createPicklayout(game, new String[]{"第六名", "第七名", "第八名", "第九名", "第十名"}, "HWMZX");
        game.setSupportInput(true);
    }

    //前五名组选 QWMZUX
    public static void QWMZUX(Game game) {
        createPicklayout(game, new String[]{"前五"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //后五名组选 HWMZUX
    public static void HWMZUX(Game game) {
        createPicklayout(game, new String[]{"后五"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //前三名不定位 QSMBDW
    public static void QSMBDW(Game game) {
        createPicklayout(game, new String[]{"前三"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //后三名不定位 HSMBDW
    public static void HSMBDW(Game game) {
        createPicklayout(game, new String[]{"后三"}, ConstantInformation.NO_YILOU_AND_LENGRE);
        game.setSupportInput(true);
    }

    //猜和值
    public static void CHZ(Game game) {
        isGYHZ = true;
        createPicklayout(game, new String[]{"猜和值"}, ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    /**
     * ============================================手工录入start=============================================================
     **/
    //猜前五 QWMC
    public static void QWMCInput(Game game) {
        addInputLayout(game, 100);
    }

    //猜后五 HWMC
    public static void HWMCInput(Game game) {
        addInputLayout(game, 100);
    }

    //猜车位 CCW
    public static void CCWInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前二名直选 QEMZX
    public static void QEMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //后二名直选 HEMZX
    public static void HEMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前二名组选 QEMZUX
    public static void QEMZUXInput(Game game) {
        addInputLayout(game, 2);
    }

    //后二名组选 HEMZUX
    public static void HEMZUXInput(Game game) {
        addInputLayout(game, 2);
    }

    //前三名直选 QSMZX
    public static void QSMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //后三名直选 HSMZX
    public static void HSMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前三名组六 QSMZL
    public static void QSMZLInput(Game game) {
        addInputLayout(game, 3);
    }

    //后三名组六 HSMZL
    public static void HSMZLInput(Game game) {
        addInputLayout(game, 3);
    }

    //前四名直选 QSIMZX
    public static void QSIMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //后四名直选 HSIMZX
    public static void HSIMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前四名组选 QSIMZUX
    public static void QSIMZUXInput(Game game) {
        addInputLayout(game, 4);
    }

    //后四名组选 HSIMZUX
    public static void HSIMZUXInput(Game game) {
        addInputLayout(game, 4);
    }

    //前五名直选 QWMZX
    public static void QWMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //后五名直选 HWMZX
    public static void HWMZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前五名组选 QWMZUX
    public static void QWMZUXInput(Game game) {
        addInputLayout(game, 5);
    }

    //后五名组选 HWMZUX
    public static void HWMZUXInput(Game game) {
        addInputLayout(game, 5);
    }

    //前三名不定位 QSMBDW
    public static void QSMBDWInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //后三名不定位 HSMBDW
    public static void HSMBDWInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    /**
     * ============================================手工录入end=============================================================
     **/

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

    /*====================================处理冷热遗漏添加的方法start=========================================================================*/
    private static void addViewLayout(Game game, View[] views, String gameMethod) {
        ViewGroup topLayout = game.getTopLayout();

        if (ConstantInformation.HISTORY_CODE_LIST.size() == 0 || gameMethod == ConstantInformation.NO_YILOU_AND_LENGRE) {//没有冷热和遗漏的情况
            ConstantInformation.YI_LOU_IS_SUPPORTED = false;
            ConstantInformation.YI_LOU_IS_SHOW = false;
            ConstantInformation.LENG_RE_IS_SHOW = false;

            for (View view : views) {
                topLayout.addView(view);
            }
        } else {
            ConstantInformation.YI_LOU_IS_SUPPORTED = true;
            ConstantInformation.YI_LOU_IS_SHOW = false;
            ConstantInformation.LENG_RE_IS_SHOW = false;
            ConstantInformation.LENG_RE_COUNT = 100;
            addViewLayoutHasLengRe(topLayout, views, gameMethod);
        }
    }

    private static void addViewLayoutHasLengRe(ViewGroup topLayout, View[] views, String gameMethod) {
        View sscLengreLayout = LayoutInflater.from(topLayout.getContext()).inflate(R.layout.ssc_lengre_layout, null, false);
        CheckBox yilou_tv = sscLengreLayout.findViewById(R.id.yilou);
//        CheckBox yilou_Checkbox = sscLengreLayout.findViewById(R.id.yilou_checked);

        CheckBox lengre_tv = sscLengreLayout.findViewById(R.id.lengre);
//        CheckBox lengre_Checkbox = sscLengreLayout.findViewById(R.id.lengre_checked);

        yilou_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.YI_LOU_IS_SHOW = !ConstantInformation.YI_LOU_IS_SHOW;

                yilou_tv.setChecked(ConstantInformation.YI_LOU_IS_SHOW);
//                yilou_Checkbox.setChecked(ConstantInformation.YI_LOU_IS_SHOW);

                for (View view : views) {
                    NumberGroupView numberGroupView = view.findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.refreshViewGroup();
                }
            }
        });
        /*yilou_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.YI_LOU_IS_SHOW = !ConstantInformation.YI_LOU_IS_SHOW;

                yilou_tv.setChecked(ConstantInformation.YI_LOU_IS_SHOW);
                yilou_Checkbox.setChecked(ConstantInformation.YI_LOU_IS_SHOW);

                for (View view : views) {
                    NumberGroupView numberGroupView = view.findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.refreshViewGroup();
                }
            }
        });*/

        /*lengre_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengre_tv.setChecked(true);
                initLengrePopupwindow(views, topLayout.getContext(), v, gameMethod);
            }
        });*/

        lengre_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.LENG_RE_IS_SHOW = !ConstantInformation.LENG_RE_IS_SHOW;
                lengre_tv.setChecked(ConstantInformation.LENG_RE_IS_SHOW);

                //选中了冷热的复选框就会弹出冷热期数对话框
                if (ConstantInformation.LENG_RE_IS_SHOW) {
                    initLengrePopupwindow(views, topLayout.getContext(), lengre_tv, gameMethod);
                }

                for (View view : views) {
                    NumberGroupView numberGroupView = view.findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.refreshViewGroup();
                }
            }
        });

        topLayout.addView(sscLengreLayout);

        for (int i = 0; i < views.length; i++) {
            NumberGroupView numberGroupView = views[i].findViewById(R.id.pick_column_NumberGroupView);
            numberGroupView.setmYiLouList(mPk10CommonGameUtils2.getYiLouList(gameMethod, i));
            numberGroupView.setmLengReList(mPk10CommonGameUtils2.getLengReList(gameMethod, i));

            numberGroupView.refreshViewGroup();
            topLayout.addView(views[i]);
        }
    }

    //弹出冷热期数选择框
    private static void initLengrePopupwindow(View[] views, Context context, View v, String gameMethod) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.lengre_diff_popupwindow, null);

        BubbleLinearLayout bubbleLinearLayout = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        BubblePopupWindow bubblePopupWindow = new BubblePopupWindow(rootView, bubbleLinearLayout);
        RadioButton lengreOne = (RadioButton) rootView.findViewById(R.id.lengre_one);
        RadioButton lengreTwo = (RadioButton) rootView.findViewById(R.id.lengre_two);
        RadioButton lengreThree = (RadioButton) rootView.findViewById(R.id.lengre_three);

        switch (ConstantInformation.LENG_RE_COUNT) {
            case 20:
                lengreThree.setChecked(true);
                break;
            case 50:
                lengreTwo.setChecked(true);
                break;
            default:
                lengreOne.setChecked(true);
                break;
        }

        lengreOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConstantInformation.LENG_RE_COUNT = 100;
                for (int i = 0; i < views.length; i++) {
                    NumberGroupView numberGroupView = views[i].findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.setmYiLouList(mPk10CommonGameUtils2.getYiLouList(gameMethod, i));
                    numberGroupView.setmLengReList(mPk10CommonGameUtils2.getLengReList(gameMethod, i));
                    numberGroupView.refreshViewGroup();
                }

                bubblePopupWindow.dismiss();
            }
        });
        lengreTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.LENG_RE_COUNT = 50;
                for (int i = 0; i < views.length; i++) {
                    NumberGroupView numberGroupView = views[i].findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.setmYiLouList(mPk10CommonGameUtils2.getYiLouList(gameMethod, i));
                    numberGroupView.setmLengReList(mPk10CommonGameUtils2.getLengReList(gameMethod, i));
                    numberGroupView.refreshViewGroup();
                }
                bubblePopupWindow.dismiss();
            }
        });
        lengreThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.LENG_RE_COUNT = 20;
                for (int i = 0; i < views.length; i++) {
                    NumberGroupView numberGroupView = views[i].findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.setmYiLouList(mPk10CommonGameUtils2.getYiLouList(gameMethod, i));
                    numberGroupView.setmLengReList(mPk10CommonGameUtils2.getLengReList(gameMethod, i));

                    numberGroupView.refreshViewGroup();
                }
                bubblePopupWindow.dismiss();
            }
        });
        bubblePopupWindow.showArrowTo(v, BubbleStyle.ArrowDirection.Down);
    }
    /*====================================处理冷热遗漏添加的方法end=========================================================================*/
}
