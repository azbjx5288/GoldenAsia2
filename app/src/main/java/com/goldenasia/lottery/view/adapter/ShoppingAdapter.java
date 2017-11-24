package com.goldenasia.lottery.view.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ShoppingCart;
import com.goldenasia.lottery.material.Ticket;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShoppingAdapter extends BaseAdapter {

    private List<Ticket> ticketsList;
    private OnDeleteItemClickListener onDeleteClickListener;

    public ShoppingAdapter(List<Ticket> tickets) {
        ticketsList = tickets;
    }

    public void setOnDeleteClickListener(OnDeleteItemClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public void setData(List<Ticket> tickets) {
        ticketsList = tickets;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return ticketsList != null? ticketsList.size() : 0;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_fragment_reveal, parent, false);
            holder = new ViewHolder(convertView);
            holder.delButton.setOnClickListener(onClickListener);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = ticketsList.get(position);

        holder.playName.setText(ticket.getChooseMethod().getCname()); //玩法名
        String isShoppingNote=convertView.getContext().getResources().getString(R.string.is_shopping_note);
        isShoppingNote= StringUtils.replaceEach(isShoppingNote, new String[]{"NOTE"}, new String[]{""+ticket.getChooseNotes()});
        holder.noteNumber.setText(Html.fromHtml(isShoppingNote)); //注数
        holder.code.setText(ticket.getCodes()); //Code
        holder.delButton.setTag(position);

        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onDeleteClickListener!=null){
                onDeleteClickListener.onDeleteItemClick((Integer) v.getTag());
            }
        }
    };

    static class ViewHolder {
        @Bind(R.id.shopping_item_method)
        TextView playName;
        @Bind(R.id.shopping_item_code)
        TextView code;
        @Bind(R.id.shopping_item_notenum)
        TextView noteNumber;
        @Bind(R.id.shopping_item_delete)
        ImageButton delButton;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnDeleteItemClickListener {
        void onDeleteItemClick(int position);
    }
}