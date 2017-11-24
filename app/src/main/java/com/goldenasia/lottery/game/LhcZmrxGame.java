package com.goldenasia.lottery.game;

import android.content.DialogInterface;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.LhcQuickStart;
import com.goldenasia.lottery.pattern.LhcPickNumber;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.view.LhcNumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;


/**
 * 六合彩正码任选
 * Created by Gan on 2017/10/18.
 */
public class LhcZmrxGame extends LhcGame implements LhcQuickStart.OnQuickListner {
    private String TAG=LhcZmrxGame.class.getName();

    private LhcQuickStart lhcQuickStart;
    private int selectedID;
    private CustomDialog.Builder builder;
    private LhcNumberGroupView numberGroupView;

    public LhcZmrxGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {
        createPicklayout(this, new String[]{""},6);
    }

    public void onClearPick(LhcGame game) {
        for (LhcPickNumber pickNumber : game.pickNumbers)
            pickNumber.onClearPick();
        game.notifyListener();
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(transformDigitJsonArray(digits));
        for (LhcPickNumber pickNumber : pickNumbers)
        {
            jsonArray.add(transformSpecial(pickNumber.getCheckedNumber(), false, true));
        }
        Log.i(TAG,"getWebViewCode  "+jsonArray.toString());
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        builder.append(transformDigit(digits));
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transformSpecial(pickNumbers.get(i).getCheckedNumber(), false, true));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        Log.i(TAG,"getSubmitCodes  "+builder.toString());
        return builder.toString();
    }

    private void addPickNumber2Game(LhcGame game, View topView, String title) {
        LhcPickNumber pickNumber2 = new LhcPickNumber(topView, title);
        pickNumber2.setPickColumnArea(false);
        game.addPickNumber(pickNumber2);
    }

    public View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_lhc_num_digits, null, false);
    }

    private void createPicklayout(LhcGame game, String[] name,int digit) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            game.initDigitPanel(view,digit);//六合彩位数选择面板  checkbox
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }
        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }

        game.setColumn(1);//多少行同样的投注面板

        numberGroupView = (LhcNumberGroupView) getTopLayout().findViewById(R.id.pick_column_NumberGroupView);

        //快捷投注
        views[0].findViewById(R.id.quick_start).setVisibility(View.VISIBLE);
        views[0].findViewById(R.id.quick_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initQuickStart();
            }
        });
    }

    private void initQuickStart() {
        View view = LayoutInflater.from(getTopLayout().getContext()).inflate(R.layout.popup_lhc_quick_start, null);
        lhcQuickStart = new LhcQuickStart(getTopLayout().getContext(), view.findViewById(R.id.main_panel));
        lhcQuickStart.setOnQuickListner(this);

        builder = new CustomDialog.Builder(getTopLayout().getContext());
        builder.setContentView(view);
        builder.setTitle("快捷投注");
        builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setSelected(selectedID);
                selectedID = 0;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onQuickStart(int id) {
        selectedID = id;
    }

    private void setSelected(int id) {
        int max = numberGroupView.getMaxNumber();
        int min = numberGroupView.getMinNumber();
        ArrayList<Integer> list = new ArrayList<>();
        switch (id) {
            case 0:
                onClearPick(this);
                break;
            case R.id.single_num:
                for (int i = min; i <= max; i++) {
                    if (i % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.small_single:
                for (int i = min; i <= max / 2; i++) {
                    if (i % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.combine_single:
                for (int i = min; i <= max; i++) {
                    int combine = i % 10 + i / 10;
                    if (combine % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.poultry:
                for (int i = min; i <= max; i++) {
                    for (ArrayList<Integer> animal : ConstantInformation.POULTRY_NUM) {
                        if (animal.contains(i)) {
                            list.add(i);
                            break;
                        }
                    }
                }
                break;
            case R.id.beast:
                for (int i = min; i <= max; i++) {
                    for (ArrayList<Integer> animal : ConstantInformation.BEAST_NUM) {
                        if (animal.contains(i)) {
                            list.add(i);
                            break;
                        }
                    }
                }
                break;
            case R.id.double_num:
                for (int i = min; i <= max; i++) {
                    if (i % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.small_double:
                for (int i = min; i <= max / 2; i++) {
                    if (i % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.combine_double:
                for (int i = min; i <= max; i++) {
                    int combine = i % 10 + i / 10;
                    if (combine % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.mouse:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.MOUSE_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.dragon:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.DRAGON_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.monkey:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.MONKEY_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.big_num:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    list.add(i);
                }
                break;
            case R.id.big_single:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    if (i % 2 != 0)
                        list.add(i);
                }
                break;
            case R.id.combine_big:
                for (int i = min; i <= max; i++) {
                    int combine = i % 10 + i / 10;
                    if (combine >= 7) {
                        list.add(i);
                    }
                }
                break;
            case R.id.cow:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.COW_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.snake:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.SNAKE_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.chicken:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.CHIKEN_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.small_num:
                for (int i = min, end = min + (max - min + 1) / 2; i < end; i++) {
                    list.add(i);
                }
                break;
            case R.id.big_double:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    if (i % 2 == 0)
                        list.add(i);
                }
                break;
            case R.id.combine_small:
                for (int i = min; i <= max; i++) {
                    int combine = i % 10 + i / 10;
                    if (combine < 7) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tiger:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.TIGER_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.horse:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.HORSE_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.dog:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.DOG_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.head_0:
                for (int i = min; i <= max; i++) {
                    if (i > 0 && i < 10) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_0:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_5:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 5) {
                        list.add(i);
                    }
                }
                break;
            case R.id.rabbit:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.RABBIT_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.sheep:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.SHEEP_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.pig:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.PIG_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.head_1:
                for (int i = min; i <= max; i++) {
                    if (i >= 10 && i < 20) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_1:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 1) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_6:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 6) {
                        list.add(i);
                    }
                }
                break;
            case R.id.red:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.RED_WAVE_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.blue:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.BLUE_WAVE_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.green:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.GREEN_WAVE_NUM.contains(i)) {
                        list.add(i);
                    }
                }
                break;
            case R.id.head_2:
                for (int i = min; i <= max; i++) {
                    if (i >= 20 && i < 30) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_2:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 2) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_7:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 7) {
                        list.add(i);
                    }
                }
                break;
            case R.id.red_single:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.RED_WAVE_NUM.contains(i) && i % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.blue_single:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.BLUE_WAVE_NUM.contains(i) && i % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.green_single:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.GREEN_WAVE_NUM.contains(i) && i % 2 != 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.head_3:
                for (int i = min; i <= max; i++) {
                    if (i >= 30 && i < 40) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_3:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 3) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_8:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 8) {
                        list.add(i);
                    }
                }
                break;
            case R.id.red_double:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.RED_WAVE_NUM.contains(i) && i % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.blue_double:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.BLUE_WAVE_NUM.contains(i) && i % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.green_double:
                for (int i = min; i <= max; i++) {
                    if (ConstantInformation.GREEN_WAVE_NUM.contains(i) && i % 2 == 0) {
                        list.add(i);
                    }
                }
                break;
            case R.id.head_4:
                for (int i = min; i <= max; i++) {
                    if (i >= 40 && i < 50) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_4:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 4) {
                        list.add(i);
                    }
                }
                break;
            case R.id.tail_9:
                for (int i = min; i <= max; i++) {
                    if (i % 10 == 9) {
                        list.add(i);
                    }
                }
                break;
            case R.id.red_big:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    if (ConstantInformation.RED_WAVE_NUM.contains(i))
                        list.add(i);
                }
                break;
            case R.id.blue_big:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    if (ConstantInformation.BLUE_WAVE_NUM.contains(i))
                        list.add(i);
                }
                break;
            case R.id.green_big:
                for (int i = min + (max - min + 1) / 2; i <= max; i++) {
                    if (ConstantInformation.GREEN_WAVE_NUM.contains(i))
                        list.add(i);
                }
                break;
            case R.id.clear:
                selectedID = 0;
                list.clear();
                break;
            case R.id.red_small:
                for (int i = min, end = min + (max - min + 1) / 2; i < end; i++) {
                    if (ConstantInformation.RED_WAVE_NUM.contains(i))
                        list.add(i);
                }
                break;
            case R.id.blue_small:
                for (int i = min, end = min + (max - min + 1) / 2; i < end; i++) {
                    if (ConstantInformation.BLUE_WAVE_NUM.contains(i))
                        list.add(i);
                }
                break;
            case R.id.green_small:
                for (int i = min, end = min + (max - min + 1) / 2; i < end; i++) {
                    if (ConstantInformation.GREEN_WAVE_NUM.contains(i))
                        list.add(i);
                }
                break;
        }
        numberGroupView.setCheckNumber(list);
        notifyListener();
    }
}