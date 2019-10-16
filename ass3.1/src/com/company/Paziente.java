package com.company;

public class Paziente extends Thread {
    private int cod;
    private String color;
    private MedEquipe medici;
    private int k;

    public Paziente(int codice, String color, MedEquipe med){
        this.cod=codice;
        this.color=color;
        this.medici=med;
        this.k=(int)(Math.random()*5)+1;
    }

    @Override
    public void run(){
        switch (color) {
            case "B":
                this.whitecode(this.cod, this.color, this.medici);
                break;
            case "G":
                int doc = (int) (Math.random() * 9);
                this.yellowcode(this.cod, this.color, this.medici, doc);
                break;
            case "R":
                this.redcode(this.cod, this.color, this.medici);
                break;
        }
    }

    //Metodo per la gestione dei codici rossi
    private void redcode(int cod, String color, MedEquipe medici){
        //DEVO FARE K VISITE
        for(int i=0; i<this.k; i++) {
            long v_time = (long) (Math.random() * 100) + 1;
            long wait_time = (long) (Math.random() * 100) + 1;

            //entro in mutua esclusione per poter lavorare sui medici
            medici.visita.lock();

            try {
                //se qualcuno è già in coda o i medici sono occupati mi fermo
                while (medici.visita.hasWaiters(medici.red) || medici.medCounter!=0)
                    medici.red.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            medici.setRedMed();

            try {
                this.visita(i, v_time);
                medici.unsetRedMed();
                medici.red.signalAll();
                //rilascio la mutua esclusione dopo aver svegliato i possibili thread che si sono messi in attesa
                medici.visita.unlock();
                Thread.sleep(wait_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo per la gestione dei codici gialli
    private void yellowcode(int cod, String color, MedEquipe medici, int ind){
        //DEVO FARE K VISITE
        for(int i=0;i<k;i++){
            long v_time = (long) (Math.random() * 100) + 1;
            long wait_time = (long) (Math.random() * 100) + 1;

            //entro in mutua esclusione per poter lavorare sui medici
            medici.visita.lock();

            try {
                while(medici.equipe.get(ind) || medici.visita.hasWaiters(medici.red) || medici.visita.hasWaiters(medici.yellow))
                    medici.yellow.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            medici.equipe.set(ind, true);
            medici.medCounter+=1;
            try {
                this.visita(i, v_time);
                medici.equipe.set(ind, false);
                medici.medCounter-=1;
                medici.red.signalAll();
                medici.yellow.signalAll();
                //rilascio la mutua esclusione dopo aver svegliato i possibili thread che si sono messi in attesa
                medici.visita.unlock();
                Thread.sleep(wait_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo per la gestione dei codici bianchi
    private void whitecode(int cod, String color, MedEquipe medici){
        //DEVO FARE K VISITE
        for(int i=0;i<k;i++){
            long v_time = (long) (Math.random() * 100) + 1;
            long wait_time = (long) (Math.random() * 100) + 1;

            //entro in mutua esclusione per poter lavorare sui medici
            medici.visita.lock();

            try {
                while(medici.visita.hasWaiters(medici.red) || medici.visita.hasWaiters(medici.yellow) || medici.visita.hasWaiters(medici.white) || medici.medCounter==10)
                    medici.white.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int ind=medici.setWhiteMed();
            try {
                this.visita(i, v_time);
                medici.equipe.set(ind, false);
                medici.medCounter-=1;
                medici.red.signalAll();
                medici.yellow.signalAll();
                medici.white.signalAll();
                //rilascio la mutua esclusione dopo aver svegliato i possibili thread che si sono messi in attesa
                medici.visita.unlock();
                Thread.sleep(wait_time);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo per la simulazione di una visita
    private void visita(int i, long v_time) throws InterruptedException{
        System.out.print("Paziente "+cod+" entra in codice "+color+" per la visita "+i+"\n");
        Thread.sleep(v_time);
        System.out.print("Paziente "+cod+" esce dalla visita "+i+"\n");
    }
}