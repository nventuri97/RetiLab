import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedStructure {
    private LinkedList<File> list;
    private ReentrantLock block;
    private Condition access;
    private boolean finish;

    public SharedStructure(){
        this.list=new LinkedList<>();
        this.block=new ReentrantLock();
        this.access=this.block.newCondition();
        this.finish=false;
    }

    public void put(File s) throws InterruptedException{
        //Lavoro in mutua esclusione ma non ho bisogno di condition variables visto che ho un solo produttore
        block.lock();
        list.add(s);
        access.signalAll();
        block.unlock();
    }

    public File get() throws InterruptedException{
        File path;
        block.lock();
        while(block.hasWaiters(access) || list.isEmpty())
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
        this.finish=true;
    }

    public boolean isFinish(){
        return finish;
    }
}
