import java.util.concurrent.*;
import java.nio.*;

public class MainClass {

    public static void main(String[] args){
        //Creo istanza variabili condivise
        SharedVariables list=new SharedVariables();
        //Creo il pool di thread
        Controller pool=new Controller();
        //path del file json
        String path="Account.json";

        //Creo istanza del file lettore e lo eseguo
        ReaderThread reader=new ReaderThread(path, list);
        reader.run();

        while (reader.isAlive() || !list.emptyArray()){
            Task task=new Task(list.get(), list);

            pool.executeTask(task);
        }

        pool.atClose();
        list.printer();

    }
}
