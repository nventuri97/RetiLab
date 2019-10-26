import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SharedVariables {
    //variabili condivise che andrò a modificare in mutua esclusione
    private int bonifico, accredito, bollettino, f24, pagobancomat;

    //Lock e variabili di condizione per ogni variabile condivisa
    private ReentrantLock block;
    final Condition c_data;
    final Condition modifing;
    private JSONArray data;
    private int[] counter=new int[5];
    boolean mod;


    public SharedVariables(){
        //Inizializzo la lock per gestire la mutua esclusione
        this.block=new ReentrantLock();
        //JSONArray in cui saranno salvati i singoli conti correnti
        this.data=new JSONArray();

        //Inizializzo tutte le variabili di condizione che mi servono per gestire i contatori
        this.c_data=block.newCondition();
        this.modifing=block.newCondition();
        this.mod=false;
        for(int i=0;i<5;i++)
            counter[i]=0;
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

    public void addCasual(int ind){
        block.lock();

        try {
            while (mod || block.hasWaiters(modifing))
                modifing.await();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        mod=true;
        counter[ind]++;
        mod=false;
        modifing.signalAll();
        block.unlock();
    }

    public void printer(){
        System.out.print("Bonifici "+this.counter[0]+"\n");
        System.out.print("Accrediti "+this.counter[1]+"\n");
        System.out.print("Bollettini "+this.counter[2]+"\n");
        System.out.print("F24 "+this.counter[3]+"\n");
        System.out.print("PagoBancomat "+this.counter[4]+"\n");
    }
}
