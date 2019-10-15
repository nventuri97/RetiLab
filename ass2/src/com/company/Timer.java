package com.company;

public class Timer implements Runnable {
    long time;

    public Timer(long value){
        this.time=value;
    }

    public void run(){
        try{
            Thread.sleep(this.time);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
