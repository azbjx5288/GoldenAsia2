package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.data.Lottery;

/**
 * Created by Gan on 2018/6/27.
 * 单挑说明
 */

public class GameDantiaoInfoFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Lottery lottery = (Lottery) getArguments().getSerializable("lottery");

        return  selectLayoutInflator(inflater, container, lottery);
    }

    private View selectLayoutInflator(LayoutInflater inflater, @Nullable ViewGroup container, Lottery lottery) {
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
                return inflater.inflate(R.layout.fragment_game_dantiao_info_11select5, container, false);  //11选5 玩法说明
            case 1://重庆时时彩
            case 4://新疆时时彩
            case 8://天津时时彩
            case 11://亚洲分分彩
            case 15://亚洲秒秒彩
            case 19://亚洲5分彩
            case 35://台湾五分彩
            case 37:
            case 50://北京5分彩
                return inflater.inflate(R.layout.fragment_game_dantiao_info_shishicai, container, false);//时时彩 玩法说明
            case 24://超快3D
            case 9://福彩3D
                return inflater.inflate(R.layout.fragment_game_dantiao_info_3d, container, false);//福彩3D 玩法说明
            case 17://六合彩
            case 26://六合彩分分彩
                return inflater.inflate(R.layout.fragment_game_dantiao_info_liuhecai, container, false);//六合彩 玩法说明
            case 27://北京PK10
            case 38://PK10分分彩
            case 47://PK10二分彩
                return inflater.inflate(R.layout.fragment_game_dantiao_info_pk10, container, false);//北京PK10 玩法说明
            case 10://P3p5
                return inflater.inflate(R.layout.fragment_game_dantiao_info_p3p5, container, false);//北京PK10 玩法说明
            case 48://北京快乐8:
                return inflater.inflate(R.layout.fragment_game_dantiao_info_kuaile8, container, false);//北京PK10 玩法说明
            default:

        }
        return inflater.inflate(R.layout.fragment_game_dantiao_info_shishicai, container, false);//时时彩 玩法说明
    }

}
