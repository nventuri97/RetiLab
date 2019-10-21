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
        File dir=new File(main_dir);
        recursiveVisit(dir);
    }

    public void recursiveVisit(File dir){
        if(dir.isDirectory()){
            try {
                sh.put(dir);
                File[] list=dir.listFiles();
                for(File file: list){
                    if(file!=null)
                        recursiveVisit(file);
                }
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
