package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.GaBean;
import com.goldenasia.lottery.material.ConstantInformation;

import java.util.ArrayList;

/**
 * Created by Sakura on 2017/3/15.
 */

public class GaAdapter extends BaseAdapter
{
    private ArrayList<GaBean> data;
    
    public void setData(ArrayList<GaBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }
    
    private class ViewHolder
    {
        ImageView icon;
        TextView name;
        TextView desc;
        TextView playerNum;
    }
    
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }
    
    public Object getItem(int position)
    {
        return null;
    }
    
    public long getItemId(int position)
    {
        return position;
    }
    
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ga_listview, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.icon);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.playerNum = (TextView) convertView.findViewById(R.id.player_num);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        
        if (data != null && data.size() > 0)
        {
            GaBean gaBean = data.get(position);
            holder.icon.setImageResource(ConstantInformation.getLotteryLogo(gaBean.getLotteryId(), true));
            holder.name.setText(gaBean.getCname());
            holder.desc.setText(gaBean.getDesc());
            holder.playerNum.setText(gaBean.getPlayerNum());
            //convertView.setId(gaBean.getLotteryId());
        } else
        {
        }
        
        return convertView;
    }
}
