import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedVariables {
    //variabili condivise che andrò a modificare in mutua esclusione
    private int bonifico, accredito, bollettino, f24, pagobancomat;

    //Lock e variabili di condizione per ogni variabile condivisa
    private ReentrantLock lock;
    final Condition bon, acc, boll, f, pag;
    private JSONArray data;

    public SharedVariables(){
        this.lock=new ReentrantLock();
        this.data=new JSONArray();
        this.bon=lock.newCondition();
        this.acc=lock.newCondition();
        this.boll=lock.newCondition();
        this.f=lock.newCondition();
        this.pag=lock.newCondition();

        this.bonifico=this.accredito=this.bollettino=this.f24=this.pagobancomat=0;
    }

    public void put(JSONObject obj){
        lock.lock();
        data.add(obj);
        lock.unlock();
    }

    public void addBonifico() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(bon))
            bon.await();

        bonifico++;
        bon.signalAll();
        lock.unlock();
    }

    public void addAccredito() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(acc))
            acc.await();

        accredito++;
        acc.signalAll();
        lock.unlock();
    }

    private void addBollettino() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(boll))
            boll.await();

        bollettino++;
        boll.signalAll();
        lock.unlock();
    }

    public void addF24() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(f))
            f.await();

        f24++;
        f.signalAll();
        lock.unlock();
    }

    public void addPagoBancomat() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(pag))
            pag.await();

        pagobancomat++;
        pag.signalAll();
        lock.unlock();
    }
}
