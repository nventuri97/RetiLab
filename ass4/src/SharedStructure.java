import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedStructure {
    private LinkedList<String> list;
    private ReentrantLock block;
    private Condition access;
    private boolean finish;

    public SharedStructure(){
        this.list=new LinkedList<>();
        this.block=new ReentrantLock();
        this.access=this.block.newCondition();
        this.finish=false;
    }

    public void put(String s) throws InterruptedException{
        //Lavoro in mutua esclusione ma non ho bisogno di condition variables visto che ho un solo produttore
        block.lock();

        list.add(s);

        if(block.hasWaiters(access))
            access.signalAll();
        block.unlock();
    }

    public String get() throws InterruptedException{
        String path;
        block.lock();

        while(block.hasWaiters(access ) || list.isEmpty())
            access.await();

        path=list.poll();
        access.signalAll();
        block.unlock();
        return path;
    }

    public boolean emptyQueue(){
        return list.isEmpty();
    }

    public void setFinish(){
        block.lock();
        this.finish=true;
        block.unlock();
    }

    public boolean isFinish(){
        return finish;
    }
}
