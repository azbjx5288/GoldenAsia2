package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.BindCardDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/5/5.
 */
public class BankCardAdapter extends BaseAdapter {

    private List bankCardList;
    public BankCardAdapter(List bankCardList) {
        this.bankCardList = bankCardList;
    }
    public void setData(List bankCardList) {
        this.bankCardList = bankCardList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return bankCardList != null ? bankCardList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        if (bankCardList == null) {
            return null;
        }
        if (position >= 0 && position < bankCardList.size()) {
            return bankCardList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bankcard_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BindCardDetail bindCard=(BindCardDetail)bankCardList.get(position);
        viewHolder.bankName.setText(bindCard.getUsername());
        viewHolder.bankCard.setText(bindCard.getCardNum());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.bank_name)
        TextView bankName;
        @BindView(R.id.bank_card)
        TextView bankCard;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
