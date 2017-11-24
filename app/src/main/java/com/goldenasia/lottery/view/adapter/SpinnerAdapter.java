package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Platform;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.material.Ticket;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpinnerAdapter extends BaseAdapter {

    private List<Platform> transferList;

    public SpinnerAdapter() {
        transferList = ConstantInformation.getTransferArray();
    }

    @Override
    public int getCount() {
        return transferList != null? transferList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.spiner_item_layout, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Platform platform = transferList.get(position);
        holder.noteNumber.setText(platform.getName()); //注数
        convertView.setId(platform.getId());
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.textView)
        TextView noteNumber;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

}