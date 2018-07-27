package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.data.Lottery;

/**
 * Created by Gan on 2017/10/6.
 * 玩法说明
 */

public class GameMethodInfoFragment extends BaseFragment {

    TextView  tv_taiwan;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Lottery lottery = (Lottery) getArguments().getSerializable("lottery");
        View view=inflater.inflate(R.layout.fragment_game_method_info_shishicai, container, false);

        view = selectLayoutInflator(inflater, container, lottery, view);

        tv_taiwan= (TextView) view.findViewById(R.id.tv_taiwan);

        if(lottery.getLotteryId()==35) {//台湾五分彩
            tv_taiwan.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private View selectLayoutInflator(LayoutInflater inflater, @Nullable ViewGroup container, Lottery lottery, View view) {
        switch (lottery.getLotteryId()) {
            case 2://山东11选5
            case 6://江西11选5
            case 7://广东11选5
            case 16://11选5分分彩
            case 20://北京11选5
            case 21://上海11选5
            case 32:
            case 33:
            case 34:
            case 36://山西11选5
            case 44://11选5秒秒彩
                view=inflater.inflate(R.layout.fragment_game_method_info_11select5, container, false);  //11选5 玩法说明
                break;
            case 1://重庆时时彩
            case 4://新疆时时彩
            case 8://天津时时彩
            case 11://亚洲分分彩
            case 15://亚洲秒秒彩
            case 19://亚洲5分彩
            case 35://台湾五分彩
            case 37:
                view=inflater.inflate(R.layout.fragment_game_method_info_shishicai, container, false);//时时彩 玩法说明
                break;
            case 24://超快3D
                view=inflater.inflate(R.layout.fragment_game_method_info_chaokuai3d, container, false);
                break;
            case 12://江苏快三
            case 13://快三分分彩
            case 22://安徽快三
            case 23://湖北快三
            case 41://河北快三
            case 42://河南快三
            case 43://福建快三
            case 45://快三秒秒彩
                view=inflater.inflate(R.layout.fragment_game_method_info_kuaisan, container, false);//快三 玩法说明
                break;
            case 9://福彩3D
                view=inflater.inflate(R.layout.fragment_game_method_info_fucai3d, container, false);//福彩3D 玩法说明
                break;
            case 10://P3p5
                view=inflater.inflate(R.layout.fragment_game_method_info_p3p5, container, false);//P3P5 玩法说明
                break;
            case 17://六合彩
            case 26://六合彩分分彩
                view=inflater.inflate(R.layout.fragment_game_method_info_lhc, container, false);//六合彩 玩法说明
                break;
            case 27://北京PK10
            case 38://PK10分分彩
            case 47://PK10二分彩
                view=inflater.inflate(R.layout.fragment_game_method_info_pk10, container, false);//北京PK10 玩法说明
                break;
            case 14://山东快乐扑克
                view=inflater.inflate(R.layout.fragment_game_method_info_kuailepuke, container, false);//山东快乐扑克 玩法说明
                break;
            case 48:
                view=inflater.inflate(R.layout.fragment_game_method_info_kl8, container, false);
                break;
            case 49:
                view=inflater.inflate(R.layout.fragment_game_method_info_tencent_ffc, container, false);
                break;
            default:
                break;
        }
        return view;
    }

}
