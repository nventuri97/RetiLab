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
        System.out.println(k);

        Producer produttore= new Producer(main_path, shared);
        Thread p=new Thread(produttore);
        p.start();
        try{
            p.join();
        } catch(InterruptedException e){
            e.printStackTrace();
        }

        for(int i=0;i<k;i++){
            Consumer consumatore=new Consumer(shared);
            Thread c= new Thread(consumatore);
            c.start();
            try {
                c.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
