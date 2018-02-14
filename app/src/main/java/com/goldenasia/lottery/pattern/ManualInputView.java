package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Lottery;

/**
 * Created by ACE-PC on 2016/9/29.
 */

public class ManualInputView {
    private static final String TAG = "ManualInputView";
    private View view;
    private EditText inputEditText;

    private int column = 0;
    private Lottery lottery;
    private OnAddListner onAddListner;
    private boolean flag = true;

    public ManualInputView(View inputView, Lottery lottery, int column) {
        this.view = inputView;
        this.flag = true;
        this.column = column;
        this.lottery = lottery;
        createView();
    }

    public ManualInputView(View inputView, Lottery lottery, int column, OnAddListner onAddListner) {
        this.view = inputView;
        this.flag = true;
        this.column = column;
        this.lottery = lottery;
        this.onAddListner = onAddListner;
        createView();
    }

    private void createView() {
        inputEditText = (EditText) view.findViewById(R.id.input_multiline_text);

        inputEditText.addTextChangedListener(new TextWatcher() {
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
                handler.postDelayed(delayRun, 800);
            }
        });

        inputEditText.setOnEditorActionListener((TextView v, int keyCode, KeyEvent event) ->
        {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (keyCode == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                        String keyWord = inputEditText.getText().toString().trim();
                        if (null == keyWord) {
                            keyWord = "";
                        }
                        calculateNotes();
                        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        return true;
                    /*解决 EditText 无法换行*/
                    /*default:
                        return true;*/
                }
            }
            return false;
        });

        /*String hintStr = view.getContext().getResources().getString(R.string.is_multiline_tip);
        hintStr = StringUtils.replaceEach(hintStr, new String[]{"SHOW1", "SHOW2"}, new String[]{lottery
        .getLotteryType() == 2 ? "01,02,03  04,05,06" : "123 1,2,3", lottery.getLotteryType() == 2 ? "01-02-03
        04-05-06" : "1-2-3  4-5-6"});
        inputEditText.setHint(Html.fromHtml(hintStr).toString());*/

        String hintStr = "";
        switch (lottery.getLotteryType()) {
            case 1://时时彩
            case 4://福彩3D
                String colss="";
                switch (column){
                    case 1:
                        colss="1 2";
                        break;
                    case 2:
                        colss="12 12";
                        break;
                    case 3:
                        colss="123 123";
                        break;
                    case 4:
                        colss="1234 1234";
                        break;
                    case 5:
                        colss="12345 12345";
                        break;
                }
                hintStr = "请把您的号码复制或输入到文本框中：\n" +
                        "每注号码之间用间隔符隔开：空格  逗号[,] 分号[；]竖线[|] \n" +
                        "范例："+colss+"\n";
                break;
            case 2://十一选五
                String colsy="";
                switch (column){
                    case 1:
                        colsy="01;02";
                        break;
                    case 2:
                        colsy="01 02;01 02";
                        break;
                    case 3:
                        colsy="01 02 03;01 02 03";
                        break;
                    case 4:
                        colsy="01 02 03 04;01 02 03 04";
                        break;
                    case 5:
                        colsy="01 02 03 04 05;01 02 03 04 05";
                        break;
                }
                hintStr = "请把您的号码复制或输入到文本框中：\n" +
                        "1、每注各组成员之间用空格[ ]或TAB[]隔开。\n" +
                        "2、各注号码之间用间隔符隔开：逗号[,] 分号[；]竖线[|]\n" +
                        "范例："+colsy+"\n";
                break;
            case 8: //PK10
                String colPK10="";
                switch (column){
                    case 1:
                        colPK10="1"+"\n"+"2";
                        break;
                    case 2:
                        colPK10="1 2"+"\n"+"1 2";
                        break;
                    case 3:
                        colPK10="1 2 3"+"\n"+"1 2 3";
                        break;
                    case 4:
                        colPK10="1 2 3 4"+"\n"+"1 2 3 4";
                        break;
                    case 5:
                        colPK10="1 2 3 4 5" +"\n"+
                                "1 2 3 4 5";
                        break;
                    case 10:
                        colPK10="1 - - - - - - - - -" +"\n"+
                                "2 - - - - - - - - -";
                        break;
                    case 100: // //猜前五 QWMC   //猜后五 HWMC
                        colPK10="1 - - - -" +"\n"+
                                "2 - - - -";
                        break;
                }
                hintStr = "提示：\n" +
                        "请把号码复制或输入到文本框中。\n" +
                        "1、每注号码之间的间隔符支持回车 逗号[,] 分号[;]冒号[:]竖线[|]\n" +
                        "2、每注内的号码间隔使用空格即可\n" +
                        "3、定位胆，如某一位不选号，则用- 表示，如3 - 5 6 7\n" +
                        "4、仅支持单式，最高可投10万注。\n" +
                        "例如：\n"
                        +colPK10+"\n";
                break;
        }
        inputEditText.setHint(hintStr);
    }

    public EditText getInputEditText() {
        return inputEditText;
    }

    private Handler handler = new Handler();

    private Runnable delayRun = new Runnable() {
        @Override
        public void run() {
            calculateNotes();
        }
    };

    private void calculateNotes() {
        if (inputEditText.getText().length() > 0) {
            ManualInputEntry manualInput;
            switch (lottery.getLotteryType()) {
                case 2://11选5
                    manualInput = new ManualInputEntry(view.getContext(), inputEditText.getText().toString(), column, false, 1, 11);
                    break;
                case 8://pk10
                    manualInput = new ManualInputEntry(view.getContext(), inputEditText.getText().toString(), column, false, 1, 10,8);
                    break;
                default:
                    manualInput = new ManualInputEntry(view.getContext(), inputEditText.getText().toString(), column, true, 0, 9);
                    break;
            }

            if (onAddListner != null) {
                onAddListner.onAdd(manualInput.getChooseArray());
            }
        }
    }

    public void setOnAddListner(OnAddListner onAddListner) {
        this.flag = true;
        this.onAddListner = onAddListner;
    }
}
