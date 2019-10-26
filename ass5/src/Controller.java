import java.util.concurrent.*;

public class Controller {
    private ExecutorService controllers;

    public Controller(){
        this.controllers=Executors.newCachedThreadPool();
    }

    public void executeTask(Task task){
        controllers.execute(task);
    }

    public void atClose(){
        controllers.shutdown();
    }
}
