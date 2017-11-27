package com.goldenasia.lottery.game;

import android.app.Activity;

import com.goldenasia.lottery.data.Lottery;
import com.goldenasia.lottery.data.Method;

/**
 * 配置不同玩法对应的处理类
 * Created by Alashi on 2016/2/16.
 */
public class GameConfig
{
    public static Game createGame(Activity activity, Method method, Lottery lottery)
    {
        //Log.d("Config", "createGame: " + GsonHelper.toJson(method));
        String name = method.getName();
        switch (name)
        {
            case "RELHH":
                return new TextGame(method);

            case "LMGYH":
            case "LMMC":
            case "LMLH":
            case "WXHZDXDS":
            case "JSETDX":
            case "JSETFX":
            case "JSSTDX":
            case "JSPK":
            
            case "EXDXDS":
            case "SXDXDS":
            case "QEDXDS":
            case "QSDXDS":
            case "ZSDXDS":
            case "JSDX": //大小
            case "JSDS": //单双
            case "JSYS": //颜色
                return new TextMultipleGame(method);
            
            case "EXBD":
            case "EXHZ":
            case "QEBD":
            case "QEHZ":
            case "QSBD":
            case "QSHZ":
            case "ZSBD":
            case "ZSHZ":
            case "SXBD":
            case "SXHZ":
            case "SXZXHZ":
            case "QSZXHZ":
            case "JSHZ":
            case "WXHZ":
                return new SpecialGame(method);
            
            case "JSSTTX":
            case "JSSLTX":
                return new TxGame(method);
            case "QSHHZX":
            case "SXHHZX":
            case "ZSHHZX":
                return new DsGame(activity, method, lottery);
            case "RSHHZX":
                return new DsRSHHZXGame(activity, method, lottery);

            case "NIUNIU":
                return new CowCowGame(method);
        }
        
        //山东11选5，除“定单双, SDDDS”
        switch (method.getLotteryId())
        {
            case 2://山东11选5
            case 6://江西11选5
            case 7://广东11选5
            case 16://11选5分分彩
            case 20://北京11选5
            case 21://上海11选5
            case 32://江苏11选5
            case 33://浙江11选5
            case 34://福建11选5
            case 36://山西11选5
                if (name.equals("SDDDS"))
                {
                    return new SdddsGame(method);//无手工录入
                } else
                {
                    return new SyxwCommonGame(method);
                }
            case 1://重庆时时彩
            case 3://黑龙江时时彩
            case 4://新疆时时彩
            case 8://天津时时彩
            case 11://亚洲分分彩
            case 19://亚洲5分彩
            case 24://超快3D
            case 35://台湾五分彩
            case 37://亚洲2分彩
                return new SscCommonGame(method);
            case 12:
            case 13:
            case 22:
            case 23:
                return new KsCommonGame(method);
            case 9://福彩3D
                return new Fc3dCommonGame(method);
            case 10://P3p5
                return new P3p5CommonGame(method);
            case 15://亚洲秒秒彩
                return new MmcCommonGame(method);
            case 27://北京赛车PK10
            case 38://PK10分分彩
                return new Pk10CommonGame(method);
            default:
                break;
        }
        return new NonsupportGame(method);
    }
    
    public static LhcGame createLhcGame(Method method)
    {
        String name = method.getName();
        switch (name)
        {
            //六合彩直选玩法
            case "TMZX":
            case "ZTYM":
            case "ZMZX1":
            case "ZMZX2":
            case "ZMZX3":
            case "ZMZX4":
            case "ZMZX5":
            case "ZMZX6":
                return new LhcZxGame(method);
            case "TMDXDS":
            case "ZTHZDXDS":
                return new LhcTextGame(method);
            //六合彩任选玩法
            case "SSLM":
            case "EELM":
            case "SELM":
            case "SIELM":
            case "SISLM":
            case "SISILM":
            case "ZTBZ5":
            case "ZTBZ6":
            case "ZTBZ7":
            case "ZTBZ8":
            case "ZTBZ9":
            case "ZTBZ10":
                return new LhcRxGame(method);
            //六合彩生肖玩法
            case "TMSX":
            case "ZTYX":
            case "ERLX":
            case "SNLX":
            case "SILX":
                return new LhcSxGame(method);
            //六合彩尾数玩法
            case "TMWS":
            case "ZTWS":
                return new LhcTailGame(method);
            //六合彩色波玩法
            case "TMSB":
                return new LhcColorGame(method);
            //正码任选
            case "ZMRX":
                return new LhcZmrxGame(method);
            //总肖
            case "ZONGX":
                return new LhcZongXGame(method);
        }
        return new NonsupportLhcGame(method);
    }
    
    public static final int DS_TYPE_SSC = 1;
    public static final int DS_TYPE_SYXW = 2;
    
    /**
     * 特定彩种，单选玩法的数字类型
     */
    public static int getDsType(Lottery lottery)
    {
        switch (lottery.getLotteryType())
        {
            case 2://11选5
                return DS_TYPE_SYXW;
            case 1://时时彩
            case 4://3D
                return DS_TYPE_SSC;
            default:
                return -1;
        }
    }
}
