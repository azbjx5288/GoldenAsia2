package com.goldenasia.lottery.data;

import java.util.ArrayList;

/**
 * Created by Sakura on 2016/10/10.
 */

public class GgcMyCardList
{
    private int[] pages;
    private int itemCount;
    private int pageCount;
    private int pageSize;

    private ArrayList<GgcCardEntity> cards;

    public ArrayList<GgcCardEntity> getCards()
    {
        return cards;
    }

    public void setCards(ArrayList<GgcCardEntity> cards)
    {
        this.cards = cards;
    }

    public int getItemCount()
    {
        return itemCount;
    }

    public void setItemCount(int itemCount)
    {
        this.itemCount = itemCount;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public int[] getPages()
    {
        return pages;
    }

    public void setPages(int[] pages)
    {
        this.pages = pages;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
}
