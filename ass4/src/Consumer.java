import java.io.File;
import java.io.IOException;

public class Consumer implements Runnable {
    private SharedStructure sh;

    public Consumer(SharedStructure sh){
        this.sh=sh;
    }

    public void run(){
        while(!sh.emptyQueue() || sh.isFinish()) {
            try {
                String path = sh.get();
                if(path!=null) {
                    File dir=new File(path);
                    if (dir.isDirectory()) {
                        File[] files = dir.listFiles();
                        if (files != null)
                            for (File file : files) {
                                if (file != null)
                                    if (file.isDirectory()) {
                                        System.out.print("Directory: " + file.getCanonicalFile() + "\n");
                                        System.out.flush();
                                    } else if (file.isFile()) {
                                        System.out.print("File: " + file.getCanonicalFile() + "\n");
                                        System.out.flush();
                                    }
                            }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
