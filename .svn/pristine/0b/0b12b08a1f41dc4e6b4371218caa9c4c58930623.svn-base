package com.goldenasia.lottery.pattern;

import android.view.View;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.AutoFitTextView;
import com.goldenasia.lottery.component.QuantityView;

/**
 * Created by ACE-PC on 2016/3/31.
 */
public class ChaseRowMmcView implements QuantityView.OnQuantityChangeListener {
    private static final String TAG = ChaseRowMmcView.class.getSimpleName();
    private TextView issueNo;
    private QuantityView multipleSet;
    private AutoFitTextView current;
    private AutoFitTextView investput;

    private int id = 0,multiple = 1;
    private double currentTotal = 0.00,grandTotal = 0.00;
    private OnInvestmentListener onInvestmentListener;

    public ChaseRowMmcView(View view, int position, int multiple) {
        this.id = position;
        this.multiple = multiple;
        issueNo = (TextView) view.findViewById(R.id.chase_mmc_issue);
        issueNo.setText("第" + (id+1) + "次");
        multipleSet = (QuantityView) view.findViewById(R.id.chase_mmc_multiple);
        current = (AutoFitTextView) view.findViewById(R.id.chase_mmc_current);
        investput = (AutoFitTextView) view.findViewById(R.id.chase_mmc_investput);
        setListener();
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
        multipleSet.setQuantity(this.multiple);
        multipleSet.setMinQuantity(1);
        multipleSet.setMaxQuantity(1000);
        multipleSet.setOnQuantityChangeListener(this);

        current.setText(String.format("%.2f", currentTotal));   //当前投入
        investput.setText(String.format("%.2f", grandTotal));   //累计投入
    }

    public void updateData(int multiple,double currentTotal, double grandTotal) {
        this.multiple = multiple;
        this.currentTotal = currentTotal;
        this.grandTotal = grandTotal;
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
