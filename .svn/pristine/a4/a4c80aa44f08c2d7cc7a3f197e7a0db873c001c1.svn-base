package com.goldenasia.lottery.game;

import android.util.SparseArray;

import com.goldenasia.lottery.material.Lunar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZodiacSigns {

    private SparseArray<ArrayList<String>> animalZodiacNo;
    private SparseArray<ArrayList<Integer>> animalZodiacSet;
    /**
     * 生肖数组
     */
    private static SparseArray<String> sxListStr = new SparseArray() {{
        put(0, "鼠");
        put(1, "牛");
        put(2, "虎");
        put(3, "兔");
        put(4, "龙");
        put(5, "蛇");
        put(6, "马");
        put(7, "羊");
        put(8, "猴");
        put(9, "鸡");
        put(10, "狗");
        put(11, "猪");
    }};

    private static List<Integer> poultryList = Arrays.asList(1, 6, 7, 9, 10, 11);

    private static List<Integer> beastList = Arrays.asList(0, 2, 3, 4, 5, 8);

    private ZodiacSigns() {
        animalZodiacNo = new SparseArray<>();
        animalZodiacSet = new SparseArray<>();
        int index = sxListStr.indexOfValue(Lunar.getInstance().animalsYear());
        cypher(index);
    }

    public static ZodiacSigns from() {
        return new ZodiacSigns();
    }

    private void cypher(int index) {
        if (index != -1) {
            int nm = 1, n = index; //nm 号码 n 生肖下标、1-49个号码按当年的农历生肖位进行分配
            do {//生肖
                List<String> noList = animalZodiacNo.get(n);
                if (noList == null) {
                    ArrayList<String> zodiacNo = new ArrayList<>();
                    ArrayList<Integer> zodiacSet = new ArrayList<>();
                    zodiacNo.add(String.format("%02d", nm));
                    zodiacSet.add(nm);
                    animalZodiacNo.put(n, zodiacNo);
                    animalZodiacSet.put(n, zodiacSet);
                } else {
                    animalZodiacNo.get(n).add(String.format("%02d", nm));
                    animalZodiacSet.get(n).add(nm);
                }
                if (n == 0) {
                    n = sxListStr.size() - 1;
                } else {
                    n--;
                }
                nm++;
            } while (nm < 50);
        }
    }

    public SparseArray<ArrayList<String>> getAnimalZodiacNo() {
        return animalZodiacNo;
    }

    public SparseArray<ArrayList<Integer>> getAnimalZodiacSet() {
        return animalZodiacSet;
    }

    public String zodiacName(int key) {
        if (key < 0 && key > sxListStr.size()) {
            return "";
        }
        return sxListStr.valueAt(key);
    }


    public List<ArrayList<Integer>> getAnimalPoultryList() {
        List<ArrayList<Integer>> poultry = new ArrayList<>();
        for (Integer p : poultryList) {
            poultry.add(animalZodiacSet.get(p));
        }
        return poultry;
    }

    public List<ArrayList<Integer>> getAnimalBeastList() {
        List<ArrayList<Integer>> beast = new ArrayList<>();
        for (Integer b : beastList) {
            beast.add(animalZodiacSet.get(b));
        }
        return beast;
    }
}
