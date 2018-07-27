package com.goldenasia.lottery.game;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.google.gson.JsonArray;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gan on 2018/3/5.
 * 山东快乐扑克
 */

public class ShanDongKuaiLePuKeGame extends Game {

    private String TAG=ShanDongKuaiLePuKeGame.class.getName();

    private Map<String,Boolean> mPickMap=new HashMap<>();//当前选中的图片的中文名字集合

    private String[] mMethod01=new String[]{"同花包选","顺子包选","同花顺包选","豹子包选","对子包选"};//包选 PKBX
    private String[] mMethod02=new String[]{"黑桃","红桃","梅花","方块"};       //同花  PKTH
    private String[] mMethod03=new String[]{"A23","234","345","456","567","678" ,"789" ,"8910", "910J","10JQ","JQK","QKA"};//顺子 PKSZ
    private String[] mMethod04=new String[]{"黑桃顺子","红桃顺子","梅花顺子","方块顺子"};//同花顺 PKTHS
    private String[] mMethod05=new String[]{"AAA","222","333","444","555","666","777","888","999","101010","JJJ","QQQ","KKK"};//豹子 PKBZ
    private String[] mMethod06=new String[]{"AA","22","33","44","55","66","77","88","99","1010","JJ","QQ","KK"};//对子 PKDZ
    private String[] mMethod07=new String[]{"A","2","3","4","5","6","7","8","9","10","J","Q","K"};//任选

    public ShanDongKuaiLePuKeGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {

        //ButterKnife.bind(game, view); 和反射冲突 所以放弃用反射的方法
        /*try {
            java.lang.reflect.Method function = getClass().getDeclaredMethod(method.getName(), Game.class);
            function.setAccessible(true);//设置允许访问
            function.invoke(getClass(), this);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ShanDongKuaiLePuKeGame", "onInflate: " + "//" + method.getCname() + " " + method.getName() + " public static " +
                    "" + "void " + method.getName() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }*/
        if("PKBX".equals(method.getName())){       //包选 PKBX
            PKBX(this);
        }else if("PKTH".equals(method.getName())){ //同花  PKTH
            PKTH(this);
        }else if("PKSZ".equals(method.getName())){ //顺子 PKSZ
            PKSZ(this);
        }else if("PKTHS".equals(method.getName())){ //同花顺 PKTHS
            PKTHS(this);
        }else if("PKBZ".equals(method.getName())){ //豹子 PKBZ
            PKBZ(this);
        }else if("PKDZ".equals(method.getName())){ //对子 PKDZ
            PKDZ(this);
        }else if("PKRX1".equals(method.getName())){ //任选一 PKRX1
            PKRX1(this);
        }else if("PKRX2".equals(method.getName())){ //任选二 PKRX2
            PKRX2(this);
        }else if("PKRX3".equals(method.getName())){ //任选三 PKRX3
            PKRX3(this);
        }else if("PKRX4".equals(method.getName())){ //任选四 PKRX4
            PKRX4(this);
        }else if("PKRX5".equals(method.getName())){ //任选五 PKRX5
            PKRX5(this);
        }else if("PKRX6".equals(method.getName())){ //任选六 PKRX6
            PKRX6(this);
        }else{
            Log.e("ShanDongKuaiLePuKeGame", "onInflate: " + "//" + method.getCname() + " " + method.getName() + " public static " +
                    "" + "void " + method.getName() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public String getWebViewCode()
    {
        switch (method.getName()){
            case "PKRX1"://任选一 PKRX1
            case "PKRX2":// 任选二 PKRX2
            case "PKRX3"://任选三 PKRX3
            case "PKRX4"://任选四 PKRX4
            case "PKRX5": //任选五 PKRX5
            case "PKRX6"://任选六 PKRX6
                return getWebViewCodeRenXuan();
            default:
                StringBuilder stringBuilder = new StringBuilder();

                String[] methodStringArray = getGameMethodStringArray();

                for (int i = 0; i < methodStringArray.length; i++)
                {
                    if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                        stringBuilder.append("1");
                    }
                }
                JsonArray jsonArray = new JsonArray();
                jsonArray.add(stringBuilder.toString());
                return jsonArray.toString();
        }
    }

    private String getWebViewCodeRenXuan() {
        StringBuilder stringBuilder = new StringBuilder();

        String[] methodStringArray = getGameMethodStringArray();

        for (int i = 0; i < methodStringArray.length; i++)
        {
            if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                stringBuilder.append("1");
                stringBuilder.append("_");
            }
        }
        if(stringBuilder.length()>=2) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());

        return jsonArray.toString();
    }

    //确定按钮按下后 带到 购物车中的
    //对选中的扑克要进行排序
    @Override
    public String getSubmitCodes()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String[] methodStringArray = getGameMethodStringArray();
        for (int i = 0; i < methodStringArray.length; i++)
        {
            if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                stringBuilder.append(methodStringArray[i]);
                stringBuilder.append("_");
            }
        }
        if(stringBuilder.length()>=2) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    //根据不同玩法返回不同的String数组
    private String[] getGameMethodStringArray() {
        switch (method.getName()) {
            case "PKBX"://包选 PKBX
                return mMethod01;
            case "PKTH"://同花  PKTH
                return mMethod02;
            case "PKSZ"://顺子 PKSZ
                return mMethod03;
            case "PKTHS"://同花顺 PKTHS
                return mMethod04;
            case "PKBZ": //豹子 PKBZ
                return mMethod05;
            case "PKDZ"://对子 PKDZ
                return mMethod06;
            default:
                return mMethod07;
        }
    }

    @Override
    public void reset()
    {
        setAllImageState(false);
    }

    //填充游戏不同玩法的数据  imageCount代表该玩法有多少张图片
    private void setAllImageState(boolean state) {
        //不同玩法数据变化
        String[] methodStringArray = getGameMethodStringArray();
        //界面变化
        int[] imageRes=new int[]{R.id.image_01_01, R.id.image_01_02, R.id.image_01_03, R.id.image_01_04, R.id.image_01_05,
                R.id.image_01_06, R.id.image_01_07, R.id.image_01_08, R.id.image_01_09, R.id.image_01_10,
                R.id.image_01_11, R.id.image_01_12, R.id.image_01_13};
        for(int i=0;i<methodStringArray.length;i++){//最多玩法中有13个图片
            topLayout.findViewById(imageRes[i]).setSelected(state);
        }
        for (int i = 0; i < methodStringArray.length; i++)
        {
            mPickMap.put(methodStringArray[i],state);
        }

        notifyListener();
    }

    @OnClick({R.id.image_01_01, R.id.image_01_02, R.id.image_01_03, R.id.image_01_04, R.id.image_01_05,//包选界面
            R.id.image_01_06, R.id.image_01_07, R.id.image_01_08, R.id.image_01_09, R.id.image_01_10,//包选界面
            R.id.image_01_11, R.id.image_01_12, R.id.image_01_13,//包选界面  每个玩法一共 13张图片
            R.id.sdklpk_all, R.id.sdklpk_clear//全 清
    })
    public void onLayoutClick(View view)
    {

        switch (view.getId()){
            case R.id.sdklpk_all://全选
                setAllImageState(true);
                break;
            case R.id.sdklpk_clear://清除
                setAllImageState(false);
                break;
            default://点击图片
                if (view.isSelected())
                {
                    view.setSelected(false);
                    mPickMap.put(view.getTag().toString(),false);
                } else
                {
                    view.setSelected(true);
                    mPickMap.put(view.getTag().toString(),true);
                }
        }

        notifyListener();
    }

    /*====================================具体玩法添加开始===========================================================================*/
    private static void addTopLayout(Game game, View view) {
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
    }

    //包选
    public static void PKBX(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_baoxuan, null, false);
        ButterKnife.bind(game, view);
        addTopLayout(game, view);
    }

    //同花  PKTH
    public static void PKTH(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_tonghua, null, false);
        ButterKnife.bind(game, view);
        addTopLayout(game, view);
    }


    //顺子 PKSZ
    public static void PKSZ(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_shunzi, null, false);
        ButterKnife.bind(game, view);
        addTopLayout(game, view);
    }

    //同花顺 PKTHS
    public static void PKTHS(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_tonghuashun, null, false);
        ButterKnife.bind(game, view);
        addTopLayout(game, view);
    }

    //豹子 PKBZ
    public static void PKBZ(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_baozi, null, false);
        ButterKnife.bind(game, view);
        addTopLayout(game, view);
    }

    //对子 PKDZ
    public static void PKDZ(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_duizi, null, false);
        ButterKnife.bind(game, view);
        addTopLayout(game, view);
    }

    //任选一 PKRX1
    public static void PKRX1(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_renxuan, null, false);
        ButterKnife.bind(game, view);
        TextView   pickColumnTitleTextView=view.findViewById(R.id.pick_column_title);
        pickColumnTitleTextView.setText("任选一");
        addTopLayout(game, view);
    }

    //任选二  PKRX2
    public static void PKRX2(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_renxuan, null, false);
        ButterKnife.bind(game, view);
        TextView   pickColumnTitleTextView=view.findViewById(R.id.pick_column_title);
        pickColumnTitleTextView.setText("任选二");
        addTopLayout(game, view);
    }

    //任选三 PKRX3
    public static void PKRX3(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_renxuan, null, false);
        ButterKnife.bind(game, view);
        TextView   pickColumnTitleTextView=view.findViewById(R.id.pick_column_title);
        pickColumnTitleTextView.setText("任选三");
        addTopLayout(game, view);
    }

    //任选四  PKRX4
    public static void PKRX4(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_renxuan, null, false);
        ButterKnife.bind(game, view);
        TextView   pickColumnTitleTextView=view.findViewById(R.id.pick_column_title);
        pickColumnTitleTextView.setText("任选四");
        addTopLayout(game, view);
    }

    //任选五 PKRX5
    public static void PKRX5(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_renxuan, null, false);
        ButterKnife.bind(game, view);
        TextView   pickColumnTitleTextView=view.findViewById(R.id.pick_column_title);
        pickColumnTitleTextView.setText("任选五");
        addTopLayout(game, view);
    }

    //任选六  PKRX6
    public static void PKRX6(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_renxuan, null, false);
        ButterKnife.bind(game, view);
        TextView   pickColumnTitleTextView=view.findViewById(R.id.pick_column_title);
        pickColumnTitleTextView.setText("任选六");
        addTopLayout(game, view);
    }
    /*====================================具体玩法添加结束===========================================================================*/
}
