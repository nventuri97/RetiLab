package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainClass {

    public static void main(String[] args) {
        //Prendo il path della cartella passata
        String main_path=args[0];

	    //Creo l'istanza per l'utilizzo della struttura condivisa
        SharedStructure shared=new SharedStructure();

        //Genero casualmente un numero di consumatori tra 1 e 10
        int k=(int) (Math.random()*9)+1;

        Producer produttore= new Producer();
        Thread p=new Thread(produttore);
        p.start();

        for(int i=0;i<k;i++){
            Consumer consumatore=new Consumer();
            Thread c= new Thread(consumatore);
            c.start();
        }
    }
}
