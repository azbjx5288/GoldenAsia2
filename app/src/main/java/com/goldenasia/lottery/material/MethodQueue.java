package com.goldenasia.lottery.material;

import com.goldenasia.lottery.data.Method;

/**
 * Created by Sakura on 2018/1/3.
 */

public class MethodQueue extends LimitedQueue<Method>
{
    public MethodQueue(int limit)
    {
        super(limit);
    }
    
    @Override
    public void offer(Method method)
    {
        for (Object object : queue)
        {
            if ((method).compareTo(object) == 0)
            {
                queue.remove(object);
                queue.offer(method);
                return;
            }
        }
        super.offer(method);
    }
}
