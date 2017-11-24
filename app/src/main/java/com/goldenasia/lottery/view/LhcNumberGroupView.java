package com.goldenasia.lottery.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.Calculation;

import java.util.ArrayList;

/**
 * 用于显示六合彩选择时的数字栏
 * Created by Alashi on 2016/1/13.
 */
public class LhcNumberGroupView extends View
{
    private static final String TAG = LhcNumberGroupView.class.getSimpleName();
    
    private Context context;
    private TextPaint paint;
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
    private int rColor,bColor,gColor,otherColor;
    private boolean isColorful = true;
    
    private OnChooseItemClickListener chooseItemListener;
    
    private ArrayList<Integer> pickList;
    private int lastPick;
    
    public LhcNumberGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,context);
    }
    
    public LhcNumberGroupView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(attrs,context);
    }
    
    private void init(AttributeSet attrs,Context context)
    {
        this.context = context;
        TypedArray attribute = getContext().obtainStyledAttributes(attrs, R.styleable.NumberGroupView);
        itemSize = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_itemSize, 48);
        verticalGap = attribute.getDimensionPixelSize(R.styleable.NumberGroupView_verticalGap, 16);
        textColor = attribute.getColor(R.styleable.NumberGroupView_textColor, Color.YELLOW);
        textSize = attribute.getDimension(R.styleable.NumberGroupView_textSize, 36);
        checkedDrawable = attribute.getDrawable(R.styleable.NumberGroupView_checkedDrawable);
        uncheckedDrawable = attribute.getDrawable(R.styleable.NumberGroupView_uncheckedDrawable);
        numberStyle = attribute.getBoolean(R.styleable.NumberGroupView_numberStyle, true);
        chooseMode = attribute.getBoolean(R.styleable.NumberGroupView_chooseMode, false);
        maxNumber = attribute.getInt(R.styleable.NumberGroupView_maxNumber, 9);
        minNumber = attribute.getInt(R.styleable.NumberGroupView_minNumber, 0);
        column = attribute.getInt(R.styleable.NumberGroupView_column, 5);
        attribute.recycle();
    
        rColor = ContextCompat.getColor(context, R.color.lhc_red);
        bColor = ContextCompat.getColor(context, R.color.lhc_blue);
        gColor = ContextCompat.getColor(context, R.color.lhc_green);
        otherColor = ContextCompat.getColor(context, R.color.app_main);
        textCheckedColor = ContextCompat.getColor(context, R.color.white);
        
        paint = new TextPaint();
        paint.setStyle(Paint.Style.FILL);
        
        checkedArray = new SparseBooleanArray(maxNumber - minNumber + 1);
        pickList = new ArrayList<>();
        
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                calculateClick((int) e.getX(), (int) e.getY());
                //Log.d(TAG, "onSingleTapUp: " + Arrays.deepToString(getCheckedNumber().toArray()));
                return true;
            }
            
        });
        
    }
    
    /**
     * 获取选中的数字
     */
    public ArrayList<Integer> getCheckedNumber()
    {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++)
        {
            if (checkedArray.get(i + minNumber))
            {
                list.add(i + minNumber);
            }
        }
        return list;
    }
    
    /**
     * 设置可以选中的数字的范围[min, max]
     */
    public void setNumber(int minNumber, int maxNumber)
    {
        if (this.minNumber == minNumber && this.maxNumber == maxNumber)
        {
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
    public void setDisplayText(String[] displayText)
    {
        this.displayText = displayText;
    }
    
    /**
     * 设置数字显示的尺寸大小，单位像素
     */
    public void setItemSize(int itemSize)
    {
        if (this.itemSize == itemSize)
        {
            return;
        }
        this.itemSize = itemSize;
        requestLayout();
    }
    
    /**
     * 设置一行显示多少个数字
     */
    public void setColumn(int column)
    {
        if (this.column == column)
        {
            return;
        }
        this.column = column;
        requestLayout();
    }
    
    /**
     * 设置选中的数字显示的背景图
     */
    public void setCheckedDrawable(Drawable checkedDrawable)
    {
        this.checkedDrawable = checkedDrawable;
    }
    
    /**
     * 设置未选中的数字显示的背景图
     */
    public void setUncheckedDrawable(Drawable uncheckedDrawable)
    {
        this.uncheckedDrawable = uncheckedDrawable;
    }
    
    /**
     * 行间垂直间隔，单位像素
     */
    public void setVerticalGap(int verticalGap)
    {
        if (this.verticalGap == verticalGap)
        {
            return;
        }
        this.verticalGap = verticalGap;
        requestLayout();
    }
    
    /**
     * 选择模式 true 单选模式 false 多选模式
     */
    public void setChooseMode(boolean chooseMode)
    {
        this.chooseMode = chooseMode;
    }
    
    private boolean isRadioStyle()
    {
        return chooseMode;
    }
    
    /**
     * 数字显示的风格，true 数字显示成“1”，false 数字显示成“01” null显示文字
     */
    public void setNumberStyle(Boolean numberStyle)
    {
        this.numberStyle = numberStyle;
    }
    
    /**
     * 数字显示尺寸
     */
    public void setTextSize(float textSize)
    {
        this.textSize = textSize;
    }
    
    /**
     * 数字显示颜色
     */
    public void setTextColor(int textColor)
    {
        this.textColor = textColor;
    }
    
    public boolean isColorful()
    {
        return isColorful;
    }
    
    public void setColorful(boolean colorful)
    {
        isColorful = colorful;
    }
    
    public void setDisplayMethod(DisplayMethod method)
    {
        this.method = method;
    }
    
    public OnChooseItemClickListener getChooseItemListener()
    {
        return chooseItemListener;
    }
    
    public void setChooseItemListener(OnChooseItemClickListener chooseItemListener)
    {
        this.chooseItemListener = chooseItemListener;
    }
    
    /**
     * 设置被选中的数字(0-9)
     */
    public void setCheckNumber(ArrayList<Integer> checkNumber)
    {
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
    
    private void calculateClick(int eventX, int eventY)
    {
        int x, y;
        Rect rect = new Rect();
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++)
        {
            x = i % column * (itemSize + horizontalGap);
            y = i / column * (itemSize + verticalGap);
            rect.set(x, y, x + itemSize, y + itemSize);
            
            if (rect.contains(eventX, eventY))
            {
                if (isRadioStyle())
                {
                    Log.d(TAG, checkedArray.toString());
                    checkedArray.clear();
                }
                lastPick = i + minNumber;
                checkedArray.put(i + minNumber, !checkedArray.get(i + minNumber));
                if (checkedArray.get(lastPick))
                    pickList.add(lastPick);
                else
                    pickList.remove(Integer.valueOf(lastPick));
                
                if (chooseItemListener != null)
                {
                    chooseItemListener.onChooseItemClick();
                }
                invalidate();
                return;
            }
        }
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        gestureDetector.onTouchEvent(event);
        return true;
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int specSize = MeasureSpec.getSize(widthMeasureSpec);
        horizontalGap = (specSize - column * itemSize) / (column - 1);
        int itemCount = maxNumber - minNumber + 1;
        int line = (itemCount) / column + ((itemCount) % column != 0 ? 1 : 0);
        int specHeight = line * itemSize + (line - 1) * verticalGap;
        setMeasuredDimension(specSize, specHeight);
    }
    
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        
        paint.setTextSize(textSize);
        //paint.setColor(textColor);
        paint.setAntiAlias(true);
        
        checkedDrawable.setBounds(0, 0, itemSize, itemSize);
        uncheckedDrawable.setBounds(0, 0, itemSize, itemSize);
        
        float x, y;
        float offTextY = itemSize * 0.5f + paint.getTextSize() * 0.5f - paint.getTextSize() * 0.2f;
        for (int i = 0, count = maxNumber - minNumber + 1; i < count; i++)
        {
            if (isColorful)
                switch (i)
                {
                    case 0:
                    case 1:
                    case 6:
                    case 7:
                    case 11:
                    case 12:
                    case 17:
                    case 18:
                    case 22:
                    case 23:
                    case 28:
                    case 29:
                    case 33:
                    case 34:
                    case 39:
                    case 44:
                    case 45:
                        paint.setColor(rColor);
                        break;
                    case 2:
                    case 3:
                    case 8:
                    case 9:
                    case 13:
                    case 14:
                    case 19:
                    case 24:
                    case 25:
                    case 30:
                    case 35:
                    case 36:
                    case 40:
                    case 46:
                    case 47:
                        paint.setColor(bColor);
                        break;
                    case 4:
                    case 5:
                    case 10:
                    case 15:
                    case 16:
                    case 20:
                    case 21:
                    case 26:
                    case 27:
                    case 31:
                    case 32:
                    case 37:
                    case 38:
                    case 42:
                    case 43:
                    case 48:
                        paint.setColor(gColor);
                        break;
                }
            else
                paint.setColor(otherColor);
            x = i % column * (itemSize + horizontalGap);
            y = i / column * (itemSize + verticalGap);
            canvas.save();
            canvas.translate(x, y);
            if (checkedArray.get(i + minNumber))
            {
                checkedDrawable.draw(canvas);
                if(!isColorful)
                    paint.setColor(textCheckedColor);
            } else
            {
                uncheckedDrawable.draw(canvas);
                if(!isColorful)
                    paint.setColor(otherColor);
            }
            String text;
            if (numberStyle == null)
            {
                if (displayText.length >= maxNumber)
                    text = moreDisplay(i);
                else
                {
                    Log.e(TAG, "Less than the array size required to display text");
                    text = "";
                }
            } else
            {
                text = String.format(numberStyle ? "%d" : "%02d", i + minNumber);
            }
            float offTextX = (itemSize - paint.measureText(text)) / 2;
            canvas.drawText(text, offTextX, offTextY, paint);
            canvas.restore();
        }
    }
    
    private String moreDisplay(int iteration)
    {
        String text = "";
        switch (method)
        {
            case SINGLE:
                text = displayText[iteration];
                break;
            case TWIN:
                String twin = "";
                for (int i = 0; i < 2; i++)
                {
                    twin += displayText[iteration];
                }
                text = twin;
                break;
            case THREE:
                String three = "";
                for (int i = 0; i < 2; i++)
                {
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
    
    private enum DisplayMethod
    {
        SINGLE, TWIN, THREE, JUNKO, MORE
    }
    
    public int getMinNumber()
    {
        return minNumber;
    }
    
    public int getMaxNumber()
    {
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
    public interface OnChooseItemClickListener
    {
        void onChooseItemClick();
    }
}
