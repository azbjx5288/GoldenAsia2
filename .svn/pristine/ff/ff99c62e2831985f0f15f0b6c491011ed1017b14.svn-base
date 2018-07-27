package com.goldenasia.lottery.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.RegLinksBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2017/12/8.
 */

public class LinkListAdapter extends RecyclerView.Adapter<LinkListAdapter.ViewHolder> implements View.OnClickListener
{
    private ArrayList<RegLinksBean> data;
    private OnDetailClickListner onDetailClickListner;
    private OnDeleteClickListner onDeleteClickListner;
    
    public void setData(ArrayList<RegLinksBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_list, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        if (data != null && data.size() > 0)
        {
            RegLinksBean regLinksBean = data.get(position);
            holder.channel.setText(regLinksBean.getChannel());
            holder.userAmount.setText(regLinksBean.getUserNum());
            holder.date.setText(regLinksBean.getCreate_time());
            
            holder.detail.setTag(data.get(position));
            holder.delete.setTag(data.get(position));
        }
    }
    
    @Override
    public int getItemCount()
    {
        return data != null ? data.size() : 0;
    }
    
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.detail:
                if (onDetailClickListner != null)
                    onDetailClickListner.onDetailClick(view, (RegLinksBean) view.getTag());
                break;
            case R.id.delete:
                if (onDeleteClickListner != null)
                    onDeleteClickListner.onDeleteClick(view, (RegLinksBean) view.getTag());
                break;
        }
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.channel)
        TextView channel;
        @BindView(R.id.user_amount)
        TextView userAmount;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.detail)
        TextView detail;
        @BindView(R.id.delete)
        ImageView delete;
        
        public ViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setTag(this);

            detail.setOnClickListener(LinkListAdapter.this::onClick);
            delete.setOnClickListener(LinkListAdapter.this::onClick);
        }
    }
    
    public static interface OnDetailClickListner
    {
        public void onDetailClick(View view, RegLinksBean curData);
    }
    
    public static interface OnDeleteClickListner
    {
        public void onDeleteClick(View view, RegLinksBean curData);
    }
    
    public void setOnDetailClickListner(OnDetailClickListner onDetailClickListner)
    {
        this.onDetailClickListner = onDetailClickListner;
    }
    
    public void setOnDeleteClickListner(OnDeleteClickListner onDeleteClickListner)
    {
        this.onDeleteClickListner = onDeleteClickListner;
    }
}
