package com.goldenasia.lottery.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.TextView;

import com.goldenasia.lottery.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 刮刮彩的横向滑动View的控制
 * Created by Alashi on 2016/10/13.
 */

public class SlideViewHelper
{
    private static final String TAG = "SlideViewHelper";
    
    private View slideRightButton;
    private View slideLeftButton;
    private TextView slideNumber;
    private TextView slideTotal;
    private SlideView slideView;

    private Context context;
    private String scrapeType;
    private int focusIndex = 0;
    private int total = 20;

    private List<String> cardIds;
    private ArrayList<String> checkCardIds = new ArrayList<>();

    private SparseBooleanArray checkArray = new SparseBooleanArray();

    public interface OnClickListener
    {
        void onClick(int index, boolean checked);
    }

    private OnClickListener onClickListener;

    public void updata()
    {
    }

    public void setDrawables()
    {
        Resources re = context.getResources();
        if ("1".equals(scrapeType))
        {
            slideView.setMainDrawable(re.getDrawable(R.drawable.sxggk_hd_da));
            slideView.setBgDrawable(re.getDrawable(R.drawable.sxguaguaka_hdbg));
            slideView.setCheckedDrawable(re.getDrawable(R.drawable.checked_));
            slideView.setUncheckedDrawable(re.getDrawable(R.drawable.unchecked));
        } else
        {
            slideView.setMainDrawable(re.getDrawable(R.drawable.zqggk_hd_da));
            slideView.setBgDrawable(re.getDrawable(R.drawable.zqguaguaka_hdbg));
            slideView.setCheckedDrawable(re.getDrawable(R.drawable.checked_));
            slideView.setUncheckedDrawable(re.getDrawable(R.drawable.unchecked));
        }
    }

    public SlideViewHelper(View topView)
    {
        context = topView.getContext();
        slideRightButton = topView.findViewById(R.id.slideRight);
        slideLeftButton = topView.findViewById(R.id.slideLeft);
        slideNumber = (TextView) topView.findViewById(R.id.slideNumber);
        slideTotal = (TextView) topView.findViewById(R.id.slideTotal);
        slideView = (SlideView) topView.findViewById(R.id.slideView);

        slideRightButton.setOnClickListener(v -> slideView.slideRight());
        slideLeftButton.setOnClickListener(v -> slideView.slideLeft());

        slideView.setListener(new SlideView.OnChangedListener()
        {
            @Override
            public void OnChanged(boolean left)
            {
                if (left)
                {
                    focusIndex++;
                    if (focusIndex == total)
                    {
                        focusIndex = 0;
                    }
                } else
                {
                    focusIndex--;
                    if (focusIndex < 0)
                    {
                        focusIndex = total - 1;
                    }
                }
                Log.d(TAG, "OnChanged: is 移动方向：" + (left ? "左边" : "右边") + "，下标：" + focusIndex);
                if (cardIds != null)
                {
                    slideNumber.setText("卡号：" + cardIds.get(focusIndex));
                }
            }

            @Override
            public void onClick()
            {
                if (cardIds == null)
                {
                    return;
                }
                Log.d(TAG, "onClick: " + focusIndex);
                if (checkArray.get(focusIndex))
                {
                    checkArray.delete(focusIndex);
                    checkCardIds.remove(cardIds.get(focusIndex));
                    if (onClickListener != null)
                    {
                        onClickListener.onClick(focusIndex, false);
                    }
                } else
                {
                    checkArray.put(focusIndex, true);
                    checkCardIds.add(cardIds.get(focusIndex));
                    if (onClickListener != null)
                    {
                        onClickListener.onClick(focusIndex, true);
                    }
                }
            }

            @Override
            public boolean isChecked()
            {
                return checkArray.get(focusIndex);
            }
        });
    }

    public void setOnClickListener(OnClickListener onClickListener)
    {
        this.onClickListener = onClickListener;
    }

    public void setCardIds(List<String> cardIds)
    {
        this.cardIds = cardIds;
        checkCardIds.clear();
        checkArray.clear();
        focusIndex = 0;
        total = cardIds.size();
        slideTotal.setText("本包还剩" + total + "张");
        slideNumber.setText("卡号：" + cardIds.get(focusIndex));
        slideView.invalidate();
    }

    public List<String> getCardIds()
    {
        return cardIds;
    }

    public ArrayList<String> getCheckCard()
    {
        return checkCardIds;
    }

    public void clearChecked()
    {
        checkCardIds.clear();
        checkArray.clear();
        slideView.invalidate();
    }

    public void cancel(String cardId)
    {
        if (cardIds == null)
        {
            return;
        }
        int index = cardIds.indexOf(cardId);
        checkCardIds.remove(cardId);
        if (index >= 0)
        {
            checkArray.delete(index);
        }
        slideView.invalidate();
    }

    public void pickAll()
    {
        if (cardIds == null)
        {
            return;
        }
        checkCardIds.clear();
        checkArray.clear();
        for (int i = 0; i < total; i++)
        {
            checkArray.put(i, true);
        }
        checkCardIds.addAll(cardIds);
        slideView.invalidate();
    }

    public boolean pick(int num)
    {
        if (cardIds == null)
        {
            return false;
        }
        if (num > total - checkCardIds.size())
        {
            return false;
        }

        for (int i = total - 1; i >= 0; i--)
        {
            if (!checkArray.get(i))
            {
                checkArray.put(i, true);
                checkCardIds.add(cardIds.get(i));
                num--;
            }
            if (num == 0)
            {
                break;
            }
        }
        slideView.invalidate();
        return true;
    }

    public String getScrapeType()
    {
        return scrapeType;
    }

    public void setScrapeType(String scrapeType)
    {
        this.scrapeType = scrapeType;
    }
}
