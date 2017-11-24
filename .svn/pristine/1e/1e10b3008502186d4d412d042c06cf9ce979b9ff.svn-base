package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.JcRebateOptions;
import com.goldenasia.lottery.data.LhcRebateOptions;
import com.goldenasia.lottery.data.NormalRebateOptions;
import com.goldenasia.lottery.fragment.LowerRebateSetting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACE-PC on 2017/2/2.
 */

public class RebateView {

    private static final String TAG = RebateView.class.getSimpleName();

    private Context context;
    private LinearLayout rebatesLayout;
    private TextView textView;
    private Spinner spinner;

    private OnItemSelectedListener onItemSelectedListener;

    public RebateView(View rebateView) {
        this.context = rebateView.getContext();
        rebatesLayout = (LinearLayout) rebateView.findViewById(R.id.rebateslottery);
        textView = (TextView) rebateView.findViewById(R.id.rebates_lottery_name);
        spinner = (Spinner) rebateView.findViewById(R.id.lottery_spinner_rebate);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    public void setRebatesViewGroup(LinearLayout.LayoutParams params){
        rebatesLayout.setLayoutParams(params);
    }

    public void setNormalRebate(NormalRebateOptions normalRebate, String key) {
        textView.setText(normalRebate.getPropertyName());
        List<String> datalist = new ArrayList<>();
        for (int i = 0, thinSize = normalRebate.getOptions().size(); i < thinSize; i++) {
            double rebate = normalRebate.getOptions().get(i).getRebate() * 100;
            datalist.add(String.format("%.1f%%", rebate));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.item_spinner_text, datalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(onItemSelectedListener!=null){
                    String selected=spinner.getSelectedItem().toString();
                    onItemSelectedListener.onItemSelected(selected,String.valueOf(normalRebate.getPropertyId()),key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });

    }

    public void setJCRebate(JcRebateOptions jcRebate, String key) {
        textView.setText(jcRebate.getMethodName());
        List<String> datalist = new ArrayList<>();
        for (int i = 0, thinSize = jcRebate.getOptions().size(); i < thinSize; i++) {
            double rebate = jcRebate.getOptions().get(i).getRebate() * 100;
            datalist.add(String.format("%.1f%%", rebate));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.item_spinner_text, datalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);//simple_spinner_dropdown_item
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(onItemSelectedListener!=null){
                    String selected=spinner.getSelectedItem().toString();
                    onItemSelectedListener.onItemSelected(selected,jcRebate.getMethodId(),key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });
    }

    public void setLHCRebate(LhcRebateOptions lhcRebate, String key) {
        textView.setText(lhcRebate.getMethodName());
        List<String> datalist = new ArrayList<>();
        for (int i = 0, thinSize = lhcRebate.getOptions().size(); i < thinSize; i++) {
            double rebate = lhcRebate.getOptions().get(i).getRebate() * 100;
            datalist.add(String.format("%.1f%%", rebate));
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(context, R.layout.item_spinner_text, datalist);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_selectable_list_item);//simple_spinner_dropdown_item
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if(onItemSelectedListener!=null){
                    String selected=spinner.getSelectedItem().toString();
                    onItemSelectedListener.onItemSelected(selected,lhcRebate.getMethodId(),key);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }

        });
    }

    /**
     * 选中监听器
     */
    public interface OnItemSelectedListener {
        void onItemSelected(String selected,String id, String key);
    }
}
