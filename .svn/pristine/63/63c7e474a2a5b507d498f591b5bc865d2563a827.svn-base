package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2016/10/5.
 */

public class GgcCartAdapter extends BaseAdapter
{
    private static final int TAIL_DIGIT = 5;

    private ArrayList<String> data;
    private String scrapeType;

    public void setData(ArrayList<String> data, String scrapeType)
    {
        this.data = data;
        this.scrapeType = scrapeType;
        notifyDataSetChanged();
    }

    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }

    @Override
    public String getItem(int position)
    {
        if (data == null)
            return null;
        if (position >= 0 && position < data.size())
            return data.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ggc_cart, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
            viewHolder = (ViewHolder) convertView.getTag();

        String number = data.get(position);
        viewHolder.tailNum.setText("尾号：" + number.substring(number.length() - TAIL_DIGIT, number.length()));
        viewHolder.close.setImageResource(R.drawable.shanchu);
        //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.shanchu, viewHolder.close);
        if ("1".equals(scrapeType))
            viewHolder.imageView.setImageResource(R.drawable.sxggk_buy);
            //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.sxggk_buy, viewHolder.imageView);
        else
            viewHolder.imageView.setImageResource(R.drawable.zqggk_buy);
        //ImageLoader.getInstance().displayImage("drawable://" + R.drawable.zqggk_buy, viewHolder.imageView);

        return convertView;
    }

    static class ViewHolder
    {
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.tail_num)
        TextView tailNum;
        @BindView(R.id.close)
        ImageView close;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
