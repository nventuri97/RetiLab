package com.company;

public class Task implements Runnable{
        private String name;

        public Task(String name){
            this.name=name;
        }

        public void run(){
            System.out.printf("%s starts\n", name);

            try{
                long time=(long) (Math.random()*10000);
                System.out.printf("%s: %s: Doing a task during %d milliseconds\n",  Thread.currentThread().getName(),name,time);
                Thread.sleep(time);
            }catch(Exception ex){
                ex.printStackTrace();
            }
            System.out.printf("%s is finished\n", name);
        }
}
