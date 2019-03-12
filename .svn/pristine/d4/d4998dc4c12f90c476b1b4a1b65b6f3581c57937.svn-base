package com.goldenasia.lottery.game;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.Lunar;
import com.goldenasia.lottery.pattern.LhcLayout;
import com.goldenasia.lottery.pattern.LhcPickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 六合彩生肖玩法
 * Created by Sakura on 2016/9/15.
 */

public class LhcSxGame extends LhcGame {

    private final static String TAG = LhcSxGame.class.getName();

    private boolean showAnimal;
    private ArrayList<String> pickSxList;

    /**
     * 生肖数组
     */
    private static SparseArray<String> sxListStr = new SparseArray() {{
        put(0, "鼠");
        put(1, "牛");
        put(2, "虎");
        put(3, "兔");
        put(4, "龙");
        put(5, "蛇");
        put(6, "马");
        put(7, "羊");
        put(8, "猴");
        put(9, "鸡");
        put(10, "狗");
        put(11, "猪");
    }};

    /**
     * 全部生肖ID
     */
    private static SparseArray<Integer> sxListId = new SparseArray<Integer>() {{
        put(0, R.id.layout_mouse);
        put(1, R.id.layout_cow);
        put(2, R.id.layout_tiger);
        put(3, R.id.layout_rabbit);
        put(4, R.id.layout_dragon);
        put(5, R.id.layout_snake);
        put(6, R.id.layout_horse);
        put(7, R.id.layout_sheep);
        put(8, R.id.layout_monkey);
        put(9, R.id.layout_chicken);
        put(10, R.id.layout_dog);
        put(11, R.id.layout_pig);
    }};


    /*家禽6个  1,6,7,9,10,11*/
    private int[] poultryList = new int[]{R.id.layout_cow, R.id.layout_horse, R.id.layout_sheep, R.id.layout_chicken, R.id.layout_dog, R.id.layout_pig};
    /*野兽6个  0,2,3,4,5,8*/
    private int[] beastList = new int[]{R.id.layout_mouse, R.id.layout_tiger, R.id.layout_rabbit, R.id.layout_dragon, R.id.layout_snake, R.id.layout_monkey};

    public LhcSxGame(Method method) {
        super(method);
        pickSxList = new ArrayList<>();
        if (!"TMSX".equals(method.getName())) {
            showAnimal = false;
        } else {
            showAnimal = true;
        }
    }

    @Override
    public void onInflate() {
        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_column_lhc_sx, topLayout, true);
        //取农历生肖
        int index = sxListStr.indexOfValue(Lunar.getInstance().animalsYear());
        if (index != -1) {
            int nm = 1, n = index; //nm 号码 n 生肖下标、1-49个号码按当年的农历生肖位进行分配
            SparseArray<List<String>> animalZodiacNo = new SparseArray<>();
            do {//生肖
                List<String> noList = animalZodiacNo.get(n);
                if (noList == null) {
                    List<String> zodiacNo = new ArrayList<>();
                    zodiacNo.add(String.format("%02d", nm));
                    animalZodiacNo.put(n, zodiacNo);
                } else {
                    animalZodiacNo.get(n).add(String.format("%02d", nm));
                }
                if (n == 0) {
                    n = sxListStr.size() - 1;
                } else {
                    n--;
                }
                nm++;
            } while (nm < 50);
            //生成UI生肖与号码
            LinearLayout layout = topLayout.findViewById(R.id.lhc_sx_layout);
            for (int i = 0; i < animalZodiacNo.size(); i++) {
                int key = animalZodiacNo.keyAt(i);
                List<String> serial = animalZodiacNo.get(key);
                LhcLayout view = createDefaultPickLayout(layout);
                int id = sxListId.valueAt(key);
                view.setId(id);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0, 10, 0, 0);
                view.setLayoutParams(lp);
                String sx = sxListStr.valueAt(key);
                view.setSx(sx);
                LhcPickNumber pickNumberText = new LhcPickNumber(view, sx);
                pickNumberText.getNumberGroupView().setNumber(1, serial.size());
                pickNumberText.setDisplayText(serial.toArray(new String[serial.size()]));
                pickNumberText.setColorful(false);
                pickNumberText.setNumberStyle(null);
                pickNumberText.setEnabledLineClick(true);
                pickNumberText.setColumnAreaHideOrShow(false);
                this.addPickNumber(pickNumberText, id);
                layout.addView(view);
            }
        }

        ButterKnife.bind(this, topLayout);
        showMode(showAnimal);
    }

    public static LhcLayout createDefaultPickLayout(ViewGroup container) {
        return (LhcLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_lhc_sx_option, null);
    }

    @Override
    public void reset() {
        for (int i = 0; i < sxListId.size(); i++) {
            int id = sxListId.valueAt(i);
            if (topLayout.findViewById(id) != null)
                topLayout.findViewById(id).setSelected(false);
        }
        pickSxList.clear();
        notifyListener();
    }

    @OnClick({R.id.layout_mouse, R.id.layout_cow, R.id.layout_tiger, R.id.layout_rabbit, R.id.layout_dragon, R.id.layout_snake,
            R.id.layout_horse, R.id.layout_sheep, R.id.layout_monkey, R.id.layout_chicken, R.id.layout_dog, R.id.layout_pig})
    public void onLayoutClick(LhcLayout view) {
        if (view.isSelected()) {
            view.setSelected(false);
            pickSxList.remove(view.getSx());
        } else {
            view.setSelected(true);
            pickSxList.add(view.getSx());
        }
        notifyListener();
    }

    public void onRandomCodes() {
        reset();
        switch (method.getName()) {
            case "ERLX":
                randomAdd(random(0, 11, 2));
                break;
            case "SNLX":
                randomAdd(random(0, 11, 3));
                break;
            case "SILX":
                randomAdd(random(0, 11, 4));
                break;
            default:
                randomAdd(random(0, 11, 1));
        }
        /*if ("ERLX".equals(method.getName()))
        {
            randomAdd(random(0, 11, 2));
        } else if ("SNLX".equals(method.getName()))
        {
            randomAdd(random(0, 11, 3));
        } else
            randomAdd(random(0, 11, 1));*/
        notifyListener();
    }

    @Override
    public String getWebViewCode() {
        StringBuilder stringBuilder = new StringBuilder();
        int length = pickSxList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(pickSxList.get(i));
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
        int length = pickSxList.size();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(pickSxList.get(i));
            if (i != length - 1) {
                stringBuilder.append('_');
            }
        }
        return stringBuilder.toString();
    }

    private void showMode(boolean isShow) {
        if (isShow)
            topLayout.findViewById(R.id.animal).setVisibility(View.VISIBLE);
        else
            topLayout.findViewById(R.id.animal).setVisibility(View.GONE);
    }

    /**
     * 家禽、野兽
     *
     * @param radioButton
     */
    @OnClick({R.id.poultry, R.id.beast})
    public void onSelectAnimal(RadioButton radioButton) {
        reset();
        switch (radioButton.getId()) {
            case R.id.poultry:
                for (int i : poultryList)
                    selectAdd(i);
                notifyListener();
                break;
            case R.id.beast:
                for (int i : beastList)
                    selectAdd(i);
                notifyListener();
                break;
        }
    }

    private void selectAdd(int id) {
        LhcLayout lastClick = topLayout.findViewById(id);
        lastClick.setSelected(true);
        pickSxList.add(lastClick.getSx());
        notifyListener();
    }

    private void randomAdd(ArrayList<Integer> textID) {
        for (int i : textID)
            switch (i) {
                case 0:
                    selectAdd(sxListId.valueAt(0));
                    break;
                case 1:
                    selectAdd(sxListId.valueAt(1));
                    break;
                case 2:
                    selectAdd(sxListId.valueAt(2));
                    break;
                case 3:
                    selectAdd(sxListId.valueAt(3));
                    break;
                case 4:
                    selectAdd(sxListId.valueAt(4));
                    break;
                case 5:
                    selectAdd(sxListId.valueAt(5));
                    break;
                case 6:
                    selectAdd(sxListId.valueAt(6));
                    break;
                case 7:
                    selectAdd(sxListId.valueAt(7));
                    break;
                case 8:
                    selectAdd(sxListId.valueAt(8));
                    break;
                case 9:
                    selectAdd(sxListId.valueAt(9));
                    break;
                case 10:
                    selectAdd(sxListId.valueAt(10));
                    break;
                case 11:
                    selectAdd(sxListId.valueAt(11));
                    break;
            }
    }

    @Override
    public void onClearPick(LhcGame game) {
        reset();
    }

    @Override
    public void onChooseLineClick(int lineID) {
        LhcLayout view = topLayout.findViewById(lineID);
        if (view.isSelected()) {
            view.setSelected(false);
            pickSxList.remove(view.getSx());
        } else {
            view.setSelected(true);
            pickSxList.add(view.getSx());
        }
        notifyListener();
    }
}
