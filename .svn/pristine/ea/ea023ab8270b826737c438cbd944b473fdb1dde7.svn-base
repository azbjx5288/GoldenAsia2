package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;

/**
 * Created by Sakura on 2017/3/13.
 */

public class FavouriteAdapter extends BaseAdapter {
    private ArrayList<Lottery> data;

    public void setData(ArrayList<Lottery> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    private class GirdHolder {
        ImageView icon;
        CheckBox checkBox;
        TextView name;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        GirdHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite,
                    null);
            holder = new GirdHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        } else {
            holder = (GirdHolder) convertView.getTag();
        }

        if (data != null && data.size() > 0) {
            Lottery lottery = data.get(position);
            holder.icon.setImageResource(ConstantInformation.getLotteryLogo(lottery.getLotteryId(), lottery
                    .isAvailable()));
            holder.name.setText(lottery.getCname());
            convertView.setId(lottery.getLotteryId());
        } else {
        }

        return convertView;
    }
}
