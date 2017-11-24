package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.LotteriesHistory;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created on 2016/01/19.
 *
 * @author ACE
 * @功能描述: 历史开奖适配器
 */
public class HistoryLotteryAdapter extends BaseAdapter {
    private List<List<LotteriesHistory>> dataSourceArray;
    private OnItemClickListener clickListener;

    public HistoryLotteryAdapter() {
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getCount() {
        return dataSourceArray != null? dataSourceArray.size(): 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void refresh(List<List<LotteriesHistory>> dataSourceArray) {
        this.dataSourceArray = dataSourceArray;
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lottery_trend, parent, false);
            holder = new ViewHolder(convertView);
            holder.historyBet.setOnClickListener(onClickListener);
            holder.otherMore.setOnClickListener(onClickListener);
            holder.otherList.setOnClickListener(onClickListener);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        List<LotteriesHistory> data = dataSourceArray.get(position);
        if (data.size() == 0) {
            return convertView;
        }

        LotteriesHistory history = data.get(0);
        Integer tagPosition = position;
        holder.historyBet.setTag(tagPosition);
        holder.otherMore.setTag(tagPosition);
        holder.otherList.setTag(tagPosition);
        holder.title.setText(history.getCname());
        holder.issue.setText("第" + history.getIssue() + "期");

        String digit = history.getCode();
        if (digit.contains(" ")) {
            String[] item = digit.split(" ");
            setBallText(item, holder);
        } else {
            CharSequence[] item = new CharSequence[digit.length()];
            for (int i = 0; i < item.length; i++) {
                item[i] = String.valueOf(digit.charAt(i));
            }
            setBallText(item, holder);
        }

        for (int i = 0, size = data.size(); i < holder.otherLayout.length; i++) {
            if (i + 1 < size) {
                history = data.get(i + 1);
                holder.otherCode[i].setText(history.getCode());
                holder.otherIssue[i].setText(history.getIssue());
                holder.otherLayout[i].setVisibility(View.VISIBLE);
            } else {
                holder.otherLayout[i].setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            if (clickListener != null) {
                clickListener.onItemClick(position, dataSourceArray.get(position).get(0).getCname(),
                        v.getId() == R.id.lottery_history_bet);
            }
        }
    };

    private void setBallText(CharSequence[] item, ViewHolder holder) {
        for (int i = 0; i < holder.ballTexts.length; i++) {
            if (i < item.length ) {
                holder.ballTexts[i].setVisibility(View.VISIBLE);
                holder.ballTexts[i].setText(item[i]);
            } else {
                holder.ballTexts[i].setVisibility(View.GONE);
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, String cname, boolean flag);
    }

    // 依据item的layout
    static class ViewHolder {
        @Bind(R.id.lottery_history_title)
        TextView title;
        @Bind(R.id.lottery_history_issue)
        TextView issue;
        @Bind(R.id.lottery_history_code)
        LinearLayout ballList;
        @Bind(R.id.lottery_trend_other_list)
        LinearLayout otherList;
        @Bind(R.id.lottery_other_more)
        View otherMore;
        @Bind(R.id.lottery_history_bet)
        View historyBet;

        TextView[] ballTexts;
        LinearLayout[] otherLayout;
        TextView[] otherIssue;
        TextView[] otherCode;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
            ballTexts = new TextView[ballList.getChildCount()];
            for (int i = 0; i < ballTexts.length; i++) {
                ballTexts[i] = (TextView) ballList.getChildAt(i);
            }

            int childCount = 4;
            otherIssue = new TextView[childCount];
            otherCode = new TextView[childCount];
            otherLayout = new LinearLayout[childCount];
            for (int i = 0, j = 0; j < otherIssue.length && i < otherList.getChildCount(); i++) {
                View tmp = otherList.getChildAt(i);
                if (tmp instanceof LinearLayout) {
                    otherIssue[j] = (TextView) tmp.findViewById(R.id.lottery_historyother_issue);
                    otherCode[j] = (TextView) tmp.findViewById(R.id.lottery_historyother_code);
                    otherLayout[j] = (LinearLayout) tmp;
                    j++;
                }
            }
        }
    }

}
