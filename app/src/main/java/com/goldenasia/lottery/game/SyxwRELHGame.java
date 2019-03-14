package com.goldenasia.lottery.game;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.LuDanView2;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.util.SharedPreferencesUtils;
import com.goldenasia.lottery.util.SscLHHLuDan;
import com.goldenasia.lottery.view.MultiLineRadioGroup;
import com.google.gson.JsonArray;

import java.util.List;

/**
 * 11选5龙虎玩法添加
 */
public class SyxwRELHGame extends Game {
    private static String[] dragonTigerSumText = new String[]{"龙", "虎"};

    private static LuDanView2 luDanView;

    private static String checkedLudan = "[一V二]";//一V二 一V三 一V四 一V五 二V三 二V四 二V五 三V四 三V五 四V五

    private static  Lottery lottery;

    public SyxwRELHGame(Method method, Lottery lottery) {
        super(method);
        this.lottery=lottery;
        setHasRandom(false);
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

        builder.append(checkedLudan);
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transformtext(pickNumbers.get(i).getCheckedNumber(), dragonTigerSumText, false));
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
            Log.i("SscCommonGame", "onInflate: " + "//" + method.getCname() + "随机 " + method.getName() + " public " +
                    "static void " + method.getName() + "Random" + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public void updataOtherViews() {
        if (luDanView != null) {
            luDanView.refreshView();
        }
    }

    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    public static View createDigitPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_digits, null, false);
    }

    private static void addPickTextGame(Game game, View topView, String title, String[] disText) {
        PickNumber pickNumberText = new PickNumber(topView, title);
        pickNumberText.getNumberGroupView().setNumber(1, disText.length);
        pickNumberText.setDisplayText(disText);
        pickNumberText.setNumberStyle(null);
        pickNumberText.setChooseMode(true);
        pickNumberText.setColumnAreaHideOrShow(false);
        game.addPickNumber(pickNumberText);
    }

    private static void createDigitPicklayout(Game game, String[] name, String[] disText, int digit) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDigitPickLayout(game.getTopLayout());
            view.findViewById(R.id.digit).setVisibility(View.GONE);//去掉
            addPickTextGame(game, view, name[i], disText);
            views[i] = view;
        }

        game.getTopLayout().addView(getLuDan(game.getTopLayout()));


        for (View view : views) {
            game.getTopLayout().addView(view);
        }

        luDanView = LuDanView2.from(game.getTopLayout());
        game.getTopLayout().addView(luDanView.getLudanPanel());

        game.setColumn(name.length);
    }


    private static View getLuDan(ViewGroup container) {
        View digits_panel_ludan = container.inflate(container.getContext(),R.layout.digits_panel_ludan2, null);
        MultiLineRadioGroup ludan_01 = digits_panel_ludan.findViewById(R.id.radioGroup_sex_id);

        ludan_01.setOnCheckedChangeListener(new MultiLineRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(MultiLineRadioGroup group, int checkedId) {
                List<List<String>> luDanDataList;
                switch (checkedId) {
                    case R.id.ludan_01:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 1);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 1);
                        checkedLudan = "[一V二]";//一V二 一V三 一V四 一V五 二V三 二V四 二V五 三V四 三V五 四V五
                        break;
                    case R.id.ludan_02:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 2);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 2);
                        checkedLudan = "[一V三]";
                        break;
                    case R.id.ludan_03:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 3);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 3);
                        checkedLudan = "[一V四]";
                        break;
                    case R.id.ludan_04:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 4);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 4);
                        checkedLudan = "[一V五]";
                        break;
                    case R.id.ludan_05:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(1, 2);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 5);
                        checkedLudan = "[二V三]";
                        break;
                    case R.id.ludan_06:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(1, 3);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 6);
                        checkedLudan = "[二V四]";
                        break;
                    case R.id.ludan_07:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(1, 4);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 7);
                        checkedLudan = "[二V五]";
                        break;
                    case R.id.ludan_08:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(2, 3);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 8);
                        checkedLudan = "[三V四]";
                        break;
                    case R.id.ludan_09:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(2, 4);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 9);
                        checkedLudan = "[三V五]";
                        break;
                    case R.id.ludan_10:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(3, 4);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 10);
                        checkedLudan = "[四V五]";
                        break;
                    default:
                        luDanDataList = SscLHHLuDan.getLongHuHeList2(0, 1);
                        SharedPreferencesUtils.putInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 1);
                        checkedLudan = "[一V二]";
                        break;
                }
                luDanView.setLuDanList(luDanDataList);
            }
        });

        switch (SharedPreferencesUtils.getInt(container.getContext(), ConstantInformation.APP_INFO, ConstantInformation.luDanLastCheck2, 1)) {
            case 1:
                ludan_01.check(R.id.ludan_01);
                break;
            case 2:
                ludan_01.check(R.id.ludan_02);
                break;
            case 3:
                ludan_01.check(R.id.ludan_03);
                break;
            case 4:
                ludan_01.check(R.id.ludan_04);
                break;
            case 5:
                ludan_01.check(R.id.ludan_05);
                break;
            case 6:
                ludan_01.check(R.id.ludan_06);
                break;
            case 7:
                ludan_01.check(R.id.ludan_07);
                break;
            case 8:
                ludan_01.check(R.id.ludan_08);
                break;
            case 9:
                ludan_01.check(R.id.ludan_09);
                break;
            case 10:
                ludan_01.check(R.id.ludan_10);
                break;
        }
        return digits_panel_ludan;
    }


    //龙虎 RELH
    public static void RELH(Game game) {
        createDigitPicklayout(game, new String[]{"龙虎"}, dragonTigerSumText, 2);
    }
}
