package com.company;

import java.io.File;

public class Consumer implements Runnable {
    private SharedStructure sh;

    public Consumer(SharedStructure sh){
        this.sh=sh;
    }

    public void run(){
        while(!sh.emptyQueue()) {
            try {
                File dir = sh.get();
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    for (File file : files) {
                        if(file.isDirectory())
                            System.out.print("Directory: "+file+"\n");
                        else if(file.isFile())
                            System.out.print("File: "+file+"\n");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
