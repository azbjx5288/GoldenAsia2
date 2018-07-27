package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.LowerMember;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/5/3.
 */
public class LowerMemberAdapter extends BaseAdapter
{
    private OnManageListner onManageListner;
    private List lowerMemberList;
    private boolean isHiddenEditImage=false;
    
    public LowerMemberAdapter(List lowerMemberList, boolean isHiddenEditImage)
    {
        this.lowerMemberList = lowerMemberList;
        this.isHiddenEditImage=isHiddenEditImage;
    }
    
    public void setData(List lowerMemberList)
    {
        this.lowerMemberList = lowerMemberList;
        notifyDataSetChanged();
    }
    
    public interface OnManageListner
    {
        void onEdit(int position);
        
        void onDetal(int position);
    }
    
    public void setOnManageListner(OnManageListner onManageListner)
    {
        this.onManageListner = onManageListner;
    }
    
    @Override
    public int getCount()
    {
        return lowerMemberList != null ? lowerMemberList.size() : 0;
    }
    
    @Override
    public Object getItem(int position)
    {
        if (lowerMemberList == null)
        {
            return null;
        }
        if (position >= 0 && position < lowerMemberList.size())
        {
            return lowerMemberList.get(position);
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lower_member_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        LowerMember lowerMembers = (LowerMember) lowerMemberList.get(position);
        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (onManageListner != null)
                    onManageListner.onDetal(position);
            }
        });
        viewHolder.lowerUsername.setText(lowerMembers.getUsername());
        viewHolder.enroltime.setText(lowerMembers.getRegTime());
        viewHolder.lowerStatus.setText(getStatus(lowerMembers.getStatus()));
        viewHolder.lowerMoney.setText(String.format("%.2f", lowerMembers.getBalance()));
        viewHolder.edit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (onManageListner != null)
                    onManageListner.onEdit(position);
            }
        });
        viewHolder.edit.setVisibility(isHiddenEditImage?View.GONE :View.VISIBLE);
        
        return convertView;
    }
    
    static class ViewHolder
    {
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.lower_username)
        TextView lowerUsername;
        @BindView(R.id.enroltime)
        TextView enroltime;
        @BindView(R.id.lower_status)
        TextView lowerStatus;
        @BindView(R.id.lower_money)
        TextView lowerMoney;
        @BindView(R.id.edit)
        ImageButton edit;
        
        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
    
    //    level  0=总代；1=一代；2=二代；3=三代；4=四代；5=五代；6=六代；10=普通用户
    private String getLevel(int index)
    {
        String name = "";
        switch (index)
        {
            case 0:
                name = "总代";
                break;
            case 1:
                name = "一代";
                break;
            case 2:
                name = "二代";
                break;
            case 3:
                name = "三代";
                break;
            case 4:
                name = "四代";
                break;
            case 5:
                name = "五代";
                break;
            case 6:
                name = "六代";
                break;
            case 10:
                name = "普通用户";
        }
        return name;
    }
    
    
    //0=已删除；1=冻结；5=已回收；8=正常
    
    private String getStatus(int index)
    {
        String name = "";
        switch (index)
        {
            case 0:
                name = "已删除";
                break;
            case 1:
                name = "冻结";
                break;
            case 5:
                name = "已回收";
                break;
            case 8:
                name = "正常";
                break;
        }
        return name;
    }
}
