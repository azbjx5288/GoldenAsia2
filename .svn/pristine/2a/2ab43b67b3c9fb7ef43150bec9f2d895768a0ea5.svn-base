package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.component.DialogLayout;
import com.goldenasia.lottery.component.LimitTextWatcher;
import com.goldenasia.lottery.component.MyAutoCompleteTextView;
import com.goldenasia.lottery.material.ShoppingCart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by ACE-PC on 2016/9/8.
 */
public class ChaseMmcLottery {
    private static final String TAG = ChaseMmcLottery.class.getSimpleName();

    private Context context;
    private TextView totalTextView;
    private CheckBox appendSettings;
    private MyAutoCompleteTextView autoTextView;
    private ArrayList<ChaseRowMmcView> chaseRowLV = new ArrayList<>();
    private SparseArray<Integer> multipleArray = new SparseArray<>();
    private double totalCost = 0.00;
    private OnChaseStartListener onChaseStartListener;

    public ChaseMmcLottery(Context context) {
        this.context = context;
        popupDialog();
    }

    public void setOnChaseStartListener(OnChaseStartListener onChaseStartListener) {
        this.onChaseStartListener = onChaseStartListener;
    }

    private View initChaseLotteryView() {
        View convertView = LayoutInflater.from(context).inflate(R.layout.chase_mmc_fragment, null);
        ViewGroup chaseLVLayout = (ViewGroup) convertView.findViewById(R.id.chase_mmc_listview);
        totalTextView = (TextView) convertView.findViewById(R.id.total_textView);
        appendSettings = (CheckBox) convertView.findViewById(R.id.shopping_mmc_append_settings);//R.array.planets
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(context, R.array.planets, android.R.layout.simple_spinner_item);

        autoTextView = (MyAutoCompleteTextView) convertView.findViewById(R.id.issue_spinner);

        autoTextView.setAdapter(arrayAdapter);
        autoTextView.setText(String.valueOf(arrayAdapter.getItem(0)));
        autoTextView.setSelection(String.valueOf(arrayAdapter.getItem(0)).length());
        autoTextView.setThreshold(1);
        autoTextView.addTextChangedListener(new LimitTextWatcher(autoTextView,4));
        autoTextView.setOnEditorActionListener((v, actionId, event) -> {
            createRowLayout(chaseLVLayout, autoTextView.getText().toString());
            InputMethodManager imm = (InputMethodManager)context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();
            if (isOpen) {
                // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
                imm.hideSoftInputFromWindow(autoTextView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            return false;
        });
        (convertView.findViewById(R.id.add_order)).setOnClickListener((v) -> {
            InputMethodManager imm = (InputMethodManager)context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            boolean isOpen = imm.isActive();
            if (isOpen) {
                // imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);//没有显示则显示
                imm.hideSoftInputFromWindow(autoTextView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            //更新开奖次数
            createRowLayout(chaseLVLayout, autoTextView.getText().toString());
        });
        createRowLayout(chaseLVLayout, autoTextView.getText().toString());
        return convertView;
    }

    private void popupDialog() {
        CustomDialog.Builder builder = new CustomDialog.Builder(context);
        builder.setContentView(initChaseLotteryView()); //处理UI
        builder.setTitle("自定义连续开奖");
        builder.setDisplayLayout(LayoutInflater.from(context).inflate(R.layout.alert_dialog_mmc_layout, null));
        builder.setLayoutSet(DialogLayout.LEFT_AND_RIGHT);
        builder.setPositiveButton("确认开奖", (dialog, which) -> {
            int[] multiples = new int[multipleArray.size()];
            for (int i = 0; i < multipleArray.size(); i++) {
                multiples[i] = multipleArray.get(i);
            }
            if (onChaseStartListener != null) {
                onChaseStartListener.onChaseStart(Integer.parseInt(autoTextView.getText().toString()), multiples,appendSettings.isChecked(), totalCost);
            }
            dialog.dismiss();
        });

        builder.setNegativeButton("返回", (dialog, which) -> {
            dialog.dismiss();
        });
        CustomDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //创建行
    private void createRowLayout(ViewGroup chaseLVLayout, String rows) {
        if(rows.isEmpty()){
            Toast.makeText(context,"请输入开奖次数",Toast.LENGTH_LONG).show();
            return;
        }
        int row=Integer.parseInt(rows);
        if (row == 0 || row > 20) {
            Toast.makeText(context, row == 0 ? "最小连续开奖1次" : "最多连续开奖20次", Toast.LENGTH_LONG).show();
            return;
        }
        multipleArray.clear();
        chaseRowLV.clear();
        chaseLVLayout.removeAllViews();
        View[] views = new View[row];
        for (int i = 0, size = row; i < size; i++) {
            View viewrow = LayoutInflater.from(chaseLVLayout.getContext()).inflate(R.layout.fragment_chase_mmc_item, null, false);
            this.multipleArray.put(i, ShoppingCart.getInstance().getMultiple());
            ChaseRowMmcView chaseRowView = new ChaseRowMmcView(viewrow, i, ShoppingCart.getInstance().getMultiple());
            chaseRowView.setOnInvestmentListener((int newQuantity) -> {
                this.multipleArray.put(chaseRowView.getId(), newQuantity);
                updateData();
            });
            chaseRowLV.add(chaseRowView);
            views[i] = viewrow;
        }
        for (View viewrow : views) {
            chaseLVLayout.addView(viewrow);
        }
        updateData();
    }

    /**
     * 倍数更新
     */
    private void updateData() {
        totalCost=0.00;
        double grandtotal = 0.00;
        for (int i = 0, size = chaseRowLV.size(); i < size; i++) {
            ChaseRowMmcView row = chaseRowLV.get(i);
            int mlp = -1;
            if (this.multipleArray.size() == size) {
                mlp = multipleArray.get(i);
            }
            if (multipleArray.size() == size && mlp > 1000) {
                mlp = 1000;
                multipleArray.put(i, mlp);
            }

            double currentTotal = 0.00;
            if (mlp != -1) {
                currentTotal = ShoppingCart.getInstance().getPlanNotes() * ShoppingCart.getInstance().getLucreMode().getFactor() * 2 * mlp;
                if (i != 0) {
                    grandtotal += currentTotal;
                } else {
                    grandtotal = currentTotal;
                }
            } else {
                currentTotal = ShoppingCart.getInstance().getPlanNotes() * ShoppingCart.getInstance().getLucreMode().getFactor() * 2 * mlp;
                if (i != 0) {
                    grandtotal += currentTotal;
                } else {
                    grandtotal = currentTotal;
                }
            }
            row.updateData(multipleArray.get(i).intValue(), currentTotal, grandtotal);
        }
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
        totalTextView.setText("合计：" + format.format(grandtotal));
        totalCost += grandtotal;
    }

    public interface OnChaseStartListener {
        void onChaseStart(int openCount, int[] traceData, boolean winStop, double totalCost);
    }
}
