package com.goldenasia.lottery.game;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.Method;
import com.goldenasia.lottery.material.Calculation;
import com.goldenasia.lottery.pattern.ManualInputView;
import com.goldenasia.lottery.pattern.OnAddListner;
import com.goldenasia.lottery.pattern.PickNumber;
import com.goldenasia.lottery.view.NumberGroupView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 11选5时，一般性的玩法，均由这个类处理，除“定单双, SDDDS”
 */
public class SyxwCommonGame extends Game {
    private static final String TAG = SyxwCommonGame.class.getSimpleName();

    public SyxwCommonGame(Method method) {
        super(method);
    }

    @Override
    public void onInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName(), Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    private static View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.pick_column2, null, false);
    }

    private static void createPicklayout(Game game, String[] name) {
        View[] views = new View[name.length];
        for (int i = 0; i < name.length; i++) {
            View view = createDefaultPickLayout(game.getTopLayout());
            game.addPickNumber(new PickNumber(view, name[i]));
            views[i] = view;
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View view : views) {
            topLayout.addView(view);
        }
        game.setColumn(name.length);
    }

    @Override
    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, size = pickNumbers.size(); i < size; i++) {
            builder.append(transform(pickNumbers.get(i).getCheckedNumber(), false, true));
            if (i != size - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    @Override
    public void onRandomCodes() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Random", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInputInflate() {
        try {
            java.lang.reflect.Method function = getClass().getMethod(method.getName() + "Input", Game.class);
            function.invoke(null, this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    public void displayInputView() {
        if (onManualEntryListener != null || manualInputView != null) {
            manualInputView.setOnAddListner(new OnAddListner() {
                @Override
                public void onAdd(ArrayList<String[]> chooseArray) {
                    if (getColumn() != 1) {
                        ArrayList<String> codeArray = new ArrayList<>();
                        for (int i =0,size= chooseArray.size(); i<size; i++) {
                            StringBuilder codeBuilder = new StringBuilder();
                            for (int j = 0, length = chooseArray.get(i).length; j < length; j++) {
                                codeBuilder.append(chooseArray.get(i)[j]);
                                if (j != length - 1) {
                                    codeBuilder.append(",");
                                }
                            }
                            codeArray.add(codeBuilder.toString());
                        }
                        setSubmitArray(codeArray);
                        setSingleNum(chooseArray.size());
                        onManualEntryListener.onManualEntry(chooseArray.size());
                    } else {
                        ArrayList<String> codeArray = new ArrayList<>();
                        int note =0;
                        for (int i =0,size= chooseArray.size(); i<size; i++) {
                            StringBuilder codeBuilder = new StringBuilder();
                            for (int j = 0, length = chooseArray.get(i).length; j < length; j++) {
                                codeBuilder.append(chooseArray.get(i)[j]);
                                if (j != length - 1) {
                                    codeBuilder.append("_");
                                }
                            }
                            codeArray.add(codeBuilder.toString());
                            note += Calculation.getInstance().calculationNote(null, null, null, Arrays.asList(chooseArray.get(i)), getMethod());
                        }

                        if (note > 0) {
                            setSubmitArray(codeArray);
                            setSingleNum(note);
                        }
                        onManualEntryListener.onManualEntry(note);
                    }
                }
            });
        }
    }

    public static View createManualInputLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.popup_write_comment, null, false);
    }

    public static void addInputLayout(Game game, int column) {
        ViewGroup manualInput = game.getManualInput();
        View view = createManualInputLayout(manualInput);
        ManualInputView manualInputView = new ManualInputView(view, game.lottery, column);
        game.addManualInputView(manualInputView);
        manualInput.addView(view);
    }

    private static void createDantuoLayout(Game game, int danSize, int tuoSize) {
        View[] views = new View[2];
        PickNumber danNum;
        NumberGroupView danNumView;
        PickNumber tuoNum;
        NumberGroupView tuoNumView;

        View view0 = createDefaultPickLayout(game.getTopLayout());
        danNum = new PickNumber(view0, "胆码", danSize);
        danNum.setColumnAreaHideOrShow(false);
        game.addPickNumber(danNum);
        danNumView = danNum.getNumberGroupView();
        views[0] = view0;

        View view1 = createDefaultPickLayout(game.getTopLayout());
        tuoNum = new PickNumber(view1, "拖码", tuoSize);
        tuoNum.setColumnAreaHideOrShow(false);
        game.addPickNumber(tuoNum);
        tuoNumView = tuoNum.getNumberGroupView();
        views[1] = view1;

        danNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick() {
                int lastPick = danNumView.getLastPick();
                int size = danNumView.getPickList().size();
                if (tuoNumView.getPickList().contains(lastPick) && danNumView.getCheckedArray().get(lastPick)) {
                    tuoNumView.getCheckedArray().put(lastPick, false);
                    tuoNumView.getPickList().remove(Integer.valueOf(lastPick));
                    tuoNumView.invalidate();
                }
                if (size > danSize) {
                    danNumView.getCheckedArray().put(danNumView.getPickList().get(size - 2), false);
                    danNumView.getPickList().remove(size - 2);
                }
                game.notifyListener();
            }
        });

        tuoNum.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
            @Override
            public void onChooseItemClick() {
                int lastPick = tuoNumView.getLastPick();
                if (danNumView.getPickList().contains(lastPick) && tuoNumView.getCheckedArray().get(lastPick)) {
                    danNumView.getCheckedArray().put(lastPick, false);
                    danNumView.getPickList().remove(Integer.valueOf(lastPick));
                    danNumView.invalidate();
                }
                game.notifyListener();
            }
        });

        ViewGroup topLayout = game.getTopLayout();
        for (View v : views) {
            topLayout.addView(v);
        }
    }

    private static int isContainsLastPick(List<NumberGroupView> numViewList,int lastPick,int  currentPosition){
        for(int i=0;i<numViewList.size();i++){
            NumberGroupView numView=numViewList.get(i);
            if(numView==null){
                continue;
            }
            if(currentPosition==i){
                continue;
            }
            if(numView.getPickList().contains(lastPick)){
                return  i;
            }
        }
        return  -1;
    }

    private static void createLexuanLayout(Game game, int danSize,String[] name) {
        View[] views = new View[name.length];
        List<NumberGroupView> numViewList=new ArrayList<NumberGroupView>();
        for(int  i=0;i<name.length;i++){
            View view = createDefaultPickLayout(game.getTopLayout());
            PickNumber num= new PickNumber(view, name[i], danSize);
            num.setColumnAreaHideOrShow(false);
            game.addPickNumber(num);

            NumberGroupView numView= num.getNumberGroupView();

            numViewList.add(numView);
            views[i] = view;

            final int postion=i;
            num.setChooseItemClickListener(new NumberGroupView.OnChooseItemClickListener() {
                @Override
                public void onChooseItemClick() {
                    int lastPick = numView.getLastPick();
                    int otherContainsPosition=isContainsLastPick(numViewList,lastPick,postion);
                    if (otherContainsPosition!=-1) {
                        NumberGroupView otherNumView=numViewList.get(otherContainsPosition);
                        otherNumView.getCheckedArray().put(lastPick, false);
                        otherNumView.getPickList().remove(Integer.valueOf(lastPick));
                        otherNumView.invalidate();
                    }
                    game.notifyListener();
                }
            });
        }

        ViewGroup topLayout = game.getTopLayout();
        for (View v : views) {
            topLayout.addView(v);
        }
    }
    //乐选二
    public static void SDLX2(Game game) {
        createLexuanLayout(game, 11, new String[]{"第一位", "第二位"});
    }
    //乐选三
    public static void SDLX3(Game game) {
        createLexuanLayout(game, 11, new String[]{"第一位", "第二位", "第三位"});
//        game.setSupportInput(true);
    }
    //乐选四
    public static void SDLX4(Game game) {
        createPicklayout(game, new String[]{"乐选四"});
//        game.setSupportInput(true);
    }
    //乐选五
    public static void SDLX5(Game game) {
        createPicklayout(game, new String[]{"乐选五"});
//        game.setSupportInput(true);
    }

    //前二组选胆拖
    public static void SDDTQEZUX(Game game) {
        createDantuoLayout(game, 1, 11);
    }

    //前三组选胆拖
    public static void SDDTQSZUX(Game game) {
        createDantuoLayout(game, 2, 11);
    }

    //任选二中二胆拖
    public static void SDDTRX2(Game game) {
        createDantuoLayout(game, 1, 11);
    }

    //任选三中三胆拖
    public static void SDDTRX3(Game game) {
        createDantuoLayout(game, 2, 11);
    }

    //任选四中四胆拖
    public static void SDDTRX4(Game game) {
        createDantuoLayout(game, 3, 11);
    }

    //任选五中五胆拖
    public static void SDDTRX5(Game game) {
        createDantuoLayout(game, 4, 11);
    }

    //任选六中五胆拖
    public static void SDDTRX6(Game game) {
        createDantuoLayout(game, 5, 11);
    }

    //任选七中五胆拖
    public static void SDDTRX7(Game game) {
        createDantuoLayout(game, 6, 11);
    }

    //任选八中五胆拖
    public static void SDDTRX8(Game game) {
        createDantuoLayout(game, 7, 11);
    }

    //前三直选
    public static void SDQSZX(Game game) {
        createPicklayout(game, new String[]{"第一位", "第二位", "第三位"});
        game.setSupportInput(true);
    }

    //前三组选
    public static void SDQSZUX(Game game) {
        createPicklayout(game, new String[]{"前三组"});
        game.setSupportInput(true);
    }

    //前二直选
    public static void SDQEZX(Game game) {
        createPicklayout(game, new String[]{"第一位", "第二位"});
        game.setSupportInput(true);
    }

    //前二组选
    public static void SDQEZUX(Game game) {
        createPicklayout(game, new String[]{"前二组"});
        game.setSupportInput(true);
    }

    public static void SDRX1(Game game) {
        createPicklayout(game, new String[]{"任选一"});
    }

    public static void SDRX2(Game game) {
        createPicklayout(game, new String[]{"任选二"});
        game.setSupportInput(true);
    }

    public static void SDRX3(Game game) {
        createPicklayout(game, new String[]{"任选三"});
        game.setSupportInput(true);
    }

    public static void SDRX4(Game game) {
        createPicklayout(game, new String[]{"任选四"});
        game.setSupportInput(true);
    }

    public static void SDRX5(Game game) {
        createPicklayout(game, new String[]{"任选五"});
        game.setSupportInput(true);
    }

    public static void SDRX6(Game game) {
        createPicklayout(game, new String[]{"任选六"});
        game.setSupportInput(true);
    }

    public static void SDRX7(Game game) {
        createPicklayout(game, new String[]{"任选七"});
        game.setSupportInput(true);
    }

    public static void SDRX8(Game game) {
        createPicklayout(game, new String[]{"任选八"});
        game.setSupportInput(true);
    }

    //前三不定位胆
    public static void SDQSBDW(Game game) {
        createPicklayout(game, new String[]{"前三位"});
    }

    //前三定位胆
    public static void SDQSDWD(Game game) {
        createPicklayout(game, new String[]{"第一位", "第二位", "第三位"});
    }

    //猜中位
    public static void SDCZW(Game game) {
        View view1 = createDefaultPickLayout(game.getTopLayout());
        PickNumber pickNumber2 = new PickNumber(view1, "猜中位");
        game.addPickNumber(pickNumber2);
        pickNumber2.getNumberGroupView().setNumber(3, 9);
        pickNumber2.setChooseMode(true);
        pickNumber2.setColumnAreaHideOrShow(false);

        game.getTopLayout().addView(view1);
    }
    //乐选二
    public static void SDLX2Input(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //乐选三
    public static void SDLX3Input(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //乐选四
    public static void SDLX4Input(Game game) {
        addInputLayout(game, game.getColumn());
    }
    //乐选五
    public static void SDLX5Input(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前三直选 SDQSZX
    public static void SDQSZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前三组选 SDQSZUX
    public static void SDQSZUXInput(Game game) {
        addInputLayout(game, 3);
    }

    //前二直选 SDQEZX
    public static void SDQEZXInput(Game game) {
        addInputLayout(game, game.getColumn());
    }

    //前二组选 SDQEZUX
    public static void SDQEZUXInput(Game game) {
        addInputLayout(game, 2);
    }

    //任选二中二 SDRX2
    public static void SDRX2Input(Game game) {
        addInputLayout(game, 2);
    }

    //任选三中三 SDRX3
    public static void SDRX3Input(Game game) {
        addInputLayout(game, 3);
    }

    //任选四中四 SDRX4
    public static void SDRX4Input(Game game) {
        addInputLayout(game, 4);
    }

    //任选五中五 SDRX5
    public static void SDRX5Input(Game game) {
        addInputLayout(game, 5);
    }

    //任选六中五 SDRX6
    public static void SDRX6Input(Game game) {
        addInputLayout(game, 6);
    }

    //任选七中五 SDRX7
    public static void SDRX7Input(Game game) {
        addInputLayout(game, 7);
    }

    //任选八中五 SDRX8
    public static void SDRX8Input(Game game) {
        addInputLayout(game, 8);
    }

    public static void YardsRandom(Game game, int yards, int single) {
        ArrayList<Integer> randomList = new ArrayList<>();
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 11, yards);
                pickNumber.onRandom(randomList);
            } else
                pickNumber.onRandom(randomCommon(1, 11, single, randomList));
        }
        game.notifyListener();
    }

    public static void SDDTQEZUXRandom(Game game) {
        YardsRandom(game, 1, 1);
    }

    public static void SDDTQSZUXRandom(Game game) {
        YardsRandom(game, 1, 2);
    }

    public static void SDDTRX2Random(Game game) {
        YardsRandom(game, 1, 1);
    }

    public static void SDDTRX3Random(Game game) {
        YardsRandom(game, 1, 2);
    }

    public static void SDDTRX4Random(Game game) {
        YardsRandom(game, 1, 3);
    }

    public static void SDDTRX5Random(Game game) {
        YardsRandom(game, 1, 4);
    }

    public static void SDDTRX6Random(Game game) {
        YardsRandom(game, 1, 5);
    }

    public static void SDDTRX7Random(Game game) {
        YardsRandom(game, 1, 6);
    }

    public static void SDDTRX8Random(Game game) {
        YardsRandom(game, 1, 7);
    }

    //前三直选随机 SDQSZX
    public static void SDQSZXRandom(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 11, 1);
                pickNumber.onRandom(randomList);
            } else {
                ArrayList<Integer> A = randomCommon(1, 11, 1, randomList);
                randomList.addAll(A);
                pickNumber.onRandom(A);
            }
        }
        game.notifyListener();
    }

    //前三组选随机 SDQSZUX
    public static void SDQSZUXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 3));
        game.notifyListener();
    }

    //前二组选随机 SDQEZUX
    public static void SDQEZUXRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 2));
        game.notifyListener();
    }

    //前二直选随机 SDQEZX
    public static void SDQEZXRandom(Game game) {
        ArrayList<Integer> randomList = null;
        for (int i = 0, size = game.pickNumbers.size(); i < size; i++) {
            PickNumber pickNumber = game.pickNumbers.get(i);
            if (i == 0) {
                randomList = random(1, 11, 1);
                pickNumber.onRandom(randomList);
            } else {
                ArrayList<Integer> A = randomCommon(1, 11, 1, randomList);
                randomList.addAll(A);
                pickNumber.onRandom(A);
            }
        }
        game.notifyListener();
    }

    //任选二中二随机 SDRX2
    public static void SDRX2Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 2));
        game.notifyListener();
    }

    //任选六中五随机 SDRX6
    public static void SDRX6Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 6));
        game.notifyListener();
    }

    //任选三中三随机 SDRX3
    public static void SDRX3Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 3));
        game.notifyListener();
    }

    //任选七中五随机 SDRX7
    public static void SDRX7Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 7));
        game.notifyListener();
    }

    //任选四中四随机 SDRX4
    public static void SDRX4Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 4));
        game.notifyListener();
    }

    //任选八中五随机 SDRX8
    public static void SDRX8Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 8));
        game.notifyListener();
    }

    //任选一中一随机 SDRX1
    public static void SDRX1Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 1));
        game.notifyListener();
    }

    //任选五中五随机 SDRX5
    public static void SDRX5Random(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 5));
        game.notifyListener();
    }

    //前三定位胆随机 SDQSDWD
    public static void SDQSDWDRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(1, 11, 1));
        game.notifyListener();
    }

    //定单双随机 SDDDS
    public static void SDDDSRandom(Game game) {

    }

    //猜中位随机 SDCZW
    public static void SDCZWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(random(3, 9, 1));
        game.notifyListener();
    }

    //前三不定位胆随机 SDQSBDW
    public static void SDQSBDWRandom(Game game) {
        for (PickNumber pickNumber : game.pickNumbers)
            pickNumber.onRandom(randomCommon(1, 11, 1, new ArrayList<>()));
        game.notifyListener();
    }
}
