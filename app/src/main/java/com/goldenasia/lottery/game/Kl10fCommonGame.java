package com.goldenasia.lottery.game;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.view.NumberGroupView;

import java.util.ArrayList;

public class Kl10fCommonGame extends Game {

    public Kl10fCommonGame(Method method) {
        super (method);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass ( ).getMethod (method.getName ( ), Game.class);
            function.invoke (null, this);
        } catch (Exception e) {
            e.printStackTrace ( );
            Log.e ("Kl10fCommonGame", "// " + method.getCname ( ) + " public static void " + method.getName ( ) + "(Game game){}");
            Toast.makeText (topLayout.getContext ( ), "不支持的类型", Toast.LENGTH_LONG).show ( );
        }
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder ( );
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString ( );
    }

    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass ( ).getMethod (method.getName ( ) + "Random", Game.class);
            function.invoke (null, this);
        } catch (Exception e) {
            e.printStackTrace ( );
            Toast.makeText (topLayout.getContext ( ), "不支持的类型", Toast.LENGTH_LONG).show ( );
        }
    }

    //手工录入
    public void onInputInflate() {
        try {
            java.lang.reflect.Method function = getClass ( ).getMethod (method.getName ( ) + "Input", Game.class);
            function.invoke (null, this);
        } catch (Exception e) {
            e.printStackTrace ( );
            Toast.makeText (topLayout.getContext ( ), "不支持的类型", Toast.LENGTH_LONG).show ( );
        }
    }

    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from (container.getContext ( )).inflate (R.layout.pick_column_kl10f, null, false);
    }

    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber (topView, title);
        game.addPickNumber (pickNumber2);
    }

    private static void createPicklayout(Game game, String[] name, String gameMethod) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout (game.getTopLayout ( ));
            addPickNumber2Game (game, view, name[i]);
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout ( );
        for (View view : views) {
            topLayout.addView (view);
        }
    }

    private static void createDantuoLayout(Game game, int danSize, int tuoSize) {
        View[] views = new View[2];
        PickNumber danNum;
        NumberGroupView danNumView;
        PickNumber tuoNum;
        NumberGroupView tuoNumView;

        View view0 = createDefaultPickLayout(game.getTopLayout());
        danNum = new PickNumber(view0, "胆码", danSize);
        danNum.setColumnAreaHideOrShow(false);
        game.addPickNumber(danNum);
        danNumView = danNum.getNumberGroupView();
        views[0] = view0;

        View view1 = createDefaultPickLayout(game.getTopLayout());
        tuoNum = new PickNumber(view1, "拖码", tuoSize);
//        tuoNum.setColumnAreaHideOrShow(false);
        game.addPickNumber(tuoNum);
        tuoNumView = tuoNum.getNumberGroupView();
        views[1] = view1;

        danNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick() {
                int lastPick = danNumView.getLastPick();
                int size = danNumView.getPickList().size();
                if (tuoNumView.getPickList().contains(lastPick) && danNumView.getCheckedArray().get(lastPick)) {
                    tuoNumView.getCheckedArray().put(lastPick, false);
                    tuoNumView.getPickList().remove(Integer.valueOf(lastPick));
                    tuoNumView.invalidate();
                }
                if (size > danSize) {
                    danNumView.getCheckedArray().put(danNumView.getPickList().get(size - 2), false);
                    danNumView.getPickList().remove(size - 2);
                }
                game.notifyListener();
            }
        });

        tuoNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick() {
//                int lastPick = tuoNumView.getLastPick();
//                if (danNumView.getPickList().contains(lastPick) && tuoNumView.getCheckedArray().get(lastPick)) {
//                    danNumView.getCheckedArray().put(lastPick, false);
//                    danNumView.getPickList().remove(Integer.valueOf(lastPick));
//                    danNumView.invalidate();
//                }
//                game.notifyListener();

                /*对 全大小奇偶反清”选择的处理 start*/
                ArrayList<Integer> checkedNumber = tuoNumView.getCheckedNumber();
                for (int j = 0; j < checkedNumber.size(); j++) {
                    danNumView.getCheckedArray().put(checkedNumber.get(j), false);
                    danNumView.getPickList().remove(Integer.valueOf(checkedNumber.get(j)));
                }
                danNumView.invalidate();
                game.notifyListener();
                /*对 全大小奇偶反清”选择的处理 end*/
            }
        });

        ViewGroup topLayout = game.getTopLayout();
        for (View v : views) {
            topLayout.addView(v);
        }
    }

    // 任选二
    public static void GDRX2(Game game) {
        createPicklayout(game, new String[]{"任选二"}, "GDRX2");
    }

    // 任选三
    public static void GDRX3(Game game) {
        createPicklayout(game, new String[]{"任选三"}, "GDRX3");
    }

    // 任选四
    public static void GDRX4(Game game) {
        createPicklayout(game, new String[]{"任选四"}, "GDRX4");
    }

    // 任选五
    public static void GDRX5(Game game) {
        createPicklayout(game, new String[]{"任选五"}, "GDRX5");
    }

    // 任选六
    public static void GDRX6(Game game) {
        createPicklayout(game, new String[]{"任选六"}, "GDRX6");
    }

    // 任选七
    public static void GDRX7(Game game) {
        createPicklayout(game, new String[]{"任选七"}, "GDRX7");
    }

    // 前二组选胆拖
    public static void GDDTQEZUX(Game game) {
        createDantuoLayout(game, 1, 20);
    }

    // 前二组选
    public static void GDQEZUX(Game game) {
        createPicklayout(game, new String[]{"前二组选"}, "GDQEZUX");
    }

    // 前二直选
    public static void GDQEZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位"}, "SCQEZX");
    }

    // 前三组选胆拖
    public static void GDDTQSZUX(Game game) {
        createDantuoLayout(game, 1, 20);
    }

    // 前三组选
    public static void GDQSZUX(Game game) {
        createPicklayout(game, new String[]{"前三组选"}, "GDQSZUX");
    }

    // 前三直选
    public static void GDQSZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"}, "GDQSZX");
    }
}
