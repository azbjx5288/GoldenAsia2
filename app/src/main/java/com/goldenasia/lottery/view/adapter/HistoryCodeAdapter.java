package com.goldenasia.lottery.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.IssueEntity;
import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.util.UiUtils;
import com.goldenasia.lottery.util.ResultsUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/3/1.
 */
public class HistoryCodeAdapter extends BaseAdapter {
    private List codeList;
    private int mLotteryId;
    private static int mLotteryType = -1;
    private String mMethodName;
    private Context mContext;

    public HistoryCodeAdapter(List codeList, int lotteryId, int lotteryType, String methodName, Context context) {
        this.codeList = codeList;
        mLotteryId = lotteryId;
        mLotteryType = lotteryType;
        mMethodName = methodName;
        mContext = context;
    }

    @Override
    public int getCount() {
        return codeList != null ? codeList.size() : 0;
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

    public void setData(List codeList) {
        this.codeList = codeList;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mLotteryType == 7) {
            //山东快乐扑克
            ViewHolderShandongKuailePuke holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_result_item_shandongkuailepuke, parent, false);
                holder = new ViewHolderShandongKuailePuke(convertView);
            } else {
                holder = (ViewHolderShandongKuailePuke) convertView.getTag();
            }
            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            holder.issue.setText(historyCode.getIssue());

            //开奖号码
            String codeOpen = historyCode.getCode();
            holder.textSdlklpu01.setText((codeOpen.charAt(0) + "").replace("T", "10"));
            holder.textSdlklpu02.setText((codeOpen.charAt(3) + "").replace("T", "10"));
            holder.textSdlklpu03.setText((codeOpen.charAt(6) + "").replace("T", "10"));

            //图片
            holder.imageSdlklpu01.setImageDrawable(letterToDrawable(codeOpen.charAt(1) + ""));
            holder.imageSdlklpu02.setImageDrawable(letterToDrawable(codeOpen.charAt(4) + ""));
            holder.imageSdlklpu03.setImageDrawable(letterToDrawable(codeOpen.charAt(7) + ""));

            holder.history_xingtai.setText(ResultsUtils.geiKuaiLePukeXT(codeOpen));
            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }
        } else if (mLotteryType == 1//时时彩
                || mLotteryType == 2//11选5
                || mLotteryType == 6//快三
        ) {
            switch (mMethodName) {
                case "WXDW":
                case "REZX":
                case "RSZX":
                case "RSIZX":
                case "REZUX":
                case "RSZS":
                case "RSZL":
                case "RSHHZX":
                case "WXYMBDW":
                case "WXEMBDW":
                case "WXSMBDW":
                case "SDQSDWD":
                case "SDLX4":
                case "SDLX5":
                case "SDRX1":
                case "SDRX2":
                case "SDRX3":
                case "SDRX4":
                case "SDRX5":
                case "SDRX6":
                case "SDRX7":
                case "SDRX8":
                case "SDDTRX1":
                case "SDDTRX2":
                case "SDDTRX3":
                case "SDDTRX4":
                case "SDDTRX5":
                case "SDDTRX6":
                case "SDDTRX7":
                case "SDDTRX8":
                    /*  11选5的*/
                case "SDWXDWD":// SDWXDWD 五星定位胆   11选5的
                    convertView = getSortedView(position, convertView, parent, "dx_ds", mMethodName);
                    break;
                case "EXZX":
                case "EXLX":
                case "EXZUX":
                case "EXZUXBD":
                case "EXBD":
                case "EXHZ":
                    convertView = getSortedView(position, convertView, parent, "h2_hz_dx_ds", mMethodName);
                    break;
                case "QEZX":
                case "QELX":
                case "QEZUX":
                case "QEZUXBD":
                case "QEBD":
                case "QEHZ":
                    convertView = getSortedView(position, convertView, parent, "q2_hz_dx_ds", mMethodName);
                    break;
                case "EXKD":
                    convertView = getSortedView(position, convertView, parent, "h2_hz_kd_dx", mMethodName);
                    break;
                case "QEKD":
                    convertView = getSortedView(position, convertView, parent, "q2_hz_kd_dx", mMethodName);
                    break;
                case "QSKD":
                    convertView = getSortedView(position, convertView, parent, "q3_hz_kd_dx", mMethodName);
                    break;
                case "ZSKD":
                    convertView = getSortedView(position, convertView, parent, "z3_hz_kd_dx", mMethodName);
                    break;
                case "SXKD":
                    convertView = getSortedView(position, convertView, parent, "h3_hz_kd_dx", mMethodName);
                    break;
                case "QSBD":
                    convertView = getSortedView(position, convertView, parent, "q3_bd", mMethodName);
                    break;
                case "ZSBD":
                    convertView = getSortedView(position, convertView, parent, "z3_bd", mMethodName);
                    break;
                case "SXBD":
                    convertView = getSortedView(position, convertView, parent, "h3_bd", mMethodName);
                    break;
                case "QSIZX":
                    convertView = getSortedView(position, convertView, parent, "q4_zx", mMethodName);
                    break;
                case "SIXZX":
                    convertView = getSortedView(position, convertView, parent, "h4_zx", mMethodName);
                    break;
                case "WXHZ":
                    convertView = getSortedView(position, convertView, parent, "wx_hz", mMethodName);
                    break;
                case "QEDXDS":
                case "SDQEZX":
                case "SDQEZUX":
                case "SDDTQEZUX":
                case "SDLX2":
                    convertView = getSortedView(position, convertView, parent, "q2_dx_ds", mMethodName);
                    break;
                case "EXDXDS":
                case "SDEXZX":
                case "SDEXZUX":
                case "SDDTEXZUX":
                    convertView = getSortedView(position, convertView, parent, "h2_dx_ds", mMethodName);
                    break;
                case "QSDXDS":
                case "QSYMBDW":
                case "QSEMBDW":
                case "SDQSZX":
                case "SDQSZUX":
                case "SDDTQSZUX":
                case "SDQSBDW":
                case "SDLX3":
                    convertView = getSortedView(position, convertView, parent, "q3_dx_ds", mMethodName);
                    break;
                case "ZSDXDS":
                case "ZSYMBDW":
                case "ZSEMBDW":
                    convertView = getSortedView(position, convertView, parent, "z3_dx_ds", mMethodName);
                    break;
                case "SXDXDS":
                case "SDSXZX":
                case "SDSXZUX":
                case "SDDTSXZUX":
                    convertView = getSortedView(position, convertView, parent, "h3_dx_ds", mMethodName);
                    break;
                case "YMBDW":
                case "EMBDW":
                    if (mLotteryType == 1) {
                        convertView = getSortedView(position, convertView, parent, "h3_dx_ds", mMethodName);
                        break;
                    } else {
                        convertView = getSortedView(position, convertView, parent, "dx_ds", mMethodName);
                        break;
                    }
                case "SXYMBDW":
                case "SXEMBDW":
                    convertView = getSortedView(position, convertView, parent, "h4_dx_ds", mMethodName);
                    break;
                case "WXHZDXDS":
                    convertView = getSortedView(position, convertView, parent, "5x_hz_dx_ds", mMethodName);
                    break;
                case "JSEBT":
                case "JSSBT":
                case "JSHZ":
                case "CYBUC":
                case "CEBUC":
                case "CSBUC":
                case "CYBIC":
                case "CEBIC":
                case "CSBIC":
                case "JSDX":
                case "JSDS":
                    convertView = getSortedView(position, convertView, parent, "3x_hz_dx_ds", mMethodName);
                    break;
                case "JSETDX":
                case "JSSTDX":
                case "JSETFX":
                case "JSSTTX":
                case "JSSLTX":
                    convertView = getSortedView(position, convertView, parent, "hz_dx_xt", mMethodName);
                    break;
                case "RELHH":
                    convertView = getSortedView(position, convertView, parent, "lhh", mMethodName);
                    break;
                case "NIUNIU":
                    convertView = getSortedView(position, convertView, parent, "nn", mMethodName);
                    break;
                case "QSZX":
                case "QSLX":
                case "QSZS":
                case "QSZL":
                case "QSHHZX":
                case "QSZUXBD":
                case "QSHZ":
                case "QSZXHZ"://组选和值   P3组选
                    convertView = getSortedView(position, convertView, parent, "q3_hz_zx_dx", mMethodName);
                    break;
                case "ZSZX":
                case "ZSLX":
                case "ZSZS":
                case "ZSZL":
                case "ZSHHZX":
                case "ZSZUXBD":
                case "ZSHZ":
                    convertView = getSortedView(position, convertView, parent, "z3_hz_zx_dx", mMethodName);
                    break;
                case "SXLX":
                case "SXZS":
                case "SXZL":
                case "SXHHZX":
                case "SXZUXBD":
                case "SXHZ":
                case "HSZUXBD"://后三包胆
                    convertView = getSortedView(position, convertView, parent, "h3_hz_zx_dx", mMethodName);
                    break;
                case "SXZX":
                    if (mLotteryType == 1) {
                        convertView = getSortedView(position, convertView, parent, "h3_hz_zx_dx", mMethodName);
                        break;
                    } else {
                        convertView = getSortedView(position, convertView, parent, "hz_zx", mMethodName);
                        break;
                    }
                case "QSIZUX4":
                case "QSIZUX6":
                case "QSIZUX12":
                case "QSIZUX24":
                    convertView = getSortedView(position, convertView, parent, "q4_hz_zx_kd", mMethodName);
                    break;
                case "ZUX4":
                case "ZUX6":
                case "ZUX12":
                case "ZUX24":
                    convertView = getSortedView(position, convertView, parent, "h4_hz_zx_kd", mMethodName);
                    break;
                case "WXZX":
                case "WXLX":
                case "ZUX5":
                case "ZUX10":
                case "ZUX20":
                case "ZUX30":
                case "ZUX60":
                case "ZUX120":
                    convertView = getSortedView(position, convertView, parent, "5x_hz_kd_zx", mMethodName);
                    break;
                case "YFFS":
                case "HSCS":
                case "SXBX":
                case "SJFC":
                    convertView = getSortedView(position, convertView, parent, "qwwf", mMethodName);
                    break;
                case "SDDDS":
                    convertView = getSortedView(position, convertView, parent, "dds", mMethodName);
                    break;
                case "SDCZW":
                    convertView = getSortedView(position, convertView, parent, "czw", mMethodName);
                    break;
                case "JSYS":
                    convertView = getSortedView(position, convertView, parent, "hz_dx_ys", mMethodName);
                    break;
                default:
                    convertView = getSortedView(position, convertView, parent, ""/*, R.layout
                    .fragment_history_result_item*/, mMethodName);
            }
        } else if (mLotteryType == 3) { //六合彩{
            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            String code = historyCode.getCode();
            ViewHolder holder;
            if (convertView == null) {
                switch (mMethodName) {
                    case "TMZX":
                    case "TMSX"://特码生肖
                    case "TMSB"://特码色波
                    case "TMWS"://特码尾数
                    case "TMDXDS"://特码大小单双
                    case "ZTHZDXDS"://正特和值大小单双
                    case "ZONGX"://总肖
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result3/*fragment_history_result_item*/, parent, false);
                        break;
                    default:
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result4/*fragment_history_result_item*/, parent, false);
                }
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
            holder.issue.setText(historyCode.getIssue());

            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }

            switch (mMethodName) {
                case "TMZX":
                case "TMSX"://特码生肖
                case "TMSB"://特码色波
                    String[] codeArr = ResultsUtils.getStringArrFromString(code, " ");
                   /* StringBuffer normalSb=new StringBuffer();
                    StringBuffer diffSb=new StringBuffer();
                    for(int i=0;i<codeArr.length;i++){
                        if(i==6){
                            diffSb.append(codeArr[i]);
                        }else{
                            normalSb.append(codeArr[i]).append(" ");
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorAfter(normalSb.toString(),diffSb.toString()));*/
                    holder.code.setVisibility(View.GONE);
                    holder.tv3.setText(codeArr[0]);
                    holder.tv4.setText(codeArr[1]);
                    holder.tv5.setText(codeArr[2]);
                    holder.tv6.setText(codeArr[3]);
                    holder.tv7.setText(codeArr[4]);
                    holder.tv8.setText(codeArr[5]);
                    holder.tv9.setText(codeArr[6]);
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.tv7.setVisibility(View.VISIBLE);
                    holder.tv8.setVisibility(View.VISIBLE);
                    holder.tv9.setVisibility(View.VISIBLE);

                    int lastCode = ResultsUtils.getLastIntFromString(code, " ");
                    ResultsUtils utils=ResultsUtils.from();
                    holder.tv1.setText(utils.getShengXiaoFromCode(lastCode));
                    holder.tv2.setText(ResultsUtils.getSeBoFromCode(lastCode));
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "TMWS"://特码尾数
                    codeArr = ResultsUtils.getStringArrFromString(code, " ");
                    /*normalSb=new StringBuffer();
                    diffSb=new StringBuffer();
                    for(int i=0;i<codeArr.length;i++){
                        if(i==6){
                            diffSb.append(codeArr[i]);
                        }else{
                            normalSb.append(codeArr[i]).append(" ");
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorAfter(normalSb.toString(),diffSb.toString()));*/
                    holder.code.setVisibility(View.GONE);
                    holder.tv3.setText(codeArr[0]);
                    holder.tv4.setText(codeArr[1]);
                    holder.tv5.setText(codeArr[2]);
                    holder.tv6.setText(codeArr[3]);
                    holder.tv7.setText(codeArr[4]);
                    holder.tv8.setText(codeArr[5]);
                    holder.tv9.setText(codeArr[6]);
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.tv7.setVisibility(View.VISIBLE);
                    holder.tv8.setVisibility(View.VISIBLE);
                    holder.tv9.setVisibility(View.VISIBLE);


                    int lastCode2 = ResultsUtils.getLastIntFromString(code, " ");
                    holder.tv1.setText(ResultsUtils.getShengXiaoFromCode(lastCode2));
                    holder.tv2.setText(ResultsUtils.getWeiShuFromCode(String.format("%02d", lastCode2)));
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "TMDXDS"://特码大小单双
                    codeArr = ResultsUtils.getStringArrFromString(code, " ");
                   /*  normalSb=new StringBuffer();
                    diffSb=new StringBuffer();
                    for(int i=0;i<codeArr.length;i++){
                        if(i==6){
                            diffSb.append(codeArr[i]);
                        }else{
                            normalSb.append(codeArr[i]).append(" ");
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorAfter(normalSb.toString(),diffSb.toString()));*/
                    holder.code.setVisibility(View.GONE);
                    holder.tv3.setText(codeArr[0]);
                    holder.tv4.setText(codeArr[1]);
                    holder.tv5.setText(codeArr[2]);
                    holder.tv6.setText(codeArr[3]);
                    holder.tv7.setText(codeArr[4]);
                    holder.tv8.setText(codeArr[5]);
                    holder.tv9.setText(codeArr[6]);
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.tv7.setVisibility(View.VISIBLE);
                    holder.tv8.setVisibility(View.VISIBLE);
                    holder.tv9.setVisibility(View.VISIBLE);

                    int lastCode3 = ResultsUtils.getLastIntFromString(code, " ");

                    holder.tv1.setText(lastCode3 < 25 ? "小" : "大");
                    holder.tv2.setText(lastCode3 % 2 == 0 ? "双" : "单");
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "ZMZX1"://ZMZX2
                case "ZMZX2"://ZMZX2
                case "ZMZX3"://ZMZX2
                case "ZMZX4"://ZMZX2
                case "ZMZX5"://ZMZX2
                case "ZMZX6"://ZMZX2
                case "ZMRX"://ZMZX2
                case "ZTYX"://ZMZX2
                case "ZTWS"://ZTWS
                case "ERLX"://ZMZX2
                case "SNLX"://ZMZX2
                case "SILX"://ZMZX2
                case "SIELM"://ZMZX2
                case "SISLM"://ZMZX2
                case "SISILM"://ZMZX2
                case "SSLM"://ZMZX2
                case "EELM"://ZMZX2
                case "SELM"://ZMZX2
                case "ZTYM"://ZMZX2

                case "ZTBZ5"://ZMZX2
                case "ZTBZ6"://ZMZX2
                case "ZTBZ7"://ZMZX2
                case "ZTBZ8"://ZMZX2
                case "ZTBZ9"://ZMZX2
                case "ZTBZ10"://ZMZX2

                    holder.code.setVisibility(View.GONE);
                    String[] StringArr = ResultsUtils.getStringArrFromString(code, " ");

                    holder.tv1.setText(StringArr[0]);
                    holder.tv2.setText(StringArr[1]);
                    holder.tv3.setText(StringArr[2]);
                    holder.tv4.setText(StringArr[3]);
                    holder.tv5.setText(StringArr[4]);
                    holder.tv6.setText(StringArr[5]);
                    holder.tv7.setText(StringArr[6]);
//                    holder.tv7.setTextColor(UiUtils.getColor(mContext,R.color.kaijiang_diff_textcolor));
                    holder.tv7.setTextColor(UiUtils.getColor(mContext, R.color.white));
                    holder.tv7.setBackgroundResource(R.drawable.bg_special_complete_circle_choose_ball2);

                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.tv7.setVisibility(View.VISIBLE);
                    return convertView;
                case "ZTHZDXDS"://正特和值大小单双
                    codeArr = ResultsUtils.getStringArrFromString(code, " ");
                   /*  normalSb=new StringBuffer();
                    diffSb=new StringBuffer();
                    for(int i=0;i<codeArr.length;i++){
                        if(i==6){
                            diffSb.append(codeArr[i]);
                        }else{
                            normalSb.append(codeArr[i]).append(" ");
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorAfter(normalSb.toString(),diffSb.toString()));*/
                    holder.code.setVisibility(View.GONE);
                    holder.tv3.setText(codeArr[0]);
                    holder.tv4.setText(codeArr[1]);
                    holder.tv5.setText(codeArr[2]);
                    holder.tv6.setText(codeArr[3]);
                    holder.tv7.setText(codeArr[4]);
                    holder.tv8.setText(codeArr[5]);
                    holder.tv9.setText(codeArr[6]);
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.tv7.setVisibility(View.VISIBLE);
                    holder.tv8.setVisibility(View.VISIBLE);
                    holder.tv9.setVisibility(View.VISIBLE);

                    int sum = ResultsUtils.getSum(code, " ");
                    holder.tv1.setText(String.valueOf(sum));

                    StringBuffer stringBufferXingTai = new StringBuffer();
                    if (sum < 175) {
                        stringBufferXingTai.append("小");
                    } else {
                        stringBufferXingTai.append("大");
                    }
                    if (sum % 2 == 0) {
                        stringBufferXingTai.append("双");
                    } else {
                        stringBufferXingTai.append("单");
                    }

                    holder.tv2.setText(stringBufferXingTai.toString());

                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "ZONGX"://总肖
                    codeArr = ResultsUtils.getStringArrFromString(code, " ");
                    /*normalSb=new StringBuffer();
                    diffSb=new StringBuffer();
                    for(int i=0;i<codeArr.length;i++){
                        if(i==6){
                            diffSb.append(codeArr[i]);
                        }else{
                            normalSb.append(codeArr[i]).append(" ");
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorAfter(normalSb.toString(),diffSb.toString()));*/

                    int sumZongXiao = ResultsUtils.getZongXiao(code, " ");
                    holder.code.setVisibility(View.GONE);
                    holder.tv3.setText(codeArr[0]);
                    holder.tv4.setText(codeArr[1]);
                    holder.tv5.setText(codeArr[2]);
                    holder.tv6.setText(codeArr[3]);
                    holder.tv7.setText(codeArr[4]);
                    holder.tv8.setText(codeArr[5]);
                    holder.tv9.setText(codeArr[6]);
                    holder.tv3.setVisibility(View.VISIBLE);
                    holder.tv4.setVisibility(View.VISIBLE);
                    holder.tv5.setVisibility(View.VISIBLE);
                    holder.tv6.setVisibility(View.VISIBLE);
                    holder.tv7.setVisibility(View.VISIBLE);
                    holder.tv8.setVisibility(View.VISIBLE);
                    holder.tv9.setVisibility(View.VISIBLE);

                    holder.tv1.setText(String.valueOf(sumZongXiao) + "肖");
                    holder.tv2.setText(sumZongXiao % 2 == 0 ? "双" : "单");
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                default:
            }
        } else if (mLotteryType == 4) { //福彩3D
            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            String code = historyCode.getCode();
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .item_history_result5/*fragment_history_result_item*/, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.issue.setText(historyCode.getIssue());
//            holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }

            switch (mMethodName) {
                case "SXDW":
                case "YMBDW"://一码不定位
                case "EMBDW"://二码不定位

                    holder.code.setText(code);

                    int[] codeArr = ResultsUtils.getIntArrFromString(code, "", 3);

                    StringBuffer stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < codeArr.length; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    StringBuffer stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < codeArr.length; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());


                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "EXZX"://后二直选
                case "EXZUX"://后二组选
                    codeArr = ResultsUtils.getIntArrFromString(code, "", 3);
                    holder.code.setText(ResultsUtils.showDiffColorAfter(String.valueOf(codeArr[0]), String.valueOf(codeArr[1]) + codeArr[2]));

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 1; i < codeArr.length; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 1; i < codeArr.length; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());


                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "QEZX"://前二直选
                case "QEZUX"://前二组选
//                    holder.code.setText(code);
                    codeArr = ResultsUtils.getIntArrFromString(code, "", 3);

                    holder.code.setText(ResultsUtils.showDiffColorBefore(String.valueOf(codeArr[0]) + codeArr[1], String.valueOf(codeArr[2])));

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());


                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "SXZX"://直选
                    holder.code.setText(code);

                    codeArr = ResultsUtils.getIntArrFromString(code, "", 3);

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 3; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 3; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());

                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "SXHZ"://直选和值
                    holder.code.setText(code);

                    ArrayList<Integer> codeArr2 = ResultsUtils.getIntegerArrFromString(code, "");

                    int sum = ResultsUtils.getSum(code, "");
                    holder.tv1.setText(String.valueOf(sum));


                    holder.tv2.setText(ResultsUtils.getZuXuanResult(1, codeArr2));

                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "SXZS"://
                case "SXZL"://组六
                case "SXHHZX"://
                case "SXZXHZ"://组选和值
                    holder.code.setText(code);

                    codeArr2 = ResultsUtils.getIntegerArrFromString(code, "");

                    sum = ResultsUtils.getSum(code, "");
                    holder.tv1.setText(String.valueOf(sum));


                    holder.tv2.setText(ResultsUtils.getZuXuanResult(1, codeArr2));

                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "EXDXDS"://后二大小单双
//                    holder.code.setText(code);

                    codeArr = ResultsUtils.getIntArrFromString(code, "", 3);

                    holder.code.setText(ResultsUtils.showDiffColorAfter(String.valueOf(codeArr[0]), String.valueOf(codeArr[1]) + codeArr[2]));

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 1; i < 3; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 1; i < 3; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                case "QEDXDS"://前二大小单双
//                    holder.code.setText(code);
                    codeArr = ResultsUtils.getIntArrFromString(code, "", 3);

                    holder.code.setText(ResultsUtils.showDiffColorBefore(String.valueOf(codeArr[0]) + codeArr[1], String.valueOf(codeArr[2])));

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    return convertView;
                default:
                    if (convertView == null) {
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result/*fragment_history_result_item*/, parent, false);
                        holder = new ViewHolder(convertView);
                    } else {
                        holder = (ViewHolder) convertView.getTag();
                    }
            }
        } else if (mLotteryType == 8) { //lottery.getLotteryType()==8){ //PK10（PK10分分彩、PK10二分彩、北京PK10）
            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            String code = historyCode.getCode();
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result6/*fragment_history_result_item*/, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
//            holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }

            holder.issue.setText(historyCode.getIssue());
            holder.code.setVisibility(View.GONE);

            int[] codeArr = ResultsUtils.getIntArrFromString(code, " ");
            holder.tv1.setText(String.valueOf(codeArr[0]));
            holder.tv2.setText(String.valueOf(codeArr[1]));
            holder.tv3.setText(String.valueOf(codeArr[2]));
            holder.tv4.setText(String.valueOf(codeArr[3]));
            holder.tv5.setText(String.valueOf(codeArr[4]));
            holder.tv6.setText(String.valueOf(codeArr[5]));
            holder.tv7.setText(String.valueOf(codeArr[6]));
            holder.tv8.setText(String.valueOf(codeArr[7]));
            holder.tv9.setText(String.valueOf(codeArr[8]));
            holder.tv10.setText(String.valueOf(codeArr[9]));

            holder.tv1.setVisibility(View.VISIBLE);
            holder.tv2.setVisibility(View.VISIBLE);
            holder.tv3.setVisibility(View.VISIBLE);
            holder.tv4.setVisibility(View.VISIBLE);
            holder.tv5.setVisibility(View.VISIBLE);
            holder.tv6.setVisibility(View.VISIBLE);
            holder.tv7.setVisibility(View.VISIBLE);
            holder.tv8.setVisibility(View.VISIBLE);
            holder.tv9.setVisibility(View.VISIBLE);
            holder.tv10.setVisibility(View.VISIBLE);
            switch (mMethodName) {
                case "GYHZ"://冠亚和  猜和值
                    int getSum = codeArr[0] + codeArr[1];
                    holder.tv11.setText(String.valueOf(getSum));
                    holder.tv11.setVisibility(View.VISIBLE);
                    return convertView;
                case "HWMC"://猜后五
                    holder.tv6.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv7.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv8.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv9.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv10.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "QWMC"://猜前五
                    holder.tv1.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv2.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv3.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv4.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv5.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "HEMZX"://后二名直选
                case "HEMZUX"://
                    holder.tv9.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv10.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "QEMZX"://
                case "QEMZUX"://
                    holder.tv1.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv2.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "HSMZX"://后三名直选
                case "HSMZL"://
                case "HSMBDW"://后三名不定位
                    holder.tv8.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv9.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv10.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "QSMZX"://
                case "QSMZL"://前三名组六
                case "QSMBDW"://前三名不定位
                    holder.tv1.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv2.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv3.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                /*四星*/
                case "HSIMZX"://
                case "HSIMZUX"://
                    holder.tv7.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv8.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv9.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv10.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "QSIMZX"://
                case "QSIMZUX"://前三名组六
                    holder.tv1.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv2.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv3.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv4.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                /*五星*/
                case "HWMZX"://
                case "HWMZUX"://
                    holder.tv6.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv7.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv8.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv9.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv10.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                case "QWMZX"://
                case "QWMZUX"://前三名组六
                    holder.tv1.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv2.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv3.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv4.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    holder.tv5.setTextColor(UiUtils.getColor(mContext, R.color.kaijiang_diff_textcolor));
                    return convertView;
                default:
                    return convertView;
            }
        } else if (mLotteryType == 5) {//(mLotteryId == 48){ //48://北京快乐8:
            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            String code = historyCode.getCode();
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result1/*fragment_history_result_item*/, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }

            holder.issue.setText(historyCode.getIssue());
//                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(code.substring(0, 29));
            stringBuffer.append("\n");
            stringBuffer.append(code.substring(30, code.length()));
            holder.code.setText(stringBuffer.toString());
            switch (mMethodName) {
                case "HZDS"://和值单双
//                    holder.issue.setText(historyCode.getIssue());
////                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
//                    holder.code.setText(code);

                    int sum = ResultsUtils.getSum(code, " ");
                    holder.tv1.setText(sum % 2 == 0 ? "双" : "单");
                    holder.tv1.setVisibility(View.VISIBLE);
                    break;
                case "HZ810"://和值810
//                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
//                    holder.code.setText(code);

                    sum = ResultsUtils.getSum(code, " ");
                    if (sum > 810) {
                        holder.tv1.setText("大");
                    } else if (sum == 810) {
                        holder.tv1.setText("810");
                    } else {
                        holder.tv1.setText("小");
                    }
                    holder.tv1.setVisibility(View.VISIBLE);
                    break;
                case "HZWX"://和值五行
//                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
//                    holder.code.setText(code);

                    sum = ResultsUtils.getSum(code, " ");
                    if (sum >= 210 && sum <= 695) {
                        holder.tv1.setText("金");
                    } else if (sum >= 696 && sum <= 763) {
                        holder.tv1.setText("木");
                    } else if (sum >= 764 && sum <= 855) {
                        holder.tv1.setText("水");
                    } else if (sum >= 856 && sum <= 923) {
                        holder.tv1.setText("火");
                    } else if (sum >= 924 && sum <= 1410) {
                        holder.tv1.setText("土");
                    } else {
                        holder.tv1.setText("");
                    }
                    holder.tv1.setVisibility(View.VISIBLE);
                    break;
                case "KNJOH"://奇偶和
//                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
//                    holder.code.setText(code);

                    int[] codeArr = ResultsUtils.getIntArrFromString(code, " ");
                    int jcount = 0;//奇数个数
                    int ocount = 0;//偶数个数

                    for (int i = 0; i < codeArr.length; i++) {
                        if (codeArr[i] % 2 == 0) {
                            ocount++;
                        } else {
                            jcount++;
                        }
                    }

                    if (jcount > ocount) {
                        holder.tv1.setText("奇");
                    } else if (jcount < ocount) {
                        holder.tv1.setText("偶");
                    } else {
                        holder.tv1.setText("和");
                    }
                    holder.tv1.setVisibility(View.VISIBLE);
                    break;
                case "KNSZX"://上中下
//                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
//                    holder.code.setText(code);

                    int upcount = 0;//奇数个数
                    int downcount = 0;//偶数个数

                    codeArr = ResultsUtils.getIntArrFromString(code, " ");

                    for (int i = 0; i < codeArr.length; i++) {
                        if (codeArr[i] < 41) {
                            upcount++;
                        } else {
                            downcount++;
                        }
                    }

                    if (upcount > downcount) {
                        holder.tv1.setText("上");
                    } else if (upcount < downcount) {
                        holder.tv1.setText("下");
                    } else {
                        holder.tv1.setText("中");
                    }
                    holder.tv1.setVisibility(View.VISIBLE);
                    break;
                default:
//                    holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
//                    holder.code.setText(code);
                    return convertView;
            }
        } else if (mLotteryType == 9) { //快乐12 By Ace
            ViewHolderQ2DXDS holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                holder = new ViewHolderQ2DXDS(convertView);
            } else {
                holder = (ViewHolderQ2DXDS) convertView.getTag();
            }

            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }

            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            String code = historyCode.getCode();
            holder.issue.setText(historyCode.getIssue());

            switch (mMethodName) {
                case "SCQEZX":
                case "SCQEZUX":
                case "SCDTQEZUX":
                    int[] codeArr = ResultsUtils.getIntArrFromString(code, " ", 5);

                    StringBuffer codeStringBuffer = new StringBuffer();
                    for (int i = 0, size = codeArr.length; i < size; i++) {
                        if (i >= 2) {
                            codeStringBuffer.append(String.format("%02d", codeArr[i]));
                            if (i != size - 1) {
                                codeStringBuffer.append(" ");
                            }
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorBefore(String.format("%02d", codeArr[0]) + " " + String.format("%02d", codeArr[1]) + " ", codeStringBuffer.toString()));

                    StringBuffer stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    StringBuffer stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    break;
                case "SCDTQSZUX":
                case "SCQSZX":
                case "SCQSZUX":
                    codeArr = ResultsUtils.getIntArrFromString(code, " ", 5);

                    codeStringBuffer = new StringBuffer();
                    for (int i = 0, size = codeArr.length; i < size; i++) {
                        if (i >= 3) {
                            codeStringBuffer.append(String.format("%02d", codeArr[i]));
                            if (i != size - 1) {
                                codeStringBuffer.append(" ");
                            }
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorBefore(String.format("%02d", codeArr[0]) + " " + String.format("%02d", codeArr[1]) + " " + String.format("%02d", codeArr[2]) + " ", codeStringBuffer.toString()));

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 3; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 3; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    break;
                default:
                    codeArr = ResultsUtils.getIntArrFromString(code, " ", 5);

                    holder.code.setText(code);
                    stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 5; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 5; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
            }

            return convertView;
        } else if (mLotteryType == 10) {    //快乐10分彩
            ViewHolderQ2DXDS holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                holder = new ViewHolderQ2DXDS(convertView);
            } else {
                holder = (ViewHolderQ2DXDS) convertView.getTag();
            }

            if (position % 2 != 0) {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.kaijiang_diff_gb));
            } else {
                holder.ll_main.setBackgroundColor(UiUtils.getColor(parent.getContext(), R.color.white));
            }

            IssueEntity historyCode = (IssueEntity) codeList.get(position);
            String code = historyCode.getCode();
            holder.issue.setText(historyCode.getIssue());

            switch (mMethodName) {
                case "GDDTQEZUX":
                case "GDQEZUX":
                case "GDQEZX":
                    int[] codeArr = ResultsUtils.getIntArrFromString(code, " ", 8);

                    StringBuffer codeStringBuffer = new StringBuffer();
                    for (int i = 0, size = codeArr.length; i < size; i++) {
                        if (i >= 2) {
                            codeStringBuffer.append(String.format("%02d", codeArr[i]));
                            if (i != size - 1) {
                                codeStringBuffer.append(" ");
                            }
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorBefore(String.format("%02d", codeArr[0]) + " " + String.format("%02d", codeArr[1]) + " ", codeStringBuffer.toString()));

                    StringBuffer stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    StringBuffer stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 2; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    break;
                case "GDDTQSZUX":
                case "GDQSZUX":
                case "GDQSZX":
                    codeArr = ResultsUtils.getIntArrFromString(code, " ", 8);

                    codeStringBuffer = new StringBuffer();
                    for (int i = 0, size = codeArr.length; i < size; i++) {
                        if (i >= 3) {
                            codeStringBuffer.append(String.format("%02d", codeArr[i]));
                            if (i != size - 1) {
                                codeStringBuffer.append(" ");
                            }
                        }
                    }
                    holder.code.setText(ResultsUtils.showDiffColorBefore(String.format("%02d", codeArr[0]) + " " + String.format("%02d", codeArr[1]) + " " + String.format("%02d", codeArr[2]) + " ", codeStringBuffer.toString()));

                    stringBufferTv1 = new StringBuffer();
                    for (int i = 0; i < 3; i++) {
                        if (codeArr[i] < 5) {
                            stringBufferTv1.append("小");
                        } else {
                            stringBufferTv1.append("大");
                        }
                    }
                    holder.tv1.setText(stringBufferTv1.toString());

                    stringBufferTv2 = new StringBuffer();
                    for (int i = 0; i < 3; i++) {
                        if (codeArr[i] % 2 == 0) {
                            stringBufferTv2.append("双");
                        } else {
                            stringBufferTv2.append("单");
                        }
                    }
                    holder.tv2.setText(stringBufferTv2.toString());
                    holder.tv1.setVisibility(View.VISIBLE);
                    holder.tv2.setVisibility(View.VISIBLE);
                    break;
                default:
                    holder.code.setText(code);
                    holder.tv1.setVisibility(View.GONE);
                    holder.tv2.setVisibility(View.GONE);
                    break;
            }
            return convertView;
        } else {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result/*fragment_history_result_item*/, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
        }

        return convertView;
    }

    private View getSortedView(int position, View convertView, ViewGroup parent, String holderName, String mMethodName) {
        IssueEntity historyCode = (IssueEntity) codeList.get(position);
        String code = historyCode.getCode();
        ViewHolder holder;
        switch (holderName) {
            case "dx_ds":
                if (convertView == null) {
                    if (mMethodName.equals("WXDW")) {//定位胆 //时时彩)
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result2, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDWXDWD")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                        /* *//*11选5 任选*//*
                        case "SDRX1"://任选一中一
                        case "SDRX2"://
                        case "SDRX3"://
                        case "SDRX4"://
                        case "SDRX5"://
                        case "SDRX6"://任选六中五
                        case "SDRX7"://任选七中五
                        case "SDRX8"://任选八中五*/
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX1")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX2")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX3")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX4")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX5")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX6")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX7")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDRX8")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                        /*  *//*11选5 任选胆拖*//*
                        case "SDDTRX1":
                        case "SDDTRX2"://任选二中二胆拖
                        case "SDDTRX3"://任选三中三胆拖
                        case "SDDTRX4"://
                        case "SDDTRX5"://
                        case "SDDTRX6"://
                        case "SDDTRX7"://
                        case "SDDTRX8"://任选八中五胆拖*/
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX1")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX2")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX3")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX4")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX5")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX6")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX7")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else if (mLotteryType == 2 && mMethodName.equals("SDDTRX8")) {//11选5 SDWXDWD 五星定位胆   11选5的
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result7, parent, false);
                    } else {
                        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    }
                    holder = new ViewHolder5XDXDS(convertView);
                } else {
                    holder = (ViewHolder5XDXDS) convertView.getTag();
                }
                break;
            case "h2_hz_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH2HZDXDS(convertView);
                } else {
                    holder = (ViewHolderH2HZDXDS) convertView.getTag();
                }
                break;
            case "q2_hz_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ2HZDXDS(convertView);
                } else {
                    holder = (ViewHolderQ2HZDXDS) convertView.getTag();
                }
                break;
            case "h2_hz_kd_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH2HZKDDX(convertView);
                } else {
                    holder = (ViewHolderH2HZKDDX) convertView.getTag();
                }
                break;
            case "q2_hz_kd_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ2HZKDDX(convertView);
                } else {
                    holder = (ViewHolderQ2HZKDDX) convertView.getTag();
                }
                break;
            case "q3_hz_zx_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ3HZZXDX(convertView);
                } else {
                    holder = (ViewHolderQ3HZZXDX) convertView.getTag();
                }
                break;
            case "z3_hz_zx_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderZ3HZZXDX(convertView);
                } else {
                    holder = (ViewHolderZ3HZZXDX) convertView.getTag();
                }
                break;
            case "h3_hz_zx_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH3HZZXDX(convertView);
                } else {
                    holder = (ViewHolderH3HZZXDX) convertView.getTag();
                }
                break;
            case "q3_hz_kd_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ3HZKDDX(convertView);
                } else {
                    holder = (ViewHolderQ3HZKDDX) convertView.getTag();
                }
                break;
            case "z3_hz_kd_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderZ3HZKDDX(convertView);
                } else {
                    holder = (ViewHolderZ3HZKDDX) convertView.getTag();
                }
                break;
            case "h3_hz_kd_dx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH3HZKDDX(convertView);
                } else {
                    holder = (ViewHolderH3HZKDDX) convertView.getTag();
                }
                break;
            case "q3_bd":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ3BD(convertView);
                } else {
                    holder = (ViewHolderQ3BD) convertView.getTag();
                }
                break;
            case "z3_bd":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderZ3BD(convertView);
                } else {
                    holder = (ViewHolderZ3BD) convertView.getTag();
                }
                break;
            case "h3_bd":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH3BD(convertView);
                } else {
                    holder = (ViewHolderH3BD) convertView.getTag();
                }
                break;
            case "h4_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH4DXDS(convertView);
                } else {
                    holder = (ViewHolderH4DXDS) convertView.getTag();
                }
                break;
            case "q4_zx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ4ZX(convertView);
                } else {
                    holder = (ViewHolderQ4ZX) convertView.getTag();
                }
                break;
            case "h4_zx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH4ZX(convertView);
                } else {
                    holder = (ViewHolderH4ZX) convertView.getTag();
                }
                break;
            case "q4_hz_zx_kd":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ4HZZXKD(convertView);
                } else {
                    holder = (ViewHolderQ4HZZXKD) convertView.getTag();
                }
                break;
            case "h4_hz_zx_kd":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH4HZZXKD(convertView);
                } else {
                    holder = (ViewHolderH4HZZXKD) convertView.getTag();
                }
                break;
            case "wx_hz":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolder5XHZHDXHW(convertView);
                } else {
                    holder = (ViewHolder5XHZHDXHW) convertView.getTag();
                }
                break;
            case "q2_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ2DXDS(convertView);
                } else {
                    holder = (ViewHolderQ2DXDS) convertView.getTag();
                }
                break;
            case "h2_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH2DXDS(convertView);
                } else {
                    holder = (ViewHolderH2DXDS) convertView.getTag();
                }
                break;
            case "q3_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQ3DXDS(convertView);
                } else {
                    holder = (ViewHolderQ3DXDS) convertView.getTag();
                }
                break;
            case "z3_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderZ3DXDS(convertView);
                } else {
                    holder = (ViewHolderZ3DXDS) convertView.getTag();
                }
                break;
            case "h3_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderH3DXDS(convertView);
                } else {
                    holder = (ViewHolderH3DXDS) convertView.getTag();
                }
                break;
            case "5x_hz_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolder5XHZDXDS(convertView);
                } else {
                    holder = (ViewHolder5XHZDXDS) convertView.getTag();
                }
                break;
            case "3x_hz_dx_ds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolder3XHZDXDS(convertView);
                } else {
                    holder = (ViewHolder3XHZDXDS) convertView.getTag();
                }
                break;
            case "5x_hz_kd_zx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolder5XHZKDZX(convertView);
                } else {
                    holder = (ViewHolder5XHZKDZX) convertView.getTag();
                }
                break;
            case "qwwf":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderQWWF(convertView);
                } else {
                    holder = (ViewHolderQWWF) convertView.getTag();
                }
                break;
            case "lhh":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderLHH(convertView);
                } else {
                    holder = (ViewHolderLHH) convertView.getTag();
                }
                break;
            case "nn":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderNN(convertView);
                } else {
                    holder = (ViewHolderNN) convertView.getTag();
                }
                break;
            case "dds":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderDDS(convertView);
                } else {
                    holder = (ViewHolderDDS) convertView.getTag();
                }
                break;
            case "czw":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderCZW(convertView);
                } else {
                    holder = (ViewHolderCZW) convertView.getTag();
                }
                break;
            case "hz_dx_xt":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderHZDXXT(convertView);
                } else {
                    holder = (ViewHolderHZDXXT) convertView.getTag();
                }
                break;
            case "hz_dx_ys":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderHZDXYS(convertView);
                } else {
                    holder = (ViewHolderHZDXYS) convertView.getTag();
                }
                break;
            case "hz_zx":
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result, parent, false);
                    holder = new ViewHolderHZZX(convertView);
                } else {
                    holder = (ViewHolderHZDXYS) convertView.getTag();
                }
                break;
            default:
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result/*fragment_history_result_item*/, parent, false);
                    holder = new ViewHolder(convertView);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
        }

        holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));

        showDiffCode(mMethodName, code, holder);

        //if (!TextUtils.isEmpty(mMethodName) && mLotteryType != -1)
        String[] letters;
        if (code.contains(" "))
            letters = code.split(" ");
        else
            letters = code.split("");

        ArrayList<Integer> integers = new ArrayList<>();
        for (String s : letters) {
            if (TextUtils.isEmpty(s))
                continue;
            integers.add(Integer.parseInt(s));
        }
        holder.sort(integers);
        holder.show(position);

        return convertView;
    }

    private void showDiffCode(String mMethodName, String code, ViewHolder holder) {
        switch (mMethodName) {
            /*      case "WXDW"://定位胆
             *//*  11选5的*//*
            case "SDWXDWD":// SDWXDWD 五星定位胆   11选5的
                holder.code.setText(code);
                holder.code.setTextColor(UiUtils.getColor(mContext,R.color.kaijiang_diff_textcolor));
                break;*/
            case "QEDXDS"://前二大小单双
            case "SDQEZX"://前二直选
            case "SDQEZUX"://前二组选
            case "SDDTQEZUX"://前二组选胆拖
                /* 时时彩*/
            case "QEZX"://前二直选
            case "QELX"://前二连选
            case "QEZUX"://前二组选
            case "QEZUXBD"://前二包胆
            case "QEBD"://前二包点
            case "QEHZ"://前二和值
                /*  时时彩*/
            case "QEKD"://前二跨度
                String[] codeArr = getSplitContent(code);
                StringBuffer code1Sb1 = new StringBuffer();
                StringBuffer code1Sb2 = new StringBuffer();
                code1Sb1.append(codeArr[0]).append(codeArr[1]);
                code1Sb2.append(codeArr[2]).append(codeArr[3]).append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorBefore(code1Sb1.toString(), code1Sb2.toString()));
                break;
            case "EXDXDS"://后二大小单双
                /*11选5 后二*/
            case "SDEXZX"://后二直选
            case "SDEXZUX"://SDEXZUX
            case "SDDTEXZUX"://后二组选胆拖
                /* 时时彩*/
            case "EXZX"://后二直选
            case "EXLX"://后二连选
            case "EXZUX"://EXZUX
            case "EXZUXBD"://后二包胆
            case "EXBD"://后二包点
            case "EXHZ"://后二和值
                /*  时时彩*/
            case "EXKD"://后二跨度
                codeArr = getSplitContent(code);

                code1Sb1 = new StringBuffer();
                code1Sb2 = new StringBuffer();

                code1Sb1.append(codeArr[0]).append(codeArr[1]).append(codeArr[2]);
                code1Sb2.append(codeArr[3]).append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorAfter(code1Sb1.toString(), code1Sb2.toString()));

                break;
            case "QSDXDS"://前三大小单双
            case "QSYMBDW"://前三一码不定位
            case "QSEMBDW"://前三二码不定位
                /*11选5  前三*/
            case "SDQSZX"://前三直选
            case "SDQSZUX"://前三组选
            case "SDDTQSZUX"://前三组选胆拖
                /*11选5 不定位胆*/
            case "SDQSBDW"://前三不定位胆
                /*  时时彩*/
            case "QSKD"://前三跨度
                /*  时时彩*/
            case "QSBD"://前三包点
                /*  时时彩*/
            case "QSZX"://前三直选
            case "QSLX"://前三连选
            case "QSZS"://前三组三
            case "QSZL"://前三组六
            case "QSHHZX"://前三混合组选
            case "QSZUXBD"://前三包胆
            case "QSHZ"://前三和值
            case "QSZXHZ"://组选和值   P3组选
                codeArr = getSplitContent(code);

                code1Sb1 = new StringBuffer();
                code1Sb2 = new StringBuffer();
                code1Sb1.append(codeArr[0]).append(codeArr[1]).append(codeArr[2]);
                code1Sb2.append(codeArr[3]).append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorBefore(code1Sb1.toString(), code1Sb2.toString()));

                break;
            case "SXDXDS"://后三大小单双
            case "YMBDW"://后三一码不定位
            case "EMBDW"://后三二码不定位
                /*11选5 后三*/
            case "SDSXZX"://后三直选
            case "SDSXZUX"://后三组选
            case "SDDTSXZUX"://后三组选胆拖
                /*  时时彩*/
            case "SXKD"://后三跨度
                /*  时时彩*/
            case "SXBD"://后三包点
            case "ZSHZ"://后三直选
            case "SXLX"://后三连选
            case "SXZS"://后三组三
            case "SXZL"://后三组六
            case "SXHHZX"://后三混合组选
            case "SXZUXBD"://后三包胆
            case "SXHZ"://后三和值
            case "SXZX"://后三直选
            case "HSZUXBD"://后三包胆
                codeArr = getSplitContent(code);

                code1Sb1 = new StringBuffer();
                code1Sb2 = new StringBuffer();

                code1Sb1.append(codeArr[0]).append(codeArr[1]);
                code1Sb2.append(codeArr[2]).append(codeArr[3]).append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorAfter(code1Sb1.toString(), code1Sb2.toString()));

                break;
            case "ZSDXDS"://中三大小单双
            case "ZSYMBDW"://中三一码不定位
            case "ZSEMBDW"://中三二码不定位
                /*  时时彩*/
            case "ZSKD"://中三跨度
                /*  时时彩*/
            case "ZSBD"://中三包点
                /*  时时彩*/
            case "ZSZX"://中三直选
            case "ZSLX":
            case "ZSZS":
            case "ZSZL":
            case "ZSHHZX":
            case "ZSZUXBD":
                codeArr = getSplitContent(code);

                code1Sb1 = new StringBuffer();
                code1Sb2 = new StringBuffer();

                StringBuffer code1Sb3 = new StringBuffer();

                code1Sb1.append(codeArr[0]);
                code1Sb2.append(codeArr[1]).append(codeArr[2]).append(codeArr[3]);
                code1Sb3.append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorCenter(code1Sb1.toString(), code1Sb2.toString(), code1Sb3.toString()));

                break;
            /* 前四个显示不同的*/
            /*  时时彩*/
            case "QSIZX"://前四直选
                /*  时时彩*/
            case "QSIZUX4"://前四直选
            case "QSIZUX6"://
            case "QSIZUX12":
            case "QSIZUX24":
                codeArr = getSplitContent(code);

                code1Sb1 = new StringBuffer();
                code1Sb2 = new StringBuffer();

                code1Sb1.append(codeArr[0]).append(codeArr[1]).append(codeArr[2]).append(codeArr[3]);
                code1Sb2.append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorAfter(code1Sb1.toString(), code1Sb2.toString()));

                break;
            /* 后四个显示不同的*/
            case "SXYMBDW"://四星一码不定位
            case "SXEMBDW"://四星二码不定位
                /*  时时彩*/
            case "SIXZX"://后四直选
                /*  时时彩*/
            case "ZUX4"://后四组选4
            case "ZUX6"://后四组选6
            case "ZUX12"://后四组选12
            case "ZUX24"://后四组选24
                codeArr = getSplitContent(code);

                code1Sb1 = new StringBuffer();
                code1Sb2 = new StringBuffer();

                code1Sb1.append(codeArr[0]);
                code1Sb2.append(codeArr[1]).append(codeArr[2]).append(codeArr[3]).append(codeArr[4]);
                holder.code.setText(ResultsUtils.showDiffColorAfter(code1Sb1.toString(), code1Sb2.toString()));

                break;
            default:
                holder.code.setText(code);
        }
    }

    private String[] getSplitContent(String code) {
        if (code.contains(" ")) {
            String[] codeArr = code.split(" ");

            String[] newCodeArr = new String[codeArr.length];
            for (int i = 0; i < codeArr.length; i++) {
                if (i != codeArr.length - 1) {
                    newCodeArr[i] = codeArr[i] + " ";
                } else {
                    newCodeArr[i] = codeArr[i];
                }
            }

            return newCodeArr;
        } else {
            String[] codeArr = code.split("");
            String[] newCodeArr = new String[5];
            int count = 0;
            for (int i = 0; i < codeArr.length; i++) {
                if (!TextUtils.isEmpty(codeArr[i])) {
                    newCodeArr[count++] = codeArr[i];
                }
            }

            return newCodeArr;
        }
    }

    //根据字母判定花色  花色?s,?h ?c ?d(一律小写)
    private Drawable letterToDrawable(String letter) {
        if ("s".equals(letter)) {
            return UiUtils.getDrawable(mContext, R.drawable.ht_icon);
        } else if ("h".equals(letter)) {
            return UiUtils.getDrawable(mContext, R.drawable.hongt_icon);
        } else if ("c".equals(letter)) {
            return UiUtils.getDrawable(mContext, R.drawable.mh_icon);
        } else if ("d".equals(letter)) {
            return UiUtils.getDrawable(mContext, R.drawable.fk_icon);
        } else {
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
        @BindView(R.id.history_xingtai)
        TextView history_xingtai;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;


        public ViewHolderShandongKuailePuke(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }

    public interface SortOut {
        void sort(ArrayList<Integer> integers);
    }

    static class ViewHolder implements SortOut {
        @BindView(R.id.historycode_issue)
        TextView issue;
        @BindView(R.id.historycode_code)
        TextView code;
        @BindView(R.id.tv_1)
        TextView tv1;
        @BindView(R.id.tv_2)
        TextView tv2;
        @BindView(R.id.tv_3)
        TextView tv3;
        @BindView(R.id.tv_4)
        TextView tv4;
        @BindView(R.id.tv_5)
        TextView tv5;
        @BindView(R.id.tv_6)
        TextView tv6;
        @BindView(R.id.tv_7)
        TextView tv7;
        @BindView(R.id.tv_8)
        TextView tv8;
        @BindView(R.id.tv_9)
        TextView tv9;
        @BindView(R.id.tv_10)
        TextView tv10;
        @BindView(R.id.tv_11)
        TextView tv11;
        @BindView(R.id.ll_main)
        LinearLayout ll_main;

        protected List<TextView> textViews;
        protected List<TextView> views;
        protected List<StringBuilder> builders;
        protected List<Integer> integers;
        protected List<Integer> selectedInts;
        protected int size;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            initData();
            view.setTag(this);
        }

        public void initData() {
            textViews = new ArrayList<>();
            textViews.add(tv1);
            textViews.add(tv2);
            textViews.add(tv3);
            textViews.add(tv4);
            textViews.add(tv5);
            textViews.add(tv6);
            textViews.add(tv7);
            textViews.add(tv8);
            textViews.add(tv9);
            textViews.add(tv10);
            views = new ArrayList<>();
            builders = new ArrayList<>();
            selectedInts = new ArrayList<>();
        }

        public void addData(int num) {
            for (int i = 0; i < num; i++) {
                TextView textView = textViews.get(i);
                textView.setVisibility(View.VISIBLE);
                views.add(textView);
                builders.add(new StringBuilder());
            }
        }

        public void reset() {
            for (TextView textView : textViews)
                textView.setVisibility(View.GONE);
            if (views != null && views.size() > 0)
                views.clear();
            if (builders != null && builders.size() > 0)
                builders.clear();
            if (selectedInts != null && selectedInts.size() > 0)
                selectedInts.clear();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            this.integers = integers;
            size = integers.size();
        }

        public void show(int position) {
            if (views.size() <= builders.size()) {
                if (position % 2 == 0) {
                    issue.setBackgroundResource(R.drawable.background_border);
                    code.setBackgroundResource(R.drawable.background_border);
                } else {
                    issue.setBackgroundResource(R.drawable.background_dark_border);
                    code.setBackgroundResource(R.drawable.background_dark_border);
                }
                for (int i = 0, size = views.size(); i < size; i++) {
                    views.get(i).setText(builders.get(i).toString());
                    builders.get(i).setLength(0);
                    if (position % 2 == 0)
                        views.get(i).setBackgroundResource(R.drawable.background_border);
                    else
                        views.get(i).setBackgroundResource(R.drawable.background_dark_border);
                }
            }
        }
    }

    static class ViewHolder3Lines extends ViewHolder {
        public ViewHolder3Lines(View view) {
            super(view);
            addData(3);
        }
    }

    static class ViewHolderHZ extends ViewHolder {
        public ViewHolderHZ(View view) {
            super(view);
        }

        public int getSum(List<Integer> list) {
            int sum = 0;
            for (int i : list)
                sum += i;
            return sum;
        }
    }

    static class ViewHolderHZHDXHDS extends ViewHolderHZ {
        public ViewHolderHZHDXHDS(View view) {
            super(view);
            addData(3);
        }

        public String getHDX(List<Integer> list, int boundary) {
            return getSum(list) < boundary ? "小" : "大";
        }

        public String getHDS(List<Integer> list) {
            return getSum(list) % 2 != 0 ? "单" : "双";
        }
    }

    static class ViewHolderDXDS extends ViewHolder {
        public ViewHolderDXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
            addData(2);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            sort2();
        }

        protected void sort2() {
            if (mLotteryType == 1 || mLotteryType == 4)
                for (int num : selectedInts) {
                    builders.get(0).append(num < 5 ? "小" : "大");
                    builders.get(1).append(num % 2 == 0 ? "双" : "单");
                }
            else
                for (int num : selectedInts) {
                    builders.get(0).append(num < 6 ? "小" : "大");
                    builders.get(1).append(num % 2 == 0 ? "双" : "单");
                }
        }
    }

    static class ViewHolder5XDXDS extends ViewHolderDXDS {
        public ViewHolder5XDXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        protected void sort2() {
            selectedInts = integers;
            super.sort2();
        }
    }

    static class ViewHolderQ2DXDS extends ViewHolderDXDS {
        public ViewHolderQ2DXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            super.sort2();
        }
    }

    static class ViewHolderQ3DXDS extends ViewHolderDXDS {
        public ViewHolderQ3DXDS(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            super.sort2();
        }
    }

    static class ViewHolderH4DXDS extends ViewHolderDXDS {
        public ViewHolderH4DXDS(View view) {
            super(view);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            selectedInts.add(integers.get(4));
            super.sort2();
        }
    }

    static class ViewHolderZ3DXDS extends ViewHolderDXDS {
        public ViewHolderZ3DXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            super.sort2();
        }
    }

    static class ViewHolderH3DXDS extends ViewHolderDXDS {
        public ViewHolderH3DXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            selectedInts.add(integers.get(4));
            super.sort2();
        }
    }

    static class ViewHolderH2DXDS extends ViewHolderDXDS {
        public ViewHolderH2DXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.add(integers.get(size - 2));
            selectedInts.add(integers.get(size - 1));
            super.sort2();
        }
    }

    static class ViewHolderHZDXDS extends ViewHolder3Lines {
        public ViewHolderHZDXDS(View view) {
            super(view);
        }

        @Override
        public void initData() {
            super.initData();
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            sort2();
        }

        public void sort2() {
            int sum = 0;
            for (int num : selectedInts) {
                sum += num;
                builders.get(1).append(num < 5 ? "小" : "大");
                builders.get(2).append(num % 2 == 0 ? "双" : "单");
            }
            builders.get(0).append(String.valueOf(sum));
        }
    }

    static class ViewHolderH2HZDXDS extends ViewHolderHZDXDS {
        public ViewHolderH2HZDXDS(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        public void sort2() {
            if (size > 2) {
                selectedInts.clear();
                selectedInts.add(integers.get(size - 2));
                selectedInts.add(integers.get(size - 1));
            }
            super.sort2();
        }
    }

    static class ViewHolderQ2HZDXDS extends ViewHolderHZDXDS {
        public ViewHolderQ2HZDXDS(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }

        @Override
        public void sort2() {
            if (size > 1) {
                selectedInts.clear();
                selectedInts.add(integers.get(0));
                selectedInts.add(integers.get(1));
            }
            super.sort2();
        }
    }

    static class ViewHolderKD extends ViewHolder3Lines {
        public ViewHolderKD(View view) {
            super(view);
        }

        public int getKD(List<Integer> list) {
            int max = 0, min;
            if (mLotteryType == 1)
                min = 9;
            else
                min = 11;
            for (int num : list) {
                if (num > max)
                    max = num;
                if (num < min)
                    min = num;
            }
            return (max - min) > 0 ? (max - min) : 0;
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }
    }

    static class ViewHolderHZKD extends ViewHolderKD {
        public ViewHolderHZKD(View view) {
            super(view);
        }

        public int getSum(List<Integer> integers) {
            int sum = 0;
            for (int num : integers) {
                sum += num;
            }
            return sum;
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }
    }

    static class ViewHolderHZKDDX extends ViewHolderHZKD {
        public ViewHolderHZKDDX(View view) {
            super(view);
        }

        public void setDX(List<Integer> integers, int position) {
            if (mLotteryType == 1 /*|| mLotteryType == 4*/)
                for (int num : integers) {
                    builders.get(position).append(num < 5 ? "小" : "大");
                }
            /*else
                for (int num : integers)
                {
                    builders.get(position).append(num < 6 ? "小" : "大");
                }*/
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }
    }

    static class ViewHolderHZHWKD extends ViewHolderHZKD {
        public ViewHolderHZHWKD(View view) {
            super(view);
        }

        public int getHW(List<Integer> integers) {
            return getSum(integers) % 10;
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }
    }

    static class ViewHolderQ2HZKDDX extends ViewHolderHZKDDX {
        public ViewHolderQ2HZKDDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            if (size > 4) {
                selectedInts.clear();
                selectedInts.add(integers.get(0));
                selectedInts.add(integers.get(1));
                builders.get(0).append(getSum(selectedInts));
                builders.get(1).append(getKD(selectedInts));
                setDX(selectedInts, 2);
            }
        }
    }

    static class ViewHolderH2HZKDDX extends ViewHolderHZKDDX {
        public ViewHolderH2HZKDDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            if (size > 4) {
                selectedInts.clear();
                selectedInts.add(integers.get(size - 2));
                selectedInts.add(integers.get(size - 1));
                builders.get(0).append(getSum(selectedInts));
                builders.get(1).append(getKD(selectedInts));
                setDX(selectedInts, 2);
            }
        }
    }

    static class ViewHolderQ3HZKDDX extends ViewHolderHZKDDX {
        public ViewHolderQ3HZKDDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getKD(selectedInts));
            setDX(selectedInts, 2);
            /*if (size > 3)
            {
                builders.get(0).append(String.valueOf(integers.get(0) + integers.get(1) + integers.get(2)));
                for (int i = 0; i < 3; i++)
                {
                    if (integers.get(i) < 5)
                        builders.get(2).append("小");
                    else
                        builders.get(2).append("大");
                    int num = integers.get(i);
                    if (num > max)
                        max = num;
                    if (num < min)
                        min = num;
                }
                builders.get(1).append(String.valueOf(max - min));
            }*/
        }
    }

    static class ViewHolderQ3HZZXDX extends ViewHolderQ3HZKDDX {
        public ViewHolderQ3HZZXDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(1, list));
        }
    }

    static class ViewHolderZ3HZZXDX extends ViewHolderZ3HZKDDX {
        public ViewHolderZ3HZZXDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < 4; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(1, list));
        }
    }

    static class ViewHolderH3HZZXDX extends ViewHolderH3HZKDDX {
        public ViewHolderH3HZZXDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 2; i < size; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(1, list));
        }
    }

    static class ViewHolderQ4ZX extends ViewHolderHZHWKD {
        public ViewHolderQ4ZX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getHW(selectedInts));
            builders.get(2).append(getKD(selectedInts));
        }
    }

    static class ViewHolderQ4HZZXKD extends ViewHolderQ4ZX {
        public ViewHolderQ4HZZXKD(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(2, list));
        }
    }

    static class ViewHolderH4HZZXKD extends ViewHolderH4ZX {
        public ViewHolderH4HZZXKD(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < 5; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(2, list));
        }
    }

    static class ViewHolderH4ZX extends ViewHolderHZHWKD {
        public ViewHolderH4ZX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            selectedInts.add(integers.get(4));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getHW(selectedInts));
            builders.get(2).append(getKD(selectedInts));
        }
    }

    static class ViewHolderQ3BD extends ViewHolderHZHDXHDS {
        public ViewHolderQ3BD(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getHDX(selectedInts, 14));
            builders.get(2).append(getHDS(selectedInts));
        }
    }

    static class ViewHolderZ3HZKDDX extends ViewHolderHZKDDX {
        public ViewHolderZ3HZKDDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getKD(selectedInts));
            setDX(selectedInts, 2);
            /*int size = integers.size();
            if (size > 4)
            {
                builders.get(0).append(String.valueOf(integers.get(1) + integers.get(2) + integers.get(3)));
                for (int i = 1; i < 4; i++)
                {
                    if (integers.get(i) < 5)
                        builders.get(2).append("小");
                    else
                        builders.get(2).append("大");
                    int num = integers.get(i);
                    if (num > max)
                        max = num;
                    if (num < min)
                        min = num;
                }
                builders.get(1).append(String.valueOf(max - min));
            }*/
        }
    }

    static class ViewHolderZ3BD extends ViewHolderHZHDXHDS {
        public ViewHolderZ3BD(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getHDX(selectedInts, 14));
            builders.get(2).append(getHDS(selectedInts));
        }
    }

    static class ViewHolderH3HZKDDX extends ViewHolderHZKDDX {
        public ViewHolderH3HZKDDX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(size - 3));
            selectedInts.add(integers.get(size - 2));
            selectedInts.add(integers.get(size - 1));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getKD(selectedInts));
            setDX(selectedInts, 2);
            /*int size = integers.size();
            if (size > 3)
            {
                builders.get(0).append(String.valueOf(integers.get(size - 1) + integers.get(size - 2) + integers.get
                        (size - 3)));
                for (int i = size - 3; i < size; i++)
                {
                    if (integers.get(i) < 5)
                        builders.get(2).append("小");
                    else
                        builders.get(2).append("大");
                    int num = integers.get(i);
                    if (num > max)
                        max = num;
                    if (num < min)
                        min = num;
                }
                builders.get(1).append(String.valueOf(max - min));
            }*/
        }
    }

    static class ViewHolderH3BD extends ViewHolderHZHDXHDS {
        public ViewHolderH3BD(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            selectedInts.clear();
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            selectedInts.add(integers.get(4));
            builders.get(0).append(getSum(selectedInts));
            builders.get(1).append(getHDX(selectedInts, 14));
            builders.get(2).append(getHDS(selectedInts));
        }
    }

    static class ViewHolder5XHZHDXHW extends ViewHolderHZHWKD {
        public ViewHolder5XHZHDXHW(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            int sum = getSum(integers);
            String[] temp = String.valueOf(sum).split("");
            builders.get(0).append(sum);
            builders.get(1).append(sum % 2 != 0 ? "单" : "双");
            builders.get(2).append(temp[temp.length - 1]);
        }
    }

    static class ViewHolder5XHZDXDS extends ViewHolderHZHDXHDS {
        public ViewHolder5XHZDXDS(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 23));
            builders.get(2).append(getHDS(integers));
        }
    }

    static class ViewHolder3XHZDXDS extends ViewHolderHZHDXHDS {
        public ViewHolder3XHZDXDS(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 11));
            builders.get(2).append(getHDS(integers));
        }
    }

    static class ViewHolder5XHZKDZX extends ViewHolderHZKD {
        public ViewHolder5XHZKDZX(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getKD(integers));
            builders.get(2).append(getZuXuanResult(3, integers));
        }
    }

    static class ViewHolderQWWF extends ViewHolderHZKD {
        public ViewHolderQWWF(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getKD(integers));
            builders.get(2).append(getZuXuanResult(4, integers));
        }
    }

    static class ViewHolderLHH extends ViewHolder {
        public ViewHolderLHH(View view) {
            super(view);
            addData(10);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            if (size > 4) {
                int myriabit = integers.get(0);
                int thousand = integers.get(1);
                int hundred = integers.get(2);
                int ten = integers.get(3);
                int unit = integers.get(4);
                builders.get(0).append(getLHH(myriabit, thousand));
                builders.get(1).append(getLHH(myriabit, hundred));
                builders.get(2).append(getLHH(myriabit, ten));
                builders.get(3).append(getLHH(myriabit, unit));
                builders.get(4).append(getLHH(thousand, hundred));
                builders.get(5).append(getLHH(thousand, ten));
                builders.get(6).append(getLHH(thousand, unit));
                builders.get(7).append(getLHH(hundred, ten));
                builders.get(8).append(getLHH(hundred, unit));
                builders.get(9).append(getLHH(ten, unit));
            }
        }
    }

    static private String getLHH(int higher, int lower) {
        if (higher > lower)
            return "龙";
        else if (higher < lower)
            return "虎";
        else
            return "和";
    }

    static class ViewHolderNN extends ViewHolder {
        public ViewHolderNN(View view) {
            super(view);
            addData(2);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            int totalAmount = 0;
            String label = "";
            boolean isOther = true;
            for (int i = 0; i < size - 2; i++) {
                totalAmount += integers.get(i);
                for (int j = i + 1; j < size - 1; j++) {
                    for (int k = j + 1; k < size; k++) {
                        int sum = integers.get(i) + integers.get(j) + integers.get(k);
                        if (sum % 10 == 0) {
                            isOther = false;
                            int otherSum = 0;
                            for (int l = 0; l < size; l++) {
                                if (l == i || l == j || l == k)
                                    continue;
                                otherSum += integers.get(l);
                            }
                            switch (otherSum % 10) {
                                case 0:
                                    label = "牛牛";
                                    break;
                                case 1:
                                    label = "牛一";
                                    break;
                                case 2:
                                    label = "牛二";
                                    break;
                                case 3:
                                    label = "牛三";
                                    break;
                                case 4:
                                    label = "牛四";
                                    break;
                                case 5:
                                    label = "牛五";
                                    break;
                                case 6:
                                    label = "牛六";
                                    break;
                                case 7:
                                    label = "牛七";
                                    break;
                                case 8:
                                    label = "牛八";
                                    break;
                                case 9:
                                    label = "牛九";
                                    break;
                            }
                        }
                    }
                }
            }
            if (isOther)
                label = "杂牌";
            totalAmount += integers.get(size - 2) + integers.get(size - 1);
            builders.get(0).append(String.valueOf(totalAmount));
            builders.get(1).append(label);
        }
    }

    static class ViewHolderDDS extends ViewHolder {
        public ViewHolderDDS(View view) {
            super(view);
            addData(1);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            int odd = 0, even = 0;
            for (int n : integers)
                if (n % 2 != 0)
                    odd++;
                else
                    even++;
            builders.get(0).append(odd + "单" + even + "双");
        }
    }

    static class ViewHolderCZW extends ViewHolder {
        public ViewHolderCZW(View view) {
            super(view);
            addData(2);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            Collections.sort(integers);
            int num = integers.get(2);
            builders.get(0).append(num < 10 ? "0" + num : String.valueOf(num));
            builders.get(1).append(num % 2 != 0 ? "单" : "双");
        }
    }

    static class ViewHolderHZDXXT extends ViewHolderHZHDXHDS {
        public ViewHolderHZDXXT(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 11));
            builders.get(2).append(getZuXuanResult(5, integers));
        }
    }

    static class ViewHolderHZDXYS extends ViewHolderHZHDXHDS {
        public ViewHolderHZDXYS(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 11));
            int red = 0, black = 0;
            for (int i : integers) {
                if (i == 1 || i == 4)
                    red++;
                else
                    black++;
            }
            if (red == 3)
                builders.get(2).append("全红");
            else if (black == 3)
                builders.get(2).append("全黑");
            else
                builders.get(2).append(red + "红" + black + "黑");
        }
    }

    static class ViewHolderLHC extends ViewHolder {
        protected int tm;

        public ViewHolderLHC(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            tm = integers.get(size - 1);
        }
    }

    static class ViewHolderSXSB extends ViewHolderLHC {
        public ViewHolderSXSB(View view) {
            super(view);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
        }
    }

    static class ViewHolderHZZX extends ViewHolderHZ {
        public ViewHolderHZZX(View view) {
            super(view);
            addData(2);
        }

        @Override
        public void sort(ArrayList<Integer> integers) {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getZuXuanResult(1, integers));
        }
    }

    static class ViewHolderAllDXDS extends ViewHolderDXDS {
        public ViewHolderAllDXDS(View view) {
            super(view);
        }

        @Override
        protected void sort2() {
            selectedInts.clear();
            selectedInts.addAll(integers);
            super.sort2();
        }

    }


    static String getZuXuanResult(int mode, ArrayList<Integer> list) {
        int size = list.size();
        ArrayList<Integer> doneList = new ArrayList<>();
        int match2 = 0;
        int match3 = 0;
        switch (mode) {
            case 1:
                if (size == 3) {
                    for (int i = 0; i < size - 1; i++) {
                        for (int j = i + 1; j < size; j++)
                            if (list.get(i) == list.get(j))
                                match2++;
                    }
                    switch (match2) {
                        case 0:
                            return "组六";
                        case 1:
                            return "组三";
                        case 3:
                            return "豹子";
                    }
                    return "";
                }
                break;
            case 2:
                if (size == 4) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "豹子";
                        if (NumbericUtils.getApperTimes(i, list) == 3)
                            return "组选4";
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match2 == 2)
                        return "组选6";
                    else if (match2 == 1)
                        return "组选12";
                    else
                        return "组选24";
                }
                break;
            case 3:
                if (size == 5) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 5)
                            return "豹子";
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "组选5";
                        if (NumbericUtils.getApperTimes(i, list) == 3) {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match3 == 1 && match2 == 1)
                        return "组选10";
                    else if (match3 == 1 && match2 < 1)
                        return "组选20";
                    else if (match2 == 2)
                        return "组选30";
                    else if (match2 == 1 && match3 < 1)
                        return "组选60";
                    else
                        return "组选120";
                }
                break;
            case 4:
                if (size == 5) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "四季发财";
                        if (NumbericUtils.getApperTimes(i, list) == 3) {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match3 == 1)
                        return "三星报喜";
                    else if (match2 == 1 || match2 == 2)
                        return "好事成双";
                    else
                        return "一帆风顺";
                }
                break;
            case 5:
                if (size == 3) {
                    for (int i : list) {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 3) {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2) {
                            match2++;
                            doneList.add(i);
                        }
                    }
                    if (match3 == 1)
                        return "三同号";
                    else if (match2 == 1)
                        return "二同号";
                    else if (NumbericUtils.isConsecutiveNumbers(list))
                        return "三连号";
                    else
                        return "-";
                }
                break;
        }
        return "";
    }

}
