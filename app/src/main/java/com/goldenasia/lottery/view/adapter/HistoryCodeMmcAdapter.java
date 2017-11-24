package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.db.MmcWinHistory;
import com.goldenasia.lottery.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/9/29.
 */

public class HistoryCodeMmcAdapter extends BaseAdapter {

    private List codeList;

    public HistoryCodeMmcAdapter(){
        this.codeList=new ArrayList<MmcWinHistory>();
    }

    public void setCodeList(List codeList) {
        this.codeList = codeList;
    }

    @Override
    public int getCount() {
        return codeList!=null?codeList.size():0;
    }

    @Override
    public Object getItem(int position) {
        if (codeList == null) {
            return null;
        }
        if (position >= 0 && position < codeList.size()) {
            return codeList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        HistoryCodeMmcAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_result_item_mmc, parent, false);
            holder = new HistoryCodeMmcAdapter.ViewHolder(convertView);
        } else {
            holder = (HistoryCodeMmcAdapter.ViewHolder) convertView.getTag();
        }

        MmcWinHistory historyCode = (MmcWinHistory) codeList.get(position);
        holder.count.setText(historyCode.getCount());
        holder.code.setText(historyCode.getNumber());
        holder.betMoney.setText(historyCode.getBetMoney());
        holder.winMoney.setText(historyCode.getWinMoney());

        if(position%2!=0){
            holder.llHistoryResult.setBackgroundColor(UiUtils.getColor(parent.getContext(),R.color.app_main_rim));
        }else{
            holder.llHistoryResult.setBackgroundColor(UiUtils.getColor(parent.getContext(),R.color.white));
        }

        return convertView;
    }
    static class ViewHolder {
        @Bind(R.id.historycode_count)
        TextView count;
        @Bind(R.id.historycode_code)
        TextView code;
        @Bind(R.id.bet_money)
        TextView betMoney;
        @Bind(R.id.win_money)
        TextView winMoney;
        @Bind(R.id.ll_history_result_item_mmc)
        LinearLayout llHistoryResult;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
