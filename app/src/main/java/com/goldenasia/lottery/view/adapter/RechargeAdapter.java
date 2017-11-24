package com.goldenasia.lottery.view.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.base.net.GsonHelper;
import com.goldenasia.lottery.data.RechargeConfig;
import com.goldenasia.lottery.material.ConstantInformation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/11/15.
 */

public class RechargeAdapter extends BaseAdapter
{
    private List<RechargeConfig> cardList;
    private OnRechargeMethodClickListener onRechargeMethodClickListener;
    
    public RechargeAdapter(List<RechargeConfig> cardList)
    {
        this.cardList = cardList;
    }
    
    public void setData(List<RechargeConfig> cardList)
    {
        this.cardList = cardList;
        notifyDataSetChanged();
    }
    
    public void setOnRechargeMethodClickListener(OnRechargeMethodClickListener onRechargeMethodClickListener)
    {
        this.onRechargeMethodClickListener = onRechargeMethodClickListener;
    }
    
    @Override
    public int getCount()
    {
        return cardList == null ? 0 : cardList.size();
    }
    
    @Override
    public RechargeConfig getItem(int position)
    {
        return cardList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recharge_pick, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RechargeConfig item = getItem(position);
        viewHolder.rechargeName.setText(item.getPayName());
        
        Drawable img_off = parent.getContext().getResources().getDrawable(ConstantInformation.getRechargeLogo(item
                .getTradeType(), item.getBankId()));
        img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
        viewHolder.rechargeName.setCompoundDrawables(img_off, null, null, null); //设置左图标
        
        //根据Item位置分配不同背景
    /*    if (cardList.size() > 0) {
            if (cardList.size() == 1) {
                viewHolder.background.setBackgroundResource(R.drawable.settings_item);
            } else {
                if (position == 0) {
                    viewHolder.background.setBackgroundResource(R.drawable.settings_itemtop_press);
                } else if (position == cardList.size() - 1) {
                    viewHolder.background.setBackgroundResource(R.drawable.settings_itembottom_press);
                } else {
                    viewHolder.background.setBackgroundResource(R.drawable.settings_itemmiddle_press);
                }
            }
        }*/
        //当RadioButton被选中时，将其状态记录进States中，并更新其他RadioButton的状态使它们不被选中
        viewHolder.rechargeSelect.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                GoldenAsiaApp.getUserCentre().setRechargeMode(GsonHelper.toJson(item));
                notifyDataSetChanged();
            }
        });
        
        viewHolder.background.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //重置，确保最多只有一项被选中
                GoldenAsiaApp.getUserCentre().setRechargeMode(GsonHelper.toJson(item));
                notifyDataSetChanged();
            }
        });
        
        RechargeConfig channelSelect = GsonHelper.fromJson(GoldenAsiaApp.getUserCentre().getRechargeMode(),
                RechargeConfig.class);
        if (channelSelect != null)
        {
            if (channelSelect.getBankId() == item.getBankId() && channelSelect.getTradeType() == item.getTradeType())
            {
                viewHolder.rechargeSelect.setChecked(true);
                if (onRechargeMethodClickListener != null)
                {
                    onRechargeMethodClickListener.onRechargeMethodListener(item, position);
                }
            } else
            {
                viewHolder.rechargeSelect.setChecked(false);
            }
        } else
        {
            if (position == 0)
            {
                viewHolder.rechargeSelect.setChecked(true);
                if (onRechargeMethodClickListener != null)
                {
                    onRechargeMethodClickListener.onRechargeMethodListener(item, position);
                }
            } else
            {
                viewHolder.rechargeSelect.setChecked(false);
            }
        }
        
        return convertView;
    }
    
    static class ViewHolder
    {
        @Bind(R.id.choose_recharge)
        RelativeLayout background;
        @Bind(R.id.recharge_select)
        RadioButton rechargeSelect;
        @Bind(R.id.recharge_name)
        TextView rechargeName;
        
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
    
    /**
     * 选中监听器
     */
    public interface OnRechargeMethodClickListener
    {
        void onRechargeMethodListener(RechargeConfig card, int position);
    }
}
