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
    private boolean mod_bon, mod_acc, mod_bol, mod_f, mod_pag;

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
        this.mod_acc=this.mod_bol=this.mod_bon=this.mod_f=this.mod_pag=false;
    }

    public void put(JSONObject obj){
        block.lock();
        data.add(obj);
        block.unlock();
    }

    public JSONObject get(){
        block.lock();

        try {
            //Si blocca se la lista è vuota o ci sono altri in attesa
            while(data.isEmpty() || block.hasWaiters(c_data))
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

        while(mod_bon || block.hasWaiters(bon))
            bon.await();

        mod_bon=true;
        bonifico++;
        mod_bon=false;
        bon.signalAll();
        block.unlock();
    }

    public void addAccredito() throws InterruptedException{
        block.lock();

        while(mod_acc || block.hasWaiters(acc))
            acc.await();

        mod_acc=true;
        accredito++;
        mod_acc=false;
        acc.signalAll();
        block.unlock();
    }

    public void addBollettino() throws InterruptedException{
        block.lock();

        while(mod_bol || block.hasWaiters(boll))
            boll.await();

        mod_bol=true;
        bollettino++;
        mod_bol=false;
        boll.signalAll();
        block.unlock();
    }

    public void addF24() throws InterruptedException{
        block.lock();

        while(mod_f || block.hasWaiters(f))
            f.await();

        mod_f=true;
        f24++;
        mod_f=false;
        f.signalAll();
        block.unlock();
    }

    public void addPagoBancomat() throws InterruptedException{
        block.lock();

        while(mod_pag || block.hasWaiters(pag))
            pag.await();

        mod_pag=true;
        pagobancomat++;
        mod_pag=false;
        pag.signalAll();
        block.unlock();
    }

    public void printer(){
        System.out.print("Bonifici "+this.bonifico+"\n");
        System.out.print("Accrediti "+this.accredito+"\n");
        System.out.print("Bollettini "+this.bollettino+"\n");
        System.out.print("F24 "+this.f24+"\n");
        System.out.print("PagoBancomat "+this.pagobancomat+"\n");
    }
}
