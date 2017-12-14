package com.goldenasia.lottery.game;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.DigitCheckBoxPanel;
import com.goldenasia.lottery.component.RankCheckBoxPanel;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.ManualInputView;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 某一种彩种下的一种玩法：提供选号界面中间的选号区布局配置，计算注数，格式化输出选号Code的String
 * Created by Alashi on 2016/2/16.
 */
public abstract class Game implements NumberGroupView.OnChooseItemClickListener, DigitCheckBoxPanel
        .OnChooseDigitClickListener, RankCheckBoxPanel.OnChooseRankClickListener {
    protected ViewGroup topLayout;
    protected ViewGroup inputViewGroup;
    protected OnSelectedListener onSelectedListener;
    protected OnManualEntryListener onManualEntryListener;
    protected Method method;
    protected Lottery lottery;
    protected ArrayList<PickNumber> pickNumbers = new ArrayList<>();
    protected ManualInputView manualInputView;
    private int singleNum = 0;
    private boolean isDup;
    private boolean isSupportInput = false;
    private boolean exchange = true;
    private List<String> submitArray = new ArrayList<>();
    private int column;//开奖位数
    private boolean hasRandom;

    protected boolean isDigital;
    protected DigitCheckBoxPanel digitCheckBoxPanel;
    protected SparseBooleanArray digits = new SparseBooleanArray();

    protected boolean isRanking;
    protected RankCheckBoxPanel rankCheckBoxPanel;
    protected SparseBooleanArray ranks = new SparseBooleanArray();

    public boolean isHasRandom() {
        return hasRandom;
    }

    public void setHasRandom(boolean hasRandom) {
        this.hasRandom = hasRandom;
    }

    public boolean isDigital() {
        return isDigital;
    }

    public void setDigital(boolean digital) {
        this.isDigital = digital;
    }

    public SparseBooleanArray getDigits() {
        return digits;
    }

    public void setDigits(SparseBooleanArray digits) {
        this.digits = digits;
    }

    public boolean isRanking() {
        return isRanking;
    }

    public void setRanking(boolean ranking) {
        isRanking = ranking;
    }

    public SparseBooleanArray getRanks() {
        return ranks;
    }

    public void setRanks(SparseBooleanArray ranks) {
        this.ranks = ranks;
    }

    public Game(Method method) {
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

    public final void destroyInput() {
        if (inputViewGroup != null) {
            inputViewGroup.removeAllViews();
        }
    }

    public void onDestroy() {
        clearDigits();
        pickNumbers.clear();
        manualInputView = null;
    }

    public final void addPickNumber(PickNumber pickNumber) {
        pickNumbers.add(pickNumber);
        pickNumber.setChooseItemClickListener(this);
    }

    public final void initDigitPanel(View view,int digit) {
        digitCheckBoxPanel = new DigitCheckBoxPanel(getTopLayout().getContext(), view.findViewById(R.id.digit),digit);
        digitCheckBoxPanel.setOnChooseDigitClickListener(this);
        digitCheckBoxPanel.initCheck();
    }

    public final void initRankPanel(View view) {
        rankCheckBoxPanel = new RankCheckBoxPanel(getTopLayout().getContext(), view.findViewById(R.id.rank));
        rankCheckBoxPanel.setOnChooseRankClickListener(this);
        rankCheckBoxPanel.initCheck();
    }

    public final void addManualInputView(ManualInputView manualInputView) {
        this.manualInputView = manualInputView;
    }

    public String getWebViewCode() {
        JsonArray jsonArray = new JsonArray();
        for (PickNumber pickNumber : pickNumbers) {
            jsonArray.add(transform(pickNumber.getCheckedNumber(), false, true));
        }
        return jsonArray.toString();
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, false));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    public void onRandomCodes() {
    }

    public void onClearPick(Game game) {
        clearDigits();
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onClearPick();
        game.notifyListener();

        if (manualInputView != null) {
            manualInputView.getInputEditText().setText("");
            setSingleNum(0);
            if (onManualEntryListener != null) {
                onManualEntryListener.onManualEntry(0);
            }
        }
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

    public void displayInputView() {
    }

    public ViewGroup getTopLayout() {
        return topLayout;
    }

    public ViewGroup getManualInput() {
        return inputViewGroup;
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

    public boolean isSupportInput() {
        return isSupportInput;
    }

    public boolean isExchange() {
        return exchange;
    }

    public void setExchange(boolean exchange) {
        this.exchange = exchange;
    }

    public List<String> getSubmitArray() {
        return submitArray;
    }

    public void setSubmitArray(List<String> submitArray) {
        this.submitArray = submitArray;
    }

    public void setOnManualEntryListener(OnManualEntryListener onManualEntryListener) {
        submitArray.clear();
        this.onManualEntryListener = onManualEntryListener;
        displayInputView();
    }

    public void setSupportInput(boolean supportInput) {
        isSupportInput = supportInput;
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
    public void onChooseDigitClick(SparseBooleanArray digits) {
        setDigits(digits);
        notifyListener();
    }

    @Override
    public void onChooseRankClick(SparseBooleanArray ranks) {
        setRanks(ranks);
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

    protected static String transformtextSpecial(ArrayList<Integer> list, String[] disText, boolean numberStyle,
                                                 boolean emptyStyle) {
        StringBuilder builder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0, size = list.size(); i < size; i++) {
                builder.append(disText[list.get(i) - 1]);
                if (!numberStyle && i != size - 1) {
                    builder.append("_");
                }
            }
        } else {
            builder.append(emptyStyle ? "" : "-");
        }
        return builder.toString();
    }

    protected static String transformDigit(SparseBooleanArray list) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int size = list.size(), i = size - 1; i > -1; i--) {
            if (list.get(ConstantInformation.DIGIT_KEYS[i])) {
                builder.append(ConstantInformation.DIGIT_MAP.get(ConstantInformation.DIGIT_KEYS[i]));
            }
        }
        builder.append("]");
        return builder.toString();
    }

    protected static String transformRank(SparseBooleanArray list) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        for (int size = list.size(), i = 0; i < size; i++) {
            if (list.get(i)) {
                builder.append(list.keyAt(i) + 1);
                builder.append(",");
            }
        }
        builder.deleteCharAt(builder.toString().length() - 1);
        builder.append("]");
        return builder.toString();
    }

    protected static String transformDigitJsonArray(SparseBooleanArray digits) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < digits.size(); i++) {
            if (digits.get(ConstantInformation.DIGIT_KEYS[i])) {
                stringBuilder.append("1");
            }
        }
        return stringBuilder.toString();
    }

    protected static String transformRankJsonArray(SparseBooleanArray ranks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ranks.size(); i++) {
            if (ranks.get(i)) {
                stringBuilder.append("1");
            }
        }
        return stringBuilder.toString();
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
        clearDigits();
        for (PickNumber pickNumber : pickNumbers) {
            pickNumber.getNumberGroupView().setCheckNumber(new ArrayList<Integer>());
        }
        notifyListener();
    }

    public void clearDigits() {
        if (digitCheckBoxPanel != null)
            digitCheckBoxPanel.initCheck();
        if (rankCheckBoxPanel != null)
            rankCheckBoxPanel.initCheck();
    }

    /*专门针对 时时彩(任二 任三 任四直选)玩法添加的，该方法主要是响应（万位千位百位十位个位）点击和手工输入的变化 所带来的注数变化 */
    public void sscRenXuanManualMethodResult(){

    }
}
