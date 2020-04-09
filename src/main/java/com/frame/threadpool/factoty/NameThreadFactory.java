package com.frame.threadpool.factoty;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 〈一句话功能简述〉<br>
 * 〈线程工厂〉
 *
 * @author KOLO
 * @create 2020/4/9
 * @since 1.0.0
 */
public class NameThreadFactory implements ThreadFactory {

    private final String name;

    private final AtomicInteger threadIndex = new AtomicInteger();

    public NameThreadFactory(String name){
        this.name = name;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + "-" + threadIndex.incrementAndGet());
    }
}