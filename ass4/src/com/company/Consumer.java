package com.company;

public class Consumer implements Runnable {

    private SharedStructure sh;

    public Consumer(SharedStructure sh){
        this.sh=sh;
    }

    public void run(){}
}
