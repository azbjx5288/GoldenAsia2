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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.goldenasia.lottery.game.GameConfig.DS_TYPE_SSC;
import static com.goldenasia.lottery.game.GameConfig.DS_TYPE_SYXW;

/**
 * Created by Sakura on 2016/11/18.
 * 单式玩法
 */
public class DsGame extends Game {
    private static final String TAG = DsGame.class.getName();
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

    public DsGame(Activity activity, Method method, Lottery lottery) {
        super(method);
        this.activity = activity;
        this.lottery = lottery;
        switch (method.getName()) {
            case "QSHHZX":
            case "SXHHZX":
            case "ZSHHZX":
            case "RSHHZX":
                digit = 3;
                break;
            default:
                Log.w(TAG, "DsGame: 不支持的method类型：" + method.getMethodId());
                ToastUtils.showShortToast(activity, "不支持的类型");
        }
        int type = GameConfig.getDsType(lottery);
        switch (type) {
            case DS_TYPE_SSC:
            case DS_TYPE_SYXW:
                break;
            default:
                Log.w(TAG, "DsGame: 不支持的类型：method:" + method.getName() + ", lottery:" + lottery.getLotteryId());
                ToastUtils.showShortToast(activity, "不支持的类型");
                break;
        }
        codeList = new ArrayList<>();
        setSingleNum(0);
    }

    @Override
    public void onInflate() {
        createPicklayout(this);
    }

    private void doCalculation() {
        verify();
        int type = GameConfig.getDsType(lottery);
        switch (type) {
            case DS_TYPE_SSC:
                codeList = NumbericUtils.delDupWithOrder(codeList);
                break;
            case DS_TYPE_SYXW:
                codeList = NumbericUtils.delDup(codeList);
                break;
            default:
                break;
        }
        codesInput.setText(getWebViewCode());
        codesInput.setSelection(codesInput.getText().length());
        setColumn(digit);
        setSingleNum(codeList.size());
        pickNoticeView.callOnClick();
    }

    private void verify() {
        hasIllegal = false;
        codeList.clear();
        if ("".equals(codesInput.getText().toString())) {
            return;
        }
        String[] codes;
        int type = GameConfig.getDsType(lottery);
        switch (type) {
            case DS_TYPE_SSC:
                codes = codesInput.getText().toString().split("\\s|,|，|;|；|\\||｜");
                break;
            default:
                codes = codesInput.getText().toString().split("\\s|,|，|;|；|\\||｜");
                break;
        }

        for (String code : codes) {
            String[] strs;
            switch (type) {
                case DS_TYPE_SSC:
                    if(verifyOrFormat(code)){
                        strs = code.split(",");
                    }else{
                        int length = code.length();
                        strs = new String[length];
                        for (int i = 0; i < length; i++) {
                            strs[i] = String.valueOf(code.charAt(i));
                        }
                    }
                    break;
                default:
                    strs = code.split(" ");
                    if (NumbericUtils.hasDupString(strs)) {
                        hasIllegal = true;
                        continue;
                    }
                    break;
            }
            if (strs.length != digit) {
                hasIllegal = true;
                continue;
            }

            switch (type) {
                case DS_TYPE_SSC:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SSC);
                    break;
                case DS_TYPE_SYXW:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SYXW);
                    break;
                default:
                    Log.w(TAG, "verify: 不支持的类型：" + type);
                    Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        /*if (NumbericUtils.hasDupArray(codeList)) {
            hasIllegal = true;
        }*/
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
        StringBuilder builder = new StringBuilder();
        int type = GameConfig.getDsType(lottery);
        switch (type) {
            case DS_TYPE_SSC:
                for (int i = 0, size = codeList.size(); i < size; i++) {
                    for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                        builder.append(codeList.get(i)[j]);
                    }
                    if (i < size - 1) {
                        builder.append(" ");
                    }
                }
                break;
            default:
                Log.w(TAG, "getWebViewCode: 不支持的类型：" + type);
                ToastUtils.showShortToast(activity, "不支持的类型");
                break;
        }
        return builder.toString();
    }

    public List<String> getSubmitArray() {
        List<String> array= new ArrayList<>();
        int type = GameConfig.getDsType(lottery);
        switch (type) {
            case DS_TYPE_SSC:
                for (int i = 0, size = codeList.size(); i < size; i++) {
                    StringBuffer builder=new StringBuffer();
                    for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                        builder.append(codeList.get(i)[j]).append(",");
                    }
                    builder.delete(builder.length()-1,builder.length());
                    array.add(builder.toString());
                }
                break;
            default:
                Log.w(TAG, "getSubmitCodes: 不支持的类型：" + type);
                ToastUtils.showShortToast(topLayout.getContext(), "不支持的类型");
                break;
        }
        return array;
    }

    public void onClearPick(Game game)
    {
        codesInput.setText("");
        doCalculation();
    }

    private View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_medley,null, false);
    }

    private void createPicklayout(Game game) {
        View view = createDefaultPickLayout(game.getTopLayout());
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
