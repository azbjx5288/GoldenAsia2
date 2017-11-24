package com.goldenasia.lottery.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.GgcCardEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2016/10/5.
 */

public class GgcCardListAdapter extends BaseAdapter {
    private static final int TAIL_DIGIT = 9;
    private ArrayList<GgcCardEntity> data;

    public void setData(ArrayList<GgcCardEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        if (data == null)
            return null;
        if (position >= 0 && position < data.size())
            return data.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ggc_card_list, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        String number = data.get(position).getSerial_num();
        String tail = number.substring(number.length() - TAIL_DIGIT, number.length());
        viewHolder.number.setText("编号：" + tail);
        viewHolder.prize.setText(data.get(position).getPrize());

        if ("1".equals(data.get(position).getScrape_type())) {
            if ("1".equals(data.get(position).getStatus())) {
                viewHolder.imageView.setImageResource(R.drawable.sxggk_list_used);
                /*ImageLoader.getInstance().displayImage("drawable://" + R.drawable.sxggk_list_used, viewHolder
                        .imageView);*/

                if (!"0.00".equals(data.get(position).getPrize())) {
                    viewHolder.prize.setText("已中奖\n" + data.get(position).getPrize());
                    viewHolder.prize.setBackgroundColor(Color.RED);
                } else {
                    viewHolder.prize.setText("未中奖");
                    viewHolder.prize.setBackgroundColor(Color.GRAY);
                }
                viewHolder.prize.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageView.setImageResource(R.drawable.sxggk_list_ys);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.sxggk_list_ys, viewHolder
                // .imageView);
                viewHolder.prize.setVisibility(View.INVISIBLE);
            }
        } else {
            if ("1".equals(data.get(position).getStatus())) {
                viewHolder.imageView.setImageResource(R.drawable.zqggk_list_used);
                /*ImageLoader.getInstance().displayImage("drawable://" + R.drawable.zqggk_list_used, viewHolder
                        .imageView);*/

                if (!"0.00".equals(data.get(position).getPrize())) {
                    viewHolder.prize.setText("已中奖" + data.get(position).getPrize());
                    viewHolder.prize.setBackgroundColor(Color.RED);
                } else {
                    viewHolder.prize.setText("未中奖");
                    viewHolder.prize.setBackgroundColor(Color.GRAY);
                }
                viewHolder.prize.setVisibility(View.VISIBLE);
            } else {
                viewHolder.imageView.setImageResource(R.drawable.zqggk_list_ys);
                //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.zqggk_list_ys, viewHolder
                // .imageView);
                viewHolder.prize.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.prize)
        TextView prize;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
