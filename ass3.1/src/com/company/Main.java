package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //Ricevo da linea di comando i numeri per i codici bianco, giallo e rosso
        int b=Integer.parseInt(args[0]);
        int g=Integer.parseInt(args[1]);
        int r=Integer.parseInt(args[2]);

        //Creo un'istanza della classe MedEquipe per inizializzare
        MedEquipe medici= new MedEquipe();

        //Creo la lista di attesa per i pazienti
        ArrayList<Paziente> attesa=new ArrayList();

        int i;
        //aggiungo i pazienti rossi in cima alla lista
        for(i=0;i<r;i++){
            Paziente paziente=new Paziente(i+1, "R", medici);
            paziente.setPriority(Thread.MAX_PRIORITY);
            attesa.add(paziente);
        }

        //aggiungo i pazienti gialli
        for(i=r;i<r+g;i++){
            Paziente paziente=new Paziente(i+1, "G", medici);
            paziente.setPriority(Thread.NORM_PRIORITY);
            attesa.add(paziente);
        }

        //aggiungo i pazienti bianchi

        for(i=r+g;i<r+g+b;i++){
            Paziente paziente=new Paziente(i+1, "B", medici);
            paziente.setPriority(Thread.MIN_PRIORITY);
            attesa.add(paziente);
        }

        for(Paziente p: attesa) {
            p.start();
        }
    }
}