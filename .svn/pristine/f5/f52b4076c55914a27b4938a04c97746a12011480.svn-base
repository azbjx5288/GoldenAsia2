package com.goldenasia.lottery.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.IssueEntity;
import com.goldenasia.lottery.util.UiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/3/1.
 */
public class HistoryCodeAdapter extends BaseAdapter {

    private List codeList;
    private int mLotteryId;
    private Context mContext;

    public HistoryCodeAdapter(List codeList,int lotteryId,Context context){
        this.codeList=codeList;
        mLotteryId=lotteryId;
        mContext=context;
    }

    @Override
    public int getCount() {
        return codeList!=null?codeList.size():0;
    }

    @Override
    public Object getItem(int position) {
        if (codeList == null) {
            return null;
        }
        if (position >= 0 && position < codeList.size()) {
            return codeList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public  void setData(List codeList){
        this.codeList=codeList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mLotteryId==14) {//山东快乐扑克
            ViewHolderShandongKuailePuke holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_result_item_shandongkuailepuke, parent, false);
                holder = new ViewHolderShandongKuailePuke(convertView);
            } else {
                holder = (ViewHolderShandongKuailePuke) convertView.getTag();
            }
            IssueEntity historyCode=(IssueEntity)codeList.get(position);
            holder.issue.setText(historyCode.getIssue());

            //开奖号码
            String codeOpen=historyCode.getCode();
            holder.textSdlklpu01.setText((codeOpen.charAt(0)+"").replace("T", "10"));
            holder.textSdlklpu02.setText((codeOpen.charAt(3)+"").replace("T", "10"));
            holder.textSdlklpu03.setText((codeOpen.charAt(6)+"").replace("T", "10"));

            //图片
            holder.imageSdlklpu01.setImageDrawable(letterToDrawable(codeOpen.charAt(1)+""));
            holder.imageSdlklpu02.setImageDrawable(letterToDrawable(codeOpen.charAt(4)+""));
            holder.imageSdlklpu03.setImageDrawable(letterToDrawable(codeOpen.charAt(7)+""));

        }else{
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_result_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            IssueEntity historyCode=(IssueEntity)codeList.get(position);
            holder.issue.setText(historyCode.getIssue());
            holder.code.setText(historyCode.getCode());
        }

        return convertView;
    }

    //根据字母判定花色  花色♠s,♥h ♣c ♦d(一律小写)
    private Drawable letterToDrawable(String letter){
        if ("s".equals(letter)){
            return UiUtils.getDrawable(mContext,R.drawable.ht_icon);
        }else if("h".equals(letter)){
            return UiUtils.getDrawable(mContext,R.drawable.hongt_icon);
        }else if("c".equals(letter)){
            return UiUtils.getDrawable(mContext,R.drawable.mh_icon);
        }else if("d".equals(letter)){
            return UiUtils.getDrawable(mContext,R.drawable.fk_icon);
        }else{
            return null;
        }
    }

    static class ViewHolderShandongKuailePuke {
        @BindView(R.id.historycode_issue)
        TextView issue;
        @BindView(R.id.image_sdlklpu01)
        ImageView imageSdlklpu01;
        @BindView(R.id.image_sdlklpu02)
        ImageView imageSdlklpu02;
        @BindView(R.id.image_sdlklpu03)
        ImageView imageSdlklpu03;
        @BindView(R.id.text_sdlklpu01)
        TextView textSdlklpu01;
        @BindView(R.id.text_sdlklpu02)
        TextView textSdlklpu02;
        @BindView(R.id.text_sdlklpu03)
        TextView textSdlklpu03;

        public ViewHolderShandongKuailePuke(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    static class ViewHolder {
        @BindView(R.id.historycode_issue)
        TextView issue;
        @BindView(R.id.historycode_code)
        TextView code;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
