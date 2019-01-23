package com.goldenasia.lottery.game;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.DigitCheckBoxLhcPanel;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.ManualInputView;
import com.goldenasia.lottery.pattern.LhcPickNumber;
import com.goldenasia.lottery.view.LhcNumberGroupView;
import com.goldenasia.lottery.view.LhcNumberGroupView.OnChooseItemClickListener;
import com.goldenasia.lottery.view.LhcNumberGroupView.OnChooseLineClickListener;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.Random;

/**
 * 六合彩玩法：提供选号界面中间的选号区布局配置，计算注数，格式化输出选号Code的String
 * Created by Alashi on 2016/2/16.
 */
public abstract class LhcGame implements OnChooseItemClickListener, OnChooseLineClickListener, DigitCheckBoxLhcPanel.OnChooseDigitClickListener {
    protected ViewGroup topLayout;
    protected OnSelectedListener onSelectedListener;
    protected Method method;
    protected Lottery lottery;
    protected ArrayList<LhcPickNumber> pickNumbers = new ArrayList<>();
    protected ManualInputView manualInputView;
    private int singleNum;
    private boolean isDup;
    private boolean exchange = true;
    private ArrayList<String> submitArray = new ArrayList<>();
    private int column;//开奖位数
    protected ViewGroup inputViewGroup;
    private boolean isSupportInput = false;
    protected OnManualEntryListener onManualEntryListener;

    protected DigitCheckBoxLhcPanel digitCheckBoxPanel;
    protected SparseBooleanArray digits = new SparseBooleanArray();

    public LhcGame(Method method) {
        this.method = method;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public final void inflate(ViewGroup container) {
        topLayout = container;
        onInflate();
    }

    public final void setOnSelectedListener(OnSelectedListener listener) {
        this.onSelectedListener = listener;
    }

    protected final void notifyListener() {
        if (onSelectedListener != null) {
            onSelectedListener.onChanged(this);
        }
    }

    public final void destroy() {
        topLayout.removeAllViews();
        onSelectedListener = null;
        onDestroy();
        destroyInput();
    }

    public void onDestroy() {
        pickNumbers.clear();
        manualInputView = null;
    }

    public final void addPickNumber(LhcPickNumber pickNumber) {
        pickNumbers.add(pickNumber);
        pickNumber.setChooseItemClickListener(this);
    }

    public final void addPickNumber(LhcPickNumber pickNumber,int lineID) {
        pickNumbers.add(pickNumber);
        pickNumber.setChooseItemClickListener(this);
        pickNumber.setChooseLineClickListener(this,lineID);
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (LhcPickNumber pickNumber : pickNumbers) {
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
    }

    public void onClearPick(LhcGame game) {

    }

    public ViewGroup getTopLayout() {
        return topLayout;
    }

    public int getSingleNum() {
        return singleNum;
    }

    public void setSingleNum(int singleNum) {
        this.singleNum = singleNum;
    }

    public boolean isDup() {
        return isDup;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public boolean isExchange() {
        return exchange;
    }

    public ArrayList<String> getSubmitArray() {
        return submitArray;
    }

    public void setSubmitArray(ArrayList<String> submitArray) {
        this.submitArray = submitArray;
    }

    public void setNumState(int singleNum, boolean isDup) {
        this.singleNum = singleNum;
        this.isDup = isDup;
    }

    public abstract void onInflate();

    /**
     * 提示调用
     */
    public void onCustomDialog(String msg) {
        PromptManager.showCustomDialog(topLayout.getContext(), msg);
    }

    @Override
    public void onChooseItemClick() {
        notifyListener();
    }

    @Override
    public void onChooseLineClick(int lineID) {
        notifyListener();
    }

    /**
     * 将Int的list转换成字符串，如list[06, 07] 转成string[06_07]
     *
     * @param list        int型数组
     * @param numberStyle 数字显示风格，true: 6, false: 06
     * @param emptyStyle  数组空时的显示风格， true: ""，false: "-"
     */
    protected static String transform(ArrayList<Integer> list, boolean numberStyle, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(String.format(numberStyle ? "%d" : "%02d", list.get(i)));
                if (!numberStyle && i != size - 1) {
                    builder.append("_");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    protected static String transformSpecial(ArrayList<Integer> list, boolean numberStyle, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(list.get(i));
                if (!numberStyle && i != size - 1) {
                    builder.append("_");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    /**
     * 将Int的list转换成字串文字信息 如[1，3]
     *
     * @param list
     * @param disText
     * @return
     */
    protected static String transformtext(ArrayList<Integer> list, String[] disText, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(disText[list.get(i) - 1]);
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    protected static String transformtextSpecial(ArrayList<Integer> list, String[] disText, boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(disText[list.get(i) - 1]);
                if (i != size - 1) {
                    builder.append("_");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    protected static ArrayList<Integer> random(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        ArrayList<Integer> result = new ArrayList<Integer>();
        int count = 0;
        while (count < n) {
            //int num = (int) (Math.random() * (max - min)) + min;
            Random random = new Random();
            int num = random.nextInt(max - min + 1) + min;
            if (result.contains(num)) {
                continue;
            }
            Log.e("random: ", num + "");
            result.add(num);
            count++;
        }
        return result;
    }

    protected static ArrayList<Integer> randomCommon(int min, int max, int n, ArrayList<Integer> array) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        ArrayList<Integer> result = new ArrayList<>();
        int count = 0;
        while (count < n) {
            //int num = (int) (Math.random() * (max - min)) + min;
            Random random = new Random();
            int num = random.nextInt(max - min + 1) + min;
            boolean arrayflag = true;
            for (Integer a : array) {
                if (num == a) {
                    arrayflag = false;
                    break;
                }
            }
            if (arrayflag) {
                if (!result.contains(num)) {
                    result.add(num);
                    count++;
                }
            }
        }
        return result;
    }

    public void reset() {
        for (LhcPickNumber pickNumber : pickNumbers) {
            pickNumber.getNumberGroupView().setCheckNumber(new ArrayList<Integer>());
        }
        notifyListener();
    }

    //六合彩正码任选添加的 start

    //正码一  正码二  正码三  正码四  正码五  正码六
    public final void initDigitPanel(View view, int digit) {
        digitCheckBoxPanel = new DigitCheckBoxLhcPanel(getTopLayout().getContext(), view.findViewById(R.id.digit), digit);
        digitCheckBoxPanel.setOnChooseDigitClickListener(this);
        digitCheckBoxPanel.initCheck();
    }

    public SparseBooleanArray getDigits() {
        return digits;
    }

    public void setDigits(SparseBooleanArray digits) {
        this.digits = digits;
    }

    @Override
    public void onChooseDigitClick(SparseBooleanArray digits) {
        setDigits(digits);
        notifyListener();
    }

    protected static String transformDigitJsonArray(SparseBooleanArray digits) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits.size(); i++) {
            if (digits.get(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i])) {
                stringBuilder.append("1");
            }
        }
        return stringBuilder.toString();
    }

    //保证正码 只出现一次  [正码一二三四五六]
    protected static String transformDigit(SparseBooleanArray list) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int startDigit = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i])) {
                if (startDigit == 0) {
                    builder.append("正码" + ConstantInformation.LhcZmrx.DIGIT_LHC_MAP.get(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i]));
                    startDigit++;
                } else {
                    builder.append(ConstantInformation.LhcZmrx.DIGIT_LHC_MAP.get(ConstantInformation.LhcZmrx.DIGIT_LHC_KEYS[i]));
                }
            }
        }
        builder.append("]");
        return builder.toString();
    }
    //六合彩正码任选添加的 end

    //2018.05.22 六合彩单式投注涉及玩法 特码直选、正码一直选、正码二直选、正码三直选、正码四直选、正码五直选、正码六直选  start
    public final void addManualInputView(ManualInputView manualInputView) {
        this.manualInputView = manualInputView;
    }

    public void onManualInput(Lottery lottery, ViewGroup inputViewGroup) {
        destroyInput();
        this.lottery = lottery;
        this.inputViewGroup = inputViewGroup;
        submitArray.clear();
        onInputInflate();
    }

    public void onInputInflate() {

    }

    public final void destroyInput() {
        if (inputViewGroup != null) {
            inputViewGroup.removeAllViews();
        }
    }

    public void setOnManualEntryListener(OnManualEntryListener onManualEntryListener) {
        submitArray.clear();
        this.onManualEntryListener = onManualEntryListener;
        displayInputView();
    }

    public void displayInputView() {
    }

    public void setExchange(boolean exchange) {
        this.exchange = exchange;
    }

    public boolean isSupportInput() {
        return isSupportInput;
    }

    public ViewGroup getManualInput() {
        return inputViewGroup;
    }

    public void setSupportInput(boolean supportInput) {
        isSupportInput = supportInput;
    }
    //2018.05.22 六合彩单式投注涉及玩法 特码直选、正码一直选、正码二直选、正码三直选、正码四直选、正码五直选、正码六直选  end
}
