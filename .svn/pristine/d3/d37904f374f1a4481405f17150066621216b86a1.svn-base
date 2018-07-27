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
import com.goldenasia.lottery.data.ServiceSystemBean;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.util.UiUtils;

import java.util.ArrayList;
import java.util.List;


public class CustometServiceAdapter extends BaseAdapter
{
    private List<ServiceSystemBean> data;

    public CustometServiceAdapter(List<ServiceSystemBean> data) {
        this.data = data;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_dialog_item, parent, false);
            holder = new ViewHolder();
            
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
    
        holder.button_name = convertView.findViewById(R.id.button_name);

        if(position%2==0){
            holder.button_name.setText(data.get(position).getName());
            holder.button_name.setBackground(UiUtils.getDrawable(parent.getContext(),R.drawable.background_service_customer_bg_01));
        }else{
            holder.button_name.setText(data.get(position).getName());
            holder.button_name.setBackground(UiUtils.getDrawable(parent.getContext(),R.drawable.background_service_customer_bg_02));
        }

        return convertView;
    }

    private class ViewHolder
    {
        TextView button_name;
    }

}
