package com.goldenasia.lottery.material;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Sakura on 2017/12/27.
 */

public class LimitedQueue<E> implements Serializable
{
    private int limit; // 队列长度
    
    protected LinkedList<E> queue = new LinkedList<>();
    
    public LimitedQueue(int limit)
    {
        this.limit = limit;
    }
    
    /**
     * 入列：当队列大小已满时，把队头的元素poll掉
     */
    public void offer(E e)
    {
        if (queue.size() >= limit)
        {
            queue.poll();
        }
        queue.offer(e);
    }
    
    public E get(int position)
    {
        return queue.get(position);
    }
    
    public E getLast()
    {
        return queue.getLast();
    }
    
    public E getFirst()
    {
        return queue.getFirst();
    }
    
    public int getLimit()
    {
        return limit;
    }
    
    public int size()
    {
        return queue.size();
    }
}
