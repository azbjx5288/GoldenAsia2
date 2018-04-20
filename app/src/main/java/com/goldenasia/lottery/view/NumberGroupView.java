package com.goldenasia.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.Calculation;
import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于显示彩票选择时的数字栏
 * Created by Alashi on 2016/1/13.
 */
public class NumberGroupView extends View{
    private static final String TAG = NumberGroupView.class.getSimpleName();

    private TextPaint paint;
    private TextPaint minPaint = new TextPaint();
    private TextPaint maxPaint = new TextPaint();
    private TextPaint normalPaint = new TextPaint();
    private Drawable checkedDrawable;
    private Drawable uncheckedDrawable;
    private int itemSize;
    private int horizontalGap;
    private int verticalGap;
    /**
     * true 数字显示成“1”，false 数字显示成“01” null 显示文字
     */
    private Boolean numberStyle;
    /**
     * true 默认为单选 false 为多选
     */
    private boolean chooseMode;
    private int maxNumber;
    private int minNumber;
    private String[] displayText;
    private int column;
    private DisplayMethod method = DisplayMethod.SINGLE;

    private SparseBooleanArray checkedArray;
    private GestureDetector gestureDetector;
    private float textSize;
    private int textColor,textCheckedColor;


    private OnChooseItemClickListener chooseItemListener;

    private ArrayList<Integer> pickList;
    private int lastPick;
    private List<String>  mYiLouList=new ArrayList<>();
    private List<String>  mLengReList=new ArrayList<>();
    private int MAX_YILOU =0;///遗漏中最大的数
    private int MAX_LENGRE =0;//冷热中最小的数
    private int MIN_LENGRE =0;//冷热中最大的数

    public NumberGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NumberGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.NumberGroupView);
        itemSize = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_itemSize, 48);
        verticalGap = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_verticalGap, 16);
        textColor = attribute.getColor(R.styleable.NumberGroupView_textColor, Color.YELLOW);
        textCheckedColor = attribute.getColor(R.styleable.NumberGroupView_textCheckedColor, Color.YELLOW);
        textSize = attribute.getDimension(R.styleable.NumberGroupView_textSize, 36);
        checkedDrawable = attribute.getDrawable(R.styleable.NumberGroupView_checkedDrawable);
        uncheckedDrawable = attribute.getDrawable(R.styleable.NumberGroupView_uncheckedDrawable);
        numberStyle = attribute.getBoolean(R.styleable.NumberGroupView_numberStyle, true);
        chooseMode = attribute.getBoolean(R.styleable.NumberGroupView_chooseMode, false);
        maxNumber = attribute.getInt(R.styleable.NumberGroupView_maxNumber, 9);
        minNumber = attribute.getInt(R.styleable.NumberGroupView_minNumber, 0);
        column = attribute.getInt(R.styleable.NumberGroupView_column, 5);
        attribute.recycle();

        paint = new TextPaint();
        paint.setStyle(Paint.Style.FILL);

        checkedArray = new SparseBooleanArray(maxNumber - minNumber + 1);
        pickList = new ArrayList<>();

        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                calculateClick((int) e.getX(), (int) e.getY());
                //Log.d(TAG, "onSingleTapUp: " + Arrays.deepToString(getCheckedNumber().toArray()));
                return true;
            }

        });

    }

    /**
     * 获取选中的数字
     */
    public ArrayList<Integer> getCheckedNumber() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++) {
            if (checkedArray.get(i + minNumber)) {
                list.add(i + minNumber);
            }
        }
        return list;
    }

    /**
     * 设置可以选中的数字的范围[min, max]
     */
    public void setNumber(int minNumber, int maxNumber) {
        if (this.minNumber == minNumber && this.maxNumber == maxNumber) {
            return;
        }
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;
        checkedArray = new SparseBooleanArray(maxNumber - minNumber + 1);
        requestLayout();
    }

    /**
     * 设置需要显示的文字集合
     **/
    public void setDisplayText(String[] displayText) {
        this.displayText = displayText;
    }

    /**
     * 设置数字显示的尺寸大小，单位像素
     */
    public void setItemSize(int itemSize) {
        if (this.itemSize == itemSize) {
            return;
        }
        this.itemSize = itemSize;
        requestLayout();
    }

    /**
     * 设置一行显示多少个数字
     */
    public void setColumn(int column) {
        if (this.column == column) {
            return;
        }
        this.column = column;
        requestLayout();
    }

    /**
     * 设置选中的数字显示的背景图
     */
    public void setCheckedDrawable(Drawable checkedDrawable) {
        this.checkedDrawable = checkedDrawable;
    }

    /**
     * 设置未选中的数字显示的背景图
     */
    public void setUncheckedDrawable(Drawable uncheckedDrawable) {
        this.uncheckedDrawable = uncheckedDrawable;
    }

    /**
     * 行间垂直间隔，单位像素
     */
    public void setVerticalGap(int verticalGap) {
        if (this.verticalGap == verticalGap) {
            return;
        }
        this.verticalGap = verticalGap;
        requestLayout();
    }

    /**
     * 选择模式 true 单选模式 false 多选模式
     */
    public void setChooseMode(boolean chooseMode) {
        this.chooseMode = chooseMode;
    }

    private boolean isRadioStyle() {
        return chooseMode;
    }

    /**
     * 数字显示的风格，true 数字显示成“1”，false 数字显示成“01” null显示文字
     */
    public void setNumberStyle(Boolean numberStyle) {
        this.numberStyle = numberStyle;
    }

    /**
     * 数字显示尺寸
     */
    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    /**
     * 数字显示颜色
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextCheckedColor(int textCheckedColor) { this.textCheckedColor = textCheckedColor; }

    public void setDisplayMethod(DisplayMethod method) {
        this.method = method;
    }

    public OnChooseItemClickListener getChooseItemListener() {
        return chooseItemListener;
    }

    public void setChooseItemListener(OnChooseItemClickListener chooseItemListener) {
        this.chooseItemListener = chooseItemListener;
    }

    /**
     * 设置被选中的数字(0-9)
     */
    public void setCheckNumber(ArrayList<Integer> checkNumber) {
        checkedArray.clear();
        pickList.clear();
        for (int number : checkNumber) {
            checkedArray.put(number, true);
            pickList.add(number);
            lastPick = number;
        }
        invalidate();
        //Log.d(TAG, "setCheckNumber: " + Arrays.deepToString(getCheckedNumber().toArray()));
    }

    private void calculateClick(int eventX, int eventY) {
        int x, y;
        Rect rect = new Rect();
        int  yiLouHeight= ConstantInformation.YI_LOU_IS_SHOW ?itemSize:0;
        int  lengReHeight= ConstantInformation.LENG_RE_IS_SHOW ?itemSize:0;

        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++) {
            x = i % column * (itemSize + horizontalGap);
            y = i / column * (itemSize + verticalGap);
            rect.set(x, y, x + itemSize, y + itemSize+yiLouHeight+lengReHeight);

            if (rect.contains(eventX, eventY)) {
                if (isRadioStyle()) {
                    Log.d(TAG, checkedArray.toString());
                    checkedArray.clear();
                }
                lastPick = i + minNumber;
                checkedArray.put(i + minNumber, !checkedArray.get(i + minNumber));
                if (checkedArray.get(lastPick))
                    pickList.add(lastPick);
                else
                    pickList.remove(Integer.valueOf(lastPick));

                if (chooseItemListener != null) {
                    chooseItemListener.onChooseItemClick();
                }

                invalidate();
                return;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.i(TAG,"onMeasure(int widthMeasureSpec, int heightMeasureSpec) ....  ");

        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        horizontalGap = (specSize - column * itemSize) / (column - 1);
        int itemCount = maxNumber - minNumber + 1;
        int line = (itemCount) / column + ((itemCount) % column != 0 ? 1 : 0);

        int  yiLouHeight= ConstantInformation.YI_LOU_IS_SHOW ?itemSize:0;
        int  lengReHeight= ConstantInformation.LENG_RE_IS_SHOW ?itemSize:0;

        int specHeight = line * (itemSize+yiLouHeight+lengReHeight) + (line - 1) * verticalGap;

        setMeasuredDimension(specSize, specHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
        minPaint.set(paint);
        maxPaint.set(paint);
        normalPaint.set(paint);
        paint.setColor(textColor);
        minPaint.setColor(getResources().getColor(R.color.app_chart_shiball_color));
        maxPaint.setColor(getResources().getColor(R.color.app_main_support));
        normalPaint.setColor(getResources().getColor(R.color.gray));

        checkedDrawable.setBounds(0, 0, itemSize, itemSize);
        uncheckedDrawable.setBounds(0, 0, itemSize, itemSize);

        float x, y;
        float offTextY = itemSize * 0.5f + paint.getTextSize() * 0.5f - paint.getTextSize() * 0.2f;

        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++) {
            x = i % column * (itemSize + horizontalGap);
            y = i / column * (itemSize + verticalGap);
            canvas.save();
            if(y==0){
                canvas.translate(x, y);
            }else{
                int  yiLouHeight= ConstantInformation.YI_LOU_IS_SHOW ?itemSize:0;
                int  lengReHeight= ConstantInformation.LENG_RE_IS_SHOW ?itemSize:0;
                canvas.translate(x, y+yiLouHeight+lengReHeight);
            }

            if (checkedArray.get(i + minNumber)) {
                checkedDrawable.draw(canvas);
                paint.setColor(textCheckedColor);
            } else {
                uncheckedDrawable.draw(canvas);
                paint.setColor(textColor);
            }
            String text = null;
            if (numberStyle == null) {
                if (displayText.length >= maxNumber)
                    text = moreDisplay(i);
                else {
                    Log.e(TAG, "Less than the array size required to display text");
                    text = "";
                }
            } else {
                text = String.format(numberStyle ? "%d" : "%02d", i + minNumber);
            }
            float offTextX = (itemSize - paint.measureText(text)) / 2;
            canvas.drawText(text, offTextX, offTextY, paint);

            /*添加遗漏和冷热具体数据start*/
            int  yiLouHeight=itemSize;
            if(ConstantInformation.YI_LOU_IS_SHOW&&mYiLouList.size()>0) {
                if(mYiLouList.get(i).equals(String.valueOf(MAX_YILOU))){
                    canvas.drawText(mYiLouList.get(i), offTextX,  offTextY+itemSize, minPaint);
                }else{
                    canvas.drawText(mYiLouList.get(i), offTextX,  offTextY+itemSize, normalPaint);
                }
                yiLouHeight+=itemSize;
            }
			
            if(ConstantInformation.LENG_RE_IS_SHOW&&mLengReList.size()>0) {
                if(mLengReList.get(i).equals(String.valueOf(MAX_LENGRE))){
                    canvas.drawText(mLengReList.get(i), offTextX,  offTextY+yiLouHeight, maxPaint);
                }else if(mLengReList.get(i).equals(String.valueOf(MIN_LENGRE))){
                    canvas.drawText(mLengReList.get(i), offTextX,  offTextY+yiLouHeight, minPaint);
                }else{
                    canvas.drawText(mLengReList.get(i), offTextX,  offTextY+yiLouHeight, normalPaint);
                }
            }
            /*添加遗漏和冷热具体数据end*/

            canvas.restore();
        }
    }

    private String moreDisplay(int iteration) {
        String text = "";
        switch (method) {
            case SINGLE:
                text = displayText[iteration];
                break;
            case TWIN:
                String twin = "";
                for (int i = 0; i < 2; i++) {
                    twin += displayText[iteration];
                }
                text = twin;
                break;
            case THREE:
                String three = "";
                for (int i = 0; i < 2; i++) {
                    three += displayText[iteration];
                }
                text = three;
                break;
            case JUNKO:
                Calculation.getInstance().combine(displayText, 3);

                break;
            case MORE:

                break;
            default:
                text = displayText[iteration];
        }
        return text;
    }

    private enum DisplayMethod {
        SINGLE, TWIN, THREE, JUNKO, MORE
    }

    public int getMinNumber() {
        return minNumber;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public SparseBooleanArray getCheckedArray() {
        return checkedArray;
    }

    public void setCheckedArray(SparseBooleanArray checkedArray) {
        this.checkedArray = checkedArray;
    }

    public ArrayList<Integer> getPickList() {
        return pickList;
    }

    public void setPickList(ArrayList<Integer> pickList) {
        this.pickList = pickList;
    }

    public int getLastPick() {
        return lastPick;
    }

    public void setLastPick(int lastPick) {
        this.lastPick = lastPick;
    }

    public interface OnChooseItemClickListener {
        void onChooseItemClick();
    }

    public void setmYiLouList(List<String> yiLouList) {
        this.mYiLouList = yiLouList;
        //取出遗漏中最大值
        MAX_YILOU=Integer.parseInt(yiLouList.get(0));
        for(int i=1;i<yiLouList.size();i++){
            if(MAX_YILOU<Integer.parseInt(yiLouList.get(i))){
                MAX_YILOU=Integer.parseInt(yiLouList.get(i));
            }
        }
    }

    public void setmLengReList(List<String> lengReList) {
        this.mLengReList = lengReList;

        //取出冷热中最大值和最小值
        MAX_LENGRE=Integer.parseInt(lengReList.get(0));
        MIN_LENGRE=Integer.parseInt(lengReList.get(0));
        for(int i=1;i<lengReList.size();i++){
            if(MAX_LENGRE<Integer.parseInt(lengReList.get(i))){
                MAX_LENGRE=Integer.parseInt(lengReList.get(i));
            }
            if(MIN_LENGRE>Integer.parseInt(lengReList.get(i))){
                MIN_LENGRE=Integer.parseInt(lengReList.get(i));
            }
        }

    }

    //刷新界面
    public void refreshViewGroup(){
        requestLayout();
    }

}
