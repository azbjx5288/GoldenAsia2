package com.goldenasia.lottery.data;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by Sakura on 2016/10/6.
 */

public class GgcBuyBean implements Serializable
{
    private LinkedHashMap<Integer, String> cards;
    private int length;

    public LinkedHashMap<Integer, String> getCards()
    {
        return cards;
    }

    public void setCards(LinkedHashMap<Integer, String> cards)
    {
        this.cards = cards;
    }

    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }
}
