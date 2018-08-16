package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ACE-PC on 2016/5/4.
 */
public class Options {
    /**
     * rebate : 0.120
     * prize : 1940
     */

    @SerializedName("rebate")
    private double rebate;//·µµã
    @SerializedName("prize")
    private double prize;//½±½ð×é

    public void setRebate(double rebate) {
        this.rebate = rebate;
    }

    public void setPrize(double prize) {
        this.prize = prize;
    }

    public double getRebate() {
        return rebate;
    }

    public double getPrize() {
        return prize;
    }
}
