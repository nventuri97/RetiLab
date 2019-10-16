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
    public void redcode(int cod, String color, MedEquipe medici){
        for(int i=0; i<this.k; i++) {
            long v_time = (long) (Math.random() * 100) + 1;
            long wait_time = (long) (Math.random() * 100) + 1;
            medici.visita.lock();

            try {
                while (medici.visita.hasWaiters(medici.red))
                    medici.red.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            medici.setRedMed();

            try {
                this.visita(i, v_time);
                medici.unsetRedMed();
                medici.red.notifyAll();
                medici.visita.unlock();
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo per la gestione dei codici gialli
    public void yellowcode(int cod, String color, MedEquipe medici, int ind){
        for(int i=0;i<k;i++){
            long v_time = (long) (Math.random() * 100) + 1;
            long wait_time = (long) (Math.random() * 100) + 1;
            medici.visita.lock();

            try {
                while(medici.equipe.get(ind)==true || medici.visita.hasWaiters(medici.red) || medici.visita.hasWaiters(medici.yellow))
                    medici.yellow.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            medici.equipe.set(ind, true);
            try {
                this.visita(i, v_time);
                medici.equipe.set(ind, false);
                medici.yellow.notifyAll();
                medici.visita.unlock();
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo per la gestione dei codici bianchi
    public void whitecode(int cod, String color, MedEquipe medici){
        for(int i=0;i<k;i++){
            long v_time = (long) (Math.random() * 100) + 1;
            long wait_time = (long) (Math.random() * 100) + 1;
            medici.visita.lock();

            try {
                while(medici.visita.hasWaiters(medici.red) || medici.visita.hasWaiters(medici.yellow) || medici.visita.hasWaiters(medici.white))
                    medici.white.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int ind=medici.setWhiteMed();
            try {
                this.visita(i, v_time);
                medici.equipe.set(ind, false);
                medici.white.notifyAll();
                medici.visita.unlock();
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //Metodo per la simulazione di una visita
    public void visita(int i, long v_time) throws InterruptedException{
        System.out.print("Paziente "+cod+" entra in codice "+color+" per la visita "+i+"\n");
        Thread.sleep(v_time);
        System.out.print("Paziente "+cod+" esce dalla visita "+i+"\n");
    }
}