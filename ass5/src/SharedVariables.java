import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedVariables {
    //variabili condivise che andrò a modificare in mutua esclusione
    private int bonifico, accredito, bollettino, f24, pagobancomat;

    //Lock e variabili di condizione per ogni variabile condivisa
    private ReentrantLock lock;
    final Condition bon, acc, boll, f, pag;

    public SharedVariables(){
        this.lock=new ReentrantLock();
        this.bon=lock.newCondition();
        this.acc=lock.newCondition();
        this.boll=lock.newCondition();
        this.f=lock.newCondition();
        this.pag=lock.newCondition();

        this.bonifico=this.accredito=this.bollettino=this.f24=this.pagobancomat=0;
    }

    private void addBonifico() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(bon))
            bon.await();

        bonifico++;
        bon.signalAll();
        lock.unlock();
    }

    private void addAccredito() throws InterruptedException{
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

    private void addF24() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(f))
            f.await();

        f24++;
        f.signalAll();
        lock.unlock();
    }

    private void addPagoBancomat() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(pag))
            pag.await();

        pagobancomat++;
        pag.signalAll();
        lock.unlock();
    }
}
