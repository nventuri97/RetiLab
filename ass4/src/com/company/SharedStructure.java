package com.company;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedStructure {
    private LinkedList<String> list;
    private ReentrantLock block;
    private Condition access;

    public SharedStructure(){
        this.list=new LinkedList<>();
        this.block=new ReentrantLock();
        this.access=this.block.newCondition();
    }

    public void put(String s) throws InterruptedException{
        block.lock();
        while(block.hasWaiters(access))
            access.wait();

        list.add(s);
        access.signalAll();
        block.unlock();
    }

    public String get(int index) throws InterruptedException{
        String path;
        block.lock();
        while(block.hasWaiters(access))
            access.wait();

        path=list.get(index);
        return path;
    }
}
