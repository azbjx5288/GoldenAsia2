package com.goldenasia.lottery.pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.goldenasia.lottery.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2016/10/17.
 */

public class GgcMethodPopupWindow extends PopupWindow
{
    private static final String TAG = "GgcMethodPopupWindow";

    private ListView listView;

    private Context context;
    private ArrayList<String> methodList;

    private OnChooseListner onChooseListner;
    private MyAdapter adapter;

    public GgcMethodPopupWindow(Context context, ArrayList<String> methodList)
    {
        super(context);
        this.context = context;
        this.methodList = methodList;
        View view = LayoutInflater.from(context).inflate(R.layout.popup_window_ggc_method, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        setOutsideTouchable(true);

        listView = (ListView) view.findViewById(R.id.list_view);
        adapter = new MyAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                if (onChooseListner != null)
                    onChooseListner.onChoose(i);
                dismiss();
            }
        });
    }

    public interface OnChooseListner
    {
        public void onChoose(int i);
    }

    public OnChooseListner getOnChooseListner()
    {
        return onChooseListner;
    }

    public void setOnChooseListner(OnChooseListner onChooseListner)
    {
        this.onChooseListner = onChooseListner;
    }

    private class MyAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return methodList.size();
        }

        @Override
        public Object getItem(int i)
        {
            return null;
        }

        @Override
        public long getItemId(int i)
        {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup)
        {
            ViewHolder viewHolder;
            if (view == null)
            {
                view = LayoutInflater.from(context).inflate(R.layout.item_ggc_method, viewGroup, false);
                viewHolder = new ViewHolder(view);
            } else
                viewHolder = (ViewHolder) view.getTag();

            viewHolder.methodName.setText(methodList.get(i));
            return view;
        }
    }

    static class ViewHolder
    {
        @Bind(R.id.method_name)
        TextView methodName;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
}
