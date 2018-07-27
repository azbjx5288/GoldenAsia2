package com.goldenasia.lottery.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.GgcMoneyNumberEntity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2016/10/5.
 */

public class GgcCodeAdapter extends BaseAdapter
{
    private ArrayList<GgcMoneyNumberEntity> data;

    public void setData(ArrayList<GgcMoneyNumberEntity> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ggc_code_prize, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        if (data.get(position).isRed())
        {
            viewHolder.number.setTextColor(Color.RED);
            viewHolder.money.setTextColor(Color.RED);
        }
        viewHolder.number.setText(data.get(position).getNumber() + "");
        viewHolder.money.setText("￥" + data.get(position).getMoney());
        return convertView;
    }

    static class ViewHolder
    {
        @BindView(R.id.number)
        TextView number;
        @BindView(R.id.money)
        TextView money;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
