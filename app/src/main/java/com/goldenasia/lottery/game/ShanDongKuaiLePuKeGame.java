package com.goldenasia.lottery.game;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.pattern.LhcLayout;
import com.goldenasia.lottery.pattern.PickNumber;
import com.google.gson.JsonArray;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Gan on 2018/3/5.
 * 山东快乐扑克
 */

public class ShanDongKuaiLePuKeGame extends Game {

    private String TAG=ShanDongKuaiLePuKeGame.class.getName();

    @BindView(R.id.image_01_01)
    ImageView image_01_01;


    private int  mPickCount=0;

    public ShanDongKuaiLePuKeGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {

//        try {
//            java.lang.reflect.Method function = getClass().getMethod(method.getName(), Game.class);
//            function.invoke(null, this);
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("SscCommonGame", "onInflate: " + "//" + method.getCname() + " " + method.getName() + " public static " +
//                    "" + "void " + method.getName() + "(Game game) {}");
//            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
//        }

//        LayoutInflater.from(topLayout.getContext()).inflate(R.layout.pick_shandongkuailepuke_baoxuan, , true);
//        ButterKnife.bind(this, topLayout);

//        image_01.setSelected(true);
//

        if("PKBX".equals(method.getName())){
            PKBX(this);
        }
    }

    @Override
    public String getWebViewCode()
    {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mPickCount; i++)
        {
            stringBuilder.append(i);
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());
        return jsonArray.toString();
    }


    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        if (isDigital)
            builder.append(transformDigit(digits));
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), true, false));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }


    private static void addTopLayout(Game game, View view) {
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
    }

    //包选
    public static void PKBX(Game game) {

        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_shandongkuailepuke_baoxuan, null, false);
        ButterKnife.bind(game, view);

        addTopLayout(game, view);
//        LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column2, null, false);

//        return LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.popup_write_comment, null, false));;
//
//
//        LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_cow_cow, game.getTopLayout(), true);
//        ButterKnife.bind(this, game.getTopLayout());
//        ButterKnife.bind(this, topLayout);
    }

/*onc
    @OnClick({R.id.image_01_01, R.id.layout_blue, R.id.layout_green})
    public void OnClick(LhcLayout view)
    {
        if (view.isSelected())
        {
            view.setSelected(false);
            pickColorList.remove(view.getColor());
        } else
        {
            view.setSelected(true);
            pickColorList.add(view.getColor());
        }

        notifyListener();
    }*/

    @OnClick({R.id.image_01_01, R.id.image_01_02, R.id.image_01_03, R.id.image_01_04, R.id.image_01_05//包选界面

    })
    public void onLayoutClick(ImageView view)
    {
        if (view.isSelected())
        {
            view.setSelected(false);
            mPickCount--;
        } else
        {
            view.setSelected(true);
            mPickCount++;
        }

        notifyListener();
    }

    @Override
    public void onClearPick(Game game)
    {
        reset();
    }


    /*====================================具体玩法添加开始===========================================================================*/
    //同花  PKTH
    public static void PKTH(Game game) {

    }


    //顺子 PKSZ

    public static void PKSZ(Game game) {

    }

    //同花顺 PKTHS
    public static void PKTHS(Game game) {

    }

    //豹子         PKBZ
    public static void PKBZ(Game game) {

    }
    //对子 PKDZ

    public static void PKDZ(Game game) {

    }

    //任选一 PKRX1
    public static void PKRX1(Game game) {

    }

    //任选二  PKRX2
    public static void PKRX2(Game game) {

    }

    //任选三 PKRX3
    public static void PKRX3(Game game) {

    }

    //任选四  PKRX4
    public static void PKRX4(Game game) {

    }

    //任选五 PKRX5
    public static void PKRX5(Game game) {

    }

    //任选六  PKRX6
    public static void PKRX6(Game game) {

    }
    /*====================================具体玩法添加结束===========================================================================*/
}
