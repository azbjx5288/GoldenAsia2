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
import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.PackageBean;
import com.goldenasia.lottery.material.ConstantInformation;
import com.goldenasia.lottery.user.UserCentre;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2016/1/15.
 */
public class MemberOrderAdapter extends BaseAdapter
{
    private static final int STATE_WIN = 0;
    private static final int STATE_FAIL = 1;
    private static final int STATE_DOING = 2;
    private static final int STATE_DONE = 3;
    
    private List dataList;
    private int[] colors = new int[4];
    private UserCentre userCentre;
    private SparseArray<String> states;
    
    public MemberOrderAdapter(Context context)
    {
        userCentre = GoldenAsiaApp.getUserCentre();
        colors[STATE_WIN] = ContextCompat.getColor(context, R.color.app_main);
        colors[STATE_FAIL] = ContextCompat.getColor(context, R.color.app_font_dark_color);
        colors[STATE_DOING] = ContextCompat.getColor(context, R.color.app_font_dark_color);
        colors[STATE_DONE] = ContextCompat.getColor(context, R.color.app_font_dark_color);
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
        if (data instanceof PackageBean)
            setData(viewHolder, (PackageBean) data);
        return convertView;
    }
    
    private void setData(ViewHolder viewHolder, PackageBean packageBean)
    {
        viewHolder.icon.setImageResource(ConstantInformation.getLotteryLogo(Integer.valueOf(packageBean.getLottery_id()), true));
        viewHolder.money.setText(packageBean.getAmount() + "元");
        viewHolder.time.setText(packageBean.getCreate_time());
        Lottery lottery = userCentre.getLottery(Integer.valueOf(packageBean.getLottery_id()));
        if (lottery != null)
        {
            viewHolder.name.setText(lottery.getCname());
        } else
        {
            viewHolder.name.setText("未知彩种");
        }
        
        switch (Integer.valueOf(packageBean.getCheck_prize_status()))
        {
            case 0:
                viewHolder.prize.setVisibility(View.GONE);
                viewHolder.state.setTextColor(colors[STATE_DOING]);
                viewHolder.state.setText(states.get(Integer.valueOf(packageBean.getCancel_status())));
                break;
            
            case 1:
                viewHolder.prize.setVisibility(View.VISIBLE);
                viewHolder.state.setTextColor(colors[STATE_WIN]);
                viewHolder.state.setText("已中奖");
                viewHolder.prize.setText(packageBean.getPrize());
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
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.money)
        TextView money;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.state)
        TextView state;
        @BindView(R.id.prize)
        TextView prize;
        
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
