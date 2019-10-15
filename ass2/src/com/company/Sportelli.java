package com.company;

import java.util.concurrent.*;

public class Sportelli {
    int k=10;
    ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue(k);
    ThreadPoolExecutor executor;

    public Sportelli(){
        this.executor= new ThreadPoolExecutor(4, 4, 60, TimeUnit.SECONDS, queue);
    }

    public void executeTask(Task task){
        executor.execute(task);
    }

    public void atClose(){
        executor.shutdown();
    }

    public int queueSize(){
        return this.queue.size();
    }
}
