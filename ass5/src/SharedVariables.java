import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedVariables {
    //variabili condivise che andrò a modificare in mutua esclusione
    private int bonifico, accredito, bollettino, f24, pagobancomat;

    //Lock e variabili di condizione per ogni variabile condivisa
    private ReentrantLock block;
    final Condition bon, acc, boll, f, pag, c_data;
    private JSONArray data;

    public SharedVariables(){
        //Inizializzo la lock per gestire la mutua esclusione
        this.block=new ReentrantLock();
        //JSONArray in cui saranno salvati i singoli conti correnti
        this.data=new JSONArray();

        //Inizializzo tutte le variabili di condizione che mi servono per gestire i contatori
        this.c_data=block.newCondition();
        this.bon=block.newCondition();
        this.acc=block.newCondition();
        this.boll=block.newCondition();
        this.f=block.newCondition();
        this.pag=block.newCondition();
        //variabili condivise, contatori numero causali
        this.bonifico=this.accredito=this.bollettino=this.f24=this.pagobancomat=0;
    }

    public void put(JSONObject obj){
        block.lock();
        data.add(obj);
        block.unlock();
    }

    public JSONObject get(){
        block.lock();

        try {
            while (data.isEmpty() || block.hasWaiters(c_data))
                c_data.await();

            return (JSONObject) data.get(0);
        } catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            c_data.signalAll();
            block.unlock();
        }

        return null;
    }

    public boolean emptyArray(){
        return data.isEmpty();
    }

    public void addBonifico() throws InterruptedException{
        block.lock();

        while(block.isLocked() || block.hasWaiters(bon))
            bon.await();

        bonifico++;
        bon.signalAll();
        block.unlock();
    }

    public void addAccredito() throws InterruptedException{
        block.lock();

        while(block.isLocked() || block.hasWaiters(acc))
            acc.await();

        accredito++;
        acc.signalAll();
        block.unlock();
    }

    public void addBollettino() throws InterruptedException{
        block.lock();

        while(block.isLocked() || block.hasWaiters(boll))
            boll.await();

        bollettino++;
        boll.signalAll();
        block.unlock();
    }

    public void addF24() throws InterruptedException{
        block.lock();

        while(block.isLocked() || block.hasWaiters(f))
            f.await();

        f24++;
        f.signalAll();
        block.unlock();
    }

    public void addPagoBancomat() throws InterruptedException{
        block.lock();

        while(block.isLocked() || block.hasWaiters(pag))
            pag.await();

        pagobancomat++;
        pag.signalAll();
        block.unlock();
    }
}
