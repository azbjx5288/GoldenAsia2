package com.goldenasia.lottery.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Alashi on 2016/1/22.
 */
public class BetListResponse {
    @SerializedName("packages")
    private List<Bet> bets;
    private int packagesNumber;

    public List<Bet> getBets() {
        return bets;
    }

    public int getPackagesNumber() {
        return packagesNumber;
    }
}
