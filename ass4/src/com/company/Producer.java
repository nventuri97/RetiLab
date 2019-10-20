package com.company;

import java.io.File;

public class Producer implements Runnable {
    private String main_dir;
    private SharedStructure sh;

    public Producer(String dir, SharedStructure sh){
        this.main_dir=dir;
        this.sh=sh;
    }

    public void run(){
        recursiveVisit(main_dir);
    }

    public void recursiveVisit(String d){
        File dir=new File(d);
        if(dir.isDirectory()){
            try {
                sh.put(d);
                String[] list=dir.list();
                for(String file: list){
                    recursiveVisit(file);
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
