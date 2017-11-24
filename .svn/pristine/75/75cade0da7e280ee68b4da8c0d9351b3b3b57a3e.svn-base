package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.goldenasia.lottery.R;

import org.apache.commons.lang3.StringUtils;

/**
 * 提示选择注数 订单金额
 * Created by ACE-PC on 2016/2/5.
 */
public class ChooseTips  {
    private Context context;
    private TextView bettingText;		// 提示信息
    private TextView balanceText;			// 金额

    public ChooseTips(View tipView){
        this.context=tipView.getContext();
        bettingText = (TextView) tipView.findViewById(R.id.betting_tips);
        balanceText = (TextView) tipView.findViewById(R.id.balance_tips);
    }

    /** 提示信息 */
    public void setTipsText(String notice,String money,String balance){
        String isBettingTips=context.getResources().getString(R.string.is_betting_tips);
        isBettingTips = StringUtils.replaceEach(isBettingTips, new String[]{"NOTICE", "MONEY"},new String[]{notice,money});
        bettingText.setText(Html.fromHtml(isBettingTips));
        String isBalanceTips=context.getResources().getString(R.string.is_balance_tips);
        isBalanceTips = StringUtils.replaceEach(isBalanceTips, new String[]{"BALANCE"},new String[]{balance});
        balanceText.setText(Html.fromHtml(isBalanceTips));
    }
}
