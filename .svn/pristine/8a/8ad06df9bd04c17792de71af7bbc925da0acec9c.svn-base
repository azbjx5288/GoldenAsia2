package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.material.ShoppingCart;
import com.goldenasia.lottery.material.Ticket;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingMmcAdapter extends BaseAdapter
{

    private List<Ticket> ticketsList;
    private OnDeleteItemClickListener onDeleteClickListener;

    public ShoppingMmcAdapter()
    {
        ticketsList = ShoppingCart.getInstance().getCodesMap();
    }

    public void setOnDeleteClickListener(OnDeleteItemClickListener onDeleteClickListener)
    {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @Override
    public int getCount()
    {
        return ticketsList != null ? ticketsList.size() : 0;
    }

    @Override
    public Object getItem(int position)
    {
        return 0;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_fragment_mmc_reveal,
                    parent, false);
            holder = new ViewHolder(convertView);
            holder.delButton.setOnClickListener(onClickListener);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = ticketsList.get(position);

        holder.playName.setText(ticket.getChooseMethod().getCname()); //玩法名
        holder.noteNumber.setText(String.format("%d 注", ticket.getChooseNotes())); //注数
        holder.code.setText(ticket.getCodes()); //Code
        holder.delButton.setTag(position);

        return convertView;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            ShoppingCart.getInstance().deleteCode((Integer) v.getTag());
            notifyDataSetChanged();
            onDeleteClickListener.onDeleteItemClick();
        }
    };

    static class ViewHolder
    {
        @BindView(R.id.shopping_item_method)
        TextView playName;
        @BindView(R.id.shopping_item_code)
        TextView code;
        @BindView(R.id.shopping_item_notenum)
        TextView noteNumber;
        @BindView(R.id.shopping_item_delete)
        ImageButton delButton;

        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnDeleteItemClickListener
    {
        void onDeleteItemClick();
    }
}