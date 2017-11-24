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
 * Created by Sakura on 2016/9/2.
 */

public class MmcCommonGame extends Game {
    private static final String TAG = MmcCommonGame.class.getSimpleName();

    public MmcCommonGame(Method method) {
        super(method);
        switch (method.getName()) {
            case "RSZS":
            case "RSZL":
                isDigital = true;
                setHasRandom(false);
                break;
            default:
                isDigital = false;
                setHasRandom(true);
        }
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
        if (isDigital)
            jsonArray.add(transformDigitJsonArray(digits));
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), true, true));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        if (isDigital)
            builder.append(transformDigit(digits));
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
        //为了 亚洲秒秒彩增加以下玩法的单式投注 而添加的
        game.setColumn(name.length);
    }

    public static View createDigitPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_digits, null, false);
    }

    private static void addViewLayout(Game game,View[] views){
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
        game.setColumn(views.length);
    }

    private static void createDigitPicklayout(Game game, String[] name,int digit) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDigitPickLayout(game.getTopLayout());
            game.initDigitPanel(view,digit);
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }
        addViewLayout(game,views);
    }


    //五星定位 WXDW
    public static void WXDW(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
    }

    //后一直选 YXZX
    public static void YXZX(Game game) {
        createPicklayout(game, new String[]{"个位"});
    }

    //后二直选: EXZX
    public static void EXZX(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"});
        game.setSupportInput(true);
    }

    //后二组选 EXZUX
    public static void EXZUX(final Game game) {
        createPicklayout(game, new String[]{"组二"});
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

    //后二连选 EXLX
    public static void EXLX(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"});
        game.setSupportInput(true);
    }

    //后三直选 SXZX
    public static void SXZX(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"});
        game.setSupportInput(true);
    }

    //后三组三 SXZS
    public static void SXZS(Game game) {
        createPicklayout(game, new String[]{"组三"});
    }

    //后三组六 SXZL
    public static void SXZL(Game game) {
        createPicklayout(game, new String[]{"组六"});
    }

    //后三连选 SXLX
    public static void SXLX(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"});
        game.setSupportInput(true);
    }


    //前二组选 QEZUX
    public static void QEZUX(final Game game) {
        createPicklayout(game, new String[]{"组二"});
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

    //前二连选 QELX
    public static void QELX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位"});
        game.setSupportInput(true);
    }


    //前二直选 QEZX
    public static void QEZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位"});
        game.setSupportInput(true);
    }

    //前三组六 QSZL
    public static void QSZL(Game game) {
        createPicklayout(game, new String[]{"组六"});
    }

    //前三连选 QSLX
    public static void QSLX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"});
        game.setSupportInput(true);
    }

    //前三直选 QSZX
    public static void QSZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"});
        game.setSupportInput(true);
    }

    //前三组三 QSZS
    public static void QSZS(Game game) {
        createPicklayout(game, new String[]{"组三"});
    }

    //中三直选 ZSZX
    public static void ZSZX(Game game) {
        createPicklayout(game, new String[]{"千位", "百位", "十位"});
        game.setSupportInput(true);
    }


    //中三组三 ZSZS
    public static void ZSZS(Game game) {
        createPicklayout(game, new String[]{"组三"});
    }

    //中三组六 ZSZL
    public static void ZSZL(Game game) {
        createPicklayout(game, new String[]{"组六"});
    }

    //中三连选 ZSLX
    public static void ZSLX(Game game) {
        createPicklayout(game, new String[]{"千位", "百位", "十位"});
        game.setSupportInput(true);
    }

    //后四直选 SIXZX
    public static void SIXZX(Game game) {
        createPicklayout(game, new String[]{"千位", "百位", "十位", "个位"});
        game.setSupportInput(true);
    }

    //前四直选 QSIZX
    public static void QSIZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位"});
        game.setSupportInput(true);
    }

    //前四组选4 QSIZUX4
    public static void QSIZUX4(Game game) {
        createPicklayout(game, new String[]{"三重号位", "单号位"});
    }
    //前四组选6 QSIZUX6
    public static void QSIZUX6(Game game) {
        createPicklayout(game, new String[]{"二重号位"});
    }

     //前四组选12 QSIZUX12
    public static void QSIZUX12(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"});
    }

    //前四组选24 QSIZUX24
    public static void QSIZUX24(Game game) {
        createPicklayout(game, new String[]{"组选24"});
    }

    //后四组选12 ZUX12
    public static void ZUX12(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"});
    }

    //后四组选6 ZUX6
    public static void ZUX6(Game game) {
        createPicklayout(game, new String[]{"二重号位"});
    }

    //后四组选4 ZUX4
    public static void ZUX4(Game game) {
        createPicklayout(game, new String[]{"三重号位", "单号位"});
    }

    //后四组选24 ZUX24
    public static void ZUX24(Game game) {
        createPicklayout(game, new String[]{"组选24"});
    }

    //组选20 ZUX20
    public static void ZUX20(Game game) {
        createPicklayout(game, new String[]{"三重号位", "单号位"});
    }

    //五星直选 WXZX
    public static void WXZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
        game.setSupportInput(true);
    }

    //组选120 ZUX120
    public static void ZUX120(Game game) {

        createPicklayout(game, new String[]{"组选120"});
    }

    //组选10 ZUX10
    public static void ZUX10(Game game) {
        createPicklayout(game, new String[]{"三重号位", "二重号位"});
    }

    //五星连选 WXLX
    public static void WXLX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
        game.setSupportInput(true);
    }

    //组选60 ZUX60
    public static void ZUX60(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"});
    }

    //组选5 ZUX5
    public static void ZUX5(Game game) {
        createPicklayout(game, new String[]{"四重号位", "单号位"});
    }

    //组选30 ZUX30
    public static void ZUX30(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"});
    }

    //任三直选 RSZX
    public static void RSZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
//        game.setSupportInput(true);
    }

    //任二直选 REZX
    public static void REZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"});
//        game.setSupportInput(true);
    }
    //任三组三 RSZS
    public static void RSZS(Game game) {
        createDigitPicklayout(game, new String[]{"任三组三"},3);
    }
    //任三组六 RSZL
    public static void RSZL(Game game) {
        createDigitPicklayout(game, new String[]{"任三组六"},3);
    }

    //后三一码不定位 YMBDW
    public static void YMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //前三二码不定位 QSEMBDW
    public static void QSEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //四星二码不定位 SXEMBDW
    public static void SXEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //后三二码不定位 EMBDW
    public static void EMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //中三一码不定位 ZSYMBDW
    public static void ZSYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //五星一码不定位 WXYMBDW
    public static void WXYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //中三二码不定位 ZSEMBDW
    public static void ZSEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //五星二码不定位 WXEMBDW
    public static void WXEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //前三一码不定位 QSYMBDW
    public static void QSYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //四星一码不定位 SXYMBDW
    public static void SXYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //五星三码不定位 WXSMBDW
    public static void WXSMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //四季发财 SJFC
    public static void SJFC(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //一帆风顺 YFFS
    public static void YFFS(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //好事成双 HSCS
    public static void HSCS(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //三星报喜 SXBX
    public static void SXBX(Game game) {
        createPicklayout(game, new String[]{"胆码"});
    }

    //前二包胆 QEZUXBD
    public static void QEZUXBD(Game game) {
        createPicklayout(game, new String[]{"前二包胆"});
    }

    //后一直选 YXZX
    public static void YXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //五星定位随机 WXDW
    public static void WXDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
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

    //后二连选随机 EXLX
    public static void EXLXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //后三直选随机 SXZX
    public static void SXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //后三组三随机 SXZS
    public static void SXZSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //后三组六随机 SXZL
    public static void SXZLRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 3));
        game.notifyListener();
    }

    //后三连选随机 SXLX
    public static void SXLXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
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

    //前二连选随机 QELX
    public static void QELXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //前三组三随机 QSZS
    public static void QSZSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //前三组六随机 QSZL
    public static void QSZLRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 3));
        game.notifyListener();
    }

    //前三连选随机 QSLX
    public static void QSLXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //前三直选随机 QSZX
    public static void QSZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //中三连选随机 ZSLX
    public static void ZSLXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //中三直选随机 ZSZX
    public static void ZSZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //中三组三随机 ZSZS
    public static void ZSZSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //中三组六随机 ZSZL
    public static void ZSZLRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 3));
        game.notifyListener();
    }

    //后四直选随机 SIXZX
    public static void SIXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //前四直选随机 QSIZX
    public static void QSIZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //后四组选24随机 ZUX24
    public static void ZUX24Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 4));
        game.notifyListener();
    }

    //后四组选12随机 ZUX12
    public static void ZUX12Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 1);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 2, randomList));
        }
        game.notifyListener();
    }

    //后四组选6随机 ZUX6
    public static void ZUX6Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //后四组选4随机 ZUX4
    public static void ZUX4Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 1);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 1, randomList));
        }
        game.notifyListener();
    }

    //组选30随机 ZUX30
    public static void ZUX30Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 2);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 1, randomList));
        }
        game.notifyListener();
    }

    //组选20随机 ZUX20
    public static void ZUX20Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 1);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 2, randomList));
        }
        game.notifyListener();
    }

    //组选120随机 ZUX120
    public static void ZUX120Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 5));
        game.notifyListener();
    }

    //组选10随机 ZUX10
    public static void ZUX10Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 1);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 1, randomList));
        }
        game.notifyListener();
    }

    //五星直选随机 WXZX
    public static void WXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //组选60随机 ZUX60
    public static void ZUX60Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 1);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 3, randomList));
        }
        game.notifyListener();
    }

    //组选5随机 ZUX5
    public static void ZUX5Random(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(0, 9, 1);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(0, 9, 1, randomList));
        }
        game.notifyListener();
    }

    //五星连选随机 WXLX
    public static void WXLXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //任二直选随机 REZX
    public static void REZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //任三直选随机 RSZX
    public static void RSZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //前三一码不定位随机 QSYMBDW
    public static void QSYMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //四星一码不定位随机 SXYMBDW
    public static void SXYMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //五星三码不定位随机 WXSMBDW
    public static void WXSMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 3));
        game.notifyListener();
    }

    //前三二码不定位随机 QSEMBDW
    public static void QSEMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //四星二码不定位随机 SXEMBDW
    public static void SXEMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //后三一码不定位随机 YMBDW
    public static void YMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //中三一码不定位随机 ZSYMBDW
    public static void ZSYMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //五星一码不定位随机 WXYMBDW
    public static void WXYMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //后三二码不定位随机 EMBDW
    public static void EMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //中三二码不定位随机 ZSEMBDW
    public static void ZSEMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //五星二码不定位随机 WXEMBDW
    public static void WXEMBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //三星报喜随机 SXBX
    public static void SXBXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //四季发财随机 SJFC
    public static void SJFCRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //一帆风顺随机 YFFS
    public static void YFFSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //好事成双随机 HSCS
    public static void HSCSRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    public static void OTHERRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers) {
            pickNumber.onRandom(random(0, 9, 7));
        }
    }

    /**============================================手工录入代码添加开始=============================================================**/
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

    //手工录入的监听
    public void displayInputView() {
        if (onManualEntryListener != null || manualInputView != null) {
            manualInputView.setOnAddListner(new OnAddListner() {

                @Override
                public void onAdd(ArrayList<String[]> chooseArray) {
                    Log.i(TAG," manualInputView.setOnAddListner(new OnAddListner() {");
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

    public static void addInputLayout(Game game, int column) {
        ViewGroup manualInput = game.getManualInput();
        View view = createManualInputLayout(manualInput);
        ManualInputView manualInputView = new ManualInputView(view, game.lottery, column);
        game.addManualInputView(manualInputView);
        manualInput.addView(view);
    }

    public static View createManualInputLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.popup_write_comment, null, false);
    }

    //17个  亚洲秒秒彩增加以下玩法的单式投注
    //后二直选 EXZX
    public static void EXZXInput(Game game) {
        addInputLayout(game,game.getColumn());
    }
    //后二连选 EXLX
    public static void EXLXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //后三直选 SXZX
    public static void SXZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //后三连选 SXLX
    public static void SXLXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //前二直选 QEZX
    public static void QEZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //前二连选 QELX
    public static void QELXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //前三直选 QSZX
    public static void QSZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //前三连选 QSLX
    public static void QSLXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //中三直选 ZSZX
    public static void ZSZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //中三连选 ZSLX
    public static void ZSLXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //后四直选 SIXZX
    public static void SIXZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //前四直选 QSIZX
    public static void QSIZXInput(Game game) {
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
    //任二直选 REZX
    public static void REZXInput(Game game) {
        addInputLayout(game, 2);
    }
    //任三直选 RSZX
    public static void RSZXInput(Game game) {
        addInputLayout(game, 3);
    }
    //任四直选 RSIZX
    public static void RSIZXInput(Game game) {
        addInputLayout(game, 4);
    }
    /**============================================手工录入代码添加结束=============================================================**/

}
