package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.CollectScheme;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SchemeAdapter extends BaseAdapter {

    private List<CollectScheme> collectSchemes = new ArrayList<>();

    @Override
    public int getCount() {
        return collectSchemes.size() > 0 ? collectSchemes.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setData(List<CollectScheme> collectSchemes) {
        this.collectSchemes = collectSchemes;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_scheme_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CollectScheme collectScheme = collectSchemes.get(position);

        holder.checkType.setText(collectScheme.getCname());


        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.chooseType)
        CheckBox checkType;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
