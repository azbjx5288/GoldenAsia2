package com.goldenasia.lottery.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.IssueEntity;
import com.goldenasia.lottery.util.NumbericUtils;
import com.goldenasia.lottery.util.UiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/3/1.
 */
public class HistoryCodeAdapter extends BaseAdapter
{
    private List codeList;
    private int mLotteryId;
    private static int mLotteryType = -1;
    private String mMethodName;
    private Context mContext;
    
    public HistoryCodeAdapter(List codeList, int lotteryId, int lotteryType, String methodName, Context context)
    {
        this.codeList = codeList;
        mLotteryId = lotteryId;
        mLotteryType = lotteryType;
        mMethodName = methodName;
        mContext = context;
    }
    
    @Override
    public int getCount()
    {
        return codeList != null ? codeList.size() : 0;
    }
    
    @Override
    public Object getItem(int position)
    {
        if (codeList == null)
        {
            return null;
        }
        if (position >= 0 && position < codeList.size())
        {
            return codeList.get(position);
        }
        return null;
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    public void setData(List codeList)
    {
        this.codeList = codeList;
        notifyDataSetChanged();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (mLotteryId == 14)//山东快乐扑克
        {
            ViewHolderShandongKuailePuke holder;
            if (convertView == null)
            {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                        .fragment_history_result_item_shandongkuailepuke, parent, false);
                holder = new ViewHolderShandongKuailePuke(convertView);
            } else
            {
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
        } else
        {
            //if (!TextUtils.isEmpty(mMethodName) && mLotteryType != -1)
            switch (mMethodName)
            {
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
                    convertView = getSortedView(position, convertView, parent, "dx_ds");
                    break;
                case "EXZX":
                case "EXLX":
                case "EXZUX":
                case "EXZUXBD":
                case "EXBD":
                case "EXHZ":
                    convertView = getSortedView(position, convertView, parent, "h2_hz_dx_ds");
                    break;
                case "QEZX":
                case "QELX":
                case "QEZUX":
                case "QEZUXBD":
                case "QEBD":
                case "QEHZ":
                    convertView = getSortedView(position, convertView, parent, "q2_hz_dx_ds");
                    break;
                case "EXKD":
                    convertView = getSortedView(position, convertView, parent, "h2_hz_kd_dx");
                    break;
                case "QEKD":
                    convertView = getSortedView(position, convertView, parent, "q2_hz_kd_dx");
                    break;
                case "QSKD":
                    convertView = getSortedView(position, convertView, parent, "q3_hz_kd_dx");
                    break;
                case "ZSKD":
                    convertView = getSortedView(position, convertView, parent, "z3_hz_kd_dx");
                    break;
                case "SXKD":
                    convertView = getSortedView(position, convertView, parent, "h3_hz_kd_dx");
                    break;
                case "QSBD":
                    convertView = getSortedView(position, convertView, parent, "q3_bd");
                    break;
                case "ZSBD":
                    convertView = getSortedView(position, convertView, parent, "z3_bd");
                    break;
                case "SXBD":
                    convertView = getSortedView(position, convertView, parent, "h3_bd");
                    break;
                case "QSIZX":
                    convertView = getSortedView(position, convertView, parent, "q4_zx");
                    break;
                case "SIXZX":
                    convertView = getSortedView(position, convertView, parent, "h4_zx");
                    break;
                case "WXHZ":
                    convertView = getSortedView(position, convertView, parent, "wx_hz");
                    break;
                case "QEDXDS":
                case "SDQEZX":
                case "SDQEZUX":
                case "SDDTQEZUX":
                case "SDLX2":
                    convertView = getSortedView(position, convertView, parent, "q2_dx_ds");
                    break;
                case "EXDXDS":
                case "SDEXZX":
                case "SDEXZUX":
                case "SDDTEXZUX":
                    convertView = getSortedView(position, convertView, parent, "h2_dx_ds");
                    break;
                case "QSDXDS":
                case "QSYMBDW":
                case "QSEMBDW":
                case "SDQSZX":
                case "SDQSZUX":
                case "SDDTQSZUX":
                case "SDQSBDW":
                case "SDLX3":
                    convertView = getSortedView(position, convertView, parent, "q3_dx_ds");
                    break;
                case "ZSDXDS":
                case "ZSYMBDW":
                case "ZSEMBDW":
                    convertView = getSortedView(position, convertView, parent, "z3_dx_ds");
                    break;
                case "SXDXDS":
                case "SDSXZX":
                case "SDSXZUX":
                case "SDDTSXZUX":
                    convertView = getSortedView(position, convertView, parent, "h3_dx_ds");
                    break;
                case "YMBDW":
                case "EMBDW":
                    if (mLotteryType == 1)
                    {
                        convertView = getSortedView(position, convertView, parent, "h3_dx_ds");
                        break;
                    } else
                    {
                        convertView = getSortedView(position, convertView, parent, "dx_ds");
                        break;
                    }
                case "SXYMBDW":
                case "SXEMBDW":
                    convertView = getSortedView(position, convertView, parent, "h4_dx_ds");
                    break;
                case "WXHZDXDS":
                    convertView = getSortedView(position, convertView, parent, "5x_hz_dx_ds");
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
                    convertView = getSortedView(position, convertView, parent, "3x_hz_dx_ds");
                    break;
                case "JSETDX":
                case "JSSTDX":
                case "JSETFX":
                case "JSSTTX":
                case "JSSLTX":
                    convertView = getSortedView(position, convertView, parent, "hz_dx_xt");
                    break;
                case "RELHH":
                    convertView = getSortedView(position, convertView, parent, "lhh");
                    break;
                case "NIUNIU":
                    convertView = getSortedView(position, convertView, parent, "nn");
                    break;
                case "QSZX":
                case "QSLX":
                case "QSZS":
                case "QSZL":
                case "QSHHZX":
                case "QSZUXBD":
                case "QSHZ":
                    convertView = getSortedView(position, convertView, parent, "q3_hz_zx_dx");
                    break;
                case "ZSZX":
                case "ZSLX":
                case "ZSZS":
                case "ZSZL":
                case "ZSHHZX":
                case "ZSZUXBD":
                case "ZSHZ":
                    convertView = getSortedView(position, convertView, parent, "z3_hz_zx_dx");
                    break;
                case "SXLX":
                case "SXZS":
                case "SXZL":
                case "SXHHZX":
                case "SXZUXBD":
                case "SXHZ":
                    convertView = getSortedView(position, convertView, parent, "h3_hz_zx_dx");
                    break;
                case "SXZX":
                    if (mLotteryType == 1)
                    {
                        convertView = getSortedView(position, convertView, parent, "h3_hz_zx_dx");
                        break;
                    } else
                    {
                        convertView = getSortedView(position, convertView, parent, "hz_zx");
                        break;
                    }
                case "QSIZUX4":
                case "QSIZUX6":
                case "QSIZUX12":
                case "QSIZUX24":
                    convertView = getSortedView(position, convertView, parent, "q4_hz_zx_kd");
                    break;
                case "ZUX4":
                case "ZUX6":
                case "ZUX12":
                case "ZUX24":
                    convertView = getSortedView(position, convertView, parent, "h4_hz_zx_kd");
                    break;
                case "WXZX":
                case "WXLX":
                case "ZUX5":
                case "ZUX10":
                case "ZUX20":
                case "ZUX30":
                case "ZUX60":
                case "ZUX120":
                    convertView = getSortedView(position, convertView, parent, "5x_hz_kd_zx");
                    break;
                case "YFFS":
                case "HSCS":
                case "SXBX":
                case "SJFC":
                    convertView = getSortedView(position, convertView, parent, "qwwf");
                    break;
                case "SDDDS":
                    convertView = getSortedView(position, convertView, parent, "dds");
                    break;
                case "SDCZW":
                    convertView = getSortedView(position, convertView, parent, "czw");
                    break;
                case "JSYS":
                    convertView = getSortedView(position, convertView, parent, "hz_dx_ys");
                    break;
                default:
                    convertView = getSortedView(position, convertView, parent, ""/*, R.layout
                    .fragment_history_result_item*/);
            }
        }
        
        return convertView;
    }
    
    private View getSortedView(int position, View convertView, ViewGroup parent, String holderName)
    {
        IssueEntity historyCode = (IssueEntity) codeList.get(position);
        String code = historyCode.getCode();
        ViewHolder holder;
        switch (holderName)
        {
            case "dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolder5XDXDS(convertView);
                } else
                {
                    holder = (ViewHolder5XDXDS) convertView.getTag();
                }
                break;
            case "h2_hz_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH2HZDXDS(convertView);
                } else
                {
                    holder = (ViewHolderH2HZDXDS) convertView.getTag();
                }
                break;
            case "q2_hz_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ2HZDXDS(convertView);
                } else
                {
                    holder = (ViewHolderQ2HZDXDS) convertView.getTag();
                }
                break;
            case "h2_hz_kd_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH2HZKDDX(convertView);
                } else
                {
                    holder = (ViewHolderH2HZKDDX) convertView.getTag();
                }
                break;
            case "q2_hz_kd_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ2HZKDDX(convertView);
                } else
                {
                    holder = (ViewHolderQ2HZKDDX) convertView.getTag();
                }
                break;
            case "q3_hz_zx_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ3HZZXDX(convertView);
                } else
                {
                    holder = (ViewHolderQ3HZZXDX) convertView.getTag();
                }
                break;
            case "z3_hz_zx_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderZ3HZZXDX(convertView);
                } else
                {
                    holder = (ViewHolderZ3HZZXDX) convertView.getTag();
                }
                break;
            case "h3_hz_zx_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH3HZZXDX(convertView);
                } else
                {
                    holder = (ViewHolderH3HZZXDX) convertView.getTag();
                }
                break;
            case "q3_hz_kd_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ3HZKDDX(convertView);
                } else
                {
                    holder = (ViewHolderQ3HZKDDX) convertView.getTag();
                }
                break;
            case "z3_hz_kd_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderZ3HZKDDX(convertView);
                } else
                {
                    holder = (ViewHolderZ3HZKDDX) convertView.getTag();
                }
                break;
            case "h3_hz_kd_dx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH3HZKDDX(convertView);
                } else
                {
                    holder = (ViewHolderH3HZKDDX) convertView.getTag();
                }
                break;
            case "q3_bd":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ3BD(convertView);
                } else
                {
                    holder = (ViewHolderQ3BD) convertView.getTag();
                }
                break;
            case "z3_bd":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderZ3BD(convertView);
                } else
                {
                    holder = (ViewHolderZ3BD) convertView.getTag();
                }
                break;
            case "h3_bd":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH3BD(convertView);
                } else
                {
                    holder = (ViewHolderH3BD) convertView.getTag();
                }
                break;
            case "h4_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH4DXDS(convertView);
                } else
                {
                    holder = (ViewHolderH4DXDS) convertView.getTag();
                }
                break;
            case "q4_zx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ4ZX(convertView);
                } else
                {
                    holder = (ViewHolderQ4ZX) convertView.getTag();
                }
                break;
            case "h4_zx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH4ZX(convertView);
                } else
                {
                    holder = (ViewHolderH4ZX) convertView.getTag();
                }
                break;
            case "q4_hz_zx_kd":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ4HZZXKD(convertView);
                } else
                {
                    holder = (ViewHolderQ4HZZXKD) convertView.getTag();
                }
                break;
            case "h4_hz_zx_kd":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH4HZZXKD(convertView);
                } else
                {
                    holder = (ViewHolderH4HZZXKD) convertView.getTag();
                }
                break;
            case "wx_hz":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolder5XHZHDXHW(convertView);
                } else
                {
                    holder = (ViewHolder5XHZHDXHW) convertView.getTag();
                }
                break;
            case "q2_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ2DXDS(convertView);
                } else
                {
                    holder = (ViewHolderQ2DXDS) convertView.getTag();
                }
                break;
            case "h2_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH2DXDS(convertView);
                } else
                {
                    holder = (ViewHolderH2DXDS) convertView.getTag();
                }
                break;
            case "q3_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQ3DXDS(convertView);
                } else
                {
                    holder = (ViewHolderQ3DXDS) convertView.getTag();
                }
                break;
            case "z3_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderZ3DXDS(convertView);
                } else
                {
                    holder = (ViewHolderZ3DXDS) convertView.getTag();
                }
                break;
            case "h3_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderH3DXDS(convertView);
                } else
                {
                    holder = (ViewHolderH3DXDS) convertView.getTag();
                }
                break;
            case "5x_hz_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolder5XHZDXDS(convertView);
                } else
                {
                    holder = (ViewHolder5XHZDXDS) convertView.getTag();
                }
                break;
            case "3x_hz_dx_ds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolder3XHZDXDS(convertView);
                } else
                {
                    holder = (ViewHolder3XHZDXDS) convertView.getTag();
                }
                break;
            case "5x_hz_kd_zx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolder5XHZKDZX(convertView);
                } else
                {
                    holder = (ViewHolder5XHZKDZX) convertView.getTag();
                }
                break;
            case "qwwf":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderQWWF(convertView);
                } else
                {
                    holder = (ViewHolderQWWF) convertView.getTag();
                }
                break;
            case "lhh":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderLHH(convertView);
                } else
                {
                    holder = (ViewHolderLHH) convertView.getTag();
                }
                break;
            case "nn":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderNN(convertView);
                } else
                {
                    holder = (ViewHolderNN) convertView.getTag();
                }
                break;
            case "dds":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderDDS(convertView);
                } else
                {
                    holder = (ViewHolderDDS) convertView.getTag();
                }
                break;
            case "czw":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderCZW(convertView);
                } else
                {
                    holder = (ViewHolderCZW) convertView.getTag();
                }
                break;
            case "hz_dx_xt":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderHZDXXT(convertView);
                } else
                {
                    holder = (ViewHolderHZDXXT) convertView.getTag();
                }
                break;
            case "hz_dx_ys":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderHZDXYS(convertView);
                } else
                {
                    holder = (ViewHolderHZDXYS) convertView.getTag();
                }
                break;
            case "hz_zx":
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_result,
                            parent, false);
                    holder = new ViewHolderHZZX(convertView);
                } else
                {
                    holder = (ViewHolderHZDXYS) convertView.getTag();
                }
                break;
            default:
                if (convertView == null)
                {
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout
                            .item_history_result/*fragment_history_result_item*/, parent, false);
                    holder = new ViewHolder(convertView);
                } else
                {
                    holder = (ViewHolder) convertView.getTag();
                }
        }
        
        holder.issue.setText(historyCode.getIssue().substring(4, historyCode.getIssue().length()));
        holder.code.setText(code);
        //if (!TextUtils.isEmpty(mMethodName) && mLotteryType != -1)
        String[] letters;
        if (code.contains(" "))
            letters = code.split(" ");
        else
            letters = code.split("");
        
        ArrayList<Integer> integers = new ArrayList<>();
        for (String s : letters)
        {
            if (TextUtils.isEmpty(s))
                continue;
            integers.add(Integer.parseInt(s));
        }
        holder.sort(integers);
        holder.show(position);
        
        return convertView;
    }
    
    //根据字母判定花色  花色?s,?h ?c ?d(一律小写)
    private Drawable letterToDrawable(String letter)
    {
        if ("s".equals(letter))
        {
            return UiUtils.getDrawable(mContext, R.drawable.ht_icon);
        } else if ("h".equals(letter))
        {
            return UiUtils.getDrawable(mContext, R.drawable.hongt_icon);
        } else if ("c".equals(letter))
        {
            return UiUtils.getDrawable(mContext, R.drawable.mh_icon);
        } else if ("d".equals(letter))
        {
            return UiUtils.getDrawable(mContext, R.drawable.fk_icon);
        } else
        {
            return null;
        }
    }
    
    static class ViewHolderShandongKuailePuke
    {
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
        
        public ViewHolderShandongKuailePuke(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
    
    public interface SortOut
    {
        void sort(ArrayList<Integer> integers);
    }
    
    static class ViewHolder implements SortOut
    {
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
        
        protected List<TextView> textViews;
        protected List<TextView> views;
        protected List<StringBuilder> builders;
        protected List<Integer> integers;
        protected List<Integer> selectedInts;
        protected int size;
        
        public ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            initData();
            view.setTag(this);
        }
        
        public void initData()
        {
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
        
        public void addData(int num)
        {
            for (int i = 0; i < num; i++)
            {
                TextView textView = textViews.get(i);
                textView.setVisibility(View.VISIBLE);
                views.add(textView);
                builders.add(new StringBuilder());
            }
        }
        
        public void reset()
        {
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
        public void sort(ArrayList<Integer> integers)
        {
            this.integers = integers;
            size = integers.size();
        }
        
        public void show(int position)
        {
            if (views.size() <= builders.size())
            {
                if (position % 2 == 0)
                {
                    issue.setBackgroundResource(R.drawable.background_border);
                    code.setBackgroundResource(R.drawable.background_border);
                } else
                {
                    issue.setBackgroundResource(R.drawable.background_dark_border);
                    code.setBackgroundResource(R.drawable.background_dark_border);
                }
                for (int i = 0, size = views.size(); i < size; i++)
                {
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
    
    static class ViewHolder3Lines extends ViewHolder
    {
        public ViewHolder3Lines(View view)
        {
            super(view);
            addData(3);
        }
    }
    
    static class ViewHolderHZ extends ViewHolder
    {
        public ViewHolderHZ(View view)
        {
            super(view);
        }
        
        public int getSum(List<Integer> list)
        {
            int sum = 0;
            for (int i : list)
                sum += i;
            return sum;
        }
    }
    
    static class ViewHolderHZHDXHDS extends ViewHolderHZ
    {
        public ViewHolderHZHDXHDS(View view)
        {
            super(view);
            addData(3);
        }
        
        public String getHDX(List<Integer> list, int boundary)
        {
            return getSum(list) < boundary ? "小" : "大";
        }
        
        public String getHDS(List<Integer> list)
        {
            return getSum(list) % 2 != 0 ? "单" : "双";
        }
    }
    
    static class ViewHolderDXDS extends ViewHolder
    {
        public ViewHolderDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
            addData(2);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            sort2();
        }
        
        protected void sort2()
        {
            if (mLotteryType == 1 || mLotteryType == 4)
                for (int num : selectedInts)
                {
                    builders.get(0).append(num < 5 ? "小" : "大");
                    builders.get(1).append(num % 2 == 0 ? "双" : "单");
                }
            else
                for (int num : selectedInts)
                {
                    builders.get(0).append(num < 6 ? "小" : "大");
                    builders.get(1).append(num % 2 == 0 ? "双" : "单");
                }
        }
    }
    
    static class ViewHolder5XDXDS extends ViewHolderDXDS
    {
        public ViewHolder5XDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts = integers;
            super.sort2();
        }
    }
    
    static class ViewHolderQ2DXDS extends ViewHolderDXDS
    {
        public ViewHolderQ2DXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            super.sort2();
        }
    }
    
    static class ViewHolderQ3DXDS extends ViewHolderDXDS
    {
        public ViewHolderQ3DXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.add(integers.get(0));
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            super.sort2();
        }
    }
    
    static class ViewHolderH4DXDS extends ViewHolderDXDS
    {
        public ViewHolderH4DXDS(View view)
        {
            super(view);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            selectedInts.add(integers.get(4));
            super.sort2();
        }
    }
    
    static class ViewHolderZ3DXDS extends ViewHolderDXDS
    {
        public ViewHolderZ3DXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.add(integers.get(1));
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            super.sort2();
        }
    }
    
    static class ViewHolderH3DXDS extends ViewHolderDXDS
    {
        public ViewHolderH3DXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.add(integers.get(2));
            selectedInts.add(integers.get(3));
            selectedInts.add(integers.get(4));
            super.sort2();
        }
    }
    
    static class ViewHolderH2DXDS extends ViewHolderDXDS
    {
        public ViewHolderH2DXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.add(integers.get(size - 2));
            selectedInts.add(integers.get(size - 1));
            super.sort2();
        }
    }
    
    static class ViewHolderHZDXDS extends ViewHolder3Lines
    {
        public ViewHolderHZDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void initData()
        {
            super.initData();
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            sort2();
        }
        
        public void sort2()
        {
            int sum = 0;
            for (int num : selectedInts)
            {
                sum += num;
                builders.get(1).append(num < 5 ? "小" : "大");
                builders.get(2).append(num % 2 == 0 ? "双" : "单");
            }
            builders.get(0).append(String.valueOf(sum));
        }
    }
    
    static class ViewHolderH2HZDXDS extends ViewHolderHZDXDS
    {
        public ViewHolderH2HZDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        public void sort2()
        {
            if (size > 2)
            {
                selectedInts.clear();
                selectedInts.add(integers.get(size - 2));
                selectedInts.add(integers.get(size - 1));
            }
            super.sort2();
        }
    }
    
    static class ViewHolderQ2HZDXDS extends ViewHolderHZDXDS
    {
        public ViewHolderQ2HZDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
        
        @Override
        public void sort2()
        {
            if (size > 1)
            {
                selectedInts.clear();
                selectedInts.add(integers.get(0));
                selectedInts.add(integers.get(1));
            }
            super.sort2();
        }
    }
    
    static class ViewHolderKD extends ViewHolder3Lines
    {
        public ViewHolderKD(View view)
        {
            super(view);
        }
        
        public int getKD(List<Integer> list)
        {
            int max = 0, min;
            if (mLotteryType == 1)
                min = 9;
            else
                min = 11;
            for (int num : list)
            {
                if (num > max)
                    max = num;
                if (num < min)
                    min = num;
            }
            return (max - min) > 0 ? (max - min) : 0;
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
    }
    
    static class ViewHolderHZKD extends ViewHolderKD
    {
        public ViewHolderHZKD(View view)
        {
            super(view);
        }
        
        public int getSum(List<Integer> integers)
        {
            int sum = 0;
            for (int num : integers)
            {
                sum += num;
            }
            return sum;
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
    }
    
    static class ViewHolderHZKDDX extends ViewHolderHZKD
    {
        public ViewHolderHZKDDX(View view)
        {
            super(view);
        }
        
        public void setDX(List<Integer> integers, int position)
        {
            if (mLotteryType == 1 /*|| mLotteryType == 4*/)
                for (int num : integers)
                {
                    builders.get(position).append(num < 5 ? "小" : "大");
                }
            /*else
                for (int num : integers)
                {
                    builders.get(position).append(num < 6 ? "小" : "大");
                }*/
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
    }
    
    static class ViewHolderHZHWKD extends ViewHolderHZKD
    {
        public ViewHolderHZHWKD(View view)
        {
            super(view);
        }
        
        public int getHW(List<Integer> integers)
        {
            return getSum(integers) % 10;
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
    }
    
    static class ViewHolderQ2HZKDDX extends ViewHolderHZKDDX
    {
        public ViewHolderQ2HZKDDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            if (size > 4)
            {
                selectedInts.clear();
                selectedInts.add(integers.get(0));
                selectedInts.add(integers.get(1));
                builders.get(0).append(getSum(selectedInts));
                builders.get(1).append(getKD(selectedInts));
                setDX(selectedInts, 2);
            }
        }
    }
    
    static class ViewHolderH2HZKDDX extends ViewHolderHZKDDX
    {
        public ViewHolderH2HZKDDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            if (size > 4)
            {
                selectedInts.clear();
                selectedInts.add(integers.get(size - 2));
                selectedInts.add(integers.get(size - 1));
                builders.get(0).append(getSum(selectedInts));
                builders.get(1).append(getKD(selectedInts));
                setDX(selectedInts, 2);
            }
        }
    }
    
    static class ViewHolderQ3HZKDDX extends ViewHolderHZKDDX
    {
        public ViewHolderQ3HZKDDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderQ3HZZXDX extends ViewHolderQ3HZKDDX
    {
        public ViewHolderQ3HZZXDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 3; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(1, list));
        }
    }
    
    static class ViewHolderZ3HZZXDX extends ViewHolderZ3HZKDDX
    {
        public ViewHolderZ3HZZXDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < 4; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(1, list));
        }
    }
    
    static class ViewHolderH3HZZXDX extends ViewHolderH3HZKDDX
    {
        public ViewHolderH3HZZXDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 2; i < size; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(1, list));
        }
    }
    
    static class ViewHolderQ4ZX extends ViewHolderHZHWKD
    {
        public ViewHolderQ4ZX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderQ4HZZXKD extends ViewHolderQ4ZX
    {
        public ViewHolderQ4HZZXKD(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < 4; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(2, list));
        }
    }
    
    static class ViewHolderH4HZZXKD extends ViewHolderH4ZX
    {
        public ViewHolderH4HZZXKD(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(1).setLength(0);
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 1; i < 5; i++)
                list.add(integers.get(i));
            builders.get(1).append(getZuXuanResult(2, list));
        }
    }
    
    static class ViewHolderH4ZX extends ViewHolderHZHWKD
    {
        public ViewHolderH4ZX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderQ3BD extends ViewHolderHZHDXHDS
    {
        public ViewHolderQ3BD(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderZ3HZKDDX extends ViewHolderHZKDDX
    {
        public ViewHolderZ3HZKDDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderZ3BD extends ViewHolderHZHDXHDS
    {
        public ViewHolderZ3BD(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderH3HZKDDX extends ViewHolderHZKDDX
    {
        public ViewHolderH3HZKDDX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderH3BD extends ViewHolderHZHDXHDS
    {
        public ViewHolderH3BD(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolder5XHZHDXHW extends ViewHolderHZHWKD
    {
        public ViewHolder5XHZHDXHW(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            int sum = getSum(integers);
            String[] temp = String.valueOf(sum).split("");
            builders.get(0).append(sum);
            builders.get(1).append(sum % 2 != 0 ? "单" : "双");
            builders.get(2).append(temp[temp.length - 1]);
        }
    }
    
    static class ViewHolder5XHZDXDS extends ViewHolderHZHDXHDS
    {
        public ViewHolder5XHZDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 23));
            builders.get(2).append(getHDS(integers));
        }
    }
    
    static class ViewHolder3XHZDXDS extends ViewHolderHZHDXHDS
    {
        public ViewHolder3XHZDXDS(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 11));
            builders.get(2).append(getHDS(integers));
        }
    }
    
    static class ViewHolder5XHZKDZX extends ViewHolderHZKD
    {
        public ViewHolder5XHZKDZX(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getKD(integers));
            builders.get(2).append(getZuXuanResult(3, integers));
        }
    }
    
    static class ViewHolderQWWF extends ViewHolderHZKD
    {
        public ViewHolderQWWF(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getKD(integers));
            builders.get(2).append(getZuXuanResult(4, integers));
        }
    }
    
    static class ViewHolderLHH extends ViewHolder
    {
        public ViewHolderLHH(View view)
        {
            super(view);
            addData(10);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            if (size > 4)
            {
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
    
    static private String getLHH(int higher, int lower)
    {
        if (higher > lower)
            return "龙";
        else if (higher < lower)
            return "虎";
        else
            return "和";
    }
    
    static class ViewHolderNN extends ViewHolder
    {
        public ViewHolderNN(View view)
        {
            super(view);
            addData(2);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            int totalAmount = 0;
            String label = "";
            boolean isOther = true;
            for (int i = 0; i < size - 2; i++)
            {
                totalAmount += integers.get(i);
                for (int j = i + 1; j < size - 1; j++)
                {
                    for (int k = j + 1; k < size; k++)
                    {
                        int sum = integers.get(i) + integers.get(j) + integers.get(k);
                        if (sum % 10 == 0)
                        {
                            isOther = false;
                            int otherSum = 0;
                            for (int l = 0; l < size; l++)
                            {
                                if (l == i || l == j || l == k)
                                    continue;
                                otherSum += integers.get(l);
                            }
                            switch (otherSum % 10)
                            {
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
    
    static class ViewHolderDDS extends ViewHolder
    {
        public ViewHolderDDS(View view)
        {
            super(view);
            addData(1);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
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
    
    static class ViewHolderCZW extends ViewHolder
    {
        public ViewHolderCZW(View view)
        {
            super(view);
            addData(2);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            Collections.sort(integers);
            int num = integers.get(2);
            builders.get(0).append(num < 10 ? "0" + num : String.valueOf(num));
            builders.get(1).append(num % 2 != 0 ? "单" : "双");
        }
    }
    
    static class ViewHolderHZDXXT extends ViewHolderHZHDXHDS
    {
        public ViewHolderHZDXXT(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 11));
            builders.get(2).append(getZuXuanResult(5, integers));
        }
    }
    
    static class ViewHolderHZDXYS extends ViewHolderHZHDXHDS
    {
        public ViewHolderHZDXYS(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getHDX(integers, 11));
            int red = 0, black = 0;
            for (int i : integers)
            {
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
    
    static class ViewHolderLHC extends ViewHolder
    {
        protected int tm;
        
        public ViewHolderLHC(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            tm = integers.get(size - 1);
        }
    }
    
    static class ViewHolderSXSB extends ViewHolderLHC
    {
        public ViewHolderSXSB(View view)
        {
            super(view);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
        }
    }
    
    static class ViewHolderHZZX extends ViewHolderHZ
    {
        public ViewHolderHZZX(View view)
        {
            super(view);
            addData(2);
        }
        
        @Override
        public void sort(ArrayList<Integer> integers)
        {
            super.sort(integers);
            builders.get(0).append(getSum(integers));
            builders.get(1).append(getZuXuanResult(1, integers));
        }
    }
    
    static class ViewHolderAllDXDS extends ViewHolderDXDS
    {
        public ViewHolderAllDXDS(View view)
        {
            super(view);
        }
        
        @Override
        protected void sort2()
        {
            selectedInts.clear();
            selectedInts.addAll(integers);
            super.sort2();
        }
    }
    
    
    static String getZuXuanResult(int mode, ArrayList<Integer> list)
    {
        int size = list.size();
        ArrayList<Integer> doneList = new ArrayList<>();
        int match2 = 0;
        int match3 = 0;
        switch (mode)
        {
            case 1:
                if (size == 3)
                {
                    for (int i = 0; i < size - 1; i++)
                    {
                        for (int j = i + 1; j < size; j++)
                            if (list.get(i) == list.get(j))
                                match2++;
                    }
                    switch (match2)
                    {
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
                if (size == 4)
                {
                    for (int i : list)
                    {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "豹子";
                        if (NumbericUtils.getApperTimes(i, list) == 3)
                            return "组选4";
                        if (NumbericUtils.getApperTimes(i, list) == 2)
                        {
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
                if (size == 5)
                {
                    for (int i : list)
                    {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 5)
                            return "豹子";
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "组选5";
                        if (NumbericUtils.getApperTimes(i, list) == 3)
                        {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2)
                        {
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
                if (size == 5)
                {
                    for (int i : list)
                    {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 4)
                            return "四季发财";
                        if (NumbericUtils.getApperTimes(i, list) == 3)
                        {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2)
                        {
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
                if (size == 3)
                {
                    for (int i : list)
                    {
                        if (doneList.contains(i))
                            continue;
                        if (NumbericUtils.getApperTimes(i, list) == 3)
                        {
                            match3++;
                            doneList.add(i);
                        }
                        if (NumbericUtils.getApperTimes(i, list) == 2)
                        {
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
