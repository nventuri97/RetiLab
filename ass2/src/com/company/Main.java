package com.company;
import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        int n=(int) (Math.random()*10000)+1;
        LinkedBlockingQueue<Runnable> queue1 = new LinkedBlockingQueue();

        Sportelli sportelli=new Sportelli();
        Timer timer =new Timer(n);
        Thread t= new Thread(timer);
        t.start();

        int i=0;
        while (t.isAlive()) {
            try {
                Task task = new Task("Task " + (i + 1));
                i++;
                //Inserisco dentro la coda se non ho raggiunto la capacità massima;
                if (queue1.size() != 500)
                    queue1.put(task);
                if(sportelli.queueSize()<10){
                    Task client=(Task) queue1.take();
                    sportelli.executeTask(client);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sportelli.atClose();
        while(!sportelli.executor.isTerminated()){}
        System.out.println("Non ci sono più clienti");
    }
}
