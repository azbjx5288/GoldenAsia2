package com.goldenasia.lottery.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2017/6/5.
 */

public class IssueNoAdapter extends BaseAdapter {
    private static final String TAG = IssueNoAdapter.class.getSimpleName();

    private int currentItem = -1;
    private boolean dupo=false;
    private Context context;
    private List<Integer> datalist;
    private OnIssueNoClickListener onIssueNoClickListener;

    public IssueNoAdapter(Context context, List<Integer> datalist) {
        this.context=context;
        this.datalist = datalist;
    }

    public void setOnIssueNoClickListener(OnIssueNoClickListener onIssueNoClickListener) {
        this.onIssueNoClickListener = onIssueNoClickListener;
    }

    @Override
    public int getCount() {
        return datalist != null || datalist.size() >= 0 ? datalist.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spiner_item_layout, null);
            holderView = new ViewHolder(convertView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        // 记录的点击位置来设置"对应Item"的可见性（在list依次加载列表数据时，每加载一个时都看一下是不是需改变可见性的那一条）
        if (currentItem == position) {
            holderView.layout.setBackgroundResource(R.color.app_main);//Color.parseColor("#16A085")
            if(!dupo){
                dupo=true;
                if(onIssueNoClickListener!=null){
                    onIssueNoClickListener.onInitialAmount(position);
                }
            }
        } else {
            holderView.layout.setBackgroundResource(R.color.background);//Color.parseColor("#e2e2e2")
            if(!dupo){
                dupo=true;
                if(onIssueNoClickListener!=null){
                    onIssueNoClickListener.onInitialAmount(position);
                }
            }
        }

        holderView.textView.setTag(position);
        holderView.textView.setText(datalist.get(position)+"");
        holderView.textView.setPadding(0,15,0,15);

        holderView.textView.setOnClickListener((View view) -> {
            // 用 currentItem 记录点击位置
            int tag = (Integer) view.getTag();
            if(tag != currentItem) {
                currentItem = tag;
                if(onIssueNoClickListener!=null){
                    onIssueNoClickListener.onIssueNoListener(position);
                }
                notifyDataSetChanged(); // 必须有的一步
            }
        });

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.textView)
        TextView textView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    /**
     * 选中监听器
     */
    public interface OnIssueNoClickListener {
        void onIssueNoListener(int position);

        void onInitialAmount(int position);
    }
}