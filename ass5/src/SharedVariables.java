import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedVariables {
    //variabili condivise che andrò a modificare in mutua esclusione
    private int bonifico, accredito, bollettino, f24, pagobancomat;

    //Lock e variabile condivisa
    private ReentrantLock lock;
    private Condition access;

    public SharedVariables(){
        this.lock=new ReentrantLock();
        this.access=lock.newCondition();

        this.bonifico=this.accredito=this.bollettino=this.f24=this.pagobancomat=0;
    }

    private void addBonifico() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(access))
            access.await();

        bonifico++;
        access.signalAll();
        lock.unlock();
    }

    private void addAccredito() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(access))
            access.await();

        accredito++;
        access.signalAll();
        lock.unlock();
    }

    private void addBollettino() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(access))
            access.await();

        bollettino++;
        access.signalAll();
        lock.unlock();
    }

    private void addF24() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(access))
            access.await();

        f24++;
        access.signalAll();
        lock.unlock();
    }

    private void addPagoBancomat() throws InterruptedException{
        lock.lock();

        while(lock.isLocked() || lock.hasWaiters(access))
            access.await();

        pagobancomat++;
        access.signalAll();
        lock.unlock();
    }
}
