package com.goldenasia.lottery.view.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.MemberReportBean;
import com.goldenasia.lottery.material.Ticket;
import com.goldenasia.lottery.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/13.
 * 会员报表适配器
 */
public class MemberReportSubAdapter extends BaseAdapter {

    private List<MemberReportBean> list;
    private Context context;

    public MemberReportSubAdapter(Context context,List<MemberReportBean> list) {
        this.context=context;
        this.list = list;
    }

    public void setData(List<MemberReportBean> memberReportBeanList) {
        list = memberReportBeanList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public MemberReportBean getItem(int position) {
        return  list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_report_sub_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MemberReportBean memberReportBean =getItem(position);

        if (memberReportBean.getLevel().equals("10")) {//非代理账号显示
            holder.item_1.setText(memberReportBean.getItem_1());
        } else {
            holder.item_1.setText(Html.fromHtml("<u>"+memberReportBean.getItem_1()+"</u>"));
        }

        holder.item_2.setText(memberReportBean.getItem_2());
        holder.item_3.setText(memberReportBean.getItem_3());
        holder.item_4.setText(memberReportBean.getItem_4());
        holder.item_5.setText(memberReportBean.getItem_5());
        holder.item_6.setText(memberReportBean.getItem_6());
        holder.item_7.setText(memberReportBean.getItem_7());
        holder.item_8.setText(memberReportBean.getItem_8());

        //item9
        String moneyString=memberReportBean.getItem_9();
        double money=Double.parseDouble(moneyString);
        if(money>0){
            holder.item_9.setTextColor(UiUtils.getColor(context,R.color.app_main));
        }else{
            holder.item_9.setTextColor(UiUtils.getColor(context,R.color.app_main_support));
        }
        holder.item_9.setText(moneyString);

        holder.item_10.setText(memberReportBean.getItem_10());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_1)
        TextView item_1;
        @BindView(R.id.item_2)
        TextView item_2;
        @BindView(R.id.item_3)
        TextView item_3;

        @BindView(R.id.item_4)
        TextView item_4;
        @BindView(R.id.item_5)
        TextView item_5;
        @BindView(R.id.item_6)
        TextView item_6;
        @BindView(R.id.item_7)
        TextView item_7;
        @BindView(R.id.item_8)
        TextView item_8;
        @BindView(R.id.item_9)
        TextView item_9;
        @BindView(R.id.item_10)
        TextView item_10;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

}