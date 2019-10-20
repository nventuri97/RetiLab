package com.company;

import java.io.File;

public class Consumer implements Runnable {
    private SharedStructure sh;

    public Consumer(SharedStructure sh){
        this.sh=sh;
    }

    public void run(){
        try {
            String path = sh.get();
            File dir=new File(path);
            if(dir.isDirectory()){
                String[] files=dir.list();
                for(String file: files){
                    System.out.println(file);
                }
            }
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
