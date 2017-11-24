package com.goldenasia.lottery.pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.MmcEntity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/9/8.
 */
public class ChaseMmcWinView {
    private Context context;

    public ChaseMmcWinView(Context context) {
        this.context = context;
    }

    @SuppressLint("NewApi")
    public static class BuilderView {
        private Context context;
        private LinearLayout winnersLayout;
        private Button positiveButton;
        private Button negativeButton;
        private Button doubleButton;

        private String winners;
        private boolean singleTip = false;
        private boolean playDouble = false;
        private ArrayList<MmcEntity> openCodeList = new ArrayList<>();
        private CustomDialog.Builder builder;
        private ChaseMmcWinAdapter adapter;
        private OnPlayAgainListener onPlayAgainListener;
        private OnCancelListener onCancelListener;
        private OnPlayDoubleListner onPlayDoubleListner;

        private NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);

        public BuilderView(Context context) {
            this.context = context;
            this.builder = new CustomDialog.Builder(context);
        }

        public BuilderView setOpenCodeList(ArrayList<MmcEntity> openCodeList) {
            this.openCodeList = openCodeList;
            return this;
        }

        /**
         * 中奖最大金额显示
         *
         * @param winners
         * @return
         */
        public BuilderView setWinners(String winners) {
            this.winners = winners;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param message
         * @return
         */
        public BuilderView setWinners(int message) {
            this.winners = (String) context.getText(message);
            return this;
        }

        /**
         * 单次或者多次
         *
         * @return
         */
        public boolean isSingleOrMore() {
            return playDouble;
        }

        public BuilderView setSingleOrMore(boolean playDouble) {
            this.playDouble = playDouble;
            return this;
        }

        /**
         * 单次提示
         *
         * @return
         */
        public boolean isSingleTip() {
            return singleTip;
        }

        public BuilderView setSingleTip(boolean singleTip) {
            this.singleTip = singleTip;
            return this;
        }

        public void setOnPlayAgainListener(OnPlayAgainListener onPlayAgainListener) {
            this.onPlayAgainListener = onPlayAgainListener;
        }

        public void setOnCancelListener(OnCancelListener onCancelListener) {
            this.onCancelListener = onCancelListener;
        }

        public void setOnPlayDoubleListner(OnPlayDoubleListner onPlayDoubleListner) {
            this.onPlayDoubleListner = onPlayDoubleListner;
        }

        @SuppressLint("NewApi")
        public ChaseMmcWinView create() {
            ChaseMmcWinView mmcWinView = new ChaseMmcWinView(context);
            View view = initChaseListView();
            builder.setContentView(view); //处理UI
            builder.setTitleHideOrShow(false);
            builder.setBottomHideOrShow(false);
            CustomDialog dialog = builder.create();
            dialog.setOnDismissListener((d) -> {
                        if (onCancelListener != null) {
                            onCancelListener.onCancel();
                        }
                    }
            );
            dialog.show();


            winnersLayout = (LinearLayout) view.findViewById(R.id.winners_layout);

            positiveButton = (Button) view.findViewById(R.id.positiveButton);
            positiveButton.setOnClickListener((View v) -> {
                if (onPlayAgainListener != null) {
                    onPlayAgainListener.onPlayAgain();
                }
                dialog.dismiss();
            });

            doubleButton = (Button) view.findViewById(R.id.doubleButton);
            doubleButton.setOnClickListener((View v) -> {
                if (onPlayDoubleListner != null) {
                    onPlayDoubleListner.onPlayDouble();
                }
                dialog.dismiss();
            });

            /*if (isSingleTip())
            {
                winnersLayout.setVisibility(View.GONE);
            } else
            {
                winnersLayout.setVisibility(View.VISIBLE);
            }*/

            if (isSingleOrMore()) {
                doubleButton.setVisibility(View.VISIBLE);
            } else {
                doubleButton.setVisibility(View.GONE);
            }
            return mmcWinView;
        }

        private View initChaseListView() {
            View convertView = LayoutInflater.from(context).inflate(R.layout.chase_mmc_listview_fragment, null);
            ((TextView) convertView.findViewById(R.id.tip_award)).setText(winners);
//            (convertView.findViewById(R.id.spacingview)).setVisibility(isSingleOrMore() ? View.VISIBLE : View.GONE);

            ListView chaseLV = (ListView) convertView.findViewById(R.id.chase_win_listview);
            chaseLV.setVisibility(isSingleTip() ? View.GONE : View.VISIBLE);
            adapter = new ChaseMmcWinAdapter();
            View headView = LayoutInflater.from(context).inflate(R.layout.chase_mmc_listview_item, null);
            headView.findViewById(R.id.mmc_win_row).setBackgroundResource(R.color.white);
            chaseLV.addHeaderView(headView);
            chaseLV.setAdapter(adapter);
            return convertView;
        }

        public class ChaseMmcWinAdapter extends BaseAdapter {

            @Override
            public int getCount() {
                return openCodeList != null ? openCodeList.size() : 0;
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
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chase_mmc_listview_item, parent, false);
                    holder = new ViewHolder(convertView);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                MmcEntity mmcEntity = openCodeList.get(position);
                Spannable wordtoSpan = new SpannableString("第" + (position + 1) + "次开奖");
                wordtoSpan.setSpan(new ForegroundColorSpan(Color.parseColor("#129FB4")), 1, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mmcOrder.setText(wordtoSpan);
                holder.mmcOrder.setTextSize(14);
                holder.mmcOrder.setTextColor(Color.parseColor("#454443"));
                holder.mmcCode.setText(mmcEntity.getOpenCode());
                holder.mmcCode.setTextColor(parent.getContext().getResources().getColor(R.color.app_main_support));
                holder.mmcCode.setTextSize(14);
                holder.mmcWin.setText(mmcEntity.getPrize() > 0 ? "" + format.format(mmcEntity.getPrize()) : "未中奖");
                holder.mmcWin.setTextColor(mmcEntity.getPrize() > 0 ? Color.parseColor("#EE7E45") : Color.parseColor("#75716F"));
                holder.mmcWin.setTextSize(14);
                return convertView;
            }
        }

        static class ViewHolder {
            @Bind(R.id.mmc_win_row)
            LinearLayout mmcWinRow;
            @Bind(R.id.mmc_order)
            TextView mmcOrder;
            @Bind(R.id.mmc_code)
            TextView mmcCode;
            @Bind(R.id.mmc_win)
            TextView mmcWin;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
                view.setTag(this);
            }
        }

        public interface OnPlayAgainListener {
            void onPlayAgain();
        }

        public interface OnCancelListener {
            void onCancel();
        }

        public interface OnPlayDoubleListner {
            void onPlayDouble();
        }
    }


}
