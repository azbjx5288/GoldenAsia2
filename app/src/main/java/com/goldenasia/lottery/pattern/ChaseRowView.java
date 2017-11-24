package com.goldenasia.lottery.pattern;

import android.view.View;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.QuantityView;

/**
 * Created by ACE-PC on 2016/3/31.
 */
public class ChaseRowView implements QuantityView.OnQuantityChangeListener {
    private static final String TAG = ChaseRowView.class.getSimpleName();
    private TextView issueNo;
    private QuantityView multipleSet;
    private TextView multipleText;
    private TextView current;
    private TextView investput;
    private TextView deficit;
    private TextView chaseTotal;
    private TextView interestrate;

    //cost 当前成本　grandTotal 累计投入　Bonus 资金 total 累计利润　TotalProfit 利润率
    private String issue;
    private double bonus = 0.00;
    private int multiple = 1,tagType=0,id = 0,totalProfit = 0;
    private double grandTotal = 0;
    private double cost = 0;
    private OnInvestmentListener onInvestmentListener;

    public ChaseRowView(View view, String issue, double bonus, int position,int tagType) {
        this.id = position;
        this.issue = issue;
        this.bonus = bonus;
        this.tagType=tagType;
        issueNo = (TextView) view.findViewById(R.id.chase_issue);
        multipleSet = (QuantityView) view.findViewById(R.id.chase_multiple);
        multipleText = (TextView) view.findViewById(R.id.chase_multiple_text);

        current = (TextView) view.findViewById(R.id.chase_current);
        investput = (TextView) view.findViewById(R.id.chase_investput);
        deficit = (TextView) view.findViewById(R.id.chase_deficit);
        chaseTotal = (TextView) view.findViewById(R.id.chase_total);

        interestrate = (TextView) view.findViewById(R.id.chase_interestrate);
        if(tagType==0){
            deficit.setVisibility(View.GONE);
            chaseTotal.setVisibility(View.GONE);
            interestrate.setVisibility(View.GONE);
            multipleSet.setVisibility(View.VISIBLE);
            multipleText.setVisibility(View.GONE);
            view.findViewById(R.id.chase_deficit_line).setVisibility(View.GONE);
            view.findViewById(R.id.chase_total_line).setVisibility(View.GONE);
            view.findViewById(R.id.chase_interestrate_line).setVisibility(View.GONE);

        }else{
            deficit.setVisibility(View.VISIBLE);
            chaseTotal.setVisibility(View.VISIBLE);
            interestrate.setVisibility(View.VISIBLE);
            multipleSet.setVisibility(View.GONE);
            multipleText.setVisibility(View.VISIBLE);
            view.findViewById(R.id.chase_deficit_line).setVisibility(View.VISIBLE);
            view.findViewById(R.id.chase_total_line).setVisibility(View.VISIBLE);
            view.findViewById(R.id.chase_interestrate_line).setVisibility(View.VISIBLE);
        }
//        setListener();
    }

    public void setOnInvestmentListener(OnInvestmentListener onInvestmentListener) {
        this.onInvestmentListener = onInvestmentListener;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private void setListener() {
        issueNo.setText(issue);
        if(tagType==0) {
            multipleSet.setQuantity(multiple);
            multipleSet.setMinQuantity(1);
            multipleSet.setMaxQuantity(50000);
            multipleSet.setOnQuantityChangeListener(this);
        }else{
            multipleText.setText(String.valueOf(multiple));
        }

        current.setText(String.format("%.3f", cost));               //当前投入
        investput.setText(String.format("%.3f", grandTotal));       //累计投入
        deficit.setText(String.format("%.3f", bonus));              //当前奖金
        double total = bonus - grandTotal;               //奖金 * 倍数－累计投入＝中奖盈亏
        chaseTotal.setText(String.format("%.3f", total));           //利润率
        interestrate.setText(String.format("%3d%%", totalProfit));
    }

    public void updateData(double bonus, int multiple, double grandTotal, double cost, int totalProfit) {
        this.bonus = bonus;
        this.multiple = multiple;
        this.grandTotal = grandTotal;
        this.cost = cost;
        this.totalProfit = totalProfit;
        setListener();
    }

    @Override
    public void onQuantityChanged(int newQuantity, boolean programmatically) {
        if (onInvestmentListener != null && !programmatically) {
            onInvestmentListener.onInvestment(newQuantity);
        }
    }

    @Override
    public void onLimitReached() {

    }

    public interface OnInvestmentListener {
        void onInvestment(int newQuantity);
    }
}
