package com.goldenasia.lottery.data;

import java.util.ArrayList;

/**
 * Created by Sakura on 2016/10/10.
 */

public class GgcPlay2
{
    private ArrayList<Integer> open_numbers;
    private ArrayList<GgcMoneyNumberEntity> my_numbers;

    public ArrayList<GgcMoneyNumberEntity> getMy_numbers()
    {
        return my_numbers;
    }

    public void setMy_numbers(ArrayList<GgcMoneyNumberEntity> my_numbers)
    {
        this.my_numbers = my_numbers;
    }

    public ArrayList<Integer> getOpen_numbers()
    {
        return open_numbers;
    }

    public void setOpen_numbers(ArrayList<Integer> open_numbers)
    {
        this.open_numbers = open_numbers;
    }
}
