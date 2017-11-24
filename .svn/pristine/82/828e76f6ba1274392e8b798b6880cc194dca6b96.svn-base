package com.goldenasia.lottery.material;

/**
 * 投注模式：元、角、分
 * Created by Alashi on 2016/4/7.
 */
public enum LucreMode {
    /**
     * "元"模式
     */
    YUAN(0, "元", "1", 1),
    /**
     * "角"模式
     */
    JIAO(1, "角", "0.1", 0.10),
    /**
     * "分"模式
     */
    FEN(2, "分", "0.01", 0.01),
    /**
     * "厘"模式
     */
    LI(3, "厘", "0.001", 0.001);

    private int index;
    private String name;
    private String modes;
    private double factor;

    LucreMode(int index, String name, String modes, double factor) {
        this.index = index;
        this.name = name;
        this.modes = modes;
        this.factor = factor;
    }

    public double getFactor() {
        return factor;
    }

    public String getName() {
        return name;
    }

    public String getModes() {
        return modes;
    }

    public static LucreMode fromCode(int index) {
        for (LucreMode lucreMode : LucreMode.values()) {
            if (lucreMode.index == index) {
                return lucreMode;
            }
        }

        return YUAN;
    }
}
