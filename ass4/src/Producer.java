import java.io.File;
import java.io.IOException;

public class Producer implements Runnable {
    private String main_dir;
    private SharedStructure sh;

    public Producer(String dir, SharedStructure sh){
        this.main_dir=dir;
        this.sh=sh;
    }

    public void run(){
        File dir=new File(main_dir);
        recursiveVisit(dir);
        this.sh.setFinish();
    }

    public void recursiveVisit(File dir){
        if(dir.isDirectory()){
            try {
                //insirisco il path all'interno della lista
                //uso getCanonicalPath per evitare problemi con le cartelle . e ..
                sh.put(dir.getCanonicalPath());
                File[] list=dir.listFiles();
                if(list!=null)
                    for(File file: list){
                        //se il file non è null chiamo la funzione ricorsiva sul file per guardarne il contenuto
                        if(file!=null)
                            recursiveVisit(file);
                    }
            } catch(InterruptedException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
