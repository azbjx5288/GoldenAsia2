package com.goldenasia.lottery.game;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.cpiz.android.bubbleview.BubbleLinearLayout;
import com.cpiz.android.bubbleview.BubblePopupWindow;
import com.cpiz.android.bubbleview.BubbleStyle;
import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.pattern.ManualInputView;
import com.goldenasia.lottery.pattern.OnAddListner;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.view.NumberGroupView;
import com.google.gson.JsonArray;

import java.util.ArrayList;

/**
 * 时时彩，一般性玩法均在这个类
 * Created by Alashi on 2016/2/17.
 */
public class SscCommonGame extends Game {
    private String TAG=SscCommonGame.class.getName();


    private  static   SscCommonGameUtils mScCommonGameUtils=new SscCommonGameUtils();

    public SscCommonGame(Method method) {
        super(method);
        switch (method.getName()) {
            case "REZUX":
                isDigital = true;
                setHasRandom(false);
                break;
            case "RSIZX":
                isDigital = false;
                setHasRandom(false);
                break;
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
            ConstantInformation.LENG_RE_COUNT=100;
            java.lang.reflect.Method function = getClass().getMethod(method.getName(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SscCommonGame", "onInflate: " + "//" + method.getCname() + " " + method.getName() + " public static " +
                    "" + "void " + method.getName() + "(Game game) {}");
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
        Log.i(TAG,"getWebViewCode  "+jsonArray.toString());
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

    @Override
    public void sscRenXuan2ManualMethodResult(){
        int notes=0;
        if(mChooseArray!=null) {
            notes= mChooseArray.size();

            int  digitLength=transformDigitJsonArray(digits).length();

            switch (digitLength){
                case 2:
                    digitLength=1;
                    break;
                case 3:
                    digitLength=3;
                    break;
                case 4:
                    digitLength=6;
                    break;
                case 5:
                    digitLength=10;
                    break;
                default:
                    digitLength=0;
            }

            notes=notes*digitLength;

            ArrayList<String> codeArray = new ArrayList<>();

            StringBuffer builder=new StringBuffer();
            builder.append(transformDigit(digits));
            for (int i = 0; i < mChooseArray.size(); i++) {
                for (int j = 0 ; j < mChooseArray.get(i).length; j++) {
                    builder.append(mChooseArray.get(i)[j]);
                }
                builder.append(",");
            }
            builder.delete(builder.length()-1,builder.length());
            codeArray.add(builder.toString());

            setSubmitArray(codeArray);
        }
        setSingleNum(notes);
        if(onManualEntryListener!=null) {
            onManualEntryListener.onManualEntry(notes);
        }
    }

    @Override
    public void sscRenXuan3ManualMethodResult(){
        int notes=0;
        if(mChooseArray!=null) {
            notes= mChooseArray.size();

            int  digitLength=transformDigitJsonArray(digits).length();

            switch (digitLength){
                case 3:
                    digitLength=1;
                    break;
                case 4:
                    digitLength=4;
                    break;
                case 5:
                    digitLength=10;
                    break;
                default:
                    digitLength=0;
            }

            notes=notes*digitLength;

            ArrayList<String> codeArray = new ArrayList<>();

            StringBuffer builder=new StringBuffer();
            builder.append(transformDigit(digits));
            for (int i = 0; i < mChooseArray.size(); i++) {
                for (int j = 0 ; j < mChooseArray.get(i).length; j++) {
                    builder.append(mChooseArray.get(i)[j]);
                }
                builder.append(",");
            }
            builder.delete(builder.length()-1,builder.length());
            codeArray.add(builder.toString());

            setSubmitArray(codeArray);
        }
        setSingleNum(notes);
        if(onManualEntryListener!=null) {
            onManualEntryListener.onManualEntry(notes);
        }
    }


    @Override
    public void sscRenXuan4ManualMethodResult(){
        int notes=0;
        if(mChooseArray!=null) {
            notes= mChooseArray.size();

            int  digitLength=transformDigitJsonArray(digits).length();

            switch (digitLength){
                case 4:
                    digitLength=1;
                    break;
                case 5:
                    digitLength=5;
                    break;
                default:
                    digitLength=0;
            }

            notes=notes*digitLength;

            ArrayList<String> codeArray = new ArrayList<>();

            StringBuffer builder=new StringBuffer();
            builder.append(transformDigit(digits));
            for (int i = 0; i < mChooseArray.size(); i++) {
                for (int j = 0 ; j < mChooseArray.get(i).length; j++) {
                    builder.append(mChooseArray.get(i)[j]);
                }
                builder.append(",");
            }
            builder.delete(builder.length()-1,builder.length());
            codeArray.add(builder.toString());

            setSubmitArray(codeArray);
        }
        setSingleNum(notes);
        if(onManualEntryListener!=null) {
            onManualEntryListener.onManualEntry(notes);
        }
    }

    public void displayInputView() {
        if (onManualEntryListener != null || manualInputView != null) {
            manualInputView.setOnAddListner(new OnAddListner() {

                @Override
                public void onAdd(ArrayList<String[]> chooseArray) {
                    mChooseArray=chooseArray;
                    int notes = 0;
                    if (getMethod().getName().equals("WXDW")) {
                        for (String[] wxdw : chooseArray) {
                            for (String d : wxdw) {
                                if (!d.equals("-")) {
                                    notes += 1;
                                }
                            }
                        }
                    }  else if(getMethod().getName().equals("REZX")) {//任二直选 RSZX
                        sscRenXuan2ManualMethodResult();
                        return;
                    }else if(getMethod().getName().equals("RSZX")){//任三直选 RSZX
                        sscRenXuan3ManualMethodResult();
                        return;
                    }else if(getMethod().getName().equals("RSIZX")){//任四直选 RSIZX
                        sscRenXuan4ManualMethodResult();
                        return;
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

    private static void addPickNumber2Game(Game game, View topView, String title) {
        PickNumber pickNumber2 = new PickNumber(topView, title);
        game.addPickNumber(pickNumber2);
    }

    public static void addInputLayout(Game game, int column) {
        ViewGroup manualInput = game.getManualInput();
        View view =null;
        if("REZX".equals(game.getMethod().getName())//任二直选 RSZX
                ||"RSZX".equals(game.getMethod().getName())//任三直选 RSZX
                ||"RSIZX".equals(game.getMethod().getName())//任四直选 RSIZX
        ){
            view=LayoutInflater.from(manualInput.getContext()).inflate(R.layout.popup_write_comment, null, false);
            game.initDigitPanel(view,column);
            LinearLayout digitLL=view.findViewById(R.id.digit);
            digitLL.setVisibility(View.VISIBLE);
        }else{
            view=createManualInputLayout(manualInput);
        }
        ManualInputView manualInputView = new ManualInputView(view, game.lottery, column);
        game.addManualInputView(manualInputView);
        manualInput.addView(view);
    }

    public static View createManualInputLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.popup_write_comment, null, false);
    }


    public static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column, null, false);
    }

    public static View createDigitPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column_digits, null, false);
    }

    private static void createDigitPicklayout(Game game, String[] name,int digit,String  gameMethod) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDigitPickLayout(game.getTopLayout());
            game.initDigitPanel(view,digit);
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }
        addViewLayout(game,views,gameMethod);
    }

    private static void createPicklayout(Game game, String[] name,String  gameMethod) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            addPickNumber2Game(game, view, name[i]);
            views[i] = view;
        }
        addViewLayout(game,views,gameMethod);
    }

    private static void addViewLayout(Game game,View[] views,String  gameMethod){
        ViewGroup topLayout = game.getTopLayout();

        if( ConstantInformation.HISTORY_CODE_LIST.size()==0
                ||gameMethod==ConstantInformation.NO_YILOU_AND_LENGRE){//没有冷热和遗漏的情况
            ConstantInformation.YI_LOU_IS_SUPPORTED=false;

            for (View view : views) {
                topLayout.addView(view);
            }
        }else{
            ConstantInformation.YI_LOU_IS_SUPPORTED=true;
            ConstantInformation.YI_LOU_IS_SHOW=false;
            ConstantInformation.LENG_RE_IS_SHOW=false;
            ConstantInformation.LENG_RE_COUNT=100;
            addViewLayoutHasLengRe(topLayout,views,gameMethod);
        }

        game.setColumn(views.length);
    }

    private static void addViewLayoutHasLengRe(ViewGroup topLayout,View[] views,String  gameMethod) {
        View sscLengreLayout=LayoutInflater.from(topLayout.getContext()).inflate(R.layout.ssc_lengre_layout, null, false);
        CheckBox yilou_tv=sscLengreLayout.findViewById(R.id.yilou);
        CheckBox yilou_Checkbox=sscLengreLayout.findViewById(R.id.yilou_checked);

        CheckBox lengre_tv=sscLengreLayout.findViewById(R.id.lengre);
        CheckBox lengre_Checkbox=sscLengreLayout.findViewById(R.id.lengre_checked);

        yilou_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.YI_LOU_IS_SHOW =!ConstantInformation.YI_LOU_IS_SHOW;

                yilou_tv.setChecked(ConstantInformation.YI_LOU_IS_SHOW);
                yilou_Checkbox.setChecked(ConstantInformation.YI_LOU_IS_SHOW);

                for (View view : views) {
                    NumberGroupView numberGroupView=view.findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.refreshViewGroup();
                }
            }
        });
        yilou_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.YI_LOU_IS_SHOW =!ConstantInformation.YI_LOU_IS_SHOW;

                yilou_tv.setChecked(ConstantInformation.YI_LOU_IS_SHOW);
                yilou_Checkbox.setChecked(ConstantInformation.YI_LOU_IS_SHOW);

                for (View view : views) {
                    NumberGroupView numberGroupView=view.findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.refreshViewGroup();
                }
            }
        });

        lengre_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lengre_tv.setChecked(true);
                initLengrePopupwindow(views,topLayout.getContext(),v,gameMethod);
            }
        });

        lengre_Checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.LENG_RE_IS_SHOW =!ConstantInformation.LENG_RE_IS_SHOW;
                lengre_tv.setChecked( ConstantInformation.LENG_RE_IS_SHOW);

                //选中了冷热的复选框就会弹出冷热期数对话框
                if(ConstantInformation.LENG_RE_IS_SHOW){
                    initLengrePopupwindow(views,topLayout.getContext(),lengre_tv,gameMethod);
                }

                for (View view : views) {
                    NumberGroupView numberGroupView=view.findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.refreshViewGroup();
                }
            }
        });

        topLayout.addView(sscLengreLayout);

        for (int i=0;i<  views.length;i++) {
            NumberGroupView numberGroupView=views[i].findViewById(R.id.pick_column_NumberGroupView);
            numberGroupView.setmYiLouList(mScCommonGameUtils.getYiLouList(gameMethod,i));
            numberGroupView.setmLengReList(mScCommonGameUtils.getLengReList(gameMethod,i));

            numberGroupView.refreshViewGroup();
            topLayout.addView(views[i]);
        }
    }

    //弹出冷热期数选择框
    private static void initLengrePopupwindow(View[] views,Context  context,View v,String  gameMethod) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.lengre_diff_popupwindow, null);

        BubbleLinearLayout bubbleLinearLayout = (BubbleLinearLayout) rootView.findViewById(R.id.popup_bubble);
        BubblePopupWindow bubblePopupWindow = new BubblePopupWindow(rootView, bubbleLinearLayout);
        RadioButton  lengreOne = (RadioButton) rootView.findViewById(R.id.lengre_one);
        RadioButton lengreTwo = (RadioButton) rootView.findViewById(R.id.lengre_two);
        RadioButton lengreThree = (RadioButton) rootView.findViewById(R.id.lengre_three);

        switch ( ConstantInformation.LENG_RE_COUNT){
            case 20:
                lengreThree.setChecked(true);
                break;
            case 50:
                lengreTwo.setChecked(true);
                break;
            default:
                lengreOne.setChecked(true);
                break;
        }

        lengreOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConstantInformation.LENG_RE_COUNT=100;
                for (int i=0;i<  views.length;i++) {
                    NumberGroupView numberGroupView=views[i].findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.setmYiLouList(mScCommonGameUtils.getYiLouList(gameMethod,i));
                    numberGroupView.setmLengReList(mScCommonGameUtils.getLengReList(gameMethod,i));
                    numberGroupView.refreshViewGroup();
                }

                bubblePopupWindow.dismiss();
            }
        });
        lengreTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.LENG_RE_COUNT=50;
                for (int i=0;i<  views.length;i++) {
                    NumberGroupView numberGroupView=views[i].findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.setmYiLouList(mScCommonGameUtils.getYiLouList(gameMethod,i));
                    numberGroupView.setmLengReList(mScCommonGameUtils.getLengReList(gameMethod,i));
                    numberGroupView.refreshViewGroup();
                }
                bubblePopupWindow.dismiss();
            }
        });
        lengreThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstantInformation.LENG_RE_COUNT=20;
                for (int i=0;i<  views.length;i++) {
                    NumberGroupView numberGroupView=views[i].findViewById(R.id.pick_column_NumberGroupView);
                    numberGroupView.setmYiLouList(mScCommonGameUtils.getYiLouList(gameMethod,i));
                    numberGroupView.setmLengReList(mScCommonGameUtils.getLengReList(gameMethod,i));

                    numberGroupView.refreshViewGroup();
                }
                bubblePopupWindow.dismiss();
            }
        });
        bubblePopupWindow.showArrowTo(v, BubbleStyle.ArrowDirection.Down);
    }

    //五星定位 WXDW
    public static void WXDW(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"},"WXDW");
    }

    //后一直选 YXZX
    public static void YXZX(Game game) {
        createPicklayout(game, new String[]{"个位"},"YXZX");
    }

    //后二直选: EXZX
    public static void EXZX(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"},"EXZX");
        game.setSupportInput(true);
    }

    //后二组选 EXZUX
    public static void EXZUX(final Game game) {
        createPicklayout(game, new String[]{"组二"},"EXZUX");
    }

    //后二连选 EXLX
    public static void EXLX(Game game) {
        createPicklayout(game, new String[]{"十位", "个位"},"EXLX");
        game.setSupportInput(true);
    }

    //后三直选 SXZX
    public static void SXZX(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"},"SXZX");
        game.setSupportInput(true);
    }

    //后三组三 SXZS
    public static void SXZS(Game game) {
        createPicklayout(game, new String[]{"组三"},"SXZS");
    }

    //后三组六 SXZL
    public static void SXZL(Game game) {
        createPicklayout(game, new String[]{"组六"},"SXZL");
    }

    //后三连选 SXLX
    public static void SXLX(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"},"SXLX");
        game.setSupportInput(true);
    }

    //前二组选 QEZUX
    public static void QEZUX(final Game game) {
        createPicklayout(game, new String[]{"组二"},"QEZUX");
        /*final PickNumber pickNumber = game.pickNumbers.get(0);
        pickNumber.setChooseItemClickListener(() -> {
            ArrayList<Integer> numList = pickNumber.getCheckedNumber();
            if (numList.size() > 7) {
                game.onCustomDialog("当前最多只能选择7个号码");
                OTHERRandom(game);
            }
            game.notifyListener();
        });*/
    }

    //前二连选 QELX
    public static void QELX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位"},"QELX");
        game.setSupportInput(true);
    }


    //前二直选 QEZX
    public static void QEZX(Game game) {
        createPicklayout(game, new String[]{"第一位", "第二位"},"QEZX");
        game.setSupportInput(true);
    }

    //前三组六 QSZL
    public static void QSZL(Game game) {
        createPicklayout(game, new String[]{"组六"},"QSZL");
    }

    //前三连选 QSLX
    public static void QSLX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"},"QSLX");
        game.setSupportInput(true);
    }

    //前三直选 QSZX
    public static void QSZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位"},"QSZX");
        game.setSupportInput(true);
    }

    //后三包胆 HSZUXBD
    public static void HSZUXBD(Game game) {
        createPicklayout(game, new String[]{"后三包胆"},"HSZUXBD");
    }

    //前三包胆 QSZUXBD
    public static void QSZUXBD(Game game) {
        createPicklayout(game, new String[]{"前三包胆"},"QSZUXBD");
    }

    //前三组三 QSZS
    public static void QSZS(Game game) {
        createPicklayout(game, new String[]{"组三"},"QSZS");
    }

    //中三直选 ZSZX
    public static void ZSZX(Game game) {
        createPicklayout(game, new String[]{"千位", "百位", "十位"},"ZSZX");
        game.setSupportInput(true);
    }

    //中三包胆 ZSZUXBD
    public static void ZSZUXBD(Game game) {
        createPicklayout(game, new String[]{"中三包胆"},"ZSZUXBD");
    }

    //中三组三 ZSZS
    public static void ZSZS(Game game) {
        createPicklayout(game, new String[]{"组三"},"ZSZS");
    }

    //中三组六 ZSZL
    public static void ZSZL(Game game) {
        createPicklayout(game, new String[]{"组六"},"ZSZL");
    }

    //中三连选 ZSLX
    public static void ZSLX(Game game) {
        createPicklayout(game, new String[]{"千位", "百位", "十位"},"ZSLX");
        game.setSupportInput(true);
    }

    //后四直选 SIXZX
    public static void SIXZX(Game game) {
        createPicklayout(game, new String[]{"千位", "百位", "十位", "个位"},"SIXZX");
        game.setSupportInput(true);
    }

    //前四直选 QSIZX
    public static void QSIZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位"},"QSIZX");
        game.setSupportInput(true);
    }

    //前四组选12 QSIZUX12
    public static void QSIZUX12(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //前四组选6 QSIZUX6
    public static void QSIZUX6(Game game) {
        createPicklayout(game, new String[]{"二重号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //前四组选4 QSIZUX4
    public static void QSIZUX4(Game game) {
        createPicklayout(game, new String[]{"三重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //前四组选24 QSIZUX24
    public static void QSIZUX24(Game game) {
        createPicklayout(game, new String[]{"组选24"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //后四组选12 ZUX12
    public static void ZUX12(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //后四组选6 ZUX6
    public static void ZUX6(Game game) {
        createPicklayout(game, new String[]{"二重号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //后四组选4 ZUX4
    public static void ZUX4(Game game) {
        createPicklayout(game, new String[]{"三重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //后四组选24 ZUX24
    public static void ZUX24(Game game) {
        createPicklayout(game, new String[]{"组选24"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //组选20 ZUX20
    public static void ZUX20(Game game) {
        createPicklayout(game, new String[]{"三重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //五星直选 WXZX
    public static void WXZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"},"WXZX");
        game.setSupportInput(true);
    }

    //组选120 ZUX120
    public static void ZUX120(Game game) {
        createPicklayout(game, new String[]{"组选120"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //组选10 ZUX10
    public static void ZUX10(Game game) {
        createPicklayout(game, new String[]{"三重号位", "二重号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //五星连选 WXLX
    public static void WXLX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"},"WXLX");
        game.setSupportInput(true);
    }

    //组选60 ZUX60
    public static void ZUX60(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //组选5 ZUX5
    public static void ZUX5(Game game) {
        createPicklayout(game, new String[]{"四重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //组选30 ZUX30
    public static void ZUX30(Game game) {
        createPicklayout(game, new String[]{"二重号位", "单号位"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    

    //任三直选 RSZX
    public static void RSZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"},"RSZX");
        game.setSupportInput(true);
    }

    //任二直选 REZX
    public static void REZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"},"REZX");
        game.setSupportInput(true);
    }

    //后三一码不定位 YMBDW
    public static void YMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"YMBDW");
    }

    //前三二码不定位 QSEMBDW
    public static void QSEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"QSEMBDW");
    }

    //四星二码不定位 SXEMBDW
    public static void SXEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"SXEMBDW");
    }

    //后三二码不定位 EMBDW
    public static void EMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"EMBDW");
    }

    //中三一码不定位 ZSYMBDW
    public static void ZSYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"ZSYMBDW");
    }

    //五星一码不定位 WXYMBDW
    public static void WXYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"WXYMBDW");
    }

    //中三二码不定位 ZSEMBDW
    public static void ZSEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"ZSEMBDW");
    }

    //五星二码不定位 WXEMBDW
    public static void WXEMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"WXEMBDW");
    }

    //前三一码不定位 QSYMBDW
    public static void QSYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"QSYMBDW");
    }

    //四星一码不定位 SXYMBDW
    public static void SXYMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"SXYMBDW");
    }

    //五星三码不定位 WXSMBDW
    public static void WXSMBDW(Game game) {
        createPicklayout(game, new String[]{"胆码"},"WXSMBDW");
    }

    //四季发财 SJFC
    public static void SJFC(Game game) {
        createPicklayout(game, new String[]{"胆码"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //一帆风顺 YFFS
    public static void YFFS(Game game) {
        createPicklayout(game, new String[]{"胆码"},"YFFS");
    }

    //好事成双 HSCS
    public static void HSCS(Game game) {
        createPicklayout(game, new String[]{"胆码"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //三星报喜 SXBX
    public static void SXBX(Game game) {
        createPicklayout(game, new String[]{"胆码"},ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //任四直选 RSIZX
    public static void RSIZX(Game game) {
        createPicklayout(game, new String[]{"万位", "千位", "百位", "十位", "个位"},"RSIZX");
        game.setSupportInput(true);
    }

    //后一直选 YXZX
    public static void YXZXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    //定位胆 SXDW
    public static void SXDW(Game game) {
        createPicklayout(game, new String[]{"百位", "十位", "个位"},"SXDW");
    }

    //任二组选 REZUX
    public static void REZUX(Game game) {
        createDigitPicklayout(game, new String[]{"任二组选"},2,ConstantInformation.NO_YILOU_AND_LENGRE);
    }

    //任三组三 RSZS
    public static void RSZS(Game game) {
        createDigitPicklayout(game, new String[]{"任三组三"},3,ConstantInformation.NO_YILOU_AND_LENGRE);
    }
    //任三组六 RSZL
     public static void RSZL(Game game) {
         createDigitPicklayout(game, new String[]{"任三组六"},3,ConstantInformation.NO_YILOU_AND_LENGRE);
     }
    /**============================================手工录入=============================================================**/
    //后二直选 EXZX
     public static void EXZXInput(Game game) {
         addInputLayout(game, game.getColumn());
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
    
    //前二包胆 QEZUXBD
    public static void QEZUXBD(Game game) {
        createPicklayout(game, new String[]{"前二包胆"},"QEZUXBD");
    }
    //后二包胆 EXZUXBD
    public static void EXZUXBD(Game game) {
        createPicklayout(game, new String[]{"后二包胆"},"EXZUXBD");
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

    //前四组选24随机 QSIZUX24
    public static void QSIZUX24Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 4));
        game.notifyListener();
    }

    //前四组选12随机 QSIZUX12
    public static void QSIZUX12Random(Game game) {
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

    //前四组选6随机 QSIZUX6
    public static void QSIZUX6Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 2));
        game.notifyListener();
    }

    //前四组选4随机 QSIZUX4
    public static void QSIZUX4Random(Game game) {
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

    //定位胆 SXDW
    public static void SXDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(0, 9, 1));
        game.notifyListener();
    }

    public static void OTHERRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers) {
            pickNumber.onRandom(random(0, 9, 7));
        }
    }
}
