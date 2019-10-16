package com.company;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class MedEquipe {
    final Condition white;
    final Condition yellow;
    final Condition red;
    protected int medCounter;
    ArrayList<Boolean> equipe=new ArrayList(10);
    ReentrantLock visita;

    public MedEquipe(){
        this.visita=new ReentrantLock();
        this.medCounter=0;

        //Inizializzo le tre variabili di condizione
        this.white=this.visita.newCondition();
        this.yellow=this.visita.newCondition();
        this.red=this.visita.newCondition();

        //Aggiungo i medici alla lista settandoli a false
        for(int i=0;i<10;i++)
            this.equipe.add(false);
    }

    public void setRedMed(){
        for(Boolean m: equipe)
            m=true;
        this.medCounter=10;
    }

    public void unsetRedMed(){
        for(Boolean m: equipe)
            m=false;
        this.medCounter=0;
    }

    public int setWhiteMed(){
        int i;
        for(i=0;i<10;i++) {
            if (equipe.get(i) == false) {
                equipe.set(i, true);
                break;
            }
        }
        this.medCounter+=1;
        return i;
    }
}