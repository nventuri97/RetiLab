import java.io.File;

public class Consumer implements Runnable {
    private SharedStructure sh;

    public Consumer(SharedStructure sh){
        this.sh=sh;
    }

    public void run(){
        while(!sh.emptyQueue() || sh.isFinish()) {
            try {
                File dir = sh.get();
                if (dir.isDirectory()) {
                    File[] files = dir.listFiles();
                    if(files!=null)
                        for (File file : files) {
                            if(file!=null)
                                if(file.isDirectory()) {
                                    System.out.print("Directory: " + file + "\n");
                                    System.out.flush();
                                }
                                else if(file.isFile()) {
                                    System.out.print("File: " + file + "\n");
                                    System.out.flush();
                                }
                        }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
