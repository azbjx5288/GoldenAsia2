package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.GaGameListResponse;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/21.
 */
public class GameGAHistoryAdapter extends BaseAdapter
{

    private List<GaGameListResponse.ListBean> dataList;

    public GameGAHistoryAdapter(List<GaGameListResponse.ListBean> dataList )
    {
        this.dataList=dataList;
    }
    
    public void setData(List data)
    {
        this.dataList = data;
        notifyDataSetChanged();
    }
    
    @Override
    public int getCount()
    {
        return dataList == null ? 0 : dataList.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        if (dataList == null)
        {
            return null;
        }
        if (position >= 0 && position < dataList.size())
        {
            return dataList.get(position);
        }
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_ga_history, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GaGameListResponse.ListBean data = this.dataList.get(position);

        viewHolder.item_1.setText(data.getGame_name());
        viewHolder.item_2.setText(data.getBelong_date());
        viewHolder.item_3.setText(data.getGa_buy_amount());
        viewHolder.item_4.setText(data.getGa_prize_amount());
        viewHolder.item_5.setText(data.getGa_rebate_amount());
        viewHolder.item_6.setText(data.getGa_win_lose());

        return convertView;
    }
    
    static class ViewHolder
    {
        @Bind(R.id.item_1)
        TextView item_1;
        @Bind(R.id.item_2)
        TextView item_2;
        @Bind(R.id.item_3)
        TextView item_3;
        @Bind(R.id.item_4)
        TextView item_4;
        @Bind(R.id.item_5)
        TextView item_5;
        @Bind(R.id.item_6)
        TextView item_6;
        
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
