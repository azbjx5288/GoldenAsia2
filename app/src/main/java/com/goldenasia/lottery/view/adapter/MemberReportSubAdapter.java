package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.MemberReportBean;
import com.goldenasia.lottery.material.Ticket;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/10/13.
 * 会员报表适配器
 */
public class MemberReportSubAdapter extends BaseAdapter {

    private List<MemberReportBean> list;

    public MemberReportSubAdapter(List<MemberReportBean> list) {
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
    public Object getItem(int position) {
        return 0;
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

        MemberReportBean memberReportBean = list.get(position);

        holder.item_1.setText(memberReportBean.getItem_1());
        holder.item_2.setText(memberReportBean.getItem_2());
        holder.item_3.setText(memberReportBean.getItem_3());
        holder.item_4.setText(memberReportBean.getItem_4());
        holder.item_5.setText(memberReportBean.getItem_5());
        holder.item_6.setText(memberReportBean.getItem_6());
        holder.item_7.setText(memberReportBean.getItem_7());
        holder.item_8.setText(memberReportBean.getItem_8());
        holder.item_9.setText(memberReportBean.getItem_9());

        return convertView;
    }

    static class ViewHolder {
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
        @Bind(R.id.item_7)
        TextView item_7;
        @Bind(R.id.item_8)
        TextView item_8;
        @Bind(R.id.item_9)
        TextView item_9;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

}