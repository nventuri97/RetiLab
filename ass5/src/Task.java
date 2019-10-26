import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Task implements Runnable {
    private JSONObject account;
    private SharedVariables sh;

    public Task(JSONObject cc, SharedVariables sh){
        this.account=cc;
        this.sh=sh;
    }

    public void run() {
        JSONArray movements=(JSONArray) account.get("Movements");
        while(!movements.isEmpty()){
            JSONObject str=(JSONObject) movements.remove(0);
            String casual=(String) str.get("Casual");
            try {
                switch (casual) {
                    case "Bonifico":
                        sh.addBonifico();
                        break;
                    case "Accredito":
                        sh.addAccredito();
                        break;
                    case "Bollettino":
                        sh.addBollettino();
                        break;
                    case "F24":
                        sh.addF24();
                        break;
                    case "PagoBancomat":
                        sh.addPagoBancomat();
                        break;
                }
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}