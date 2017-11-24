package com.goldenasia.lottery.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.GoldenAsiaApp;
import com.goldenasia.lottery.data.Bet;
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Trace;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.user.UserCentre;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/1/15.
 */
public class GameHistoryAdapter extends BaseAdapter
{
    private static final int STATE_WIN = 0;
    private static final int STATE_FAIL = 1;
    private static final int STATE_DOING = 2;
    private static final int STATE_DONE = 3;
    
    private List dataList;
    private int[] colors = new int[4];
    private UserCentre userCentre;
    private SparseArray<String> states;
    
    public GameHistoryAdapter(Context context)
    {
        userCentre = GoldenAsiaApp.getUserCentre();
        colors[STATE_WIN] = ContextCompat.getColor(context,R.color.app_main);
        colors[STATE_FAIL] = ContextCompat.getColor(context,R.color.app_font_dark_color);
        colors[STATE_DOING] = ContextCompat.getColor(context,R.color.app_font_dark_color);
        colors[STATE_DONE] = ContextCompat.getColor(context,R.color.app_font_dark_color);
        states = new SparseArray<>();
        states.put(0, "未开奖");
        states.put(1, "用户撤单");
        states.put(2, "追中撤单");
        states.put(3, "出号撤单");
        states.put(4, "未开撤单");
        states.put(9, "管理员撤单");
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game_history, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        
        Object data = this.dataList.get(position);
        if (data instanceof Bet)
        {
            setBetData(viewHolder, (Bet) data);
        } else
        {
            viewHolder.prize.setVisibility(View.GONE);
            setTraceData(viewHolder, (Trace) data);
        }
        return convertView;
    }
    
    private void setTraceData(ViewHolder viewHolder, Trace data)
    {
        viewHolder.icon.setImageResource(ConstantInformation.getLotteryLogo(data.getLotteryId(), true));
        viewHolder.money.setText(data.getTotalAmount() + "元");
        viewHolder.time.setText(data.getCreateTime());
        Lottery lottery = userCentre.getLottery(data.getLotteryId());
        if (lottery != null)
        {
            viewHolder.name.setText(lottery.getCname());
        } else
        {
            viewHolder.name.setText("未知彩种");
        }
        
        viewHolder.prize.setVisibility(View.GONE);
        switch (data.getStatus())
        {
            case 0:
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText("未开始");
                break;
            
            case 1:
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText("进行中");
                break;
            
            case 2:
                viewHolder.state.setTextColor(colors[STATE_DONE]);
                viewHolder.state.setText("已完成");
                break;
            
            case 3:
                viewHolder.state.setTextColor(colors[STATE_DONE]);
                viewHolder.state.setText("已取消");
                break;
        }
    }
    
    private void setBetData(ViewHolder viewHolder, Bet bet)
    {
        viewHolder.icon.setImageResource(ConstantInformation.getLotteryLogo(bet.getLotteryId(), true));
        viewHolder.money.setText(bet.getAmount() + "元");
        viewHolder.time.setText(bet.getCreateTime());
        Lottery lottery = userCentre.getLottery(bet.getLotteryId());
        if (lottery != null)
        {
            viewHolder.name.setText(lottery.getCname());
        } else
        {
            viewHolder.name.setText("未知彩种");
        }
        
        switch (bet.getCheckPrizeStatus())
        {
            case 0:
                viewHolder.prize.setVisibility(View.GONE);
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText(states.get(bet.getCancelStatus()));
                break;
            
            case 1:
                viewHolder.prize.setVisibility(View.VISIBLE);
                viewHolder.state.setTextColor(colors[STATE_WIN]);
                viewHolder.state.setText("已中奖");
                viewHolder.prize.setText(bet.getPrize());
                break;
            
            case 2:
                viewHolder.prize.setVisibility(View.GONE);
                viewHolder.state.setTextColor(colors[STATE_FAIL]);
                viewHolder.state.setText("未中奖");
                break;
        }
    }
    
    static class ViewHolder
    {
        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.money)
        TextView money;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.state)
        TextView state;
        @Bind(R.id.prize)
        TextView prize;
        
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
