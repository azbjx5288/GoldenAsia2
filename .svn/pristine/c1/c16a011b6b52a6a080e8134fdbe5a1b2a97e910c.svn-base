package com.goldenasia.lottery.game;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.util.ToastUtils;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gan on 2017/11/18.
 * 单式玩法--任三混合组选
 */
public class DsRSHHZXGame extends Game {
    private static final String TAG = DsRSHHZXGame.class.getName();
    private Activity activity;
    private int digit;
    private boolean hasIllegal;
    private ArrayList<String[]> codeList=new ArrayList<>();

    private EditText codesInput;
    private LinearLayout mainLayout;
    private LinearLayout parentLayout;
    private TextView pickNoticeView;

    private Handler handler = new Handler();

    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            doCalculation();
        }
    };

    public DsRSHHZXGame(Activity activity, Method method, Lottery lottery) {
        super(method);
        this.activity = activity;
        this.lottery = lottery;
        digit = 3;

        codeList = new ArrayList<>();
        setSingleNum(0);
    }

    @Override
    public void onInflate() {
        createPicklayout(this);
    }

    private void doCalculation() {
        verify();
        codeList = NumbericUtils.delDupWithOrder(codeList);
        codesInput.setText(getWebViewCode2());
        codesInput.setSelection(codesInput.getText().length());
        setColumn(digit);
        int digitsLength=transformDigitJsonArray(digits).length();

        switch(digitsLength){
            case 3:
                digitsLength=1;
                break;
            case 4:
                digitsLength=4;
                break;
            case 5:
                digitsLength=10;
                break;
            default:
                digitsLength=0;
                break;
        }

        setSingleNum(digitsLength*codeList.size());
        pickNoticeView.callOnClick();
    }

    private void verify() {
        hasIllegal = false;
        codeList.clear();
        if ("".equals(codesInput.getText().toString())) {
            return;
        }
        String[] codes = codesInput.getText().toString().split("\\s|,|，|;|；|\\||｜");

        for (String code : codes) {
            String[] strs;

            if(verifyOrFormat(code)){
                strs = code.split(",");
            }else{
                int length = code.length();
                strs = new String[length];
                for (int i = 0; i < length; i++) {
                    strs[i] = String.valueOf(code.charAt(i));
                }
            }
            if (strs.length != digit) {
                hasIllegal = true;
                continue;
            }
            verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SSC);
        }
    }

    /**
     * 验证格式
     */
    private boolean verifyOrFormat(String orgStr) {
        String regEx = "[,]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(orgStr);
        return m.find();
    }

    private void verifyNumber(String[] strs, ArrayList<String> legals) {
        boolean isLegal = true;
        for (String str : strs) {
            if (!legals.contains(str)) {
                isLegal = false;
                hasIllegal = true;
                break;
            }
        }
        if (isLegal) {
            codeList.add(strs);
        }
    }

    public String getWebViewCode(){
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(transformDigitJsonArray(digits));

        StringBuilder builder = new StringBuilder();

        for (int i = 0, size = codeList.size(); i < size; i++) {
            for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                builder.append(codeList.get(i)[j]);
            }
            if (i < size - 1) {
                builder.append("_");
            }
        }

        jsonArray.add(builder.toString());
        Log.d(TAG, "getWebViewCode  jsonArray: " + jsonArray.toString());
        return jsonArray.toString();
    }

    private String getWebViewCode2(){
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = codeList.size(); i < size; i++) {
            for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                builder.append(codeList.get(i)[j]);
            }
           // if (i < size - 1) {
                builder.append(" ");
            //}
        }
        return builder.toString();
    }

    public List<String> getSubmitArray() {
        List<String> array= new ArrayList<>();

        StringBuffer builder=new StringBuffer();
        builder.append(transformDigit(digits));
        for (int i = 0, size = codeList.size(); i < size; i++) {
            for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                builder.append(codeList.get(i)[j])/*.append(",")*/;
            }
            builder.append(",");
            /*builder.delete(builder.length()-1,builder.length());*/
        }
        builder.delete(builder.length()-1,builder.length());
        array.add(builder.toString());

        return array;
    }

    public void onClearPick(Game game)
    {
        codesInput.setText("");
        doCalculation();
    }

    private View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_medley_rshhzx,null, false);
    }

    private void createPicklayout(Game game) {
        View view = createDefaultPickLayout(game.getTopLayout());

        game.initDigitPanel(view,3);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
        /*对GameFragment的View进行操作*/
        pickNoticeView = (TextView) activity.getWindow().getDecorView().findViewById(R.id.pick_notice);
        parentLayout = (LinearLayout) activity.getWindow().getDecorView().findViewById(R.id.parent_layout);
        mainLayout = (LinearLayout) view.findViewById(R.id.main_layout);
        codesInput = (EditText) view.findViewById(R.id.input_multiline_medley);
        codesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (delayRun != null) {
                    handler.removeCallbacks(delayRun);
                }
                handler.postDelayed(delayRun, 1500);
            }
        });

        codesInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int keyCode, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (keyCode == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    switch (event.getAction()) {
                        case KeyEvent.ACTION_UP:
                            doCalculation();
                            imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            if (hasIllegal && !activity.isFinishing()) {
                                ToastUtils.showShortToastLocation(activity, "您的注单存在错误/重复项，已为您优化注单。", Gravity.CENTER, 0, -300);
                            }
                            return true;
                        default:
                            return true;
                    }
                }
                return false;
            }
        });
        mainLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                codesInput.clearFocus();
                if (delayRun != null) {
                    handler.removeCallbacks(delayRun);
                }
                handler.postDelayed(delayRun, 1500);
            }
        });
        codesInput.setOnClickListener((View v) -> codesInput.setFocusable(true));
        parentLayout.setOnClickListener((View v) -> codesInput.clearFocus());
    }

}
