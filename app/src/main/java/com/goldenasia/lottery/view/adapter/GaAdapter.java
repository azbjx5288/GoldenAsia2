package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
    private OnPlayListner onPlayListner;
    private ArrayList<GaBean> data;
    
    public void setData(ArrayList<GaBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }
    
    public interface OnPlayListner
    {
        void onPlay(int position);
        
        void onTrial(int position);
    }
    
    public void setOnPlayListner(OnPlayListner onPlayListner)
    {
        this.onPlayListner = onPlayListner;
    }
    
    private class ViewHolder
    {
        RelativeLayout layout;
        ImageView icon;
        TextView name;
        TextView desc;
        TextView playerNum;
        TextView play;
        TextView trial;
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
            
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
    
        holder.layout = convertView.findViewById(R.id.layout);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(onPlayListner!=null)
                    onPlayListner.onPlay(position);
            }
        });
        holder.icon = convertView.findViewById(R.id.icon);
        holder.name = convertView.findViewById(R.id.name);
        holder.desc = convertView.findViewById(R.id.desc);
        holder.playerNum = convertView.findViewById(R.id.player_num);
        holder.play = convertView.findViewById(R.id.play);
        holder.play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(onPlayListner!=null)
                    onPlayListner.onPlay(position);
            }
        });
        holder.trial = convertView.findViewById(R.id.trial);
        holder.trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(onPlayListner!=null)
                    onPlayListner.onTrial(position);
            }
        });
        
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
